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
                - Topological Sort\s
                - Two Pointers Opposite + Same\s
                - Prefix Sum\s
                - Sliding Window Fixed + Longest + Shortest\s
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
        int m = grid.length, n = grid[0].length;
        Deque<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[m][n];
        int[][] dirs = {{0,1}, {1,0}, {-1, 0}, {0,-1}};

        q.add(new int[]{sr, sc});
        visited[sr][sc] = true;

        while(!q.isEmpty()){
            int[] curr = q.poll();
            int r = curr[0], c = curr[1];

            for(int[] d : dirs){
                int newR = r + d[0], newC = c + d[1];
                if(newR >= 0 && newC >= 0 &&  newR < m && newC < n && !visited[newR][newC] && grid[newR][newC] == target){
                    q.add(new int[]{newR, newC});
                    visited[newR][newC] = true;
                }
            }
        }
   }

    //BFS multiSoure directional + levels
    private static int bfs(int target, int start, int[][] grid){
        int m = grid.length, n = grid[0].length;
        Deque<int[]> q = new ArrayDeque<>();
        int[][] dirs = {{0,1}, {-1,0}, {1, 0}, {0, -1}};
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

    //topoSort
    private static List<Character> topoSort(Map<Character, List<Character>> graph){
        List<Character> ans = new ArrayList<>();
        Map<Character, Integer> indegree = new HashMap<>();
        for(Character c : graph.keySet()){
            indegree.put(c, 0);
        }
        for(Map.Entry<Character, List<Character>> entry: graph.entrySet()){
            for(Character c : entry.getValue()){
                indegree.put(c, indegree.get(c) + 1);
            }
        }
        PriorityQueue<Character> pq = new PriorityQueue<>();

        for(Map.Entry<Character, Integer> entry: indegree.entrySet()){
            if(entry.getValue() == 0) pq.add(entry.getKey());
        }
        while(!pq.isEmpty()){
            char curr = pq.poll();
            ans.add(curr);
            for(Character c : graph.get(curr)){
                indegree.put(c, indegree.get(c) - 1);
                if(indegree.get(c) == 0) pq.add(c);
            }
        }
        for(int i : indegree.values()){
            if(i != 0) return null;
        }

        return ans;
    }


    //two pointers same + opposite
    private static void twoPointersBoth(int[] arr){
        int l = 0, r = arr.length - 1;
        //opposite
        while( l < r){
            //process
            if(arr[l] == arr[r]){
                l++;
            }else r--;
        }
        //same
        int slow = 0, fast = 0;
         while(fast < arr.length){
             //process
             if(arr[slow] != arr[fast]){
                 slow++;
                 arr[slow] = arr[fast];
             }
             fast++;
         }
    }


    //pre-fix sum //trip[] = {capacity, start, end}
    private static boolean prefixSum(int[][] trips, int maxCapacity){
        int[] diff = new int[trips.length + 1];
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

    //sliding window fixed, longest, shortest
    private static List<Integer> slidingWindowAll(List<Integer> input, int windowSize, int target){
        List<Integer> ans = new ArrayList<>();

        //fixed
        int sum = 0;
        int left = 0;
        List<Integer> window = input.subList(0, windowSize);
        for(int i : window) sum += i;
        if(sum == target) return window;

        for(int right = windowSize + 1; right < input.size(); right++){
            left = right - windowSize;
            window.add(input.get(right));
            window.remove(0);
            sum += input.get(right);
            sum -= input.get(left);
            if(sum == target) return window;
        }

        //longest
        sum = 0;
        left = 0;
        window = new ArrayList<>();
        int maxSize = 0;

        for(int right = 0; right < input.size(); right++){
            window.add(input.get(right));
            sum += input.get(right);

            while(sum > target && left <= right){
                window.remove(0);
                sum -= input.get(left);
                left++;
            }

            if(maxSize < window.size()){
                maxSize = window.size();
                ans = window;
            }
        }

        //shortest
        sum = 0;
        left = 0;
        window = new ArrayList<>();
        int minSize = Integer.MAX_VALUE;

        for(int right = 0; right < input.size(); right++){
            window.add(input.get(right));
            sum += input.get(right);

            while(sum >= target && left <= right){
                if(minSize > window.size()){
                    minSize = window.size();
                    ans = window;
                }
                window.remove(0);
                sum -= input.get(left);
                left++;
            }
        }

        return ans;
    }

    //sorting with TieBreaker
    private static void sortWithTieBreaker(int[][] arr){
        Arrays.sort(arr, (a,b) -> {
            int cmp = Integer.compare(a[0], b[0]);
            if(cmp != 0) return cmp;
            return Integer.compare(a[1], b[1]);
        });
    }

    //PriorityQueue
    private static void pq(int time, int value){
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0] - b[0]);
        pq.add(new int[]{time, value});
    }

    //HashMap/Set/Max Priority Queue
    private static void mapSetMaxPQ(){
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
        Deque<Integer> decStack = new ArrayDeque<>();
        Deque<Integer> incStack = new ArrayDeque<>();
        for(int i = 0; i < n; i++){
            while(!decStack.isEmpty() && a[i] > a[decStack.peek()]){
                ans[decStack.pop()] = i;
            }
            decStack.push(i);
        }
        for(int i = 0; i < n; i++){
            while(!incStack.isEmpty() && a[i] < a[incStack.peek()]){
                ans[incStack.pop()] = i;
            }
            incStack.push(i);
        }
        return ans;
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
