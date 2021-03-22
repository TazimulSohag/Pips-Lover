package co.banglabs.pips_lover.activity;


import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


import co.banglabs.pips_lover.R;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText logName, logPassword;
    TextView goto_reg, forgot_pass;
    private FirebaseAuth mAuth;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPref = getSharedPreferences(String.valueOf(R.string.login_info), MODE_PRIVATE);
        try{
            //Toast.makeText(this, sharedPref.getString(String.valueOf(R.string.login_email), "default"), Toast.LENGTH_SHORT).show();
            if(sharedPref.contains(String.valueOf(R.string.login_email)) && sharedPref.contains(String.valueOf(R.string.login_password))){
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                //Toast.makeText(this, "sending", Toast.LENGTH_SHORT).show();
                finish();
            }
            //Toast.makeText(this, "else", Toast.LENGTH_SHORT).show();

        }catch (Exception E){
            //Toast.makeText(this, "error found", Toast.LENGTH_SHORT).show();
        }

        mAuth = FirebaseAuth.getInstance();
        logName = findViewById(R.id.reg_name_et);
        logPassword = findViewById(R.id.reg_password_et);
        goto_reg = findViewById(R.id.goto_reg_page_id);
        forgot_pass = findViewById(R.id.forgot_id);
        Button login_btn = findViewById(R.id.login_btn_id);


        login_btn.setOnClickListener(this);
        goto_reg.setOnClickListener(this);
        forgot_pass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.goto_reg_page_id){
            //Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Login.this, Registration.class);
            startActivity(intent);
            finish();
        }
        else if(v.getId()==R.id.forgot_id){

            final EditText resetMail = new EditText(v.getContext());
            final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
            passwordResetDialog.setTitle("Reset Password ?");
            passwordResetDialog.setMessage("Enter Your Login Email To receive Password Reset mail");
            passwordResetDialog.setView(resetMail);
            passwordResetDialog.setPositiveButton("Yes", (dialog, which) -> {
                String mail;
                if(!TextUtils.isEmpty(resetMail.getText())){
                    mail = resetMail.getText().toString();
                    mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(aVoid -> Toast.makeText(Login.this, "An Email has been send to your email", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(Login.this, "Email can't be send", Toast.LENGTH_SHORT).show());

                }
                else{
                    resetMail.setError("Enter an Email Aaddress");
                }

            });
            passwordResetDialog.setNeutralButton("Cancle", (dialog, which) -> {

            });
            passwordResetDialog.setCancelable(true);
            passwordResetDialog.create().show();

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

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    saveData();
                }

                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                // If sign in fails, display a message to the user.
                logName.requestFocus();
                Toast.makeText(Login.this, "Email or password is not match.", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void saveData()  {


        SharedPreferences.Editor editor = getSharedPreferences(String.valueOf(R.string.login_info), MODE_PRIVATE).edit();
        editor.putString(String.valueOf(R.string.login_email), logName.getText().toString());
        editor.putString(String.valueOf(R.string.login_password), logPassword.getText().toString());
        editor.apply();
        //Toast.makeText(this, sharedPref.getString(String.valueOf(R.string.login_email), "default"), Toast.LENGTH_SHORT).show();

    }

}