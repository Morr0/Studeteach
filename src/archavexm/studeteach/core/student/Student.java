package archavexm.studeteach.core.student;

import archavexm.studeteach.core.Person;
import archavexm.studeteach.core.student.timetable.Day;
import archavexm.studeteach.core.student.timetable.Timetable;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;

public class Student implements Person {

    private static Student student = new Student();

    private String firstName;
    private String lastName;
    private String preferedName;
    private int age;

    private School school;
    private int schoolYear;
    private HashSet<Day> schoolDays;

    private Timetable timetable;

    private Student(){}

    public static Student getStudent(String firstName, String lastName){
        student.firstName = firstName;
        student.lastName = lastName;

        return student;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getFullName(){
        return firstName + "" + lastName;
    }

    public String getPreferedName(){
        return preferedName;
    }

    public String getSchoolName(){
        return school.getSchoolName();
    }

    public String getSchoolType(){
        return school.getSchoolType();
    }

    public int getSchoolYear(){
        return schoolYear;
    }

    public String[] getSchoolDays(){
        LinkedList<String> days = new LinkedList<>();

        for (Day day: schoolDays){
            days.add(day.toString());
        }

        return (String[])days.toArray();
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

    public void setSchool(School school){
        this.school = school;
    }

    public void setSchoolYear(int year){
        schoolYear = year;
    }

    public void setSchoolDays(HashSet<Day> days){
        schoolDays = days;
    }

    @Override
    public String toString(){
        return "First Name: " + firstName + " - " + "Last Name: " + lastName + " - Age: " + (Integer.toString(age)) + " - " + "School Name: " + (school.getSchoolName());
    }

}
























