import java.util.*;

public class AStarGridPath {
    private static final int GRID_SIZE = 8; // Adjust grid size for testing
    private static final int NUM_MOVES = 63;

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
        int totalPaths = findPathsAStar(input);
        long endTime = System.currentTimeMillis();

        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    private static int findPathsAStar(String input) {
        int totalPaths = 0;

        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.cost));
        pq.add(new State(0, 0, 0, 1, 0)); // Start state

        while (!pq.isEmpty()) {
            State current = pq.poll();

            // If reached the destination with all moves used
            if (current.row == GRID_SIZE - 1 && current.col == 0 && current.moveIndex == NUM_MOVES) {
                totalPaths++;
                continue;
            }

            // If moves are exhausted
            if (current.moveIndex >= NUM_MOVES) {
                continue;
            }

            char direction = input.charAt(current.moveIndex);
            if (direction == '*') {
                // Explore all possible directions
                for (char d : new char[]{'D', 'U', 'L', 'R'}) {
                    processMove(current, d, pq);
                }
            } else {
                // Explore the specified direction
                processMove(current, direction, pq);
            }
        }

        return totalPaths;
    }

    private static void processMove(State current, char direction, PriorityQueue<State> pq) {
        int newRow = current.row;
        int newCol = current.col;

        switch (direction) {
            case 'D':
                newRow++;
                break;
            case 'U':
                newRow--;
                break;
            case 'L':
                newCol--;
                break;
            case 'R':
                newCol++;
                break;
        }

        // Check if the move is valid
        if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE) {
            int newCellBit = 1 << (newRow * GRID_SIZE + newCol);
            if ((current.visited & newCellBit) == 0) { // Cell not visited
                int newVisited = current.visited | newCellBit;
                int g = current.moveIndex + 1; // Cost so far
                int h = Math.abs(newRow - (GRID_SIZE - 1)) + Math.abs(newCol - 0); // Manhattan distance
                pq.add(new State(newRow, newCol, g, newVisited, g + h));
            }
        }
    }

    private static class State {
        int row, col, moveIndex, visited, cost;

        State(int row, int col, int moveIndex, int visited, int cost) {
            this.row = row;
            this.col = col;
            this.moveIndex = moveIndex;
            this.visited = visited;
            this.cost = cost;
        }
    }
}

