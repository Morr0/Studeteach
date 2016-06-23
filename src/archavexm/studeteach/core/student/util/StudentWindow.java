package archavexm.studeteach.core.student.util;

// this interface is used to tag all the student windows for passing file path information
// the init() method initializes the window with it's own properties
public interface StudentWindow {
    void setFilePath(String filePath);
    void init();
}

