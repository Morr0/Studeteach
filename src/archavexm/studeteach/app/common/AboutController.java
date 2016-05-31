package archavexm.studeteach.app.common;

import archavexm.studeteach.core.Studeteach;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
}
