package archavexm.studeteach.core.student;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import javax.xml.bind.annotation.XmlElement;

@Root
public class School {
    private String name;
    @Attribute
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

    @XmlElement(name = "type")
    public String getSchoolType(){
        String output = null;

        if (schoolType == SchoolType.PRIMARY){
            output = "Primary";
        }
        else if(schoolType == SchoolType.SECONDARY) {
            output = "Secondary";
        }
        else {
            output = "University";
        }

        return output;
    }

    public void setSchoolType(SchoolType schoolType){
        this.schoolType = schoolType;
    }

}
