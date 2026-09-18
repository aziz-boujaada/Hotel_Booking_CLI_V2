package main.java.Services;

import main.java.Enums.RoomStatus;
import main.java.Enums.RoomType;
import main.java.Models.Room;
import main.java.Repositories.impl.InMemoryRoomRepo;
import main.java.Utils.MoneyUtil;

import java.util.List;


public class RoomService {

    private final MoneyUtil moneyUtil ;
    private final InMemoryRoomRepo roomRepo ;

    public RoomService(){
        this.moneyUtil = new MoneyUtil();
        this.roomRepo = new InMemoryRoomRepo();
    }

    public Room addRoom(RoomType roomType , double nightPrice , int capacity , RoomStatus roomStatus){

        String parsedPrice = Double.toString(nightPrice);
        moneyUtil.validateEmpty(parsedPrice);
        moneyUtil.validatePrices(parsedPrice);

        Room room = new Room(roomType , nightPrice , capacity , roomStatus);
        return roomRepo.addNewRoom(room);
    }

    public List<Room> getAvailableRooms(){

        List<Room> availableRooms = roomRepo.showAllRooms();

       return  availableRooms.stream()
                .filter(room -> room.getRoomStatus() == RoomStatus.AVAILABLE)
                .toList();
    }
}
