import java.util.Scanner;

// Singleton MailBox class
class MailBox {
    // Private static instance variable
    private static MailBox instance;

    // Private constructor to prevent instantiation from outside
    private MailBox() {
    }

    // Public static method to get the instance
    public static MailBox getInstance() {
        if (instance == null) {
            instance = new MailBox();
        }
        return instance;
    }

    // Method to handle incoming mail
    public void receiveMail(String message) {
        System.out.println("Received mail: " + message);
    }
}

// Main class to demonstrate usage
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user to enter mail message
        System.out.println("Enter your mail message:");
        String message = scanner.nextLine();

        // Get the instance of the Singleton MailBox
        MailBox mailBox = MailBox.getInstance();

        // Use the mailBox instance to receive mail
        mailBox.receiveMail(message);

        scanner.close();
    }
}