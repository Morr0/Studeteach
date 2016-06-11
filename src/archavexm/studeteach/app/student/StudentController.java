package archavexm.studeteach.app.student;

import archavexm.studeteach.app.StartupController;
import archavexm.studeteach.app.common.AboutController;
import archavexm.studeteach.app.common.todolist.TODOController;
import archavexm.studeteach.app.student.window.ProfileEditorController;
import archavexm.studeteach.app.student.window.TaskManagerController;
import archavexm.studeteach.app.student.window.TimetableEditorController;
import archavexm.studeteach.core.Studeteach;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.student.subject.Subjects;
import archavexm.studeteach.core.student.timetable.Day;
import archavexm.studeteach.core.student.timetable.Period;
import archavexm.studeteach.core.student.timetable.Timetable;
import archavexm.studeteach.core.util.ObjectDeserializer;
import archavexm.studeteach.core.util.ObjectSerializer;
import archavexm.studeteach.core.util.Utilities;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class StudentController{
    // menu bar and other labels
    @FXML private MenuBar menuBar;
    @FXML private Label labelName;
    @FXML private Label labelAge;
    @FXML private Label labelYear;
    @FXML private Label labelNumberOfTasks;

    // Timetable things
    @FXML private AnchorPane anchorTimetable;
    @FXML private ComboBox<String> comboTimetables;
    @FXML private ComboBox<String> comboDays;
    @FXML private CheckBox checkPrimaryTimetable;
    @FXML private ListView<String> listTimetable;

    private Student student;
    private Timetable primaryTimetable;
    private String filePath;

    private String preferredName;
    private String stageTitle;
    private Stage currentStage;

    private HashSet<Day> schoolDays = new HashSet<>();
    private LinkedList<Timetable> timetables = new LinkedList<>();
    private ObservableList<Node> anchorTimetableChildren;
    private Timetable selectedTimetable;
    private Day selectedDay;

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
            unpackStudent();
            anchorTimetableChildren = anchorTimetable.getChildren();
            schoolDays = student.getSchoolDays();
            setPreferedName();
            setLabels();
            setTitle();
            getSchoolDays();
            getTimetables();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void toProfileEditor(){
        URL url = ProfileEditorController.class.getResource("ProfileEditor.fxml");
        toWindow(url, "ProfileEditor", preferredName + " - Profile Editor - " + Studeteach.APP_NAME);
        refresh();
    }

    public void toTaskManager(){
        if (primaryTimetable.isTimetableEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You have not set your primaryTimetable. In order to access the task manager you have to have a primaryTimetable.");
            alert.showAndWait();

            return;
        } else {
            URL url = TaskManagerController.class.getResource("TaskManager.fxml");
            toWindow(url, "TaskManager", "Task Manager - " + Studeteach.APP_NAME);
            refresh();
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
            unpackStudent();
            setPreferedName();
            setLabels();
            setTitle();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void unpackStudent(){
        try {
            student = ObjectDeserializer.deserializeStudent(filePath);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void setPreferedName(){
        if (student.getPreferredName() == null){
            stageTitle = (student.getFirstName()) + " - " + Studeteach.APP_NAME;
            preferredName = student.getFirstName();
        } else if (student.getPreferredName().isEmpty()){
            stageTitle = (student.getFirstName()) + " - " + Studeteach.APP_NAME;
            preferredName = student.getFirstName();
        } else {
            stageTitle = (student.getPreferredName()) + " - " + Studeteach.APP_NAME;
            preferredName = student.getPreferredName();
        }
    }

    private void setLabels(){
        if (preferredName == null)
            labelName.setText(student.getFirstName());
        else
            labelName.setText(preferredName);

        labelAge.setText(Integer.toString(student.getAge()));

        if (student.getSchoolYear() == 0)
            labelYear.setText("?");
        else
            labelYear.setText(Integer.toString(student.getSchoolYear()));

        labelNumberOfTasks.setText(Integer.toString(student.getTasks().size()));
    }

    private void setTitle(){
        currentStage.setTitle(stageTitle);
    }

    private void getSchoolDays(){
        for (Day day: schoolDays){
            comboDays.getItems().add(Utilities.capitalizeFirstLetter(day.toString()));
        }
    }

    private void save(){
        try {
            ObjectSerializer.serializeStudent(filePath, student);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void save(String path){
        try {
            ObjectSerializer.serializeStudent(path, student);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void changedTimetable(){
        if (comboTimetables.getItems().size() > 1){
            selectedTimetable = student.getTimetable(comboTimetables.getValue());

            if (student.isPrimaryTimetable(selectedTimetable)){
                primaryTimetable = selectedTimetable;
                checkPrimaryTimetable.setSelected(true);
            } else
                checkPrimaryTimetable.setSelected(false);
        }
    }

    public void changedDay(){
        selectedDay = Utilities.toDayFromString(comboDays.getSelectionModel().getSelectedItem());
        refreshTimetable();
    }

    public void setPrimaryTimetable(){
        if (checkPrimaryTimetable.isSelected()){
            checkPrimaryTimetable.setSelected(false);
            student.setPrimaryTimetableId(0);
        } else {
            checkPrimaryTimetable.setSelected(true);
            student.setPrimaryTimetableId(selectedTimetable.getId());
        }

        save();
    }

    public void getTimetables() {
        timetables.clear();
        comboTimetables.getItems().clear();

        if (student.getTimetables().size() > 0){
            if (!anchorTimetable.getChildren().equals(anchorTimetableChildren)){
                anchorTimetable.getChildren().clear();
                for (Node node: anchorTimetableChildren)
                    anchorTimetable.getChildren().add(node);
            }

            for (Timetable t : student.getTimetables()){
                timetables.add(t);
                comboTimetables.getItems().add(t.getName());
            }
            primaryTimetable = student.getPrimaryTimetable();
            if (primaryTimetable != null)
                selectedTimetable = primaryTimetable;
             else
                selectedTimetable = student.getTimetables().getFirst();

            comboTimetables.setPromptText(selectedTimetable.getName());
            checkPrimaryTimetable.setSelected(true);
        }
        else {
            anchorTimetable.getChildren().clear();

            Label info = new Label("You do not have any timetable do you want to create one.");
            Button button = new Button("Create");
            button.setOnAction(event -> {
                primaryTimetable = new Timetable();
                primaryTimetable.setName(preferredName + "'s timetable");
                primaryTimetable.setId(new Random().nextInt(1000));
                timetables.add(primaryTimetable);
                student.setTimetables(timetables);
                student.setPrimaryTimetableId(primaryTimetable.getId());
                selectedTimetable = primaryTimetable;
                save();
            });
            VBox vbox = new VBox();
            vbox.getChildren().addAll(info, button);
            anchorTimetable.getChildren().add(vbox);
        }
    }

    private void refreshTimetable(){
        listTimetable.getItems().clear();
        LinkedList<Period> periods = selectedTimetable.getDayPeriods(selectedDay);
        ObservableList<String> strings = FXCollections.observableArrayList();

        if (periods.isEmpty()){
            listTimetable.setPlaceholder(new Label("You don't have any periods on " + Utilities.capitalizeFirstLetter(selectedDay.toString())));
        } else {
            for (Period period: periods){
                String line = null;
                if (period.getSubject().getSubject().equals(Subjects.NONE))
                    line = "Period " + period.getNumber() + ": no class" ;
                else
                    line = "Period " + period.getNumber() + ": " + period.getSubject().toString() + " class";
                strings.add(line);
            }
        }
        listTimetable.setItems(strings);
    }

    public void createTimetable(){

    }

    public void deleteTimetable(){
        LinkedList<Timetable> newTimetables = new LinkedList<>();
        for (Timetable timetable: timetables)
            if (timetable.getId() != selectedTimetable.getId())
                newTimetables.add(timetable);

        student.setTimetables(newTimetables);
        save();
        setLabels();
        }


    private int generateRandomTimetableId(){
        int id = (new Random()).nextInt(1000);
        for (Timetable t: timetables)
            if (t.getId() == id)
                generateRandomTimetableId();

        return id;
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

        save(anotherFilePath);
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
