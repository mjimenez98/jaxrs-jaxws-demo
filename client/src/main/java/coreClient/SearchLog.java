package coreClient;

public class SearchLog {
    private String from;
    private String to;
    private ChangeType type;

    public SearchLog() {
        from = null;
        to = null;
        type = null;
    }

    public SearchLog(String from, String to, ChangeType type) {
        this.from = from;
        this.to = to;
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public ChangeType getType() {
        return type;
    }

    public String getTo() {
        return to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setType(ChangeType type) {
        this.type = type;
    }
}
