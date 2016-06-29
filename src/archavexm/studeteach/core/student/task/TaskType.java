package archavexm.studeteach.core.student.task;

import org.simpleframework.xml.Root;

@Root(name = "type")
public enum TaskType {
    ASSIGNMENT,
    EXAM,
    HOMEWORK
}
