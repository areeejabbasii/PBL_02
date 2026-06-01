package main;

import database.DBConnection;
import gui.LoginFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!DBConnection.testConnection()) {
            JOptionPane.showMessageDialog(null,
                    "Database se connect nahi ho saka.\n\n"
                            + "1. SSMS mein database.sql chalain (OnlineExamSystem)\n"
                            + "2. PowerShell (Admin) se scripts\\enable-sql-server.ps1 chalain\n"
                            + "3. Phir run.bat se app dubara start karein\n\n"
                            + "Details console mein dekhein.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}