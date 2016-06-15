package archavexm.studeteach.app.student;

import archavexm.studeteach.app.ToMainMenuController;
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
import archavexm.studeteach.core.student.util.ITimetable;
import archavexm.studeteach.core.student.util.StudentWindow;
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
import javafx.scene.text.Font;
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

    // Timetable things
    @FXML private AnchorPane anchorTimetable;
    @FXML private ComboBox<String> comboTimetables;
    @FXML private ComboBox<String> comboDays;
    @FXML private Label checkPrimaryTimetable;
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
        currentStage.setOnCloseRequest(event -> Platform.exit());
    }

    public void initStudent(){
        try {
            unpackStudent();
            anchorTimetableChildren = anchorTimetable.getChildren();
            schoolDays = student.getSchoolDays();
            setPreferredName();
            setLabels();
            setTitle();
            getSchoolDays();

            if (schoolDays.size() == 0) {
                Label label = new Label("You have not set your school days. You can press the button below in order to edit your profile and set your school days.");
                label.setWrapText(true);
                Button button = new Button("Edit Profile");
                button.setOnAction(event -> toProfileEditor());
                VBox vBox = new VBox();
                vBox.getChildren().addAll(label, button);
                anchorTimetable.getChildren().clear();
                anchorTimetable.getChildren().add(vBox);
            } else
                getTimetables();

            Label listTimetablePlaceholder = new Label("You have not selected any day for viewing your timetable. You can select above.");
            listTimetablePlaceholder.setFont(new Font(18.0));
            listTimetablePlaceholder.setWrapText(true);
            listTimetable.setPlaceholder(listTimetablePlaceholder);
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

                if (controller instanceof ITimetable){
                    ITimetable timetableController = (ITimetable) controller;
                    timetableController.setTimetable(selectedTimetable);
                }

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
            setPreferredName();
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

    private void setPreferredName(){
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
            selectedTimetable = student.getTimetable(comboTimetables.getSelectionModel().getSelectedItem());

            if (student.isPrimaryTimetable(selectedTimetable)){
                primaryTimetable = selectedTimetable;
                checkPrimaryTimetable.setText("Yes");
            } else
                checkPrimaryTimetable.setText("No");
        }
    }

    public void changedDay(){
        selectedDay = Utilities.toDayFromString(comboDays.getSelectionModel().getSelectedItem());
        if (student.isPrimaryTimetable(selectedTimetable))
            checkPrimaryTimetable.setText("Yes");
        else
            checkPrimaryTimetable.setText("No");


        refreshTimetable();
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

                if (student.isPrimaryTimetable(t)){
                    primaryTimetable = t;
                    selectedTimetable = t;
                    comboTimetables.setPromptText(selectedTimetable.getName());
                    checkPrimaryTimetable.setText("Yes");
                }
            }

            if (primaryTimetable == null)
                if (timetables.size() == 1){
                    selectedTimetable = timetables.getFirst();
                    comboTimetables.setPromptText(selectedTimetable.getName());
                }
        } else {
            anchorTimetable.getChildren().clear();

            Label info = new Label("You do not have any timetable do you want to create one.");
            Button button = new Button("Create");
            button.setOnAction(event -> {
                primaryTimetable = new Timetable();
                primaryTimetable.setName(preferredName + "'s timetable");
                primaryTimetable.setId(student.generateRandomIdForTimetable());
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
                String line;
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
        String anotherTimetableName = selectedTimetable.getName() + (student.getTimetables().size() + 1);
        int id = student.generateRandomIdForTimetable();

        Timetable anotherTimetable = new Timetable();
        anotherTimetable.setName(anotherTimetableName);
        anotherTimetable.setId(id);

        timetables.add(anotherTimetable);
        student.setTimetables(timetables);
        save();
        unpackStudent();
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

    // File menu
    public void fileSave(){
        save();
    }

    public void fileSaveAs(){
        String anotherFilePath;
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
            URL url = ToMainMenuController.class.getResource("ToMainMenu.fxml");
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