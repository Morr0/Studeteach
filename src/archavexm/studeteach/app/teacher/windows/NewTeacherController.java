package archavexm.studeteach.app.teacher.windows;

import archavexm.studeteach.app.common.NewPersonBackButton;
import archavexm.studeteach.app.common.Studeteach;
import archavexm.studeteach.app.teacher.TeacherController;
import archavexm.studeteach.core.common.SchoolType;
import archavexm.studeteach.core.common.subject.Subjects;
import archavexm.studeteach.core.teacher.Teacher;
import archavexm.studeteach.core.util.ObjectSerializer;
import archavexm.studeteach.core.util.Utilities;
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

public class NewTeacherController implements NewPersonBackButton{
    @FXML private TextField textFirstName;
    @FXML private TextField textLastName;
    @FXML private TextField textPreferredName;
    @FXML private TextField textSchoolName;

    @FXML private ComboBox<String> comboSubject;
    @FXML private ComboBox<String> comboSchoolType;

    @FXML private Button buttonBack;

    public void initialize(){
        init(buttonBack, textFirstName);
    }

    public void create(){
        String firstName = textFirstName.getText();
        String lastName = textLastName.getText();
        String preferredName = textPreferredName.getText();
        String schoolName = textSchoolName.getText();
        Subjects specializedSubject = null;
        SchoolType schoolType = null;
        Alert alert = new Alert(Alert.AlertType.ERROR);

        if (firstName.isEmpty()) {
            alert.setContentText("You must type in your first name");
            alert.showAndWait();

            return;
        }

        if (schoolName.isEmpty()){
            alert.setContentText("You must type in the name of the school you are working at");
            alert.showAndWait();

            return;
        }

        if (comboSubject.getValue() == null){
            alert.setContentText("You must choose your specialized teaching subject");
            alert.showAndWait();

            return;
        } else
            specializedSubject = Utilities.toSubjectsFromString(comboSubject.getSelectionModel().getSelectedItem());

        if (comboSchoolType.getValue() == null){
            alert.setContentText("You must choose the type of school you are teaching at");
            alert.showAndWait();

            return;
        } else
            schoolType = SchoolType.toSchoolType(comboSchoolType.getSelectionModel().getSelectedItem());

        Teacher teacher = Teacher.getTeacher();
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setPreferredName(preferredName);
        teacher.setSubject(specializedSubject);
        teacher.setSchoolName(schoolName);
        teacher.setSchoolType(schoolType);

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save .studeteach file");

            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Studeteach files", "*.studeteach");
            fileChooser.getExtensionFilters().add(extensionFilter);

            Stage currentStage = (Stage) textFirstName.getScene().getWindow();
            File file = fileChooser.showSaveDialog(currentStage);
            String filePath = file.getAbsolutePath();
            ObjectSerializer.serializeTeacher(filePath, teacher);

            FXMLLoader loader = new FXMLLoader();
            Parent teacherWindow = loader.load(TeacherController.class.getResource("Teacher.fxml").openStream());

            TeacherController teacherController = loader.getController();
            teacherController.setFilePath(filePath);
            teacherController.setCurrentStage(currentStage);
            teacherController.init();

            Stage stage = new Stage();
            stage.setTitle(teacher.getTitleName() + " - " + Studeteach.APP_NAME);
            stage.getIcons().add(new Image(Studeteach.APP_ICON));
            stage.setScene(new Scene(teacherWindow));
            stage.show();

            currentStage.close();
        } catch (NullPointerException ex){
            return;
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
