import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatHistory {
    private String formatType;
    private String formatName;
    private String timestamp;

    public FormatHistory(String formatType, String formatName) {
        this.formatType = formatType;
        this.formatName = formatName;
        this.timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public String getFormatType() { return formatType; }
    public String getFormatName() { return formatName; }
    public String getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + formatType + ": " + formatName;
    }
}