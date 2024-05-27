package namedEntities.entitiesManager;

import java.util.List;

public class OtherNE extends NamedEntity {
    private String suggestedCategory;

    public OtherNE(String label, String category, List<String> topics) {
        super(label, category, topics);
    }

    public void setSuggestedCategory(String suggestedCategory) {
        this.suggestedCategory = suggestedCategory;
    }

    public String getSuggestedCategory() {
        return this.suggestedCategory;
    }
}
