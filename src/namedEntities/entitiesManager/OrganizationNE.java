package namedEntities.entitiesManager;

import java.util.List;

public class OrganizationNE extends NamedEntity {
    private String tipoSocietario;
    private String capital;

    public OrganizationNE(String label, String category, List<String> topics) {
        super(label, category, topics);
    }

    public void setTipoSocietario(String tipoSocietario) {
        this.tipoSocietario = tipoSocietario;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getTipoSocietario(String tipoSocietario) {
        return this.tipoSocietario;
    }

    public String getCapital() {
        return this.capital;
    }
}
