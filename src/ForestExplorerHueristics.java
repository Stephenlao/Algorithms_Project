//import java.util.PriorityQueue;
//import java.util.HashSet;
//import java.util.Set;
//
//public class ForestExplorer2 {
//
//    private static final int GRID_SIZE = 8;
//    private static long pathCount = 0;
//
//    public static void main(String[] args) {
//        String input = "*****DR******R******R********************R*D************L******"; // 63 characters
//        if (args.length > 0) {
//            input = args[0];
//        }
//
//        // Validate input
//        if (!isValidInput(input)) {
//            System.out.println("Invalid input. Input must be a string of 63 characters containing only 'U', 'D', 'L', 'R', or '*'.");
//            return;
//        }
//
//        long startTime = System.currentTimeMillis();
//
//        explorePaths(input);
//
//        long endTime = System.currentTimeMillis();
//
//        System.out.println("Total paths: " + pathCount);
//        System.out.println("Time (ms): " + (endTime - startTime));
//    }
//
//    private static boolean isValidInput(String input) {
//        if (input.length() != 63) {
//            return false;
//        }
//        for (char c : input.toCharArray()) {
//            if (c != 'U' && c != 'D' && c != 'L' && c != 'R' && c != '*') {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private static void explorePaths(String path) {
//        PriorityQueue<Node> queue = new PriorityQueue<>();
//        Set<String> visitedStates = new HashSet<>();
//        queue.add(new Node(0, 0, 0, heuristic(0, 0)));
//
//        while (!queue.isEmpty()) {
//            Node current = queue.poll();
//            int row = current.row;
//            int col = current.col;
//            int step = current.step;
//
//            // Ensure we don't access out-of-bounds indices
//            if (step >= path.length()) {
//                continue;
//            }
//
//            // Check for goal condition
//            if (row == GRID_SIZE - 1 && col == 0 && step == path.length() - 1) {
//                pathCount++;
//                continue;
//            }
//
//            // Generate a unique key for the current state
//            String stateKey = row + "," + col + "," + step;
//
//            if (visitedStates.contains(stateKey)) {
//                continue;
//            }
//            visitedStates.add(stateKey);
//
//            char direction = path.charAt(step);
//
//            // Explore valid directions
//            if ((direction == 'U' || direction == '*') && isValidMove(row - 1, col, visitedStates, step + 1)) {
//                queue.add(new Node(row - 1, col, step + 1, heuristic(row - 1, col)));
//            }
//            if ((direction == 'D' || direction == '*') && isValidMove(row + 1, col, visitedStates, step + 1)) {
//                queue.add(new Node(row + 1, col, step + 1, heuristic(row + 1, col)));
//            }
//            if ((direction == 'L' || direction == '*') && isValidMove(row, col - 1, visitedStates, step + 1)) {
//                queue.add(new Node(row, col - 1, step + 1, heuristic(row, col - 1)));
//            }
//            if ((direction == 'R' || direction == '*') && isValidMove(row, col + 1, visitedStates, step + 1)) {
//                queue.add(new Node(row, col + 1, step + 1, heuristic(row, col + 1)));
//            }
//        }
//    }
//
//    private static int heuristic(int row, int col) {
//        // Manhattan distance to the target cell (GRID_SIZE - 1, 0)
//        return Math.abs(row - (GRID_SIZE - 1)) + Math.abs(col - 0);
//    }
//
//    private static boolean isValidMove(int row, int col, Set<String> visitedStates, int nextStep) {
//        return row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE && !visitedStates.contains(row + "," + col + "," + nextStep);
//    }
//
//    // Node class for the priority queue
//    static class Node implements Comparable<Node> {
//        int row, col, step, priority;
//
//        Node(int row, int col, int step, int priority) {
//            this.row = row;
//            this.col = col;
//            this.step = step;
//            this.priority = priority;
//        }
//
//        @Override
//        public int compareTo(Node other) {
//            return Integer.compare(this.priority, other.priority);
//        }
//    }
//}
