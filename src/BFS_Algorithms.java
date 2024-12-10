import java.util.Scanner;

public class BFS_Algorithms {

    private static final int GRID_SIZE = 8;
    private static final int TOTAL_STEPS = 63;
    private static long pathCount = 0;

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        while (true) { // Loop until valid input is provided
            String input = getInput(); // Get input from the user

            if (!isValidInput(input)) {
                System.out.println("Invalid input. Input must be a string of 63 characters containing only 'U', 'D', 'L', 'R', or '*'.");
                continue; // Ask again if input is invalid
            }

            System.out.println("Waiting...");
            long startTime = System.currentTimeMillis();

            long visitedMask = 1L; // start at (0,0) visited
            explorePaths(input, 0, 0, 0, visitedMask);

            long endTime = System.currentTimeMillis();
            System.out.println("Total paths: " + pathCount);
            System.out.println("Time (ms): " + (endTime - startTime));
            break; // Exit the loop after successful execution
        }
    }

    private static String getInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input the characters to test: ");
        return scanner.nextLine();
    }

    private static boolean isValidInput(String input) {
        if (input.length() != 63) return false;
        for (char c : input.toCharArray()) {
            if (c != 'U' && c != 'D' && c != 'L' && c != 'R' && c != '*') {
                return false;
            }
        }
        return true;
    }

    private static void explorePaths(String path, int row, int col, int step, long visitedMask) {
        if (step == TOTAL_STEPS) {
            // Check if we ended at (7,0)
            if (row == GRID_SIZE - 1 && col == 0) {
                pathCount++;
            }
            return;
        }

        char direction = path.charAt(step);

        // Up
        if (direction == 'U' || direction == '*') {
            if (row > 0) {
                int nr = row - 1, nc = col;
                int idx = (nr << 3) + nc;
                long mask = 1L << idx;
                if ((visitedMask & mask) == 0) {
                    long newMask = visitedMask | mask;
                    if (isStillViable(newMask)) {
                        explorePaths(path, nr, nc, step + 1, newMask);
                    }
                }
            }
        }

        // Down
        if (direction == 'D' || direction == '*') {
            if (row < GRID_SIZE - 1) {
                int nr = row + 1, nc = col;
                int idx = (nr << 3) + nc;
                long mask = 1L << idx;
                if ((visitedMask & mask) == 0) {
                    long newMask = visitedMask | mask;
                    if (isStillViable(newMask)) {
                        explorePaths(path, nr, nc, step + 1, newMask);
                    }
                }
            }
        }

        // Left
        if (direction == 'L' || direction == '*') {
            if (col > 0) {
                int nr = row, nc = col - 1;
                int idx = (nr << 3) + nc;
                long mask = 1L << idx;
                if ((visitedMask & mask) == 0) {
                    long newMask = visitedMask | mask;
                    if (isStillViable(newMask)) {
                        explorePaths(path, nr, nc, step + 1, newMask);
                    }
                }
            }
        }

        // Right
        if (direction == 'R' || direction == '*') {
            if (col < GRID_SIZE - 1) {
                int nr = row, nc = col + 1;
                int idx = (nr << 3) + nc;
                long mask = 1L << idx;
                if ((visitedMask & mask) == 0) {
                    long newMask = visitedMask | mask;
                    if (isStillViable(newMask)) {
                        explorePaths(path, nr, nc, step + 1, newMask);
                    }
                }
            }
        }
    }

    /**
     * Check if the remaining unvisited cells are still all reachable from the target cell (7,0).
     * If not, prune.
     */
    private static boolean isStillViable(long visitedMask) {
        int visitedCount = Long.bitCount(visitedMask);
        if (visitedCount == 64) return true; // all visited => viable

        int startRow = 7, startCol = 0;
        int startIndex = (startRow << 3) + startCol;
        long startMask = 1L << startIndex;

        // If (7,0) is visited, no BFS from that cell can proceed on unvisited cells.
        // That would mean we ended up at the goal too early. Prune in that scenario.
        if ((visitedMask & startMask) != 0) {
            return false;
        }

        // BFS from (7,0) over unvisited cells
        // We'll use a small custom queue implemented with arrays
        boolean[] visitedForBFS = new boolean[64];
        visitedForBFS[startIndex] = true;

        int[] queue = new int[64];
        int head = 0;
        int tail = 0;
        queue[tail++] = startIndex; // enqueue

        int reachableCount = 0;
        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        while (head < tail) {
            int cell = queue[head++];
            reachableCount++;
            int r = cell >> 3;
            int c = cell & 7;

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];
                if (nr >= 0 && nr < GRID_SIZE && nc >= 0 && nc < GRID_SIZE) {
                    int nIdx = (nr << 3) + nc;
                    long bit = 1L << nIdx;
                    // Check if unvisited
                    if ((visitedMask & bit) == 0 && !visitedForBFS[nIdx]) {
                        visitedForBFS[nIdx] = true;
                        queue[tail++] = nIdx; // enqueue
                    }
                }
            }
        }

        int unvisitedCount = 64 - visitedCount;
        return reachableCount == unvisitedCount;
    }
}
