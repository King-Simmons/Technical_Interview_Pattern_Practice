import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelTwoPatterns {
    public static void main(String[] args) {
        System.out.println("""
            Level Two Patterns:\s
            - Prefix Sum Range Query\s
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
    public class ListNode {
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
}
