import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

class LRUPage {
    // Method to find page faults using indexes
    static int pageFaults(int pages[], int n, int capacity) {
        // To represent set of current pages. We use
        // a HashSet so that we can quickly check
        // if a page is present in the set or not
        HashSet<Integer> s = new HashSet<>(capacity);

        // To store least recently used indexes of pages
        HashMap<Integer, Integer> indexes = new HashMap<>();

        // Start from initial page
        int page_faults = 0;
        for (int i = 0; i < n; i++) {
            // Check if the set can hold more pages
            if (s.size() < capacity) {
                // Insert it into the set if not already present
                // which represents a page fault
                if (!s.contains(pages[i])) {
                    s.add(pages[i]);

                    // Increment page fault count
                    page_faults++;
                }

                // Store the recently used index of each page
                indexes.put(pages[i], i);
            }

            // If the set is full, then perform LRU:
            // remove the least recently used page and insert the current page
            else {
                // Check if the current page is not already present in the set
                if (!s.contains(pages[i])) {
                    // Find the least recently used page that is present in the set
                    int lru = Integer.MAX_VALUE, val = Integer.MIN_VALUE;

                    Iterator<Integer> itr = s.iterator();

                    while (itr.hasNext()) {
                        int temp = itr.next();
                        if (indexes.get(temp) < lru) {
                            lru = indexes.get(temp);
                            val = temp;
                        }
                    }

                    // Remove the least recently used page from the set and hashmap
                    s.remove(val);
                    indexes.remove(val);
                    // Insert the current page into the set
                    s.add(pages[i]);

                    // Increment page fault count
                    page_faults++;
                }

                // Update the index of the current page
                indexes.put(pages[i], i);
            }
        }

        return page_faults;
    }

    // Driver method
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of pages:");
        int n = scanner.nextInt();

        int pages[] = new int[n];
        System.out.println("Enter the page sequence:");
        for (int i = 0; i < n; i++) {
            pages[i] = scanner.nextInt();
        }

        System.out.println("Enter the cache capacity:");
        int capacity = scanner.nextInt();

        System.out.println("Total page faults using LRU: " + pageFaults(pages, n, capacity));

        scanner.close();
    }
}