package archavexm.studeteach.core.student.timetable;

import archavexm.studeteach.core.common.subject.Subject;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Period {
    @Element
    private Subject subject;
    @Attribute
    private int number;

    public Period(){}

    public Period(int number){
        this.number = number;
    }

    public Period(Subject subject){
        this.subject = subject;
    }

    public Period(Subject subject, int number){
        this.subject = subject;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Subject getSubject(){
        return subject;
    }

    public void setSubject(Subject subject){
        this.subject = subject;
    }
}
