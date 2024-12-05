import Hueristics.LinkedListPriorityQueue;

public class ForestExplorerWithHeuristics {

    private static final int GRID_SIZE = 8;
    private static long pathCount = 0;


    static class State implements Comparable<State> {
        int row, col, step;
        int cost; // Total cost = steps so far + heuristic

        State(int row, int col, int step, int cost) {
            this.row = row;
            this.col = col;
            this.step = step;
            this.cost = cost;
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    public static void main(String[] args) {
        String input = "*****DR******R******R********************R*D************L******"; // Example input
        if (args.length > 0) {
            input = args[0];
        }

        // Validate input
        if (!isValidInput(input)) {
            System.out.println("Invalid input. Input must be a string of 63 characters containing only 'U', 'D', 'L', 'R', or '*'.");
            return;
        }

        long startTime = System.currentTimeMillis();

        heuristicSearch(input);

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

    private static void heuristicSearch(String path) {
        boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
        LinkedListPriorityQueue<State> pq = new LinkedListPriorityQueue<>();
        pq.add(new State(0, 0, 0, 0)); // Start at (0, 0) with 0 steps and cost 0

        while (!pq.isEmpty()) {
            State current = pq.poll();

            int row = current.row;
            int col = current.col;
            int step = current.step;

            // Base case: Successfully reached the goal
            if (row == GRID_SIZE - 1 && col == 0 && step == 63) {
                pathCount++;
                continue;
            }

            // Skip invalid or already visited cells
            if (step == 63 || visited[row][col]) {
                continue;
            }

            visited[row][col] = true;

            // Explore all possible moves
            char direction = path.charAt(step);
            if (direction == 'U' || direction == '*') {
                if (row > 0 && !visited[row - 1][col]) {
                    pq.add(new State(row - 1, col, step + 1, step + 1 + heuristic(row - 1, col)));
                }
            }
            if (direction == 'D' || direction == '*') {
                if (row < GRID_SIZE - 1 && !visited[row + 1][col]) {
                    pq.add(new State(row + 1, col, step + 1, step + 1 + heuristic(row + 1, col)));
                }
            }
            if (direction == 'L' || direction == '*') {
                if (col > 0 && !visited[row][col - 1]) {
                    pq.add(new State(row, col - 1, step + 1, step + 1 + heuristic(row, col - 1)));
                }
            }
            if (direction == 'R' || direction == '*') {
                if (col < GRID_SIZE - 1 && !visited[row][col + 1]) {
                    pq.add(new State(row, col + 1, step + 1, step + 1 + heuristic(row, col + 1)));
                }
            }

            // Backtrack: Mark the current cell as unvisited for other paths
            visited[row][col] = false;
        }
    }

    private static int heuristic(int row, int col) {
        // Manhattan Distance to the goal (GRID_SIZE - 1, 0)
        return Math.abs(GRID_SIZE - 1 - row) + Math.abs(0 - col);
    }
}

