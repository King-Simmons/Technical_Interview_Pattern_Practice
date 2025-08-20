import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Map;
import java.util.Deque;
import java.util.ArrayDeque;

public class LevelOnePatterns {
    public static void main(String[] args) {
        System.out.println("""
                Level One Patterns:\s
                - BFS\s
                - BFS-MultiSource + levels\s
                - DFS\s
                - Backtracking\s
                - Two Pointers Opposite + Same\s
                - Prefix Sum\s
                - Sliding Window\s
                - Sorting with Tie Breaker\s
                - Priority Queue
                - HashMap/Set/Max Priority Queue\s
                - Monotonic Stack (increasing and decreasing)\s
                - Binary Search\s
                - 1-DP\s
                - 2-DP/Matrix DP\s
                
                """);



        System.out.println("Compiled!!!");
    }

    //BFS directional
   private static void bfs(int sr, int sc, int[][] grid, int target){
        int[][] dirs = {{0,1}, {1,0}, {-1, 0}, {0, -1}};
        ArrayDeque<int[]> q = new ArrayDeque<>();
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];

        q.add(new int[]{sr, sc});
        visited[sr][sc] = true;

        while(!q.isEmpty()){
            int[] curr = q.poll();
            int r = curr[0], c = curr[1];
            for(int[] d : dirs){
                int nr = r + d[0], nc = c + d[1];
                if(nr >= 0 && nc >= 0 && nr < m && nc < n && !visited[nr][nc] && grid[nr][nc] == target){
                    q.add(new int[]{nr, nc});
                    visited[nr][nc] = true;
                }
            }
        }
   }

    //BFS multiSoure directional + levels
    private static int bfs(int sr, int sc, int target, int start, int[][] grid){
        int[][] dirs = {{0,1}, {1,0}, {-1,0}, {0, -1}};
        int m = grid.length, n = grid[0].length;
        ArrayDeque<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[m][n];
        int levels = 0;

        for(int r = 0; r < m; r++){
            for(int c = 0; c < n; c++){
                if(grid[r][c] == start){
                    q.add(new int[]{r,c});
                    visited[r][c] = true;
                }
            }
        }

        while(!q.isEmpty()){
            int size = q.size();
            for(int i = 0; i < size; i++){
                int[] curr = q.poll();
                int r = curr[0], c = curr[1];
                for(int[] d : dirs){
                    int nr = r + d[0], nc = c + d[1];
                    if(nr >= 0 && nc >= 0 && nr < m && nc < n && !visited[nr][nc] && grid[nr][nc] == target){
                        q.add(new int[] {nr, nc});
                        visited[nr][nc] = true;
                    }
                }
            }
            levels++;
        }

        return levels;
    }

    //dfs
    private static void dfs(int r, int c, int[][] grid, int target, boolean[][] visited){
        if(r < 0 || c <0 || r >= grid.length || c >= grid[0].length || visited[r][c] || grid[r][c] != target){
            return;
        }
        visited[r][c] = true;
        int[][] dirs = {{0,1}, {1, 0}, {-1, 0}, {0, -1}};

        for(int[] d : dirs){
            dfs(r+d[0], c+d[1], grid, target, visited);
        }
    }


    //backtracking
    private static void backtrack(List<List<Integer>> res, List<Integer> curr, int target, int[] possible, boolean[] used){
        if(curr.size() == target){
            res.add(new ArrayList<>(curr));
            return;
        }
        for(int i = 0; i < possible.length; i++){
            if(used[i] == true) continue;
            used[i] = true;
            curr.add(possible[i]);
            backtrack(res, curr, target, possible, used);
            curr.remove(curr.size()-1 );
            used[i] = false;
        }
    }

    //two pointers
    private static void twoPointersBoth(int[] arr){
        int l = 0, r = arr.length -1;
        while(l < r){
            //process
            if(arr[l] == arr[r]){ //condition
                l++;
            }else r--;
        }
        int slow = 0, fast = 0;
        while(fast < arr.length){
            //process
            if(arr[slow] != arr[fast]){ //condition
                slow++;
                arr[slow] = arr[fast];
            }
            fast++;
        }
    }


    //pre-fix sum //trip[] = {capacity, start, end}
    private static boolean prefixSum(int[] arr, int[][] trips, int maxCapacity){
        int[] diff = new int[arr.length + 1];
        for(int[] trip : trips){
            diff[trip[1]] += trip[0];
            diff[trip[2]] += trip[0];
        }
        int curr = 0;
        for(int passenger : diff){
            if(curr > maxCapacity) return false;
        }
        return true;
    }

    //sliding window
    private static void slidingWindow(String s, int k){
        int left = 0;
        HashMap<Character, Integer> freq = new HashMap<>();

        for(int right = 0; right < s.length(); right++){
            char rightChar = s.charAt(right);
            freq.put(rightChar, freq.getOrDefault(rightChar, 0) + 1);

            while(freq.size() > k){
                char leftChar = s.charAt(left);
                freq.put(leftChar, freq.get(left) - 1);
                if(freq.get(leftChar) == 0) freq.remove(leftChar);
            }
        }
    }

    //sorting with TieBreaker
    private static void sortingWithTieBreaker(int[][] arr){
        Arrays.sort(arr, (a,b) -> {
            int cmp = Integer.compare(a[0],b[0]);
            if(cmp != 0) return cmp;
            return Integer.compare(a[1], b[1]);
        });
    }

    //PriorityQueue
    private static void pq(int time, int value){
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0] - b[0]);
        pq.add(new int[]{time, value});
    };

    //HashMap/Set/Max Priority Queue
    private static void mapSetMaxQueue(){
        HashMap<String, HashSet<String>> graph = new HashMap<>();
        graph.computeIfAbsent("A", k -> new HashSet<>()).add("B");

        HashMap<Integer, Integer> freq = new HashMap<>();
        PriorityQueue<Map.Entry<Integer,Integer>> pq = new PriorityQueue<>((a,b) -> b.getValue() - a.getValue());
        pq.addAll(freq.entrySet());
    }

    //monotonic Stack
    private static int[] monoStack(int[] a){
        int n = a.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        Deque<Integer> decreasingStack = new ArrayDeque<>();
        Deque<Integer> increasingStack = new ArrayDeque<>();
        for(int i = 0; i < n; i++){
            while(!decreasingStack.isEmpty() && a[i] > a[decreasingStack.peek()]){
                ans[decreasingStack.pop()] = i;
            }
            decreasingStack.push(i);
        }
        for(int i = 0; i < n; i++){
            while(!increasingStack.isEmpty() && a[i] < a[increasingStack.peek()]){
                ans[increasingStack.pop()] = i;
            }
            increasingStack.push(i);
        }
        return a;
    }

    //binary Search
    private static int binarySearch(int[] arr, boolean[] isFeasible){
        int left = 0, right = arr.length -1;
        int firstTrueIdx = -1;
        while(left <= right){
            int mid = left + (right - left) / 2;
            if(isFeasible[mid]){
                firstTrueIdx = mid;
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return firstTrueIdx;
    }

    //1D-DP
    private static int oneDimDP(int[] coins, int amount){
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int coin : coins)
            for (int i = coin; i <= amount; i++)
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
        return dp[amount] > amount ? -1 : dp[amount];
    }


    //2D-D
    private static int twoDimDP(int n, int m){
        int[][] dp = new int[n][m];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(i == 0 || j == 0){
                    dp[i][j]=1;
                }else{
                    dp[i][j] = dp[i - 1][j] + dp[i][j-1];
                }
            }
        }
        return dp[n-1][m-1];
    }

}
