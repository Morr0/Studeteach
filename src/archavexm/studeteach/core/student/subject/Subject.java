package archavexm.studeteach.core.student.subject;

public class Subject {
    private Subjects subject;

    public Subject(Subjects subject){
        this.subject = subject;
    }

    public Subjects getSubject(){
        return subject;
    }

    public String getSubjectInString(){
        return (subject.toString()).toLowerCase();
    }
}
