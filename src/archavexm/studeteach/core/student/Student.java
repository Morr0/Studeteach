package archavexm.studeteach.core.student;

import archavexm.studeteach.core.Person;
import archavexm.studeteach.core.student.task.Task;
import archavexm.studeteach.core.student.timetable.Day;
import archavexm.studeteach.core.student.timetable.Timetable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.LinkedList;

@XmlRootElement(name = "student")
public class Student implements Person {

    private static Student student = new Student();

    private String firstName;
    private String lastName;
    private String preferedName;
    private int age;

    private School school = School.getInstance(null, null);
    private int schoolYear;
    private HashSet<Day> schoolDays = new HashSet<>(7);

    private LinkedList<Task> tasks;

    private Timetable timetable = Timetable.getTimetable();

    private Student(){}

    public static Student getStudent(){
        return student;
    }

    @XmlAttribute(name = "firstName")
    public String getFirstName(){
        return firstName;
    }

    @XmlAttribute(name = "lastName")
    public String getLastName(){
        return lastName;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    @XmlAttribute(name = "nickname")
    public String getPreferedName(){
        return preferedName;
    }

    public String getSchoolName(){
        return school.getSchoolName();
    }

    public String getSchoolType(){
        return school.getSchoolType();
    }

    @XmlAttribute(name = "year")
    public int getSchoolYear(){
        return schoolYear;
    }

    @XmlElement(name = "school_days")
    public HashSet<Day> getSchoolDays(){
        return schoolDays;
    }

    @XmlElement
    public School getSchool(){
        return school;
    }

    @XmlAttribute(name = "age")
    public int getAge(){
        return age;
    }

    @XmlElement(name = "tasks")
    public LinkedList<Task> getTasks(){
        return tasks;
    }

    @XmlElement
    public Timetable getTimetable(){
        return timetable;
    }

    public void setFirstName(String name){
        firstName = name;
    }

    public void setLastName(String name){
        lastName = name;
    }

    public void setPreferedName(String name){
        preferedName = name;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setSchoolName(String schoolName){
        school.setSchoolName(schoolName);
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

    public boolean doesHaveThisDay(Day day){
        for (Day d: getSchoolDays()){
            if (d == day){
                return true;
            }
        }
        return false;
    }

    public void setTimetable(Timetable timetable){
        this.timetable = timetable;
    }

}
























