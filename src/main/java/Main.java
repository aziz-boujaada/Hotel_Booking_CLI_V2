package main.java;

import main.java.ConsoleUI.AuthMenu;
import main.java.ConsoleUI.RoomManagmentMenu;
import main.java.Repositories.impl.InMemoryReservationRepo;
import main.java.Repositories.impl.InMemoryRoomRepo;
import main.java.Services.AuthService;
import main.java.Services.ReservationService;
import main.java.Services.RoomService;
import main.java.Utils.DatesUtil;
import main.java.Utils.InputsUtil;

public class Main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();

        InMemoryRoomRepo roomRepo = new InMemoryRoomRepo();
        InMemoryReservationRepo reservationRepo = new InMemoryReservationRepo();

        DatesUtil datesUtil = new DatesUtil();
        RoomService roomService = new RoomService();

        ReservationService reservationService = new ReservationService(
                authService,
                reservationRepo,
                roomRepo,
                datesUtil
        );

        InputsUtil inputsUtil = new InputsUtil(
                authService,
                reservationService,
                roomService
        );

        RoomManagmentMenu roomManagmentMenu = new RoomManagmentMenu(
                authService,
                inputsUtil
        );

        AuthMenu authMenu = new AuthMenu(
                authService,
                inputsUtil,
                roomRepo,
                roomService,
                reservationRepo,
                reservationService,
                roomManagmentMenu
        );

        authMenu.showMenu();
    }
}