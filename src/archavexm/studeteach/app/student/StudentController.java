package archavexm.studeteach.app.student;

import archavexm.studeteach.app.student.window.ProfileEditorController;
import archavexm.studeteach.app.student.window.TaskManagerController;
import archavexm.studeteach.app.student.window.TimetableEditorController;
import archavexm.studeteach.core.Studeteach;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.student.timetable.Day;
import archavexm.studeteach.core.student.timetable.Period;
import archavexm.studeteach.core.student.timetable.Timetable;
import archavexm.studeteach.core.util.Deserializer;

import archavexm.studeteach.core.util.Serializer;
import archavexm.studeteach.core.util.Utilities;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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

    // Periods on days lists
    @FXML
    private ListView<String> listMonday;
    @FXML
    private ListView<String> listTuesday;
    @FXML
    private ListView<String> listWednesday;
    @FXML
    private ListView<String> listThursday;
    @FXML
    private ListView<String> listFriday;
    @FXML
    private ListView<String> listSaturday;
    @FXML
    private ListView<String> listSunday;

    private Student student;
    private Timetable timetable;
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

            if (student.getTimetables().isEmpty()){
                timetable = new Timetable();
            } else {

            }
        } catch (Exception ex){
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
        } catch (IOException ex){
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

    public void refresh(){
        try {
            student = Deserializer.deserializeStudent(filePath);
            setPreferedName();
            setTitle();
            updateNumberOfTasks();
            updateTimetable();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void setPreferedName(){
        if (student.getPreferedName() == null){
            stageTitle = (student.getFirstName()) + " - " + Studeteach.APP_NAME;
            preferedName = student.getFirstName();
        } else if (student.getPreferedName().isEmpty()){
            stageTitle = (student.getFirstName()) + " - " + Studeteach.APP_NAME;
            preferedName = student.getFirstName();
        } else {
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

    private void updateTimetable(){

    }

    private void toTimetableEditor() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        Parent timetableEditor = loader.load(getClass().getResource("window/TimetableEditor.fxml").openStream());

        TimetableEditorController timetableEditorController = loader.getController();
        timetableEditorController.setFilePath(filePath);
        timetableEditorController.init();

        Stage stage = new Stage();
        stage.setTitle(preferedName + " - " + "Timetable Editor" + " - " + Studeteach.APP_NAME);
        stage.setScene(new Scene(timetableEditor));
        stage.showAndWait();
    }

    // File menu
    public void fileSave(){
        try {
            Serializer.serializeStudent(filePath, student);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void fileSaveAs(){
        String anotherFilePath = null;
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save As .studeteach");

            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Studeteach files", "*.studeteach");
            fileChooser.setSelectedExtensionFilter(extensionFilter);

            Stage currentStage = (Stage)labelAge.getScene().getWindow();
            File file = fileChooser.showSaveDialog(currentStage);

            anotherFilePath = file.getAbsolutePath();

        } catch (Exception ex){
            return;
        }

        try {
            Serializer.serializeStudent(anotherFilePath, student);
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void fileCloseProfile(){

    }

    public void fileExit(){
        Platform.exit();
    }
}

















