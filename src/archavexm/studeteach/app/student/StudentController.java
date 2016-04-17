package archavexm.studeteach.app.student;

import archavexm.studeteach.core.Studeteach;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.util.Deserializer;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StudentController{
    @FXML
    private MenuBar menuBar;
    @FXML
    private Label labelName;
    @FXML
    private Label labelAge;
    @FXML
    private Label labelYear;
    @FXML
    private Label labelNumberOfTasks;

    private Student student;
    private String preferedName;

    private String filePath;
    private String stageTitle;

    private Stage currentStage;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void initStudent(){
        try {
            student = Deserializer.deserializeStudent(filePath);

            if (student.getPreferedName().isEmpty()){
                stageTitle = (student.getFirstName()) + " - " + Studeteach.APP_NAME;
                preferedName = student.getFirstName();
            }
            else {
                stageTitle = (student.getPreferedName()) + " - " + Studeteach.APP_NAME;
                preferedName = student.getPreferedName();
            }

            labelName.setText(preferedName);
            labelAge.setText(Integer.toString(student.getAge()));
            labelYear.setText(Integer.toString(student.getSchoolYear()));

            filePath = null;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
