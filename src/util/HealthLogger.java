package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HealthLogger {
    private String fileName = "logs/health.log";

    public void writer(String level, String source, String message) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String text = "[" + timeStamp + "]" +
                "[" + level + "]" +
                "[" + source + "]"
                + message;
        new File("logs").mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));) {
            writer.write(text);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("File has not written: " + e.getMessage());
        }
    }
}
