package Mappers;

import Enums.RoomStatus;
import Enums.RoomType;
import Models.Room;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomMapper {
    public static Room map(ResultSet resultSet)throws SQLException {
         return new Room(
                 RoomType.valueOf(resultSet.getString("room_type")),
                 resultSet.getDouble("night_price"),
                 resultSet.getInt("capacity"),
                 RoomStatus.valueOf(resultSet.getString("room_status"))

         );
    }
    private  RoomMapper(){

    }
}
