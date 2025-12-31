package principale.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import principale.model.Region;
import principale.utils.Const;

public class RegionView extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JComboBox<Region> regionBox = new JComboBox<>(Region.values());
    private JButton btnValider = new JButton("Valider la Région");

    public RegionView() {
        setTitle("Choix de la région");
        setSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.NONE;

        // Titre
        JLabel titleLabel = new JLabel("Choix de la région");
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.gridwidth = 1;

        // Label + ComboBox
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Région :"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(regionBox, gbc);

        // Bouton valider
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(btnValider, gbc);
    }

    public Region getRegion() { return (Region) regionBox.getSelectedItem(); }
    public void setValiderListener(ActionListener l) { btnValider.addActionListener(l); }
}