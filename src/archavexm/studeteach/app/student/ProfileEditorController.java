package archavexm.studeteach.app.student;

import archavexm.studeteach.app.common.PersonWindow;
import archavexm.studeteach.core.common.Day;
import archavexm.studeteach.core.common.SchoolType;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.util.ObjectDeserializer;
import archavexm.studeteach.core.util.ObjectSerializer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashSet;

// The profile editor for the student
public class ProfileEditorController implements PersonWindow {
    @FXML private TextField textFirstName;
    @FXML private TextField textLastName;
    @FXML private TextField textPreferedName;
    @FXML private TextField textAge;
    @FXML private TextField textYear;
    @FXML private TextField textSchoolName;
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
    private Student student;

    @Override
    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    @Override
    public void init(){
        try {
            student = ObjectDeserializer.deserializeStudent(filePath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        textFirstName.setText(student.getFirstName());
        textLastName.setText(student.getLastName());
        textPreferedName.setText(student.getPreferredName());
        textAge.setText(Integer.toString(student.getAge()));

        if (student.getSchoolYear() != 0) {
            textYear.setText(Integer.toString(student.getSchoolYear()));
        }
        textSchoolName.setText(student.getSchoolName());

        String content = student.getSchoolType();
        if (content.contains("Primary"))
            comboSchoolType.setValue("Primary");
        else if (content.contains("Secondary"))
            comboSchoolType.setValue("Secondary (High School)");
        else if (content.contains("University"))
            comboSchoolType.setValue("University");

        HashSet<Day> schoolDays = student.getSchoolDays();
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
        String firstName;
        String lastName;
        String preferredName;
        int age;
        int year;
        String schoolName;
        SchoolType schoolType;
        HashSet<Day> schoolDays = new HashSet<>(7);

        if (textFirstName.getText().isEmpty()){
            drawAlert("You must provide your first name. Otherwise you cannot proceed.");
            return;
        }

        firstName = textFirstName.getText();
        lastName = textLastName.getText();
        preferredName = textPreferedName.getText().trim();

        try {
            age = Integer.parseInt(textAge.getText().trim());
            year = Integer.parseInt(textYear.getText().trim());
        } catch (NumberFormatException ex){
            drawAlert("You must provide a non-negative number in the year field and it must not exceed 12. You must also provide an integer in the age field.");
            return;
        }

        if (age == 0){
            drawAlert("You must provide your age. It must not be less than 5 or more than 75.");
            return;
        } else if (age < 5){
            drawAlert("Your age must be over 5 years old.");
            return;
        } else if (age > 75){
            drawAlert("Your age must be less than 75 years old.");
            return;
        }

        String st = comboSchoolType.getSelectionModel().getSelectedItem().toLowerCase();
        switch (st){
            case "primary":
                schoolType = SchoolType.PRIMARY;
                break;
            case "secondary (high school)":
                schoolType = SchoolType.SECONDARY;
                break;
            case "university":
                schoolType = SchoolType.UNIVERSITY;
                break;
            default:
                drawAlert("You must provide the level of education like primary or secondary or university.");
                return;
        }

        if (year <= 0 || year > 12){
            drawAlert("You must provide the correct year you are studying in.");
            return;
        } else {
            if (schoolType.equals(SchoolType.PRIMARY) && year > 6){
                drawAlert("You must provide the correct year you are studying in.");
                return;
            } else if (year <= 6 && schoolType.equals(SchoolType.SECONDARY)){
                drawAlert("You must provide the correct year you are studying in.");
                return;
            }
        }

        if (textSchoolName.getText().isEmpty()){
            drawAlert("You must provide the name of your school.");
            return;
        } else
            schoolName = textSchoolName.getText();

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

        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setPreferredName(preferredName);
        student.setAge(age);
        student.setSchoolYear(year);
        student.setSchoolName(schoolName);
        student.setSchoolType(schoolType);
        student.setSchoolDays(schoolDays);

        try {
            ObjectSerializer.serializeStudent(filePath, student);
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