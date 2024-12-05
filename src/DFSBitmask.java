public class DFSBitmask { // 10 mins
    static final int N = 8; // Grid size
    static final int TOTAL_MOVES = 63; // Total moves
    static int totalPaths = 0; // Counter for valid paths
    static int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static char[] moveChars = {'U', 'D', 'L', 'R'};

    public static void main(String[] args) {
        String inputPath = "*****DR******R******R********************R*D************L******";

        if (!isValidInput(inputPath)) {
            System.out.println("Invalid input. Ensure 63 characters from {U, D, L, R, *}.");
            return;
        }

        long startTime = System.currentTimeMillis();
        dfs(0, 0, 0, 1L, inputPath); // Start DFS from the top-left corner
        long endTime = System.currentTimeMillis();

        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    public static boolean isValidInput(String path) {
        return path.length() == TOTAL_MOVES && path.matches("[UDLR*]+");
    }

    public static void dfs(int x, int y, int step, long visited, String path) {
        // Base case: If all steps are completed, check if the endpoint is valid
        if (step == TOTAL_MOVES) {
            if (x == 7 && y == 0) {
                totalPaths++;
            }
            return;
        }

        char direction = path.charAt(step);

        // Explore all possible moves
        for (int i = 0; i < directions.length; i++) {
            if (direction != '*' && direction != moveChars[i]) {
                continue; // Skip invalid moves
            }

            int newX = x + directions[i][0];
            int newY = y + directions[i][1];

            // Check if the move is valid
            if (isValidMove(newX, newY, visited)) {
                long newVisited = visited | (1L << (newX * N + newY)); // Mark the cell as visited
                dfs(newX, newY, step + 1, newVisited, path);
            }
        }
    }

    public static boolean isValidMove(int x, int y, long visited) {
        return x >= 0 && x < N && y >= 0 && y < N && ((visited & (1L << (x * N + y))) == 0);
    }
}
