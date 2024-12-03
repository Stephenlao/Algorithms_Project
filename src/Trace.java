import java.util.*;

public class Trace {
    private static final int GRID_SIZE = 8;
    private static final int TARGET_ROW = GRID_SIZE - 1;
    private static final int TARGET_COL = 0;

    public static void main(String[] args) {
        String input = "*****DR******R******R********************R*D************L******";
        if (input.length() != 63) {
            System.out.println("Invalid input length");
            return;
        }

        long startTime = System.currentTimeMillis();
        List<String> path = findPathWithTrace(input);
        long endTime = System.currentTimeMillis();

        if (path != null) {
            System.out.println("Path found: " + path);
        } else {
            System.out.println("No path found");
        }

        System.out.println("Time (ms): " + (endTime - startTime));
    }

    private static List<String> findPathWithTrace(String input) {
        Map<String, String> cameFrom = new HashMap<>();  // Tracks the previous state of each node
        Map<String, Integer> costSoFar = new HashMap<>(); // Tracks the cost to reach each node
        Queue<State> openList = new PriorityQueue<>(Comparator.comparingInt(state -> state.cost));
        openList.add(new State(0, 0, 0, 0)); // Start at (0, 0) with 0 cost

        while (!openList.isEmpty()) {
            State currentState = openList.poll();

            // If we've reached the target, reconstruct the path
            if (currentState.row == TARGET_ROW && currentState.col == TARGET_COL) {
                return reconstructPath(cameFrom, currentState); // Trace back from the goal to start
            }

            String currentKey = currentState.row + "," + currentState.col;
            if (cameFrom.containsKey(currentKey) && costSoFar.get(currentKey) <= currentState.cost) {
                continue;
            }
            cameFrom.put(currentKey, currentState.row + "," + currentState.col);
            costSoFar.put(currentKey, currentState.cost);

            char direction = input.charAt(currentState.cost);
            if (direction == '*') {
                // Jump to possible jump points
                for (char d : new char[] {'D', 'U', 'L', 'R'}) {
                    exploreJump(currentState, d, openList, cameFrom, costSoFar);
                }
            } else {
                exploreJump(currentState, direction, openList, cameFrom, costSoFar);
            }
        }

        return null; // No valid path found
    }

    private static List<String> reconstructPath(Map<String, String> cameFrom, State goal) {
        List<String> path = new ArrayList<>();
        String current = goal.row + "," + goal.col;

        while (cameFrom.containsKey(current)) {
            String previous = cameFrom.get(current);
            path.add(current);
            current = previous;
        }

        Collections.reverse(path); // Reverse the path to go from start to goal
        return path;
    }

    private static void exploreJump(State currentState, char direction, Queue<State> openList,
                                    Map<String, String> cameFrom, Map<String, Integer> costSoFar) {
        int newRow = currentState.row, newCol = currentState.col;

        // Move in the specified direction and check for jump points
        while (true) {
            switch (direction) {
                case 'D': newRow++; break;
                case 'U': newRow--; break;
                case 'L': newCol--; break;
                case 'R': newCol++; break;
            }

            // Check if out of bounds
            if (newRow < 0 || newRow >= GRID_SIZE || newCol < 0 || newCol >= GRID_SIZE) {
                break;
            }

            // If we have reached a jump point, add it to the open list
            if (isJumpPoint(newRow, newCol, currentState.row, currentState.col)) {
                openList.add(new State(newRow, newCol, currentState.cost + 1, currentState.visited | (1 << (newRow * GRID_SIZE + newCol))));
            }
        }
    }

    private static boolean isJumpPoint(int newRow, int newCol, int prevRow, int prevCol) {
        // A simple check: we consider a jump point if there's a change in direction or an obstacle
        return (newRow != prevRow || newCol != prevCol); // More complex conditions can be added
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

