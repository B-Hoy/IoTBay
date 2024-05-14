import java.util.HashMap;
import java.util.Map;

import uts.iotbay.Database;
import uts.iotbay.User;

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

    public boolean authenticate(String email, String password) {
        // Retrieve the user from the database based on the provided email
        Database db = new Database();
        User user = db.get_user(email);
    
        // Check if the user exists and if the provided password matches the stored password
        return user != null && user.get_password().equals(password);
    }
}