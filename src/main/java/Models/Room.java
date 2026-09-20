package Models;

import Enums.RoomStatus;
import Enums.RoomType;

import java.util.UUID;

public class Room {

    private  String identify ;
    private RoomType roomType;
    private double nightPrice ;
    private int capacity;
    private RoomStatus roomStatus;


    public static int counter = 0 ;

    public Room(RoomType roomType, double nightPrice, int capacity, RoomStatus roomStatus) {
        this.identify = generateRoomID();
        this.roomType = roomType;
        this.nightPrice = nightPrice;
        this.capacity = capacity;
        this.roomStatus = roomStatus;
    }


    // generate unique identify to room
    public String generateRoomID(){
        UUID identify = UUID.randomUUID();
        return "ROOM-" + identify.toString().substring(0 , 4) + "-" + String.format("%04d" , counter++);
    }

    public String getIdentify() {
        return identify;
    }

    public String getRoomNumber() {
        return identify;
    }

    public void setRoomNumber(String identify) {
        this.identify = Room.this.identify;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public double getNightPrice() {
        return nightPrice;
    }

    public void setNightPrice(double nightPrice) {
        this.nightPrice = nightPrice;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    @Override
    public String toString(){
        return "Room{" +
                "identify='" + identify + '\'' +
                ", type='" + roomType + '\'' +
                ", Night Price ='" + nightPrice + '\'' +
                ", Capacity='" + capacity + '\'' +
                ", Status='" + roomStatus + '\'' +
                '}';
    }
}
