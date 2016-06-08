package archavexm.studeteach.app.student;

import archavexm.studeteach.app.StartupController;
import archavexm.studeteach.app.common.AboutController;
import archavexm.studeteach.app.common.todolist.TODOController;
import archavexm.studeteach.app.student.window.ProfileEditorController;
import archavexm.studeteach.app.student.window.TaskManagerController;
import archavexm.studeteach.app.student.window.TimetableEditorController;
import archavexm.studeteach.core.Studeteach;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.student.timetable.Day;
import archavexm.studeteach.core.student.timetable.Period;
import archavexm.studeteach.core.student.timetable.Timetable;
import archavexm.studeteach.core.util.ObjectDeserializer;
import archavexm.studeteach.core.util.ObjectSerializer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;

public class StudentController{
    // menu bar and other labels
    @FXML private MenuBar menuBar;
    @FXML private Label labelName;
    @FXML private Label labelAge;
    @FXML private Label labelYear;
    @FXML private Label labelNumberOfTasks;

    // Periods on days lists
    @FXML private ListView<String> listMonday;
    @FXML private ListView<String> listTuesday;
    @FXML private ListView<String> listWednesday;
    @FXML private ListView<String> listThursday;
    @FXML private ListView<String> listFriday;
    @FXML private ListView<String> listSaturday;
    @FXML private ListView<String> listSunday;

    // The labels for week days
    @FXML private Label labelMonday;
    @FXML private Label labelTuesday;
    @FXML private Label labelWednesday;
    @FXML private Label labelThursday;
    @FXML private Label labelFriday;
    @FXML private Label labelSaturday;
    @FXML private Label labelSunday;

    // Edits the timetable
    @FXML private Button buttonEdit;
    // Deletes the timetable
    @FXML private Button buttonDeleteTimetable;

    private Student student;
    private Timetable timetable;
    private String filePath;

    private String preferedName;
    private String stageTitle;
    private Stage currentStage;

    private HashSet<Day> schoolDays = new HashSet<>();
    private LinkedList<Timetable> timetables = new LinkedList<>();

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setCurrentStage(Stage stage){
        currentStage = stage;
        currentStage.setOnCloseRequest(event -> {
            Platform.exit();
        });
    }

