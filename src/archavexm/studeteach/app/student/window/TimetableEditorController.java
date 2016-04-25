package archavexm.studeteach.app.student.window;

import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.student.subject.Subject;
import archavexm.studeteach.core.student.timetable.Day;
import archavexm.studeteach.core.student.timetable.Period;
import archavexm.studeteach.core.student.timetable.Timetable;
import archavexm.studeteach.core.util.Deserializer;
import archavexm.studeteach.core.util.Serializer;
import archavexm.studeteach.core.util.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class TimetableEditorController {
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
    private String selectedDay;
    private boolean isReadyToSave;

    private Student oStudent;
    private Student student;
    private Timetable timetable;

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public void init(){
        try {
            oStudent = Deserializer.deserializeStudent(filePath);
            timetable = oStudent.getTimetable();

            HashMap<Day, LinkedList<Period>> days = timetable.getDayPeriods();
            for (Day d: oStudent.getSchoolDays())
                days.put(d, null);
            timetable.setDayPeriods(days);

            for (Day day: oStudent.getSchoolDays()){
                comboSchoolDays.getItems().add(Utilities.capitalizeFirstLetter((day.toString()).toLowerCase()));
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void updateContext(ActionEvent event){
        ComboBox<String> d = (ComboBox<String>) event.getSource();
        selectedDay = d.getSelectionModel().getSelectedItem();

        Day day = Utilities.toDayFromString(selectedDay);
        LinkedList<Period> periods = new LinkedList<>();
        HashMap<Day, LinkedList<Period>> dayPeriods = new HashMap<>(7);

        for (Map.Entry<Day, LinkedList<Period>> dp: timetable.getDayPeriods().entrySet()){
            if (dp.getKey() == day){
                if (dp.getValue() == null){
                    break;
                }
                else {
                    for (Period p: dp.getValue()){
                        periods.add(p);
                    }
                }
            }
        }

        for (Period p: periods){
            int num = p.getNumber();
            if (num == 1){
                textPeriod1.setText(p.getSubject().getSubjectInString());
            }
            else if (num == 2){
                textPeriod2.setText(p.getSubject().getSubjectInString());
            }
            else if (num == 3){
                textPeriod3.setText(p.getSubject().getSubjectInString());
            }
            else if (num == 4){
                textPeriod4.setText(p.getSubject().getSubjectInString());
            }
            else if (num == 5){
                textPeriod5.setText(p.getSubject().getSubjectInString());
            }
            else if (num == 6){
                textPeriod6.setText(p.getSubject().getSubjectInString());
            }
            else if (num == 7){
                textPeriod7.setText(p.getSubject().getSubjectInString());
            }
            else if (num == 8){
                textPeriod8.setText(p.getSubject().getSubjectInString());
            }
            else if (num == 9){
                textPeriod9.setText(p.getSubject().getSubjectInString());
            }
        }

        save();
    }

    public void save(){
        ArrayList<String> periodsInString = new ArrayList<>(9);
        LinkedList<Period> periods = new LinkedList<>();
        Day day = Utilities.toDayFromString(selectedDay);

        periodsInString.add(textPeriod1.getText());
        periodsInString.add(textPeriod2.getText());
        periodsInString.add(textPeriod3.getText());
        periodsInString.add(textPeriod4.getText());
        periodsInString.add(textPeriod5.getText());
        periodsInString.add(textPeriod6.getText());
        periodsInString.add(textPeriod7.getText());
        periodsInString.add(textPeriod8.getText());
        periodsInString.add(textPeriod9.getText());

        for (String s: periodsInString){
            if (s != null){
                periods.add(new Period(new Subject(Utilities.toSubjectsFromString(s))));
            }
        }

        HashMap<Day, LinkedList<Period>> dayperiods = timetable.getDayPeriods();
        dayperiods.put(day, periods);
        timetable.setDayPeriods(dayperiods);
        student = oStudent;
        try {
            student.setTimetable(timetable);
        }
        catch (Exception ex){
            ex.printStackTrace();
            //System.out.println(ex.getMessage());
        }
        oStudent = null;

        try {
            Serializer.serializeStudent(filePath, student);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}










