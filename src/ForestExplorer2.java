import java.util.Scanner;

public class ForestExplorer2 { // around 8 mins

    private static final int GRID_SIZE = 8;
    private static boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
    private static boolean[][] usedInPaths = new boolean[GRID_SIZE][GRID_SIZE]; // Track cells used in previous paths
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

//        if (args.length > 0) {
//            input = args[0];
//        }

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
        // Skip cells already used in previous valid paths during this exploration
        if (usedInPaths[row][col]) {
            return;
        }

        // Print current state
        System.out.println("Step: " + step + ", Position: (" + row + ", " + col + "), Direction: " + (step < 63 ? path.charAt(step) : "N/A"));

        // Base case: Successfully reached the goal
        if (row == GRID_SIZE - 1 && col == 0 && step == 63) {
            pathCount++;
            System.out.println("Path found! Total paths so far: " + pathCount);
            markPathAsUsed(); // Mark current path for pruning
            return;
        }

        // Base case: Steps exhausted but not at the goal
        if (step == 63) {
            return;
        }

        // Mark the current cell as visited
        visited[row][col] = true;

        // Check all possible moves based on the current direction
        char direction = path.charAt(step);
        if (direction == 'U' || direction == '*') {
            if (row > 0 && !visited[row - 1][col]) {
                explorePaths(path, row - 1, col, step + 1);
            }
        }
        if (direction == 'D' || direction == '*') {
            if (row < GRID_SIZE - 1 && !visited[row + 1][col]) {
                explorePaths(path, row + 1, col, step + 1);
            }
        }
        if (direction == 'L' || direction == '*') {
            if (col > 0 && !visited[row][col - 1]) {
                explorePaths(path, row, col - 1, step + 1);
            }
        }
        if (direction == 'R' || direction == '*') {
            if (col < GRID_SIZE - 1 && !visited[row][col + 1]) {
                explorePaths(path, row, col + 1, step + 1);
            }
        }

        // Backtrack: Unmark the current cell
        visited[row][col] = false;
    }

    private static void markPathAsUsed() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (visited[row][col]) {
                    usedInPaths[row][col] = true; // Temporarily block this cell for other paths
                }
            }
        }

        // Reset usedInPaths after one path completes to allow alternative explorations
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (visited[row][col]) {
                    usedInPaths[row][col] = false; // Allow the cell for future paths
                }
            }
        }
    }
}





//public class ForestExplorer2 { // around 8 mins
//
//    private static final int GRID_SIZE = 8;
//    private static boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
//    private static boolean[][] usedInPaths = new boolean[GRID_SIZE][GRID_SIZE]; // Track cells used in previous paths
//    private static long pathCount = 0;
//
//    public static void main(String[] args) {
//        String input = "DDDDDDRUUUUUURDDDDDDRUUUUUURDDDDDDR****************************";
//
//        if (args.length > 0) {
//            input = args[0];
//        }
//
//        // Validate input
//        if (!isValidInput(input)) {
//            System.out.println("Invalid input. Input must be a string of 63 characters containing only 'U', 'D', 'L', 'R', or '*'.");
//            return;
//        }
//
//        long startTime = System.currentTimeMillis();
//
//        explorePaths(input, 0, 0, 0);
//
//        long endTime = System.currentTimeMillis();
//
//        System.out.println("Total paths: " + pathCount);
//        System.out.println("Time (ms): " + (endTime - startTime));
//    }
//
//    private static boolean isValidInput(String input) {
//        if (input.length() != 63) {
//            return false;
//        }
//        for (char c : input.toCharArray()) {
//            if (c != 'U' && c != 'D' && c != 'L' && c != 'R' && c != '*') {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private static void explorePaths(String path, int row, int col, int step) {
//        // Skip cells already used in previous valid paths during this exploration
//        if (usedInPaths[row][col]) {
//            return;
//        }
//
//        // Base case: Successfully reached the goal
//        if (row == GRID_SIZE - 1 && col == 0 && step == 63) {
//            pathCount++;
//            markPathAsUsed(); // Mark current path for pruning
//            return;
//        }
//
//        // Base case: Steps exhausted but not at the goal
//        if (step == 63) {
//            return;
//        }
//
//        // Mark the current cell as visited
//        visited[row][col] = true;
//
//        // Check all possible moves based on the current direction
//        char direction = path.charAt(step);
//        if (direction == 'U' || direction == '*') {
//            if (row > 0 && !visited[row - 1][col]) {
//                explorePaths(path, row - 1, col, step + 1);
//            }
//        }
//        if (direction == 'D' || direction == '*') {
//            if (row < GRID_SIZE - 1 && !visited[row + 1][col]) {
//                explorePaths(path, row + 1, col, step + 1);
//            }
//        }
//        if (direction == 'L' || direction == '*') {
//            if (col > 0 && !visited[row][col - 1]) {
//                explorePaths(path, row, col - 1, step + 1);
//            }
//        }
//        if (direction == 'R' || direction == '*') {
//            if (col < GRID_SIZE - 1 && !visited[row][col + 1]) {
//                explorePaths(path, row, col + 1, step + 1);
//            }
//        }
//
//        // Backtrack: Unmark the current cell
//        visited[row][col] = false;
//    }
//
//    private static void markPathAsUsed() {
//        for (int row = 0; row < GRID_SIZE; row++) {
//            for (int col = 0; col < GRID_SIZE; col++) {
//                if (visited[row][col]) {
//                    usedInPaths[row][col] = true; // Temporarily block this cell for other paths
//                }
//            }
//        }
//
//        // Reset usedInPaths after one path completes to allow alternative explorations
//        for (int row = 0; row < GRID_SIZE; row++) {
//            for (int col = 0; col < GRID_SIZE; col++) {
//                if (visited[row][col]) {
//                    usedInPaths[row][col] = false; // Allow the cell for future paths
//                }
//            }
//        }
//    }
//}
//
//
//
//
