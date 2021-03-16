package coreClient;

public class Search {
    private String text;

    public Search() {
        text = null;
    }

    public Search(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
