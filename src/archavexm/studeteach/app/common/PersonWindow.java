package archavexm.studeteach.app.common;

// this interface is used to tag all the Person windows for passing file path information
// the init() method initializes the windows with it's own properties
public interface PersonWindow {
    void setFilePath(String filePath);
    void init();
}

