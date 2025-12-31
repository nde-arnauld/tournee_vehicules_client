package principale.model;

import com.fasterxml.jackson.annotation.JsonProperty;

// "extends Ville" signifie qu'une EtapeTournee possède déjà nom, lat et lon.
public class EtapeTournee extends Ville {

    @JsonProperty("type_route")
    private String typeRoute;

    @JsonProperty("distance")
    private double distance;

    // Constructeur vide pour Jackson
    public EtapeTournee() {
        super();
    }

    // Getters spécifiques
    public String getTypeRoute() {
        return typeRoute != null ? typeRoute : "defaut";
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return super.toString() + " -> " + getTypeRoute() + " (" + distance + " km)";
    }
}