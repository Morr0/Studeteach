package archavexm.studeteach.core.student.subject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "subject")
public class Subject {
    private Subjects subject;

    public Subject(){}

    public Subject(Subjects subject) {
        this.subject = subject;
    }

    @XmlElement(name = "name")
    public Subjects getSubject(){
        return subject;
    }

    public String getSubjectInString(){
        return (subject.toString()).toLowerCase();
    }
}
