package Repositories.impl;

import  Models.Reservation;
import  Models.User;
import Repositories.ReservationRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryReservationRepo implements ReservationRepository {

    private static final HashMap<String, Reservation> reservations = new HashMap<>();

    public InMemoryReservationRepo() {
    }

    public Reservation save(Reservation reservation) {
        String reservationId = reservation.getReservationID();
        reservations.put(reservationId, reservation);
        return reservation;
    }

    public Reservation findById(String reservationId) {
        return reservations.get(reservationId);
    }

    public List<Reservation> findByRoomId(String roomId) {
        return reservations.values().stream()
                .filter(reservation -> reservation.getRoom().getIdentify().equals(roomId))
                .toList();
    }

    public List<Reservation> myReservations(User loggedUser) {
        List<Reservation> myReservationsList = new ArrayList<>();

        for (Reservation reservation : reservations.values()) {
            if (reservation.getClient() != null && loggedUser != null && reservation.getClient().getId().equals(loggedUser.getId())) {
                myReservationsList.add(reservation);
            }
        }
        return myReservationsList;
    }

    public Reservation update(Reservation reservation) {
        if (reservation == null || reservation.getReservationID() == null) {
            return null;
        }
        reservations.put(reservation.getReservationID(), reservation);
        return reservation;
    }

    public void delete(String reservationId) {
        reservations.remove(reservationId);
    }
}
