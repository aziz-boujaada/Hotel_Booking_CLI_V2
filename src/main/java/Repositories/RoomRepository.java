package Repositories;

import Models.Room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    Room addNewRoom(Room room) ;
    List<Room> showAllRooms();
    Optional<Room> findById(String roomId);
}
