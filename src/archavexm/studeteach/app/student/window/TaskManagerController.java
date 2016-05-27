package archavexm.studeteach.app.student.window;

import archavexm.studeteach.core.Studeteach;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.student.task.Task;
import archavexm.studeteach.core.student.task.TaskType;
import archavexm.studeteach.core.util.ObjectDeserializer;
import archavexm.studeteach.core.util.ObjectSerializer;
import archavexm.studeteach.core.util.Utilities;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class TaskManagerController {
    @FXML
    private Label labelNumOfTasks;

    @FXML
    private ListView<String> listAssignments;
    @FXML
    private ListView<String> listExams;
    @FXML
    private ListView<String> listHomeworks;

    private Student student;
    private String filePath;
    private LinkedList<Task> tasks;
    private String selectedItem;

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public void init(){
        try {
            student = ObjectDeserializer.deserializeStudent(filePath);

            labelNumOfTasks.setText(Integer.toString(getNumberOfTasks()));
            tasks = student.getTasks();
            refresh();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void addAssignment(){
        newTaskDialog("Assignment");
    }

    public void addExam(){
        newTaskDialog("Exam");
    }

    public void addHomework(){
        newTaskDialog("Homework");
    }

    public void deleteAssignment(){
        delete(TaskType.ASSIGNMENT);
    }

    public void deleteExam(){
        delete(TaskType.EXAM);
    }

    public void deleteHomework(){
        delete(TaskType.HOMEWORK);
    }

    private int getNumberOfTasks(){
        String num;
        int output = 0;

        if (student.getTasks() == null){
            num = "0";
        }
        else {
            num = Integer.toString(student.getTasks().size());
            output = student.getTasks().size();
        }

        labelNumOfTasks.setText(num);
        return output;
    }

    private void newTaskDialog(String type){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent taskAdder = loader.load(getClass().getResource("TaskAdder.fxml").openStream());

            TaskAdderController taskAdderController = loader.getController();
            taskAdderController.setFilePath(filePath);

            if (type == "Assignment")
                taskAdderController.setTaskType(TaskType.ASSIGNMENT);
            else if (type == "Exam")
                taskAdderController.setTaskType(TaskType.EXAM);
            else if (type == "Homework")
                taskAdderController.setTaskType(TaskType.HOMEWORK);


            taskAdderController.init();

            Stage stage = new Stage();
            stage.setTitle("Add Task - " + Studeteach.APP_NAME);
            stage.setScene(new Scene(taskAdder));
            stage.showAndWait();

            refresh();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void refresh(){
        try {
            student = ObjectDeserializer.deserializeStudent(filePath);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        tasks = student.getTasks();

        listAssignments.getItems().clear();
        listExams.getItems().clear();
        listHomeworks.getItems().clear();

        for (Task task: tasks){
            LocalDate localDate = (task.getDueDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (task.getType() == TaskType.ASSIGNMENT){
                String t = (task.getSubject().toString()) + " - due on the " + (localDate.format(DateTimeFormatter.ISO_DATE)) + " " + (Utilities.capitalizeFirstLetter(task.getDueDay().toString())) +
                        " - on period " + (task.getDuePeriod().getNumber()) + " - " + (Utilities.capitalizeFirstLetter(task.getDuePeriod().getSubject().getSubject().toString())) + " class." +
                        " - id:" + (task.getTaskId());
                listAssignments.getItems().add(t);
            } else if (task.getType() == TaskType.EXAM){
                String t = (task.getSubject().toString()) + " - due on the " + (localDate.format(DateTimeFormatter.ISO_DATE)) + " " + (Utilities.capitalizeFirstLetter(task.getDueDay().toString())) +
                        " - on period " + (task.getDuePeriod().getNumber()) + " - " + (Utilities.capitalizeFirstLetter(task.getDuePeriod().getSubject().getSubject().toString())) + " class." +
                " - id:" + (task.getTaskId());
                listExams.getItems().add(t);
            } else {
                String t = (task.getSubject().toString()) + " - due on the " + (localDate.format(DateTimeFormatter.ISO_DATE)) + " " + (Utilities.capitalizeFirstLetter(task.getDueDay().toString())) +
                        " - on period " + (task.getDuePeriod().getNumber()) + " - " + (Utilities.capitalizeFirstLetter(task.getDuePeriod().getSubject().getSubject().toString())) + " class." +
                        " - id:" + (task.getTaskId());
                listHomeworks.getItems().add(t);
            }
        }
    }

    private void delete(TaskType taskType){
        if (taskType == TaskType.ASSIGNMENT)
            selectedItem = listAssignments.getSelectionModel().getSelectedItem();
        else if (taskType == TaskType.EXAM)
            selectedItem = listExams.getSelectionModel().getSelectedItem();
        else if (taskType == TaskType.HOMEWORK)
            selectedItem = listHomeworks.getSelectionModel().getSelectedItem();

        int id = Utilities.getIdFromTask(selectedItem);
        LinkedList<Task> newTasks = new LinkedList<>();

        for (Task task: tasks){
            if (task.getTaskId() != id){
                newTasks.add(task);
            }
        }

        student.setTasks(newTasks);

        try {
            ObjectSerializer.serializeStudent(filePath, student);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        refresh();
    }
}
