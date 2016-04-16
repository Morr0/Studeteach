package archavexm.studeteach.core.util;

import archavexm.studeteach.core.student.Student;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;

public final class Deserializer {
    public static Student deserializeStudent(String filePath) throws Exception{
        JAXBContext jc = JAXBContext.newInstance(Student.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();

        FileInputStream fis = new FileInputStream(filePath);
        Student output = (Student)unmarshaller.unmarshal(fis);
        return output;
    }

    public static void deserializeTeacher(String filePath){

    }
}
