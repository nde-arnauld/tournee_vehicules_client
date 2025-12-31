package principale.view;
import principale.model.Ville;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlanificationView extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private List<Ville> villes = new ArrayList<Ville>();
	private DefaultListModel<Ville> modeleVilles = new DefaultListModel<>();
    private DefaultListModel<Ville> modeleVillesSelectionnees = new DefaultListModel<>();
    
    private JButton btnOptimiser = new JButton("Optimiser & Visualiser ►");
    private JButton btnAjouter = new JButton("Ajouter la ville");
    private JButton btnAjouterTout = new JButton("Ajouter toutes les villes");
    private JButton btnRetirer = new JButton("Retirer la ville");
    private JButton btnRetourRegion = new JButton("◄ Changer de Région");

    private JList<Ville> listeVilles = new JList<>(modeleVilles);
    private JList<Ville> listeVillesSelectionnees = new JList<>(modeleVillesSelectionnees);

    public PlanificationView(List<Ville> villes) {
        setTitle("Planification des Camions");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel center = new JPanel(new GridLayout(1, 3, 10, 10));
        center.setBorder(new EmptyBorder(10, 10, 10, 10));
        center.add(creerPanel("Villes à livrer", listeVilles));
        center.add(creerPanel("Ville(s) sélectionnée(s)", listeVillesSelectionnees));
        add(center, BorderLayout.CENTER);
        
        JPanel sud = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        sud.add(btnRetourRegion);
        sud.add(Box.createHorizontalStrut(40));
        sud.add(btnAjouterTout);
        sud.add(btnAjouter);
        sud.add(btnRetirer);
        sud.add(Box.createHorizontalStrut(40));
        sud.add(btnOptimiser);
        add(sud, BorderLayout.SOUTH);
        
        this.villes = villes;

        chargerDonnees();
    }
    
    private JPanel creerPanel(String titre, JComponent c) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder(titre));
        p.add(new JScrollPane(c));
        return p;
    }
    
    private void chargerDonnees() {
    	for(Ville ville: villes) {
    		modeleVilles.addElement(ville);
    	}
    }

    // Méthodes pour le Controller
    public Ville getVilleSelectionnee() { return listeVilles.getSelectedValue(); }
    public List<Ville> getVilles() { return Collections.list(modeleVilles.elements()); }
    
    public Ville getVilleSelectionneeTournee() { return listeVillesSelectionnees.getSelectedValue(); }
    public List<Ville> getVillesSelectionnees() { return Collections.list(modeleVillesSelectionnees.elements()); }
    
    public void ajouterVille(Ville v) { 
    	modeleVillesSelectionnees.addElement(v); 
    	modeleVilles.removeElement(v); 
    }
    
    public void ajouterToutesLesVilles(List<Ville> villes) {
    	for(Ville ville: villes) {
    		modeleVillesSelectionnees.addElement(ville);
    		modeleVilles.removeElement(ville);
    	}
    }
    
    public void retirerVille(Ville v) { 
    	modeleVilles.addElement(v); 
    	modeleVillesSelectionnees.removeElement(v); 
    }

    public void setOptimiserListener(ActionListener l) { btnOptimiser.addActionListener(l); }
    public void setAjouterToutesLesVillesListener(ActionListener l) { btnAjouterTout.addActionListener(l); }
    public void setAjouterListener(ActionListener l) { btnAjouter.addActionListener(l); }
    public void setRetirerListener(ActionListener l) { btnRetirer.addActionListener(l); }
    public void setRetourRegionListener(ActionListener l) { btnRetourRegion.addActionListener(l); }
}