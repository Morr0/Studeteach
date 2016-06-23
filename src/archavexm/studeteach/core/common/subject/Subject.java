package archavexm.studeteach.core.common.subject;

import archavexm.studeteach.core.util.Utilities;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "subject")
public class Subject {
    @Element
    private Subjects subject;

    public Subject(){
        subject = Subjects.NONE;
    }

    public Subject(Subjects subject) {
        this.subject = subject;
    }

    public Subjects getSubject(){
        return subject;
    }

    @Override
    public String toString(){
        return Utilities.capitalizeFirstLetter(subject.toString());
    }
}
