package utils;

public class FeedsData {
    private String label;
    private String url;
    private String type;

    public FeedsData(String label, String url, String type) {
        this.label = label;
        this.url = url;
        this.type = type;
    }

    public String getLabel() {
        return this.label;
    }

    public String getUrl() {
        return this.url;
    }

    public String getType() {
        return this.type;
    }

    public void print() {
        System.out.println("Feed: " + label);
        System.out.println("URL: " + url);
        System.out.println("Type: " + type);
    }
}