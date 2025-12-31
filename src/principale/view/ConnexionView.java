package principale.view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import principale.utils.Const;

import java.awt.*;
import java.awt.event.ActionListener;

public class ConnexionView extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField ipField;
    private JTextField portField;
    private JButton connectButton;

    public ConnexionView() {
        super("Connexion - Optimisation de Tournées");
        
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);

        setFont(new Font("SansSerif", Font.PLAIN, 12));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        
        // Label 
        JLabel titleLabel = new JLabel("Connexion au serveur");
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 6, 20, 6);

        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.NONE;
        
        // Saisie IP
        ipField = createFixedField();
        ipField.setToolTipText("Exemple: 127.0.0.1");
        ipField.setText("127.0.0.1");
        
        // Saisie Port
        portField = createFixedField();
        portField.setToolTipText("Exemple: 8080");
        portField.setText("8080");

        connectButton = new JButton("Se connecter");
        connectButton.setEnabled(false);

        addRow(mainPanel, gbc, 1, "Adresse IP :", ipField);
        addRow(mainPanel, gbc, 2, "Port :", portField);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(connectButton, gbc);
        
        addValidation();
    }
    
    private JTextField createFixedField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(Const.FIELD_WIDTH, Const.FIELD_HEIGHT));
        field.setMinimumSize(field.getPreferredSize());
        field.setMaximumSize(field.getPreferredSize());
        return field;
    }

    private void addRow(JPanel p, GridBagConstraints gbc, int y, String label, JComponent c) {
        gbc.gridx = 0; 
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.EAST;
        p.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        p.add(c, gbc);
    }

    private void addValidation() {
        DocumentListener listener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validateForm(); }
            public void removeUpdate(DocumentEvent e) { validateForm(); }
            public void changedUpdate(DocumentEvent e) { validateForm(); }
        };

        ipField.getDocument().addDocumentListener(listener);
        portField.getDocument().addDocumentListener(listener);
    }

    // validation du formulaire
    private void validateForm() {
        boolean valid =
                isValidIP(ipField.getText()) &&
                isValidPort(portField.getText());

        connectButton.setEnabled(valid);
    }
    
    // validation de l'adresse IP
    private boolean isValidIP(String ip) {
        String[] parts = ip.split("\\.");
        if (parts.length != 4) return false;

        try {
            for (String p : parts) {
                int v = Integer.parseInt(p);
                if (v < 0 || v > 255) return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    // validation du port de connexion
    private boolean isValidPort(String port) {
        try {
            int p = Integer.parseInt(port);
            return p > 0 && p <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // getters
    public JTextField getIpField() { return ipField; }
    public JTextField getPortField() { return portField; }
    public JButton getConnectButton() { return connectButton; }
    
    public void setConnectListener(ActionListener l) {
        connectButton.addActionListener(l);
    }
}
