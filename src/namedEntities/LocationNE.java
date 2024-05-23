package namedEntities;

import java.util.List;

public class LocationNE extends NamedEntity {

    private String latitud;
    private String longitud;

    public LocationNE(String label, String category, List<String> topics) {
        super(label, category, topics);
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return this.latitud;
    }

    public String getLongitud() {
        return this.longitud;
    }
}
