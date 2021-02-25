package co.banglabs.pips_lover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Login extends AppCompatActivity implements View.OnClickListener {

    private TextView logName, logPassword, goto_reg;
    private Button login_btn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        logName = findViewById(R.id.reg_name_et);
        logPassword = findViewById(R.id.reg_password_et);
        goto_reg = findViewById(R.id.goto_reg_page_id);

        login_btn = findViewById(R.id.login_btn_id);


        login_btn.setOnClickListener(this);
        goto_reg.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.goto_reg_page_id){
            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Login.this, Registration.class);
            startActivity(intent);
            finish();

        }

        else{


            userLogin();


        }

    }

    private void userLogin() {


        String email, password;

        email = logName.getText().toString().trim();
        password = logPassword.getText().toString().trim();

        if(email.isEmpty()){

            logName.setError("Enter an Email address");
            logName.requestFocus();
            return;


        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){


            logName.setError("Enter a valid Email address");
            logName.requestFocus();
            return;

        }
        if(password.isEmpty()){

            logPassword.setError("Enter an password");
            logPassword.requestFocus();
            return;


        }
        if(password.length()<6){

            logPassword.setError("Enter an strong");
            logPassword.requestFocus();
            return;

        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(Login.this, "login is not Sucessfull.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}