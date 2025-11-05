public class SpellChecker implements Observer {
    @Override
    public void update(String eventType, String text) {
        switch (eventType) {
            case "textChanged":
            case "textEdited":
                System.out.println("SpellChecker: Checking spelling...");
                checkSpelling(text);
                break;
            case "documentOpened":
                System.out.println("SpellChecker: Document opened");
                break;
            case "documentClosed":
                System.out.println("SpellChecker: Document closed");
                break;
        }
    }

    private void checkSpelling(String text) {
        String cleanText = removeFormatting(text);
        String[] words = cleanText.split("\\s+");
        boolean hasErrors = false;

        for (String word : words) {
            if (isMisspelled(word)) {
                System.out.println("SpellChecker: '" + word + "' might be misspelled");
                hasErrors = true;
            }
        }

        if (!hasErrors && words.length > 0) {
            System.out.println("SpellChecker: No errors found");
        }
    }

    private boolean isMisspelled(String word) {
        String[] misspelledWords = {"eror", "recieve", "seperate", "definately"};
        for (String misspelled : misspelledWords) {
            if (word.equalsIgnoreCase(misspelled)) {
                return true;
            }
        }
        return false;
    }

    private String removeFormatting(String text) {
        return text.replaceAll("\u001B\\[[;\\d]*m", "");
    }
}