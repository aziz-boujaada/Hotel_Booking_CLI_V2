import Config.DatabaseConfig;
import  ConsoleUI.AuthMenu;
import  ConsoleUI.RoomManagmentMenu;
import Repositories.impl.JdbcReservationRepo;
import Repositories.impl.JdbcRoomRepo;
import  Services.AuthService;
import  Services.ReservationService;
import  Services.RoomService;
import  Utils.DatesUtil;
import  Utils.InputsUtil;

public class Main {
    public static void main(String[] args) {
        DatabaseConfig databaseConfig = DatabaseConfig.getInstance();
        AuthService authService = new AuthService(databaseConfig);

        JdbcRoomRepo roomRepo = new JdbcRoomRepo(databaseConfig);
        JdbcReservationRepo reservationRepo = new JdbcReservationRepo();

        DatesUtil datesUtil = new DatesUtil();
        RoomService roomService = new RoomService(roomRepo);

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