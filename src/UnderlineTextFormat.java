ublic class UnderlineTextFormat implements TextFormatStrategy {
    @Override
    public String format(String text) {
        return "\u001B[4m" + text + "\u001B[0m";
    }

    @Override
    public String getName() {
        return "Underline";
    }
}
