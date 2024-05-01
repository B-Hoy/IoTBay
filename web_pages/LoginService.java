import java.util.HashMap;
import java.util.Map;

public class LoginService {
    // Simulated database of usernames and passwords
    private static final Map<String, String> userDatabase = new HashMap<>();

    static {
        // Add some hardcoded usernames and passwords (replace with your actual database logic)
        userDatabase.put("user1", "password1");
        userDatabase.put("user2", "password2");
        userDatabase.put("user3", "password3");
    }

    // Method to authenticate user credentials
    public boolean authenticate(String username, String password) {
        // Retrieve the password associated with the username from the database
        String storedPassword = userDatabase.get(username);

        // Check if the username exists in the database and if the provided password matches the stored password
        return storedPassword != null && storedPassword.equals(password);
    }

    // Example method to demonstrate usage
    public static void main(String[] args) {
        // Example usage
        LoginService loginService = new LoginService();

        // Test authentication
        String username = "user1";
        String password = "password1";
        if (loginService.authenticate(username, password)) {
            System.out.println("Login successful for user: " + username);
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }
}
