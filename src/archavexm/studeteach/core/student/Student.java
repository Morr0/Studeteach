package archavexm.studeteach.core.student;

import archavexm.studeteach.core.common.Day;
import archavexm.studeteach.core.common.Person;
import archavexm.studeteach.core.common.SchoolType;
import archavexm.studeteach.core.common.TODOList;
import archavexm.studeteach.core.common.timetable.Timetable;
import archavexm.studeteach.core.student.task.Task;
import archavexm.studeteach.core.util.Utilities;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.HashSet;
import java.util.LinkedList;

// The main student class
// The entire student profile is here
@Root(name = "student")
public class Student implements Person {

    private static final Student student = new Student();

    @Attribute(name = "first_name")
    private String firstName;
    @Attribute(name = "last_name")
    private String lastName;
    @Attribute(name = "preferred_name")
    private String preferredName;
    @Attribute(name = "age")
    private int age;

    @Attribute(name = "primary_timetable")
    private int primaryTimetableId;

    @Element(name = "school")
    private School school = School.getInstance(null, null);
    @Attribute(name = "school_name")
    private String schoolName;
    @Attribute(name = "school_year")
    private int schoolYear;
    @ElementList(name = "days", entry = "day")
    private HashSet<Day> schoolDays = new HashSet<>(7);

    @ElementList(name = "tasks", entry = "task")
    private LinkedList<Task> tasks = new LinkedList<>();
    @ElementList(name = "timetables", entry = "timetable")
    private LinkedList<Timetable> timetables = new LinkedList<>();
    @ElementList(name = "todo_lists", entry = "list", required = false)
    private LinkedList<TODOList> todoLists = new LinkedList<>();

    private Student(){}

    public static Student getStudent(){
        return student;
    }

    @Override
    public String getFirstName(){
        return firstName;
    }

    @Override
    public String getLastName(){
        return lastName;
    }

    @Override
    public String getFullName(){
        return firstName + " " + lastName;
    }

    @Override
    public String getPreferredName(){
        return preferredName;
    }

    @Override
    public PersonType getPersonType(){
        return PersonType.STUDENT;
    }

    public String getSchoolName(){
        return schoolName;
    }

    public String getSchoolType(){
        return school.getSchoolType();
    }

    public int getSchoolYear(){
        return schoolYear;
    }

    @Override
    public HashSet<Day> getSchoolDays(){
        return schoolDays;
    }

    public School getSchool(){
        return school;
    }

    public int getAge(){
        return age;
    }

    @Override
    public int getPrimaryTimetableId(){
        return primaryTimetableId;
    }

    public LinkedList<Task> getTasks(){
        return tasks;
    }

    public LinkedList<String> getSchoolDaysInString(){
        LinkedList<String> days = new LinkedList<>();
        for (Day day: schoolDays){
            String d = Utilities.capitalizeFirstLetter(day.toString());
            days.add(d);
        }

        return days;
    }

    @Override
    public LinkedList<Timetable> getTimetables(){
        return timetables;
    }

    @Override
    public LinkedList<TODOList> getTodoLists() {
        return todoLists;
    }

    public void setFirstName(String name){
        firstName = name;
    }

    public void setLastName(String name){
        lastName = name;
    }

    public void setPreferredName(String name){
        preferredName = name;
    }

    public void setAge(int age){
        this.age = age;
    }

    @Override
    public void setPrimaryTimetableId(int primaryTimetableId){
        this.primaryTimetableId = primaryTimetableId;
    }

    public void setSchoolName(String schoolName){
        school.setSchoolName(schoolName);
        this.schoolName = schoolName;
    }

    public void setSchool(School school){
        this.school = school;
    }

    public void setSchoolYear(int year){
        schoolYear = year;
    }

    @Override
    public void setSchoolDays(HashSet<Day> days){
        schoolDays = days;
    }

    public void setSchoolType(SchoolType schoolType){
        school.setSchoolType(schoolType);
    }

    public void setTasks(LinkedList<Task> tasks){
        this.tasks = tasks;
    }

    @Override
    public void setTimetables(LinkedList<Timetable> timetables){
        this.timetables = timetables;
    }

    @Override
    public void setTodoLists(LinkedList<TODOList> todoLists) {
        this.todoLists = todoLists;
    }
}