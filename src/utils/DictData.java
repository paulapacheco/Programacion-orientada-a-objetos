package utils;

import java.util.List;

public class DictData {
    private String label;
    private String category;
    private List<String> topics;
    private List<String> keywords;

    public DictData(String label, String category, List<String> topics, List<String> keywords) {
        this.label = label;
        this.category = category;
        this.topics = topics;
        this.keywords = keywords;
    }

    public String getLabel() {
        return this.label;
    }

    public String getCategory() {
        return this.category;
    }

    public List<String> getTopics() {
        return this.topics;
    }

    public List<String> getKeywords() {
        return this.keywords;
    }

    public void print() {
        System.out.println("Label: " + label);
        System.out.println("Category: " + category);
        System.out.println("Topics: " + topics);
    }
}
