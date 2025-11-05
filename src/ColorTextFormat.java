public class ColorTextFormat implements TextFormatStrategy {
    private String colorCode;
    private String colorName;

    public ColorTextFormat(String colorCode, String colorName) {
        this.colorCode = colorCode;
        this.colorName = colorName;
    }

    @Override
    public String format(String text) {
        return colorCode + text + "\u001B[0m";
    }

    @Override
    public String getName() {
        return colorName;
    }
}
