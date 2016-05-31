package archavexm.studeteach.core.student;

import archavexm.studeteach.core.common.Person;
import archavexm.studeteach.core.common.TODOList;
import archavexm.studeteach.core.student.task.Task;
import archavexm.studeteach.core.student.timetable.Day;
import archavexm.studeteach.core.student.timetable.Timetable;
import archavexm.studeteach.core.util.Utilities;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.HashSet;
import java.util.LinkedList;

@Root(name = "student")
public class Student implements Person {

    private static Student student = new Student();

    @Attribute(name = "first_name")
    private String firstName;
    @Attribute(name = "last_name")
    private String lastName;
    @Attribute(name = "preferred_name")
    private String preferredName;
    @Attribute(name = "age")
    private int age;

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

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public String getPreferredName(){
        return preferredName;
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

    public HashSet<Day> getSchoolDays(){
        return schoolDays;
    }

    public School getSchool(){
        return school;
    }

    public int getAge(){
        return age;
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

    public LinkedList<Timetable> getTimetables(){
        return timetables;
    }

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

    public void setSchoolDays(HashSet<Day> days){
        schoolDays = days;
    }

    public void setSchoolType(SchoolType schoolType){
        school.setSchoolType(schoolType);
    }

    public void setTasks(LinkedList<Task> tasks){
        this.tasks = tasks;
    }

    public void setTimetables(LinkedList<Timetable> timetables){
        this.timetables = timetables;
    }

    public void setTodoLists(LinkedList<TODOList> todoLists) {
        this.todoLists = todoLists;
    }

    public boolean doesHaveThisDay(Day day){
        for (Day d: getSchoolDays()){
            if (d == day){
                return true;
            }
        }
        return false;
    }

    public TODOList getSelectedTODOList(String list){
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
}
























