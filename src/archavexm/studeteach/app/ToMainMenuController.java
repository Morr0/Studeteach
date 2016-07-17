package archavexm.studeteach.app;

import archavexm.studeteach.app.common.AboutController;
import archavexm.studeteach.app.common.PersonController;
import archavexm.studeteach.app.common.Studeteach;
import archavexm.studeteach.core.common.Person;
import archavexm.studeteach.core.util.ObjectDeserializer;
import archavexm.studeteach.core.util.ObjectSerializer;
import archavexm.studeteach.core.util.Utilities;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ToMainMenuController {
    @FXML private Label labelTitle;

    private String filePath;
    private Person.PersonType personType;

    public void newUser(){
        Alert studentOrTeacherDialog = new Alert(Alert.AlertType.CONFIRMATION);
        studentOrTeacherDialog.setContentText("If you want to create a new Student profile please press the Student button. If you do not want to create a " +
                "Student profile you can create a Teacher profile for teacher by just pressing the Teacher button. " +
                "Otherwise you can just press the Cancel button.");

        ButtonType studentButton = new ButtonType("Student");
        ButtonType teacherButton = new ButtonType("Teacher");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        studentOrTeacherDialog.getButtonTypes().setAll(studentButton, teacherButton, cancelButton);
        Optional<ButtonType> selectedPerson = studentOrTeacherDialog.showAndWait();

        if (!selectedPerson.get().getText().equals("Cancel")){
            try {
                FXMLLoader loader = new FXMLLoader();
                String person = null;
                Parent root = null;
                if (selectedPerson.get().getText().equals("Student")){
                    root = loader.load(getClass().getResource("student/NewStudent.fxml"));
                    person = "Student";
                } else if (selectedPerson.get().getText().equals("Teacher")){
                    root = loader.load(getClass().getResource("teacher/NewTeacher.fxml"));
                    person = "Teacher";
                }

                Stage stage = new Stage();
                stage.getIcons().add(new Image(Studeteach.APP_ICON));
                stage.setScene(new Scene(root != null ? root : null));
                stage.setTitle("New " + person + " - " + Studeteach.APP_NAME);
                stage.show();
            } catch (IOException ex){
                ex.printStackTrace();
            }

            Stage current = (Stage)labelTitle.getScene().getWindow();
            current.close();
        }
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
                String content = Utilities.readFirstLine(filePath);
                Person.PersonType personType;
                if (content.contains("student"))
                    personType = Person.PersonType.STUDENT;
                else
                    personType = Person.PersonType.TEACHER;

                makeWindow(filePath, personType);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The file you are trying to open is not a valid .studeteach file.");
                alert.showAndWait();

            }
        } catch (NullPointerException ex){
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void organisePerson() throws Exception{
        Person person = ObjectDeserializer.deserialize(filePath);
        person.organiseTimetables();
        ObjectSerializer.serialize(filePath, person);
    }

    private void makeWindow(String filePath, Person.PersonType personType){
        try {
            organisePerson();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(PersonController.class.getResource("Person.fxml").openStream());

            PersonController personController = loader.getController();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(Studeteach.APP_ICON));
            stage.setScene(new Scene(root));

            personController.setFilePath(filePath);
            personController.setCurrentStage(stage);
            personController.setPersonType(personType);
            personController.init();
            stage.show();
        } catch (Exception ex){
            ex.printStackTrace();
        }

        Stage currentStage = (Stage)labelTitle.getScene().getWindow();
        currentStage.close();
    }
}
