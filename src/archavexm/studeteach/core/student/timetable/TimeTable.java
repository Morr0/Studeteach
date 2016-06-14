package archavexm.studeteach.core.student.timetable;

import archavexm.studeteach.core.student.subject.Subject;
import archavexm.studeteach.core.student.subject.Subjects;
import archavexm.studeteach.core.util.Utilities;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.LinkedList;

@Root
public class Timetable {
    @Attribute(required = false)
    private String name;
    @Attribute(required = true)
    private int id;

    @ElementList(name = "monday_periods", required = false)
    private LinkedList<Period> mondayPeriods;
    @ElementList(name = "tuesday_periods", required = false)
    private LinkedList<Period> tuesdayPeriods;
    @ElementList(name = "wednesday_periods", required = false)
    private LinkedList<Period> wednesdayPeriods;
    @ElementList(name = "thursday_periods", required = false)
    private LinkedList<Period> thursdayPeriods;
    @ElementList(name = "friday_periods", required = false)
    private LinkedList<Period> fridayPeriods;
    @ElementList(name = "saturday_periods", required = false)
    private LinkedList<Period> saturdayPeriods;
    @ElementList(name = "sunday_periods", required = false)
    private LinkedList<Period> sundayPeriods;

    public Timetable() {
        mondayPeriods = new LinkedList<>();
        tuesdayPeriods = new LinkedList<>();
        wednesdayPeriods = new LinkedList<>();
        thursdayPeriods = new LinkedList<>();
        fridayPeriods = new LinkedList<>();
        saturdayPeriods = new LinkedList<>();
        sundayPeriods = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public LinkedList<Period> getMondayPeriods() {
        return mondayPeriods;
    }

    public void setMondayPeriods(LinkedList<Period> mondayPeriods) {
        this.mondayPeriods = mondayPeriods;
    }

    public LinkedList<Period> getTuesdayPeriods() {
        return tuesdayPeriods;
    }

    public void setTuesdayPeriods(LinkedList<Period> tuesdayPeriods) {
        this.tuesdayPeriods = tuesdayPeriods;
    }

    public LinkedList<Period> getWednesdayPeriods() {
        return wednesdayPeriods;
    }

    public void setWednesdayPeriods(LinkedList<Period> wednesdayPeriods) {
        this.wednesdayPeriods = wednesdayPeriods;
    }

    public LinkedList<Period> getThursdayPeriods() {
        return thursdayPeriods;
    }

    public void setThursdayPeriods(LinkedList<Period> thursdayPeriods) {
        this.thursdayPeriods = thursdayPeriods;
    }

    public LinkedList<Period> getFridayPeriods() {
        return fridayPeriods;
    }

    public void setFridayPeriods(LinkedList<Period> fridayPeriods) {
        this.fridayPeriods = fridayPeriods;
    }

    public LinkedList<Period> getSaturdayPeriods() {
        return saturdayPeriods;
    }

    public void setSaturdayPeriods(LinkedList<Period> saturdayPeriods) {
        this.saturdayPeriods = saturdayPeriods;
    }

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
        String day = Utilities.capitalizeFirstLetter(input.toString());

        switch (day){
            case "Monday":
                return mondayPeriods;
            case "Tuesday":
                return tuesdayPeriods;
            case "Wednesday":
                return wednesdayPeriods;
            case "Thursday":
                return thursdayPeriods;
            case "Friday":
                return fridayPeriods;
            case "Saturday":
                return saturdayPeriods;
            case "Sunday":
                return sundayPeriods;
            default:
                return new LinkedList<>();

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
            default:
                break;
        }
    }

    public boolean containsPeriodOnDay(Day day){
        LinkedList<Period> periods = getDayPeriods(day);

        if (periods.isEmpty())
            return false;
        else
            return true;

    }

    public boolean containsPeriodOnDay(Subject subject, Day day){
        LinkedList<Period> periods = getDayPeriods(day);

        for (Period period: periods){
            if (period.getSubject().getSubject() == subject.getSubject())
                return true;
        }
        return false;
    }

    public int getPeriodNumber(Day day, Subjects subject){
        LinkedList<Period> periods = getDayPeriods(day);

        for (Period period: periods){
            if (period.getSubject().getSubject() == subject){
                return period.getNumber();
            }
        }
        return -1;
    }
}





