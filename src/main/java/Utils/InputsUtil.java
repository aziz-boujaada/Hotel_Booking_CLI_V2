package Utils;

import Enums.*;
import Helpers.InputsReader;
import Models.Payment;
import Models.Reservation;
import Models.Room;
import Models.User;
import Repositories.PaymentRepository;
import Repositories.ReservationTransactions;
import Repositories.impl.ReservationTransactionsJdbc;
import Services.AuthService;
import Services.PaymentService;
import Services.ReservationService;
import Services.RoomService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class InputsUtil {

    private final AuthService authService;
    private final RoomService roomService;
    private final ReservationService reservationService;
    private final PaymentService paymentService;
    private final ReservationTransactionsJdbc reservationTransactions;
    private final InputsReader inputsReader;
    private final Scanner scanner;

    public InputsUtil(
            AuthService authService,
            ReservationService reservationService,
            RoomService roomService ,
            ReservationTransactionsJdbc reservationTransactions

    ) {
        this.authService = authService;
        this.roomService = roomService;
        this.reservationService = reservationService;
        this.inputsReader = new InputsReader();
        this.paymentService = new PaymentService();
        this.reservationTransactions = reservationTransactions;

        this.scanner = new Scanner(System.in);
    }


    public void registerForm() {
        while (true) {
            try {
                System.out.println("======= Register ======");

                String fullname = inputsReader.readRequiredLine("Enter Your full name: ");
                String email = inputsReader.readRequiredLine("Enter Your email: ");
                String phone = inputsReader.readRequiredLine("Enter Your phone: ");
                String password = inputsReader.readRequiredLine("Enter Your password: ");

                UserRole role;
                System.out.println("---- choice Role ----");
                System.out.println("1- ADMIN");
                System.out.println("2- CLIENT");
                int choiceRole = inputsReader.readIntWithRange("Select role: ", 1, 2);

                switch (choiceRole) {
                    case 1 -> role = UserRole.ADMIN;
                    case 2 -> role = UserRole.CLIENT;
                    default -> throw new IllegalArgumentException("invalid role");
                }

                User user = authService.register(fullname, email, phone, password, role);
                System.out.println("\n===== User Registered =====");
                System.out.println(user.toString());

                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Validation error: " + e.getMessage());
                System.out.println("Please try again.\n");
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                System.out.println("Please try again.\n");
            }
        }
    }

    public User loginForm() {
        while (true) {
            try {
                System.out.println("======= Login ======");

                String email = inputsReader.readRequiredLine("Enter Your email: ");
                String password = inputsReader.readRequiredLine("Enter Your password: ");

                User user = authService.login(email, password);
                System.out.println("\n===== Login successfully =====");
                System.out.println(user.toString());
                return user;

            } catch (IllegalArgumentException e) {
                System.out.println("Login failed: " + e.getMessage());
                System.out.println("Please try again.\n");
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                System.out.println("Please try again.\n");
            }
        }
    }

    public void changePasswordForm(User loggedUser) {
        while (true) {
            try {
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();
                authService.changePassword(loggedUser, newPassword);
                System.out.println("Password updated successfully!");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Password update failed: " + e.getMessage());
                System.out.println("Please try again.\n");
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                System.out.println("Please try again.\n");
            }
        }
    }

    public void updateProfileForm(User loggedUser) {
        while (true) {
            try {
                System.out.println("======= Update Profile ======");
                String fullName = inputsReader.readRequiredLine("Enter your full name: ");
                String email = inputsReader.readRequiredLine("Enter your email: ");
                String phone = inputsReader.readRequiredLine("Enter your phone: ");

                User updatedUser = authService.updateProfile(loggedUser, fullName, email, phone);
                System.out.println("Profile updated successfully");
                System.out.println(updatedUser);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Profile update failed: " + e.getMessage());
                System.out.println("Please try again.\n");
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                System.out.println("Please try again.\n");
            }
        }
    }

    public void addRoomForm() {
        while (true) {
            try {
                System.out.println("======= Add New Room ======");

                RoomType roomType;
                RoomStatus roomStatus;

                System.out.println("---- choice Room Type  ----");
                System.out.println("1- Single");
                System.out.println("2- Double");
                System.out.println("3- Suite");
                int choiceRoomType = inputsReader.readIntWithRange("Select room type: ", 1, 3);

                switch (choiceRoomType) {
                    case 1 -> roomType = RoomType.SINGLE;
                    case 2 -> roomType = RoomType.DOUBLE;
                    case 3 -> roomType = RoomType.SUITE;
                    default -> throw new IllegalArgumentException("invalid room type");
                }

                int capacity = inputsReader.readPositiveInt("Enter capacity of room: ");
                double nightPrice = inputsReader.readDouble("Enter night price: ");

                System.out.println("---- choice Room Status  ----");
                System.out.println("1- Available");
                System.out.println("2- In Repair");
                int choiceRoomStatus = inputsReader.readIntWithRange("Select room status: ", 1, 2);

                switch (choiceRoomStatus) {
                    case 1 -> roomStatus = RoomStatus.AVAILABLE;
                    case 2 -> roomStatus = RoomStatus.IN_REPAIR;
                    default -> throw new IllegalArgumentException("invalid room status");
                }

                Room room = roomService.addRoom(roomType, nightPrice, capacity, roomStatus);
                System.out.println("Room created Successfully");
                System.out.println(room.toString());
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Room creation failed: " + e.getMessage());
                System.out.println("Please try again.\n");
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                System.out.println("Please try again.\n");
            }
        }
    }

    public void addReservationForm() {

        try {
            System.out.println("====== Add New Reservation =====");

            String roomID = inputsReader.readRequiredLine("Enter Room ID: ");
            String checkIn = inputsReader.readRequiredLine("Enter Check-In Date: ");
            String checkOut = inputsReader.readRequiredLine("Enter Check-Out Date: ");
            int personsNumber = inputsReader.readPositiveInt("Enter number of persons: ");

            Reservation reservation = reservationService.addNewReservation(roomID, checkIn, checkOut, personsNumber);

            PaymentMethod paymentMethod =  paymentForm();
            Payment payment = paymentService.createPayment(reservation , paymentMethod);

            // save reservation and payment in one transaction
            reservationTransactions.executeTransaction(reservation , payment);

            System.out.println("Reservation created Successfully");
            System.out.println(reservation.toString());
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Reservation failed: " + e.getMessage());
            System.out.println("Please try again.\n");
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            System.out.println("Please try again.\n");
        }

    }

    public void updateReservationForm(User loggedUser) {
        while (true) {
            try {
                System.out.println("====== Update Reservation =====");
                List<Reservation> reservations = reservationService.getMyReservations(loggedUser);

                if (reservations.isEmpty()) {
                    System.out.println("You have no reservations to update.");
                    return;
                }

                String reservationId = inputsReader.readRequiredLine("Enter Reservation ID: ");


                String checkIn = inputsReader.readRequiredLine("Enter new Check-In Date: ");
                String checkOut = inputsReader.readRequiredLine("Enter new Check-Out Date: ");
                int personsNumber = inputsReader.readPositiveInt("Enter new number of persons: ");

                Reservation updated = reservationService.updateReservation(
                        reservationId,
                        checkIn,
                        checkOut,
                        personsNumber
                );

                System.out.println("Reservation updated successfully.");
                System.out.println(updated);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Update failed: " + e.getMessage());
                System.out.println("Please try again.\n");
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                System.out.println("Please try again.\n");
            }
        }
    }

    public void cancelReservationForm(User loggedUser) {
        while (true) {
            try {
                System.out.println("====== Cancel Reservation =====");
                List<Reservation> reservations = reservationService.getMyReservations(loggedUser);

                if (reservations.isEmpty()) {
                    System.out.println("You have no reservations to cancel.");
                    return;
                }

                String reservationId = inputsReader.readRequiredLine("Enter Reservation ID: ");


                boolean cancelled = reservationService.cancelReservation(reservationId);
                if (cancelled) {
                    System.out.println("Reservation cancelled successfully.");
                    return;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Cancellation failed: " + e.getMessage());
                System.out.println("Please try again.\n");
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                System.out.println("Please try again.\n");
            }
        }
    }

    public PaymentMethod paymentForm(){

        System.out.println("=============== Chose Payment Method =============");

        System.out.println("1- Cash");
        System.out.println("2- bank transfer ");

        int choicePaymentMethod = inputsReader.readIntWithRange("Select payment method : ", 1, 2);

      return  switch (choicePaymentMethod) {
            case 1 ->  PaymentMethod.CASH;
            case 2 ->  PaymentMethod.BANK_TRANSFER;
            default -> throw new IllegalArgumentException("invalid payment method");
        };
    }
}
