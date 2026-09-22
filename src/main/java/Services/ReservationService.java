package Services;

import  Enums.ReservationStatus;
import  Enums.RoomStatus;
import  Models.Reservation;
import  Models.Room;
import  Models.User;
import Repositories.impl.JdbcReservationRepo;
import Repositories.impl.JdbcRoomRepo;
import  Utils.DatesUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ReservationService {

    private final JdbcRoomRepo roomRepository;
    private final JdbcReservationRepo reservationRepo;
    private final DatesUtil datesUtil;
    private final AuthService authService;

    public ReservationService(
            AuthService authService,
            JdbcReservationRepo reservationRepo,
            JdbcRoomRepo roomRepo,
            DatesUtil datesUtil
    ) {
        this.roomRepository = roomRepo;
        this.datesUtil = datesUtil;
        this.reservationRepo = reservationRepo;
        this.authService = authService;
    }

    public Reservation addNewReservation(String roomID, String checkIn, String checkOut, int personsNumber) {
        LocalDate parsedCheckIn = datesUtil.parseStringToDate(checkIn);
        LocalDate parsedCheckOut = datesUtil.parseStringToDate(checkOut);

        datesUtil.checkGreaterDate(checkIn, checkOut);
        long nights = datesUtil.calculNights(checkIn, checkOut);

        Optional<Room> roomOptional = roomRepository.findById(roomID);
        if (roomOptional.isEmpty()) {
            throw new IllegalArgumentException("Room not found");
        }

        List<Reservation> reservations = reservationRepo.findByRoomId(roomID);
        checkAlreadyReserved(reservations, parsedCheckIn, parsedCheckOut);

        Room room = roomOptional.get();

        if (personsNumber > room.getCapacity()) {
            throw new IllegalArgumentException("Number of persons must be smaller than or equal to room capacity.");
        }

        if(room.getRoomStatus().equals(RoomStatus.IN_REPAIR)){
            throw new IllegalArgumentException("this room in repair chose other room or wait until room available ");
        }

        double total = room.getNightPrice() * (int) nights;

        User user = authService.getLoggedUser();
        if (user == null) {
            throw new IllegalArgumentException("No user is logged in.");
        }

        Reservation reservation = new Reservation(
                user,
                room,
                parsedCheckIn,
                parsedCheckOut,
                nights,
                total,
                personsNumber,
                ReservationStatus.CONFIRMED
        );

        reservationRepo.save(reservation);
        return reservation;
    }

    public List<Reservation> getMyReservations(User loggedUser) {
        if (loggedUser == null) {
            throw new IllegalArgumentException("User is required.");
        }
        return reservationRepo.myReservations(loggedUser);
    }

    public Reservation updateReservation(String reservationId, String newCheckIn, String newCheckOut, int newPersonsNumber) {
        Optional<Reservation> OptionalReservation = reservationRepo.findById(reservationId);
        if (OptionalReservation.isEmpty()) {
            throw new IllegalArgumentException("Reservation not found.");
        }
          Reservation reservation = OptionalReservation.get();

        if (!reservation.getClient().getId().equals(authService.getLoggedUser().getId())) {
            throw new IllegalArgumentException("You can only update your own reservation.");
        }

        LocalDate parsedCheckIn = datesUtil.parseStringToDate(newCheckIn);
        LocalDate parsedCheckOut = datesUtil.parseStringToDate(newCheckOut);
        datesUtil.checkGreaterDate(newCheckIn, newCheckOut);

        List<Reservation> sameRoomReservations = reservationRepo.findByRoomId(reservation.getRoom().getIdentify());
        for (Reservation existing : sameRoomReservations) {
            if (!existing.getReservationID().equals(reservationId)
                    && parsedCheckIn.isBefore(existing.getCheckOut())
                    && parsedCheckOut.isAfter(existing.getCheckIn())) {
                throw new IllegalArgumentException("This room is already reserved for the updated dates.");
            }
        }

        if (newPersonsNumber > reservation.getRoom().getCapacity()) {
            throw new IllegalArgumentException("Number of persons must be smaller than or equal to room capacity.");
        }

        reservation.setCheckIn(parsedCheckIn);
        reservation.setCheckOut(parsedCheckOut);
        reservation.setPersonneNumbers(newPersonsNumber);
        reservation.setNights(datesUtil.calculNights(newCheckIn, newCheckOut));
        reservation.setTotal(reservation.getRoom().getNightPrice() * reservation.getNights());

        return reservationRepo.update(reservation);
    }

    public boolean cancelReservation(String reservationId) {
        Optional<Reservation> OptionalReservation = reservationRepo.findById(reservationId);
        if (OptionalReservation.isEmpty()) {
            throw new IllegalArgumentException("Reservation not found.");
        }
         Reservation reservation = OptionalReservation.get();

        if (!reservation.getClient().getId().equals(authService.getLoggedUser().getId())) {
            throw new IllegalArgumentException("You can only cancel your own reservation.");
        }

        reservation.setStatus(ReservationStatus.CANCELED);
        reservationRepo.update(reservation);
        return true;
    }

    public void checkAlreadyReserved(List<Reservation> reservations, LocalDate checkIn, LocalDate checkOut) {
        for (Reservation reservation : reservations) {
            if (checkIn.isBefore(reservation.getCheckOut())
                    && checkOut.isAfter(reservation.getCheckIn())) {
                throw new IllegalArgumentException("This room is already reserved for these dates");
            }
        }
    }
}
