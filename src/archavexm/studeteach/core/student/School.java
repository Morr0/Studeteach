package archavexm.studeteach.core.student;

import archavexm.studeteach.core.common.SchoolType;
import archavexm.studeteach.core.util.Utilities;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class School {
    private String name;
    @Attribute
    private SchoolType schoolType;

    private static final School instance = new School();

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
        return Utilities.capitalizeFirstLetter(schoolType.toString());
    }

    public void setSchoolType(SchoolType schoolType){
        this.schoolType = schoolType;
    }

}
