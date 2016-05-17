package archavexm.studeteach.core.util;

import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.teacher.Teacher;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;

public class ObjectDeserializer {
    public static Student deserializeStudent(String filePath) throws Exception{
        Serializer deserializer = new Persister();
        File file = new File(filePath);

        Student student = deserializer.read(Student.class, file);
        return student;
    }

    public static Teacher deserializeTeacher(String filePath) throws Exception{
        Serializer deserializer = new Persister();
        File file = new File(filePath);

        Teacher teacher = deserializer.read(Teacher.class, file);
        return teacher;
    }
}
