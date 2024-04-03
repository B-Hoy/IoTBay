package uts.isd;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class User {
    private String name;
    private String email;
    private String password;
    private LocalDateTime reg_date;
    private String admin;
    private String card_num;
    public User(String name, String email, String password, LocalDateTime reg_date, String admin, String card_num, String card_exp) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.reg_date = reg_date;
        this.admin = admin;
        this.card_num = card_num;
        this.card_exp = card_exp;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public LocalDateTime getReg_date() {
        return reg_date;
    }
    public void setReg_date(LocalDateTime reg_date) {
        this.reg_date = reg_date;
    }
    public String getAdmin() {
        return admin;
    }
    public void setAdmin(String admin) {
        this.admin = admin;
    }
    public String getCard_num() {
        return card_num;
    }
    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }
    public String getCard_exp() {
        return card_exp;
    }
    public void setCard_exp(String card_exp) {
        this.card_exp = card_exp;
    }
    public String get_formatted_date(){
        return reg_date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
    public static String get_formatted_date(LocalDateTime inp_date){
        return inp_date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
    public String get_admin_string(){
        if (admin == null || admin.equals("0")){
            return "False";
        }
        return "True";
    }
    private String card_exp;
}
