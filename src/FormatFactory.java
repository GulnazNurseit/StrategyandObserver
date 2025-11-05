public class FormatFactory {
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String YELLOW = "\u001B[33m";

    public static TextFormatStrategy createFormat(String formatType) {
        switch (formatType.toLowerCase()) {
            case "bold":
                return new BoldTextFormat();
            case "italic":
                return new ItalicTextFormat();
            case "underline":
                return new UnderlineTextFormat();
            case "red":
                return new ColorTextFormat(RED, "Red");
            case "green":
                return new ColorTextFormat(GREEN, "Green");
            case "blue":
                return new ColorTextFormat(BLUE, "Blue");
            case "yellow":
                return new ColorTextFormat(YELLOW, "Yellow");
            default:
                throw new IllegalArgumentException("Unknown format: " + formatType);
        }
    }
}