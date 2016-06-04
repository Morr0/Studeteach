package archavexm.studeteach.app;

import archavexm.studeteach.app.student.StudentController;
import archavexm.studeteach.core.Studeteach;
import archavexm.studeteach.core.util.Utilities;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ToMainMenuController {
    @FXML
    private Label labelTitle;

    private String person;

    public void setLabelTitle(String person){
        this.person = person;
        labelTitle.setText(person);
    }

    public void newUser() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("NewUser.fxml").openStream());
        NewUserController nuc = (NewUserController)loader.getController();
        nuc.setPersonText(person);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("New " + person + " - " + Studeteach.APP_NAME);
        stage.show();

        Stage current = (Stage)labelTitle.getScene().getWindow();
        current.close();
    }

    public void openUser(){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open .studeteach file");

            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Studeteach files", "*.studeteach");
            fileChooser.getExtensionFilters().add(extensionFilter);

            Stage currentStage = (Stage) labelTitle.getScene().getWindow();
            File file = fileChooser.showOpenDialog(currentStage);

            String filePath = file.getAbsolutePath();

            String content = null;
            content = Utilities.read(filePath);

            if (person == "Student") {
                if (content.contains("student")) {
                    makeWindow(filePath);
                } else if (content.contains("Teacher")) {
                    person = "Teacher";
                    return;
                }
            } else {
                if (content.contains("Teacher")) {
                    makeWindow(filePath);
                } else if (content.contains("Student")) {
                    person = "Student";
                    return;
                }
            }
        } catch (NullPointerException ex){
            return;
        } catch (IOException ex){
            ex.printStackTrace();
        }

    }

    private void makeWindow(String filePath){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("student/Student.fxml").openStream());

            StudentController sc = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            sc.setFilePath(filePath);
            sc.setCurrentStage(stage);
            sc.initStudent();
            stage.show();

            Stage currentStage = (Stage)labelTitle.getScene().getWindow();
            currentStage.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

}
