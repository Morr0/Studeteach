package archavexm.studeteach.app.common;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.awt.*;
import java.net.URI;

public class AboutController {

    @FXML
    private Label labelName;
    @FXML
    private Label labelDescription;
    @FXML
    private Label labelCopyright;
    @FXML
    private Label labelVersion;
    @FXML
    private Label labelPublisher;

    public void initialize(){
        labelName.setText(Studeteach.APP_NAME);
        labelVersion.setText("Version: " + Studeteach.APP_VERSION);
        labelDescription.setText(Studeteach.APP_DESCRIPTION);
        labelPublisher.setText(Studeteach.APP_PUBLISHER);
        labelCopyright.setText(Studeteach.APP_COPYRIGHT);
    }

    public void toWebsite(){
        try {
            if (Desktop.isDesktopSupported()){
                Desktop.getDesktop().browse(new URI("https://github.com/Morr0/Studeteach"));
            }
        } catch (Exception ex){}
    }
}
