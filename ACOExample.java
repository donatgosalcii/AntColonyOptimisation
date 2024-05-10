import java.util.Arrays;
import java.util.Random;

public class ACOExample {
    public static void main(String[] args) {
        int numCities = 10;
        int[][] distanceMatrix = generateRandomDistanceMatrix(numCities);
        int numAnts = 5;
        double alpha = 1.0;
        double beta = 2.0;
        double evaporationRate = 0.5;
        AntColony antColony = new AntColony(distanceMatrix, numAnts, alpha, beta, evaporationRate);
        int maxIterations = 100;
        int[] bestTour = antColony.findBestTour(maxIterations);
        System.out.println("Best Tour: " + Arrays.toString(bestTour));
    }
    private static int[][] generateRandomDistanceMatrix(int numCities) {
        Random random = new Random();
        int[][] distanceMatrix = new int[numCities][numCities];
        for (int i = 0; i < numCities; i++) {
            for (int j = i + 1; j < numCities; j++) {
                int distance = random.nextInt(100) + 1;
                distanceMatrix[i][j] = distance;
                distanceMatrix[j][i] = distance;
            }
        }
        return distanceMatrix;
    }
}
