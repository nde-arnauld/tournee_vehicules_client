package principale.controller;

import principale.model.ClientReseau;
import principale.model.ConnexionModel;
import principale.view.ConnexionView;

public class ConnexionController {
    private ConnexionView view;
    private ConnexionModel model;
    private ApplicationController app;

    public ConnexionController(ConnexionView view, ConnexionModel model, ApplicationController app) {
        this.view = view;
        this.model = model;
        this.app = app;

        this.view.setConnectListener(e -> connect());
    }

    private void connect() {
        try {
        	String ip = view.getIpField().getText();
        	int port = Integer.parseInt(view.getPortField().getText());
        	
            if(model.tenterConnexion(ip, port)) {
            	app.afficher(view, true, "Connexion au serveur réussie!");
            }
            
            // Succès
            app.onConnected(ClientReseau.getInstance().getSocket());
        } catch (Exception ex) {
        	app.afficher(view, false, "Erreur de connexion au serveur!!");
        	ex.printStackTrace();
        }
    }
}