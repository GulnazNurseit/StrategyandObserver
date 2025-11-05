public class ItalicTextFormat implements TextFormatStrategy {
    @Override
    public String format(String text) {
        return "\u001B[3m" + text + "\u001B[0m";
    }

    @Override
    public String getName() {
        return "Italic";
    }
}
