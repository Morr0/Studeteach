package archavexm.studeteach.core.common.subject;

import org.simpleframework.xml.Root;

@Root(name = "subject")
public enum Subjects {
    ENGLISH,
    ARABIC,
    CHINESE,
    FRENCH,
    ASSYRIAN,
    LANGUAGE,
    GEOGRAPHY,
    HISTORY,
    COMMERCE,
    ART,
    RELIGION,
    MATHEMATICS,
    MUSIC,
    SCIENCE,
    PHYSICS,
    CHEMISTRY,
    BIOLOGY,
    SPORT,
    COMPUTING,
    OTHER,
    NONE;

    @Override
    public String toString(){
        switch (this){
            case ENGLISH:
                return "English";
            case ARABIC:
                return "Arabic";
            case ASSYRIAN:
                return "Assyrian";
            case FRENCH:
                return "French";
            case CHINESE:
                return "Chinese";
            case LANGUAGE:
                return "Language";
            case GEOGRAPHY:
                return "Geography";
            case HISTORY:
                return "History";
            case COMMERCE:
                return "Commerce";
            case CHEMISTRY:
                return "Chemistry";
            case ART:
                return "Art";
            case MATHEMATICS:
                return "Mathematics";
            case MUSIC:
                return "Music";
            case SCIENCE:
                return "Science";
            case PHYSICS:
                return "Physics";
            case BIOLOGY:
                return "Biology";
            case COMPUTING:
                return "Computing";
            case SPORT:
                return "Sport";
            case RELIGION:
                return "Religon";
            case OTHER:
                return "Other";
            default:
                return "None";
        }
    }
}
