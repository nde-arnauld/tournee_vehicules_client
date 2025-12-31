package principale.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import principale.model.EtapeTournee;

public class CarteView extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private CartePanel cartePanel;
    private JList<String> listeEtapes;
    private DefaultListModel<String> listModel;
    private JButton btnRetour;

    public CarteView(List<EtapeTournee> trajet) {
        setTitle("Visualisation de la Tournée");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // carte (à gauche)
        cartePanel = new CartePanel();
        cartePanel.setTrajet(trajet);
        
        JPanel nord = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        btnRetour = new JButton("◄ Modifier la sélection");
        nord.add(btnRetour);
        
        // feuille de route (à droite)
        JPanel panneauDroit = creerPanneauFeuilleDeRoute(trajet);

        // Assemblage carte + feuille de route
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, cartePanel, panneauDroit);
        
        // On place la séparation à 90% de la largeur
        splitPane.setResizeWeight(0.90);
        splitPane.setDividerLocation(0.90);
        
        add(nord, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
    }
    
    public void setRetourListener(ActionListener l) {
        btnRetour.addActionListener(l);
    }

    private JPanel creerPanneauFeuilleDeRoute(List<EtapeTournee> trajet) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));

        // Titre
        JLabel titre = new JLabel("Feuille de Route", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.PLAIN, 14));
        titre.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(titre, BorderLayout.NORTH);

        // Liste des données
        listModel = new DefaultListModel<>();
        remplirModele(trajet);

        listeEtapes = new JList<>(listModel);
        listeEtapes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listeEtapes.setFont(new Font("SansSerif", Font.PLAIN, 13));
        
        // Petite marge interne pour que le texte respire
        listeEtapes.setFixedCellHeight(50); 
        listeEtapes.setBorder(new EmptyBorder(5, 5, 5, 5));

        // ScrollPane pour faire défiler si la liste est longue
        JScrollPane scrollPane = new JScrollPane(listeEtapes);
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER);
        JPanel panelBas = new JPanel(new BorderLayout());
        panelBas.setBackground(Color.WHITE);
        
        // Le Total
        double totalKm = trajet.stream().mapToDouble(EtapeTournee::getDistance).sum();
        JLabel footer = new JLabel("Distance totale : " + String.format("%.2f km", totalKm));
        footer.setFont(new Font("SansSerif", Font.PLAIN, 14));
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        footer.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // La Légende
        LegendePanel legende = new LegendePanel();
        
        // Assemblage
        panelBas.add(footer, BorderLayout.NORTH);
        panelBas.add(legende, BorderLayout.SOUTH);

        // Ajout au panneau principal droit
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBas, BorderLayout.SOUTH);

        return panel;
    }

    private void remplirModele(List<EtapeTournee> trajet) {
        for (int i = 0; i < trajet.size(); i++) {
            EtapeTournee e = trajet.get(i);
            
            StringBuilder sb = new StringBuilder("<html>");
            
            // Numéro et Nom de la ville en GRAS
            sb.append("<b>").append(i + 1).append(". ").append(e.getNom()).append("</b>");
            
            // Ligne suivante : Type de route et distance (sauf pour la dernière ville)
            if (i < trajet.size() - 1) {
                sb.append("<br>");
                sb.append("<span style='color:gray; font-size:10px;'>");
                sb.append(" via ").append(e.getTypeRoute());
                sb.append(" (").append(e.getDistance()).append(" km)");
                sb.append("</span>");
            } else {
                sb.append("<br><span style='color:green; font-size:10px;'>Arrivée</span>");
            }
            
            sb.append("</html>");
            listModel.addElement(sb.toString());
        }
    }
}