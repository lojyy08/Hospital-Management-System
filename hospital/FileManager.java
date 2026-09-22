import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class FileManager {

    public void writeToFile(String fileName, ArrayList<String> dataList, boolean append) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, append))) {
            for (String line : dataList) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to " + fileName + ": " + e.getMessage());
        }
    }

    public void writeToFile(String fileName, String data, boolean append) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, append))) {
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to " + fileName + ": " + e.getMessage());
        }
    }

    public ArrayList<String> readData(String fileName) {
        ArrayList<String> dataList = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) {
            return dataList; // silent — missing file is handled by caller
        }
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (!line.trim().isEmpty()) {
                    dataList.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return dataList;
    }

    public ArrayList<String[]> getParsedData(String fileName) {
        ArrayList<String[]> parsedList = new ArrayList<>();
        for (String line : readData(fileName)) {
            String[] parts = line.split(",");
            for (int i = 0; i < parts.length; i++) {
                parts[i] = parts[i].trim();
            }
            parsedList.add(parts);
        }
        return parsedList;
    }
}
