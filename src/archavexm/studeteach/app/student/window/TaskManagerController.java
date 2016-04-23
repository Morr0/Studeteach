package archavexm.studeteach.app.student.window;

import archavexm.studeteach.core.Studeteach;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.student.task.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class TaskManagerController {
    @FXML
    private Label labelNumOfTasks;
    @FXML
    private ListView<Assignment> listAssignments;
    @FXML
    private ListView<Exam> listExams;
    @FXML
    private ListView<Homework> listHomeworks;

    private Student oStudent;
    private Student student;

    private String filePath;
    private String typeOfTask;


    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public void setOldStudent(Student student){
        oStudent = student;
    }

    public void init(){
        try {
            int numOfTasks = getNumberOfTasks();
            if (numOfTasks != 0){
                for (Task task: oStudent.getTasks()){
                    if (task.getType() == TaskType.ASSIGNMENT){
                        listAssignments.getItems().add((Assignment) task);
                        typeOfTask = "Assignment";
                    }
                    if (task.getType() == TaskType.EXAM){
                        listExams.getItems().add((Exam) task);
                        typeOfTask = "Exam";
                    }
                    if (task.getType() == TaskType.HOMEWORK){
                        listHomeworks.getItems().add((Homework) task);
                        typeOfTask = "Homework";
                    }
                }
            }

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

    public void deleteAssignment(ActionEvent event){

    }

    public void deleteExam(){

    }

    public void deleteHomework(){

    }

    public void save(){

    }

    private int getNumberOfTasks(){
        String num = null;
        int output = 0;

        if (oStudent.getTasks() == null){
            num = "0";
        }
        else {
            num = Integer.toString(oStudent.getTasks().size());
            output = oStudent.getTasks().size();
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

            if (typeOfTask == "Assignment"){
                taskAdderController.setAssignment(new Assignment());
                taskAdderController.setTypeOfTask("Assignment");
            }
            else if (typeOfTask == "Exam"){
                taskAdderController.setExam(new Exam());
                taskAdderController.setTypeOfTask("Exam");
            }
            else {
                taskAdderController.setHomework(new Homework());
                taskAdderController.setTypeOfTask("Homework");
            }

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

    }
}
