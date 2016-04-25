package archavexm.studeteach.app.student;

import archavexm.studeteach.app.student.window.ProfileEditorController;
import archavexm.studeteach.app.student.window.TaskManagerController;
import archavexm.studeteach.app.student.window.TimetableEditorController;
import archavexm.studeteach.core.Studeteach;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.student.timetable.Timetable;
import archavexm.studeteach.core.util.Deserializer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
    @FXML
    private TableView tableTimetable;

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
            timetable = student.getTimetable();
            setPreferedName();
            labelName.setText(preferedName);
            labelAge.setText(Integer.toString(student.getAge()));
            labelYear.setText(Integer.toString(student.getSchoolYear()));
            if (timetable.isTimetableEmpty()){
                VBox verticalBox = new VBox();
                verticalBox.setAlignment(Pos.CENTER);

                Button newTimetable = new Button("Create");
                newTimetable.setPrefWidth(60);
                newTimetable.setPrefHeight(40);
                newTimetable.setFont(new Font("Arial", 12));
                newTimetable.setOnAction(event -> {
                    if (student.getSchoolDays().size() == 0){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("You did not specify the school days in your profile, you have to specify the days then come back here.");
                        alert.showAndWait();

                        return;
                    }

                    try {
                        toTimetableEditor();
                    }
                    catch (IOException ex){
                        ex.printStackTrace();
                    }

                });

                verticalBox.getChildren().add(new Label("You Did not set your timetable before. If you want to set you can press the button below."));
                verticalBox.getChildren().add(newTimetable);
                tableTimetable.setPlaceholder(verticalBox);
            }
            else
                updateTimetable();

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
            updateTimetable();
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
}

















