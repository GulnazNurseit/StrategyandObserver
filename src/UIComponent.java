public class UIComponent implements Observer {
    @Override
    public void update(String eventType, String text) {
        if ("textChanged".equals(eventType) || "formatApplied".equals(eventType)) {
            System.out.println("UI: " + text);
        }
    }
}
