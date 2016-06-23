package archavexm.studeteach.core.common.task;

import org.simpleframework.xml.Root;

@Root(name = "type")
public enum TaskType {
    ASSIGNMENT,
    EXAM,
    HOMEWORK
}
