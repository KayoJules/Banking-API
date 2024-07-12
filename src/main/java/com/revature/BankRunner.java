package com.revature;

import com.revature.Account.*;
import com.revature.User.*;
import com.revature.util.auth.*;

import java.util.Scanner;
import java.sql.Connection;


public class BankRunner
{
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        AuthService authService = new AuthService(userRepository);
        AuthController authController = new AuthController(authService);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to our bank!");
            System.out.println("1. Sign in");
            System.out.println("2. Create login");
            System.out.println("3. Create account");
            System.out.println("4. Check balance");
            System.out.println("5. Make a deposit");
            System.out.println("6. Make a withdrawal");
            System.out.println("7. View transaction log");
            System.out.println("8. Exit");
            System.out.println();
            System.out.print("Enter your numeric choice from above: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    authController.login(scanner);
                    break;
                case 2:
                    authController.register(scanner);
                    break;
                case 3:
                    // create account logic
                    break;
                case 4:
                    // check balance logic
                    break;
                case 5:
                    // make deposit logic
                    break;
                case 6:
                    // make withdrawal logic
                    break;
                case 7:
                    // view transaction log logic
                    break;
                case 8:
                    System.out.println("Exiting the application...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