    public void initStudent(){
        try {
            student = ObjectDeserializer.deserializeStudent(filePath);
            schoolDays = student.getSchoolDays();
            setPreferedName();
            setLabels();
            setTitle();

            try {
                timetable = student.getTimetables().getFirst();
            } catch (Exception ex){
                timetable = new Timetable();
                timetables.add(timetable);
                student.setTimetables(timetables);

                ObjectSerializer.serializeStudent(filePath, student);
                student = ObjectDeserializer.deserializeStudent(filePath);
            }

            refreshTimetable();
            getSchoolDays();

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void toProfileEditor(){
        URL url = ProfileEditorController.class.getResource("ProfileEditor.fxml");
        toWindow(url, "ProfileEditor", preferedName + " - Profile Editor - " + Studeteach.APP_NAME);
        refresh();
    }

    public void toTaskManager(){
        if (timetable.isTimetableEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You have not set your timetable. In order to access the task manager you have to have a timetable.");
            alert.showAndWait();

            return;
        } else {
            URL url = TaskManagerController.class.getResource("TaskManager.fxml");
            toWindow(url, "TaskManager", "Task Manager - " + Studeteach.APP_NAME);
        }
    }

    public void toTODOList(){
        URL url = TODOController.class.getResource("TODO.fxml");
        toWindow(url, "TODO", "TODO List - " + Studeteach.APP_NAME);
    }

    public void toAboutMenu(){
        URL url = AboutController.class.getResource("About.fxml");
        toWindow(url, "About", "About - " + Studeteach.APP_NAME);
    }

    public void toTimetableEditor(){
        URL url = TimetableEditorController.class.getResource("TimetableEditor.fxml");
        toWindow(url, "TimetableEditor", "Timetable Editor - " + Studeteach.APP_NAME);
    }

    private void toWindow(URL url, String name, String title){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent window = loader.load(url.openStream());

            if (!name.equals("About")){
                StudentWindow controller = loader.getController();
                controller.setFilePath(filePath);
                controller.init();
            }

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(window));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(currentStage);
            stage.showAndWait();

        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void refresh(){
        try {
            student = ObjectDeserializer.deserializeStudent(filePath);
            setPreferedName();
            setLabels();
            setTitle();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void setLabels(){
        if (preferedName == null)
            labelName.setText(student.getFirstName());
        else
            labelName.setText(preferedName);

        labelAge.setText(Integer.toString(student.getAge()));

        if (student.getSchoolYear() == 0)
            labelYear.setText("Not Specified");
        else
            labelYear.setText(Integer.toString(student.getSchoolYear()));

        labelNumberOfTasks.setText(Integer.toString(student.getTasks().size()));
    }

    private void refreshTimetable(){
        if (timetable.isTimetableEmpty()){
            timetable = new Timetable();

            Label label = new Label("You have not set your timetable. You can set your timetable down below.");
            Button button = new Button("Set");
            VBox vbox = new VBox();
            vbox.getChildren().addAll(label, button);

            button.setOnAction(e -> toTimetableEditor());

            listMonday.setPlaceholder(vbox);
            listTuesday.setVisible(false);
            listWednesday.setVisible(false);
            listThursday.setVisible(false);
            listFriday.setVisible(false);
            listSaturday.setVisible(false);
            listSunday.setVisible(false);

            labelMonday.setVisible(false);
            labelTuesday.setVisible(false);
            labelWednesday.setVisible(false);
            labelThursday.setVisible(false);
            labelFriday.setVisible(false);
            labelSaturday.setVisible(false);
            labelSunday.setVisible(false);


            buttonEdit.setVisible(false);
            buttonDeleteTimetable.setVisible(false);
        } else
            updateTimetable();
    }

    private void setPreferedName(){
        if (student.getPreferredName() == null){
            stageTitle = (student.getFirstName()) + " - " + Studeteach.APP_NAME;
            preferedName = student.getFirstName();
        } else if (student.getPreferredName().isEmpty()){
            stageTitle = (student.getFirstName()) + " - " + Studeteach.APP_NAME;
            preferedName = student.getFirstName();
        } else {
            stageTitle = (student.getPreferredName()) + " - " + Studeteach.APP_NAME;
            preferedName = student.getPreferredName();
        }
    }

    private void setTitle(){
        currentStage.setTitle(stageTitle);
    }

    private void updateTimetable(){
        if (schoolDays.contains(Day.MONDAY)){
            if (timetable.getMondayPeriods().size() == 0)
                listMonday.setPlaceholder(new Label("You have not set the periods on monday."));
            else
                for (Period period: timetable.getMondayPeriods())
                    listMonday.getItems().add(period.getNumber() + " - " + period.getSubject().toString());
        } else{
            listMonday.setVisible(false);
            labelMonday.setVisible(false);
        }

        if (schoolDays.contains(Day.TUESDAY)){
            if (timetable.getTuesdayPeriods().size() == 0)
                listTuesday.setPlaceholder(new Label("You have not set the periods on tuesday."));
            else
                for (Period period: timetable.getTuesdayPeriods())
                    listTuesday.getItems().add(period.getNumber() + " - " + period.getSubject().toString());
        } else{
            listTuesday.setVisible(false);
            labelTuesday.setVisible(false);
        }
        if (schoolDays.contains(Day.WEDNESDAY)){
            if (timetable.getWednesdayPeriods().size() == 0)
                listWednesday.setPlaceholder(new Label("You have not set the periods on wednesday."));
            else
                for (Period period: timetable.getWednesdayPeriods())
                    listWednesday.getItems().add(period.getNumber() + " - " + period.getSubject().toString());
        } else {
            listWednesday.setVisible(false);
            labelWednesday.setVisible(false);
        }
        if (schoolDays.contains(Day.THURSDAY)){
            if (timetable.getThursdayPeriods().size() == 0)
                listThursday.setPlaceholder(new Label("You have not set the periods on thursday."));
            else
                for (Period period: timetable.getThursdayPeriods())
                    listThursday.getItems().add(period.getNumber() + " - " + period.getSubject().toString());
        } else {
            listThursday.setVisible(false);
            labelThursday.setVisible(false);
        }
        if (schoolDays.contains(Day.FRIDAY)){
            if (timetable.getFridayPeriods().size() == 0)
                listFriday.setPlaceholder(new Label("You have not set the periods on friday."));
            else
                for (Period period: timetable.getFridayPeriods())
                    listFriday.getItems().add(period.getNumber() + " - " + period.getSubject().toString());
        } else {
            listFriday.setVisible(false);
            labelFriday.setVisible(false);
        }
        if (schoolDays.contains(Day.SATURDAY)){
            if (timetable.getSaturdayPeriods().size() == 0)
                listSaturday.setPlaceholder(new Label("You have not set the periods on saturday."));
            else
                for (Period period: timetable.getSaturdayPeriods())
                    listSaturday.getItems().add(period.getNumber() + " - " + period.getSubject().toString());
        } else {
            listSaturday.setVisible(false);
            labelSaturday.setVisible(false);
        }
        if (schoolDays.contains(Day.SUNDAY)){
            if (timetable.getSundayPeriods().size() == 0)
                listSunday.setPlaceholder(new Label("You have not set the periods on sunday."));
            else
                for (Period period: timetable.getSundayPeriods())
                    listSunday.getItems().add(period.getNumber() + " - " + period.getSubject().toString());
        } else {
            listSunday.setVisible(false);
            labelSunday.setVisible(false);
        }
    }

    private void getSchoolDays(){
        for (Day day: student.getSchoolDays()){
            schoolDays.add(day);
        }
    }

    private void save(){
        try {
            ObjectSerializer.serializeStudent(filePath, student);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void deleteTimetable(){
        LinkedList<Timetable> tts = new LinkedList<>();
        tts.add(new Timetable());
        student.setTimetables(tts);

        try {
            ObjectSerializer.serializeStudent(filePath, student);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        refreshTimetable();
        refresh();
    }

    // File menu
    public void fileSave(){
        save();
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

        save();
    }

    public void fileCloseProfile(){
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = StartupController.class.getResource("Startup.fxml");
            Parent mainMenu = loader.load(url);

            Stage stage = new Stage();
            stage.setTitle(Studeteach.APP_NAME);
            stage.setScene(new Scene(mainMenu));
            stage.show();
        } catch (IOException ex){
            ex.printStackTrace();
        }

        currentStage.close();
    }

    public void fileExit(){
        Platform.exit();
    }
}