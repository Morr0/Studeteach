package archavexm.studeteach.app.student.window;

import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.student.subject.Subject;
import archavexm.studeteach.core.student.subject.Subjects;
import archavexm.studeteach.core.student.task.Assignment;
import archavexm.studeteach.core.student.task.Exam;
import archavexm.studeteach.core.student.task.Homework;
import archavexm.studeteach.core.student.task.Task;
import archavexm.studeteach.core.student.timetable.Day;
import archavexm.studeteach.core.student.timetable.Period;

import archavexm.studeteach.core.util.Deserializer;
import archavexm.studeteach.core.util.Utilities;
import javafx.application.Platform;
import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;

public class TaskAdderController {
    @FXML
    private ComboBox<String> comboSubject;
    @FXML
    private DatePicker pickerDueDate;
    @FXML
    private ComboBox<String> comboDay;
    @FXML
    private ComboBox<Period> comboDuePeriod;

    private String filePath;
    private String typeOfTask;

    private Student student;

    private Assignment assignment;
    private Exam exam;
    private Homework homework;

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public void setTypeOfTask(String typeOfTask){
        this.typeOfTask = typeOfTask;
    }

    public void init(){
        try {
            student = Deserializer.deserializeStudent(filePath);

            for (Day d: student.getSchoolDays()){
                String ddd = Utilities.capitalizeFirstLetter((d.toString()).toLowerCase());
                comboDay.getItems().add(ddd);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void setAssignment(Assignment assignment){
        this.assignment = assignment;
    }

    public void setExam(Exam exam){
        this.exam = exam;
    }

    public void setHomework(Homework homework){
        this.homework = homework;
    }

    public void save(){
        try {
            Task task = null;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            if (typeOfTask == "Assignment"){
                task = assignment;
            }
            else if (typeOfTask == "Exam"){
                task = exam;
            }
            else {
                task = homework;
            }

            Subject subject = null;

            // Checking for the subject
            {
                if (comboSubject.getValue() == null){
                    alert.setContentText("You must select the subject you are test/assessed on.");
                    alert.showAndWait();

                    return;
                }

                String subj = comboSubject.getSelectionModel().getSelectedItem();
                if (subj == "language"){
                    subject = new Subject(Subjects.LANGUAGE);
                }
                else if (subj == "Mathematics"){
                    subject = new Subject(Subjects.MATHEMATICS);
                }
                else if (subj == "Science"){
                    subject = new Subject(Subjects.SCIENCE);
                }
                else if (subj == "Sport"){
                    subject = new Subject(Subjects.SPORT);
                }
                else if (subj == "Computing"){
                    subject = new Subject(Subjects.COMPUTING);
                }
                else if (subj == "Geography"){
                    subject = new Subject(Subjects.GEOGRAPHY);
                }
                else if (subj == "History"){
                    subject = new Subject(Subjects.HISTORY);
                }
                else if (subj == "Commerce"){
                    subject = new Subject(Subjects.COMMERCE);
                }
                else if (subj == "Art"){
                    subject = new Subject(Subjects.ART);
                }
                else if (subj == "Music") {
                    subject = new Subject(Subjects.MUSIC);
                }
                else if (subj.isEmpty()) {
                    alert.setContentText("You must select the subject you are tested/assessed on.");
                    alert.showAndWait();

                    return;
                }
            }

            LocalDate dueDate = null;

            if (pickerDueDate.getValue() == null) {
            alert.setContentText("You must provide the due date.");
            alert.showAndWait();

            return;
            }

            dueDate = pickerDueDate.getValue();

            Day day = null;

            // Checks if the date is for that day and checks if the student has this day in the school days
            {
                if (comboDay.getValue() == null){
                    day = Utilities.toDayFromString(dueDate.getDayOfWeek().toString());
                }
                else {
                    day = Utilities.toDayFromString(comboDay.getValue());
                }

                if (day != (Utilities.toDayFromString(dueDate.getDayOfWeek().toString()))){
                    alert.setContentText("You have provided the wrong day. Can you please correct it.");
                    alert.showAndWait();

                    return;
                }
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}











