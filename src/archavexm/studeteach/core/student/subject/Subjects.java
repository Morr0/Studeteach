package archavexm.studeteach.core.student.subject;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "subject")
public enum Subjects {
    LANGUAGE,
    GEOGRAPHY,
    HISTORY,
    COMMERCE,
    ART,
    MATHEMATICS,
    MUSIC,
    SCIENCE,
    SPORT,
    COMPUTING,
    OTHER,
    NONE
}
