package archavexm.studeteach.core.student.subject;

import archavexm.studeteach.core.util.Utilities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "subject")
public class Subject {
    private Subjects subject;

    public Subject(){
        subject = Subjects.NONE;
    }

    public Subject(Subjects subject) {
        this.subject = subject;
    }

    @XmlElement
    public Subjects getSubject(){
        return subject;
    }

    public String getSubjectInString(){
        if (subject == null){
            return "";
        } else
            return Utilities.capitalizeFirstLetter(subject.toString());
    }
}
