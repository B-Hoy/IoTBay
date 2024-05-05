package uts.iotbay;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

public class Database{
	Connection conn = null;
	public Database(){
		File temp_file = null;
		try {
			Class.forName("org.sqlite.JDBC"); // Need this in case Glassfish doesn't load it automatically
		} catch (ClassNotFoundException e){
			System.out.println("ERROR: " + e.getMessage());
		}
		try{
			temp_file = File.createTempFile("sql", ".db");
			temp_file.deleteOnExit();
			conn = DriverManager.getConnection("jdbc:sqlite:" + temp_file.getAbsolutePath()); // makes a temp file wherever so it doesn't break on someone else's system
			// Make table for users
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(5);
			stmt.executeUpdate("DROP TABLE IF EXISTS Users");
			stmt.executeUpdate("CREATE TABLE Users (email TEXT NOT NULL PRIMARY KEY, id INTEGER NOT NULL, first_name TEXT NOT NULL, last_name TEXT NOT NULL, password TEXT NOT NULL, reg_date TEXT NOT NULL, is_admin BOOL NOT NULL, card_num INTEGER, card_exp INTEGER, phone_num TEXT)");
			stmt.executeUpdate("INSERT INTO Users VALUES('testacc1@uts.edu.au', 12345, 'Testing', 'User', 'securepassword', DATETIME('now', '+10 hours'), 0, NULL, NULL, NULL)");
			stmt.executeUpdate("INSERT INTO Users VALUES('testacc2@uts.edu.au', 67890, 'Another', 'Tester', 'no', DATETIME('now', '+10 hours'), 1, '1234567890123456', '01/99', '+61400000000')");

			//stmt.executeUpdate("DROP TABLE IF EXISTS Products");
			//stmt.executeUpdate("CREATE TABLE Users (email TEXT NOT NULL PRIMARY KEY, first_name TEXT NOT NULL, last_name TEXT NOT NULL, password TEXT NOT NULL, reg_date TEXT NOT NULL, is_admin BOOL NOT NULL, card_num INTEGER, card_exp INTEGER, phone_num TEXT)");
			//stmt.executeUpdate("INSERT INTO Users VALUES('testacc1@uts.edu.au', 'Testing', 'User', 'securepassword', DATETIME('now'), 0, NULL, NULL, NULL)");
			//stmt.executeUpdate("INSERT INTO Users VALUES('testacc2@uts.edu.au', 'Another', 'Tester', 'no', DATETIME('now'), 1, '1234567890123456', '01/99', '+61400000000')");
        } catch (Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }
	}
	public User get_user(String email){
		User db_user = new User();
		try{
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE email = (?)");
			stmt.setString(1, email);
			ResultSet results = stmt.executeQuery();
			while (results.next()){
				db_user.set_email(results.getString("email"));
				db_user.set_id(results.getInt("id"));
				db_user.set_first_name(results.getString("first_name"));
				db_user.set_last_name(results.getString("last_name"));
				db_user.set_password(results.getString("password"));
				db_user.set_reg_date(results.getString("reg_date"));
				db_user.set_is_admin(results.getBoolean("is_admin"));
				db_user.set_card_num(results.getString("card_num"));
				db_user.set_card_exp(results.getString("card_exp"));
				db_user.set_phone_num(results.getString("phone_num"));
			}
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}
		return db_user;
	}
	public User[] get_all_users(){
		ArrayList<User> user_arr = new ArrayList<User>();
		try{
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(5);
			ResultSet results = stmt.executeQuery("SELECT * FROM Users");
			while (results.next()){
				user_arr.add(new User(results.getString("email"), results.getInt("id"), results.getString("first_name"), results.getString("last_name"), results.getString("password"), results.getString("reg_date"), results.getBoolean("is_admin"), results.getString("card_num"), results.getString("card_exp"), results.getString("phone_num")));
			}
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}

		return user_arr.toArray(new User[]{});
	}
	int create_id(){
		int random = 0;
		boolean valid = false;
		PreparedStatement stmt;
		ResultSet results;
		while (!valid){
			try{
				random = ThreadLocalRandom.current().nextInt(10000, 99999 + 1);
				stmt = conn.prepareStatement("SELECT * FROM Users WHERE id = (?)");
				stmt.setInt(1, random);
				results = stmt.executeQuery();
				if (results.next()){
					continue;
				}else{
					valid = true;
				}
			}catch (SQLException e){
				System.out.println("ERROR: " + e.getMessage());
			}
		}
		return random;
	}
	public boolean create_user(String email, String first_name, String last_name, String password, boolean is_admin, String card_num, String card_exp, String phone_num){
		PreparedStatement stmt;
		ResultSet results;
		// checks to see if string is empty, subtitutes as null (avoids people getting a username or password that is just an empty string)
		if (email != null && email.trim().isEmpty()){
			email = null;
		}
		if (first_name != null && first_name.trim().isEmpty()){
			first_name = null;
		}
		if (last_name != null && last_name.trim().isEmpty()){
			last_name = null;
		}
		if (password != null && password.trim().isEmpty()){
			password = null;
		}
		if (card_num != null && card_num.trim().isEmpty()){
			card_num = null;
		}
		if (card_exp != null && card_exp.trim().isEmpty()){
			card_exp = null;
		}
		if (phone_num != null && phone_num.trim().isEmpty()){
			phone_num = null;
		}
		try{
			stmt = conn.prepareStatement("SELECT * FROM Users WHERE email = (?)");
			stmt.setString(1, email);
			results = stmt.executeQuery();
				if (results.next()){ // email already exists in db
					return false;
				}
			stmt = conn.prepareStatement("INSERT INTO Users VALUES((?), (?), (?), (?), (?), DATETIME('now', '+10 hours'), (?), (?), (?), (?))");
			stmt.setString(1, email);
			stmt.setInt(2, create_id());
			stmt.setString(3, first_name);
			stmt.setString(4, last_name);
			stmt.setString(5, password);
			stmt.setBoolean(6, is_admin);
			stmt.setString(7, card_num);
			stmt.setString(8, card_exp);
			stmt.setString(9, phone_num);
			stmt.executeUpdate();
			return true;
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
			return false;
		}
	}
	public void update_user(String email, String first_name, String last_name, String password, String card_num, String card_exp, String phone_num){
		if (email != null && email.trim().isEmpty()){
			email = null;
		}
		if (first_name != null && first_name.trim().isEmpty()){
			first_name = null;
		}
		if (last_name != null && last_name.trim().isEmpty()){
			last_name = null;
		}
		if (password != null && password.trim().isEmpty()){
			password = null;
		}
		if (card_num != null && card_num.trim().isEmpty()){
			card_num = null;
		}
		if (card_exp != null && card_exp.trim().isEmpty()){
			card_exp = null;
		}
		if (phone_num != null && phone_num.trim().isEmpty()){
			phone_num = null;
		}
		try{
			PreparedStatement stmt = conn.prepareStatement("UPDATE Users SET first_name = (?), last_name = (?), password = (?), card_num = (?), card_exp = (?), phone_num = (?) WHERE email = (?)");
			stmt.setString(1, first_name);
			stmt.setString(2, last_name);
			stmt.setString(3, password);
			stmt.setString(4, card_num);
			stmt.setString(5, card_exp);
			stmt.setString(6, phone_num);
			stmt.setString(7, email);
			stmt.executeUpdate();
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	public void delete_user(String email){
		try{
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM Users WHERE email = (?)");
			stmt.setString(1, email);
			stmt.executeUpdate();
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	public void disconnect(){
		if (conn != null){
			try{
				conn.close();
			} catch (SQLException e){
				System.out.println(e.getMessage());
			}
		}
	}
}
