package Repositories;

import  Models.Reservation;
import  Models.User;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
     Reservation save(Reservation reservation);
     List<Reservation> findByRoomId(String roomId);
     List<Reservation> myReservations(User loggedUser);
     Optional<Reservation> findById(String reservationId);
     void delete(String reservationId);
     Reservation update(Reservation reservation);
}
