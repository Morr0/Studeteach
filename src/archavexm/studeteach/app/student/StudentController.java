package archavexm.studeteach.app.student;

import archavexm.studeteach.app.student.window.ProfileEditorController;
import archavexm.studeteach.app.student.window.TaskManagerController;
import archavexm.studeteach.app.student.window.TimetableEditorController;
import archavexm.studeteach.core.Studeteach;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.student.timetable.Day;
import archavexm.studeteach.core.student.timetable.Period;
import archavexm.studeteach.core.student.timetable.Timetable;
import archavexm.studeteach.core.util.Deserializer;

import archavexm.studeteach.core.util.Serializer;
import archavexm.studeteach.core.util.Utilities;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

public class StudentController{
    @FXML
    private MenuBar menuBar;
    @FXML
    private Label labelName;
    @FXML
    private Label labelAge;
    @FXML
    private Label labelYear;
    @FXML
    private Label labelNumberOfTasks;

    // Periods on days lists
    @FXML
    private ListView<String> listMonday;
    @FXML
    private ListView<String> listTuesday;
    @FXML
    private ListView<String> listWednesday;
    @FXML
    private ListView<String> listThursday;
    @FXML
    private ListView<String> listFriday;
    @FXML
    private ListView<String> listSaturday;
    @FXML
    private ListView<String> listSunday;

    @FXML
    private Button buttonEdit;
    @FXML
    private Button buttonDeleteTimetable;

    private Student student;
    private Timetable timetable;
    private String preferedName;

    private String filePath;
    private String stageTitle;

    private HashSet<Day> schoolDays = new HashSet<>();
    private LinkedList<Timetable> timetables = new LinkedList<>();

