import java.util.Random;

class Asset {
    String name;
    double returnRate;
    double volatility;

    Asset(String name, double returnRate, double volatility) {
        this.name = name;
        this.returnRate = returnRate;
        this.volatility = volatility;
    }

    @Override
    public String toString() {
        return name + ":" + returnRate + "% (vol=" + volatility + ")";
    }
}

public class PortfolioReturnSorting {

    static Random random = new Random();
    static final int INSERTION_SORT_THRESHOLD = 10;

    // ---------------- MERGE SORT ----------------
    // Sort by returnRate ascending
    // Stable: preserves original order for ties
    public static void mergeSort(Asset[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    public static void merge(Asset[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Asset[] L = new Asset[n1];
        Asset[] R = new Asset[n2];

        for (int i = 0; i < n1; i++) {
            L[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (L[i].returnRate <= R[j].returnRate) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }

        while (i < n1) {
            arr[k++] = L[i++];
        }

        while (j < n2) {
            arr[k++] = R[j++];
        }
    }

    // ---------------- QUICK SORT ----------------
    // Sort by returnRate DESC + volatility ASC
    // Hybrid: uses insertion sort for small partitions
    public static void quickSort(Asset[] arr, int low, int high, String pivotType) {
        if (low < high) {
            if (high - low + 1 <= INSERTION_SORT_THRESHOLD) {
                insertionSort(arr, low, high);
                return;
            }

            int pivotIndex = choosePivot(arr, low, high, pivotType);
            int p = partition(arr, low, high, pivotIndex);

            quickSort(arr, low, p - 1, pivotType);
            quickSort(arr, p + 1, high, pivotType);
        }
    }

    public static int choosePivot(Asset[] arr, int low, int high, String pivotType) {
        if (pivotType.equalsIgnoreCase("random")) {
            return low + random.nextInt(high - low + 1);
        } else {
            return medianOfThree(arr, low, high);
        }
    }

    public static int medianOfThree(Asset[] arr, int low, int high) {
        int mid = (low + high) / 2;

        Asset a = arr[low];
        Asset b = arr[mid];
        Asset c = arr[high];

        if (compareDesc(a, b) < 0) {
            Asset temp = a; a = b; b = temp;
        }
        if (compareDesc(a, c) < 0) {
            Asset temp = a; a = c; c = temp;
        }
        if (compareDesc(b, c) < 0) {
            Asset temp = b; b = c; c = temp;
        }

        if (b == arr[low]) return low;
        if (b == arr[mid]) return mid;
        return high;
    }

    public static int partition(Asset[] arr, int low, int high, int pivotIndex) {
        swap(arr, pivotIndex, high);
        Asset pivot = arr[high];

        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (compareDesc(arr[j], pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    // Compare for Quick Sort:
    // returnRate DESC, if tie volatility ASC
    public static int compareDesc(Asset a, Asset b) {
        if (a.returnRate > b.returnRate) return -1;
        if (a.returnRate < b.returnRate) return 1;

        if (a.volatility < b.volatility) return -1;
        if (a.volatility > b.volatility) return 1;

        return 0;
    }

    // ---------------- INSERTION SORT FOR SMALL PARTITIONS ----------------
    public static void insertionSort(Asset[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            Asset key = arr[i];
            int j = i - 1;

            while (j >= low && compareDesc(arr[j], key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }

    // ---------------- UTILITY ----------------
    public static void swap(Asset[] arr, int i, int j) {
        Asset temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void printAssets(String msg, Asset[] arr) {
        System.out.print(msg + " [");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i].name + ":" + arr[i].returnRate + "%");
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        Asset[] assets = {
            new Asset("AAPL", 12, 5.2),
            new Asset("TSLA", 8, 7.5),
            new Asset("GOOG", 15, 4.1),
            new Asset("MSFT", 12, 3.8)
        };

        System.out.println("Original Asset Data:");
        printAssets("Input:", assets);

        // Merge Sort ascending by returnRate
        Asset[] mergeArray = assets.clone();
        mergeSort(mergeArray, 0, mergeArray.length - 1);
        printAssets("Merge Sort (Ascending):", mergeArray);

        // Quick Sort descending by returnRate + volatility
        Asset[] quickArray = assets.clone();
        quickSort(quickArray, 0, quickArray.length - 1, "median3");
        printAssets("Quick Sort (Descending):", quickArray);
    }
}