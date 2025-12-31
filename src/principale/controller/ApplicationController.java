package principale.controller;

import java.awt.Component;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import principale.model.ApplicationState;
import principale.model.ConnexionModel;
import principale.model.EtapeTournee;
import principale.model.Ville;
import principale.utils.VilleParser;
import principale.utils.VilleParserFactory;
import principale.view.CarteView;
import principale.view.ConnexionView;
import principale.view.PlanificationView;
import principale.view.RegionView;

public class ApplicationController {
    private final ApplicationState state = new ApplicationState();
    
    // Références pour fermer les fenêtres
    private ConnexionView connexionView;
    private RegionView regionView;
    private PlanificationView planificationView;
    private CarteView carteView;

    public void start() {
        afficherConnexion();
    }

    public void afficherConnexion() {
    	if (connexionView == null) {
	        connexionView = new ConnexionView();
	        ConnexionModel model = new ConnexionModel();
	        new ConnexionController(connexionView, model, this);
	    }
	    connexionView.setVisible(true);
    }

    public void onConnected(Socket socket) {
    	if(socket != null) {
    		connexionView.setVisible(false);
    		afficherSelectionRegion();
    	} else {
    		afficher(connexionView, false, "Echec de connexion, veuillez rééssayer!");
    	}
    }
    
    public void afficherSelectionRegion() {
        if (regionView == null) {
            regionView = new RegionView();
            
            regionView.setValiderListener(e -> {
                // Mise à jour de l'état
                state.setRegionSelectionnee(regionView.getRegion());
                
                regionView.setVisible(false);
                
                afficherPlanification();
            });
        }
        regionView.setVisible(true);
    }

    public void afficherPlanification() {
    	try {
    		List<Ville> villes = chargerVillesSelonRegion();
        	
            planificationView = new PlanificationView(villes);
            new PlanificationController(planificationView, state, this);
            
            planificationView.setRetourRegionListener(e -> {
            	planificationView.dispose();
            	afficherSelectionRegion();
            });
            
            planificationView.setVisible(true);
		} catch (Exception e) {
			afficher(planificationView, false, "Erreur de chargement des villes!");
		}
    }
    
    private List<Ville> chargerVillesSelonRegion() throws Exception {
    	List<Ville> listeVilles = new ArrayList<>();
    	String nomFichier = state.getRegionSelectionneeString() + ".json";
    	
    	VilleParser parser = VilleParserFactory.create(nomFichier);
    	listeVilles = parser.parse(nomFichier);
    	
    	return listeVilles;
    }

    public void afficherCarte() {
    	planificationView.setVisible(false);
    	
        List<EtapeTournee> resultat = state.getTourneeCamion();
        
        if (carteView != null) carteView.dispose();
        
        if (resultat == null || resultat.isEmpty()) {
            afficher(planificationView, false, "Erreur d'affichage: aucun trajet à afficher.");
        	planificationView.setVisible(true);
            return;
        }

        carteView = new CarteView(resultat);
        
        carteView.setRetourListener(e -> {
            carteView.dispose();
            planificationView.setVisible(true);
        });
        
        carteView.setVisible(true);
    }
    
    public void afficher(Component component , boolean succes, String message) {
    	int messageType = JOptionPane.ERROR_MESSAGE;
    	String titre = "Erreur";
    	
    	if (succes) {
    		messageType = JOptionPane.INFORMATION_MESSAGE;
    		titre = "Confirmation";
    	}
   	
    	JOptionPane.showMessageDialog(component, message, titre, messageType);
    }
    
    public ApplicationState getState() { return state; }
}