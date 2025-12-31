package principale.model;

import java.io.IOException;


public class ConnexionModel {
    public boolean tenterConnexion(String ip, int port) throws IOException {
        return ClientReseau.getInstance().connecter(ip, port);
    }
}
