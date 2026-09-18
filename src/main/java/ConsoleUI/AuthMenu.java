package main.java.ConsoleUI;

import main.java.Models.User;
import main.java.Repositories.impl.InMemoryReservationRepo;
import main.java.Repositories.impl.InMemoryRoomRepo;
import main.java.Services.AuthService;
import main.java.Services.ReservationService;
import main.java.Services.RoomService;
import main.java.Utils.InputsUtil;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AuthMenu {

    private final AuthService authService;
    private final InputsUtil inputsUtil;
    private final InMemoryRoomRepo roomRepo;
    private final RoomService roomService;
    private final InMemoryReservationRepo reservationRepo;
    private final ReservationService reservationService;
    private final RoomManagmentMenu roomManagmentMenu;


    public AuthMenu(
            AuthService authService,
            InputsUtil inputsUtil,
            InMemoryRoomRepo roomRepo,
            RoomService roomService,
            InMemoryReservationRepo reservationRepo,
            ReservationService reservationService,
            RoomManagmentMenu roomManagmentMenu
    ) {
        this.authService = authService;
        this.inputsUtil = inputsUtil;
        this.roomRepo = roomRepo;
        this.roomService = roomService;
        this.reservationRepo = reservationRepo;
        this.reservationService = reservationService;
        this.roomManagmentMenu = roomManagmentMenu;

    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("========================== Auth Menu =========================");
                System.out.println("1- Login");
                System.out.println("2- Register");
                System.out.print("Enter Your Choice : ");

                int userChoice = scanner.nextInt();

                switch (userChoice) {
                    case 1:
                        User loggedUser = inputsUtil.loginForm();
                        if (loggedUser == null) {
                            break;
                        }

                        MainMenu mainMenu = new MainMenu(
                                loggedUser,
                                authService,
                                roomRepo,
                                roomService,
                                reservationRepo,
                                reservationService,
                                inputsUtil,
                                roomManagmentMenu
                        );

                        boolean loggedOut = mainMenu.menu();
                        if (loggedOut) {
                            System.out.println("Returning to auth menu...");
                            continue;
                        }
                        return;
                    case 2:
                        inputsUtil.registerForm();
                        break;
                    default:
                        System.out.println("Invalid Choice. Please choose 1 or 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("Input is unavailable. Please restart the application.");
                return;
            }
        }
    }
}
