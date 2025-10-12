package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileHandler {

    public static void saveToFile(String filePath, List<String> lines) throws IOException {
        Path path = Paths.get(filePath);
        try {
            Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Failed to save data to " + filePath);
            throw e;
        }
    }

    public static void appendToFile(String filePath, String line) throws IOException {
        Path path = Paths.get(filePath);
        try {
            Files.write(path, Collections.singletonList(line), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Failed to append data to " + filePath);
            throw e;
        }
    }

    public static List<String> loadFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.err.println("Failed to load data from " + filePath);
            throw e;
        }
    }
}