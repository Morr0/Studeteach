package archavexm.studeteach.app.teacher.windows;

import archavexm.studeteach.app.common.PersonWindow;
import archavexm.studeteach.core.teacher.Teacher;
import archavexm.studeteach.core.util.ObjectDeserializer;
import archavexm.studeteach.core.util.ObjectSerializer;
import archavexm.studeteach.core.util.Utilities;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditorController implements PersonWindow {
    @FXML private TextField textFirstName;
    @FXML private TextField textLastName;
    @FXML private TextField textPreferredName;
    @FXML private TextField textSchoolName;

    @FXML private ComboBox<String> comboSpecializedSubject;
    @FXML private ComboBox<String> comboSchoolType;

    private String filePath;
    private Teacher teacher;

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void init() {
        try {
            teacher = ObjectDeserializer.deserializeTeacher(filePath);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        textFirstName.setText(teacher.getFirstName());
        textLastName.setText(teacher.getLastName());
        textPreferredName.setText(teacher.getFullName());
        textSchoolName.setText(teacher.getSchoolName());
        comboSchoolType.setValue(teacher.getSchoolType().toString());
        comboSpecializedSubject.setValue(teacher.getSubject().toString());
    }

    public void save(){
        Alert alert = new Alert(Alert.AlertType.ERROR);

        if (textFirstName.getText().isEmpty()){
            drawAlert("You must provide in your first name.");
            return;
        } else
            teacher.setFirstName(textFirstName.getText());
        teacher.setLastName(textLastName.getText());
        teacher.setPreferredName(textPreferredName.getText());

        if (textSchoolName.getText().isEmpty()){
            drawAlert("You must provide in the name of school you are teaching in.");
            return;
        } else
            teacher.setSchoolName(textSchoolName.getText());
        teacher.setSchoolType(Utilities.toSchoolTypeFromString(comboSchoolType.getSelectionModel().getSelectedItem()));
        teacher.setSubject(Utilities.toSubjectsFromString(comboSpecializedSubject.getSelectionModel().getSelectedItem()));

        try {
            ObjectSerializer.serializeTeacher(filePath, teacher);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        Stage currentStage = (Stage) textSchoolName.getScene().getWindow();
        currentStage.close();
    }

    private void drawAlert(String reason){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(reason);
        alert.showAndWait();
    }
}
