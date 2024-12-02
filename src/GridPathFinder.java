// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.util.Scanner;

public class GridPathFinder {

        private static final int GRID_SIZE = 8;
        private static final int TOTAL_MOVES = 63;
        private static final int[][] DIRECTIONS = {
                {1, 0},  // Down
                {-1, 0}, // Up
                {0, -1}, // Left
                {0, 1}   // Right
        };
        private static char[] inputPath;
        private static boolean[][] visited;
        private static int totalPaths = 0;

        public static void main(String[] args) { // 12 mins
            String input = "*****DR******R******R********************R*D************L******";

            if (!isValidInput(input)) {
                System.out.println("Invalid input! Ensure it has 63 characters and contains only 'U', 'D', 'L', 'R', or '*'.");
                return;
            }

            inputPath = input.toCharArray();
            visited = new boolean[GRID_SIZE][GRID_SIZE];
            long startTime = System.currentTimeMillis();

            visited[0][0] = true; // Start position
            findPaths(0, 0, 0);   // Start DFS

            long endTime = System.currentTimeMillis();

            System.out.println("Total paths: " + totalPaths);
            System.out.println("Time (ms): " + (endTime - startTime));
        }

        private static boolean isValidInput(String input) {
            return input.length() == TOTAL_MOVES && input.matches("[UDLR*]+");
        }

        private static void findPaths(int row, int col, int step) {
            if (step == TOTAL_MOVES) {
                if (row == GRID_SIZE - 1 && col == 0) { // Reached end cell
                    totalPaths++;
                }
                return;
            }

            char direction = inputPath[step];
            if (direction == '*') {
                // Try all possible directions
                for (int[] dir : DIRECTIONS) {
                    int newRow = row + dir[0];
                    int newCol = col + dir[1];
                    if (isValidMove(newRow, newCol)) {
                        visited[newRow][newCol] = true;
                        findPaths(newRow, newCol, step + 1);
                        visited[newRow][newCol] = false; // Backtrack
                    }
                }
            } else {
                // Move in the specific direction
                int[] dir = getDirection(direction);
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                if (isValidMove(newRow, newCol)) {
                    visited[newRow][newCol] = true;
                    findPaths(newRow, newCol, step + 1);
                    visited[newRow][newCol] = false; // Backtrack
                }
            }
        }

        private static boolean isValidMove(int row, int col) {
            return row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE && !visited[row][col];
        }

        private static int[] getDirection(char direction) {
            switch (direction) {
                case 'D': return DIRECTIONS[0];
                case 'U': return DIRECTIONS[1];
                case 'L': return DIRECTIONS[2];
                case 'R': return DIRECTIONS[3];
                default: throw new IllegalArgumentException("Invalid direction: " + direction);
            }
        }
    }
