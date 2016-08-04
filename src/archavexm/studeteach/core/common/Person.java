package archavexm.studeteach.core.common;

import archavexm.studeteach.core.common.timetable.Timetable;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public interface Person{
    PersonType getPersonType();
    HashSet<Day> getSchoolDays();
    void setSchoolDays(HashSet<Day> schoolDays);

    String getFirstName();
    String getLastName();
    String getFullName();
    String getPreferredName();

    default String getTitleName(){
        return getPreferredName() == null || getPreferredName().isEmpty() ? getFirstName() : getPreferredName();
    }

    default boolean doesHaveThisDay(Day day){
        for (Day d: getSchoolDays())
            if (d == day)
                return true;

        return false;
    }

    LinkedList<Timetable> getTimetables();
    void setTimetables(LinkedList<Timetable> timetables);
    int getPrimaryTimetableId();
    void setPrimaryTimetableId(int primaryTimetableId);
    default Timetable getPrimaryTimetable(){
        Timetable t = null;
        for (Timetable timetable: getTimetables())
            if (timetable.getId() == getPrimaryTimetableId())
                t = timetable;

        return t;
    }
    default Timetable getTimetable(String name){
        Timetable t = null;
        for (Timetable timetable: getTimetables())
            if (timetable.getName().equals(name))
                t = timetable;

        return t;
    }
    default void organiseTimetables(){
        boolean organise = true;
        for (Timetable timetable: getTimetables())
            if (timetable.getId() == getPrimaryTimetableId())
                organise = false;

        if (organise)
            setPrimaryTimetableId(0);
    }
    default int generateRandomIdForTimetable(){
        int id = new Random().nextInt(10000);
        for (Timetable timetable: getTimetables())
            if (timetable.getId() == id)
                generateRandomIdForTimetable();

        return id;
    }
    default boolean isPrimaryTimetable(Timetable timetable){
        return timetable.getId() == getPrimaryTimetableId();
    }

    LinkedList<TODOList> getTodoLists();
    void setTodoLists(LinkedList<TODOList> todoLists);

    default TODOList getSelectedTODOList(String list){
        boolean ticked = false;
        String name;

        if (list.contains("-")){
            ticked = true;
            int c = list.indexOf("-");
            name = list.substring(0, c);
        } else
            name = list;

        return new TODOList(name, ticked);
    }

    enum PersonType {
        STUDENT,
        TEACHER
    }
}
