package archavexm.studeteach.core.util;

import archavexm.studeteach.core.common.Person;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.teacher.Teacher;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;

public final class ObjectSerializer {
    public static void serialize(String filePath, Person person) throws Exception{
        if (person instanceof Student)
            serializeStudent(filePath, (Student) person);
        else
            serializeTeacher(filePath, (Teacher) person);
    }

    public static void serializeStudent(String filePath, Student student) throws Exception{
        Serializer serializer = new Persister();
        File file = new File(filePath);

        serializer.write(student, file);
    }

    public static void serializeTeacher(String filePath, Teacher teacher) throws Exception{
        Serializer serializer = new Persister();
        File file = new File(filePath);

        serializer.write(teacher, file);
    }
}
