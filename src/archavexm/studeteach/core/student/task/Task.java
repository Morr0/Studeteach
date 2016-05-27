package archavexm.studeteach.core.student.task;

import archavexm.studeteach.core.student.subject.Subject;
import archavexm.studeteach.core.student.timetable.Day;
import archavexm.studeteach.core.student.timetable.Period;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;

@Root
public class Task {
    @Element
    private TaskType type;
    @Element
    private Subject subject;
    @Attribute
    private Date dueDate;
    @Element
    private Period duePeriod;
    @Element
    private Day dueDay;
    @Attribute
    private int taskId;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public Task(){}

    public Task(TaskType taskType){
        type = taskType;
    }

    public Task(TaskType taskType, Subject subject, Date dueDate, Period duePeriod, Day dueDay){
        type = taskType;
        this.subject = subject;
        this.dueDate = dueDate;
        this.duePeriod = duePeriod;
        this.dueDay = dueDay;
    }

    public TaskType getType(){
        return type;
    }
    public Subject getSubject(){
        return subject;
    }
    public Date getDueDate(){
        return dueDate;
    }
    public Period getDuePeriod(){
        return duePeriod;
    }
    public Day getDueDay(){
        return dueDay;
    }

    public void setType(TaskType type){
        this.type = type;
    }
    public void setSubject(Subject subject){
        this.subject = subject;
    }
    public void setDueDate(Date dueDate){
        this.dueDate = dueDate;
    }
    public void setDuePeriod(Period duePeriod){
        this.duePeriod = duePeriod;
    }
    public void setDueDay(Day dueDay){
        this.dueDay = dueDay;
    }
}
