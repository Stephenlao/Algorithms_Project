import java.util.*;

public class DijkstraGridPath {
    private static final int GRID_SIZE = 8; // Adjust grid size for testing
    private static final int NUM_MOVES = 63;
    private static final int TARGET_ROW = GRID_SIZE - 1;
    private static final int TARGET_COL = 0;

    public static void main(String[] args) {
        String input = "*****DR******R******R********************R*D************L******";

        if (input.length() != NUM_MOVES) {
            System.out.println("Invalid input length");
            return;
        }

        for (char c : input.toCharArray()) {
            if (c != 'D' && c != 'U' && c != 'L' && c != 'R' && c != '*') {
                System.out.println("Invalid input character");
                return;
            }
        }

        long startTime = System.currentTimeMillis();
        int totalPaths = findPathsDijkstra(input);
        long endTime = System.currentTimeMillis();

        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    private static int findPathsDijkstra(String input) {
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(state -> state.cost));
        Map<String, Integer> visited = new HashMap<>();

        // Start from (0, 0)
        pq.add(new State(0, 0, 0, 1)); // Starting point with cost 0

        while (!pq.isEmpty()) {
            State currentState = pq.poll();

            // If we've reached the destination, return the cost
            if (currentState.row == TARGET_ROW && currentState.col == TARGET_COL) {
                return currentState.cost;
            }

            // If this state has been visited, skip it
            String key = currentState.row + "," + currentState.col;
            if (visited.containsKey(key) && visited.get(key) <= currentState.cost) {
                continue;
            }
            visited.put(key, currentState.cost);

            // Explore all possible moves
            char direction = input.charAt(currentState.cost);
            if (direction == '*') {
                for (char d : new char[] {'D', 'U', 'L', 'R'}) {
                    exploreMove(currentState, d, pq);
                }
            } else {
                exploreMove(currentState, direction, pq);
            }
        }

        return 0; // No valid path
    }

    private static void exploreMove(State currentState, char direction, PriorityQueue<State> pq) {
        int newRow = currentState.row, newCol = currentState.col;

        switch (direction) {
            case 'D': newRow++; break;
            case 'U': newRow--; break;
            case 'L': newCol--; break;
            case 'R': newCol++; break;
        }

        // Check if the new move is within bounds
        if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE) {
            int newCost = currentState.cost + 1;
            pq.add(new State(newRow, newCol, newCost, currentState.visited | (1 << (newRow * GRID_SIZE + newCol))));
        }
    }

    private static class State {
        int row;
        int col;
        int cost;
        int visited; // Bitmask to represent visited cells

        State(int row, int col, int cost, int visited) {
            this.row = row;
            this.col = col;
            this.cost = cost;
            this.visited = visited;
        }
    }
}
