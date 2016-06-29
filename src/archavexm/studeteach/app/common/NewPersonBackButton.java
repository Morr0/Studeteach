package archavexm.studeteach.app.common;

import archavexm.studeteach.app.ToMainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public interface NewPersonBackButton {
    default void init(Button buttonBack, Node node){
        Image image = new Image(ToMainMenuController.class.getClassLoader().getResourceAsStream("back_button.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(48);
        imageView.setFitWidth(48);
        buttonBack.setGraphic(imageView);
        buttonBack.setOnAction(event -> toToMainMenu(node));
    }

    default void toToMainMenu(Node node){
        try {
            Parent root = FXMLLoader.load(ToMainMenuController.class.getResource("ToMainMenu.fxml"));
            Stage currentStage = (Stage) node.getScene().getWindow();
            Stage toMainMenu = new Stage();
            toMainMenu.setTitle(Studeteach.APP_NAME);
            toMainMenu.getIcons().add(new Image(Studeteach.APP_ICON));
            toMainMenu.setScene(new Scene(root));
            toMainMenu.show();
            currentStage.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
