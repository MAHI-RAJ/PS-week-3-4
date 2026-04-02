public class RiskThresholdBinaryLookup {

    static int linearComparisons = 0;
    static int binaryComparisons = 0;

    // Linear Search in unsorted array
    public static int linearSearch(int[] arr, int target) {
        linearComparisons = 0;

        for (int i = 0; i < arr.length; i++) {
            linearComparisons++;
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // Binary Search to find insertion point (lower bound)
    public static int findInsertionPoint(int[] arr, int target) {
        binaryComparisons = 0;

        int low = 0;
        int high = arr.length;

        while (low < high) {
            binaryComparisons++;
            int mid = (low + high) / 2;

            if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }

    // Floor = largest value <= target
    public static Integer findFloor(int[] arr, int target) {
        int pos = findInsertionPoint(arr, target);

        if (pos < arr.length && arr[pos] == target) {
            return arr[pos];
        } else if (pos > 0) {
            return arr[pos - 1];
        } else {
            return null;
        }
    }

    // Ceiling = smallest value >= target
    public static Integer findCeiling(int[] arr, int target) {
        int pos = findInsertionPoint(arr, target);

        if (pos < arr.length) {
            return arr[pos];
        } else {
            return null;
        }
    }

    // Print array
    public static void printArray(String msg, int[] arr) {
        System.out.print(msg + " [");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    public static void main(String[] args) {

        int[] unsortedRisks = {50, 10, 100, 25};
        int[] sortedRisks = {10, 25, 50, 100};
        int target = 30;

        System.out.println("Risk Threshold Binary Lookup");
        System.out.println("----------------------------");

        printArray("Unsorted risks:", unsortedRisks);
        printArray("Sorted risks  :", sortedRisks);
        System.out.println("Target = " + target);

        // Linear Search
        int linearIndex = linearSearch(unsortedRisks, target);
        System.out.println("\nLinear Search (Unsorted):");
        if (linearIndex != -1) {
            System.out.println("Threshold found at index: " + linearIndex);
        } else {
            System.out.println("Threshold not found");
        }
        System.out.println("Comparisons: " + linearComparisons);

        // Binary Search variants
        int insertionPoint = findInsertionPoint(sortedRisks, target);
        int insertionComparisons = binaryComparisons;

        Integer floor = findFloor(sortedRisks, target);
        int floorComparisons = binaryComparisons;

        Integer ceiling = findCeiling(sortedRisks, target);
        int ceilingComparisons = binaryComparisons;

        System.out.println("\nBinary Search (Sorted):");
        System.out.println("Insertion point for " + target + ": index " + insertionPoint);

        if (floor != null) {
            System.out.println("Floor(" + target + ") = " + floor);
        } else {
            System.out.println("Floor(" + target + ") = not available");
        }

        if (ceiling != null) {
            System.out.println("Ceiling(" + target + ") = " + ceiling);
        } else {
            System.out.println("Ceiling(" + target + ") = not available");
        }

        System.out.println("Comparisons for insertion point: " + insertionComparisons);
        System.out.println("Comparisons for floor: " + floorComparisons);
        System.out.println("Comparisons for ceiling: " + ceilingComparisons);

        System.out.println("\nTime Complexity:");
        System.out.println("Linear Search  : O(n)");
        System.out.println("Binary Search  : O(log n)");
    }
}