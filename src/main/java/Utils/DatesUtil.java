package Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DatesUtil {

    // parse string to DATE
    public LocalDate parseStringToDate(String stringDate) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return LocalDate.parse(stringDate, formatter);

    }

    // check if date of checkout greater than check in date
    public boolean checkGreaterDate(String checkIn, String checkOut) {
        LocalDate checkInDate = parseStringToDate(checkIn);
        LocalDate checkOutDate = parseStringToDate(checkOut);

        if (!checkOutDate.isAfter(checkInDate)) {
            System.out.println("Check-Out must be  greater than Check-In date");
             return false;
        }
      return true;
    }

    public long calculNights(String checkIn, String checkOut) {
        LocalDate parsedCheckIn = parseStringToDate(checkIn);
        LocalDate parsedCheckOut = parseStringToDate(checkOut);
        if(!checkGreaterDate(checkIn , checkOut)){
            return 0 ;
        }
        return ChronoUnit.DAYS.between(parsedCheckIn, parsedCheckOut);

    }


}
