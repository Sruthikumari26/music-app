package ui;

import db.DatabaseHelper;
import utils.PasswordUtil;

import javax.swing.*;
import java.awt.*;

public class SignupFrame extends JFrame {
    public SignupFrame() {
        setTitle("🎵 MusicWave - Sign Up");
        setSize(420, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel bg = new JPanel(new GridBagLayout());
        bg.setBackground(new Color(24, 26, 42));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 30, 8, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel title = new JLabel("Create Account", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(120, 200, 255));
        gbc.gridy = 0; bg.add(title, gbc);

        JLabel sub = new JLabel("Join MusicWave today 🎧", SwingConstants.CENTER);
        sub.setForeground(Color.LIGHT_GRAY);
        gbc.gridy = 1; bg.add(sub, gbc);

        JTextField userField = field("Username");
        gbc.gridy = 2; gbc.insets = new Insets(25, 30, 8, 30); bg.add(userField, gbc);

        JTextField emailField = field("Email");
        gbc.gridy = 3; gbc.insets = new Insets(8, 30, 8, 30); bg.add(emailField, gbc);

        JPasswordField passField = new JPasswordField(20);
        style(passField);
        gbc.gridy = 4; bg.add(passField, gbc);

        JPasswordField confirmField = new JPasswordField(20);
        style(confirmField);
        gbc.gridy = 5; bg.add(confirmField, gbc);

        JButton signupBtn = new JButton("SIGN UP");
        signupBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        signupBtn.setBackground(new Color(0, 153, 255));
        signupBtn.setForeground(Color.WHITE);
        signupBtn.setFocusPainted(false);
        signupBtn.setPreferredSize(new Dimension(200, 42));
        signupBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 6; gbc.insets = new Insets(20, 30, 8, 30); bg.add(signupBtn, gbc);

        JButton backBtn = new JButton("← Back to Login");
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setForeground(new Color(150, 200, 255));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 7; bg.add(backBtn, gbc);

        add(bg);

        signupBtn.addActionListener(e -> {
            String u = userField.getText().trim();
            String em = emailField.getText().trim();
            String p = new String(passField.getPassword());
            String c = new String(confirmField.getPassword());

            if (u.isEmpty() || em.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.");
                return;
            }
            if (!p.equals(c)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.");
                return;
            }
            if (DatabaseHelper.registerUser(u, em, PasswordUtil.hash(p))) {
                JOptionPane.showMessageDialog(this, "✅ Account created! Please log in.");
                dispose();
                new LoginFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Username or email already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }

    private JTextField field(String placeholder) {
        JTextField f = new JTextField(20);
        style(f);
        f.putClientProperty("placeholder", placeholder);
        return f;
    }

    private void style(JTextField f) {
        f.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        f.setBackground(new Color(38, 42, 60));
        f.setForeground(Color.WHITE);
        f.setCaretColor(Color.WHITE);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 80, 110)),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
    }
}
