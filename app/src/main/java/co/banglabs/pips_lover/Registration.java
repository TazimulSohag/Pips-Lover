package co.banglabs.pips_lover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    private TextView regUserName, regUserPassword, regEmail;
    private Button reg_button;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        regUserName = findViewById(R.id.reg_name_et);
        regUserPassword = findViewById(R.id.reg_password_et);
        regEmail = findViewById(R.id.reg_email_et);
        reg_button = findViewById(R.id.reg_button_join_us);

        mAuth = FirebaseAuth.getInstance();


        reg_button.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.reg_button_join_us){

            register();


        }

    }

    private void register() {

        Toast.makeText(this, "Registering", Toast.LENGTH_SHORT).show();
        String name, email, password;
        name = regUserName.getText().toString().trim();
        email = regEmail.getText().toString().trim();
        password = regUserPassword.getText().toString().trim();

        if(email.isEmpty()){

            regEmail.setError("Enter an Email address");
            regEmail.requestFocus();
            return;


        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){


            regEmail.setError("Enter a valid Email address");
            regEmail.requestFocus();
            return;

        }
        if(password.isEmpty()){

            regUserPassword.setError("Enter an password");
            regUserPassword.requestFocus();
            return;


        }
        if(password.length()<6){

            regUserPassword.setError("Enter an strong");
            regUserPassword.requestFocus();
            return;

        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(Registration.this, "Registration Sucessfull.", Toast.LENGTH_SHORT).show();
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(Registration.this, "Register is not Sucessfull.", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

}