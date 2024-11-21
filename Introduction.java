import java.io.*;
import java.util.Scanner;

public class Introduction {
    public static void main(String[] args) {
        SocialNetwork network = new SocialNetwork();
        Scanner scanner = new Scanner(System.in);
        
        try {
            network.buildGraph("students.csv", "network.csv");
            boolean running = true;
            while (running) {
                System.out.println("Choose an option:");
                System.out.println("1. Print network list for a student");
                System.out.println("2. Find quickest path between two students");
                System.out.println("3. Disconnect a student from another's network");
                System.out.println("4. Increase wait days for a student");
                System.out.println("5. Decrease wait days for a student");
                System.out.println("6. Exit");

                int choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number between 1 and 6.");
                    continue; // Restart the loop to prompt the user again
                }
                
                switch (choice) {
                    case 1:
                        System.out.print("Enter student ID: ");
                        try {
                            int student = Integer.parseInt(scanner.nextLine().trim());
                            System.out.println("Network for student " + student + ": " + network.getNetwork(student));
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid student ID. Please enter a valid integer.");
                        }
                        break;
                    case 2:
                        try {
                            System.out.print("Enter start student ID: ");
                            int start = Integer.parseInt(scanner.nextLine().trim());
                            System.out.print("Enter end student ID: ");
                            int end = Integer.parseInt(scanner.nextLine().trim());
                            SocialNetwork.Path path = network.findQuickestPath(start, end);
                            System.out.println("Quickest path takes " + path.totalDays + " days with " + path.nodeCount + " nodes.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter valid integer IDs for students.");
                        }
                        break;
                    case 3:
                        try {
                            System.out.print("Enter student ID to disconnect from: ");
                            int a = Integer.parseInt(scanner.nextLine().trim());
                            System.out.print("Enter student ID to disconnect: ");
                            int b = Integer.parseInt(scanner.nextLine().trim());
                            network.disconnect(a, b);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter valid integer IDs for students.");
                        }
                        break;
                    case 4:
                        System.out.print("Enter student ID: ");
                        try {
                            int student = Integer.parseInt(scanner.nextLine().trim());
                            network.increaseWaitDays(student);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid student ID. Please enter a valid integer.");
                        }
                        break;
                    case 5:
                        System.out.print("Enter student ID: ");
                        try {
                            int student = Integer.parseInt(scanner.nextLine().trim());
                            network.decreaseWaitDays(student);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid student ID. Please enter a valid integer.");
                        }
                        break;
                    case 6:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
        } 
            scanner.close();
        
    }
}
