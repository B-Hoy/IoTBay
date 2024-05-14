import java.util.HashMap;
import java.util.Map;

public class LoginService {
    // Simulated database of usernames and passwords
    private static final Map<String, String> userDatabase = new HashMap<>();

    // Method to register new users
    public boolean register(String username, String password) {
        if (userDatabase.containsKey(username)) {
            // User already exists
            return false;
        } else {
            // Add the new user to the database
            userDatabase.put(username, password);
            return true;
        }
    }

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
}
