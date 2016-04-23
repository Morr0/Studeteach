package archavexm.studeteach.app.student;

import archavexm.studeteach.app.student.window.ProfileEditorController;
import archavexm.studeteach.app.student.window.TaskManagerController;
import archavexm.studeteach.core.Studeteach;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.util.Deserializer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

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
            setPreferedName();
            labelName.setText(preferedName);
            labelAge.setText(Integer.toString(student.getAge()));
            labelYear.setText(Integer.toString(student.getSchoolYear()));
            updateNumberOfTasks();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void showProfileEditor(){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent profileEditor = loader.load(getClass().getResource("window/ProfileEditor.fxml").openStream());

            ProfileEditorController profileEditorController = loader.getController();
            profileEditorController.setFilePath(filePath);
            profileEditorController.setStudent(student);
            profileEditorController.init();

            Stage stage = new Stage();
            stage.setTitle(preferedName + " - " + "Profile Editor" + " - " + Studeteach.APP_NAME);
            stage.setScene(new Scene(profileEditor));
            stage.showAndWait();

            refresh();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void toTaskManager(){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent taskManager = loader.load(getClass().getResource("window/TaskManager.fxml").openStream());

            TaskManagerController taskManagerController = loader.getController();
            taskManagerController.setFilePath(filePath);
            taskManagerController.setOldStudent(student);
            taskManagerController.init();

            Stage stage = new Stage();
            stage.setTitle(preferedName + " - " + "Task Manager" + " - " + Studeteach.APP_NAME);
            stage.setScene(new Scene(taskManager));
            stage.showAndWait();

            refresh();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void refresh(){
        try {
            student = Deserializer.deserializeStudent(filePath);
            setPreferedName();
            setTitle();
            updateNumberOfTasks();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void setPreferedName(){
        if (student.getPreferedName().isEmpty()){
            stageTitle = (student.getFirstName()) + " - " + Studeteach.APP_NAME;
            preferedName = student.getFirstName();
        }
        else {
            stageTitle = (student.getPreferedName()) + " - " + Studeteach.APP_NAME;
            preferedName = student.getPreferedName();
        }
    }

    private void setTitle(){
        currentStage = (Stage)labelName.getScene().getWindow();
        currentStage.setTitle(stageTitle);
    }

    private void updateNumberOfTasks(){
        String num = null;

        if (student.getTasks() == null){
            num = "0";
        }
        else {
            num = Integer.toString(student.getTasks().size());
        }

        labelNumberOfTasks.setText(num);
    }
}

















