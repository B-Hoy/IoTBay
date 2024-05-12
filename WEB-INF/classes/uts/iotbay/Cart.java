package uts.iotbay;
import java.util.ArrayList;
public class Cart {
    ArrayList<Integer> product_ids;
    ArrayList<Integer> product_amounts;
    // Order of product and amount is the same between these two
    // E.g. product_amounts[1] is the amount of the product specified at product_ids[1]
    public Cart(){
        product_ids = new ArrayList<Integer>();
        product_amounts = new ArrayList<Integer>();
    }
    // True if found and deleted, false if not found
    public boolean delete_product(int product_id){
        int location = product_ids.indexOf(product_id);
        if (location == -1){
            return false;
        }
        product_ids.remove(location);
        product_amounts.remove(location);
        return true;
    }
    public void purge_cart(){
        product_ids.clear();
        product_amounts.clear();
    }
    public void add_product(int product_id, int amount){
        int index = product_ids.indexOf(product_id);
        if (index != -1){ // If product_id already exists in cart
            product_amounts.set(index, amount + product_amounts.get(index));
        }else{
            product_ids.add(product_id);
            product_amounts.add(amount);
        }
    }
    public int get_product_amount(int product_id){
        int location = product_ids.indexOf(product_id);
        if (location == -1){
            return 0;
        }
        return product_amounts.get(location);
    }
    public Product[] get_cart_inventory(Database db){
        ArrayList<Product> product_arr = new ArrayList<Product>();
        Product temp = new Product();
        for (int i = 0; i < product_ids.size(); i++){
            temp = db.get_product(product_ids.get(i));
            // Quantity in this context means amount in cart, not stock on hand
            temp.set_quantity(product_amounts.get(i));
            product_arr.add(temp);
        }
        return product_arr.toArray(new Product[]{});
    }
}