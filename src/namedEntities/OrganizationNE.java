package namedEntities;

import java.util.List;

public class OrganizationNE extends NamedEntity {

    // TODO: Add other attributes
    private String tipoSocietario;
    private String capital;

    public OrganizationNE(String label, String category, List<String> topics) {
        super(label, category, topics);
    }

    public String getTipoSocietario(String tipoSocietario) {
        return this.tipoSocietario;
    }

    public String getCapital() {
        return this.capital;
    }
}
