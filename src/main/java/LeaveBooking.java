import java.time.LocalDate;

class LeaveBooking {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public LeaveBooking(final LocalDate startDate, final LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
