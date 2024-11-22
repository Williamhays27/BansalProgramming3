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
                        while(true){
                        System.out.print("Enter student ID: ");
                        try {
                            int student = Integer.parseInt(scanner.nextLine().trim());
                            if(student < 0 || student > 114){
                                continue;
                            }
                            System.out.println("Network for student " + student + ": " + network.getNetwork(student));
                            break; //break out of loop if valid input
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid student ID. Please enter a valid integer between 1 and 114.");
                        }
                    }
                        break;
                    case 2:
                        while(true){
                        try {
                            System.out.print("Enter start student ID between 1 and 114: ");
                            int start = Integer.parseInt(scanner.nextLine().trim());
                            if(start < 0 || start > 114){
                                continue;
                            }
                            System.out.print("Enter end student ID between 1 and 114: ");
                            int end = Integer.parseInt(scanner.nextLine().trim());
                            if(end < 0 || end > 114){
                                continue;
                            }
                            SocialNetwork.Path path = network.findQuickestPath(start, end);
                            System.out.println("Quickest path takes " + path.totalDays + " days with " + path.nodeCount + " nodes.");
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter valid integer IDs for students between 1 and 114.");
                        }
                        }
                        break;
                    case 3:
                        while(true)
                        try {
                            System.out.print("Enter student ID to disconnect from between 1 and 114: ");
                            int a = Integer.parseInt(scanner.nextLine().trim());
                            if(a < 0 || a > 114){
                                continue;
                            }
                            System.out.print("Enter student ID to disconnect between 1 and 114: ");
                            int b = Integer.parseInt(scanner.nextLine().trim());
                            if(b < 0 || b > 114){
                                continue;
                            }
                            network.discon(a, b);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter valid integer IDs for students between 1 and 114.");
                        }
                        break;
                    case 4:
                        while(true){
                        System.out.print("Enter student ID: ");
                        try {
                            int student = Integer.parseInt(scanner.nextLine().trim());
                            if(student < 0 || student > 114){
                                continue;
                            }
                            network.iWaitDays(student);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid student ID. Please enter a valid integer between 1 and 114.");
                        }
                        }
                        break;
                    case 5:
                        while(true){
                        System.out.print("Enter student ID between 1 and 114: ");
                        try {
                            int student = Integer.parseInt(scanner.nextLine().trim());
                            if(student < 0 || student > 114){
                                continue;
                            }
                            network.dWaitDays(student);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid student ID. Please enter a valid integer between 1 and 114.");
                        }
                        }
                        break;
                    case 6:
                        running = false;
                        System.out.println("Thanks for using our software.");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
        } finally{
            scanner.close();
        }
    }
}
