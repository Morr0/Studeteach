package archavexm.studeteach.app.common;

import javafx.stage.Stage;

// Tags both student and teacher controllers
// Adds setFilePath(), setCurrentStage() and Init() methods
public interface PersonController {
    void setFilePath(String filePath);
    void setCurrentStage(Stage currentStage);
    void init();
}
