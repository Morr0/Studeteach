package archavexm.studeteach.app.student.window;

import archavexm.studeteach.app.student.ITimetable;
import archavexm.studeteach.app.student.StudentWindow;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.student.subject.Subject;
import archavexm.studeteach.core.student.subject.Subjects;
import archavexm.studeteach.core.student.timetable.Day;
import archavexm.studeteach.core.student.timetable.Period;
import archavexm.studeteach.core.student.timetable.Timetable;
import archavexm.studeteach.core.util.ObjectDeserializer;
import archavexm.studeteach.core.util.ObjectSerializer;
import archavexm.studeteach.core.util.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class TimetableEditorController implements StudentWindow, ITimetable {
    @FXML private TextField textTimetableName;
    @FXML private ComboBox<String> comboSchoolDays;

    // Text fields for periods
    @FXML private TextField textPeriod1;
    @FXML private TextField textPeriod2;
    @FXML private TextField textPeriod3;
    @FXML private TextField textPeriod4;
    @FXML private TextField textPeriod5;
    @FXML private TextField textPeriod6;
    @FXML private TextField textPeriod7;
    @FXML private TextField textPeriod8;
    @FXML private TextField textPeriod9;

    private String filePath;
    private Day selectedDay;

    private Student student;
    private Timetable timetable;
    private LinkedList<Timetable> timetables = new LinkedList<>();

    @Override
    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public void setTimetable(Timetable timetable){
        this.timetable = timetable;
    }

    @Override
    public void init(){
        try {
            student = ObjectDeserializer.deserializeStudent(filePath);
            textTimetableName.setText(timetable.getName());

            for (Timetable t: student.getTimetables())
                if (t.getId() != timetable.getId())
                    timetables.add(t);


            for (Day day: student.getSchoolDays())
                comboSchoolDays.getItems().add(Utilities.capitalizeFirstLetter(day.toString()));


            } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void updateContext(ActionEvent event){
        ComboBox<String> comboCurrent = (ComboBox<String>) event.getSource();
        selectedDay = Utilities.toDayFromString(comboCurrent.getValue());

        LinkedList<Period> periods = timetable.getDayPeriods(selectedDay);
        ArrayList<String> strings = new ArrayList<>(9);
        for (Period period: periods)
            if (period.getSubject().getSubject().equals(Subjects.NONE))
                strings.add("");
             else
                strings.add(period.getSubject().toString());

        if (!periods.isEmpty()){
            for (Period p: periods){
                switch (p.getNumber()){
                    case 1:
                        textPeriod1.setText(strings.get(0));
                        break;
                    case 2:
                        textPeriod2.setText(strings.get(1));
                        break;
                    case 3:
                        textPeriod3.setText(strings.get(2));
                        break;
                    case 4:
                        textPeriod4.setText(strings.get(3));
                        break;
                    case 5:
                        textPeriod5.setText(strings.get(4));
                        break;
                    case 6:
                        textPeriod6.setText(strings.get(5));
                        break;
                    case 7:
                        textPeriod7.setText(strings.get(6));
                        break;
                    case 8:
                        textPeriod8.setText(strings.get(7));
                        break;
                    case 9:
                        textPeriod9.setText(strings.get(8));
                        break;
                }
            }
        } else {
            textPeriod1.setText("");
            textPeriod2.setText("");
            textPeriod3.setText("");
            textPeriod4.setText("");
            textPeriod5.setText("");
            textPeriod6.setText("");
            textPeriod7.setText("");
            textPeriod8.setText("");
            textPeriod9.setText("");
        }
    }

    public void save(){
        try {
            saveName();
            saveAll();

            ObjectSerializer.serializeStudent(filePath, student);
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
        for (Map.Entry<Integer, String> string: strings.entrySet())
            periods.add(new Period(new Subject(Utilities.toSubjectsFromString(string.getValue())), string.getKey()));

        timetable.setDayPeriods(selectedDay, periods);
        timetables.add(timetable);
        student.setTimetables(timetables);
    }
}