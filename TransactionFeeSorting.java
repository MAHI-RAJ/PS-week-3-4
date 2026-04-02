import java.util.ArrayList;
import java.util.List;

class Transaction {
    String id;
    double fee;
    String timestamp; // format HH:MM

    public Transaction(String id, double fee, String timestamp) {
        this.id = id;
        this.fee = fee;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return id + ":" + fee + "@" + timestamp;
    }
}

public class TransactionFeeSorting {

    // Bubble Sort: sort by fee only (ascending)
    // Stable because swap happens only when left > right
    public static void bubbleSortByFee(ArrayList<Transaction> transactions) {
        int n = transactions.size();
        int passes = 0;
        int swaps = 0;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            passes++;

            for (int j = 0; j < n - 1 - i; j++) {
                if (transactions.get(j).fee > transactions.get(j + 1).fee) {
                    Transaction temp = transactions.get(j);
                    transactions.set(j, transactions.get(j + 1));
                    transactions.set(j + 1, temp);
                    swaps++;
                    swapped = true;
                }
            }

            // Early termination if already sorted
            if (!swapped) {
                break;
            }
        }

        System.out.println("Bubble Sort Result (by fee ascending):");
        printTransactions(transactions);
        System.out.println("Passes = " + passes + ", Swaps = " + swaps);
    }

    // Insertion Sort: sort by fee, then timestamp (ascending)
    // Stable because equal elements are not unnecessarily shifted
    public static void insertionSortByFeeAndTimestamp(ArrayList<Transaction> transactions) {
        int n = transactions.size();
        int passes = 0;
        int shifts = 0;

        for (int i = 1; i < n; i++) {
            Transaction key = transactions.get(i);
            int j = i - 1;
            passes++;

            while (j >= 0 && compare(transactions.get(j), key) > 0) {
                transactions.set(j + 1, transactions.get(j));
                j--;
                shifts++;
            }

            transactions.set(j + 1, key);
        }

        System.out.println("Insertion Sort Result (by fee + timestamp ascending):");
        printTransactions(transactions);
        System.out.println("Passes = " + passes + ", Shifts = " + shifts);
    }

    // Compare by fee first, then timestamp
    public static int compare(Transaction a, Transaction b) {
        if (a.fee < b.fee) return -1;
        if (a.fee > b.fee) return 1;
        return a.timestamp.compareTo(b.timestamp);
    }

    // Flag high-fee outliers
    public static void flagHighFeeOutliers(List<Transaction> transactions) {
        boolean found = false;
        System.out.println("High-fee outliers (> $50):");

        for (Transaction t : transactions) {
            if (t.fee > 50.0) {
                System.out.println(t.id + " -> fee = $" + t.fee + ", timestamp = " + t.timestamp);
                found = true;
            }
        }

        if (!found) {
            System.out.println("None");
        }
    }

    public static void printTransactions(List<Transaction> transactions) {
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    public static void main(String[] args) {
        ArrayList<Transaction> transactions = new ArrayList<>();

        // Sample input
        transactions.add(new Transaction("id1", 10.5, "10:00"));
        transactions.add(new Transaction("id2", 25.0, "09:30"));
        transactions.add(new Transaction("id3", 5.0, "10:15"));
        transactions.add(new Transaction("id4", 60.0, "11:00")); // outlier example
        transactions.add(new Transaction("id5", 25.0, "09:45")); // duplicate fee

        System.out.println("Original Transactions:");
        printTransactions(transactions);

        int size = transactions.size();

        if (size <= 100) {
            ArrayList<Transaction> bubbleList = new ArrayList<>(transactions);
            System.out.println("\nUsing Bubble Sort for small batch (<=100)");
            bubbleSortByFee(bubbleList);
        }

        if (size > 100 && size <= 1000) {
            ArrayList<Transaction> insertionList = new ArrayList<>(transactions);
            System.out.println("\nUsing Insertion Sort for medium batch (100-1000)");
            insertionSortByFeeAndTimestamp(insertionList);
        }

        // For demonstration, we run insertion sort too
        ArrayList<Transaction> insertionDemo = new ArrayList<>(transactions);
        System.out.println("\nInsertion Sort Demo:");
        insertionSortByFeeAndTimestamp(insertionDemo);

        System.out.println();
        flagHighFeeOutliers(transactions);
    }
}
