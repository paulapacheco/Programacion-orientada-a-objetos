package namedEntities;

import java.util.List;

public class LocationNE extends NamedEntity {

    // TODO: Add other attributes
    private String latitud;
    private String longitud;

    public LocationNE(String label, String category, List<String> topics) {
        super(label, category, topics);
    }

    public String getLatitud() {
        return this.latitud;
    }

    public String getLongitud() {
        return this.longitud;
    }
}
