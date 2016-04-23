package archavexm.studeteach.app.student.window;


import archavexm.studeteach.core.student.SchoolType;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.student.timetable.Day;
import archavexm.studeteach.core.util.Deserializer;

import archavexm.studeteach.core.util.Serializer;
import archavexm.studeteach.core.util.Utilities;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashSet;

public class ProfileEditorController {
    @FXML
    private TextField textFirstName;
    @FXML
    private TextField textLastName;
    @FXML
    private TextField textPreferedName;
    @FXML
    private TextField textAge;
    @FXML
    private TextField textYear;
    @FXML
    private TextField textSchoolName;
    @FXML
    private TextField textSchoolType;

    // Day checkboxes
    @FXML
    private CheckBox checkMonday;
    @FXML
    private CheckBox checkTuesday;
    @FXML
    private CheckBox checkWednesday;
    @FXML
    private CheckBox checkThursday;
    @FXML
    private CheckBox checkFriday;
    @FXML
    private CheckBox checkSaturday;
    @FXML
    private CheckBox checkSunday;

    private String filePath;

    private Student oStudent;
    private Student student;


    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public void setStudent(Student student){
        oStudent = student;
    }

    public void init(){
        try {
            textFirstName.setText(oStudent.getFirstName());
            textLastName.setText(oStudent.getLastName());
            textPreferedName.setText(oStudent.getPreferedName());
            textAge.setText(Integer.toString(oStudent.getAge()));

            if (!(oStudent.getSchoolYear() == 0)){
                textYear.setText(Integer.toString(oStudent.getSchoolYear()));
            }

            textSchoolName.setText(oStudent.getSchoolName());

            String content = Utilities.read(filePath);
            if (content.contains("Primary")){
                textSchoolType.setText("Primary");
            }
            else if (content.contains("Secondary")){
                textSchoolType.setText("Secondary");
            }
            else if (content.contains("University")){
                textSchoolType.setText("University");
            }

            HashSet<Day> schoolDays = oStudent.getSchoolDays();
            if (!(schoolDays.isEmpty())) {
                for (Day day: schoolDays){
                    String name = day.toString().toLowerCase();

                    switch (name){
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

            student = Deserializer.deserializeStudent(filePath);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void save(){
        String firstName = null;
        String lastName = null;
        String preferedName = null;
        int age = 0;
        int year = 0;
        String schoolName = null;
        SchoolType schoolType = null;
        HashSet<Day> schoolDays = new HashSet<>(7);

        Alert alert = new Alert(Alert.AlertType.WARNING);

        if (textFirstName.getText().isEmpty()){
            alert.setContentText("You must provide your first name. Otherwise you cannot proceed.");
            alert.showAndWait();

            return;
        }

        firstName = textFirstName.getText();
        lastName = textLastName.getText();
        preferedName = textPreferedName.getText();

        if (!(Utilities.isDigit(textAge.getText()))){
            alert.setContentText("You must provide an integer in the age field.");
            alert.showAndWait();

            return;
        }
        else {
            age = Integer.parseInt(textAge.getText());
        }

        if (age == 0){
            alert.setContentText("You must provide your age. It must not be less than 5 or more than 75.");
            alert.showAndWait();

            return;
        }
        else if (age < 5){
            alert.setContentText("Your age must be over 5 years old.");
            alert.showAndWait();

            return;
        }
        else if (age > 75){
            alert.setContentText("Your age must be less than 75 years old.");
            alert.showAndWait();

            return;
        }

        if (!(Utilities.isDigit(textYear.getText()))){
            alert.setContentText("You must provide a non-negative number in the year field and it must not exceed 12.");
        }
        else {
            year = Integer.parseInt(textYear.getText());
        }
        if (year == 0){
            alert.setContentText("You must provide the year you are studying in.");
            alert.showAndWait();

            return;
        }
        else if (year > 12 || year < 0){
            alert.setContentText("You should provide the correct year you are studying in.");
            alert.showAndWait();

            return;
        }

        if (textSchoolName.getText().isEmpty()){
            alert.setContentText("You must provide the name of your school.");
            alert.showAndWait();

            return;
        }
        schoolName = textSchoolName.getText();

        String st = textSchoolType.getText().toLowerCase();

        switch (st){
            case "primary":
                schoolType = SchoolType.PRIMARY;
                break;
            case "secondary":
                schoolType = SchoolType.SECONDARY;
                break;
            case "university":
                schoolType = SchoolType.UNIVERSITY;
                break;
            default:
                alert.setContentText("You must provide the level of education like primary or secondary or university.");
                alert.showAndWait();

                return;
        }

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

        alert = null;

        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setPreferedName(preferedName);
        student.setAge(age);
        student.setSchoolYear(year);
        student.setSchoolName(schoolName);

        student.setSchoolType(schoolType);

        student.setSchoolDays(schoolDays);

        try {
            Serializer.serializeStudent(filePath, student);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        Stage currentStage = (Stage)textSchoolType.getScene().getWindow();
        currentStage.close();

    }
}















