import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

class LeaveCounter
{
    public int countWorkingDays(final List<LeaveBooking> leaveBookings, final Month month, final Year year){
        int counter = 0;

        LocalDate monthBegin = LocalDate.of(year.getValue(),month, 1 );
        LocalDate monthEnd = monthBegin.withDayOfMonth(monthBegin.lengthOfMonth());

        for (LeaveBooking leaveBooking : leaveBookings) {
            counter += countWorkingDaysForSingleLeave(monthBegin, monthEnd, leaveBooking);
        }
        return counter;
    }

    public int countWorkingDaysForSingleLeave(LocalDate monthBegin, LocalDate monthEnd, LeaveBooking leaveBooking) {
        int localCounter = 0;
        LocalDate startDate = monthBegin;
        if (leaveBooking.getStartDate().isAfter(monthBegin)) {
            startDate = leaveBooking.getStartDate();
        }

        LocalDate endDate = monthEnd;
        if (leaveBooking.getEndDate().isBefore(monthEnd)) {
            endDate = leaveBooking.getEndDate();
        }


        for (LocalDate date = startDate; date.minusDays(1).isBefore(endDate); date = date.plusDays(1)) {
            if (!(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                localCounter++;
            }
        }
        return localCounter;
    }
}

