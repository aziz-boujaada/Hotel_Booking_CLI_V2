package Repositories.impl;

import Config.DatabaseConfig;
import Helpers.SearchByParam;
import Helpers.ShowAll;
import Mappers.ReservationMapper;
import Mappers.RoomMapper;
import Models.Reservation;
import Models.User;
import Repositories.ReservationRepository;
import Repositories.RoomRepository;
import Repositories.UserRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


public class JdbcReservationRepo implements ReservationRepository {

    private static final HashMap<String, Reservation> reservations = new HashMap<>();

    private final DatabaseConfig databaseConfig;
    private final ReservationMapper reservationMapper;

    public JdbcReservationRepo(
            DatabaseConfig databaseConfig ,
            UserRepository userRepository ,
            RoomRepository roomRepository
    ) {
        this.databaseConfig = databaseConfig;
        this.reservationMapper = new ReservationMapper(userRepository , roomRepository);
    }

    public Reservation save(Reservation reservation) {
        String query = """
                INSERT INTO reservations(reservation_id , client_id , room_id , check_in , check_out , nights , total , status , person_numbers)
                VALUES(?,?,?,?,?,?,?,?,?)
                """;

        try (
                Connection connection = databaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {

           statement.setString(1, reservation.getReservationID());
           statement.setString(2, reservation.getClient().getId());
           statement.setString(3, reservation.getRoom().getIdentify());
           statement.setDate(4, Date.valueOf(reservation.getCheckIn()));
           statement.setDate(5, Date.valueOf(reservation.getCheckOut()));
           statement.setLong(6, reservation.getNights());
           statement.setDouble(7, reservation.getTotal());
           statement.setString(8, reservation.getStatus().name());
           statement.setInt(9, reservation.getPersonneNumbers());

           statement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException("Failed to Save Reservation " + e.getMessage() , e);
        }
        return reservation;
    }
    @Override
    public Optional<Reservation> findById(String reservationId)
    {
        String query = """
            SELECT * FROM reservations WHERE reservation_id = ?
            """;
        return SearchByParam.searchByParameter(reservationId , query , databaseConfig , reservationMapper::map);
    }

    public List<Reservation> findByRoomId(String roomId) {
        return reservations.values().stream()
                .filter(reservation -> reservation.getRoom().getIdentify().equals(roomId))
                .toList();
    }

    public List<Reservation> myReservations(User loggedUser) {
        String clientId = loggedUser.getId();
        String query = """
                SELECT * FROM reservations WHERE client_id = ?
                """;
        return ShowAll.showAll(clientId , query , databaseConfig, reservationMapper::map);
    }

    public Reservation update(Reservation reservation) {
        if (reservation == null || reservation.getReservationID() == null) {
            return null;
        }
        reservations.put(reservation.getReservationID(), reservation);
        return reservation;
    }

    public void delete(String reservationId) {
        reservations.remove(reservationId);
    }
}
