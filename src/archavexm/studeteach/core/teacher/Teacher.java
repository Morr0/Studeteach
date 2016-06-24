package archavexm.studeteach.core.teacher;

import archavexm.studeteach.core.common.Person;
import archavexm.studeteach.core.common.TODOList;
import archavexm.studeteach.core.common.subject.Subject;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.LinkedList;

@Root(name = "teacher")
public class Teacher implements Person {
    private static Teacher teacher = new Teacher();


    @Attribute(name = "first_name")
    private String firstName;
    @Attribute(name = "last_name")
    private String lastName;
    @Element
    private Subject subject;

    @ElementList(name = "todo_lists", entry = "list", required = false)
    private LinkedList<TODOList> todoLists = new LinkedList<>();

    private Teacher() {}

    public static Teacher getTeacher(String firstName, String lastName){
        teacher.firstName = firstName;
        teacher.lastName = lastName;

        return teacher;
    }

    public static Teacher getTeacher() {
        return teacher;
    }

    public static void setTeacher(Teacher teacher) {
        Teacher.teacher = teacher;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public LinkedList<TODOList> getTodoLists() {
        return todoLists;
    }

    @Override
    public void setTodoLists(LinkedList<TODOList> todoLists) {
        this.todoLists = todoLists;
    }

    @Override
    public PersonType getPersonType(){
        return PersonType.STUDENT;
    }

    @Override
    @Deprecated
    public void organiseTimetables(){}
}
