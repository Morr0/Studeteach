package archavexm.studeteach.app.student.window;

import archavexm.studeteach.core.Studeteach;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.student.task.Task;
import archavexm.studeteach.core.student.task.TaskType;
import archavexm.studeteach.core.student.util.StudentWindow;
import archavexm.studeteach.core.util.ObjectDeserializer;
import archavexm.studeteach.core.util.ObjectSerializer;
import archavexm.studeteach.core.util.Utilities;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class TaskManagerController implements StudentWindow {
    @FXML private Label labelNumberOfTasks;
    @FXML private ComboBox<String> comboTasks;
    @FXML private ListView<String> listTasks;

    private Student student;
    private String filePath;
    private LinkedList<Task> tasks;
    private TaskType selectedTaskType;
    private String selectedItem;

    @Override
    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    @Override
    public void init(){
        try {
            student = ObjectDeserializer.deserializeStudent(filePath);
            tasks = student.getTasks();
            selectedTaskType = TaskType.HOMEWORK;
            updateNumberOfTasks();
            getTasks();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void getTasks(){
        listTasks.getItems().clear();

        for (Task task: tasks){
            if (task.getType().equals(selectedTaskType)){
                LocalDate localDate = (task.getDueDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                String t = (task.getSubject().toString()) + " - due on the " + (localDate.format(DateTimeFormatter.ISO_DATE)) + " " + (Utilities.capitalizeFirstLetter(task.getDueDay().toString())) +
                        " - on period " + (task.getDuePeriod().getNumber()) + " - " + (Utilities.capitalizeFirstLetter(task.getDuePeriod().getSubject().getSubject().toString())) + " class." +
                        " - id:" + (task.getTaskId());
                listTasks.getItems().add(t);
            }
        }
    }

    private void save(){
        try {
            ObjectSerializer.serializeStudent(filePath, student);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void refresh(){
        try {
            student = ObjectDeserializer.deserializeStudent(filePath);
            tasks = student.getTasks();
            updateNumberOfTasks();
            getTasks();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void updateNumberOfTasks(){
        String number = Integer.toString(student.getTasks().size());
        labelNumberOfTasks.setText(number);
    }

    public void add(){
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = TaskAdderController.class.getResource("TaskAdder.fxml");
            Parent taskAdder = loader.load(url.openStream());

            TaskAdderController taskAdderController = loader.getController();
            taskAdderController.setFilePath(filePath);
            taskAdderController.setTaskType(selectedTaskType);
            taskAdderController.init();

            Stage currentStage = (Stage) listTasks.getScene().getWindow();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(currentStage);
            stage.setTitle(Utilities.capitalizeFirstLetter(selectedTaskType.toString()) + " Adder - " + Studeteach.APP_NAME);
            stage.setScene(new Scene(taskAdder));
            stage.showAndWait();

            refresh();

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void remove(){
        if (listTasks.getSelectionModel().getSelectedItem() != null){
            selectedItem = listTasks.getSelectionModel().getSelectedItem();
            int taskNumber = Utilities.getIdFromTask(selectedItem);
            LinkedList<Task> newListOfTasks = new LinkedList<>();

            for (Task task: tasks)
                if (task.getTaskId() != taskNumber)
                    newListOfTasks.add(task);

            student.setTasks(newListOfTasks);
            save();
            refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select the task in order to remove it from your list of tasks.");
            alert.showAndWait();

            return;
        }

    }

    public void changeSelection(){
        String taskType = comboTasks.getSelectionModel().getSelectedItem();
        selectedTaskType = Utilities.toTaskTypeFromString(taskType);

        refresh();
    }

}
