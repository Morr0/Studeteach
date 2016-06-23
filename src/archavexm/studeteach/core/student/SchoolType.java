package archavexm.studeteach.core.student;

public enum SchoolType {
    PRIMARY,
    SECONDARY,
    UNIVERSITY;

    public static SchoolType toSchoolType(String input){
        if ((input.toLowerCase()) == "primary")
            return SchoolType.PRIMARY;
         else if ((input.toLowerCase()) == "secondary")
            return SchoolType.SECONDARY;
        else
            return SchoolType.UNIVERSITY;
    }
}
