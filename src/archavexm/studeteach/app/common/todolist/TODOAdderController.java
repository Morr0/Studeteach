package archavexm.studeteach.app.common.todolist;

import archavexm.studeteach.core.common.Person;
import archavexm.studeteach.core.common.TODOList;
import archavexm.studeteach.core.util.ObjectDeserializer;
import archavexm.studeteach.core.util.ObjectSerializer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.LinkedList;

public class TODOAdderController {
    @FXML private TextField textTODO;

    private Person person;
    private String filePath;

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public void init(){
        try {
            person = ObjectDeserializer.deserialize(filePath);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void make(){
        if (textTODO.getText().isEmpty() || textTODO.getText().contains("-")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must write a valid list. Only letters are allowed.");
            alert.showAndWait();

        } else {
            TODOList todoList = new TODOList(textTODO.getText());
            LinkedList<TODOList> todoLists = person.getTodoLists();
            todoLists.add(todoList);

            person.setTodoLists(todoLists);
            try {
                ObjectSerializer.serialize(filePath, person);
            } catch (Exception ex){
                ex.printStackTrace();
            }

            Stage currentStage = (Stage) textTODO.getScene().getWindow();
            currentStage.close();
        }
    }
}
