package applicationPackage.businessLayer.utilityClasses;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class AvailableDates {


    public AvailableDates() {
    }

    public LocalDateTime createDateForExamination(){
        LocalDateTime time = LocalDateTime.now();

        if (time.getHour() < 23)
            time = LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), time.getHour() + 1, 00);
        else
            time = LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), 0, 00);
        if (time.getDayOfWeek() == DayOfWeek.SATURDAY)
            time = LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth() + 2, 8, 00);
        if (time.getDayOfWeek() == DayOfWeek.SUNDAY)
            time = LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth() + 1, 8, 00);

        return time;
    }

    public boolean checkIfDateHasWorkTime(LocalDateTime time){
       return time.isBefore(LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), 16, 00)) && time.isAfter(LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), 7, 45));
    }

    public LocalDateTime changeDay(LocalDateTime time){
        if (time.getDayOfWeek() == DayOfWeek.FRIDAY) {
            time = LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), 8, 00);
            time = time.plusDays(3);
        } else {
            time = LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), 8, 00);
            time = time.plusDays(1);
        }

        return time;
    }

    public LocalDateTime changeDayWhenDateIsMaintenanceDay(LocalDateTime time) {
        if (time.getDayOfWeek() == DayOfWeek.FRIDAY)
            time = time.plusDays(3);
        else
            time = time.plusDays(1);
        return time;
    }
}
