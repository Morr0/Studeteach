package archavexm.studeteach.core.student.timetable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;

@XmlRootElement(name = "timetable")
public class Timetable {
    private String name;

    private LinkedList<Period> mondayPeriods = new LinkedList<>();
    private LinkedList<Period> tuesdayPeriods = new LinkedList<>();
    private LinkedList<Period> wednesdayPeriods = new LinkedList<>();
    private LinkedList<Period> thursdayPeriods = new LinkedList<>();
    private LinkedList<Period> fridayPeriods = new LinkedList<>();
    private LinkedList<Period> saturdayPeriods = new LinkedList<>();
    private LinkedList<Period> sundayPeriods = new LinkedList<>();

    public String getName() {
        return name;
    }

    @XmlAttribute(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "monday")
    public LinkedList<Period> getMondayPeriods() {

        return mondayPeriods;
    }

    public void setMondayPeriods(LinkedList<Period> mondayPeriods) {
        this.mondayPeriods = mondayPeriods;
    }

    @XmlElement(name = "tuesday")
    public LinkedList<Period> getTuesdayPeriods() {
        return tuesdayPeriods;
    }

    public void setTuesdayPeriods(LinkedList<Period> tuesdayPeriods) {
        this.tuesdayPeriods = tuesdayPeriods;
    }

    @XmlElement(name = "wednesday")
    public LinkedList<Period> getWednesdayPeriods() {
        return wednesdayPeriods;
    }

    public void setWednesdayPeriods(LinkedList<Period> wednesdayPeriods) {
        this.wednesdayPeriods = wednesdayPeriods;
    }

    @XmlElement(name = "thursday")
    public LinkedList<Period> getThursdayPeriods() {
        return thursdayPeriods;
    }

    public void setThursdayPeriods(LinkedList<Period> thursdayPeriods) {
        this.thursdayPeriods = thursdayPeriods;
    }

    @XmlElement(name = "friday")
    public LinkedList<Period> getFridayPeriods() {
        return fridayPeriods;
    }

    public void setFridayPeriods(LinkedList<Period> fridayPeriods) {
        this.fridayPeriods = fridayPeriods;
    }

    @XmlElement(name = "saturday")
    public LinkedList<Period> getSaturdayPeriods() {
        return saturdayPeriods;
    }

    public void setSaturdayPeriods(LinkedList<Period> saturdayPeriods) {
        this.saturdayPeriods = saturdayPeriods;
    }

    @XmlElement(name = "sunday")
    public LinkedList<Period> getSundayPeriods() {
        return sundayPeriods;
    }

    public void setSundayPeriods(LinkedList<Period> sundayPeriods) {
        this.sundayPeriods = sundayPeriods;
    }

    public boolean isTimetableEmpty(){
        LinkedList<Boolean> results = new LinkedList<>();

        results.add(mondayPeriods.isEmpty());
        results.add(tuesdayPeriods.isEmpty());
        results.add(wednesdayPeriods.isEmpty());
        results.add(thursdayPeriods.isEmpty());
        results.add(fridayPeriods.isEmpty());
        results.add(saturdayPeriods.isEmpty());
        results.add(sundayPeriods.isEmpty());

        for (boolean bool: results){
            if (bool == false){
                return false;
            }
        }
        return true;
    }

    public LinkedList<Period> getDayPeriods(Day input){
        String day = input.toString().toLowerCase();

        switch (day){
            case "monday":
                return mondayPeriods;
            case "tuesday":
                return mondayPeriods;
            case "wednesday":
                return mondayPeriods;
            case "thursday":
                return mondayPeriods;
            case "friday":
                return mondayPeriods;
            case "saturday":
                return mondayPeriods;
            case "sunday":
                return mondayPeriods;
            default:
                return null;
        }
    }

    public void setDayPeriods(Day input, LinkedList<Period> periods){
        switch (input){
            case MONDAY:
                mondayPeriods = periods;
                break;
            case TUESDAY:
                tuesdayPeriods = periods;
                break;
            case WEDNESDAY:
                wednesdayPeriods = periods;
                break;
            case THURSDAY:
                thursdayPeriods = periods;
                break;
            case FRIDAY:
                fridayPeriods = periods;
                break;
            case SATURDAY:
                saturdayPeriods = periods;
                break;
            case SUNDAY:
                sundayPeriods = periods;
                break;
        }
    }

    @Override
    public String toString(){
        return "Hello, world";
    }
}





