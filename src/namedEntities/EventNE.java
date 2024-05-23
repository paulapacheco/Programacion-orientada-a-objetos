package namedEntities;

import java.util.Date;
import java.util.List;

public class EventNE extends NamedEntity {

    private Date date;

    public EventNE(String label, String category, List<String> topics) {
        super(label, category, topics);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }
}
