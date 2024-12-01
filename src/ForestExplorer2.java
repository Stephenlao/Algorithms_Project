import java.util.Arrays;

public class ForestExplorer2 {

    private static final int GRID_SIZE = 8;
    private static boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
    private static long pathCount = 0;

    public static void main(String[] args) {
        String input = "*****DR******R******R********************R*D************L******"; // 7 mins
        if (args.length > 0) {
            input = args[0];
        }

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

    private static void explorePaths(String path, int row, int col, int step) {
        if (row == GRID_SIZE - 1 && col == 0 && step == 63) {
            pathCount++;
            return;
        }

        if (step == 63) {
            return;
        }

        visited[row][col] = true;

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

        visited[row][col] = false;
    }
}