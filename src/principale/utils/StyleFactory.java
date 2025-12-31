package principale.utils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class StyleFactory {
    private static final Map<String, StyleRoute> registre = new HashMap<>();

    // Initialisation des styles
    static {
        // 1. Autoroutes / Routes Européennes : ROUGE ÉPAIS
        StyleRoute styleRapide = g -> {
            g.setColor(new Color(220, 50, 50)); // Rouge vif
            g.setStroke(new BasicStroke(3.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        };
        
        // 2. Voies Rapides / Nationales : BLEU MOYEN
        StyleRoute styleNormal = g -> {
            g.setColor(new Color(50, 100, 220)); // Bleu
            g.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        };

        // 3. Autres / Départementales : GRIS FIN
        StyleRoute styleLent = g -> {
            g.setColor(Color.GRAY);
            g.setStroke(new BasicStroke(1.5f));
        };

        // --- MAPPING (Clés exactes du JSON serveur) ---
        // Adaptez ces clés si votre serveur envoie "autoroute" ou "Autoroute"
        registre.put("route europeenne", styleRapide);
        registre.put("autoroute", styleRapide);
        
        registre.put("voie rapide", styleNormal);
        registre.put("nationale", styleNormal);
        
        registre.put("departementale", styleLent);
        
        registre.put("defaut", styleLent);
    }

    public static StyleRoute obtenir(String type) {
        if (type == null) return registre.get("defaut");
        // On met en minuscule pour éviter les soucis de casse
        return registre.getOrDefault(type.toLowerCase().trim(), registre.get("defaut"));
    }
}
