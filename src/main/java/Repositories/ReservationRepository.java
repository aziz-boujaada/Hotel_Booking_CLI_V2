package main.java.Repositories;

import main.java.Models.Reservation;
import main.java.Models.User;

import java.util.List;

public interface ReservationRepository {
     Reservation save(Reservation reservation);
     List<Reservation> findByRoomId(String roomId);
     List<Reservation> myReservations(User loggedUser);
     Reservation findById(String reservationId);
     void delete(String reservationId);
     Reservation update(Reservation reservation);
}
