package util;
import java.io.*;
import java.util.*;

public class FileHandler {
    public static void saveToFile(String filePath, List<String> lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }catch (IOException e) {
            throw new IOException("Error writing to file: " + e.getMessage());
        }
    }


public static void appendToFile(String filePath, String line) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            throw new IOException("Error appending to file: " + e.getMessage());
        }
    }
public static List<String> loadFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new IOException("Error reading from file: " + e.getMessage());
        }
        return lines;
    }
}
