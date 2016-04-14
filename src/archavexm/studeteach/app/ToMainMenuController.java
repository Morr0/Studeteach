package archavexm.studeteach.app;

import archavexm.studeteach.core.Studeteach;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ToMainMenuController {
    @FXML
    private Label labelTitle;

    private String person;

    public void setLabelTitle(String person){
        this.person = person;
        labelTitle.setText(person);
    }

    public void newUser() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("NewUser.fxml").openStream());
        NewUserController nuc = (NewUserController)loader.getController();
        nuc.setPersonText(person);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("New " + person + " - " + Studeteach.APP_NAME);
        stage.show();

        Stage current = (Stage)labelTitle.getScene().getWindow();
        current.close();
    }

    public void openUser() throws Exception{

    }

}
