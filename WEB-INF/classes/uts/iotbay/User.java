package uts.iotbay;
public class User {
    String email;
    int id;
    String first_name;
    String last_name;
    String password;
    String reg_date;
    boolean is_staff;
    String card_num;
    String card_exp;
    String phone_num;
    public User(){

    }
    public User(String email, int id, String first_name, String last_name, String password, String reg_date, boolean is_staff, String card_num, String card_exp, String phone_num){
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.reg_date = reg_date;
        this.is_staff = is_staff;
        this.card_num = card_num;
        this.card_exp = card_exp;
        this.phone_num = phone_num;
    }
    public void set_id(int id){
        this.id = id;
    }
    public int get_id(){
        return id;
    }
    public void set_email(String email){
        this.email = email;
    }
    public String get_email(){
        return email;
    }
    public void set_first_name(String first_name){
        this.first_name = first_name;
    }
    public String get_first_name(){
        return first_name;
    }
    public void set_last_name(String last_name){
        this.last_name = last_name;
    }
    public String get_last_name(){
        return last_name;
    }
    public void set_password(String password){
        this.password = password;
    }
    public String get_password(){
        return password;
    }
    public void set_reg_date(String reg_date){
        this.reg_date = reg_date;
    }
    public String get_reg_date(){
        return reg_date;
    }
    public void set_is_staff(boolean is_staff){
        this.is_staff = is_staff;
    }
    public boolean get_is_staff(){
        return is_staff;
    }
    public String get_is_staff_string(){
        return is_staff ? "True" : "False";
    }
    public void set_card_num(String card_num){
        this.card_num = card_num;
    }
    public String get_card_num(){
        return card_num;
    }
    public void set_card_exp(String card_exp){
        this.card_exp = card_exp;
    }
    public String get_card_exp(){
        return card_exp;
    }
    public void set_phone_num(String phone_num){
        this.phone_num = phone_num;
    }
    public String get_phone_num(){
        return phone_num;
    }
}
