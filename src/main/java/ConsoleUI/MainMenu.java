package ConsoleUI;

import Midllewares.AdminMiddleware;
import  Models.Reservation;
import  Models.Room;
import  Models.User;
import Repositories.impl.JdbcReservationRepo;
import Repositories.impl.JdbcRoomRepo;
import  Services.AuthService;
import  Services.ReservationService;
import  Services.RoomService;
import  Utils.InputsUtil;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private final AuthService authService;
    private final User loggedUser;
    private final InputsUtil inputsUtil;
    private final AdminMiddleware adminMiddleware;
    private final JdbcRoomRepo roomRepo;
    private final RoomService roomService;
    private final ReservationService reservationService;
    private final JdbcReservationRepo reservationRepository;
    private final RoomManagmentMenu roomManagmentMenu;
    private final Scanner scanner;


    public MainMenu(
                    User loggedUser,
                    AuthService authService ,
                    JdbcRoomRepo inMemoryRoomRepo ,
                    RoomService roomService,
                    JdbcReservationRepo reservationRepo,
                    ReservationService reservationService,
                    InputsUtil inputsUtil,
                    RoomManagmentMenu roomManagmentMenu

    ) {
        this.authService = authService;
        this.loggedUser = loggedUser;
        this.adminMiddleware = new AdminMiddleware();
        this.roomRepo = inMemoryRoomRepo;
        this.roomService = roomService;
        this.reservationRepository = reservationRepo;
        this.reservationService = reservationService;
        this.inputsUtil = inputsUtil;
        this.roomManagmentMenu = roomManagmentMenu;

        this.scanner = new Scanner(System.in);
    }

    private int readMenuChoice() {
        while (true) {
            try {
                System.out.print("Enter Your Choice : ");
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    public boolean menu() {
        int userChoice;

        do {
            System.out.println();
            System.out.println("===== Room Reservation System =====");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. View All Rooms");
            System.out.println("3. Create Reservation");
            System.out.println("4. My Reservations");
            System.out.println("5. Reservation Details");
            System.out.println("6. Update Reservation");
            System.out.println("7. Cancel Reservation");
            System.out.println("8. Update Profile");
            System.out.println("9. Change Password");
            System.out.println("10. Rooms Management");
            System.out.println("11. Logout");
            System.out.println("===================================");

            userChoice = readMenuChoice();

            try {
                switch (userChoice) {
                    case 1:
                        System.out.println("Search Available Rooms coming soon");
                        List<Room> availableRooms = roomService.getAvailableRooms();

                        if (availableRooms.isEmpty()) {
                            System.out.println("No rooms found.");
                            break;
                        }

                        for (Room room : availableRooms) {
                            System.out.println(room);
                        }
                        break;

                    case 2:
                        List<Room> rooms = roomRepo.showAllRooms();

                        if (rooms.isEmpty()) {
                            System.out.println("No rooms found.");
                            break;
                        }

                        for (Room room : rooms) {
                            System.out.println(room);
                        }
                        break;

                    case 3:
                        System.out.println("Create reservation coming soon");
                        inputsUtil.addReservationForm();
                        break;

                    case 4:
                        System.out.println("My reservations coming soon");

                        List<Reservation> myReservations = reservationRepository.myReservations(loggedUser);
                        if(myReservations.isEmpty()){
                            System.out.println("No reservations ");
                            break;
                        }
                        for(Reservation reservation : myReservations){
                            System.out.println(reservation.toString());
                        }
                            break;

                    case 5:
                        System.out.println("Reservation details coming soon");
                        break;

                    case 6:
                        inputsUtil.updateReservationForm(loggedUser);
                        break;

                    case 7:
                        inputsUtil.cancelReservationForm(loggedUser);
                        break;

                    case 8:
                        inputsUtil.updateProfileForm(loggedUser);
                        break;

                    case 9:
                        inputsUtil.changePasswordForm(loggedUser);
                        break;

                    case 10:
                        if (adminMiddleware.isAdmin(loggedUser)) {
                            roomManagmentMenu.roomMenu();
                        } else {
                            System.out.println("Access denied. Admin only.");
                        }
                        break;

                    case 11:
                        authService.logout(loggedUser);
                        System.out.println("Goodbye!");
                        return true;

                    default:
                        System.out.println("Invalid choice. Please choose between 1 and 11.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (userChoice != 11);

        return false;
    }
}
