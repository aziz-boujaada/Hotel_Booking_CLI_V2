package Mappers;

import Enums.ReservationStatus;
import Enums.RoomStatus;
import Enums.RoomType;
import Models.Reservation;
import Models.Room;
import Models.User;
import Repositories.RoomRepository;
import Repositories.UserRepository;
import Repositories.impl.JdbcReservationRepo;
import Repositories.impl.JdbcUserRepo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationMapper {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public ReservationMapper(
            UserRepository userRepository,
            RoomRepository roomRepository
    ) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public  Reservation map(ResultSet resultSet)throws SQLException {

        String clientId = resultSet.getString("client_id");
        String roomId = resultSet.getString("room_id");

        User client = userRepository.findById(clientId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found: " + clientId)
                );

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Room not fount : " + roomId)
                );
        return new Reservation(
                resultSet.getString("reservation_id"),
                client,
                room,
                resultSet.getDate("check_in").toLocalDate(),
                resultSet.getDate("check_out").toLocalDate(),
                resultSet.getInt("nights"),
                resultSet.getDouble("total"),
                resultSet.getInt("person_numbers"),
                ReservationStatus.valueOf(resultSet.getString("status"))

        );
    }

}
