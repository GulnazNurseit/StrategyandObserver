public class BoldTextFormat implements TextFormatStrategy {
    @Override
    public String format(String text) {
        return "\u001B[1m" + text + "\u001B[0m";
    }

    @Override
    public String getName() {
        return "Bold";
    }
}
