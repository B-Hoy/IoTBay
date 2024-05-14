package uts.iotbay;

import java.util.ArrayList;

public class Order {
    int id;
    int payment_id;
    String owner_email;
    boolean finalised;
    Product[] items;
    String date_created;
    public Order(){}
    public Order(Database db, int id, String owner_email, String items, boolean finalised, int payment_id, String date_created){
        this.id = id;
        this.owner_email = owner_email;
        this.finalised = finalised;
        this.date_created = date_created;
        this.payment_id = payment_id;
        this.set_items(db, items);
    }
    public int get_id() {
        return id;
    }
    public void set_id(int id) {
        this.id = id;
    }
    public int get_payment_id() {
        return payment_id;
    }
    public void set_payment_id(int payment_id) {
        this.payment_id = payment_id;
    }
    public String get_owner_email() {
        return owner_email;
    }
    public void set_owner_email(String owner_email) {
        this.owner_email = owner_email;
    }
    public boolean get_finalised() {
        return finalised;
    }
    public void set_finalised(boolean finalised) {
        this.finalised = finalised;
    }
    public String get_date_created() {
        return date_created;
    }
    public void set_date_created(String date_created) {
        this.date_created = date_created;
    }
    public Product[] get_items() {
        return items;
    }
    public void set_items(Database db, String s_items) {
        ArrayList<Product> product_arr = new ArrayList<Product>();
        String[] s = s_items.split(",");
        String[] j;
        Product p;
        for (String i : s){
            j = i.split("\\|");
            p = db.get_product(Integer.valueOf(j[0]));
            p.set_quantity(Integer.valueOf(j[1]));
            product_arr.add(p);
        }
        this.items = product_arr.toArray(new Product[]{});
    }
    
}
