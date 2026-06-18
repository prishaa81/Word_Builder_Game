import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class WordBuilderGame1 extends JFrame implements ActionListener {

    private final ArrayList<String> dictionary = new ArrayList<>();
    private final Set<String> possibleWords = new HashSet<>();
    private final Set<String> foundWords = new HashSet<>();

    private final String[] rounds = {"ATER", "ALERT"};

    private int round = 0;
    private int score = 0;
    private boolean roundOver = false;

    private JLabel roundLabel, scoreLabel, totalLabel;
    private JTextField inputField;
    private JTextArea foundWordsArea;
    private JPanel tilePanel;

    private JButton submitBtn, giveUpBtn, nextBtn, quitBtn;

    private final Color BG_TOP = new Color(40, 53, 147);
    private final Color BG_BOTTOM = new Color(26, 35, 126);
    private final Color CARD_BG = Color.WHITE;
    private final Color TILE_BG = new Color(255, 213, 79);

    private final Color BTN_GREEN = new Color(76, 175, 80);
    private final Color BTN_BLUE = new Color(33, 150, 243);
    private final Color BTN_GRAY = new Color(120, 120, 120);
    private final Color BTN_RED = new Color(244, 67, 54);

    private final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 30);
    private final Font UI_FONT = new Font("Segoe UI", Font.BOLD, 15);

    public WordBuilderGame1() {

        setTitle("Word Builder Game");
        setSize(920, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GradientPanel root = new GradientPanel(BG_TOP, BG_BOTTOM);
        root.setLayout(new BorderLayout(20, 20));
        root.setBorder(new EmptyBorder(20, 20, 20, 20));

        setContentPane(root);

        loadDictionary();
        prepareRound();

        JLabel titleLabel = new JLabel("WORD PLAY!", JLabel.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);

        JSeparator separator = new JSeparator();
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        separator.setForeground(Color.WHITE);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        JPanel statsPanel = new JPanel(new GridLayout(1, 3));
        statsPanel.setOpaque(false);

        roundLabel = createStat("Round: 1");
        scoreLabel = createStat("Score: 0");
        totalLabel = createStat("Total Words: " + possibleWords.size());

        statsPanel.add(roundLabel);
        statsPanel.add(scoreLabel);
        statsPanel.add(totalLabel);

        JPanel topWrapper = new JPanel();
        topWrapper.setOpaque(false);
        topWrapper.setLayout(new BoxLayout(topWrapper, BoxLayout.Y_AXIS));

        topWrapper.add(titleLabel);
        topWrapper.add(Box.createVerticalStrut(8));
        topWrapper.add(separator);
        topWrapper.add(Box.createVerticalStrut(10));
        topWrapper.add(statsPanel);

        JPanel centerCard = new JPanel();
        centerCard.setBackground(CARD_BG);
        centerCard.setLayout(new BoxLayout(centerCard, BoxLayout.Y_AXIS));
        centerCard.setBorder(new EmptyBorder(25, 25, 25, 25));

        tilePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        tilePanel.setOpaque(false);

        drawTiles();

        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false);

        JLabel enterLabel = new JLabel("Enter Word:");
        enterLabel.setFont(UI_FONT);

        inputField = new JTextField(10);
        inputField.setFont(UI_FONT);
        // Fix: pressing Enter in the text field now submits the word,
        // same as clicking SUBMIT.
        inputField.addActionListener(this);

        submitBtn = createButton("SUBMIT", BTN_GREEN);
        giveUpBtn = createButton("GIVE UP", BTN_BLUE);

        inputPanel.add(enterLabel);
        inputPanel.add(inputField);
        inputPanel.add(submitBtn);
        inputPanel.add(giveUpBtn);

        centerCard.add(tilePanel);
        centerCard.add(Box.createVerticalStrut(15));
        centerCard.add(inputPanel);

        JLabel foundTitle = new JLabel("WORDS FOUND");
        foundTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));

        foundWordsArea = new JTextArea();
        foundWordsArea.setEditable(false);
        foundWordsArea.setFont(UI_FONT);

        JScrollPane scrollPane = new JScrollPane(foundWordsArea);
        scrollPane.setBorder(null);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(CARD_BG);
        rightPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        rightPanel.add(foundTitle, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        nextBtn = createButton("NEXT", BTN_GRAY);
        quitBtn = createButton("QUIT", BTN_RED);

        leftPanel.add(nextBtn);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(quitBtn);

        root.add(topWrapper, BorderLayout.NORTH);
        root.add(centerCard, BorderLayout.CENTER);
        root.add(rightPanel, BorderLayout.EAST);
        root.add(leftPanel, BorderLayout.WEST);

        setVisible(true);
    }

    private JLabel createStat(String text) {
        JLabel lbl = new JLabel(text, JLabel.CENTER);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(UI_FONT);
        return lbl;
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(UI_FONT);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 18, 10, 18));
        btn.addActionListener(this);
        return btn;
    }

    private void drawTiles() {
        tilePanel.removeAll();

        for (char c : rounds[round].toCharArray()) {
            JLabel tile = new JLabel(String.valueOf(c), JLabel.CENTER);
            tile.setPreferredSize(new Dimension(60, 60));
            tile.setOpaque(true);
            tile.setBackground(TILE_BG);
            tile.setFont(new Font("Segoe UI", Font.BOLD, 28));
            tile.setBorder(new EmptyBorder(10, 10, 10, 10));
            tilePanel.add(tile);
        }

        tilePanel.revalidate();
        tilePanel.repaint();
    }

    private void loadDictionary() {
        // Fix: look for dictionary.txt next to the running .jar/.class files
        // first, falling back to the current working directory. This avoids
        // "file not found" errors when the app is launched from a different
        // working directory (e.g. double-clicking the jar, or running from
        // an IDE with a different default folder).
        java.io.File candidate = null;

        try {
            java.io.File codeLocation = new java.io.File(
                    WordBuilderGame1.class.getProtectionDomain()
                            .getCodeSource().getLocation().toURI());

            java.io.File dir = codeLocation.isFile()
                    ? codeLocation.getParentFile()
                    : codeLocation;

            java.io.File sibling = new java.io.File(dir, "dictionary.txt");

            if (sibling.exists()) {
                candidate = sibling;
            }
        } catch (Exception ignored) {
            // Fall through to the working-directory check below.
        }

        if (candidate == null) {
            java.io.File cwdFile = new java.io.File("dictionary.txt");
            if (cwdFile.exists()) {
                candidate = cwdFile;
            }
        }

        if (candidate == null) {
            JOptionPane.showMessageDialog(this,
                    "dictionary.txt not found! Make sure it is in the same "
                            + "folder as the program.");
            return;
        }

        try (BufferedReader br = new BufferedReader(
                new FileReader(candidate))) {

            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    dictionary.add(line.toLowerCase());
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Could not read dictionary.txt: " + e.getMessage());
        }
    }

    private void prepareRound() {

        possibleWords.clear();
        foundWords.clear();
        roundOver = false;

        for (String word : dictionary) {
            if (canForm(word, rounds[round])) {
                possibleWords.add(word);
            }
        }
    }

    private boolean canForm(String word, String letters) {

        ArrayList<Character> temp = new ArrayList<>();

        for (char c : letters.toCharArray()) {
            temp.add(c);
        }

        for (char c : word.toUpperCase().toCharArray()) {
            if (!temp.remove((Character) c)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == submitBtn || e.getSource() == inputField) {

            if (roundOver) {
                JOptionPane.showMessageDialog(this,
                        "Round is over. Click NEXT to continue.");
                inputField.setText("");
                return;
            }

            String word = inputField.getText().trim().toLowerCase();

            if (!word.isEmpty() && possibleWords.contains(word) &&
                    foundWords.add(word)) {

                foundWordsArea.append(word + "\n");

                score += word.length() * 10;

                scoreLabel.setText("Score: " + score);

            } else {

                JOptionPane.showMessageDialog(this,
                        "Invalid or Already Used!");
            }

            inputField.setText("");
            inputField.requestFocusInWindow();
        }

        if (e.getSource() == giveUpBtn) {

            // Fix: Give Up now locks the round so no more words can be
            // submitted until NEXT is pressed, instead of letting the
            // player keep guessing for free after seeing the answers.
            roundOver = true;

            StringBuilder sb =
                    new StringBuilder("Possible words: ");

            int i = 0;

            for (String w : possibleWords) {

                sb.append(w);

                if (++i < possibleWords.size()) {
                    sb.append(", ");
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    sb.toString(),
                    "Give Up",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

        if (e.getSource() == nextBtn) {

            if (round < rounds.length - 1) {

                round++;

                roundLabel.setText("Round: " + (round + 1));

                prepareRound();
                drawTiles();

                foundWordsArea.setText("");

                totalLabel.setText(
                        "Total Words: " + possibleWords.size());

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "\uD83C\uDFC6 Game Completed!");
            }
        }

        if (e.getSource() == quitBtn) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WordBuilderGame1::new);
    }
}

class GradientPanel extends JPanel {

    private final Color c1;
    private final Color c2;

    GradientPanel(Color a, Color b) {
        c1 = a;
        c2 = b;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setPaint(
                new GradientPaint(
                        0, 0, c1,
                        0, getHeight(), c2));

        g2.fillRect(
                0,
                0,
                getWidth(),
                getHeight());
    }
}
