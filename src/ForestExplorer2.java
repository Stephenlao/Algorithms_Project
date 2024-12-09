import java.util.Scanner;

public class ForestExplorer2 { // around 8 mins

    private static final int GRID_SIZE = 8;
    private static boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
    private static long pathCount = 0;

    public static void main(String[] args) {
        start();
    }

    private static boolean isValidInput(String input) {
        if (input.length() != 63) {
            return false;
        }
        for (char c : input.toCharArray()) {
            if (c != 'U' && c != 'D' && c != 'L' && c != 'R' && c != '*') {
                return false;
            }
        }
        return true;
    }

    public static void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input the characters to test: ");
        String input = scanner.nextLine();

        // Validate input
        if (!isValidInput(input)) {
            System.out.println("Invalid input. Input must be a string of 63 characters containing only 'U', 'D', 'L', 'R', or '*'.");
            return;
        }

        long startTime = System.currentTimeMillis();

        explorePaths(input, 0, 0, 0);

        long endTime = System.currentTimeMillis();

        System.out.println("Total paths: " + pathCount);
        System.out.println("Time (ms): " + (endTime - startTime));


    }

    private static void explorePaths(String path, int row, int col, int step) {
        // Base case: Successfully reached the goal
        if (row == GRID_SIZE - 1 && col == 0 && step == 63) {
            pathCount++;
            return;
        }

        // Base case: Steps exhausted but not at the goal
        if (step == 63) {
            return;
        }

        // Mark the current cell as visited
        visited[row][col] = true;

        // Get the current direction
        char direction = path.charAt(step);

        // Explore possible directions
        if (direction == 'U' || direction == '*') {
            if (row > 0 && !visited[row - 1][col] && canReachGoal(row - 1, col, 63 - step - 1)) {
                explorePaths(path, row - 1, col, step + 1); // Up
            }
        }
        if (direction == 'D' || direction == '*') {
            if (row < GRID_SIZE - 1 && !visited[row + 1][col] && canReachGoal(row + 1, col, 63 - step - 1)) {
                explorePaths(path, row + 1, col, step + 1); // Down
            }
        }
        if (direction == 'L' || direction == '*') {
            if (col > 0 && !visited[row][col - 1] && canReachGoal(row, col - 1, 63 - step - 1)) {
                explorePaths(path, row, col - 1, step + 1); // Left
            }
        }
        if (direction == 'R' || direction == '*') {
            if (col < GRID_SIZE - 1 && !visited[row][col + 1] && canReachGoal(row, col + 1, 63 - step - 1)) {
                explorePaths(path, row, col + 1, step + 1); // Right
            }
        }

        // Backtrack: Unmark the current cell
        visited[row][col] = false;
    }


    private static boolean canReachGoal(int row, int col, int stepsRemaining) {
        // Calculate the Manhattan distance to the goal
        int manhattanDistance = Math.abs(row - (GRID_SIZE - 1)) + Math.abs(col - 0);

        // Check if the remaining steps are enough to reach the goal
        return stepsRemaining >= manhattanDistance;
    }
}
