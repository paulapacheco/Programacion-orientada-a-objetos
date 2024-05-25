package namedEntities.entitiesManager;

import java.util.List;

public abstract class NamedEntity {
    private String label;
    private Category category;
    private SetTopic topics;

    public NamedEntity(String label, String category, List<String> listTopics) {
        this.label = label;
        this.category = new Category(category);
        this.topics = new SetTopic();
        for (String topic : listTopics) {
            this.topics.addTopic(new Topic(topic));
        }
    }

    public String getLabel() {
        return this.label;
    }

    public String getCategory() {
        return this.category.getCategoryString();
    }

    public List<String> getTopics() {
        return this.topics.getListTopics();
    }
}