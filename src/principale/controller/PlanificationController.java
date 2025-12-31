package principale.controller;

import javax.swing.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import principale.model.ApplicationState;
import principale.model.ClientReseau;
import principale.model.EtapeTournee;
import principale.model.Ville;
import principale.view.PlanificationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanificationController {
    private PlanificationView view;
    private ApplicationState state;
    private ApplicationController app;

    public PlanificationController(PlanificationView view, ApplicationState state, ApplicationController app) {
        this.view = view;
        this.state = state;
        this.app = app;

        view.setAjouterListener(e -> {
            Ville v = view.getVilleSelectionnee();
            if(v != null) view.ajouterVille(v);
            else app.afficher(view, false, "Veuillez d'abord sélectionner une ville dans la liste des villes à livrer.");
        });
        
        view.setRetirerListener(e -> {
            Ville v = view.getVilleSelectionneeTournee();
            if(v != null) view.retirerVille(v);            
            else app.afficher(view, false, "Veuillez d'abord sélectionner une ville dans la liste des ville(s) sélectionnée(s).");
        });
        
        view.setAjouterToutesLesVillesListener(e -> {
        	List<Ville> villes = view.getVilles();
        	if(villes != null) view.ajouterToutesLesVilles(villes);
            else app.afficher(view, false, "La liste des villes à livrer est vide!");
        });

        view.setOptimiserListener(e -> lancerOptimisation());
    }

    private void lancerOptimisation() {
        // Utilisation d'un Thread pour ne pas figer l'interface
        new Thread(() -> {
            try {
                // Envoi Camion
                List<EtapeTournee> tournee = traiterCamion(view.getVillesSelectionnees());
                
                // Mise à jour état
                state.setResultats(tournee);
                
                // Affichage (Retour sur le thread graphique)
                SwingUtilities.invokeLater(() -> app.afficherCarte());

            } catch (Exception e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> 
                    JOptionPane.showMessageDialog(view, "Erreur réseau : " + e.getMessage()));
            }
        }).start();
    }

    private List<EtapeTournee> traiterCamion(List<Ville> input) throws Exception {
        if (input.isEmpty()) return new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();

        // Préparation des données pour l'envoi (Serialization)
        // Le serveur attend un objet : { "region": "...", "villes": [...] }
        Map<String, Object> donneesEnvoi = new HashMap<>();
        donneesEnvoi.put("region", state.getRegionSelectionnee().getCode());
        donneesEnvoi.put("villes", input);

        // Conversion de l'objet Map en String JSON
        String jsonEnvoi = mapper.writeValueAsString(donneesEnvoi);

        // Envoi via le réseau
        ClientReseau.getInstance().envoyerMessage(jsonEnvoi);
        
        // Réception de la réponse (Bloquant)
        String jsonRecu = ClientReseau.getInstance().recevoirMessage();

        // Parsing de la réponse (Deserialization)
        // Le serveur renvoie un tableau d'objets : [...]
        // On utilise TypeReference pour dire à Jackson qu'on veut une "List<EtapeTournee>"
        List<EtapeTournee> resultat = mapper.readValue(jsonRecu, new TypeReference<List<EtapeTournee>>(){});
        
        return resultat; 
    }
}