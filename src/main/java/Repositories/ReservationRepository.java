package Repositories;

import  Models.Reservation;
import  Models.User;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
     Reservation save(Connection connection , Reservation reservation);
     List<Reservation> findByRoomId(String roomId);
     List<Reservation> myReservations(User loggedUser);
     Optional<Reservation> findById(String reservationId);
     void delete(String reservationId);
     Reservation update(Reservation reservation);
}
