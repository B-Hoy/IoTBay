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

			// This'll be used when the login/logout actions are set up
			stmt.executeUpdate("DROP TABLE IF EXISTS User_Logins");
			stmt.executeUpdate("CREATE TABLE User_Logins (id INTEGER NOT NULL PRIMARY KEY, email TEXT NOT NULL, login_date TEXT NOT NULL, logout_date TEXT)");
			// Test data go here
			stmt.executeUpdate("DROP TABLE IF EXISTS Products");
			// The image_location has an implicit "web_pages/images/" put at the start of it
			// quantity = stock on hand
			stmt.executeUpdate("CREATE TABLE Products (id INTEGER NOT NULL PRIMARY KEY, name TEXT NOT NULL, price REAL NOT NULL, rating INTEGER NOT NULL, brand TEXT NOT NULL, quantity INTEGER NOT NULL, image_location TEXT)");
			// More test data go here
			stmt.executeUpdate("INSERT INTO Products VALUES(10001, 'IoT-Enabled Smart Light Bulb', 15.99, 4, 'Connect SmartHome', 500, 'smart_light.jpg')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10002, 'WiFi Smart Camera', 139.49, 5, 'Arlo', 250, 'smart_camera.png')");
			stmt.executeUpdate("INSERT INTO Products VALUES(10003, 'Doorbell Security Camera Pro', 349.00, 3, 'Ring', 180, 'ring_camera.png')");
			// Product Orders
			stmt.executeUpdate("DROP TABLE IF EXISTS Orders");
			// Items is set up as comma-seperated, with each entry going <item id>|<amount
			// Finalised just means whether or not the user has "signed off" on it, with true meaning it can't be edited
			stmt.executeUpdate("CREATE TABLE Orders (id INTEGER NOT NULL PRIMARY KEY, user_email TEXT, items TEXT, finalised INTEGER, payment_id INTEGER, date_created TEXT NOT NULL)");
			stmt.executeUpdate("INSERT INTO Orders VALUES (20001, 'testacc1@uts.edu.au', '10001|5,10002|50,10003|500', 0, 0, DATETIME('now', '+10 hours'))");
			stmt.executeUpdate("INSERT INTO Orders VALUES (20002, 'testacc2@uts.edu.au', '10001|2000', 1, 0, DATETIME('now', '+10 hours'))");
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
	public boolean create_user(String email, String first_name, String last_name, String password, boolean is_admin, String card_num, String card_exp, String phone_num){
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
	public Product[] search_for_product(String search){
		ArrayList<Product> prod_arr = new ArrayList<Product>();
		try{
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Products WHERE name LIKE (?)");
			stmt.setString(1, "%" + search + "%");
			ResultSet results = stmt.executeQuery();
			while (results.next()){
				prod_arr.add(new Product(results.getInt("id"), results.getString("name"), results.getDouble("price"), results.getInt("rating"), results.getString("brand"), results.getInt("quantity"), results.getString("image_location")));
			}
		}
		catch (SQLException e){
			System.out.println("ERROR: " + e.getMessage());
		}
		return prod_arr.toArray(new Product[]{});
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
