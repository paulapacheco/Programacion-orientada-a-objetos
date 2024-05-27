package namedEntities.entitiesManager;

import utils.Category;
import utils.SetTopic;
import java.util.List;

public abstract class NamedEntity {
    private String label;
    private Category category;
    private SetTopic topics;

    public NamedEntity(String label, String category, List<String> listTopics) {
        this.label = label;
        this.category = new Category(category);
        this.topics = SetTopic.topicSetFromList(listTopics);
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