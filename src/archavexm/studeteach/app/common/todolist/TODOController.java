package archavexm.studeteach.app.common.todolist;

import archavexm.studeteach.app.common.Studeteach;
import archavexm.studeteach.core.common.Person;
import archavexm.studeteach.core.common.TODOList;
import archavexm.studeteach.core.student.util.StudentWindow;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.LinkedList;

public class TODOController implements StudentWindow {
    @FXML private ListView<String> listTODOLists;

    private Person person;
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
            person = ObjectDeserializer.deserialize(filePath);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        todoLists = person.getTodoLists();
        if (todoLists.isEmpty()){
            Label label = new Label("You do not have any TODO Lists. You can add TODO Lists by pressing the + Button below.");
            label.setWrapText(true);
            listTODOLists.setPlaceholder(label);
        }

        update();
    }

    private void refresh(){
        try {
            person = ObjectDeserializer.deserialize(filePath);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        todoLists = person.getTodoLists();
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

            Stage currentStage = (Stage) listTODOLists.getScene().getWindow();
            Stage stage = new Stage();
            stage.initOwner(currentStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("TODO List Adder - " + Studeteach.APP_NAME);
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
                String selected = person.getSelectedTODOList(listTODOLists.getSelectionModel().getSelectedItem()).getName().trim();

                if (!todoList.getName().trim().equals(selected))
                    todoLists.add(todoList);
            }
            save();
            refresh();
        }
    }

    public void tick(){
        if (listTODOLists.getSelectionModel().getSelectedItem() != null){
            selectedTODOList = person.getSelectedTODOList(listTODOLists.getSelectionModel().getSelectedItem());
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
        person.setTodoLists(todoLists);

        try {
            ObjectSerializer.serialize(filePath, person);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
