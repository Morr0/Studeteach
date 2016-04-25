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

import java.io.*;

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
        String filePath = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open .studeteach file");

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Studeteach files", "*.studeteach");
        fileChooser.getExtensionFilters().add(extensionFilter);

        Stage currentStage = (Stage)labelTitle.getScene().getWindow();
        File file = fileChooser.showOpenDialog(currentStage);

        filePath = file.getAbsolutePath();

        String content = null;
        try {
            content = Utilities.read(filePath);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        try {
            if (person == "Student"){
                if (content.contains("student")){
                    makeWindow(filePath);
                }
                else if (content.contains("Teacher")){
                    person = "Teacher";
                    return;
                }
            }
            else {
                if (content.contains("teacher")){
                    makeWindow(filePath);
                }
                else if (content.contains("Student")) {
                    person = "Student";
                    return;
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void makeWindow(String filePath) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("student/Student.fxml").openStream());

        StudentController sc = loader.getController();
        sc.setFilePath(filePath);
        sc.initStudent();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        Stage currentStage = (Stage)labelTitle.getScene().getWindow();
        currentStage.close();
    }

}
