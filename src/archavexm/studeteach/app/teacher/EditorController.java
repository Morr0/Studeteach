package archavexm.studeteach.app.teacher;

import archavexm.studeteach.app.common.PersonWindow;
import archavexm.studeteach.core.common.Day;
import archavexm.studeteach.core.teacher.Teacher;
import archavexm.studeteach.core.util.ObjectDeserializer;
import archavexm.studeteach.core.util.ObjectSerializer;
import archavexm.studeteach.core.util.Utilities;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashSet;

public class EditorController implements PersonWindow {
    @FXML private TextField textFirstName;
    @FXML private TextField textLastName;
    @FXML private TextField textPreferredName;
    @FXML private TextField textSchoolName;

    @FXML private ComboBox<String> comboSpecializedSubject;
    @FXML private ComboBox<String> comboSchoolType;

    // Day checkboxes
    @FXML private CheckBox checkMonday;
    @FXML private CheckBox checkTuesday;
    @FXML private CheckBox checkWednesday;
    @FXML private CheckBox checkThursday;
    @FXML private CheckBox checkFriday;
    @FXML private CheckBox checkSaturday;
    @FXML private CheckBox checkSunday;

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
        textPreferredName.setText(teacher.getPreferredName());
        textSchoolName.setText(teacher.getSchoolName());
        comboSchoolType.setValue(teacher.getSchoolType().toString());
        comboSpecializedSubject.setValue(teacher.getSubject().toString());

        HashSet<Day> schoolDays = teacher.getSchoolDays();
        if (!schoolDays.isEmpty())
            for (Day day : schoolDays) {
                String name = day.toString().toLowerCase();
                switch (name) {
                    case "monday":
                        checkMonday.setSelected(true);
                        break;
                    case "tuesday":
                        checkTuesday.setSelected(true);
                        break;
                    case "wednesday":
                        checkWednesday.setSelected(true);
                        break;
                    case "thursday":
                        checkThursday.setSelected(true);
                        break;
                    case "friday":
                        checkFriday.setSelected(true);
                        break;
                    case "saturday":
                        checkSaturday.setSelected(true);
                        break;
                    case "sunday":
                        checkSunday.setSelected(true);
                        break;
                }
            }
    }

    public void save(){
        HashSet<Day> schoolDays = new HashSet<>(7);

        if (textFirstName.getText().isEmpty()){
            drawAlert("You must provide in your first name.");
            return;
        } else
            teacher.setFirstName(textFirstName.getText());
        teacher.setLastName(textLastName.getText());
        teacher.setPreferredName(textPreferredName.getText().trim());

        if (textSchoolName.getText().isEmpty()){
            drawAlert("You must provide in the name of school you are teaching in.");
            return;
        } else
            teacher.setSchoolName(textSchoolName.getText());
        teacher.setSchoolType(Utilities.toSchoolTypeFromString(comboSchoolType.getSelectionModel().getSelectedItem()));
        teacher.setSubject(Utilities.toSubjectsFromString(comboSpecializedSubject.getSelectionModel().getSelectedItem()));

        if (checkMonday.isSelected()){
            schoolDays.add(Day.MONDAY);
        }
        if (checkTuesday.isSelected()){
            schoolDays.add(Day.TUESDAY);
        }
        if (checkWednesday.isSelected()){
            schoolDays.add(Day.WEDNESDAY);
        }
        if (checkThursday.isSelected()){
            schoolDays.add(Day.THURSDAY);
        }
        if (checkFriday.isSelected()){
            schoolDays.add(Day.FRIDAY);
        }
        if (checkSaturday.isSelected()){
            schoolDays.add(Day.SATURDAY);
        }
        if (checkSunday.isSelected()){
            schoolDays.add(Day.SUNDAY);
        }

        teacher.setSchoolDays(schoolDays);
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
