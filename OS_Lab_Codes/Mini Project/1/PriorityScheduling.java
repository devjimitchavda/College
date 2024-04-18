import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

// Definition of Process class
class Process {
    int processID;
    int arrivalTime;
    int priority;
    int burstTime;
    int tempburstTime;
    int responsetime = -1;
    int outtime;
    int intime = -1;

    // Constructor
    public Process(int processID, int arrivalTime, int priority, int burstTime) {
        this.processID = processID;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.burstTime = burstTime;
        this.tempburstTime = burstTime;
    }
}

// Main class
public class PriorityScheduling {

    // Function to insert a process into the heap
    static void insert(Process[] Heap, Process value, int[] heapsize, int[] currentTime) {
        int start = heapsize[0];
        Heap[start] = value;
        if (Heap[start].intime == -1)
            Heap[start].intime = currentTime[0];
        heapsize[0]++;

        // Ordering the Heap based on priority
        while (start != 0 && Heap[(start - 1) / 2].priority > Heap[start].priority) {
            Process temp = Heap[(start - 1) / 2];
            Heap[(start - 1) / 2] = Heap[start];
            Heap[start] = temp;
            start = (start - 1) / 2;
        }
    }

    // Function to reorder the heap based on priority
    static void order(Process[] Heap, int[] heapsize, int start) {
        int smallest = start;
        int left = 2 * start + 1;
        int right = 2 * start + 2;
        if (left < heapsize[0] && Heap[left].priority < Heap[smallest].priority)
            smallest = left;
        if (right < heapsize[0] && Heap[right].priority < Heap[smallest].priority)
            smallest = right;

        // Ordering the Heap based on priority
        if (smallest != start) {
            Process temp = Heap[smallest];
            Heap[smallest] = Heap[start];
            Heap[start] = temp;
            order(Heap, heapsize, smallest);
        }
    }

    // Function to extract the process with the highest priority from the heap
    static Process extractminimum(Process[] Heap, int[] heapsize, int[] currentTime) {
        Process min_process = Heap[0];
        if (min_process.responsetime == -1)
            min_process.responsetime = currentTime[0] - min_process.arrivalTime;
        heapsize[0]--;
        if (heapsize[0] >= 1) {
            Heap[0] = Heap[heapsize[0]];
            order(Heap, heapsize, 0);
        }
        return min_process;
    }

    // Function to compare two processes based on arrival time
    static boolean compare(Process p1, Process p2) {
        return p1.arrivalTime < p2.arrivalTime;
    }

    // Function responsible for executing the highest priority process extracted
    // from the heap
    static void scheduling(Process[] Heap, Process[] array, int n, int[] heapsize, int[] currentTime) {
        if (heapsize[0] == 0)
            return;

        Process min_process = extractminimum(Heap, heapsize, currentTime);
        min_process.outtime = currentTime[0] + 1;
        min_process.burstTime--;
        System.out.println("process id = " + min_process.processID + " current time = " + currentTime[0]);

        // If the process is not yet finished, insert it back into the Heap
        if (min_process.burstTime > 0) {
            insert(Heap, min_process, heapsize, currentTime);
            return;
        }

        for (int i = 0; i < n; i++)
            if (array[i].processID == min_process.processID) {
                array[i] = min_process;
                break;
            }
    }

    // Function responsible for managing the entire execution of processes based on
    // arrival time
    static void priority(Process[] array, int n) {
        Arrays.sort(array, Comparator.comparingInt(a -> a.arrivalTime));

        int total_waiting_time = 0;
        int total_burst_time = 0;
        int total_turnaround_time = 0;
        int inserted_process = 0;
        int[] heap_size = { 0 };
        int current_time = array[0].arrivalTime;
        int total_response_time = 0;

        Process[] Heap = new Process[4 * n];

        // Calculating the total burst time of the processes
        for (int i = 0; i < n; i++) {
            total_burst_time += array[i].burstTime;
            array[i].tempburstTime = array[i].burstTime;
        }

        // Inserting the processes into Heap according to arrival time
        while (true) {
            if (inserted_process != n) {
                for (int i = 0; i < n; i++) {
                    if (array[i].arrivalTime == current_time) {
                        inserted_process++;
                        array[i].intime = -1;
                        array[i].responsetime = -1;
                        insert(Heap, array[i], heap_size, new int[] { current_time });
                    }
                }
            }
            scheduling(Heap, array, n, heap_size, new int[] { current_time });
            current_time++;
            if (heap_size[0] == 0 && inserted_process == n)
                break;
        }

        for (int i = 0; i < n; i++) {
            total_response_time += array[i].responsetime;
            total_waiting_time += (array[i].outtime - array[i].intime - array[i].tempburstTime);
            total_turnaround_time += (array[i].outtime - array[i].intime);
            total_burst_time += array[i].burstTime;
        }

        System.out.println("Average waiting time = " + ((float) total_waiting_time / n));
        System.out.println("Average response time = " + ((float) total_response_time / n));
        System.out.println("Average turn around time = " + ((float) total_turnaround_time / n));
    }

    // Driver code
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();
        Process[] processes = new Process[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Enter details for process " + (i + 1) + ":");
            System.out.print("Process ID: ");
            int processID = scanner.nextInt();
            System.out.print("Arrival Time: ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Priority: ");
            int priority = scanner.nextInt();
            System.out.print("Burst Time: ");
            int burstTime = scanner.nextInt();
            processes[i] = new Process(processID, arrivalTime, priority, burstTime);
        }
        priority(processes, n);
        scanner.close();
    }
}