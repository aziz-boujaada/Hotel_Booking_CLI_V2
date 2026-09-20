package Services;

import  Enums.RoomStatus;
import  Enums.RoomType;
import  Models.Room;
import Repositories.impl.InMemoryRoomRepo;
import  Utils.MoneyUtil;

import java.util.List;


public class RoomService {

    private final MoneyUtil moneyUtil ;
    private final InMemoryRoomRepo roomRepo ;

    public RoomService(InMemoryRoomRepo roomRepo){
        this.moneyUtil = new MoneyUtil();
        this.roomRepo = roomRepo;
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
