package archavexm.studeteach.core.student.timetable;

public enum Day {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY;

    private int number;

    public int getNumber(){
        return number;
    }

    public void setNumber(int number){
        this.number = number;
    }
}
