public class TextEditorFacade {
    private TextEditor textEditor;
    private DocumentExporter documentExporter;

    public TextEditorFacade() {
        this.textEditor = new TextEditor();
        this.documentExporter = new DocumentExporter();

        textEditor.addObserver(new UIComponent());
        textEditor.addObserver(new AutoSaveModule());
        textEditor.addObserver(new SpellChecker());
        textEditor.addObserver(documentExporter);
    }

    public void setText(String text) {
        textEditor.setText(text);
    }

    public void saveToFile(String filename) {
        textEditor.saveToFile(filename);
    }

    public void applyBold() {
        textEditor.setTextFormat(FormatFactory.createFormat("bold"));
    }

    public void applyItalic() {
        textEditor.setTextFormat(FormatFactory.createFormat("italic"));
    }

    public void applyUnderline() {
        textEditor.setTextFormat(FormatFactory.createFormat("underline"));
    }

    public void applyColor(String colorName) {
        textEditor.setColorFormat(FormatFactory.createFormat(colorName));
    }

    public void clearFormats() {
        textEditor.clearFormats();
    }

    public String getCurrentText() {
        return textEditor.getText();
    }

    public String getCurrentFormat() {
        return textEditor.getCurrentFormat();
    }

    public String getCurrentColor() {
        return textEditor.getCurrentColor();
    }

    public void showFormatHistory() {
        textEditor.showFormatHistory();
    }

    public boolean exportToWord(String filename) {
        documentExporter.setFormatInfo(getCurrentFormat(), getCurrentColor());
        return documentExporter.exportToWord(filename);
    }

    public boolean exportToPDF(String filename) {
        documentExporter.setFormatInfo(getCurrentFormat(), getCurrentColor());
        return documentExporter.exportToPDF(filename);
    }
}
