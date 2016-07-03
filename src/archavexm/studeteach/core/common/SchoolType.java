package archavexm.studeteach.core.common;

public enum SchoolType {
    PRIMARY,
    SECONDARY,
    UNIVERSITY;

    @Override
    public String toString(){
        switch (this){
            case PRIMARY:
                return "Primary";
            case SECONDARY:
                return "Secondary (High School)";
            case UNIVERSITY:
                return "University";
            default:
                return null;
        }
    }
}
