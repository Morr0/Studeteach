package archavexm.studeteach.app.common;

import archavexm.studeteach.app.ToMainMenuController;
import archavexm.studeteach.app.common.timetable.TimetableEditorController;
import archavexm.studeteach.app.common.todolist.TODOController;
import archavexm.studeteach.app.student.ProfileEditorController;
import archavexm.studeteach.app.student.TaskManagerController;
import archavexm.studeteach.app.teacher.EditorController;
import archavexm.studeteach.core.common.Day;
import archavexm.studeteach.core.common.Person;
import archavexm.studeteach.core.common.subject.Subjects;
import archavexm.studeteach.core.common.timetable.Period;
import archavexm.studeteach.core.common.timetable.Timetable;
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
import javafx.scene.image.Image;
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
import java.util.Optional;

// The Person main window
// Here you can edit all the things that a student or a teacher has
public class PersonController {
    // Menu bar and other labels
    @FXML private MenuBar menuBar;
    @FXML private MenuItem itemToTaskManager;
    @FXML private Label labelName;
    @FXML private Button buttonTaskManager;

    // Timetable things
    @FXML private AnchorPane anchorTimetable;
    @FXML private ComboBox<String> comboTimetables;
    @FXML private ComboBox<String> comboDays;
    @FXML private Label checkPrimaryTimetable;
    @FXML private ListView<String> listTimetable;

    private Person person;
    private Person.PersonType personType;
    private Timetable primaryTimetable;
    private String filePath;

    private String preferredName;
    private String stageTitle;
    private Stage currentStage;

    // This runnable will be implemented by threads whenever an update to the profile occurs.
    private Runnable refreshingLabelsRunnable;

    private HashSet<Day> schoolDays = new HashSet<>();
    private final LinkedList<Timetable> timetables = new LinkedList<>();
    private final ObservableList<Node> anchorTimetableChildren = FXCollections.observableArrayList();
    private Timetable selectedTimetable;
    private Day selectedDay;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setCurrentStage(Stage stage){
        currentStage = stage;
        currentStage.setOnCloseRequest(event -> Platform.exit());
    }

    public void setPersonType(Person.PersonType personType){
        this.personType = personType;
        if (personType.equals(Person.PersonType.TEACHER)){
            buttonTaskManager.setVisible(false);
            itemToTaskManager.setVisible(false);
        }
    }

    public void init(){
        // Implementation of the reloading runnable interface for the threads
        try {
            refreshingLabelsRunnable = () -> {
                Platform.runLater(() -> {
                    stageTitle = person.getTitleName() + " - " + Studeteach.APP_NAME;
                    currentStage.setTitle(stageTitle);
                    preferredName = person.getTitleName();
                    labelName.setText(preferredName == null ? person.getFirstName() : preferredName);
            });};

            unpack();
            new Thread(refreshingLabelsRunnable).start();

            getSchoolDays();
            for (Node node: anchorTimetable.getChildren())
                anchorTimetableChildren.add(node);

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
        URL url;
        if (personType.equals(Person.PersonType.STUDENT))
            url = ProfileEditorController.class.getResource("ProfileEditor.fxml");
        else
            url = EditorController.class.getResource("Editor.fxml");

        toWindow(url, "ProfileEditor", preferredName + " - Profile Editor - " + Studeteach.APP_NAME);
        refresh();
        new Thread(refreshingLabelsRunnable).start();
        getSchoolDays();
        getTimetables();
    }

    public void toTaskManager(){
        if (primaryTimetable == null || primaryTimetable.isTimetableEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You have not set your primary timetable. In order to access the task manager you have to have a primary timetable.");
            alert.showAndWait();

        } else {
            URL url = TaskManagerController.class.getResource("TaskManager.fxml");
            toWindow(url, "TaskManager", "Task Manager - " + Studeteach.APP_NAME);
            unpack();
            refresh();
            new Thread(refreshingLabelsRunnable).start();
        }
    }

    public void toTODOList(){
        URL url = TODOController.class.getResource("TODO.fxml");
        toWindow(url, "TODO", "TODO List Manager - " + Studeteach.APP_NAME);
    }

    public void toAboutMenu(){
        URL url = AboutController.class.getResource("About.fxml");
        toWindow(url, "About", "About - " + Studeteach.APP_NAME);
    }

    public void toTimetableEditor(){
        if (schoolDays.size() > 0){
            URL url = TimetableEditorController.class.getResource("TimetableEditor.fxml");
            toWindow(url, "TimetableEditor", "Timetable Editor - " + Studeteach.APP_NAME);
            unpack();
            getTimetables();
            //changedTimetable();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("In order to edit your timetable you have to add at least one school day to your profile.");
            alert.showAndWait();

        }
    }

    private void toWindow(URL url, String name, String title){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent window = loader.load(url.openStream());

            if (!name.equals("About")){
                PersonWindow controller = loader.getController();

                if (controller instanceof ITimetable){
                    ITimetable timetableController = (ITimetable) controller;
                    timetableController.setTimetable(selectedTimetable);
                }

                controller.setFilePath(filePath);
                controller.init();
            }

            Stage stage = new Stage();
            stage.getIcons().add(new Image(Studeteach.APP_ICON));
            stage.setTitle(title);
            stage.setScene(new Scene(window));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(currentStage);
            stage.showAndWait();
        } catch (IOException ex){
            ex.printStackTrace();
        } catch (NullPointerException ex) {}
    }

