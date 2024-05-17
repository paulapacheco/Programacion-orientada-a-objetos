package namedEntities;

import java.util.List;

public class NamedEntity {
    private String label;
    private String category;
    private List<String> topics;
    private static List<String> listaLabels;

    public NamedEntity(String label, String category, List<String> topics) {
        this.label = label;
        this.category = category;
        this.topics = topics;
        //addLabel(label);
    }

    // Maybe this method should be called in the constructor
    // Add a label to the list of labels
    public static void addLabel(String label) {
        listaLabels.add(label);
    }

    public String getName() {
        return this.label;
    }

    public String getCategory() {
        return this.category;
    }

    public List<String> getTopics() {
        return this.topics;
    }

    public static List<String> getListaLabels() {
        return listaLabels;
    }

}