import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LeaveCounterTest {
    private Month month;
    private Year year;
    private LocalDate monthBegin;
    private LocalDate monthEnd;

    private LeaveCounter leaveCounter = new LeaveCounter();
    private List<LeaveBooking> leaveBookings = new ArrayList<>();

    private LocalDate tempStartDate;
    private LocalDate tempEndDate;
    private int tmpIdx;
    private int expectedNoOfDays;

    @BeforeEach
    public void init() {
        month = Month.FEBRUARY;
        year = Year.of(2020);
        monthBegin = LocalDate.of(year.getValue(),month, 1 );
        monthEnd = monthBegin.withDayOfMonth(monthBegin.lengthOfMonth());
    }

    @Test
    void countWorkingDays() {

        //0
        tmpIdx = 0;
        tempStartDate = LocalDate.of(2020, 2, 7);
        tempEndDate = LocalDate.of(2020, 2, 10);
        expectedNoOfDays = 2;
        subtestCountWorkingDaysForSingleLeave(tempStartDate, tempEndDate, tmpIdx, expectedNoOfDays);

        //1
        tmpIdx = 1;
        tempStartDate = LocalDate.of(2020, 2, 8);
        tempEndDate = LocalDate.of(2020, 2, 10);
        expectedNoOfDays = 1;
        subtestCountWorkingDaysForSingleLeave(tempStartDate, tempEndDate, tmpIdx, expectedNoOfDays);

        //2
        tmpIdx = 2;
        tempStartDate = LocalDate.of(2020, 2, 8);
        tempEndDate = LocalDate.of(2020, 2, 9);
        expectedNoOfDays = 0;
        subtestCountWorkingDaysForSingleLeave(tempStartDate, tempEndDate, tmpIdx, expectedNoOfDays);

        //3
        tmpIdx = 3;
        tempStartDate = LocalDate.of(2020, 2, 7);
        tempEndDate = LocalDate.of(2020, 2, 9);
        expectedNoOfDays = 1;
        subtestCountWorkingDaysForSingleLeave(tempStartDate, tempEndDate, tmpIdx, expectedNoOfDays);

        //4 - 2021 nie powinno zliczyc
        tmpIdx = 4;
        tempStartDate = LocalDate.of(2021, 2, 7);
        tempEndDate = LocalDate.of(2021, 2, 10);
        expectedNoOfDays = 0;
        subtestCountWorkingDaysForSingleLeave(tempStartDate, tempEndDate, tmpIdx, expectedNoOfDays);

        //5
        tmpIdx = 5;
        tempStartDate = LocalDate.of(2020, 1, 25);
        tempEndDate = LocalDate.of(2020, 2, 10);
        expectedNoOfDays = 6;
        subtestCountWorkingDaysForSingleLeave(tempStartDate, tempEndDate, tmpIdx, expectedNoOfDays);

        //6
        tmpIdx = 6;
        tempStartDate = LocalDate.of(2020, 2, 25);
        tempEndDate = LocalDate.of(2020, 3, 10);
        expectedNoOfDays = 4;
        subtestCountWorkingDaysForSingleLeave(tempStartDate, tempEndDate, tmpIdx, expectedNoOfDays);

        //7
        tmpIdx = 7;
        tempStartDate = LocalDate.of(2020, 2, 5);
        tempEndDate = LocalDate.of(2020, 2, 27);
        expectedNoOfDays = 17;
        subtestCountWorkingDaysForSingleLeave(tempStartDate, tempEndDate, tmpIdx, expectedNoOfDays);

        //8
        tmpIdx = 8;
        tempStartDate = LocalDate.of(2020, 1, 5);
        tempEndDate = LocalDate.of(2020, 3, 27);
        expectedNoOfDays = 20;
        subtestCountWorkingDaysForSingleLeave(tempStartDate, tempEndDate, tmpIdx, expectedNoOfDays);


        System.out.println(leaveCounter.countWorkingDays(leaveBookings, month, year));
    }

    private void subtestCountWorkingDaysForSingleLeave(LocalDate tempStartDate, LocalDate tempEndDate, int tmpIdx, int expectedNoOfDays) {
        leaveBookings.add(new LeaveBooking(tempStartDate, tempEndDate));
        assertEquals(leaveBookings.get(tmpIdx).getStartDate(), tempStartDate);
        assertEquals(leaveBookings.get(tmpIdx).getEndDate(), tempEndDate);
        assertEquals(expectedNoOfDays, leaveCounter.countWorkingDaysForSingleLeave(monthBegin, monthEnd, leaveBookings.get(tmpIdx)));
    }

    @Test
    void countWorkingDaysForSingleLeave() {

    }
}