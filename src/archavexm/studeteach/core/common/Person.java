package archavexm.studeteach.core.common;

import java.util.LinkedList;

public interface Person{
    PersonType getPersonType();
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
