package ui;

import db.DatabaseHelper;
import utils.PasswordUtil;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        DatabaseHelper.initialize();

        setTitle("🎵 MusicWave - Login");
        setSize(420, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel bg = new JPanel();
        bg.setBackground(new Color(24, 26, 42));
        bg.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel title = new JLabel("🎵 MusicWave", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(120, 200, 255));
        gbc.gridy = 0; bg.add(title, gbc);

        JLabel sub = new JLabel("Welcome back, music lover!", SwingConstants.CENTER);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sub.setForeground(Color.LIGHT_GRAY);
        gbc.gridy = 1; bg.add(sub, gbc);

        JTextField userField = styledField();
        userField.setToolTipText("Username");
        gbc.gridy = 2; gbc.insets = new Insets(30, 30, 8, 30); bg.add(userField, gbc);

        JPasswordField passField = new JPasswordField(20);
        style(passField);
        gbc.gridy = 3; bg.add(passField, gbc);

        JButton loginBtn = gradientButton("SIGN IN", new Color(0, 153, 255), new Color(0, 102, 204));
        gbc.gridy = 4; gbc.insets = new Insets(20, 30, 8, 30); bg.add(loginBtn, gbc);

        JButton signupBtn = new JButton("New here? Create an account");
        signupBtn.setBorderPainted(false);
        signupBtn.setContentAreaFilled(false);
        signupBtn.setForeground(new Color(150, 200, 255));
        signupBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 5; bg.add(signupBtn, gbc);

        add(bg);

        loginBtn.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword());
            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter username and password.");
                return;
            }
            if (DatabaseHelper.validateUser(user, PasswordUtil.hash(pass))) {
                JOptionPane.showMessageDialog(this, "✅ Login successful!");
                dispose();
                new MusicPlayerFrame(user).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "❌ Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        signupBtn.addActionListener(e -> {
            dispose();
            new SignupFrame().setVisible(true);
        });
    }

    private JTextField styledField() {
        JTextField f = new JTextField(20);
        style(f);
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

    private JButton gradientButton(String text, Color c1, Color c2) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, c1, 0, getHeight(), c2));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 45));
        return btn;
    }
}
