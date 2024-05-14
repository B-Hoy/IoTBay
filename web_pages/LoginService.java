import uts.iotbay.Database;
import uts.iotbay.User;

public class LoginService {
    public boolean authenticate(String email, String password) {
        // Retrieve the user from the database based on the provided email
        Database db = new Database();
        User user = db.get_user(email);
    
        // Check if the user exists and if the provided password matches the stored password
        return user != null && user.get_password().equals(password);
    }
}
