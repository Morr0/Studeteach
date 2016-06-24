package archavexm.studeteach.app;

import archavexm.studeteach.app.common.AboutController;
import archavexm.studeteach.app.student.StudentController;
import archavexm.studeteach.core.Studeteach;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.util.ObjectDeserializer;
import archavexm.studeteach.core.util.ObjectSerializer;
import archavexm.studeteach.core.util.Utilities;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ToMainMenuController {
    @FXML private Label labelTitle;

    private String person = "Student";
    private String filePath;

    public void setLabelTitle(String person){
        this.person = person;
        labelTitle.setText(person);
    }

    public void newUser(){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root;
            if (person.equals("Student"))
                root = loader.load(getClass().getResource("student/NewStudent.fxml"));
            else
                root = loader.load(getClass().getResource("teacher/NewTeacher.fxml"));

            Stage stage = new Stage();
            stage.getIcons().add(new Image(Studeteach.APP_ICON));
            stage.setScene(new Scene(root));
            stage.setTitle("New " + person + " - " + Studeteach.APP_NAME);
            stage.show();
        } catch (IOException ex){
            ex.printStackTrace();
        }

        Stage current = (Stage)labelTitle.getScene().getWindow();
        current.close();
    }

    public void openAboutWindow(){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent aboutWindow = loader.load(AboutController.class.getResource("About.fxml"));
            Stage currentStage = (Stage) labelTitle.getScene().getWindow();

            Stage stage = new Stage();
            stage.getIcons().add(new Image(Studeteach.APP_ICON));
            stage.initOwner(currentStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("About - " + Studeteach.APP_NAME);
            stage.setScene(new Scene(aboutWindow));
            stage.showAndWait();

        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void openUser(){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open .studeteach file");

            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Studeteach files", "*.studeteach");
            fileChooser.getExtensionFilters().add(extensionFilter);

            Stage currentStage = (Stage) labelTitle.getScene().getWindow();
            File file = fileChooser.showOpenDialog(currentStage);
            filePath = file.getAbsolutePath();

            if (Utilities.isValidStudeteachFile(filePath)){
                String content = Utilities.read(filePath);
                if (person == "Student") {
                    if (content.contains("student"))
                        makeWindow(filePath);
                    else if (content.contains("Teacher")) {
                        person = "Teacher";
                        return;
                    }
                } else {
                    if (content.contains("Teacher"))
                        makeWindow(filePath);
                    else if (content.contains("Student")) {
                        person = "Student";
                        return;
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The file you are trying to open is not a valid .studeteach file.");
                alert.showAndWait();

                return;
            }
        } catch (NullPointerException ex){
           return;
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void organisePerson() throws Exception{
        Student student = ObjectDeserializer.deserializeStudent(filePath);
        student.organiseTimetables();
        ObjectSerializer.serializeStudent(filePath, student);
    }

    private void makeWindow(String filePath){
        try {
            organisePerson();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("student/Student.fxml").openStream());

            StudentController sc = loader.getController();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(Studeteach.APP_ICON));
            stage.setScene(new Scene(root));

            sc.setFilePath(filePath);
            sc.setCurrentStage(stage);
            sc.initStudent();
            stage.show();
        } catch (Exception ex){
            ex.printStackTrace();
        }

        Stage currentStage = (Stage)labelTitle.getScene().getWindow();
        currentStage.close();
    }

}
