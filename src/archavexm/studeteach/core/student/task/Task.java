package archavexm.studeteach.core.student.task;

import archavexm.studeteach.core.student.subject.Subject;
import archavexm.studeteach.core.student.timetable.Day;
import archavexm.studeteach.core.student.timetable.Period;

import java.time.LocalDate;

public abstract class Task {
    protected TaskType type;
    protected Subject subject;
    protected LocalDate dueDate;
    protected Period duePeriod;
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
