package namedEntities;

import java.util.List;

public abstract class NamedEntity {
    private String label;
    private String category;
    private List<String> topics;

    public NamedEntity(String label, String category, List<String> topics) {
        this.label = label;
        this.category = category;
        this.topics = topics;
    }

    /*
    private String label;
    private Cqtegory category;
    private Set<Topic> topics;
    *
    * */
    public String getLabel() {
        return this.label;
    }

    public String getCategory() {
        return this.category;
    }

    public List<String> getTopics() {
        return this.topics;
    }

}