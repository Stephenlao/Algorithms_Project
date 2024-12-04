//import java.util.*;
//public class DijkstraGridPath {
//
//    private static final int GRID_SIZE = 8;
//    private static final int MOVES = 63;
//    private static final int[][] DIRECTIONS = {
//            {1, 0}, {0, 1}, {-1, 0}, {0, -1}, // D, U, L, R (old)
//    };
//
//    public static void main(String[] args) {
//        String input = "*****DR******R******R********************R*D************L******";
//
//        if (input.length() != MOVES) {
//            System.out.println("Invalid input length.");
//            return;
//        }
//
//        for (char c : input.toCharArray()) {
//            if (c != 'U' && c != 'D' && c != 'L' && c != 'R' && c != '*') {
//                System.out.println("Invalid character in input.");
//                return;
//            }
//        }
//
//        long startTime = System.currentTimeMillis();
//        int totalPaths = countPaths(input);
//        long endTime = System.currentTimeMillis();
//
//        System.out.println("Total paths: " + totalPaths);
//        System.out.println("Time (ms): " + (endTime - startTime));
//    }
//
//    private static int countPaths(String input) {
//        int[][] grid = new int[GRID_SIZE][GRID_SIZE];
//        boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
//        int[] currentPosition = {0, 0};
//        visited[currentPosition[0]][currentPosition[1]] = true;
//
//        return backtrack(grid, visited, currentPosition, input, 0);
//    }
//
//    private static int backtrack(int[][] grid, boolean[][] visited, int[] currentPosition, String input, int index) {
//        if (index == input.length()) {
//            return 1;
//        }
//
//        int count = 0;
//        int x = currentPosition[0];
//        int y = currentPosition[1];
//
//        if (input.charAt(index) == '*') {
//            for (int[] direction : DIRECTIONS) {
//                int newX = x + direction[0];
//                int newY = y + direction[1];
//
//                if (isValidMove(newX, newY, visited)) {
//                    visited[newX][newY] = true;
//                    currentPosition[0] = newX;
//                    currentPosition[1] = newY;
//                    count += backtrack(grid, visited, currentPosition, input, index + 1);
//                    visited[newX][newY] = false;
//                    currentPosition[0] = x;
//                    currentPosition[1] = y;
//                }
//            }
//        } else {
//            int newX = x + DIRECTIONS[directionIndex(input.charAt(index))][0];
//            int newY = y + DIRECTIONS[directionIndex(input.charAt(index))][1];
//
//            if (isValidMove(newX, newY, visited)) {
//                visited[newX][newY] = true;
//                currentPosition[0] = newX;
//                currentPosition[1] = newY;
//                count += backtrack(grid, visited, currentPosition, input, index + 1);
//                visited[newX][newY] = false;
//                currentPosition[0] = x;
//                currentPosition[1] = y;
//            }
//        }
//
//        return count;
//    }
//
//    private static int directionIndex(char c) {
//        switch (c) {
//            case 'D': return 0;
//            case 'U': return 1;
//            case 'L': return 2;
//            case 'R': return 3;
//            default: return -1;
//        }
//    }
//
//    private static boolean isValidMove(int x, int y, boolean[][] visited) {
//        return x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE && !visited[x][y];
//    }
//}








import java.util.*;

public class DijkstraGridPath {

    private static final int GRID_SIZE = 8;
    private static final int MOVES = 63;
    private static final int[][] DIRECTIONS = {
            {1, 0}, {0, 1}, {-1, 0}, {0, -1} // D, U, L, R
    };

    public static void main(String[] args) {
        String input = "*****DR******R******R********************R*D************L******";

        if (input.length() != MOVES) {
            System.out.println("Invalid input length.");
            return;
        }

        for (char c : input.toCharArray()) {
            if (c != 'U' && c != 'D' && c != 'L' && c != 'R' && c != '*') {
                System.out.println("Invalid character in input.");
                return;
            }
        }

        long startTime = System.currentTimeMillis();
        int totalPaths = countPaths(input);
        long endTime = System.currentTimeMillis();

        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    private static int countPaths(String input) {
        boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
        visited[0][0] = true;

        return backtrack(visited, 0, 0, input, 0);
    }

    private static int backtrack(boolean[][] visited, int x, int y, String input, int index) {
        // Base case: Path ends successfully only at (7, 0) with 63 steps
        if (index == 63) {
            return (x == GRID_SIZE - 1 && y == 0) ? 1 : 0;
        }

        // Prune paths if no valid moves exist
//        if (!hasValidMoves(x, y, visited)) {
//            return 0;
//        }


        int count = 0;

        if (input.charAt(index) == '*') {
            for (int[] direction : DIRECTIONS) {
                int newX = x + direction[0];
                int newY = y + direction[1];

                if (isValidMove(newX, newY, visited)) {
                    visited[newX][newY] = true;
                    count += backtrack(visited, newX, newY, input, index + 1);
                    visited[newX][newY] = false; // Backtrack
                }
            }
        } else {
            int[] direction = DIRECTIONS[directionIndex(input.charAt(index))];
            int newX = x + direction[0];
            int newY = y + direction[1];

            if (isValidMove(newX, newY, visited)) {
                visited[newX][newY] = true;
                count += backtrack(visited, newX, newY, input, index + 1);
                visited[newX][newY] = false; // Backtrack
            }
        }

        return count;
    }


    private static boolean isValidMove(int x, int y, boolean[][] visited) {
        return x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE && !visited[x][y];
    }

    private static boolean hasValidMoves(int x, int y, boolean[][] visited) {
        for (int[] direction : DIRECTIONS) {
            int newX = x + direction[0];
            int newY = y + direction[1];
            if (newX >= 0 && newX < GRID_SIZE && newY >= 0 && newY < GRID_SIZE && !visited[newX][newY]) {
                return true;
            }
        }
        return false; // No valid moves, prune the path
    }

        private static int directionIndex(char c) {
        switch (c) {
            case 'D': return 0;
            case 'U': return 1;
            case 'L': return 2;
            case 'R': return 3;
            default: return -1;
        }
    }
}


