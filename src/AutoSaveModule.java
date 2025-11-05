import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AutoSaveModule implements Observer {
    @Override
    public void update(String eventType, String text) {
        if ("textChanged".equals(eventType) || "formatApplied".equals(eventType)) {
            saveToFile(text, "autosave.txt");
        }
    }

    private void saveToFile(String text, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = dateFormat.format(new Date());
            writer.println("[" + timestamp + "] " + removeFormatting(text));
        } catch (IOException e) {
            System.out.println("Save error: " + e.getMessage());
        }
    }

    private String removeFormatting(String text) {
        return text.replaceAll("\u001B\\[[;\\d]*m", "");
    }
}
