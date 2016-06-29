package archavexm.studeteach.app.student;

import archavexm.studeteach.app.common.NewPersonBackButton;
import archavexm.studeteach.app.common.Studeteach;
import archavexm.studeteach.core.common.SchoolType;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.util.ObjectSerializer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

// Handles the creation of a new user
public class NewStudentController implements NewPersonBackButton{
    @FXML private TextField textFirstName;
    @FXML private TextField textLastName;
    @FXML private TextField textAge;
    @FXML private TextField textSchoolName;
    @FXML private ComboBox<String> comboSchoolType;
    @FXML private TextField textPreferedName;

    private String filePath;
    private Student student;

    @FXML private Button buttonBack;

    public void initialize(){
        init(buttonBack, textFirstName);
    }

    public void processNewPerson(){
        String firstName;
        String lastName;
        int age;
        String schoolName;
        SchoolType schoolType;
        String preferredName;
        Alert alert = new Alert(Alert.AlertType.ERROR);

        if (textFirstName.getText().trim().isEmpty()){
            alert.setContentText("You must provide your first name. Otherwise you cannot proceed.");
            alert.showAndWait();

            return;
        } else
            firstName = textFirstName.getText();
        lastName = textLastName.getText();

        try {
            age = Integer.parseInt(textAge.getText());

            if (age < 5){
                alert.setContentText("The age must be more than 5.");
                alert.showAndWait();

                return;
            } else if (age > 75){
                alert.setContentText("The age must be less than 75.");
                alert.showAndWait();

                return;
            }
        } catch (NumberFormatException ex){
            alert.setContentText("The age must be an integer. Note it must not contain any decimals. Numbers less than 6 are not accepted. Also numbers over 75 are not accepted.");
            alert.showAndWait();

            return;
        }

        if ((textSchoolName.getText().isEmpty()) || (textSchoolName.getText() == " ")){
            alert.setContentText("You must provide the name of the school you are studying at right now.");
            alert.showAndWait();

            return;
        } else
            schoolName = textSchoolName.getText();

        switch (comboSchoolType.getValue()){
            case "Primary":
                schoolType = SchoolType.PRIMARY;
                break;
            case "Secondary (High School)":
                schoolType = SchoolType.SECONDARY;
                break;
            case "University":
                schoolType = SchoolType.UNIVERSITY;
                break;
            default:
                alert.setContentText("You must choose the type of school you are studying at.");
                alert.showAndWait();

                return;
        }

        preferredName = textPreferedName.getText();
        try {
            student = Student.getStudent();
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setAge(age);
            student.setPreferredName(preferredName);
            student.setSchoolName(schoolName);
            student.setSchoolType(schoolType);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save .studeteach file");

            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Studeteach files", "*.studeteach");
            fileChooser.getExtensionFilters().add(extensionFilter);

            Stage currentStage = (Stage) textFirstName.getScene().getWindow();
            File file = fileChooser.showSaveDialog(currentStage);

            filePath = file.getAbsolutePath();
            ObjectSerializer.serializeStudent(filePath, student);
            toProfile();
        } catch (NullPointerException ex){
            return;
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void toProfile(){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent studentWindow = loader.load(getClass().getResource("Student.fxml").openStream());

            StudentController sc = loader.getController();
            sc.setFilePath(filePath);

            Stage stage = new Stage();
            stage.getIcons().add(new Image(Studeteach.APP_ICON));
            stage.setScene(new Scene(studentWindow));

            sc.setCurrentStage(stage);
            sc.init();
            stage.show();
        } catch (IOException ex){
            ex.printStackTrace();
        }

        Stage current = (Stage) textFirstName.getScene().getWindow();
        current.close();
    }
}