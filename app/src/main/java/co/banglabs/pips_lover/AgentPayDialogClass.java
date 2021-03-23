package co.banglabs.pips_lover;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AgentPayDialogClass extends Dialog implements android.view.View.OnClickListener{


    public Activity c;
    public Dialog d;
    public Button yes, no;
    String agent_Token, agent_name, new_users;
    TextView message, message2;
    DatabaseReference token_reference;

    public AgentPayDialogClass(Activity a, String agent_Token, String agent_name, String new_users) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.agent_Token = agent_Token;
        this.agent_name =agent_name;
        this.new_users = new_users;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.agent_pay_dialog);
        message = findViewById(R.id.message_id);
        message2 = findViewById(R.id.message_id2);
        message.setText(agent_name);
        message2.setText(new_users);

        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:

                try{

                    token_reference = FirebaseDatabase.getInstance().getReference("Reference").child(agent_Token);
                    token_reference.child("new_users").setValue("0");
                }catch (Exception e){

                }
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

}
