import java.util.*;

public class IDAStarGridPath {
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
        int totalPaths = findPathsIDAStar(input);
        long endTime = System.currentTimeMillis();

        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    private static int findPathsIDAStar(String input) {
        int threshold = heuristic(0, 0);
        while (true) {
            Result result = depthLimitedSearch(0, 0, 0, 1, threshold, input);
            if (result.found) {
                return result.paths;
            }
            if (result.nextThreshold == Integer.MAX_VALUE) {
                return 0; // No solution
            }
            threshold = result.nextThreshold;
        }
    }

    private static Result depthLimitedSearch(int row, int col, int moveIndex, int visited, int threshold, String input) {
        int f = moveIndex + heuristic(row, col); // g(n) + h(n)

        if (f > threshold) {
            return new Result(false, 0, f);
        }

        // Goal state reached
        if (row == TARGET_ROW && col == TARGET_COL && moveIndex == NUM_MOVES) {
            return new Result(true, 1, threshold);
        }

        if (moveIndex >= NUM_MOVES) {
            return new Result(false, 0, Integer.MAX_VALUE);
        }

        int nextThreshold = Integer.MAX_VALUE;
        int totalPaths = 0;

        char direction = input.charAt(moveIndex);
        if (direction == '*') {
            for (char d : new char[] {'D', 'U', 'L', 'R'}) {
                MoveResult move = move(row, col, d, visited);
                if (move.valid) {
                    Result res = depthLimitedSearch(move.row, move.col, moveIndex + 1, move.visited, threshold, input);
                    if (res.found) {
                        totalPaths += res.paths;
                    }
                    nextThreshold = Math.min(nextThreshold, res.nextThreshold);
                }
            }
        } else {
            MoveResult move = move(row, col, direction, visited);
            if (move.valid) {
                Result res = depthLimitedSearch(move.row, move.col, moveIndex + 1, move.visited, threshold, input);
                if (res.found) {
                    totalPaths += res.paths;
                }
                nextThreshold = Math.min(nextThreshold, res.nextThreshold);
            }
        }

        return new Result(false, totalPaths, nextThreshold);
    }

    private static MoveResult move(int row, int col, char direction, int visited) {
        int newRow = row, newCol = col;

        switch (direction) {
            case 'D': newRow++; break;
            case 'U': newRow--; break;
            case 'L': newCol--; break;
            case 'R': newCol++; break;
        }

        if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE) {
            int newCellBit = 1 << (newRow * GRID_SIZE + newCol);
            if ((visited & newCellBit) == 0) { // Cell not visited
                return new MoveResult(true, newRow, newCol, visited | newCellBit);
            }
        }
        return new MoveResult(false, row, col, visited);
    }

    private static int heuristic(int row, int col) {
        return Math.abs(TARGET_ROW - row) + Math.abs(TARGET_COL - col); // Manhattan distance
    }

    private static class Result {
        boolean found;
        int paths;
        int nextThreshold;

        Result(boolean found, int paths, int nextThreshold) {
            this.found = found;
            this.paths = paths;
            this.nextThreshold = nextThreshold;
        }
    }

    private static class MoveResult {
        boolean valid;
        int row, col, visited;

        MoveResult(boolean valid, int row, int col, int visited) {
            this.valid = valid;
            this.row = row;
            this.col = col;
            this.visited = visited;
        }
    }
}

