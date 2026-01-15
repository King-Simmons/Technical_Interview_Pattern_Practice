import java.util.*;

public class LevelTwoPatterns {
    public static void main(String[] args) {
        System.out.println("""
            Level Two Patterns:\s
            - Prefix Sum Range Query\s
            - LinkedList Traversals\s
            - TreeSet/TreeMap\s
            - DP-Warmup \s
            - DP-ConstantTransition\s
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


}
