package Models;

import DTOs.UserDto;
import Enums.ReservationStatus;

import java.time.LocalDate;
import java.util.UUID;

public class Reservation {

    private String reservationID;
    private User client;
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private long nights;
    private double total;
    private ReservationStatus status;
    private int personneNumbers;
    private LocalDate createdAt;

    public static int counter = 0;

    public Reservation(User client, Room room, LocalDate checkIn, LocalDate checkOut, long nights, double total, int personneNumbers, ReservationStatus status) {
        this.reservationID = generateReservationID();
        this.client = client;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.nights = nights;
        this.total = total;
        this.personneNumbers = personneNumbers;
        this.status = status;
        this.createdAt =  createdAt;
    }

    //
    public Reservation(String reservationID, User client, Room room, LocalDate checkIn, LocalDate checkOut, long nights, double total, int personneNumbers, ReservationStatus status) {
        this.reservationID = reservationID;
        this.client = client;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.nights = nights;
        this.total = total;
        this.personneNumbers = personneNumbers;
        this.status = status;
    }


    // generate unique identify to room
    public String generateReservationID() {
        UUID identify = UUID.randomUUID();
        return "RES-" + identify.toString().substring(0, 4) + "-" + String.format("%04d", counter++);
    }

    public String getReservationID() {
        return reservationID;
    }

    public String getReservationNumber() {
        return reservationID;
    }

    public void setReservationNumber(String reservationID) {
        this.reservationID = reservationID;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public long getNights() {
        return nights;
    }

    public void setNights(long nights) {
        this.nights = nights;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getPersonneNumbers() {
        return personneNumbers;
    }

    public void setPersonneNumbers(int personneNumbers) {
        this.personneNumbers = personneNumbers;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {

        UserDto userDto = UserDto.fromModel(client);

        return String.format(
                """
                        
                        ======================== RESERVATION INFO ========================
                        
                        +----------------+------------+------------+--------+-------------+-----------
                        | ID             | Check-In   | Check-Out  | Nights | Total       | Status   |
                        +----------------+------------+------------+--------+-------------+-----------
                       | %-14s | %-10s | %-10s | %-6d | %-11.2f | %-8s |
                        +----------------+------------+------------+--------+-------------+                                                                         
                        +----------------------+---------------------------+---------------+
                        | Name                 | Email                     | Phone         |
                        +----------------------+---------------------------+---------------+
                        | %-20s | %-25s | %-13s |
                        +----------------------+---------------------------+---------------+               
                        ============================ ROOM INFO ============================
                        
                        +--------------+----------------+----------+-------------+----------+
                        | Room ID      | Type           | Capacity | Price/Night | Status   |
                        +--------------+----------------+----------+-------------+----------+
                        | %-12s | %-14s | %-8d | %-11.2f | %-8s |
                        +--------------+----------------+----------+-------------+----------+
                        
                        """,
                reservationID,

                checkIn,
                checkOut,
                nights,
                total,
                getStatus(),

                userDto.fullName,
                userDto.email,
                userDto.phone,

                room.getIdentify(),
                room.getRoomType(),
                room.getCapacity(),
                room.getNightPrice(),
                room.getRoomStatus()
        );
    }
}
