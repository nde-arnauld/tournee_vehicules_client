package principale.view;

import javax.swing.*;

import principale.utils.StyleFactory;
import principale.utils.StyleRoute;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class LegendePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> itemsLegende;

    public LegendePanel() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Légende"));
        setPreferredSize(new Dimension(200, 100));

        itemsLegende = new LinkedHashMap<>();
        itemsLegende.put("autoroute", "Autoroute / Route Euro.");
        itemsLegende.put("nationale", "Nationale / Voie Rapide");
        itemsLegende.put("departementale", "Départementale / Autre");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int y = 30; // Position verticale de départ
        int xLigne = 20; // X début ligne
        int largeurLigne = 40; // Longueur du trait
        int xTexte = 70; // X début texte

        for (Map.Entry<String, String> entry : itemsLegende.entrySet()) {
            String clefTechnique = entry.getKey();
            String labelHumain = entry.getValue();

            StyleRoute style = StyleFactory.obtenir(clefTechnique);

            style.appliquerStyle(g2d);
            g2d.drawLine(xLigne, y, xLigne + largeurLigne, y);

            // Réinitialisation pour le texte
            g2d.setColor(Color.DARK_GRAY);
            g2d.setStroke(new BasicStroke(1));
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
            
            g2d.drawString(labelHumain, xTexte, y + 5);

            y += 25;
        }
    }
}