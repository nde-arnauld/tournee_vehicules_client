package principale;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import principale.controller.ApplicationController;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            ApplicationController app = new ApplicationController();
            app.start();
        });
    }
}