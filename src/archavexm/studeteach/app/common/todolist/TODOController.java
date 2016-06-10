package archavexm.studeteach.app.common.todolist;

import archavexm.studeteach.app.student.StudentWindow;
import archavexm.studeteach.core.Studeteach;
import archavexm.studeteach.core.common.TODOList;
import archavexm.studeteach.core.student.Student;
import archavexm.studeteach.core.util.ObjectDeserializer;
import archavexm.studeteach.core.util.ObjectSerializer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.LinkedList;

public class TODOController implements StudentWindow {
    @FXML
    private ListView<String> listTODOLists;

    private Student student;
    private String filePath;
    private LinkedList<TODOList> todoLists;
    private TODOList selectedTODOList;

    @Override
    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    @Override
    public void init(){
        try {
            student = ObjectDeserializer.deserializeStudent(filePath);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        todoLists = student.getTodoLists();

        if (todoLists.isEmpty()){
            listTODOLists.setPlaceholder(new Label("You do not have any TODO Lists. You can add TODO Lists by pressing the + Button below."));
        }

        update();
    }

    private void refresh(){
        try {
            student = ObjectDeserializer.deserializeStudent(filePath);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        todoLists = student.getTodoLists();
        update();
    }

    private void update(){
        listTODOLists.getItems().clear();
        ObservableList<String> list = FXCollections.observableArrayList();

        for (TODOList todoList: todoLists){
            if (!todoList.isTicked())
                list.add(todoList.getName());
            else
                list.add(todoList.getName() + " - TICKED");
        }

        listTODOLists.setItems(list);
        listTODOLists.refresh();
    }

    public void add(){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent todoAdder = loader.load(getClass().getResource("TODOAdder.fxml").openStream());

            TODOAdderController todoAdderController = loader.getController();
            todoAdderController.setFilePath(filePath);
            todoAdderController.init();

            Stage stage = new Stage();
            stage.setTitle("Todo List Adder - " + Studeteach.APP_NAME);
            stage.setScene(new Scene(todoAdder));
            stage.showAndWait();
        } catch (Exception ex){
            ex.printStackTrace();
        }

        refresh();
    }

    public void remove(){
        if (listTODOLists.getSelectionModel().getSelectedItem() != null){
            LinkedList<TODOList> lists = (LinkedList<TODOList>) todoLists.clone();
            todoLists.clear();

            for (TODOList todoList: lists){
                String selected = student.getSelectedTODOList(listTODOLists.getSelectionModel().getSelectedItem()).getName().trim();

                if (!todoList.getName().trim().equals(selected))
                    todoLists.add(todoList);
            }
            save();
            refresh();
        }
    }

    public void tick(){
        if (listTODOLists.getSelectionModel().getSelectedItem() != null){
            selectedTODOList = student.getSelectedTODOList(listTODOLists.getSelectionModel().getSelectedItem());
            selectedTODOList.setTicked(true);
            LinkedList<TODOList> lists = new LinkedList<>();

            for (TODOList todoList: todoLists){
                if (todoList.getName().equals(selectedTODOList.getName()))
                    lists.add(selectedTODOList);
                 else
                    lists.add(todoList);
            }
            todoLists = lists;

            save();
            refresh();
        }
    }

    private void save(){
        student.setTodoLists(todoLists);

        try {
            ObjectSerializer.serializeStudent(filePath, student);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
