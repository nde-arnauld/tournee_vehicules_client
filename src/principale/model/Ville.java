package principale.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ville {
	@JsonProperty("ville")
    protected String nom;
	
	@JsonProperty("latitude")
    protected double latitude;
	
	@JsonProperty("longitude")
    protected double longitude;

    public Ville() {}
    
    public Ville(String nom, double latitude, double longitude) {
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String toJSON() {
        return String.format("{\"ville\":\"%s\", \"latitude\":%s, \"longitude\":%s}", 
                nom, 
                String.valueOf(latitude).replace(',', '.'), 
                String.valueOf(longitude).replace(',', '.')
        );
    }

    public String getNom() { return nom; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    
    @Override
    public String toString() { 
    	return nom; 
    }
}