package archavexm.studeteach.core.student.task;

import archavexm.studeteach.core.student.subject.Subject;
import archavexm.studeteach.core.student.timetable.Day;
import archavexm.studeteach.core.student.timetable.Period;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.time.LocalDate;

@Root
public abstract class Task {
    @Attribute
    protected TaskType type;
    @Attribute
    protected Subject subject;
    @Attribute
    protected LocalDate dueDate;
    @Element
    protected Period duePeriod;
    @Attribute
    protected Day dueDay;

    public abstract TaskType getType();
    public abstract Subject getSubject();
    public abstract LocalDate getDueDate();
    public abstract Period getDuePeriod();
    public abstract Day getDueDay();

    public abstract void setType(TaskType type);
    public abstract void setSubject(Subject subject);
    public abstract void setDueDate(LocalDate dueDate);
    public abstract void setDuePeriod(Period duePeriod);
    public abstract void setDueDay(Day dueDay);
}
