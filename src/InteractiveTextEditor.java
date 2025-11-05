import java.util.Scanner;

public class InteractiveTextEditor {
    private static TextEditorFacade editor;
    private static Scanner scanner;

    public static void main(String[] args) {
        editor = new TextEditorFacade();
        scanner = new Scanner(System.in);

        System.out.println("TEXT EDITOR");
        System.out.println("===========");

        enterInitialText();

        while (true) {
            showMainMenu();
        }
    }

    private static void enterInitialText() {
        System.out.print("Enter initial text: ");
        String text = scanner.nextLine();

        if (text.trim().isEmpty()) {
            System.out.println("Text cannot be empty! Using default text.");
            text = "Edit this text...";
        }

        editor.setText(text);
        System.out.println("Text set successfully!\n");
    }

    private static void showMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("CURRENT TEXT: " + editor.getCurrentText());
        System.out.println("FORMAT: " + editor.getCurrentFormat() + " | COLOR: " + editor.getCurrentColor());
        System.out.println("=".repeat(50));

        System.out.println("1. Edit Text");
        System.out.println("2. Formatting");
        System.out.println("3. Save to File");
        System.out.println("4. Export Document");
        System.out.println("5. Format History");
        System.out.println("6. Exit");
        System.out.print("Choose option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                editText();
                break;
            case 2:
                showFormattingMenu();
                break;
            case 3:
                saveToFile();
                break;
            case 4:
                exportDocument();
                break;
            case 5:
                editor.showFormatHistory();
                break;
            case 6:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private static void editText() {
        System.out.println("\n--- TEXT EDITING ---");
        System.out.println("Current text: " + editor.getCurrentText());
        System.out.println("1. Change entire text");
        System.out.println("2. Append text to end");
        System.out.println("3. Replace part of text");
        System.out.println("4. Back");
        System.out.print("Choose option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                changeFullText();
                break;
            case 2:
                appendText();
                break;
            case 3:
                replaceText();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private static void changeFullText() {
        System.out.print("\nEnter new text: ");
        String newText = scanner.nextLine();

        if (newText.trim().isEmpty()) {
            System.out.println("Text cannot be empty!");
            return;
        }

        editor.setText(newText);
        System.out.println("Text changed successfully!");
    }

    private static void appendText() {
        System.out.print("\nEnter text to append: ");
        String additionalText = scanner.nextLine();

        if (additionalText.trim().isEmpty()) {
            System.out.println("Text cannot be empty!");
            return;
        }

        String currentText = removeFormatting(editor.getCurrentText());
        String newText = currentText + " " + additionalText;
        editor.setText(newText);
        System.out.println("Text appended successfully!");
    }

    private static void replaceText() {
        String currentText = removeFormatting(editor.getCurrentText());
        System.out.println("\nCurrent text: " + currentText);
        System.out.print("Enter text to find: ");
        String searchText = scanner.nextLine();

        System.out.print("Enter replacement text: ");
        String replaceText = scanner.nextLine();

        if (searchText.trim().isEmpty()) {
            System.out.println("Search text cannot be empty!");
            return;
        }

        if (!currentText.contains(searchText)) {
            System.out.println("Text to replace not found!");
            return;
        }

        String newText = currentText.replace(searchText, replaceText);
        editor.setText(newText);
        System.out.println("Text replaced successfully!");
    }

    private static void showFormattingMenu() {
        while (true) {
            System.out.println("\n--- TEXT FORMATTING ---");
            System.out.println("Text: " + editor.getCurrentText());
            System.out.println("1. Bold");
            System.out.println("2. Italic");
            System.out.println("3. Underline");
            System.out.println("4. Red Color");
            System.out.println("5. Green Color");
            System.out.println("6. Blue Color");
            System.out.println("7. Yellow Color");
            System.out.println("8. Clear Formatting");
            System.out.println("9. Back");
            System.out.print("Choose format: ");

            int choice = getIntInput();

            if (choice == 9) break;

            switch (choice) {
                case 1:
                    editor.applyBold();
                    System.out.println("Bold applied");
                    break;
                case 2:
                    editor.applyItalic();
                    System.out.println("Italic applied");
                    break;
                case 3:
                    editor.applyUnderline();
                    System.out.println("Underline applied");
                    break;
                case 4:
                    editor.applyColor("red");
                    System.out.println("Red color applied");
                    break;
                case 5:
                    editor.applyColor("green");
                    System.out.println("Green color applied");
                    break;
                case 6:
                    editor.applyColor("blue");
                    System.out.println("Blue color applied");
                    break;
                case 7:
                    editor.applyColor("yellow");
                    System.out.println("Yellow color applied");
                    break;
                case 8:
                    editor.clearFormats();
                    System.out.println("Formatting cleared");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void saveToFile() {
        System.out.print("\nEnter filename (e.g., document.txt): ");
        String filename = scanner.nextLine();
        editor.saveToFile(filename);
    }

    private static void exportDocument() {
        System.out.println("\n--- DOCUMENT EXPORT ---");
        System.out.println("1. Word Document (.doc)");
        System.out.println("2. PDF Document (.pdf)");
        System.out.println("3. Back");
        System.out.print("Choose format: ");

        int choice = getIntInput();

        if (choice == 3) return;

        System.out.print("Enter filename: ");
        String filename = scanner.nextLine();

        switch (choice) {
            case 1:
                if (editor.exportToWord(filename)) {
                    System.out.println("Word document created: " + filename + ".doc");
                    System.out.println("File saved in project folder");
                } else {
                    System.out.println("Error creating Word document");
                }
                break;
            case 2:
                if (editor.exportToPDF(filename)) {
                    System.out.println("PDF document created: " + filename + ".pdf");
                    System.out.println("File saved in project folder");
                } else {
                    System.out.println("Error creating PDF document");
                }
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private static String removeFormatting(String text) {
        return text.replaceAll("\u001B\\[[;\\d]*m", "");
    }

    private static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a number: ");
            }
        }
    }
}
