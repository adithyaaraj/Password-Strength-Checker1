import java.util.*;
import java.util.regex.*;

public class PasswordChecker {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Password Strength Analyzer ===");
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        int score = 0;
        List<String> suggestions = new ArrayList<>();

        // Length check
        if (password.length() >= 8) {
            score++;
        } else {
            suggestions.add("Increase length to at least 8 characters");
        }

        // Upper + lower case
        if (password.matches(".*[A-Z].*") && password.matches(".*[a-z].*")) {
            score++;
        } else {
            suggestions.add("Add both uppercase and lowercase letters");
        }

        // Numbers
        if (password.matches(".*[0-9].*")) {
            score++;
        } else {
            suggestions.add("Include at least one number");
        }

        // Special characters
        if (password.matches(".*[@$!%*?&#^()_+\\-=\\[\\]{};:'\",.<>/?|`~].*")) {
            score++;
        } else {
            suggestions.add("Add a special character (@, #, etc.)");
        }

        // Common password check
        List<String> common = Arrays.asList("123456", "password", "qwerty", "abc123");
        boolean isCommon = common.contains(password.toLowerCase());

        // Strength level
        String level;
        if (isCommon) {
            level = "Very Weak ❌ (Common Password)";
        } else if (score == 4) {
            level = "Strong 💪";
        } else if (score == 3) {
            level = "Medium ⚙️";
        } else {
            level = "Weak ⚠️";
        }

        // Output
        System.out.println("\nScore: " + score + "/4");
        System.out.println("Strength: " + level);

        if (!suggestions.isEmpty()) {
            System.out.println("\nSuggestions:");
            for (String s : suggestions) {
                System.out.println("- " + s);
            }
        }

        sc.close();
    }
}
