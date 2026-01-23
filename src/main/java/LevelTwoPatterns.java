import java.util.*;

public class LevelTwoPatterns {
    public static void main(String[] args) {
        System.out.println("""
            Level Two Patterns:\s
            - Prefix Sum Range Query\s
            - LinkedList Traversals\s
            - TreeSet/TreeMap\s
            - ArrayToList\s
            - DP-Warmup \s
            - DP-ConstantTransition\s
            - DP-Grid\s
            - DP-DualSequences\s
            - DP-NonConstantTransition\s
            - DP-Knapsack-Weight\s
            - DP-Knapsack-WeightAndValue\s
            - DP-Interval\s
            - DP-TopSort\s
            - DP-Trees\s
            - DP-Bitmask\s
            """);

        System.out.println("Compiled!!!");
    }

    //prefix sum range query
    private static List<Integer> prefixSumRangeQuery(int[] arr, int target){
        if(arr.length == 0) return List.of();
        Map<Integer, Integer> indexMap = new HashMap<>();
        int currSum = 0;
        indexMap.put(0, 0);
        for(int i = 0; i < arr.length; i++){
            currSum += arr[i];
            int diff = currSum - target;
            if(indexMap.containsKey(diff)){
                return List.of(indexMap.get(diff), i + 1);
            }else{
                indexMap.put(currSum, i + 1);
            }
        }
        return List.of();
    }

    //ListNode
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(){

        }
        ListNode(int val){
            this.val = val;
        }
        ListNode(int val, ListNode next){
            this.val = val;
            this.next = next;
        }
    }

    //Linked List Traversals
    private static void LinkedListTraversals(ListNode head){
        //Lead-Lag
        ListNode lag = head;
        ListNode lead = head.next.next;
        while(lead != null){
            lead = lead.next;
            lag = lag.next;
        }

        //Fast-Slow
        ListNode fast = head;
        ListNode slow = head;
        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }

        //Reversal
        ListNode prev = null;
        ListNode curr = head;
        ListNode next = head;

        while(curr != null){
            next = next.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
    }

    //TreeSetMap
    private static int TreeSetMap(int[] peak, int k){
        //All operations are O(log(n))
        TreeSet<Integer> treeSet = new TreeSet<>();
        int left = 0;
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < peak.length; i++){
            int curr = peak[i];
            if(i - left >= k){
                treeSet.add(peak[left]);
                left++;
            }

            if(!treeSet.isEmpty()){
                //also has methods lower and higher for non-equal comparisons
                //treeMap has same but must define Key after (ie. ceilingKey(), ceilingValue())
                Integer closestAbove = treeSet.ceiling(curr);
                Integer closestBelow = treeSet.floor(curr);
                if(closestAbove != null){
                    min = Math.min(min, closestAbove - curr);
                }
                if(closestBelow != null){
                    min = Math.min(min, curr - closestBelow);
                }
            }
        }
        return min == Integer.MAX_VALUE ? -1 : min;
    }


    //arraysToList
    private static void arrayToList(){
        int[] arr = new int[1];
        List<Integer> list = Arrays.stream(arr).boxed().toList();
        arr = list.stream().mapToInt(Integer::intValue).toArray();
        System.out.println(Arrays.toString(arr));
    }


    //dp-warmup
    private static int dpWarmUp(int n){
        //0, 1, 1, 2, 4, 7...
        if(n < 3){
            if(n == 0){
                return 0;
            }else{
                return 1;
            }
        }
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 1;
        dp[3] = 2;

        for(int i = 4; i <= n; i++){
            dp[i] = dp[i-1] + dp[i-2] + dp[i-3];
        }
        return dp[n];
    }

    //dp-constantTransition
    private static int dpConstantTransition(int[] stairs){
        int n = stairs.length;
        if(n <= 1){
            return 0;
        }
        //constantly looking back 1 and 2 steps. Can optimize space from O(n) to (1)
        int prev1 = 0;
        int prev2 = 0;

        //looking for 1 past the end
        for(int i = 2; i <= n; i++){
            int curr = Math.min(prev1 + stairs[i-1], prev2 + stairs[i-2]);
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }

    //dpGrid
    public static int dpGrid(int m, int n) {
        // Optimized to O(n) since only using the previous row
        // dp[m][n] = dp[m - 1][n] + dp[m][n-1] -> dp[n] = dp[n-1] + dp[n]
        // dp[n] is previous after each loop
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++){
                dp[j] = dp[j-1] + dp[j];
            }
        }
        return dp[n-1];
    }

    //dpDualSequences
    private static int dpDualSequences(String a, String b){
        //save space by having the column (n) be the smaller string
        if(a.length() < b.length()){
            String temp = a;
            a = b;
            b = temp;
        }

        int m = a.length();
        int n = b.length();
        if(m == 0 || n == 0){
            return 0;
        }

        //save space using 2 arrays instead of 2D array
        //possible because we only look back 1 row
        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];

        for(int i = 0; i <= m; i++){
            for(int j = 0; j <= n; j++){
                //base: empty string
                if(i == 0 || j == 0){
                    curr[j] = 0;
                }else{
                    //same
                    if(a.charAt(i-1) == b.charAt(j-1)){
                        curr[j] = prev[j-1] + 1;
                    }else{
                        //different
                        int useStr1= prev[j];
                        int useStr2 = curr[j-1];
                        curr[j] = Math.max(useStr1, useStr2);
                    }
                }
            }
            //make sure to swap instead of copying for O(1) time
            //make sure outside of the inner loop
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }
        return prev[n];
    }

    //dpNonConstantTransition
    private static int dpNonConstantTransition(List<Integer> nums){
        int m = nums.size();
        if(m == 0) return 0;
        int[] dp = new int[m];
        int max = 0;
        int longest = 0;

        for(int i = 0; i < m; i++){
            dp[i] = 1; //Every element is an LIS of 1
            //get the longest at each index, only check where nums is less than curr
            //nlog(n) by using binary search where dp[i] = lowest number at length i
            for(int j = 0; j < i; j++){
                if(nums.get(j) < nums.get(i)){
                    max = Math.max(max, dp[j]);
                }
            }
            dp[i] = max + 1;
            longest = Math.max(dp[i], longest);
            max = 0;
        }
        return longest;
    }

    //Knapsack-Weight
    private static boolean knapsackWeight(List<Integer> nums){
        int totalSum = 0;
        int n = nums.size();
        for(int i : nums){
            totalSum += i;
        }
        int target = totalSum / 2;
        if(totalSum % 2 != 0){
            return false;
        }
        boolean[][] dp = new boolean[n + 1][target + 1];
        dp[0][0] = true;

        for(int i = 1; i <= n; i++){
            for(int sum = 0; sum <= target; sum++){
                //don't take nums[i-1]
                dp[i][sum] = dp[i - 1][sum];
                // take nums[i-1]
                if(sum - nums.get(i-1) >= 0){
                    dp[i][sum] = dp[i][sum] || dp[i-1][sum-nums.get(i-1)];
                }
            }
        }
        return dp[n][target];
    }

    //Knapsack-WeightAndValue
    private static int knapsackWeightAndValue(List<Integer> weights, List<Integer> values, int maxWeight){
        int n = weights.size();
        int[][] dp = new int[n+1][maxWeight+1];

        for(int i = 1; i <= n; i++){
            for(int j = 0; j <= maxWeight; j++){
                dp[i][j] = dp[i - 1][j];
                if(j - weights.get(i - 1) >= 0){
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j-weights.get(i - 1)] + values.get(i - 1));
                }
            }
        }
        return dp[n][maxWeight];
    }



}
