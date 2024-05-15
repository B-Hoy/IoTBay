package uts.iotbay;

public class Payment {
    int payment_id;
    String owner_email;
    double amount;
    String card_num;
    String card_exp;
    int card_cvc;
    public Payment(){
    }
    public Payment(int payment_id, String owner_email, double amount, String card_num, String card_exp, int card_cvc){
        this.payment_id = payment_id;
        this.owner_email = owner_email;
        this.amount = amount;
        this.card_num = card_num;
        this.card_exp = card_exp;
        this.card_cvc = card_cvc;
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
    public double get_amount() {
        return amount;
    }
    public void set_amount(double amount) {
        this.amount = amount;
    }
    public String getCard_num() {
        return card_num;
    }
    public void set_card_num(String card_num) {
        this.card_num = card_num;
    }
    public String get_card_exp() {
        return card_exp;
    }
    public void set_card_exp(String card_exp) {
        this.card_exp = card_exp;
    }
    public int get_card_cvc() {
        return card_cvc;
    }
    public void set_card_cvc(int card_cvc) {
        this.card_cvc = card_cvc;
    }
    
}
