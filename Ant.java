import java.util.Arrays;
import java.util.Random;

public class Ant {
    private int[] tour;
    private double[][] pheromoneLevels;
    public Ant(int numCities, double[][] pheromoneLevels) {
        tour = new int[numCities];
        this.pheromoneLevels = pheromoneLevels;
    }
    public int[] getTour() {
        return tour;
    }
    public void constructTour(int startCity, int[][] distanceMatrix) {
        int numCities = distanceMatrix.length;
        boolean[] visited = new boolean[numCities];
        Arrays.fill(visited, false);
        tour[0] = startCity;
        visited[startCity] = true;
        Random random = new Random();
        for (int i = 1; i < numCities; i++) {
            int currentCity = tour[i - 1];
            int nextCity = selectNextCity(currentCity, visited, distanceMatrix);
            tour[i] = nextCity;
            visited[nextCity] = true;
        }
    }
    private int selectNextCity(int currentCity, boolean[] visited, int[][] distanceMatrix) {
        int numCities = distanceMatrix.length;
        double[] probabilities = new double[numCities];
        double totalProbability = 0.0;
        for (int city = 0; city < numCities; city++) {
            if (!visited[city]) {
                double pheromone = Math.pow(pheromoneLevels[currentCity][city], 1.0);
                double distance = Math.pow(1.0 / distanceMatrix[currentCity][city], 2.0);
                probabilities[city] = pheromone * distance;
                totalProbability += probabilities[city];
            }
        }
        double rand = Math.random() * totalProbability;
        double cumulativeProbability = 0.0;
        for (int city = 0; city < numCities; city++) {
            if (!visited[city]) {
                cumulativeProbability += probabilities[city];
                if (cumulativeProbability >= rand) {
                    return city;
                }
            }
        }
        return -1;
    }
}
