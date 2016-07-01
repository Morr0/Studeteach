package archavexm.studeteach.app.teacher.windows;

import archavexm.studeteach.app.common.PersonWindow;

public class EditorController implements PersonWindow {

    private String filePath;

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void init() {

    }
}
