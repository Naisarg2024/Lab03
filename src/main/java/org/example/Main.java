package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for their choice
        System.out.println("Please select which application version to run:");
        System.out.println("1: Legacy JPA with MySQL Database (App)");
        System.out.println("2: In-Memory HashMap Repository (App2)");
        System.out.println("3: H2 In-Memory DB Repository (App3)");
        System.out.println("4: MySQL DB Repository (App4)");
        System.out.println("5: Service Layer Demonstration (H2 & MySQL) (App5)"); // New option
        System.out.print("Enter your choice (1-5): ");

        // Read the user's input
        String choice = scanner.nextLine();

        // Use a switch statement to determine which app to run
        switch (choice) {
            case "1":
                System.out.println("\n--- Running Legacy JPA with MySQL Database (App) ---");
                new App1().run();
                break;
            case "2":
                System.out.println("\n--- Running In-Memory HashMap Repository (App2) ---");
                new App2().run();
                break;
            case "3":
                System.out.println("\n--- Running H2 In-Memory Database Repository (App3) ---");
                new App3().run();
                break;
            case "4":
                System.out.println("\n--- Running MySQL Database Repository (App4) ---");
                new App4().run();
                break;
            case "5":
                System.out.println("\n--- Running Service Layer Demonstration (App5) ---");
                new App5().run();
                break;
            default:
                // Handle invalid input
                System.out.println("\nInvalid choice. Please run the program again and enter a number from 1 to 5.");
                break;
        }

        // It's good practice to close the scanner when it's no longer needed
        scanner.close();
    }
}

// port : 3333
