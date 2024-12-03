import java.util.*;
//
//public class BFSGridPath {
//    private static final int GRID_SIZE = 8;
//    private static final int NUM_MOVES = 63;
//
//    public static void main(String[] args) {
//        String input = "*****DR******R******R********************R*D************L******";
//
//        if (input.length() != NUM_MOVES) {
//            System.out.println("Invalid input length");
//            return;
//        }
//
//        for (char c : input.toCharArray()) {
//            if (c != 'D' && c != 'U' && c != 'L' && c != 'R' && c != '*') {
//                System.out.println("Invalid input character");
//                return;
//            }
//        }
//
//        long startTime = System.currentTimeMillis();
//        int totalPaths = explorePaths(input);
//        long endTime = System.currentTimeMillis();
//
//        System.out.println("Total paths: " + totalPaths);
//        System.out.println("Time (ms): " + (endTime - startTime));
//    }
//
//    private static int explorePaths(String input) {
//        int totalPaths = 0;
//        Queue<ForestState> queue = new LinkedList<>();
//
//        // Initialize the start state
//        ForestState startState = new ForestState(0, 0, 0, new boolean[GRID_SIZE][GRID_SIZE]);
//        startState.visited[0][0] = true;
//        queue.add(startState);
//
//
//
//        while (!queue.isEmpty()) {
//            ForestState currentState = queue.poll();
//            System.out.println("Processing state: (" + currentState.row + ", " + currentState.col + "), moveIndex=" + currentState.moveIndex);
//
//
//            // If we've reached the end cell, increment the total paths
//            if (currentState.row == GRID_SIZE - 1 && currentState.col == 0) {
//                totalPaths++;
//            }
//
//            // Explore all possible next moves
//            for (int i = 0; i < input.length(); i++) {
//                char direction = input.charAt(i);
//
//                int newRow = currentState.row;
//                int newCol = currentState.col;
//
//                System.out.println("Wildcard explored: (" + newRow + ", " + newCol + ")");
//
//                switch (direction) {
//                    case 'D':
//                        newRow++;
//                        break;
//                    case 'U':
//                        newRow--;
//                        break;
//                    case 'L':
//                        newCol--;
//                        break;
//                    case 'R':
//                        newCol++;
//                        break;
//                    case '*':
//                        // Try all possible directions
//                        for (char d : new char[] {'D', 'U', 'L', 'R'}) {
//                            int tempRow = newRow;
//                            int tempCol = newCol;
//
//                            switch (d) {
//                                case 'D':
//                                    tempRow++;
//                                    break;
//                                case 'U':
//                                    tempRow--;
//                                    break;
//                                case 'L':
//                                    tempCol--;
//                                    break;
//                                case 'R':
//                                    tempCol++;
//                                    break;
//                            }
//
//                            // Check if the new position is valid
//                            if (tempRow >= 0 && tempRow < GRID_SIZE && tempCol >= 0 && tempCol < GRID_SIZE && !currentState.visited[tempRow][tempCol]) {
//                                ForestState newState = new ForestState(tempRow, tempCol, i + 1, currentState.visited.clone());
//                                newState.visited[tempRow][tempCol] = true;
//                                queue.add(newState);
//                            }
//                        }
//                        break;
//                }
//
//                // Check if the new position is valid
//                if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE && !currentState.visited[newRow][newCol]) {
//                    ForestState newState = new ForestState(newRow, newCol, i + 1, currentState.visited.clone());
//                    newState.visited[newRow][newCol] = true;
//                    queue.add(newState);
//                }
//            }
//        }
//
//        return totalPaths;
//    }
//
//    private static class ForestState {
//        int row;
//        int col;
//        int moveIndex;
//        boolean[][] visited;
//
//        ForestState(int row, int col, int moveIndex, boolean[][] visited) {
//            this.row = row;
//            this.col = col;
//            this.moveIndex = moveIndex;
//            this.visited = visited;
//        }
//    }
//}


import java.util.*;

public class BFSGridPath {
    private static final int GRID_SIZE = 8;
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
        int totalPaths = explorePaths(input);
        long endTime = System.currentTimeMillis();

        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    private static int explorePaths(String input) {
        int totalPaths = 0;
        Queue<State> queue = new LinkedList<>();

        // Initialize the start state
        queue.add(new State(0, 0, 0, new boolean[GRID_SIZE][GRID_SIZE]));
        queue.peek().visited[0][0] = true;

        while (!queue.isEmpty()) {
            State currentState = queue.poll();

            // If we've reached the end cell with all moves used
            if (currentState.row == GRID_SIZE - 1 && currentState.col == GRID_SIZE - 1 &&
                    currentState.moveIndex == NUM_MOVES) {
                totalPaths++;
                continue;
            }

            // If we run out of moves
            if (currentState.moveIndex >= NUM_MOVES) {
                continue;
            }

            char direction = input.charAt(currentState.moveIndex);
            if (direction == '*') {
                // Try all possible directions
                for (char d : new char[]{'D', 'U', 'L', 'R'}) {
                    processMove(currentState, d, queue);
                }
            } else {
                // Process the specific direction
                processMove(currentState, direction, queue);
            }
        }

        return totalPaths;
    }

    private static void processMove(State currentState, char direction, Queue<State> queue) {
        int newRow = currentState.row;
        int newCol = currentState.col;

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
        if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE &&
                !currentState.visited[newRow][newCol]) {

            // Create new state with copied visited array
            boolean[][] newVisited = new boolean[GRID_SIZE][GRID_SIZE];
            for (int i = 0; i < GRID_SIZE; i++) {
                newVisited[i] = Arrays.copyOf(currentState.visited[i], GRID_SIZE);
            }
            newVisited[newRow][newCol] = true;

            queue.add(new State(newRow, newCol, currentState.moveIndex + 1, newVisited));
        }
    }

    private static class State {
        int row;
        int col;
        int moveIndex;
        boolean[][] visited;

        State(int row, int col, int moveIndex, boolean[][] visited) {
            this.row = row;
            this.col = col;
            this.moveIndex = moveIndex;
            this.visited = visited;
        }
    }
}




