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
			stmt.executeUpdate("CREATE TABLE Users (email TEXT NOT NULL PRIMARY KEY, id INTEGER NOT NULL, first_name TEXT NOT NULL, last_name TEXT NOT NULL, password TEXT NOT NULL, reg_date TEXT NOT NULL, is_staff BOOL NOT NULL, card_num INTEGER, card_exp INTEGER, phone_num TEXT)");
			stmt.executeUpdate("INSERT INTO Users VALUES('testacc1@uts.edu.au', 10001, 'Testing', 'User', 'securepassword', DATETIME('now', '+10 hours'), 0, NULL, NULL, NULL)");
			stmt.executeUpdate("INSERT INTO Users VALUES('testacc2@uts.edu.au', 10002, 'Another', 'Tester', 'no', DATETIME('now', '+10 hours'), 1, NULL, NULL, NULL)");
			stmt.executeUpdate("INSERT INTO Users VALUES('john@gmail.com', 10003, 'Keith', 'Burke', 'password1', DATETIME('now', '+10 hours'), 0, NULL, NULL, NULL)");
			stmt.executeUpdate("INSERT INTO Users VALUES('adipiscing@yahoo.ca', 10004, 'Neil', 'Ortez', '7gZLr35FXpH5R4', DATETIME('now', '+10 hours'), 0, '378282246310005', '05/24', '0671536446')");
			stmt.executeUpdate("INSERT INTO Users VALUES('nullam@outlook.edu', 10005, 'Jean', 'Rivers', 'GD37tzxB366M5Q', DATETIME('now', '+10 hours'), 0, '371449635398431', '06/24', '0239559258')");
			stmt.executeUpdate("INSERT INTO Users VALUES('sodales@icloud.edu', 10006, 'Hanna', 'Goode', 'tU5hH6cvQiwN96', DATETIME('now', '+10 hours'), 0, '378734493671000', '07/24', '0144614283')");
			stmt.executeUpdate("INSERT INTO Users VALUES('vitae@protonmail.ca', 10007, 'Delilah', 'Clements', 'pass123', DATETIME('now', '+10 hours'), 0, '374245455400126', '08/24', '0788533753')");
			stmt.executeUpdate("INSERT INTO Users VALUES('henry@hotmail.com', 10008, 'Michael', 'Barron', 'PvZi5Z5scfaxKG', DATETIME('now', '+10 hours'), 0, NULL, NULL, '0735639413')");
			stmt.executeUpdate("INSERT INTO Users VALUES('em.ut@outlook.net', 10009, 'Ronan', 'Murphy', 'CKBg5qhD6b2Vcg', DATETIME('now', '+10 hours'), 0, NULL, NULL, '0133775695')");
			stmt.executeUpdate("INSERT INTO Users VALUES('diam@hotmail.co.uk', 10010, 'Charde', 'Hickman', 'oPtK726d7qJk7P', DATETIME('now', '+10 hours'), 0, '6011000991300009', '09/24', '0425244114')");
			stmt.executeUpdate("INSERT INTO Users VALUES('arcu@hotmail.ca', 10011, 'Petra', 'Callahan', 'pPtdkHAA96SPL5', DATETIME('now', '+10 hours'), 1, NULL, NULL, '0136599779')");
			stmt.executeUpdate("INSERT INTO Users VALUES('archie@icloud.com', 10012, 'Archibald', 'Belle', '8pYLM2DrPcvSz4', DATETIME('now', '+10 hours'), 0, '5425233430109903', '10/24', NULL)");
			stmt.executeUpdate("INSERT INTO Users VALUES('quam@yahoo.ca', 10013, 'Holmes', 'Gamb', '5gCxaBxxS5Nj55', DATETIME('now', '+10 hours'), 0, '4263982640269299', '11/24', NULL)");
			stmt.executeUpdate("INSERT INTO Users VALUES('mia@outlook.ca', 10014, 'Bruce', 'Ingram', 'd5zC2ofK7QLuMo', DATETIME('now', '+10 hours'), 0, NULL, NULL, NULL)");
			stmt.executeUpdate("INSERT INTO Users VALUES('lacus@google.com', 10015, 'Rama', 'Santiago', 'SmTjptUPQwmtF6', DATETIME('now', '+10 hours'), 0, NULL, NULL, '0272575884')");
			stmt.executeUpdate("INSERT INTO Users VALUES('quis@icloud.net', 10016, 'Tyler', 'Dickson', 'idzeCZwcpH7Sta', DATETIME('now', '+10 hours'), 0, NULL, NULL, NULL)");
			stmt.executeUpdate("INSERT INTO Users VALUES('vel@icloud.net', 10017, 'Aimee', 'Spencer', '82zEU4Wxp5Nkzi', DATETIME('now', '+10 hours'), 0, '4263982640269299', '12/24', NULL)");
			stmt.executeUpdate("INSERT INTO Users VALUES('jack@gmail.com', 10018, 'Jack', 'Ryan', 'GpTB4DJRrkHqt6', DATETIME('now', '+10 hours'), 0, NULL, NULL, NULL)");
			stmt.executeUpdate("INSERT INTO Users VALUES('jimmy@gmail.com', 10019, 'Jimmy', 'Ryan', 'jVqWSU9SiexYM6', DATETIME('now', '+10 hours'), 0, NULL, NULL, NULL)");
			stmt.executeUpdate("INSERT INTO Users VALUES('jackson@gmail.com', 10020, 'Jackson', 'Ryan', 'MGxWjn7efiJGk6', DATETIME('now', '+10 hours'), 1, NULL, NULL, '0499733483')");

			// This'll be used when the login/logout actions are set up
			stmt.executeUpdate("DROP TABLE IF EXISTS User_Logins");
			stmt.executeUpdate("CREATE TABLE User_Logins (id INTEGER NOT NULL PRIMARY KEY, email TEXT NOT NULL, login_date TEXT NOT NULL, logout_date TEXT)");
			
			stmt.executeUpdate("DROP TABLE IF EXISTS Products");
			// The image_location has an implicit "web_pages/images/" put at the start of it
			// quantity = stock on hand
			stmt.executeUpdate("CREATE TABLE Products (id INTEGER NOT NULL PRIMARY KEY, name TEXT NOT NULL, price REAL NOT NULL, rating INTEGER NOT NULL, brand TEXT NOT NULL, quantity INTEGER NOT NULL, image_location TEXT)");
			// More test data go here
			stmt.executeUpdate("INSERT INTO Products VALUES(10001, 'IoT-Enabled Smart Light Bulb', 15.99, 4, 'Connect SmartHome', 5000, 'smart_light.jpg')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10002, 'WiFi Smart Camera', 139.49, 5, 'Arlo', 2500, 'smart_camera.jpg')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10003, 'Smart Thermostat', 349.00, 3, 'Ring', 1800, 'ring_camera.png')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10004, 'Echo Pro', 389.31, 3, 'Amazon', 25000, 'amazon_echo.jpg')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10005, 'Security IoT Home System', 197.52, 1, 'Simplisafe', 66940, 'simplisafe_system.png')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10006, 'Magik Toothbrush', 293.39, 5, 'Colgate', 40900, 'colgate_magik.jpg')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10007, 'U-Bolt Pro WiFi', 247.97, 5, 'Ultraloq', 92250, 'ultraloq-bolt.webp')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10008, 'Smart Room Controller', 410.48, 4, 'Vivint', 9370, 'vivint_controller.png')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10009, 'Smart Speaker', 399.75, 2, 'Sonos', 85400, 'sonos_speaker.jpg')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10010, 'Nest Hub Tablet', 275.86, 3, 'Google', 12370, 'google_nest.jpg')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10011, 'Energy Monitor Wi-Fi Plug', 263.34, 4, 'TP-Link', 2660, 'tplink_plug.webp')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10012, 'Android-Enabled Oven', 111.75, 2, 'Anova', 86700, 'anova_oven.png')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10013, 'Roomba Smart Vaccuum', 424.98, 2, 'iRobot', 84180, 'irobot_vacuum.jpg')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10014, 'Inverter Smart Air Conditioner', 263.78, 5, 'Ring', 3640, 'mitsubishi_ac.jpg')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10015, 'Nest Smart Speaker', 278.87, 2, 'Google', 13990, 'google_home.png')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10016, 'Wi-Fi Smart Lock', 339.80, 1, 'August', 12860, 'august_lock.png')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10017, 'WeMo Smart Light Switch', 197.44, 1, 'Belkin', 64460, 'belkin_light.png')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10018, 'Nest Wireless Smoke Alarm', 171.11, 3, 'Google', 43760, 'google_smokealarm.jpg')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10019, 'HomePod Mini', 286.72, 4, 'Apple', 10010, 'apple_homepodmini.webp')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10020, 'HomePod', 381.23, 5, 'Apple', 52340, 'apple_homepod.webp')");
			// Product Orders
			stmt.executeUpdate("DROP TABLE IF EXISTS Orders");
			// Items is set up as comma-seperated, with each entry going <item id>|<amount
			// Finalised just means whether or not the user has "signed off" on it, with true meaning it can't be edited
			stmt.executeUpdate("CREATE TABLE Orders (id INTEGER NOT NULL PRIMARY KEY, user_email TEXT, items TEXT, finalised INTEGER, payment_id INTEGER, date_created TEXT NOT NULL)");
			stmt.executeUpdate("INSERT INTO Orders VALUES (10001, 'testacc1@uts.edu.au', '10001|5,10002|50,10003|500', 0, 0, DATETIME('now', '+10 hours'))");
			stmt.executeUpdate("INSERT INTO Orders VALUES (10002, 'testacc2@uts.edu.au', '10001|2000', 1, 0, DATETIME('now', '+10 hours'))");
			stmt.executeUpdate("INSERT INTO Orders VALUES (10003, 'john@gmail.com', '10014|227,10011|224,10012|128,10019|171,10006|227,10016|65,10018|123', 0, 0, DATETIME('now', '+10 hours'))");
			stmt.executeUpdate("INSERT INTO Orders VALUES (10004, 'adipiscing@yahoo.ca', '10003|94,10007|15,10017|169,10020|31,10005|189,10010|47,10019|88,10012|186,10009|229,10008|116,10006|175', 0, 0, DATETIME('now', '+10 hours'))");
			stmt.executeUpdate("INSERT INTO Orders VALUES (10005, 'nullam@outlook.edu', '10007|183,10016|97,10017|249,10003|151,10010|200,10020|218,10001|14,10012|244,10009|193,10002|52,10008|147,10014|178,10011|56,10018|107,10005|105', 0, 0, DATETIME('now', '+10 hours'))");
			stmt.executeUpdate("INSERT INTO Orders VALUES (10006, 'sodales@icloud.edu', '10017|235,10009|87,10019|162,10014|33,10012|169,10011|81,10006|96,10007|13', 0, 0, DATETIME('now', '+10 hours'))");
			stmt.executeUpdate("INSERT INTO Orders VALUES (10007, 'vitae@protonmail.ca', '10005|142,10014|14,10007|85,10015|105,10001|116,10012|84,10003|182,10020|215,10011|220', 0, 0, DATETIME('now', '+10 hours'))");
			stmt.executeUpdate("INSERT INTO Orders VALUES (10008, 'henry@hotmail.com', '10020|176,10012|154,10014|92,10015|69,10001|238,10017|141,10018|213,10005|136', 0, 0, DATETIME('now', '+10 hours'))");
			stmt.executeUpdate("INSERT INTO Orders VALUES (10009, 'em.ut@outlook.net', '10009|74,10003|149,10005|149,10014|228', 0, 0, DATETIME('now', '+10 hours'))");
			stmt.executeUpdate("INSERT INTO Orders VALUES (10010, 'diam@hotmail.co.uk', '10020|171,10005|16,10010|135,10014|160,10003|63,10018|131,10011|203,10007|236,10008|228,10001|120', 0, 0, DATETIME('now', '+10 hours'))");
			stmt.executeUpdate("INSERT INTO Orders VALUES (10011, 'arcu@hotmail.ca', '10009|105,10015|206,10018|242,10006|15,10003|247,10020|12,10008|132,10016|34,10013|201,10004|189,10012|14,10017|187,10002|58,10007|169,10001|12,10011|235,10005|19,10010|191,10019|72', 0, 0, DATETIME('now', '+10 hours'))");
			stmt.executeUpdate("INSERT INTO Orders VALUES (10012, 'archie@icloud.com', '10008|199,10006|131,10018|240,10020|28,10017|33,10013|35,10003|47,10004|162', 0, 0, DATETIME('now', '+10 hours'))");
			stmt.executeUpdate("INSERT INTO Orders VALUES (10013, 'quam@yahoo.ca', '10020|54,10009|223,10007|45,10019|166,10003|52,10011|30,10002|63,10005|124,10004|248,10014|101,10006|95,10017|213,10001|57,10010|153,10016|82', 0, 0, DATETIME('now', '+10 hours'))");
			stmt.executeUpdate("INSERT INTO Orders VALUES (10014, 'mia@outlook.ca', '10009|131,10020|55,10017|167,10002|114,10005|21,10007|65,10015|159,10016|65', 0, 0, DATETIME('now', '+10 hours'))");
			stmt.executeUpdate("INSERT INTO Orders VALUES (10015, 'lacus@google.com', '10014|72,10016|75,10004|206,10009|92,10007|182,10019|181,10001|246', 0, 0, DATETIME('now', '+10 hours'))");
			stmt.executeUpdate("INSERT INTO Orders VALUES (10016, 'quis@icloud.net', '10005|105,10013|21,10007|192,10018|70,10004|210,10008|192,10014|135,10001|167,10003|234,10010|108,10016|45,10017|117,10011|134,10009|70,10019|152,10006|104,10020|67', 0, 0, DATETIME('now', '+10 hours'))");
			stmt.executeUpdate("INSERT INTO Orders VALUES (10017, 'vel@icloud.net', '10011|73,10018|37,10012|158,10015|24,10010|124,10005|100', 0, 0, DATETIME('now', '+10 hours'))");
			stmt.executeUpdate("INSERT INTO Orders VALUES (10018, 'jack@gmail.com', '10001|153,10007|184,10020|11', 0, 0, DATETIME('now', '+10 hours'))");
			stmt.executeUpdate("INSERT INTO Orders VALUES (10019, 'jimmy@gmail.com', '10009|181,10010|142,10006|95,10005|226', 0, 0, DATETIME('now', '+10 hours'))");
			stmt.executeUpdate("INSERT INTO Orders VALUES (10020, 'jackson@gmail.com', '10018|87,10007|64,10013|165,10015|205,10016|109,10011|132,10002|120,10006|81,10012|157,10005|161,10010|140,10017|116,10019|117,10009|113,10003|118,10008|98', 0, 0, DATETIME('now', '+10 hours'))");

			stmt.executeUpdate("DROP TABLE IF EXISTS Payments");
			stmt.executeUpdate("CREATE TABLE Payments (id INTEGER NOT NULL PRIMARY KEY, user_email TEXT, amount REAL NOT NULL, card_num TEXT NOT NULL, card_exp TEXT NOT NULL, cart_cvc INTEGER NOT NULL)");
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
				db_user.set_is_staff(results.getBoolean("is_staff"));
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
				user_arr.add(new User(results.getString("email"), results.getInt("id"), results.getString("first_name"), results.getString("last_name"), results.getString("password"), results.getString("reg_date"), results.getBoolean("is_staff"), results.getString("card_num"), results.getString("card_exp"), results.getString("phone_num")));
			}
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}

		return user_arr.toArray(new User[]{});
	}
	int create_id(String table){
		int random = 0;
		boolean valid = false;
		ResultSet results;
		while (!valid){
			try{
				PreparedStatement stmt;
				random = ThreadLocalRandom.current().nextInt(10000, 99999 + 1);
				stmt = conn.prepareStatement("SELECT * FROM " + table + " WHERE id = (?)");
				stmt.setInt(1, random);
				results = stmt.executeQuery();
				if (results.next()){
					continue;
				}else{
					valid = true;
				}
			}catch (SQLException e){
				System.out.println("ERROR: " + e.getMessage());
				random = -1;
				valid = true;
			}
		}
		return random;
	}
	public boolean check_email_acc_exists(String email){
		try{
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE email = (?)");
			stmt.setString(1, email);
			ResultSet results = stmt.executeQuery();
			if (results.next()){ // email already exists in db
				return true;
			}
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}
		return false;
	}
	public boolean create_user(String email, String first_name, String last_name, String password, boolean is_staff, String card_num, String card_exp, String phone_num){
		PreparedStatement stmt;
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
			if (check_email_acc_exists(email)){return false;}
			stmt = conn.prepareStatement("INSERT INTO Users VALUES((?), (?), (?), (?), (?), DATETIME('now', '+10 hours'), (?), (?), (?), (?))");
			stmt.setString(1, email);
			stmt.setInt(2, create_id("Users"));
			stmt.setString(3, first_name);
			stmt.setString(4, last_name);
			stmt.setString(5, password);
			stmt.setBoolean(6, is_staff);
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
	public boolean email_match_password(String email, String password){
		try{
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE email = (?) AND password = (?)");
			stmt.setString(1, email);
			stmt.setString(2, password);
			ResultSet results = stmt.executeQuery();
			if (results.next()){ // email matches password in db
				return true;
			}
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}
		return false;
	}
	// Call this on a user login, we save the session ID generated here in the user's session. Returns -1 on failure
	public int add_user_login(String email, String password){
		int session_id = -1;
		try{
			if (!check_email_acc_exists(email) || !email_match_password(email, password)){return session_id;}
			session_id = create_id("User_Logins");
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO User_Logins VALUES ((?), (?), DATETIME('now', '+10 hours'), NULL)");
			stmt.setInt(1, session_id);
			stmt.setString(2, email);
			stmt.executeUpdate();
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}
		return session_id;
	}
	// Call this when the user actions a logout
	public void set_user_logout(int session_id){
		try{
			PreparedStatement stmt = conn.prepareStatement("UPDATE User_Logins SET logout_date = DATETIME('now', '+10 hours') WHERE id = (?)");
			stmt.setInt(1, session_id);
			stmt.executeUpdate();
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	public UserLogEntry get_user_log(int session_id){
		UserLogEntry log = new UserLogEntry();
		try{
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User_Logins WHERE id = (?)");
			stmt.setInt(1, session_id);
			ResultSet results = stmt.executeQuery();
			while (results.next()){
				log.set_email(results.getString("email"));
				log.set_session_id(results.getInt("id"));
				log.set_login_date(results.getString("login_date"));
				log.set_logout_date(results.getString("logout_date"));
			}
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}
		return log;
	}
	public UserLogEntry[] get_user_log_by_user(int session_id){
		ArrayList<UserLogEntry> log_arr = new ArrayList<UserLogEntry>();
		String user_email = "";
		try{
			PreparedStatement s = conn.prepareStatement("SELECT email FROM User_Logins WHERE id = (?) LIMIT 1");
			s.setInt(1, session_id);
			ResultSet r = s.executeQuery();
			while (r.next()){
				user_email = r.getString("email");
			}
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User_Logins WHERE email = (?)");
			stmt.setQueryTimeout(5);
			stmt.setString(1, user_email);
			ResultSet results = stmt.executeQuery();
			stmt.setInt(1, session_id);
			while (results.next()){
				log_arr.add(new UserLogEntry(results.getInt("id"), results.getString("email"), results.getString("login_date"), results.getString("logout_date")));
			}
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}

		return log_arr.toArray(new UserLogEntry[]{});
	}
	public UserLogEntry[] get_all_user_logs(){
		ArrayList<UserLogEntry> log_arr = new ArrayList<UserLogEntry>();
		try{
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(5);
			ResultSet results = stmt.executeQuery("SELECT * FROM User_Logins");
			while (results.next()){
				log_arr.add(new UserLogEntry(results.getInt("id"), results.getString("email"), results.getString("login_date"), results.getString("logout_date")));
			}
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}

		return log_arr.toArray(new UserLogEntry[]{});
	}
	public boolean add_product(String name, double price, int rating, String brand, int quantity){
		// The image location is set by default to a missing image, admin can change this later
		if (name != null && name.trim().isEmpty()){
			name = null;
		}
		if (brand != null && brand.trim().isEmpty()){
			brand = null;
		}
		try{
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO Products VALUES((?), (?), (?), (?), (?), (?), NULL)");
			stmt.setInt(1, create_id("Products"));
			stmt.setString(2, name);
			stmt.setDouble(3, price);
			stmt.setInt(4, rating);
			stmt.setString(5, brand);
			stmt.setInt(6, quantity);
			stmt.executeUpdate();
			return true;
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
			return false;
		}
	}
	public Product get_product(int id){
		Product db_product = new Product();
		try{
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Products WHERE id = (?)");
			stmt.setInt(1, id);
			ResultSet results = stmt.executeQuery();
			while (results.next()){
				db_product.set_name(results.getString("name"));
				db_product.set_id(results.getInt("id"));
				db_product.set_price(results.getDouble("price"));
				db_product.set_rating(results.getInt("rating"));
				db_product.set_brand(results.getString("brand"));
				db_product.set_quantity(results.getInt("quantity"));
				db_product.set_image_location(results.getString("image_location"));
			}
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}
		return db_product;
	}
	public void delete_product(int id){
		try{
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM Products WHERE id = (?)");
			stmt.setInt(1, id);
			stmt.executeUpdate();
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	public void update_product(int id, String name, double price, int rating, String brand, int quantity, String image_location){
		if (name != null && name.trim().isEmpty()){
			name = null;
		}
		if (brand != null && brand.trim().isEmpty()){
			brand = null;
		}
		if (image_location != null && image_location.trim().isEmpty()){
			image_location = null;
		}
		try{
			PreparedStatement stmt = conn.prepareStatement("UPDATE Products SET name = (?), price = (?), rating = (?), brand = (?), quantity = (?), image_location = (?) WHERE id = (?)");
			stmt.setString(1, name);
			stmt.setDouble(2, price);
			stmt.setInt(3, rating);
			stmt.setString(4, brand);
			stmt.setInt(5, quantity);
			stmt.setString(6, image_location);
			stmt.setInt(7, id);
			stmt.executeUpdate();
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	public Product[] get_all_products(){
		ArrayList<Product> product_arr = new ArrayList<Product>();
		try{
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(5);
			ResultSet results = stmt.executeQuery("SELECT * FROM Products");
			while (results.next()){
				product_arr.add(new Product(results.getInt("id"), results.getString("name"), results.getDouble("price"), results.getInt("rating"), results.getString("brand"), results.getInt("quantity"), results.getString("image_location")));
			}
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}
		return product_arr.toArray(new Product[]{});
	}
	public String cart_to_string(Cart cart){
		String fi = "";
		Product[] contents = cart.get_cart_inventory(this);
		for (int i = 0; i < contents.length - 1; i++){
			fi = fi + contents[i].get_id() + "|" + String.valueOf(contents[i].get_quantity()) + ",";
		}
		fi = fi + contents[contents.length - 1].get_id() + "|" + String.valueOf(contents[contents.length - 1].get_quantity());
		return fi;
	}
	public void create_order(Cart cart, Integer session_id){
		String owner_email;
		if (session_id == null){
			owner_email = null;
		}else{
			owner_email = this.get_user_log(session_id).get_email();
		}
		try{
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO Orders VALUES((?), (?), (?), 0, 0, DATETIME('now', '+10 hours'))");
			stmt.setInt(1, create_id("Orders"));
			stmt.setString(2, owner_email);
			stmt.setString(3, cart_to_string(cart));
			stmt.executeUpdate();
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	public Order get_order(int order_id){
		Order order = new Order();
		try{
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Orders WHERE id = (?)");
			stmt.setInt(1, order_id);
			ResultSet results = stmt.executeQuery();
			while (results.next()){
				order.set_id(results.getInt("id"));
				order.set_owner_email(results.getString("user_email"));
				order.set_items(this, results.getString("items"));
				order.set_finalised(results.getBoolean("finalised"));
				order.set_payment_id(results.getInt("payment_id"));
				order.set_date_created(results.getString("date_created"));
			}
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}
		return order;
	}
	public void delete_order(int order_id){
		try{
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM Orders WHERE id = (?) AND finalised = 0");
			stmt.setInt(1, order_id);
			stmt.executeUpdate();
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	public Order[] get_all_orders(){
		ArrayList<Order> order_arr = new ArrayList<Order>();
		try{
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(5);
			ResultSet results = stmt.executeQuery("SELECT * FROM Orders");
			while (results.next()){
				order_arr.add(new Order(this, results.getInt("id"), results.getString("user_email"), results.getString("items"), results.getBoolean("finalised"), results.getInt("payment_id"), results.getString("date_created")));
			}
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}
		return order_arr.toArray(new Order[]{});
	}
	public void insert_item_order(int order_id, int product_id, int amount){
		String prev_items = "";
		String new_items = "";
		String[] item_arr;
		String[] item_and_amount;
		boolean exists = false;
		try{
			PreparedStatement stmt = conn.prepareStatement("SELECT items FROM Orders WHERE id = (?)");
			stmt.setInt(1, order_id);
			ResultSet results = stmt.executeQuery();
			while (results.next()){
				prev_items = results.getString("items");
			}
			if (prev_items.trim().isEmpty()){
				return; // Order not found, can't insert
			}
			item_arr = prev_items.split(",");
			for (int i = 0; i < item_arr.length; i++){
				item_and_amount = item_arr[i].split("\\|");
				if (Integer.valueOf(item_and_amount[0]) == product_id){
					item_and_amount[1] = String.valueOf(amount + Integer.valueOf(item_and_amount[1]));
					exists = true;
				}
				item_arr[i] = item_and_amount[0] + "|" + item_and_amount[1];
			}
			new_items = String.join(",", item_arr);
			if (!exists){
				new_items = new_items + "," + String.valueOf(product_id) + "|" + String.valueOf(amount);
			}
			PreparedStatement stmt2 = conn.prepareStatement("UPDATE Orders SET items = (?) WHERE id = (?)");
			stmt2.setString(1, new_items);
			stmt2.setInt(2, order_id);
			stmt2.executeUpdate();
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	public void remove_item_order(int order_id, int product_id){
		String prev_items = "";
		String new_items = "";
		String[] item_arr;
		ArrayList<String> new_item_arr = new ArrayList<String>();
		String[] item_and_amount;
		try{
			PreparedStatement stmt = conn.prepareStatement("SELECT items FROM Orders WHERE id = (?)");
			stmt.setInt(1, order_id);
			ResultSet results = stmt.executeQuery();
			while (results.next()){
				prev_items = results.getString("items");
			}
			if (prev_items.trim().isEmpty()){
				return; // Order not found, can't insert
			}
			item_arr = prev_items.split(",");
			for (int i = 0; i < item_arr.length; i++){
				item_and_amount = item_arr[i].split("\\|");
				if (Integer.valueOf(item_and_amount[0]) != product_id){
					new_item_arr.add(item_and_amount[0] + "|" + item_and_amount[1]);
				}
			}
			new_items = String.join(",", new_item_arr.toArray(new String[]{}));
			PreparedStatement stmt2 = conn.prepareStatement("UPDATE Orders SET items = (?) WHERE id = (?)");
			stmt2.setString(1, new_items);
			stmt2.setInt(2, order_id);
			stmt2.executeUpdate();
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	// public Product[] search_for_product(String search){
	// 	ArrayList<Product> prod_arr = new ArrayList<Product>();
	// 	try{
	// 		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Products WHERE name LIKE (?)");
	// 		stmt.setString(1, "%" + search + "%");
	// 		ResultSet results = stmt.executeQuery();
	// 		while (results.next()){
	// 			prod_arr.add(new Product(results.getInt("id"), results.getString("name"), results.getDouble("price"), results.getInt("rating"), results.getString("brand"), results.getInt("quantity"), results.getString("image_location")));
	// 		}
	// 	}
	// 	catch (SQLException e){
	// 		System.out.println("ERROR: " + e.getMessage());
	// 	}
	// 	return prod_arr.toArray(new Product[]{});
	// }

	public Product[] search_for_product(String search, String[] ratings, String[] brands, String priceRange) {
		ArrayList<Product> prod_arr = new ArrayList<Product>();
		try {
			StringBuilder query = new StringBuilder("SELECT * FROM Products WHERE name LIKE ?");
			
			// Add dynamic filters based on user input
			if (ratings != null && ratings.length > 0) {
				query.append(" AND rating >= ").append(ratings[0]); // simplest approach for demonstration
			}
	
			if (brands != null && brands.length > 0) {
				query.append(" AND brand IN (");
				for (int i = 0; i < brands.length; i++) {
					query.append("?");
					if (i < brands.length - 1) query.append(", ");
				}
				query.append(")");
			}
	
			if (priceRange != null && !priceRange.isEmpty()) {
				String[] range = priceRange.split("-");
				query.append(" AND price BETWEEN ").append(range[0]).append(" AND ").append(range[1]);
			}
	
			PreparedStatement stmt = conn.prepareStatement(query.toString());
			stmt.setString(1, "%" + search + "%");
			
			int index = 2; // Start from the second parameter
			if (brands != null) {
				for (String brand : brands) {
					stmt.setString(index++, brand);
				}
			}
			
			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				prod_arr.add(new Product(results.getInt("id"), results.getString("name"), results.getDouble("price"), results.getInt("rating"), results.getString("brand"), results.getInt("quantity"), results.getString("image_location")));
			}
		} catch (SQLException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
		return prod_arr.toArray(new Product[]{});
	}
	public Order[] get_orders_by_email(String user_email){
		ArrayList<Order> order_arr = new ArrayList<Order>();
		try{
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Orders WHERE user_email = (?)");
			stmt.setQueryTimeout(5);
			stmt.setString(1, user_email);
			ResultSet results = stmt.executeQuery();
			while (results.next()){
				order_arr.add(new Order(this, results.getInt("id"), results.getString("user_email"), results.getString("items"), results.getBoolean("finalised"), results.getInt("payment_id"), results.getString("date_created")));
			}
		}catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}
		return order_arr.toArray(new Order[]{});
	}
	public void create_payment(String owner_email){

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