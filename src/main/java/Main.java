import Config.DatabaseConfig;
import  ConsoleUI.AuthMenu;
import  ConsoleUI.RoomManagmentMenu;
import Models.Payment;
import Repositories.PaymentRepository;
import Repositories.UserRepository;
import Repositories.impl.*;
import  Services.AuthService;
import  Services.ReservationService;
import  Services.RoomService;
import  Utils.DatesUtil;
import  Utils.InputsUtil;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        DatabaseConfig databaseConfig = DatabaseConfig.getInstance();

        JdbcUserRepo  userRepository = new JdbcUserRepo(databaseConfig) ;

        AuthService authService = new AuthService(databaseConfig, userRepository);

        JdbcRoomRepo roomRepo = new JdbcRoomRepo(databaseConfig);
        JdbcReservationRepo reservationRepo = new JdbcReservationRepo(databaseConfig,userRepository,roomRepo);

        PaymentJdbc paymentJdbc = new PaymentJdbc();


        ReservationTransactionsJdbc reservationTransactions = new ReservationTransactionsJdbc(reservationRepo ,paymentJdbc , databaseConfig);
        DatesUtil datesUtil = new DatesUtil();
        RoomService roomService = new RoomService(roomRepo);

        ReservationService reservationService = new ReservationService(
                authService,
                reservationRepo,
                roomRepo,
                datesUtil,
                databaseConfig
        );

        InputsUtil inputsUtil = new InputsUtil(
                authService,
                reservationService,
                roomService,
                reservationTransactions
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