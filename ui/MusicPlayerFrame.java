package ui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer; // optional (if using JavaFX). See notes below.

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

/**
 * NOTE: For pure Swing playback, use javax.sound.sampled for WAV,
 * or the javazoom JLayer library for MP3.
 * This demo uses a simple WAV playback via Clip.
 */
public class MusicPlayerFrame extends JFrame {
    private Clip clip;
    private String currentSong = "No song playing";

    public MusicPlayerFrame(String username) {
        setTitle("🎵 MusicWave — Welcome " + username);
        setSize(700, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(20, 22, 34));

        // Header
        JLabel header = new JLabel("  🎧 MusicWave Library", SwingConstants.LEFT);
        header.setOpaque(true);
        header.setBackground(new Color(30, 34, 52));
        header.setForeground(new Color(120, 200, 255));
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setPreferredSize(new Dimension(0, 60));
        root.add(header, BorderLayout.NORTH);

        // Song list
        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);
        list.setBackground(new Color(28, 32, 48));
        list.setForeground(Color.WHITE);
        list.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        list.setSelectionBackground(new Color(0, 120, 215));
        list.setFixedCellHeight(40);

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(new EmptyBorder(10, 10, 10, 10));
        root.add(scroll, BorderLayout.CENTER);

        // Controls
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        controls.setBackground(new Color(30, 34, 52));

        JLabel nowPlaying = new JLabel("▶ " + currentSong);
        nowPlaying.setForeground(new Color(200, 220, 255));
        nowPlaying.setFont(new Font("Segoe UI", Font.ITALIC, 14));

        JButton load = mkBtn("📂 Load Folder");
        JButton play = mkBtn("▶ Play");
        JButton stop = mkBtn("⏹ Stop");

        controls.add(load);
        controls.add(play);
        controls.add(stop);
        controls.add(nowPlaying);
        root.add(controls, BorderLayout.SOUTH);

        add(root);

        load.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File dir = fc.getSelectedFile();
                File[] files = dir.listFiles((d, n) -> n.toLowerCase().endsWith(".wav"));
                if (files != null) {
                    model.clear();
                    for (File f : files) model.addElement(f.getName());
                    model.add(0, "__PATH__" + dir.getAbsolutePath());
                }
            }
        });

        play.addActionListener(e -> {
            String sel = list.getSelectedValue();
            if (sel == null || model.isEmpty()) return;
            String basePath = model.get(0).replace("__PATH__", "");
            File file = new File(basePath, sel);
            playWav(file);
            nowPlaying.setText("▶ " + sel);
        });

        stop.addActionListener(e -> {
            if (clip != null) { clip.stop(); clip.close(); }
            nowPlaying.setText("⏹ Stopped");
        });
    }

    private JButton mkBtn(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setBackground(new Color(0, 153, 255));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(130, 38));
        return b;
    }

    private void playWav(File f) {
        try {
            if (clip != null) { clip.stop(); clip.close(); }
            AudioInputStream ais = AudioSystem.getAudioInputStream(f);
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Cannot play file: " + ex.getMessage());
        }
    }
}
