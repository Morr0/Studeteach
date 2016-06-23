package archavexm.studeteach.app;

import archavexm.studeteach.core.Studeteach;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StartupController {
    @FXML
    private Button student;

    public void toMainMenuStudent() throws Exception{
        toMainMenu("Student");
    }

    public void toMainMenuTeacher() throws Exception {
        toMainMenu("Teacher");
    }

    private void toMainMenu(String person){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("ToMainMenu.fxml").openStream());

            ToMainMenuController tmmc = (ToMainMenuController)loader.getController();
            tmmc.setLabelTitle(person);

            Stage stage = new Stage();
            stage.getIcons().add(new Image(Studeteach.APP_ICON));
            stage.setTitle(Studeteach.APP_NAME);
            stage.setScene(new Scene(root));
            stage.show();

            Stage s = (Stage)student.getScene().getWindow();
            s.close();
        } catch (Exception exc){
            System.out.println(exc.getMessage());
        }
    }
}
