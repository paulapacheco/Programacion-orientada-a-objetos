package namedEntities;

import java.util.List;

public class EventNE extends NamedEntity {

    // TODO: Add other attributes
    private String date;

    public EventNE(String label, String category, List<String> topics) {
        super(label, category, topics);
    }

    public String getDate() {
        return this.date;
    }
}
