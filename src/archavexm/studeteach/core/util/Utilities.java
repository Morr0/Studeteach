package archavexm.studeteach.core.util;

import archavexm.studeteach.core.common.Day;
import archavexm.studeteach.core.common.subject.Subjects;
import archavexm.studeteach.core.student.task.TaskType;
import com.sun.istack.internal.NotNull;

import java.io.*;

// a bunch of utility methods
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
    public static String readFirstLine(String filePath) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line = bufferedReader.readLine();
        bufferedReader.close();
        return line;

    }

    @NotNull
    public static void emptyTheFile(String filePath) throws IOException{
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file);

        char character = ' ';
        fileWriter.write(character);
        fileWriter.close();
    }

    @NotNull
    public static boolean isValidStudeteachFile(String filePath) throws IOException{
        boolean result = false;
        String content = read(filePath);
        if (!filePath.contains(".studeteach"))
            return result;
        else {
            if (content.isEmpty())
                return result;
            else {
                try {
                    String firstTenCharacters = content.substring(0, 10);
                    if (!(firstTenCharacters.contains("student") || firstTenCharacters.contains("teacher")))
                        return result;
                    else
                        result = true;
                } catch (StringIndexOutOfBoundsException ex){
                    return result;
                }
            }
        }

        return result;
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
            case "":
                return Subjects.NONE;
            case "none":
                return Subjects.NONE;
            case "english":
                return Subjects.ENGLISH;
            case "arabic":
                return Subjects.ARABIC;
            case "chinese":
                return Subjects.CHINESE;
            case "french":
                return Subjects.FRENCH;
            case "assyrian":
                return Subjects.ASSYRIAN;
            case "language":
                return Subjects.LANGUAGE;
            case "geography":
                return Subjects.GEOGRAPHY;
            case "history":
                return Subjects.HISTORY;
            case "religion":
                return Subjects.RELIGION;
            case "science":
                return Subjects.SCIENCE;
            case "physics":
                return Subjects.PHYSICS;
            case "chemistry":
                return Subjects.CHEMISTRY;
            case "biology":
                return Subjects.BIOLOGY;
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
            case "math":
                return Subjects.MATHEMATICS;
            case "maths":
                return Subjects.MATHEMATICS;
            default:
                return Subjects.OTHER;
        }
    }

    @NotNull
    public static int getIdFromTask(String input){
        String reversedText = (new StringBuilder(input)).reverse().toString();
        StringBuilder sb = new StringBuilder();

        for (Character character: reversedText.toCharArray()){
            if (character == ':')
                break;

            sb.append(character);
        }
        sb.reverse();

        int output = Integer.parseInt(sb.toString());
        return output;
    }

    @NotNull
    public static TaskType toTaskTypeFromString(String input){
        TaskType taskType = null;
        if (input.equals("Assignment"))
            taskType = TaskType.ASSIGNMENT;
        else if (input.equals("Exam"))
            taskType = TaskType.EXAM;
        else if (input.equals("Homework"))
            taskType = TaskType.HOMEWORK;

        return taskType;
    }
}













