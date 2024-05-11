package uts.iotbay;
public class Product {
    String name;
    double price;
    int rating;
    String brand;
    String image_location;
    public Product(){
    }
    public Product(String name, double price, int rating, String brand, String image_location){
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.brand = brand;
        this.image_location = image_location;
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
}
