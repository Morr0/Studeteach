package archavexm.studeteach.core.util;

import archavexm.studeteach.core.common.Person;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.teacher.Teacher;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;

public class ObjectDeserializer {
    public static Person deserialize(String filePath) throws Exception{
        String content = Utilities.read(filePath);
        if (content.contains("student"))
            return deserializeStudent(filePath);
        else
            return deserializeTeacher(filePath);
    }

    public static Student deserializeStudent(String filePath) throws Exception{
        Serializer deserializer = new Persister();
        File file = new File(filePath);
        return deserializer.read(Student.class, file);
    }

    public static Teacher deserializeTeacher(String filePath) throws Exception{
        Serializer deserializer = new Persister();
        File file = new File(filePath);
        return deserializer.read(Teacher.class, file);
    }
}