    public void refresh(){
        try {
            unpack();
            schoolDays = person.getSchoolDays();
            if (currentStage.getTitle() == null)
                currentStage.setTitle(stageTitle);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void unpack(){
        try {
            person = ObjectDeserializer.deserialize(filePath);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void getSchoolDays(){
        schoolDays = person.getSchoolDays();
        comboDays.getItems().clear();
        ObservableList<String> strings = FXCollections.observableArrayList();

        for (Day day: schoolDays)
            strings.add(Utilities.capitalizeFirstLetter(day.toString()));
        comboDays.setItems(strings);
    }

    private void save(){
        pack(filePath);
    }

    private void pack(String path){
        try {
            ObjectSerializer.serialize(path, person);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void changedTimetable(){
        if (comboTimetables.getItems().size() > 1){
            selectedTimetable = person.getTimetable(comboTimetables.getSelectionModel().getSelectedItem());
            //listTimetable.getItems().clear();
            refreshTimetable();

            if (person.isPrimaryTimetable(selectedTimetable)){
                primaryTimetable = selectedTimetable;
                checkPrimaryTimetable.setText("Yes");
            } else
                checkPrimaryTimetable.setText("No");
        }
    }

    // Fired whenever the day in the comboDay is changed
    public void changedDay(){
        try {
            selectedDay = Utilities.toDayFromString(comboDays.getSelectionModel().getSelectedItem());
            if (person.isPrimaryTimetable(selectedTimetable))
                checkPrimaryTimetable.setText("Yes");
            else
                checkPrimaryTimetable.setText("No");

            refreshTimetable();
            // empty catch block because whenever the user exits Profile Editor this will throw an exception for no reason
        } catch (NullPointerException ex) {
        }
    }

    // Reloads the timetable nodes
    // Takes care of the timetable system
    public void getTimetables() {
        timetables.clear();
        comboTimetables.getItems().clear();

        if (person.getTimetables().size() > 0){
            if (!anchorTimetable.getChildren().equals(anchorTimetableChildren)){
                anchorTimetable.getChildren().clear();
                anchorTimetable.getChildren().setAll(anchorTimetableChildren);
            }

            for (Timetable t : person.getTimetables()){
                timetables.add(t);
                comboTimetables.getItems().add(t.getName());

                if (person.isPrimaryTimetable(t)){
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
            VBox vbox = new VBox();
            vbox.getChildren().addAll(info, button);
            anchorTimetable.getChildren().add(vbox);

            button.setOnAction(event -> {
                primaryTimetable = new Timetable();
                primaryTimetable.setName(preferredName + "'s timetable");
                primaryTimetable.setId(person.generateRandomIdForTimetable());
                timetables.add(primaryTimetable);
                person.setTimetables(timetables);
                person.setPrimaryTimetableId(primaryTimetable.getId());
                selectedTimetable = primaryTimetable;
                save();
                unpack();
                getTimetables();
            });
        }
    }

    // Refreshes the timetable view from the specified day and timetable
    private void refreshTimetable(){
        if (selectedDay != null){
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
                    else if (period.getSubject().getSubject().equals(Subjects.OTHER))
                        line = "Period " + period.getNumber() + ": you have a non standard class (Other class)";
                    else
                        line = "Period " + period.getNumber() + ": you have a " + period.getSubject().toString() + " class";
                    strings.add(line);
                }
            }
            listTimetable.setItems(strings);
        }
    }

    // Creates a new timetable with a unique name and auto-generated id
    public void createTimetable(){
        String anotherTimetableName = selectedTimetable.getName() + (person.getTimetables().size() + 1);
        int id = person.generateRandomIdForTimetable();

        Timetable anotherTimetable = new Timetable();
        anotherTimetable.setName(anotherTimetableName);
        anotherTimetable.setId(id);

        timetables.add(anotherTimetable);
        person.setTimetables(timetables);
        person.organiseTimetables();
        save();
        unpack();
        getTimetables();
    }

    // Deletes the selected timetable
    public void deleteTimetable(){
        LinkedList<Timetable> newTimetables = new LinkedList<>();
        for (Timetable timetable: timetables)
            if (person.isPrimaryTimetable(timetable))
                person.setPrimaryTimetableId(0);
            else if (timetable.getId() != selectedTimetable.getId())
                newTimetables.add(timetable);

        if (newTimetables.size() > 0){
            int id = person.generateRandomIdForTimetable();
            person.setPrimaryTimetableId(id);
            Timetable timetable = newTimetables.getFirst();
            int oldId = timetable.getId();
            timetable.setId(id);

            @SuppressWarnings(value = "unchecked")
            LinkedList<Timetable> tts = (LinkedList<Timetable>) newTimetables.clone();
            newTimetables.clear();
            for (Timetable t: tts)
                if (t.getId() == oldId){
                    t.setId(id);
                    newTimetables.add(t);
                } else
                    newTimetables.add(t);
        }

        person.setTimetables(newTimetables);
        save();
        unpack();
        //setLabels();
        getTimetables();
    }

    // File menus
    public void fileSaveAs(){
        String anotherFilePath;
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save As .studeteach");

            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Studeteach files", "*.studeteach");
            fileChooser.setSelectedExtensionFilter(extensionFilter);

            Stage currentStage = (Stage) labelName.getScene().getWindow();
            File file = fileChooser.showSaveDialog(currentStage);

            anotherFilePath = file.getAbsolutePath();

        } catch (Exception ex){
            return;
        }

        pack(anotherFilePath);
    }

    public void fileDeleteProfile(){
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure that you want to delete your profile.");

            Optional<ButtonType> button = alert.showAndWait();
            if (button.get().equals(ButtonType.OK)){
                Utilities.emptyTheFile(filePath);
                fileCloseProfile();
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void fileCloseProfile(){
        try {
            FXMLLoader loader = new FXMLLoader(ToMainMenuController.class.getResource("ToMainMenu.fxml"));
            Parent mainMenu = loader.load();

            Stage stage = new Stage();
            stage.getIcons().add(new Image(Studeteach.APP_ICON));
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