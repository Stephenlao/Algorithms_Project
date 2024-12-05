import java.util.Arrays;

public class ForestExplorer {
    private static final int GRID_SIZE = 8;
    private static final int TOTAL_CELLS = GRID_SIZE * GRID_SIZE;
    private static final int[] DX = {1, -1, 0, 0};  // Down, Up, Right, Left
    private static final int[] DY = {0, 0, 1, -1};
    private static final char[] DIRECTIONS = {'D', 'U', 'R', 'L'};

    private static int totalPaths = 0;

    public static void main(String[] args) {
        String path = "*****DR******R******R********************R*D************L******"; // 12 mins // Input path
        if (path.length() != 63 || !validatePath(path)) {
            System.out.println("Invalid input.");
            return;
        }

        long startTime = System.currentTimeMillis();
        boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
        visited[0][0] = true;  // Start at the top-left corner

        explorePath(0, 0, 0, path.toCharArray(), visited);

        long endTime = System.currentTimeMillis();

        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    private static boolean validatePath(String path) {
        for (char c : path.toCharArray()) {
            if (c != 'U' && c != 'D' && c != 'L' && c != 'R' && c != '*') {
                return false;
            }
        }
        return true;
    }

    private static void explorePath(int x, int y, int step, char[] path, boolean[][] visited) {
        if (step == path.length) {
            if (x == 7 && y == 0) {
                totalPaths++;  // Found a valid path
            }
            return;
        }

        char direction = path[step];

        if (direction == '*') {
            // Try all four possible directions
            for (int i = 0; i < 4; i++) {
                int nx = x + DX[i];
                int ny = y + DY[i];
                if (isValidMove(nx, ny, visited)) {
                    visited[nx][ny] = true;
                    explorePath(nx, ny, step + 1, path, visited);
                    visited[nx][ny] = false;  // Backtrack
                }
            }
        } else {
            // Move in the given direction
            int dirIndex = getDirectionIndex(direction);
            int nx = x + DX[dirIndex];
            int ny = y + DY[dirIndex];
            if (isValidMove(nx, ny, visited)) {
                visited[nx][ny] = true;
                explorePath(nx, ny, step + 1, path, visited);
                visited[nx][ny] = false;  // Backtrack
            }
        }
    }

    private static int getDirectionIndex(char direction) {
        for (int i = 0; i < DIRECTIONS.length; i++) {
            if (DIRECTIONS[i] == direction) {
                return i;
            }
        }
        throw new IllegalArgumentException("Invalid direction: " + direction);
    }

    private static boolean isValidMove(int x, int y, boolean[][] visited) {
        return x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE && !visited[x][y];
    }
}