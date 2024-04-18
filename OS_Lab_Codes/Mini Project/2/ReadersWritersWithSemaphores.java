import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class ReadersWritersWithSemaphores {
    public static void main(String[] args) {
        try {
            // Initialize necessary variables and objects
            Robot robot = new Robot(); // Used for simulating keyboard input
            Scanner scanner = new Scanner(System.in);
            List<Integer> runningProcesses = new ArrayList<>(); // Tracks running processes' IDs
            Map<Integer, String> processNames = new HashMap<>(); // Maps process IDs to their names
            Semaphore readSemaphore = new Semaphore(3); // Semaphore for controlling max concurrent readers
            Semaphore writeSemaphore = new Semaphore(1); // Semaphore for controlling max concurrent writers
            boolean writingProcessActive = false; // Flag to track if a writing process is active
            int processIdCounter = 1; // Counter for assigning unique process IDs

            // Main program loop
            while (true) {
                System.out.println("Press 1 to start reading process");
                System.out.println("Press 2 to start writing process");
                System.out.println("Press X to terminate a process by its ID");
                System.out.println("Press Q to exit");

                String input = scanner.nextLine().toLowerCase();
                switch (input) {
                    case "1":
                        // Start a reading process
                        String readingProcessName = "ReadingProcess_" + processIdCounter;
                        processNames.put(processIdCounter, readingProcessName);
                        System.out.println(readingProcessName + " started reading");
                        runningProcesses.add(processIdCounter++);
                        readSemaphore.acquire(); // Acquire read lock
                        break;
                    case "2":
                        // Start a writing process
                        if (!writingProcessActive && writeSemaphore.tryAcquire()) {
                            writingProcessActive = true;
                            String writingProcessName = "WritingProcess_" + processIdCounter;
                            processNames.put(processIdCounter, writingProcessName);
                            System.out.println(writingProcessName + " started writing");
                            runningProcesses.add(processIdCounter++);
                        } else {
                            System.out.println(
                                    "Another writing process is already active. Cannot start writing process.");
                        }
                        break;
                    case "x":
                        // Terminate a process by its ID
                        if (runningProcesses.isEmpty()) {
                            System.out.println("No running processes to terminate.");
                        } else {
                            System.out.println("Running processes:");
                            for (Integer processId : runningProcesses) {
                                System.out
                                        .println("Process ID: " + processId + ", Name: " + processNames.get(processId));
                            }
                            System.out.println("Enter the process ID to terminate:");
                            int processId = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            if (runningProcesses.contains(processId)) {
                                runningProcesses.remove(Integer.valueOf(processId));
                                System.out.println("Terminating process with ID: " + processId + ", Name: "
                                        + processNames.get(processId));
                                if (processId != 1) {
                                    readSemaphore.release(); // Release read lock for non-reading processes
                                }
                                if (processId == 2) {
                                    writingProcessActive = false; // Reset writing process flag
                                    writeSemaphore.release(); // Release write lock
                                }
                            } else {
                                System.out.println("Invalid process ID");
                            }
                        }
                        break;
                    case "q":
                        // Exit the program
                        System.exit(0);
                    default:
                        // Invalid input
                        System.out.println("Invalid input");
                }
                System.out.println(); // Add a blank line for clearer output
            }

        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}