package archavexm.studeteach.core.student.task;

import archavexm.studeteach.core.student.subject.Subject;
import archavexm.studeteach.core.student.timetable.Day;
import archavexm.studeteach.core.student.timetable.Period;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@XmlRootElement(name = "task")
public class Assignment extends Task {
    @Override
    @XmlAttribute(name = "due_day")
    public Day getDueDay() {
        return null;
    }

    @Override
    @XmlAttribute(name = "type")
    public TaskType getType() {
        return type;
    }

    @Override
    @XmlAttribute(name = "subject")
    public Subject getSubject() {
        return subject;
    }

    @Override
    @XmlAttribute(name = "due_date")
    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    @XmlAttribute(name = "due_period")
    public Period getDuePeriod() {
        return duePeriod;
    }

    @Override
    public void setType(TaskType type) {
        this.type = type;
    }

    @Override
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void setDuePeriod(Period duePeriod) {
        this.duePeriod = duePeriod;
    }

    @Override
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public void setDueDay(Day dueDay) {
        this.dueDay = dueDay;
    }
}
