public class LoginService {
    public boolean authenticate(Database db, String email, String password) {
        // Retrieve the user from the database based on the provided email
        User user = db.get_user(email);
    
        // Check if the user exists and if the provided password matches the stored password
        return user != null && user.get_password().equals(password);
    }
}
