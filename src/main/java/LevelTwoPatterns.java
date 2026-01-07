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
}
