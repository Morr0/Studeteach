package archavexm.studeteach.core.student;

public class School {
    private String name;
    private SchoolType schoolType;

    private static School instance = new School();

    private School(){}

    public static School getInstance(String name, SchoolType schoolType){
        instance.name = name;
        instance.schoolType = schoolType;

        return instance;
    }

    public String getSchoolName(){
        return instance.name;
    }

    public void setSchoolName(String name){
        instance.name = name;
    }

    public String getSchoolType(){
        String output = null;

        switch (instance.schoolType){
            case PRIMARY:
                output = "Primary";
            case SECONDARY:
                output = "Secondary";
            case UNIVERSITY:
                output = "University";
        }

        return output;
    }

    public void setSchoolType(SchoolType schoolType){
        instance.schoolType = schoolType;
    }

}
