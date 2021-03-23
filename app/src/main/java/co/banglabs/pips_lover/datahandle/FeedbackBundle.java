package co.banglabs.pips_lover.datahandle;

import co.banglabs.pips_lover.activity.ContactActivity;

public class FeedbackBundle {

    String email, feedback;

    public FeedbackBundle(String email, String feedback) {
        this.email = email;
        this.feedback = feedback;
    }

    public String getEmail() {
        return email;
    }

    public String getFeedback() {
        return feedback;
    }

    public static boolean check(ContactActivity contactActivity) {


        return false;
    }


}
