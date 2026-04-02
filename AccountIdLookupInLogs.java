import java.util.Arrays;

public class AccountIdLookupInLogs {

    static int linearComparisons = 0;
    static int binaryComparisons = 0;

    // -------- LINEAR SEARCH: FIRST OCCURRENCE --------
    public static int linearSearchFirst(String[] arr, String target) {
        linearComparisons = 0;
        for (int i = 0; i < arr.length; i++) {
            linearComparisons++;
            if (arr[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    // -------- LINEAR SEARCH: LAST OCCURRENCE --------
    public static int linearSearchLast(String[] arr, String target) {
        int lastIndex = -1;
        for (int i = 0; i < arr.length; i++) {
            linearComparisons++;
            if (arr[i].equals(target)) {
                lastIndex = i;
            }
        }
        return lastIndex;
    }

    // -------- BINARY SEARCH: ANY ONE OCCURRENCE --------
    public static int binarySearch(String[] arr, String target) {
        binaryComparisons = 0;
        int low = 0, high = arr.length - 1;

        while (low <= high) {
            binaryComparisons++;
            int mid = (low + high) / 2;

            int cmp = arr[mid].compareTo(target);

            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }

    // -------- FIRST OCCURRENCE USING BINARY SEARCH --------
    public static int binarySearchFirst(String[] arr, String target) {
        int low = 0, high = arr.length - 1;
        int first = -1;

        while (low <= high) {
            binaryComparisons++;
            int mid = (low + high) / 2;

            int cmp = arr[mid].compareTo(target);

            if (cmp == 0) {
                first = mid;
                high = mid - 1; // search left side
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return first;
    }

    // -------- LAST OCCURRENCE USING BINARY SEARCH --------
    public static int binarySearchLast(String[] arr, String target) {
        int low = 0, high = arr.length - 1;
        int last = -1;

        while (low <= high) {
            binaryComparisons++;
            int mid = (low + high) / 2;

            int cmp = arr[mid].compareTo(target);

            if (cmp == 0) {
                last = mid;
                low = mid + 1; // search right side
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return last;
    }

    // -------- COUNT OCCURRENCES USING BINARY SEARCH --------
    public static int countOccurrences(String[] arr, String target) {
        binaryComparisons = 0;

        int first = binarySearchFirst(arr, target);
        if (first == -1) return 0;

        int last = binarySearchLast(arr, target);
        return last - first + 1;
    }

    // -------- DISPLAY ARRAY --------
    public static void printArray(String message, String[] arr) {
        System.out.print(message + " [");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    // -------- MAIN --------
    public static void main(String[] args) {

        String[] logs = {"accB", "accA", "accB", "accC"};
        String target = "accB";

        System.out.println("Original Transaction Logs:");
        printArray("Logs:", logs);

        // Linear Search
        linearComparisons = 0;
        int firstLinear = linearSearchFirst(logs, target);
        int compsAfterFirst = linearComparisons;

        int lastLinear = linearSearchLast(logs, target);
        int totalLinearComparisons = linearComparisons;

        System.out.println("\nLinear Search Results:");
        System.out.println("First occurrence of " + target + ": index " + firstLinear);
        System.out.println("Comparisons for first occurrence: " + compsAfterFirst);
        System.out.println("Last occurrence of " + target + ": index " + lastLinear);
        System.out.println("Total comparisons after both linear searches: " + totalLinearComparisons);

        // Sort for Binary Search
        String[] sortedLogs = logs.clone();
        Arrays.sort(sortedLogs);

        System.out.println("\nSorted Logs for Binary Search:");
        printArray("Sorted:", sortedLogs);

        // Binary Search
        binaryComparisons = 0;
        int binaryIndex = binarySearch(sortedLogs, target);
        int searchComparisons = binaryComparisons;

        int count = countOccurrences(sortedLogs, target);
        int totalBinaryComparisons = binaryComparisons;

        System.out.println("\nBinary Search Results:");
        System.out.println("One occurrence of " + target + ": index " + binaryIndex);
        System.out.println("Comparisons for exact match: " + searchComparisons);
        System.out.println("Count of occurrences: " + count);
        System.out.println("Comparisons during count operation: " + totalBinaryComparisons);

        // Time Complexity Report
        System.out.println("\nTime Complexity:");
        System.out.println("Linear Search: O(n)");
        System.out.println("Binary Search: O(log n) (requires sorted input)");
    }
}