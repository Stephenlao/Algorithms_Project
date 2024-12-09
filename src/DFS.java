public class DFS {
    static final int GRID_SIZE = 8; // Size of the grid (8x8)
    static int totalPaths = 0; // Total number of valid paths
    static boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE]; // Tracks visited cells

    public static void main(String[] args) {
        // Input sequence: Specify the path with directions and wildcards (*)
        // Example input: a string of directions, where '*' is a wildcard
        // String paths = "***************************************************************";
         String paths = "*****DR******R******R********************R*D************L******";
        // String paths = "***********************************";

        // Start timing the execution
        long startTime = System.currentTimeMillis();
        System.out.println("Starting now...");

        // Start exploring from the top-left corner (0,0)
        explore(0, 0, 0, paths);

        // End timing the execution
        long endTime = System.currentTimeMillis();

        // Print the results
        System.out.println("Total Paths: " + totalPaths);
        System.out.println("Execution Time: " + (endTime - startTime) + "ms");
    }

    /**
     * Recursive function to explore all valid paths using backtracking.
     *
     * @param x     Current row position
     * @param y     Current column position
     * @param step  Current step in the input path
     * @param paths Input string of directions and wildcards
     */
    static void explore(int x, int y, int step, String paths) {
        // Base case: If all steps are completed and we are at the bottom-left corner
        if (step == (GRID_SIZE * GRID_SIZE) - 1) {
            System.out.println("Step: " + step + ", Position: (" + x + "," + y + ")");
            if (x == GRID_SIZE - 1 && y == 0) {
                totalPaths++;
                if (totalPaths % 100 == 0) {
                    System.out.println("Update ------- Paths so far: " + totalPaths);
                }
            }
            return;
        }

        // Mark the current cell as visited
        visited[x][y] = true;

        // Get the current direction from the input string
        char direction = paths.charAt(step);

        // Explore all valid moves based on the current direction
        for (char move : getDirection(direction)) {
            int movedX = x, movedY = y;

            switch (move) {
                case 'D': movedX++; break;
                case 'U': movedX--; break;
                case 'L': movedY--; break;
                case 'R': movedY++; break;
            }

            // Check if the move is valid
            //if (isValid(movedX, movedY)) {
           // System.out.println("--------------------------------------------------------");
           // System.out.println(move);
           // System.out.println("Avoid dead end?: " + canAvoidDeadEnd(movedX, movedY, step) + " --- Is valid?: " + isValid(movedX, movedY) + " --- Can reach till end?: " + canReachTillEnd(movedX, movedY));
           // System.out.println("Step: " + step + ", Position: (" + x + "," + y + ")");  
            if (canAvoidDeadEnd(movedX, movedY, step) && isValid(movedX, movedY) && canReachTillEnd(movedX, movedY)) {
                explore(movedX, movedY, step + 1, paths); // Recursive call
               // }
            }
        }

        // Backtrack: Mark the current cell as unvisited for other paths
        visited[x][y] = false;
    }

    /**
     * Returns possible directions for the current step.
     *
     * @param direction Character from the input path ('D', 'U', 'L', 'R', or '*')
     * @return Array of valid directions to try
     */
    static char[] getDirection(char direction) {
        if (direction == '*') {
            // If it's a wildcard, explore all directions
            return new char[] {'D', 'U', 'L', 'R'};
        }
        return new char[] { direction }; // Single specific direction
    }

    /**
     * Checks if the given position is within bounds and not yet visited.
     *
     * @param x Row index
     * @param y Column index
     * @return True if the position is valid and not visited, false otherwise
     */
    static boolean isValid(int x, int y) {
        // Check if the position is within bounds and not already visited
        return x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE && !visited[x][y];
    }


    /**
     * Pruning: Checks if it's possible to visit all unvisited cells with the remaining moves.
     *
     * @param x     Current row position
     * @param y     Current column position
     * @param steps Current step in the path
     * @return True if there are still valid neighbors and we can potentially visit all remaining cells
     */
    static boolean canAvoidDeadEnd(int x, int y, int step) {
        // Check how many free neighbors are available
        int freeNeighbors = 0;
        for (char move : new char[] {'D', 'U', 'L', 'R'}) {
            int newX = x + dx(move);
            int newY = y + dy(move);
            
            //dont check for last step, otherwise it is always false at the end
            if(step == GRID_SIZE * GRID_SIZE - 2) {
                return true;
            }

            // Check if the neighbor is valid (within bounds and unvisited)
            if (isValid(newX, newY)) {
                freeNeighbors++;
            }
        }
       //System.out.println("freeNeighbors: " + freeNeighbors);

        // Prune if there are no free neighbors or not enough moves left to visit all unvisited cells
        return freeNeighbors > 0;
    }

    /**
     * Helper function to get the change in x direction for a given move.
     * 
     * @param move Direction character ('D', 'U', 'L', 'R')
     * @return Change in x direction
     */
    static int dx(char move) {
        return move == 'D' ? 1 : move == 'U' ? -1 : 0;
    }

    /**
     * Helper function to get the change in y direction for a given move.
     * 
     * @param move Direction character ('D', 'U', 'L', 'R')
     * @return Change in y direction
     */
    static int dy(char move) {
        return move == 'R' ? 1 : move == 'L' ? -1 : 0;
    }

    static boolean canReachTillEnd(int x, int y) {
        boolean[][] tempVisited = new boolean[GRID_SIZE][GRID_SIZE];
    
        // Copy the current visited state to a temporary array
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                tempVisited[i][j] = visited[i][j];
            }
        }
    
        // Perform a BFS/DFS from the current cell
        int reachableCells = floodFillCount(x, y, tempVisited);
    
        // Check if reachable cells are enough to cover remaining steps
        int remainingSteps = GRID_SIZE * GRID_SIZE - countVisitedCells();
        return reachableCells >= remainingSteps;
    }
    
    // Helper function for flood-fill to count reachable cells
    static int floodFillCount(int x, int y, boolean[][] tempVisited) {
        if (x < 0 || x >= GRID_SIZE || y < 0 || y >= GRID_SIZE || tempVisited[x][y]) {
            return 0;
        }
    
        tempVisited[x][y] = true; // Mark as visited
        int count = 1;
    
        // Explore all directions
        for (char move : new char[] {'D', 'U', 'L', 'R'}) {
            int newX = x + dx(move);
            int newY = y + dy(move);
            count += floodFillCount(newX, newY, tempVisited);
        }
    
        return count;
    }
    
    // Count total visited cells
    static int countVisitedCells() {
        int count = 0;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (visited[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }
    
}
