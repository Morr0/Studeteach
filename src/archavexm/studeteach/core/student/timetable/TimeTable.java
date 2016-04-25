package archavexm.studeteach.core.student.timetable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.LinkedList;

@XmlRootElement(name = "timetable")
public class Timetable {
    private HashMap<Day, LinkedList<Period>> dayPeriods = new HashMap<>();

    private boolean isEmpty = dayPeriods.isEmpty() ? true : false;

    private static Timetable timetable = new Timetable();

    private Timetable(){}

    public static Timetable getTimetable(){
        return timetable;
    }

    @XmlElement(name = "days_with_periods")
    public HashMap<Day, LinkedList<Period>> getDayPeriods(){
        return dayPeriods;
    }

    public void setDayPeriods(HashMap<Day, LinkedList<Period>> dayPeriods){
        this.dayPeriods = dayPeriods;
    }

    public boolean isTimetableEmpty(){
        return isEmpty;
    }

    public void setEmpty(boolean bool){
        isEmpty = bool;
    }

}
