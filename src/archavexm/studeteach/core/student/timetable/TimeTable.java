package archavexm.studeteach.core.student.timetable;

import java.util.HashMap;
import java.util.HashSet;

public class Timetable {
    private HashMap<Day, Period> dayPeriods;
    private HashMap<Day, Integer> numberOfPeriodsDay;

    private static Timetable instance = new Timetable();

    private Timetable(){}

    public static Timetable getTimetable(HashSet<Day> days){
        for (Day day: days){
            instance.dayPeriods.put(day, null);
            instance.numberOfPeriodsDay.put(day, null);
        }

        return instance;
    }

    public HashMap<Day, Period> getDayPeriods(){
        return dayPeriods;
    }

    public void setNumberOfDayPeriods(Day day, int num){
        numberOfPeriodsDay.put(day, num);
    }

    public void changePeriodOnNumber(Day day, int number, Period newPeriod){
        for (Period period: dayPeriods.values()){
            if (period.getPeriodNumber() == number){
                period = newPeriod;
            }
        }
    }

    public void deletePeriod(Day day, Period period){
        dayPeriods.remove(day, period);
    }

}
