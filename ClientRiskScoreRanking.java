class Client {
    String name;
    int riskScore;
    double accountBalance;

    Client(String name, int riskScore, double accountBalance) {
        this.name = name;
        this.riskScore = riskScore;
        this.accountBalance = accountBalance;
    }

    public String toString() {
        return name + " [riskScore=" + riskScore + ", balance=" + accountBalance + "]";
    }
}

public class ClientRiskScoreRanking {

    // Bubble Sort: ascending by riskScore
    public static void bubbleSortAscending(Client[] clients) {
        int n = clients.length;
        int swaps = 0;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (clients[j].riskScore > clients[j + 1].riskScore) {
                    Client temp = clients[j];
                    clients[j] = clients[j + 1];
                    clients[j + 1] = temp;

                    swaps++;
                    swapped = true;

                    System.out.println("Swap: " + clients[j].name + " <-> " + clients[j + 1].name);
                }
            }

            if (!swapped) {
                break; // already sorted
            }
        }

        System.out.println("\nBubble Sort (Ascending by riskScore):");
        printClients(clients);
        System.out.println("Total Swaps: " + swaps);
    }

    // Insertion Sort: descending by riskScore, then ascending by accountBalance
    public static void insertionSortDescending(Client[] clients) {
        int n = clients.length;

        for (int i = 1; i < n; i++) {
            Client key = clients[i];
            int j = i - 1;

            while (j >= 0 && compare(clients[j], key) > 0) {
                clients[j + 1] = clients[j];
                j--;
            }

            clients[j + 1] = key;
        }

        System.out.println("\nInsertion Sort (Descending by riskScore + accountBalance):");
        printClients(clients);
    }

    // Comparison for insertion sort
    // Higher riskScore should come first
    // If riskScore is same, lower accountBalance comes first
    public static int compare(Client a, Client b) {
        if (a.riskScore < b.riskScore) return 1;
        if (a.riskScore > b.riskScore) return -1;

        if (a.accountBalance > b.accountBalance) return 1;
        if (a.accountBalance < b.accountBalance) return -1;

        return 0;
    }

    // Print top 10 highest risk clients
    public static void printTopHighRiskClients(Client[] clients) {
        System.out.println("\nTop Highest Risk Clients:");
        int limit = Math.min(10, clients.length);

        for (int i = 0; i < limit; i++) {
            System.out.println((i + 1) + ". " + clients[i].name + " (Risk Score: " + clients[i].riskScore + ")");
        }
    }

    public static void printClients(Client[] clients) {
        for (Client c : clients) {
            System.out.println(c);
        }
    }

    public static void main(String[] args) {
        Client[] clients = {
            new Client("clientC", 80, 15000.0),
            new Client("clientA", 20, 5000.0),
            new Client("clientB", 50, 12000.0),
            new Client("clientD", 95, 8000.0),
            new Client("clientE", 50, 7000.0)
        };

        System.out.println("Original Client Data:");
        printClients(clients);

        // Bubble Sort demo
        Client[] bubbleArray = clients.clone();
        bubbleSortAscending(bubbleArray);

        // Insertion Sort demo
        Client[] insertionArray = clients.clone();
        insertionSortDescending(insertionArray);

        // Top 10 highest risk clients after descending sort
        printTopHighRiskClients(insertionArray);
    }
}