package archavexm.studeteach.app.teacher;

import archavexm.studeteach.core.teacher.Teacher;
import javafx.stage.Stage;

public class TeacherController {
    private String filePath;
    private Stage currentStage;
    private Teacher teacher;

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public void setCurrentStage(Stage currentStage){
        this.currentStage = currentStage;
    }

    public void initTeacher(){

    }
}
