package main.java.ConsoleUI;

import main.java.Services.AuthService;
import main.java.Utils.InputsUtil;

import java.util.InputMismatchException;
import java.util.Scanner;

public class RoomManagmentMenu {

    private final InputsUtil inputsUtil;
    private final Scanner scanner;

    public RoomManagmentMenu(AuthService authService , InputsUtil inputsUtil) {
        this.inputsUtil = inputsUtil;
        this.scanner = new Scanner(System.in);
    }

    public void roomMenu() {
        int userChoice;

        do {
            try {
                System.out.println("========== Rooms Management ===========");
                System.out.println("1- Add new room ");
                System.out.println("2- Delete room ");
                System.out.println("3- Show room ");
                System.out.println("0- Exit ");
                System.out.print("Enter your choice : ");

                userChoice = scanner.nextInt();

                switch (userChoice) {
                    case 1:
                        inputsUtil.addRoomForm();
                        break;
                    case 2:
                        System.out.println("Delete room method not implemented yet.");
                        break;
                    case 3:
                        System.out.println("Show room method not implemented yet.");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
                userChoice = -1;
            }
        } while (userChoice != 0);
    }
}
