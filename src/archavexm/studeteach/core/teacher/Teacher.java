package archavexm.studeteach.core.teacher;

import archavexm.studeteach.core.common.Person;
import archavexm.studeteach.core.common.SchoolType;
import archavexm.studeteach.core.common.TODOList;
import archavexm.studeteach.core.common.subject.Subjects;
import org.simpleframework.xml.Attribute;
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
    @Attribute(name = "preferred_name")
    private String preferredName;
    @Attribute(name = "subject")
    private Subjects subject;
    @Attribute(name = "school_name")
    private String schoolName;
    @Attribute(name = "school_type")
    private SchoolType schoolType;

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

    public Subjects getSubject() {
        return subject;
    }

    public void setSubject(Subjects subject) {
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
        return PersonType.TEACHER;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public SchoolType getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(SchoolType schoolType) {
        this.schoolType = schoolType;
    }

    @Override
    @Deprecated
    public void organiseTimetables(){}
}
