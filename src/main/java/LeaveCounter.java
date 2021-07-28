import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

class LeaveCounter
{
//    public static void main(String[] args) {
//        LocalDate monthBegin = LocalDate.of(2021,07, 1 );
//        LocalDate monthEnd = monthBegin.withDayOfMonth(monthBegin.lengthOfMonth());
//
//        LocalDate startDate = LocalDate.of(2021,06, 15 );
//        if(startDate.isBefore(monthBegin)){
//            startDate = monthBegin;
//        }
//        List<LeaveBooking> leaveBookings = new ArrayList<>();
//        leaveBookings.add(new LeaveBooking(
//                LocalDate.of(2020,01,20),  LocalDate.of(2020,02,10)
//        ));
//        leaveBookings.add(new LeaveBooking(
//                LocalDate.of(2020,01,20),  LocalDate.of(2020,03,10)
//        ));
//        leaveBookings.add(new LeaveBooking(
//                LocalDate.of(2020,02,13),  LocalDate.of(2020,03,10)
//        ));
//        leaveBookings.add(new LeaveBooking(
//                LocalDate.of(2020,02,11),  LocalDate.of(2020,02,14)
//        ));
//
//        Month month = Month.FEBRUARY;
//        Year year = Year.of(2020);
//
//        System.out.println(LeaveCounter.countWorkingDays(leaveBookings, month, year));
//    }

    public int countWorkingDays(final List<LeaveBooking> leaveBookings, final Month month, final Year year){
        int counter = 0;

        LocalDate monthBegin = LocalDate.of(year.getValue(),month, 1 );
        LocalDate monthEnd = monthBegin.withDayOfMonth(monthBegin.lengthOfMonth());

        //System.out.println("monthBegin: " + monthBegin);
        //System.out.println("monthEnd: " + monthEnd);

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
        //System.out.println("startDate: " + startDate);
        LocalDate endDate = monthEnd;
        if (leaveBooking.getEndDate().isBefore(monthEnd)) {
            endDate = leaveBooking.getEndDate();
        }
        //System.out.println("endDate: " + endDate);

        for (LocalDate date = startDate; date.minusDays(1).isBefore(endDate); date = date.plusDays(1)) {
            if (!(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                //counter++;
                localCounter++;
            }
        }
        //System.out.println("localCounter: " + localCounter);
        return localCounter;
    }
}

