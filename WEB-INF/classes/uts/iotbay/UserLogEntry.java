package uts.iotbay;

public class UserLogEntry {
    int session_id;
    String email;
    String login_date;
    String logout_date;
    public UserLogEntry(){
    }
    public UserLogEntry(int session_id, String email, String login_date, String logout_date){
        this.session_id = session_id;
        this.email = email;
        this.login_date = login_date;
        this.logout_date = logout_date;
    }
    public void set_email(String email){
        this.email = email;
    }
    public String get_email(){
        return email;
    }
    public void set_session_id(int session_id){
        this.session_id = session_id;
    }
    public int get_session_id(){
        return session_id;
    }
    public void set_login_date(String login_date){
        this.login_date = login_date;
    }
    public String get_login_date(){
        return login_date;
    }
    public void set_logout_date(String logout_date){
        this.logout_date = logout_date;
    }
    public String get_logout_date(){
        return logout_date;
    }
}