    private Stage currentStage;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void initStudent(){
        try {
            student = Deserializer.deserializeStudent(filePath);
            schoolDays = student.getSchoolDays();

            try {
                timetable = student.getTimetables().getFirst();
            } catch (Exception ex){
                timetable = new Timetable();
                timetables.add(timetable);
                student.setTimetables(timetables);
                Serializer.serializeStudent(filePath, student);
            }

            if (timetable.isTimetableEmpty()){
                Label label = new Label("You have not set your timetable. You can set your timetable down below.");
                Button button = new Button("Set");
                VBox vbox = new VBox();
                vbox.getChildren().addAll(label, button);

                button.setOnAction(e -> {
                    try {
                        toTimetableEditor();
                    } catch (IOException ex){
                        ex.printStackTrace();
                    }
                });

                listMonday.setPlaceholder(vbox);
                listTuesday.setVisible(false);
                listWednesday.setVisible(false);
                listThursday.setVisible(false);
                listFriday.setVisible(false);
                listSaturday.setVisible(false);
                listSunday.setVisible(false);
                buttonEdit.setVisible(false);
                buttonDeleteTimetable.setVisible(false);
            } else
                updateTimetable();

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void showProfileEditor(){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent profileEditor = loader.load(getClass().getResource("window/ProfileEditor.fxml").openStream());

            ProfileEditorController profileEditorController = loader.getController();
            profileEditorController.setFilePath(filePath);
            profileEditorController.setStudent(student);
            profileEditorController.init();

            Stage stage = new Stage();
            stage.setTitle(preferedName + " - " + "Profile Editor" + " - " + Studeteach.APP_NAME);
            stage.setScene(new Scene(profileEditor));
            stage.showAndWait();

            refresh();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void toTaskManager(){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent taskManager = loader.load(getClass().getResource("window/TaskManager.fxml").openStream());

            TaskManagerController taskManagerController = loader.getController();
            taskManagerController.setFilePath(filePath);
            taskManagerController.setOldStudent(student);
            taskManagerController.init();

            Stage stage = new Stage();
            stage.setTitle(preferedName + " - " + "Task Manager" + " - " + Studeteach.APP_NAME);
            stage.setScene(new Scene(taskManager));
            stage.showAndWait();

            refresh();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void refresh(){
        try {
            student = Deserializer.deserializeStudent(filePath);
            setPreferedName();
            getSchoolDays();
            setTitle();
            updateNumberOfTasks();
            adjustLists();

            if (timetable.isTimetableEmpty()){
                timetable = new Timetable();

                Label label = new Label("You have not set your timetable. You can set your timetable down below.");
                Button button = new Button("Set");
                VBox vbox = new VBox();
                vbox.getChildren().addAll(label, button);

                button.setOnAction(e -> {
                    try {
                        toTimetableEditor();
                    } catch (IOException ex){
                        ex.printStackTrace();
                    }
                });

                listMonday.setPlaceholder(vbox);
                listTuesday.setVisible(false);
                listWednesday.setVisible(false);
                listThursday.setVisible(false);
                listFriday.setVisible(false);
                listSaturday.setVisible(false);
                listSunday.setVisible(false);
                buttonEdit.setVisible(false);
                buttonDeleteTimetable.setVisible(false);
            } else
                updateTimetable();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void setPreferedName(){
        if (student.getPreferedName() == null){
            stageTitle = (student.getFirstName()) + " - " + Studeteach.APP_NAME;
            preferedName = student.getFirstName();
        } else if (student.getPreferedName().isEmpty()){
            stageTitle = (student.getFirstName()) + " - " + Studeteach.APP_NAME;
            preferedName = student.getFirstName();
        } else {
            stageTitle = (student.getPreferedName()) + " - " + Studeteach.APP_NAME;
            preferedName = student.getPreferedName();
        }
    }

    private void setTitle(){
        currentStage = (Stage)labelName.getScene().getWindow();
        currentStage.setTitle(stageTitle);
    }

    private void updateNumberOfTasks(){
        String num = null;

        if (student.getTasks() == null){
            num = "0";
        }
        else {
            num = Integer.toString(student.getTasks().size());
        }

        labelNumberOfTasks.setText(num);
    }

    private void adjustLists(){
        for (Day day: schoolDays){
            switch (day){
                case MONDAY:
                    listMonday.getItems().clear();
                    break;
                case TUESDAY:
                    if (listTuesday.isVisible())
                        listTuesday.setVisible(true);

                    listTuesday.getItems().clear();
                    break;
                case WEDNESDAY:
                    if (listWednesday.isVisible())
                        listWednesday.setVisible(true);

                    listWednesday.getItems().clear();
                    break;
                case THURSDAY:
                    if (listThursday.isVisible())
                        listThursday.setVisible(true);

                    listThursday.getItems().clear();
                    break;
                case FRIDAY:
                    if (listFriday.isVisible())
                        listFriday.setVisible(true);

                    listFriday.getItems().clear();
                    break;
                case SATURDAY:
                    if (listSaturday.isVisible())
                        listSaturday.setVisible(true);

                    listSaturday.getItems().clear();
                    break;
                case SUNDAY:
                    if (listSunday.isVisible())
                        listSunday.setVisible(true);

                    listSunday.getItems().clear();
                    break;
            }
        }
    }

    private void updateTimetable(){
        if (schoolDays.contains(Day.MONDAY)){
            if (timetable.getMondayPeriods().size() == 0)
                listMonday.setPlaceholder(new Label("You have not set the periods on monday."));
            else
                for (Period period: timetable.getMondayPeriods())
                    listMonday.getItems().add(period.getNumber() + " - " + period.getSubject().getSubjectInString());
        }
        if (schoolDays.contains(Day.TUESDAY)){
            if (timetable.getTuesdayPeriods().size() == 0)
                listTuesday.setPlaceholder(new Label("You have not set the periods on tuesday."));
            else
                for (Period period: timetable.getTuesdayPeriods())
                    listTuesday.getItems().add(period.getNumber() + " - " + period.getSubject().getSubjectInString());
        }
        if (schoolDays.contains(Day.WEDNESDAY)){
            if (timetable.getWednesdayPeriods().size() == 0)
                listWednesday.setPlaceholder(new Label("You have not set the periods on wednesday."));
            else
                for (Period period: timetable.getWednesdayPeriods())
                    listWednesday.getItems().add(period.getNumber() + " - " + period.getSubject().getSubjectInString());
        }
        if (schoolDays.contains(Day.THURSDAY)){
            if (timetable.getThursdayPeriods().size() == 0)
                listThursday.setPlaceholder(new Label("You have not set the periods on thursday."));
            else
                for (Period period: timetable.getThursdayPeriods())
                    listThursday.getItems().add(period.getNumber() + " - " + period.getSubject().getSubjectInString());
        }
        if (schoolDays.contains(Day.FRIDAY)){
            if (timetable.getFridayPeriods().size() == 0)
                listFriday.setPlaceholder(new Label("You have not set the periods on friday."));
            else
                for (Period period: timetable.getFridayPeriods())
                    listFriday.getItems().add(period.getNumber() + " - " + period.getSubject().getSubjectInString());
        }
        if (schoolDays.contains(Day.SATURDAY)){
            if (timetable.getSaturdayPeriods().size() == 0)
                listSaturday.setPlaceholder(new Label("You have not set the periods on saturday."));
            else
                for (Period period: timetable.getSaturdayPeriods())
                    listSaturday.getItems().add(period.getNumber() + " - " + period.getSubject().getSubjectInString());
        }
        if (schoolDays.contains(Day.SUNDAY)){
            if (timetable.getSundayPeriods().size() == 0)
                listSunday.setPlaceholder(new Label("You have not set the periods on sunday."));
            else
                for (Period period: timetable.getSundayPeriods())
                    listSunday.getItems().add(period.getNumber() + " - " + period.getSubject().getSubjectInString());
        }
    }

    private void getSchoolDays(){
        for (Day day: student.getSchoolDays()){
            schoolDays.add(day);
        }
    }

    public void toTimetableEditor() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        Parent timetableEditor = loader.load(getClass().getResource("window/TimetableEditor.fxml").openStream());

        TimetableEditorController timetableEditorController = loader.getController();
        timetableEditorController.setFilePath(filePath);
        timetableEditorController.init();

        Stage stage = new Stage();
        stage.setTitle(preferedName + " - " + "Timetable Editor" + " - " + Studeteach.APP_NAME);
        stage.setScene(new Scene(timetableEditor));
        stage.showAndWait();
    }

    public void deleteTimetable(){
        LinkedList<Timetable> tts = new LinkedList<>();
        tts.add(new Timetable());
        student.setTimetables(tts);

        try {
            Serializer.serializeStudent(filePath, student);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        refresh();
    }

    // File menu
    public void fileSave(){
        try {
            Serializer.serializeStudent(filePath, student);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void fileSaveAs(){
        String anotherFilePath = null;
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save As .studeteach");

            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Studeteach files", "*.studeteach");
            fileChooser.setSelectedExtensionFilter(extensionFilter);

            Stage currentStage = (Stage)labelAge.getScene().getWindow();
            File file = fileChooser.showSaveDialog(currentStage);

            anotherFilePath = file.getAbsolutePath();

        } catch (Exception ex){
            return;
        }

        try {
            Serializer.serializeStudent(anotherFilePath, student);
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void fileCloseProfile(){

    }

    public void fileExit(){
        Platform.exit();
    }
}

















