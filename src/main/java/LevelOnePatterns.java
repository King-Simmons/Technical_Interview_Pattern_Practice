import java.util.ArrayDeque;
import java.util.ArrayList;

public class LevelOnePatterns {
    public static void main(String[] args) {
        System.out.println("""
                Level One Patterns:\s
                - BFS\s
                - BFS-MultiSource + levels\s
                - DFS\s
                - Backtracking\s
                - Two Pointers\s
                - Prefix Sum\s
                - Sliding Window\s
                - Sorting with Tie Breaker\s
                - Priority Queue\s
                - HashMap/Set\s
                - Stack\s
                - Heap/Priority Queue\s
                - Binary Search\s
                - 1-DP\s
                - 2-DP/Matrix DP\s
                
                """);



        System.out.println("Compiled!!!");
    }

    //BFS directional
    private static void bfs(int sr, int sc, int[][] grid, int target){
        int[][] dirs = {{0,1}, {1,0}, {-1,0}, {0,-1}};
        ArrayDeque<int[]> q = new ArrayDeque<>();
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];

        q.add(new int[]{sr, sc}); //add to q
        visited[sr][sc] = true; //add to visited

        while(!q.isEmpty()){ //loop through q
            int[] curr = q.poll();//get from q
            int r = curr[0], c = curr[1]; //get coords

            for(int[] d : dirs){ //loop all through possible directions
                int newR = r + d[0], newC = c + d[1]; //get curr directions
                if(newR >= 0 && newC >= 0 && newR < m && newC < n && !visited[newR][newC] && grid[newR][newC] == target){ //if curr direction is possible
                    q.add(new int[]{newR, newC}); //add to q
                    visited[newR][newC] = true; //mark visited
                }
            }
        }
    }

    //BFS multiSoure directional + levels
    private static void bfs(int[][] grid, int target, int start){
        int[][] dirs = {{0,1}, {1, 0}, {-1,0}, {0, -1}};
        int m = grid.length, n = grid[0].length;
        ArrayDeque<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[m][n];
        int level = 0;

        for(int r = 0; r < m; r++){
            for(int c = 0; c < n; c++){
                if(grid[r][c] == start){
                    q.add(new int[]{r,c}); //add to q
                    visited[r][c] = true; //mark visited
                }
            }
        }

        while(!q.isEmpty()){ //loop through q
            int size = q.size(); //get q size
            for(int i = 0; i < size; i++){ //loop through only objects on current level
                int[] curr = q.poll(); //get from q
                int r = curr[0], c =curr[1]; //get current coord
                for(int[] d : dirs){ //loop through all possible directions
                    int newR = r + d[0], newC = c + d[1]; //get current direction
                    if(newR >= 0 && newC >= 0 && newR < m && newC < n && !visited[newR][newC] && grid[newR][newC] == target){ //is current direction possible?
                        q.add(new int[]{newR, newC}); //add to q
                        visited[newR][newC] = true; //mark visited
                    }
                }
            }
            level++; //increment level
        }
    }

    //dfs
    private static void dfs(int r, int c, int m, int n, int[][] grid, boolean[][] visited, int target){
        if(r < 0 || c < 0 || r >= m || c >= n || visited[r][c] || grid[r][c] != target) return; //base case (if no longer valid)
        visited[r][c] = true;// mark visited

        int[][] dirs = {{1,0}, {0,1}, {-1, 0}, {0, -1}};
        for(int[] d : dirs){ //loop through possible directions
            dfs(r + d[0], c + d[1], m, n, grid, visited, target); // recusively call dfs
        }
    }


    //backtracking
    private static void backtrack(ArrayList<ArrayList<Integer>> res, ArrayList<Integer> curr, int[] nums, boolean[] used){
        if(curr.size() == nums.length) {
            res.add(new ArrayList<>(curr));
        }
        for(int i = 0; i < nums.length; i++){
            if(used[i]) continue;
            used[i] = true;
            curr.add(nums[i]);
            backtrack(res, curr, nums, used);
            curr.remove(curr.size() - 1);
            used[i] = false;
        }
    }



    //two pointers
    private static void twoPointers(int[] arr){
        int left = 0, right = arr.length -1;
        while(left < right){
            if(arr[left] < arr[right]){
                left++;
            }else{
                right--;
            }
        }
    }
}
