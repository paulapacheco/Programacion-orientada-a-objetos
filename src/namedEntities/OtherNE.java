package namedEntities;

import java.util.List;

public class OtherNE extends NamedEntity {

    private String suggestedCategory;

    public OtherNE(String label, String category, List<String> topics) {
        super(label, category, topics);
    }

    public String getSuggestedCategory() {
        return this.suggestedCategory;
    }
}
