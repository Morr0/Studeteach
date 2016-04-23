package archavexm.studeteach.core.student.task;

import archavexm.studeteach.core.student.subject.Subject;
import archavexm.studeteach.core.student.timetable.Day;
import archavexm.studeteach.core.student.timetable.Period;

import javax.xml.bind.annotation.XmlAttribute;
import java.time.LocalDate;

public class Homework extends Task {
    @Override
    @XmlAttribute(name = "due_day")
    public Day getDueDay() {
        return null;
    }

    @Override
    public TaskType getType() {
        return null;
    }

    @Override
    public Subject getSubject() {
        return null;
    }

    @Override
    public LocalDate getDueDate() {
        return null;
    }

    @Override
    public Period getDuePeriod() {
        return null;
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
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public void setDuePeriod(Period duePeriod) {
        this.duePeriod = duePeriod;
    }

    @Override
    public void setDueDay(Day dueDay) {
        this.dueDay = dueDay;
    }
}
