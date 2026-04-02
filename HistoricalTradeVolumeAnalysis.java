class Trade {
    String id;
    int volume;

    Trade(String id, int volume) {
        this.id = id;
        this.volume = volume;
    }

    @Override
    public String toString() {
        return id + ":" + volume;
    }
}

public class HistoricalTradeVolumeAnalysis {

    // ---------------- MERGE SORT (Ascending, Stable) ----------------
    public static void mergeSort(Trade[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    public static void merge(Trade[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Trade[] L = new Trade[n1];
        Trade[] R = new Trade[n2];

        for (int i = 0; i < n1; i++) {
            L[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;

        // Stable: if equal, take from left side first
        while (i < n1 && j < n2) {
            if (L[i].volume <= R[j].volume) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    // ---------------- QUICK SORT (Descending, In-place) ----------------
    public static void quickSortDesc(Trade[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = medianOfThree(arr, low, high);
            int partitionIndex = partitionDesc(arr, low, high, pivotIndex);

            quickSortDesc(arr, low, partitionIndex - 1);
            quickSortDesc(arr, partitionIndex + 1, high);
        }
    }

    // Median-of-three pivot selection
    public static int medianOfThree(Trade[] arr, int low, int high) {
        int mid = (low + high) / 2;

        int a = arr[low].volume;
        int b = arr[mid].volume;
        int c = arr[high].volume;

        if ((a > b && a < c) || (a < b && a > c)) return low;
        if ((b > a && b < c) || (b < a && b > c)) return mid;
        return high;
    }

    // Lomuto partition for descending order
    public static int partitionDesc(Trade[] arr, int low, int high, int pivotIndex) {
        swap(arr, pivotIndex, high);
        Trade pivot = arr[high];

        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].volume >= pivot.volume) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    public static void swap(Trade[] arr, int i, int j) {
        Trade temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // ---------------- MERGE TWO SORTED LISTS ----------------
    public static Trade[] mergeTwoSortedLists(Trade[] a, Trade[] b) {
        Trade[] merged = new Trade[a.length + b.length];

        int i = 0, j = 0, k = 0;

        while (i < a.length && j < b.length) {
            if (a[i].volume <= b[j].volume) {
                merged[k++] = a[i++];
            } else {
                merged[k++] = b[j++];
            }
        }

        while (i < a.length) {
            merged[k++] = a[i++];
        }

        while (j < b.length) {
            merged[k++] = b[j++];
        }

        return merged;
    }

    // ---------------- TOTAL VOLUME ----------------
    public static int computeTotalVolume(Trade[] arr) {
        int total = 0;
        for (Trade t : arr) {
            total += t.volume;
        }
        return total;
    }

    // ---------------- DISPLAY ----------------
    public static void printTrades(String message, Trade[] arr) {
        System.out.print(message + " [");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {
        Trade[] trades = {
            new Trade("trade3", 500),
            new Trade("trade1", 100),
            new Trade("trade2", 300)
        };

        System.out.println("Original Trade Data:");
        printTrades("Input:", trades);

        // Merge Sort Ascending
        Trade[] mergeArray = trades.clone();
        mergeSort(mergeArray, 0, mergeArray.length - 1);
        printTrades("MergeSort (Ascending):", mergeArray);

        // Quick Sort Descending
        Trade[] quickArray = trades.clone();
        quickSortDesc(quickArray, 0, quickArray.length - 1);
        printTrades("QuickSort (Descending):", quickArray);

        // Merge two sorted trade lists (morning and afternoon)
        Trade[] morning = {
            new Trade("trade1", 100),
            new Trade("trade2", 300)
        };

        Trade[] afternoon = {
            new Trade("trade3", 500)
        };

        // Make sure both are sorted before merging
        mergeSort(morning, 0, morning.length - 1);
        mergeSort(afternoon, 0, afternoon.length - 1);

        Trade[] mergedTrades = mergeTwoSortedLists(morning, afternoon);
        printTrades("Merged Morning + Afternoon:", mergedTrades);

        int totalVolume = computeTotalVolume(mergedTrades);
        System.out.println("Total Volume: " + totalVolume);
    }
}