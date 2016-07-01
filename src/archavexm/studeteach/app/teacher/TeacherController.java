package archavexm.studeteach.app.teacher;

import archavexm.studeteach.app.common.PersonController;
import archavexm.studeteach.app.common.PersonWindow;
import archavexm.studeteach.app.common.Studeteach;
import archavexm.studeteach.app.common.todolist.TODOController;
import archavexm.studeteach.app.teacher.windows.EditorController;
import archavexm.studeteach.core.common.Day;
import archavexm.studeteach.core.teacher.Teacher;
import archavexm.studeteach.core.util.ObjectDeserializer;
import archavexm.studeteach.core.util.ObjectSerializer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;

public class TeacherController implements PersonController {
    @FXML private Label labelPreferredName;

    private String filePath;
    private Stage currentStage;
    private Teacher teacher;
    private HashSet<Day> schoolDays;

    private String preferredName;
    private String stageTitle;

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public void setCurrentStage(Stage currentStage){
        this.currentStage = currentStage;
    }

    public void init(){
        try {
            teacher = ObjectDeserializer.deserializeTeacher(filePath);
            getSchoolDays();
            setPreferredName();
            setLabels();
            if (currentStage.getTitle() == null)
                currentStage.setTitle(stageTitle);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void setPreferredName(){
        preferredName = teacher.getTitleName();
        stageTitle = teacher.getTitleName() + " - " + Studeteach.APP_NAME;
    }

    private void setLabels(){
        labelPreferredName.setText(preferredName);
    }

    public void toProfileEditor(){
        toWindow(EditorController.class.getResource("Editor.fxml"), true,"Profile Editor - " + Studeteach.APP_NAME);
    }

    public void toTodoList(){
        toWindow(TODOController.class.getResource("TODO.fxml"), true,"TODO List Manager - " + Studeteach.APP_NAME);
    }

    private void toWindow(URL url, boolean inheritsPersonWindow, String title){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent window = loader.load(url.openStream());
            if (inheritsPersonWindow){
                PersonWindow personWindow = loader.getController();
                personWindow.setFilePath(filePath);
                personWindow.init();
            }

            Stage stage = new Stage();
            stage.initOwner(currentStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle(title);
            stage.getIcons().add(new Image(Studeteach.APP_ICON));
            stage.setScene(new Scene(window));
            stage.showAndWait();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void unpackPerson(){
        try {
            teacher = ObjectDeserializer.deserializeTeacher(filePath);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void getSchoolDays(){
        schoolDays = teacher.getSchoolDays();
    }

    private void save(){
        pack(filePath);
    }

    private void pack(String filePath){
        try {
            ObjectSerializer.serializeTeacher(filePath, teacher);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
