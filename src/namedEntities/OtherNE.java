package namedEntities;

import java.util.List;

public class OtherNE extends NamedEntity {

    // TODO: Add other attributes
    private String otherField;

    public OtherNE(String label, String category, List<String> topics) {
        super(label, category, topics);
    }

    public String getOtherField() {
        return this.otherField;
    }
}
