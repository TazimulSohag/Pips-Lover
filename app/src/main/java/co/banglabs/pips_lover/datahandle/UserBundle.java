package co.banglabs.pips_lover.datahandle;

public class UserBundle {

    String name;
    String email;
    String subscription;
    String token;

    public UserBundle(){

    }

    public UserBundle(String name, String email, String token) {
        this.name = name;
        this.email = email;
        this.token = token;
        this.subscription = "0";
    }

    /*public UserBundle(String name, String email, String subscription) {
        this.name = name;
        this.email = email;
        this.subscription = subscription;
    }*/

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSubscription() {
        return subscription;
    }

    public String getToken() {
        return token;
    }
}
