package uts.iotbay;
import java.util.ArrayList;
public class Cart {
    ArrayList<Integer> product_ids;
    ArrayList<Integer> product_amounts;
    // Order of product and amount is the same between these two
    // E.g. product_amounts[1] is the amount of the product specified at product_ids[1]
    public Cart(){
    }
    // item_ids provided in comma seperated <id>|<amount> format
    public Cart(String items){
        String[] temp = items.split(",");
        String[] j;
        for (String i : temp){
            j = i.split("|");
            product_ids.add(Integer.valueOf(j[0]));
            product_amounts.add(Integer.valueOf(j[1]));
        }
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
        product_ids.add(product_id);
        product_amounts.add(amount);
    }
    public int get_product_amount(int product_id){
        int location = product_ids.indexOf(product_id);
        if (location == -1){
            return 0;
        }
        return product_amounts.get(location);
    }
    public Integer[][] get_cart_inventory(){
        // product_arr[0] = product_ids, product_arr[1] = product_amounts
        Integer[][] product_arr = new Integer[2][product_ids.size()];
        product_arr[0] = product_ids.toArray(product_arr[0]);
        product_arr[1] = product_amounts.toArray(product_arr[1]);
        return product_arr;
    }
}
