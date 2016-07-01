package archavexm.studeteach.core.common;

import archavexm.studeteach.core.common.timetable.Timetable;

import java.util.HashSet;
import java.util.LinkedList;

public interface Person{
    PersonType getPersonType();
    HashSet<Day> getSchoolDays();

    String getFirstName();
    String getLastName();
    String getFullName();
    String getPreferredName();

    default String getTitleName(){
        if (getPreferredName() == null)
            return getFirstName();
        else
            return getPreferredName();
    }

    LinkedList<Timetable> getTimetables();
    void setTimetables(LinkedList<Timetable> timetables);
    void organiseTimetables();

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

        TODOList todoList = new TODOList(name, ticked);
        return todoList;
    }

    enum PersonType {
        STUDENT,
        TEACHER
    }
}
