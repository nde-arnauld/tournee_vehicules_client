package principale.model;

import java.util.List;

public class ApplicationState {
    private Region regionSelectionnee;
    
    private List<EtapeTournee> tourneeCamion;

    public void setRegionSelectionnee(Region region) { this.regionSelectionnee = region; }
    public Region getRegionSelectionnee() { return regionSelectionnee; }
    public String getRegionSelectionneeString() { return regionSelectionnee.getCode(); }

    public void setResultats(List<EtapeTournee> tournee) {
        this.tourneeCamion = tournee;
    }
    
    public List<EtapeTournee> getTourneeCamion() { return tourneeCamion; }
}