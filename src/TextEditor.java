import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextEditor {
    private String text;
    private TextFormatStrategy currentTextFormat;
    private TextFormatStrategy currentColorFormat;
    private List<Observer> observers = new ArrayList<>();
    private String originalText;
    private List<FormatHistory> formatHistory = new ArrayList<>();
    private String currentTextFormatName = "None";
    private String currentColorFormatName = "None";
    private boolean documentOpen = false;

    public void openDocument(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            this.originalText = content.toString().trim();
            this.currentTextFormatName = "None";
            this.currentColorFormatName = "None";
            addToHistory("Document", "Opened: " + filename);
            applyAllFormats();
            documentOpen = true;
            notifyObservers("documentOpened", this.text);
            System.out.println("Document opened: " + filename);
        } catch (IOException e) {
            System.out.println("Error opening file: " + e.getMessage());
        }
    }

    public void closeDocument() {
        if (documentOpen) {
            documentOpen = false;
            addToHistory("Document", "Closed");
            notifyObservers("documentClosed", this.text);
            System.out.println("Document closed");
        }
    }

    public void setText(String text) {
        this.originalText = text;
        this.currentTextFormatName = "None";
        this.currentColorFormatName = "None";
        addToHistory("Initial", "Text set");
        applyAllFormats();
        notifyObservers("textChanged", this.text);
        notifyObservers("textEdited", this.text);
    }

    public void setTextFormat(TextFormatStrategy format) {
        String oldFormat = currentTextFormatName;
        this.currentTextFormat = format;
        this.currentTextFormatName = format != null ? format.getName() : "None";

        if (!oldFormat.equals(currentTextFormatName)) {
            addToHistory("Format", currentTextFormatName);
            notifyObservers("formatChanged", "Text format changed to: " + currentTextFormatName);
        }

        applyAllFormats();
    }

    public void setColorFormat(TextFormatStrategy color) {
        String oldColor = currentColorFormatName;
        this.currentColorFormat = color;
        this.currentColorFormatName = color != null ? color.getName() : "None";

        if (!oldColor.equals(currentColorFormatName)) {
            addToHistory("Color", currentColorFormatName);
            notifyObservers("formatChanged", "Color changed to: " + currentColorFormatName);
        }

        applyAllFormats();
    }

    private void addToHistory(String formatType, String formatName) {
        formatHistory.add(new FormatHistory(formatType, formatName));
    }

    public void applyAllFormats() {
        if (originalText != null) {
            String formattedText = originalText;

            if (currentColorFormat != null) {
                formattedText = currentColorFormat.format(formattedText);
            }

            if (currentTextFormat != null) {
                formattedText = currentTextFormat.format(formattedText);
            }

            this.text = formattedText;
            notifyObservers("formatApplied", this.text);
        }
    }

    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println(removeFormatting(text));
            notifyObservers("documentSaved", text);
            System.out.println("Saved to: " + filename);
        } catch (IOException e) {
            System.out.println("Save error: " + e.getMessage());
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
        System.out.println("Observer removed: " + observer.getClass().getSimpleName());
    }

    private void notifyObservers(String eventType, String text) {
        for (Observer observer : observers) {
            observer.update(eventType, text);
        }
    }

    public String getText() {
        return text;
    }

    public String getCurrentFormat() {
        return currentTextFormatName;
    }

    public String getCurrentColor() {
        return currentColorFormatName;
    }

    public void showFormatHistory() {
        if (formatHistory.isEmpty()) {
            System.out.println("No format history available.");
            return;
        }

        System.out.println("\n=== FORMAT HISTORY ===");
        for (int i = 0; i < formatHistory.size(); i++) {
            FormatHistory history = formatHistory.get(i);
            String currentIndicator = (i == formatHistory.size() - 1) ? " [CURRENT]" : "";
            System.out.println((i + 1) + ". " + history + currentIndicator);
        }
        System.out.println("======================");
    }

    private String removeFormatting(String text) {
        return text.replaceAll("\u001B\\[[;\\d]*m", "");
    }

    public void clearFormats() {
        this.currentTextFormat = null;
        this.currentColorFormat = null;
        this.currentTextFormatName = "None";
        this.currentColorFormatName = "None";
        addToHistory("Clear", "All formats cleared");
        notifyObservers("formatChanged", "All formats cleared");
        applyAllFormats();
    }

    public boolean isDocumentOpen() {
        return documentOpen;
    }
}
