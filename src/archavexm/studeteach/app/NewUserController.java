package archavexm.studeteach.app;

import archavexm.studeteach.app.student.StudentController;
import archavexm.studeteach.core.student.SchoolType;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.teacher.Teacher;
import archavexm.studeteach.core.util.ObjectSerializer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class NewUserController {
    @FXML
    private TextField textFirstName;
    @FXML
    private TextField textLastName;
    @FXML
    private TextField textAge;
    @FXML
    private TextField textSchoolName;
    @FXML
    private ComboBox<String> comboSchoolType;
    @FXML
    private TextField textPreferedName;

    private String personText;
    private String filePath;

    private Student student;
    private Teacher teacher;

    public void setPersonText(String text){
        personText = text;
    }

    public void processNewPerson(){
        String firstName = null;
        String lastName = null;
        int age = 0;
        String schoolName = null;
        SchoolType schoolType = null;
        String preferedName = null;

        Alert alert = new Alert(Alert.AlertType.WARNING);

        if (textFirstName.getText().trim().isEmpty()){
            alert.setContentText("You must provide your first name. Otherwise you cannot proceed.");
            alert.showAndWait();

            return;
        }
        else {
            firstName = textFirstName.getText();
        }
        lastName = textLastName.getText();

        try {
            age = Integer.parseInt(textAge.getText());

            if (age < 5){
                alert.setContentText("The age must be more than 5.");
                alert.showAndWait();

                return;
            }
            else if (age > 75){
                alert.setContentText("The age must be less than 75.");
                alert.showAndWait();

                return;
            }
        }
        catch (NumberFormatException ex){
            alert.setContentText("The age must be an integer. Note it must not contain any decimals. Numbers less than 6 are not accepted. Also numbers over 75 are not accepted.");
            alert.showAndWait();

            return;
        }

        if ((textSchoolName.getText().isEmpty()) || (textSchoolName.getText() == " ")){
            if (personText == "Student")
                alert.setContentText("You must provide the name of the school you are studying at right now.");
            else if (personText == "Teacher")
                alert.setContentText("You must provide the name of the school you are teaching at right now.");


            alert.showAndWait();

            return;
        } else
            schoolName = textSchoolName.getText();

        switch (comboSchoolType.getValue()){
            case "Primary":
                schoolType = SchoolType.PRIMARY;
                break;
            case "Secondary":
                schoolType = SchoolType.SECONDARY;
                break;
            case "University":
                schoolType = SchoolType.UNIVERSITY;
                break;
            default:
                if (personText == "Student"){
                    alert.setContentText("You must choose the type of school you are studying at.");
                }
                else if (personText == "Teacher"){
                    alert.setContentText("You must choose the type of school you are teaching at.");
                }

                alert.showAndWait();

                return;
        }

        preferedName = textPreferedName.getText();

        try {
            if (personText == "Student"){
                student = Student.getStudent();
                student.setFirstName(firstName);
                student.setLastName(lastName);
                student.setAge(age);
                student.setPreferredName(preferedName);
                student.setSchoolName(schoolName);
                student.setSchoolType(schoolType);
            }
            else if (personText == "Teacher"){

            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save .studeteach file");

            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Studeteach files", "*.studeteach");
            fileChooser.getExtensionFilters().add(extensionFilter);

            Stage currentStage = (Stage)textFirstName.getScene().getWindow();
            File file = fileChooser.showSaveDialog(currentStage);

            filePath = file.getAbsolutePath();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        try {
            if (personText == "Student"){
                ObjectSerializer.serializeStudent(filePath, student);
            }
            else {
                ObjectSerializer.serializeTeacher(filePath, teacher);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        try {
            toProfile();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void toProfile() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;

        if (personText == "Student"){
            root = loader.load(getClass().getResource("student/Student.fxml").openStream());
        }
        else {

        }

        StudentController sc = loader.getController();
        sc.setFilePath(filePath);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        sc.setCurrentStage(stage);
        sc.initStudent();
        stage.show();

        Stage current = (Stage)textFirstName.getScene().getWindow();
        current.close();
    }
}