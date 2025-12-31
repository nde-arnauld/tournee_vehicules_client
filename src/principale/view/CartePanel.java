package principale.view;

import java.awt.*;
import java.util.List;

import javax.swing.JPanel;

import principale.model.EtapeTournee;
import principale.utils.StyleFactory;
import principale.utils.StyleRoute;

/**
 * Cette classe représente le panel qui contient le dessin de la carte.
 */
public class CartePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private List<EtapeTournee> trajet;
    
    // Bornes de la carte
    private double minLat, maxLat, minLon, maxLon;
    
    // Marge en pixels autour de la carte
    private final int PADDING = 80; 

    public CartePanel() {
        setBackground(Color.WHITE);
    }

    /** 
     * Cette méthode récupère le trajet et lance l'exécution du dessin.
     * 
     * @param trajet
     */
    public void setTrajet(List<EtapeTournee> trajet) {
        this.trajet = trajet;
        calculerEchelle();
        repaint();
    }

    /**
     * Cette méthode se charge de récupérer les plus grandes latitudes et longitudes
     * afin de faire une bonne mise en échelle.
     */
    private void calculerEchelle() {
        if (trajet == null || trajet.isEmpty()) return;

        // Initialisation avec la première ville
        minLat = maxLat = trajet.get(0).getLatitude();
        minLon = maxLon = trajet.get(0).getLongitude();

        // Recherche des min/max
        for (EtapeTournee e : trajet) {
            if (e.getLatitude() < minLat) minLat = e.getLatitude();
            if (e.getLatitude() > maxLat) maxLat = e.getLatitude();
            if (e.getLongitude() < minLon) minLon = e.getLongitude();
            if (e.getLongitude() > maxLon) maxLon = e.getLongitude();
        }

        // Sécurité pour éviter la division par zéro (si une seule ville)
        if (minLat == maxLat) { minLat -= 0.01; maxLat += 0.01; }
        if (minLon == maxLon) { minLon -= 0.01; maxLon += 0.01; }
    }

    /** 
     * Cette méthode se charge de redessiner la carte 
     * lorsqu'il y a des modifications sur le panel.
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
        if (trajet == null || trajet.isEmpty()) return;

        Graphics2D g2d = (Graphics2D) g;
        // Lissage indispensable pour un rendu pro
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        dessinerRoutes(g2d);

        dessinerVilles(g2d);
    }

    private void dessinerRoutes(Graphics2D g2d) {
        for (int i = 0; i < trajet.size() - 1; i++) {
            EtapeTournee depart = trajet.get(i);
            EtapeTournee arrivee = trajet.get(i + 1);

            Point p1 = coordToPixel(depart.getLatitude(), depart.getLongitude());
            Point p2 = coordToPixel(arrivee.getLatitude(), arrivee.getLongitude());

            // Appliquer le style
            StyleRoute style = StyleFactory.obtenir(depart.getTypeRoute());
            style.appliquerStyle(g2d);

            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);

            afficherDistanceSurSegment(g2d, p1, p2, depart.getDistance());
        }
    }

    private void dessinerVilles(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(1));
        
        for (EtapeTournee etape : trajet) {
            Point p = coordToPixel(etape.getLatitude(), etape.getLongitude());

            // Rond blanc avec contour noir (plus lisible)
            g2d.setColor(Color.WHITE);
            g2d.fillOval(p.x - 5, p.y - 5, 10, 10);
            
            g2d.setColor(Color.BLACK);
            g2d.drawOval(p.x - 5, p.y - 5, 10, 10);

            // Nom de la ville
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
            g2d.drawString(etape.getNom(), p.x + 8, p.y + 5);
        }
    }
    
    /** 
     * Cette méthode permet de convertir les données GPS en pixels 
     * pour la carte.
     * @param lat
     * @param lon
     * @return Point
     */
    private Point coordToPixel(double lat, double lon) {
        int w = getWidth() - (2 * PADDING);  // Largeur utile
        int h = getHeight() - (2 * PADDING); // Hauteur utile

        double deltaLat = maxLat - minLat;
        double deltaLon = maxLon - minLon;

        // Formule de projection linéaire
        // X = (Longitude - Min) / Delta * Largeur
        int x = PADDING + (int) ((lon - minLon) / deltaLon * w);
        
        // Y = Hauteur - (Latitude - Min) / Delta * Hauteur
        int y = PADDING + (int) (h - (lat - minLat) / deltaLat * h);

        return new Point(x, y);
    }

    /** 
     * Cette méthode permet d'écrire la distance sur la ligne (route)
     * Elle prend les deux points, calcule le milieu du segment et écrit
     * la distance dessus.
     */
    private void afficherDistanceSurSegment(Graphics2D g2d, Point p1, Point p2, double distance) {
        // Calcul du milieu du segment
        int midX = (p1.x + p2.x) / 2;
        int midY = (p1.y + p2.y) / 2;

        String texte = String.format("%.2f km", distance);

        // Fond blanc transparent sous le texte pour lisibilité
        g2d.setColor(new Color(255, 255, 255, 0));
        g2d.fillRect(midX - 10, midY - 10, 40, 15);

        // Texte de la distance en petit
        g2d.setColor(Color.DARK_GRAY);
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 11));
        g2d.drawString(texte, midX - 8, midY + 2);
    }
}