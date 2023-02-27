import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class FileLettersCounter {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Write full path to file which you want to be counted:");
        String filePath = in.nextLine();
        String resultContent;
        try {
            resultContent = Files.readString(Path.of(filePath))
                    .chars()
                    .mapToObj(c -> (char)c)
                    .collect(Collectors.groupingBy(ch -> ch, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .filter(ent -> (ent.getKey() >= 'A' && ent.getKey() <= 'Z') || (ent.getKey() >= 'a' && ent.getKey() <= 'z'))
                    .map(ent -> ent.getKey() + " = " + ent.getValue() + "\n")
                    .collect(Collectors.joining());;
        } catch (IOException ex) {
            System.out.println("Cannot read content of file. Provided file may not exist.\n" +
                    "Please write the whole path to the file");
            return;
        }
        try {
            System.out.println("Please, write an output file path (or just push enter if you want to save file in the same directory):");
            String outputPath = in.nextLine();
            outputPath = outputPath.equals("\\s") || outputPath.isBlank() ? filePath + ".count.txt" : outputPath;
            Files.write(Path.of(outputPath), resultContent.getBytes());
            System.out.println("Result of counting saved in " + outputPath);
        } catch (IOException e) {
            System.out.println("Cannot write results of counting in this file");
        }
    }
}