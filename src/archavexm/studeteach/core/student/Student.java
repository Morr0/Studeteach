package archavexm.studeteach.core.student;

import archavexm.studeteach.core.common.Day;
import archavexm.studeteach.core.common.Person;
import archavexm.studeteach.core.common.TODOList;
import archavexm.studeteach.core.common.task.Task;
import archavexm.studeteach.core.student.timetable.Timetable;
import archavexm.studeteach.core.util.Utilities;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

// The main student class
// The entire student profile is here
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

    public HashSet<Day> getSchoolDays(){
        return schoolDays;
    }

    public School getSchool(){
        return school;
    }

    public int getAge(){
        return age;
    }

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

    @Override
    public void setTodoLists(LinkedList<TODOList> todoLists) {
        this.todoLists = todoLists;
    }

    public boolean doesHaveThisDay(Day day){
        for (Day d: getSchoolDays())
            if (d == day)
                return true;

        return false;
    }

    public void organiseTimetables(){
        boolean organise = true;
        for (Timetable timetable: timetables)
            if (timetable.getId() == primaryTimetableId)
                organise = false;

        if (organise)
            primaryTimetableId = 0;

    }

    public Timetable getPrimaryTimetable(){
        Timetable t = null;
        for (Timetable timetable: timetables)
            if (timetable.getId() == primaryTimetableId)
                t = timetable;

        return t;
    }

    public boolean isPrimaryTimetable(Timetable timetable){
        if (timetable.getId() == primaryTimetableId)
            return true;
        else
            return false;
    }

    public Timetable getTimetable(String name){
        Timetable t = null;
        for (Timetable timetable: timetables)
            if (timetable.getName().equals(name))
                t = timetable;

        return t;
    }

    public int generateRandomIdForTimetable(){
        int id = new Random().nextInt(10000);
        for (Timetable timetable: timetables)
            if (timetable.getId() == id)
                generateRandomIdForTimetable();

        return id;
    }
}