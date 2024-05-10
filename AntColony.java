import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AntColony {
    private int[][] distanceMatrix;
    private int numAnts;
    private double[][] pheromoneLevels;
    private double alpha;
    private double beta;
    private double evaporationRate;
    private int[] bestTour;
    public AntColony(int[][] distanceMatrix, int numAnts, double alpha, double beta, double evaporationRate) {
        this.distanceMatrix = distanceMatrix;
        this.numAnts = numAnts;
        this.alpha = alpha;
        this.beta = beta;
        this.evaporationRate = evaporationRate;
        int numCities = distanceMatrix.length;
        pheromoneLevels = new double[numCities][numCities];
        bestTour = new int[numCities];
        for (int i = 0; i < numCities; i++) {
            Arrays.fill(pheromoneLevels[i], 1.0);
        }
    }
    public int[] findBestTour(int maxIterations) {
        long startTime = System.nanoTime();
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            List<int[]> antTours = generateAntTours();
            updatePheromoneLevels(antTours);
            updateBestTour(antTours);
            evaporatePheromones();
        }
        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;
        System.out.println("Execution Time: " + (executionTime / 1e6) + " milliseconds");
        return bestTour;
    }
    private List<int[]> generateAntTours() {
        List<int[]> antTours = new ArrayList<>();
        Random random = new Random();
        for (int ant = 0; ant < numAnts; ant++) {
            int startCity = random.nextInt(distanceMatrix.length);
            Ant antObject = new Ant(distanceMatrix.length, pheromoneLevels);
            antObject.constructTour(startCity, distanceMatrix);
            antTours.add(antObject.getTour());
        }
        return antTours;
    }
    private void updatePheromoneLevels(List<int[]> antTours) {
        for (int i = 0; i < pheromoneLevels.length; i++) {
            Arrays.fill(pheromoneLevels[i], 0.0);
        }
        for (int ant = 0; ant < numAnts; ant++) {
            int[] tour = antTours.get(ant);
            double tourLength = calculateTourLength(tour);
            for (int i = 0; i < tour.length - 1; i++) {
                int city1 = tour[i];
                int city2 = tour[i + 1];
                pheromoneLevels[city1][city2] += 1.0 / tourLength;
                pheromoneLevels[city2][city1] += 1.0 / tourLength;
            }
        }
    }
    private double calculateTourLength(int[] tour) {
        double length = 0.0;
        for (int i = 0; i < tour.length - 1; i++) {
            int city1 = tour[i];
            int city2 = tour[i + 1];
            length += distanceMatrix[city1][city2];
        }
        int lastCity = tour[tour.length - 1];
        int firstCity = tour[0];
        length += distanceMatrix[lastCity][firstCity];
        return length;
    }
    private void updateBestTour(List<int[]> antTours) {
        double minTourLength = Double.MAX_VALUE;
        for (int ant = 0; ant < numAnts; ant++) {
            int[] tour = antTours.get(ant);
            double tourLength = calculateTourLength(tour);
            if (tourLength < minTourLength) {
                minTourLength = tourLength;
                System.arraycopy(tour, 0, bestTour, 0, tour.length);
            }
        }
    }
    private void evaporatePheromones() {
        for (int i = 0; i < pheromoneLevels.length; i++) {
            for (int j = 0; j < pheromoneLevels[i].length; j++) {
                pheromoneLevels[i][j] *= (1.0 - evaporationRate);
            }
        }
    }
}
