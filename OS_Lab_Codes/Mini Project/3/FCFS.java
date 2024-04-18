import java.util.Scanner;

class FCFS {
    // Method to calculate the total seek count given an array of track positions
    // and the initial head position
    static int calculateTotalSeekCount(int arr[], int head) {
        int size = arr.length;
        int seek_count = 0;
        int distance, cur_track;

        for (int i = 0; i < size; i++) {
            cur_track = arr[i];

            // Calculate the absolute distance between the current track and the head
            distance = Math.abs(cur_track - head);

            // Increase the total seek count by the distance
            seek_count += distance;

            // The accessed track becomes the new head for the next iteration
            head = cur_track;
        }

        return seek_count;
    }

    // Method to perform First-Come-First-Served (FCFS) disk scheduling
    static void FCFS(int arr[], int head) {
        int size = arr.length;
        int seek_count = calculateTotalSeekCount(arr, head);

        System.out.println("Total number of seek operations = " + seek_count);

        // The seek sequence is the same as the request array sequence
        System.out.println("Seek Sequence is");

        for (int i = 0; i < size; i++) {
            System.out.println(arr[i]);
        }
    }

    // Driver code
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the size of the request queue:");
        int size = scanner.nextInt();
        int arr[] = new int[size];

        // Take user input for the request array
        System.out.println("Enter the request queue:");
        for (int i = 0; i < size; i++) {
            arr[i] = scanner.nextInt();
        }

        System.out.println("Enter the initial head position:");
        int head = scanner.nextInt();

        FCFS(arr, head);

        scanner.close();
    }
}