import java.io.*;
import java.time.LocalDateTime;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DocumentExporter implements Observer {
    private String currentText;
    private String currentFormat;
    private String currentColor;

    @Override
    public void update(String eventType, String text) {
        if ("textChanged".equals(eventType) || "formatApplied".equals(eventType)) {
            this.currentText = text;
        }
    }

    public void setFormatInfo(String format, String color) {
        this.currentFormat = format;
        this.currentColor = color;
    }

    public boolean exportToWord(String filename) {
        try {
            if (!filename.endsWith(".doc")) {
                filename += ".doc";
            }

            Path filePath = Paths.get(filename);
            String content = createDocumentContent("WORD");
            Files.write(filePath, content.getBytes());

            return true;
        } catch (Exception e) {
            System.out.println("Error creating Word document: " + e.getMessage());
            return false;
        }
    }

    public boolean exportToPDF(String filename) {
        try {
            if (!filename.endsWith(".pdf")) {
                filename += ".pdf";
            }

            Path filePath = Paths.get(filename);
            String content = createDocumentContent("PDF");
            Files.write(filePath, content.getBytes());

            return true;
        } catch (Exception e) {
            System.out.println("Error creating PDF document: " + e.getMessage());
            return false;
        }
    }

    private String createDocumentContent(String formatType) {
        StringBuilder doc = new StringBuilder();
        doc.append("=== ").append(formatType).append(" DOCUMENT ===\n\n");
        doc.append("TEXT EDITOR - DOCUMENT EXPORT\n\n");
        doc.append("Created: ").append(LocalDateTime.now()).append("\n");
        doc.append("Text Format: ").append(currentFormat).append("\n");
        doc.append("Text Color: ").append(currentColor).append("\n");
        doc.append("Character Count: ").append(removeFormatting(currentText).length()).append("\n\n");
        doc.append("CONTENT:\n");
        doc.append("=").append("=".repeat(50)).append("\n");
        doc.append(removeFormatting(currentText)).append("\n");
        doc.append("=").append("=".repeat(50)).append("\n\n");
        doc.append("End of document");

        return doc.toString();
    }

    private String removeFormatting(String text) {
        return text.replaceAll("\u001B\\[[;\\d]*m", "");
    }
}
