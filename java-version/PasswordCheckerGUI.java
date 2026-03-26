import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class PasswordCheckerGUI {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Password Strength Analyzer");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Enter Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton button = new JButton("Check Strength");
        JLabel resultLabel = new JLabel(" ");
        JLabel crackLabel = new JLabel(" ");
        JTextArea suggestionsArea = new JTextArea(5, 30);
        JProgressBar bar = new JProgressBar(0, 4);
        bar.setValue(0);
        bar.setStringPainted(true);

        suggestionsArea.setEditable(false);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                String password = new String(passwordField.getPassword());

                int score = 0;
                List<String> suggestions = new ArrayList<>();

                if (password.length() >= 8) score++;
                else suggestions.add("Increase length to at least 8");

                if (password.matches(".*[A-Z].*") && password.matches(".*[a-z].*")) score++;
                else suggestions.add("Add uppercase & lowercase letters");

                if (password.matches(".*[0-9].*")) score++;
                else suggestions.add("Include a number");

                if (password.matches(".*[@$!%*?&#^()_+\\-=\\[\\]{};:'\",.<>/?|`~].*")) score++;
                else suggestions.add("Add a special character");
                bar.setValue(score);

if (score == 4) {
    bar.setForeground(Color.GREEN);
} else if (score == 3) {
    bar.setForeground(Color.ORANGE);
} else {
    bar.setForeground(Color.RED);
}
int pool = 0;

if (password.matches(".*[a-z].*")) pool += 26;
if (password.matches(".*[A-Z].*")) pool += 26;
if (password.matches(".*[0-9].*")) pool += 10;
if (password.matches(".*[@$!%*?&#^()_+\\-=\\[\\]{};:'\",.<>/?|`~].*")) pool += 32;

String crackTime;

if (pool == 0) {
    crackTime = "Instant";
} else {
    double entropy = password.length() * (Math.log(pool) / Math.log(2));
    double guessesPerSecond = 1e9;
    double seconds = Math.pow(2, entropy) / guessesPerSecond;

    if (seconds > 60*60*24*365*1000) {
        crackTime = ">1000 years";
    } else if (seconds >= 60*60*24*365) {
        crackTime = String.format("%.2f years", seconds / (60*60*24*365));
    } else if (seconds >= 60*60*24) {
        crackTime = String.format("%.2f days", seconds / (60*60*24));
    } else if (seconds >= 60*60) {
        crackTime = String.format("%.2f hours", seconds / (60*60));
    } else if (seconds >= 60) {
        crackTime = String.format("%.2f minutes", seconds / 60);
    } else {
        crackTime = String.format("%.2f seconds", seconds);
    }
}

                List<String> common = Arrays.asList("123456", "password", "qwerty", "abc123");
                boolean isCommon = common.contains(password.toLowerCase());

                String level;
                if (isCommon) level = "Very Weak ❌";
                else if (score == 4) level = "Strong 💪";
                else if (score == 3) level = "Medium ⚙️";
                else level = "Weak ⚠️";

                resultLabel.setText("Strength: " + level);
                crackLabel.setText("Crack Time: " + crackTime);

                StringBuilder sb = new StringBuilder();
                for (String s : suggestions) {
                    sb.append("- ").append(s).append("\n");
                }

                suggestionsArea.setText(sb.toString());
            }
        });

        frame.add(label);
        frame.add(passwordField);
        frame.add(button);
        frame.add(resultLabel);
        frame.add(crackLabel);
        frame.add(suggestionsArea);
        frame.add(bar);
        frame.setVisible(true);
       
    }
}
