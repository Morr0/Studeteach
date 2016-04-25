package archavexm.studeteach.core.student.timetable;

import archavexm.studeteach.core.student.subject.Subject;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "period")
public class Period {
    private Subject subject;
    private int number;

    public Period(){}

    public Period(Subject subject){
        this.subject = subject;
    }

    public Period(Subject subject, int number){
        this.subject = subject;
        this.number = number;
    }

    @XmlAttribute(name = "number")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @XmlElement
    public Subject getSubject(){
        return subject;
    }

    public void setSubject(Subject subject){
        this.subject = subject;
    }
}
