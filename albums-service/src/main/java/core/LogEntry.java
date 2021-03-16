package core;

import java.io.Serializable;
import java.util.Date;

public class LogEntry implements Serializable {
    private Date timestamp;
    private ChangeType type;
    private String isrc;

    public LogEntry() {
        this.timestamp = new Date();
        this.type = null;
        this.isrc = null;
    }

    public LogEntry(Date timestamp, ChangeType type, String isrc) {
        this.timestamp = timestamp;
        this.type = type;
        this.isrc = isrc;
    }

    public LogEntry(LogEntry entry) {
        this.timestamp = entry.timestamp;
        this.type = entry.type;
        this.isrc = entry.isrc;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public ChangeType getType() {
        return type;
    }

    public String getIsrc() {
        return isrc;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(ChangeType type) {
        this.type = type;
    }

    public void setIsrc(String isrc) {
        this.isrc = isrc;
    }
}
