package archavexm.studeteach.app;

import archavexm.studeteach.core.Person;
import archavexm.studeteach.core.student.School;
import archavexm.studeteach.core.student.SchoolType;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.teacher.Teacher;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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

    private Person person;

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

        if (textSchoolName.getText() == "" || textSchoolName.getText() == " "){
            if (personText == "Student"){
                alert.setContentText("You must provide the name of the school you are studying at right now.");
            }
            else if (personText == "Teacher"){
                alert.setContentText("You must provide the name of the school you are teaching at right now.");
            }

            alert.showAndWait();

            return;
        }
        else {
            schoolName = textSchoolName.getText();
        }

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

        if (personText == "Student"){
            Student student = Student.getStudent(firstName, lastName);
            student.setAge(age);
            student.setPreferedName(preferedName);

            School school = School.getInstance(schoolName, schoolType);
            student.setSchool(school);

            person = student;
        }
        else {
            person = new Teacher();
        }

        System.out.println(person.toString());

    }
}

































