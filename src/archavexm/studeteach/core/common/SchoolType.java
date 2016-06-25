package archavexm.studeteach.core.common;

public enum SchoolType {
    PRIMARY,
    SECONDARY,
    UNIVERSITY;

    public static SchoolType toSchoolType(String input){
        String in = input.toLowerCase();
        if (in == "primary")
            return SchoolType.PRIMARY;
         else if (in == "secondary (high school)")
            return SchoolType.SECONDARY;
        else
            return SchoolType.UNIVERSITY;
    }
}
