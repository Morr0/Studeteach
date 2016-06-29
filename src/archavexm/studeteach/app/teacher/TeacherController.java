package archavexm.studeteach.app.teacher;

import archavexm.studeteach.app.common.PersonController;
import archavexm.studeteach.core.teacher.Teacher;
import archavexm.studeteach.core.util.ObjectDeserializer;
import javafx.stage.Stage;

public class TeacherController implements PersonController {
    private String filePath;
    private Stage currentStage;
    private Teacher teacher;

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public void setCurrentStage(Stage currentStage){
        this.currentStage = currentStage;
    }

    public void init(){
        try {
            teacher = ObjectDeserializer.deserializeTeacher(filePath);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void toProfileEditor(){

    }

    public void toTodoList(){

    }

    private void toWindow(){

    }
}
