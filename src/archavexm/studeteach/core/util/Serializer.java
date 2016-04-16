package archavexm.studeteach.core.util;

import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.teacher.Teacher;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.FileOutputStream;

public final class Serializer {
    public static void serializeStudent(String filePath, Student student) throws Exception{
        JAXBContext jc = JAXBContext.newInstance(Student.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        FileOutputStream fos = new FileOutputStream(filePath);
        marshaller.marshal(student, fos);
    }

    public static void serializeTeacher(final String filePath, Teacher teacher){

    }
}
