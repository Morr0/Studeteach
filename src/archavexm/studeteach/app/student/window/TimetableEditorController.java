package archavexm.studeteach.app.student.window;

import archavexm.studeteach.app.student.StudentController;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.student.subject.Subject;
import archavexm.studeteach.core.student.subject.Subjects;
import archavexm.studeteach.core.student.timetable.Day;
import archavexm.studeteach.core.student.timetable.Period;
import archavexm.studeteach.core.student.timetable.Timetable;
import archavexm.studeteach.core.util.Deserializer;
import archavexm.studeteach.core.util.Serializer;
import archavexm.studeteach.core.util.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

public class TimetableEditorController {
    @FXML
    private TextField textTimetableName;
    @FXML
    private ComboBox<String> comboSchoolDays;

    // Text fields for periods
    @FXML
    private TextField textPeriod1;
    @FXML
    private TextField textPeriod2;
    @FXML
    private TextField textPeriod3;
    @FXML
    private TextField textPeriod4;
    @FXML
    private TextField textPeriod5;
    @FXML
    private TextField textPeriod6;
    @FXML
    private TextField textPeriod7;
    @FXML
    private TextField textPeriod8;
    @FXML
    private TextField textPeriod9;

    private String filePath;
    private Day selectedDay;

    private Student student;
    private Timetable timetable;

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public void init(){
        try {
            student = Deserializer.deserializeStudent(filePath);
            timetable = student.getTimetables().getFirst();

            if (timetable.getName() == null){
                timetable.setName("");
            } else {
                textTimetableName.setText(timetable.getName());
            }

            for (Day day: student.getSchoolDays()){
                comboSchoolDays.getItems().add(Utilities.capitalizeFirstLetter(day.toString()));
            }

            } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void updateContext(ActionEvent event){
        ComboBox<String> comboCurrent = (ComboBox<String>) event.getSource();
        selectedDay = Utilities.toDayFromString(comboCurrent.getValue());

        LinkedList<Period> periods = timetable.getDayPeriods(selectedDay);
        if (!periods.isEmpty()){
            for (Period p: periods){
                switch (p.getNumber()){
                    case 1:
                        textPeriod1.setText(p.getSubject().getSubjectInString());
                        break;
                    case 2:
                        textPeriod2.setText(p.getSubject().getSubjectInString());
                        break;
                    case 3:
                        textPeriod3.setText(p.getSubject().getSubjectInString());
                        break;
                    case 4:
                        textPeriod4.setText(p.getSubject().getSubjectInString());
                        break;
                    case 5:
                        textPeriod5.setText(p.getSubject().getSubjectInString());
                        break;
                    case 6:
                        textPeriod6.setText(p.getSubject().getSubjectInString());
                        break;
                    case 7:
                        textPeriod7.setText(p.getSubject().getSubjectInString());
                        break;
                    case 8:
                        textPeriod8.setText(p.getSubject().getSubjectInString());
                        break;
                    case 9:
                        textPeriod9.setText(p.getSubject().getSubjectInString());
                        break;
                }
            }
        }
    }

    public void save(){
        if (selectedDay == null)
            saveName();
        else
            saveAll();

        try {
            Serializer.serializeStudent(filePath, student);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        Stage currentStage = (Stage) comboSchoolDays.getScene().getWindow();
        currentStage.close();

    }

    private void saveName(){
        timetable.setName(textTimetableName.getText());
    }

    private void saveAll(){
        saveName();
        HashMap<Integer, String> strings = new HashMap<>();
        LinkedList<Period> periods = new LinkedList<>();

        strings.put(1, textPeriod1.getText());
        strings.put(2, textPeriod2.getText());
        strings.put(3, textPeriod3.getText());
        strings.put(4, textPeriod4.getText());
        strings.put(5, textPeriod5.getText());
        strings.put(6, textPeriod6.getText());
        strings.put(7, textPeriod7.getText());
        strings.put(8, textPeriod8.getText());
        strings.put(9, textPeriod9.getText());

        LinkedList<String> s = new LinkedList<>(strings.values());
        if (!Utilities.isEmptyStringList(s))
            for (Map.Entry<Integer, String> string: strings.entrySet())
                if (string.getValue().isEmpty()) {
                    Period period = new Period(new Subject(Subjects.NONE), string.getKey());
                    periods.add(period);
                } else {
                    Period period = new Period(new Subject(Utilities.toSubjectsFromString(string.getValue())), string.getKey());
                    periods.add(period);
                }

        timetable.setDayPeriods(selectedDay, periods);
        LinkedList<Timetable> timetables = new LinkedList<>();
        timetables.add(timetable);
        student.setTimetables(timetables);
    }
}

















