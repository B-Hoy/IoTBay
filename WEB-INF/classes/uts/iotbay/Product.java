package uts.iotbay;
public class Product {
    int id;
    String name;
    double price;
    int rating;
    String brand;
    String image_location;
    int quantity;
    public Product(){
    }
    public Product(int id, String name, double price, int rating, String brand, int quantity, String image_location){
        this.id = id;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.brand = brand;
        this.image_location = image_location;
        this.quantity = quantity;
    }
    public int get_id(){
        return id;
    }
    public void set_id(int id){
        this.id = id;
    }
    public String get_name() {
        return name;
    }
    public void set_name(String name) {
        this.name = name;
    }
    public double get_price() {
        return price;
    }
    public void set_price(double price) {
        this.price = price;
    }
    public int get_rating() {
        return rating;
    }
    public void set_rating(int rating) {
        this.rating = rating;
    }
    public String get_brand() {
        return brand;
    }
    public void set_brand(String brand) {
        this.brand = brand;
    }
    public String get_image_location() {
        return image_location;
    }
    public void set_image_location(String image_location) {
        this.image_location = image_location;
    }
    public int get_quantity() {
        return quantity;
    }
    public void set_quantity(int quantity) {
        this.quantity = quantity;
    }
}
