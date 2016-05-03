package archavexm.studeteach.core.util;

import archavexm.studeteach.core.student.subject.Subjects;
import archavexm.studeteach.core.student.timetable.Day;
import com.sun.istack.internal.NotNull;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public final class Utilities {
    @NotNull
    public static String read(String filePath) throws IOException{
        FileReader fr = new FileReader(filePath);
        BufferedReader reader = new BufferedReader(fr);

        StringBuilder content = new StringBuilder();
        String line = reader.readLine();

        while (line != null){
            content.append(line);
            line = reader.readLine();
        }

        String output = content.toString();
        return output;
    }

    @NotNull
    public static boolean isDigit(String input){
        char[] array = input.toCharArray();
        boolean output = false;

        for (Character character: array){
            if (Character.isDigit(character)){
                output = true;
            }
            else {
                output = false;
            }
        }

        return output;
    }

    @NotNull
    public static String firstLetterToCapitalCaseOnSchoolType(String input){
        char[] array = input.toCharArray();
        String newString = null;

        if (array[0] == 'p'){
            array[0] = 'P';
            newString = array.toString();
        }
        else if (array[0] == 's'){
            array[0] = 'S';
            newString = array.toString();
        }
        else if (array[0] == 'u'){
            array[0] = 'U';
            newString = array.toString();
        }

        return newString;
    }

    @NotNull
    public static Day toDayFromString(String input){
        String i = input.toLowerCase();

        switch (i){
            case "monday":
                return Day.MONDAY;
            case "tuesday":
                return Day.TUESDAY;
            case "wednesday":
                return Day.WEDNESDAY;
            case "thursday":
                return Day.THURSDAY;
            case "friday":
                return Day.FRIDAY;
            case "saturday":
                return Day.SATURDAY;
            default:
                return Day.SUNDAY;
        }
    }

    @NotNull
    public static String capitalizeFirstLetter(String input){
        String newInput = input.toLowerCase();
        String capital = (newInput.trim()).substring(0, 1).toUpperCase();
        String output = capital + (newInput.trim()).substring(1);
        return output;
    }

    @NotNull
    public static Subjects toSubjectsFromString(String input){
        String i = input.toLowerCase();

        switch (i){
            case "language":
                return Subjects.LANGUAGE;
            case "geography":
                return Subjects.GEOGRAPHY;
            case "history":
                return Subjects.HISTORY;
            case "science":
                return Subjects.SCIENCE;
            case "music":
                return Subjects.MUSIC;
            case "art":
                return Subjects.ART;
            case "commerce":
                return Subjects.COMMERCE;
            case "computing":
                return Subjects.COMPUTING;
            case "sport":
                return Subjects.SPORT;
            case "mathematics":
                return Subjects.MATHEMATICS;
            default:
                return Subjects.OTHER;
        }
    }

}













