package archavexm.studeteach.core.common;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class TODOList {
    @Attribute
    private String name;
    @Attribute
    private boolean isTicked;

    public TODOList() {}

    public TODOList(String name){
        this.name = name;
    }

    public TODOList(String name, boolean isTicked){
        this.name = name;
        this.isTicked = isTicked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTicked() {
        return isTicked;
    }

    public void setTicked(boolean ticked) {
        isTicked = ticked;
    }
}
