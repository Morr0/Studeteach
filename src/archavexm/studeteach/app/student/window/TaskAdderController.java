package archavexm.studeteach.app.student.window;

import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.student.subject.Subject;
import archavexm.studeteach.core.student.subject.Subjects;
import archavexm.studeteach.core.student.task.Task;
import archavexm.studeteach.core.student.task.TaskType;
import archavexm.studeteach.core.student.timetable.Day;
import archavexm.studeteach.core.student.timetable.Period;
import archavexm.studeteach.core.student.timetable.Timetable;
import archavexm.studeteach.core.util.ObjectDeserializer;
import archavexm.studeteach.core.util.ObjectSerializer;
import archavexm.studeteach.core.util.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

public class TaskAdderController {
    @FXML private ComboBox<String> comboSubject;
    @FXML private DatePicker pickerDueDate;
    @FXML private ComboBox<String> comboDuePeriod;

    private String filePath;
    private Student student;
    private Timetable timetable;

    private TaskType taskType;
    private Task task;
    private Day day;
    private Subject subject;
    private Period duePeriod;

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public void init(){
        try {
            student = ObjectDeserializer.deserializeStudent(filePath);
            timetable = student.getTimetables().get(0);

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void setDay(){
            if (pickerDueDate.getValue() != null)
                day = Utilities.toDayFromString(pickerDueDate.getValue().getDayOfWeek().toString());
    }

    public void refreshPeriods(){
        setDay();
        comboDuePeriod.getItems().clear();

        if (student.doesHaveThisDay(day)){
            LinkedList<Period> periods = timetable.getDayPeriods(day);
            ObservableList<String> strings = FXCollections.observableArrayList();

            for (Period period: periods){
                if (period.getSubject().getSubject() != Subjects.NONE){
                    strings.add((period.getNumber()) + " - " + period.getSubject().toString());
                }
            }

            comboDuePeriod.setItems(strings);
        } else {
            pickerDueDate.setValue(null);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("The day you chose is not one of your school days.");
            alert.showAndWait();

            return;
        }
    }

    public void setTaskType(TaskType taskType){
        this.taskType = taskType;
    }

    public void save(){
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            if (comboSubject.getValue() == "Select a Subject:"){
                alert.setContentText("You must choose the subject you are tested on.");
                alert.showAndWait();

                return;
            } else
                subject = new Subject(Utilities.toSubjectsFromString(comboSubject.getValue()));

            if (pickerDueDate.getValue() == null) {
                alert.setContentText("You must provide the due date.");
                alert.showAndWait();

            return;
            }

            LocalDate d = pickerDueDate.getValue();
            Date dueDate = Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());

            if (comboDuePeriod.getValue() == "Select a Due Period:"){
                if (timetable.containsPeriodOnDay(subject, day)) {
                    int periodNumber = timetable.getPeriodNumber(day, subject.getSubject());
                    for (Period p : timetable.getDayPeriods(day)) {
                        if (p.getNumber() == periodNumber)
                            duePeriod = new Period(subject, periodNumber);
                    }
                } else {
                    alert.setContentText("You must select the due period.");
                    alert.showAndWait();

                    return;
                }
            } else {
                String pn;
                int periodNumber;
                Subjects subj = null;
                try {
                    pn = comboDuePeriod.getSelectionModel().getSelectedItem().substring(0, 1);
                    periodNumber = Integer.parseInt(pn);
                } catch (NullPointerException ex){
                    return;
                }

                for (Period period: timetable.getDayPeriods(day)) {
                    if (period.getNumber() == periodNumber) {
                        subj = period.getSubject().getSubject();
                        break;
                    }
                }

                duePeriod = new Period(new Subject(subj), periodNumber);
            }

            task = new Task(taskType, subject, dueDate, duePeriod, day);
            LinkedList<Task> tasks = student.getTasks();

            int id = new Random().nextInt(9999);
            for (Task t: tasks){
                if (t.getTaskId() == id){
                    id = new Random().nextInt(9999);
                }
            }

            task.setTaskId(id);
            tasks.add(task);
            student.setTasks(tasks);

            ObjectSerializer.serializeStudent(filePath, student);

            Stage currentStage = (Stage) comboDuePeriod.getScene().getWindow();
            currentStage.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}