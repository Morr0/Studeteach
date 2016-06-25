package archavexm.studeteach.app;

import archavexm.studeteach.core.Studeteach;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Startup.fxml"));

        primaryStage.getIcons().add(new Image(Studeteach.APP_ICON));
        primaryStage.setTitle(Studeteach.APP_NAME);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
