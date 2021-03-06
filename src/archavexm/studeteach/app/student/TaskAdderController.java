package archavexm.studeteach.app.student;

import archavexm.studeteach.core.common.Day;
import archavexm.studeteach.core.common.subject.Subject;
import archavexm.studeteach.core.common.subject.Subjects;
import archavexm.studeteach.core.common.timetable.Period;
import archavexm.studeteach.core.common.timetable.Timetable;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.student.task.Task;
import archavexm.studeteach.core.student.task.TaskType;
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
import java.util.Objects;
import java.util.Random;

public class TaskAdderController {
    @FXML private ComboBox<String> comboSubject;
    @FXML private DatePicker pickerDueDate;
    @FXML private ComboBox<String> comboDuePeriod;

    private String filePath;
    private Student student;
    private Timetable timetable;

    private TaskType taskType;
    private Day day;
    private Period duePeriod;

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public void init(){
        try {
            student = ObjectDeserializer.deserializeStudent(filePath);
            timetable = student.getPrimaryTimetable();
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
        LocalDate d = pickerDueDate.getValue();
        Day dayByDate = Utilities.toDayFromString(d.getDayOfWeek().toString());
        if (timetable.getDayPeriods(dayByDate).size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            drawAlert("You do not have any periods on the selected day. Please select the correct day.");
            return;
        }

        setDay();
        comboDuePeriod.getItems().clear();

        if (student.doesHaveThisDay(day)){
            LinkedList<Period> periods = timetable.getDayPeriods(day);
            ObservableList<String> strings = FXCollections.observableArrayList();

            for (Period period: periods)
                if (period.getSubject().getSubject() != Subjects.NONE)
                    strings.add((period.getNumber()) + " - " + period.getSubject().toString());

            comboDuePeriod.setItems(strings);
        } else {
            pickerDueDate.setValue(null);
            drawAlert("The day you chose is not one of your school days.");
            return;
        }
    }

    public void setTaskType(TaskType taskType){
        this.taskType = taskType;
    }

    public void save(){
        try {
            Subject subject;
            if (Objects.equals(comboSubject.getValue(), "Select a Subject:")){
                drawAlert("You must choose the subject you are tested on.");
                return;
            } else
                subject = new Subject(Utilities.toSubjectsFromString(comboSubject.getValue()));

            if (pickerDueDate.getValue() == null) {
                drawAlert("You must provide the due date.");
                return;
            }

            LocalDate date = pickerDueDate.getValue();
            LocalDate currentDate = LocalDate.now();
            if (date.isBefore(currentDate)){
                drawAlert("The date you have chosen is in the past.");
                return;
            }

            Date dueDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (Objects.equals(comboDuePeriod.getValue(), "Select a Due Period:")){
                if (timetable.containsPeriodOnDay(subject, day)) {
                    int periodNumber = timetable.getPeriodNumber(day, subject.getSubject());
                    for (Period p : timetable.getDayPeriods(day)) {
                        if (p.getNumber() == periodNumber)
                            duePeriod = new Period(subject, periodNumber);
                    }
                } else {
                    drawAlert("You must select the due period.");
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
                    drawAlert("You should select the period you are having an exam on.");
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

            Task task = new Task(taskType, subject, dueDate, duePeriod, day);
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
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void drawAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
