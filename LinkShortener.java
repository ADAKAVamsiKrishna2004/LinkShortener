import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class LinkShortenerGUI extends JFrame {
    private JTextField longUrlField;
    private JTextField shortUrlField;

    private Map<String, String> shortToLongMap;
    private Map<String, String> longToShortMap;

    public LinkShortenerGUI() {
        super("Link Shortener");

        shortToLongMap = new HashMap<>();
        longToShortMap = new HashMap();

        longUrlField = new JTextField(20);
        shortUrlField = new JTextField(20);
        JButton shortenButton = new JButton("Shorten URL");
        JButton expandButton = new JButton("Expand URL");

        shortenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String longUrl = longUrlField.getText();
                String shortUrl = shortenUrl(longUrl);
                shortUrlField.setText(shortUrl);
            }
        });

        expandButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String shortUrl = shortUrlField.getText();
                String expandedUrl = expandUrl(shortUrl);
                JOptionPane.showMessageDialog(LinkShortenerGUI.this, "Expanded URL: " + expandedUrl);
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Long URL: "));
        panel.add(longUrlField);
        panel.add(new JLabel("Short URL: "));
        panel.add(shortUrlField);
        panel.add(shortenButton);
        panel.add(expandButton);

        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private String generateShortUrl(String longUrl) {
        int hashCode = longUrl.hashCode();
        return Integer.toHexString(hashCode);
    }

    private String shortenUrl(String longUrl) {
        if (longToShortMap.containsKey(longUrl)) {
            return longToShortMap.get(longUrl);
        }

        String shortUrl = generateShortUrl(longUrl);

        while (shortToLongMap.containsKey(shortUrl)) {
            shortUrl = generateShortUrl(longUrl + System.currentTimeMillis());
        }

        shortToLongMap.put(shortUrl, longUrl);
        longToShortMap.put(longUrl, shortUrl);

        return shortUrl;
    }

    private String expandUrl(String shortUrl) {
        if (!shortToLongMap.containsKey(shortUrl)) {
            return "Invalid short URL";
        }

        return shortToLongMap.get(shortUrl);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LinkShortenerGUI();
            }
        });
    }
}
