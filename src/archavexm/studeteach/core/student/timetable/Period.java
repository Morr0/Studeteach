package archavexm.studeteach.core.student.timetable;

import archavexm.studeteach.core.student.subject.Subject;

public class Period {
    private Subject subject;
    private int periodNumber;

    public Period(Subject subject, int periodNumber){
        this.subject = subject;
        this.periodNumber = periodNumber;
    }

    public int getPeriodNumber(){
        return periodNumber;
    }

    public void setPeriodNumber(int num){
        periodNumber = num;
    }

    public Subject getSubject(){
        return subject;
    }

    public void setSubject(Subject subject){
        this.subject = subject;
    }
}
