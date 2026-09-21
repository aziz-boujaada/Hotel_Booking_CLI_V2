package Repositories.impl;

import Config.DatabaseConfig;
import Mappers.RoomMapper;
import Models.Room;
import Repositories.RoomRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class JdbcRoomRepo implements RoomRepository {

    private final DatabaseConfig databaseConfig;

    public JdbcRoomRepo(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    private static final HashMap<String, Room> rooms = new HashMap<>();

    @Override
    public Room addNewRoom(Room room) {

        String sql = """
                INSERT INTO rooms(room_id , room_type , night_price , capacity , room_status)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = databaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, room.getIdentify());
            statement.setString(2, room.getRoomType().name());
            statement.setDouble(3, room.getNightPrice());
            statement.setInt(4, room.getCapacity());
            statement.setString(5, room.getRoomStatus().name());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to Save Room" + e.getMessage(), e);
        }
        return room;

    }

    @Override
    public List<Room> showAllRooms() {
        List<Room> roomList = new ArrayList<>();
        String sql = """
                SELECT * FROM rooms
                """;

        try (
                Connection connection = databaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()){
                roomList.add(RoomMapper.map(resultSet));
            }
        return roomList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<Room> findById(String id) {
        for (Room room : rooms.values()) {
            if (room.getIdentify().equalsIgnoreCase(id)) {
                return Optional.of(room);
            }
        }
        return Optional.empty();
    }

}
