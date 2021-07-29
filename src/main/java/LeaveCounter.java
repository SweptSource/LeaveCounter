import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class LeaveCounter {
    TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();

    public int countWorkingDays(final List<LeaveBooking> leaveBookings, final Month month, final Year year) {
        int counter = 0;

        LocalDate monthBegin = LocalDate.of(year.getValue(), month, 1);
        LocalDate monthEnd = monthBegin.withDayOfMonth(monthBegin.lengthOfMonth());

        for (LeaveBooking leaveBooking : leaveBookings) {
            counter += countWorkingDaysForSingleLeave(monthBegin, monthEnd, leaveBooking);
        }
        return counter;
    }

    public int countWorkingDaysForSingleLeave(LocalDate monthBegin, LocalDate monthEnd, LeaveBooking leaveBooking) {
        if (leaveBooking.getEndDate().isBefore(leaveBooking.getStartDate())) {
            return 0;
        }
        if (leaveBooking.getEndDate().getYear() != monthBegin.getYear() && leaveBooking.getStartDate().getYear() != monthBegin.getYear()) {
            return 0;
        }

        int localCounter = 0;
        LocalDate startDate = monthBegin;
        if (leaveBooking.getStartDate().isAfter(monthBegin)) {
            startDate = leaveBooking.getStartDate();
        }

        LocalDate endDate = monthEnd;
        if (leaveBooking.getEndDate().isBefore(monthEnd)) {
            endDate = leaveBooking.getEndDate();
        }

        //CALCULATIONS
        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1; //+1 means INCLUDE start day
        //amount of working days INCLUDING start and end
        long daysInWeek = 7;
        //CASE: if the last day of the month is in the same week as startDate
        if(monthEnd.get(woy) ==  startDate.get(woy)){
            daysInWeek = monthEnd.getDayOfWeek().getValue();
        }

        long startDiff = daysInWeek - startDate.getDayOfWeek().getValue() + 1; //+1 means INCLUDE start day
        long endDiff = (endDate.getDayOfWeek().getValue() == 7) ? 0 : endDate.getDayOfWeek().getValue();
        //long daysInDiffs = startDiff + endDiff;

        long daysInFullWeeks = 0;
        long workingDaysInFullWeeks = 0;
        long workDaysInDiffs = 0;
        //comparing weeks of startDate & endDate
        //if weeks are different
        if(startDate.get(woy) !=  endDate.get(woy)){
            daysInFullWeeks = days - startDiff - endDiff;
            workingDaysInFullWeeks = (daysInFullWeeks / 7) * 5;

            long startDiffWorkDays = (startDate.getDayOfWeek().getValue() < 6) ? daysInWeek - (startDate.getDayOfWeek().getValue() + 1) : 0; //+1 means INCLUDE start day
            long endDiffWorkDays = (endDate.getDayOfWeek().getValue() < 6) ? endDate.getDayOfWeek().getValue() : 5;
            workDaysInDiffs = startDiffWorkDays + endDiffWorkDays;
        }
        //if weeks are same
        else {
            long tempStartV = startDate.getDayOfWeek().getValue();
            long tempEndV = endDate.getDayOfWeek().getValue();
            //weekend
            if(tempStartV >5 && tempEndV >5){
                workDaysInDiffs = 0;
            }
            //end w weekend
            else if(tempStartV < 6 && tempEndV >5){
                workDaysInDiffs = 5 - tempStartV +1; //+1 means INCLUDE start day
            }
            //both are working days
            else {
                workDaysInDiffs = tempEndV - tempStartV +1;
            }
        }
        localCounter += workingDaysInFullWeeks + workDaysInDiffs;
//        System.out.println("localCounter: " + localCounter);
//        System.out.println("----------------------------------------");
        return localCounter;
    }
}

