
package ui;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayerFrame extends JFrame {

    private static final String FONT_NAME = "Segoe UI";

    private transient Clip clip;
    private String currentSong = "No song playing";

    private static final long serialVersionUID = 1L;

    public MusicPlayerFrame(String username) {

        setTitle("🎵 MusicWave — Welcome " + username);
        setSize(700, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(20, 22, 34));

        // Header
        JLabel header = new JLabel(
                "  🎧 MusicWave Library",
                SwingConstants.LEFT
        );

        header.setOpaque(true);
        header.setBackground(new Color(30, 34, 52));
        header.setForeground(new Color(120, 200, 255));
        header.setFont(new Font(FONT_NAME, Font.BOLD, 22));
        header.setPreferredSize(new Dimension(0, 60));

        root.add(header, BorderLayout.NORTH);

        // Song list
        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);

        list.setBackground(new Color(28, 32, 48));
        list.setForeground(Color.WHITE);
        list.setFont(new Font(FONT_NAME, Font.PLAIN, 15));
        list.setSelectionBackground(new Color(0, 120, 215));
        list.setFixedCellHeight(40);

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(new EmptyBorder(10, 10, 10, 10));

        root.add(scroll, BorderLayout.CENTER);

        // Controls
        JPanel controls = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 20, 15)
        );

        controls.setBackground(new Color(30, 34, 52));

        JLabel nowPlaying = new JLabel("▶ " + currentSong);
        nowPlaying.setForeground(new Color(200, 220, 255));
        nowPlaying.setFont(new Font(FONT_NAME, Font.ITALIC, 14));

        JButton load = mkBtn("📂 Load Folder");
        JButton play = mkBtn("▶ Play");
        JButton stop = mkBtn("⏹ Stop");

        controls.add(load);
        controls.add(play);
        controls.add(stop);
        controls.add(nowPlaying);

        root.add(controls, BorderLayout.SOUTH);

        add(root);

        // Load music folder
        load.addActionListener(e -> {

            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(
                    JFileChooser.DIRECTORIES_ONLY
            );

            if (fc.showOpenDialog(this)
                    == JFileChooser.APPROVE_OPTION) {

                File dir = fc.getSelectedFile();

                File[] files = dir.listFiles(
                        (d, name) ->
                                name.toLowerCase().endsWith(".wav")
                );

                if (files != null) {

                    model.clear();

                    for (File file : files) {
                        model.addElement(file.getName());
                    }

                    model.add(
                            0,
                            "__PATH__" + dir.getAbsolutePath()
                    );
                }
            }
        });

        // Play selected song
        play.addActionListener(e -> {

            String selectedSong = list.getSelectedValue();

            if (selectedSong == null || model.isEmpty()
                    || selectedSong.startsWith("__PATH__")) {
                return;
            }

            String basePath = model.get(0)
                    .replace("__PATH__", "");

            File file = new File(basePath, selectedSong);

            if (playWav(file)) {
                nowPlaying.setText("▶ " + selectedSong);
            }
        });

        // Stop music
        stop.addActionListener(e -> {

            if (clip != null) {
                clip.stop();
                clip.close();
                clip = null;
            }

            nowPlaying.setText("⏹ Stopped");
        });
    }

    private JButton mkBtn(String text) {

        JButton button = new JButton(text);

        button.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        button.setBackground(new Color(0, 153, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(130, 38));

        return button;
    }

    private boolean playWav(File file) {

        try {

            if (clip != null) {
                clip.stop();
                clip.close();
                clip = null;
            }

            try (AudioInputStream audioStream =
                         AudioSystem.getAudioInputStream(file)) {

                Clip newClip = AudioSystem.getClip();
                newClip.open(audioStream);
                clip = newClip;
                clip.start();
            }

            return true;

        } catch (UnsupportedAudioFileException
                 | IOException
                 | LineUnavailableException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cannot play file: " + ex.getMessage(),
                    "Playback Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }
}
