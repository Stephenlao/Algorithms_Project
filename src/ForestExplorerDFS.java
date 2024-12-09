public class ForestExplorerDFS {
    private static final int GRID_SIZE = 8;
    private static final int TOTAL_MOVES = 63;
    private static final char[] DIRECTIONS = {'U', 'D', 'L', 'R', '*'};
    private static long totalPaths = 0;

    public static void main(String[] args) {
        String inputPath = "*****DR******R******R********************R*D************L******"; // Example input
        long startTime = System.currentTimeMillis();

        if (inputPath.length() != TOTAL_MOVES) {
            System.out.println("Invalid input length.");
            return;
        }

        boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
        visited[0][0] = true;

        explorePaths(0, 0, 0, visited, inputPath);

        long endTime = System.currentTimeMillis();

        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    private static void explorePaths(int row, int col, int step, boolean[][] visited, String path) {
        // Base case: If all moves are used, check if we reached the endpoint
        if (step == TOTAL_MOVES) {
            if (row == GRID_SIZE - 1 && col == 0) {
                totalPaths++;
            }
            return;
        }

        char nextMove = path.charAt(step);
        for (char direction : getNextDirections(nextMove)) {
            int newRow = row, newCol = col;

            switch (direction) {
                case 'U': newRow--; break;
                case 'D': newRow++; break;
                case 'L': newCol--; break;
                case 'R': newCol++; break;
            }

            if (isValidMove(newRow, newCol, visited)) {
                visited[newRow][newCol] = true;
                explorePaths(newRow, newCol, step + 1, visited, path);
                visited[newRow][newCol] = false; // Backtrack
            }
        }
    }

    private static boolean isValidMove(int row, int col, boolean[][] visited) {
        return row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE && !visited[row][col];
    }

    private static char[] getNextDirections(char move) {
        return move == '*' ? DIRECTIONS : new char[]{move};
    }
}
