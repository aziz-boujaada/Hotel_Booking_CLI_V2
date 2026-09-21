package Repositories.impl;

import Config.DatabaseConfig;
import Models.Room;
import Repositories.RoomRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class InMemoryRoomRepo implements RoomRepository {

    private final DatabaseConfig databaseConfig;

    public InMemoryRoomRepo(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    private static final HashMap<String, Room> rooms = new HashMap<>();

    @Override
    public Room addNewRoom(Room room) {

        rooms.put(room.getIdentify(), room);

        return room;

    }

    @Override
    public List<Room> showAllRooms() {
        List<Room> roomList = new ArrayList<>();

        roomList.addAll(rooms.values());

        return roomList;

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
