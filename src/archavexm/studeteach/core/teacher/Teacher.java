package archavexm.studeteach.core.teacher;

import archavexm.studeteach.core.common.Person;
import archavexm.studeteach.core.common.subject.Subject;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "teacher")
public class Teacher implements Person {
    private static Teacher teacher = new Teacher();


    @Attribute(name = "first_name")
    private String firstName;
    @Attribute(name = "last_name")
    private String lastName;
    @Element
    private Subject specializedSubject;

    private Teacher() {}

    public static Teacher getTeacher(String firstName, String lastName){
        teacher.firstName = firstName;
        teacher.lastName = lastName;

        return teacher;
    }


}
