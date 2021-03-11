package co.banglabs.pips_lover.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.security.GeneralSecurityException;

import co.banglabs.pips_lover.R;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private TextView logName, logPassword, goto_reg;
    private Button login_btn;
    private FirebaseAuth mAuth;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPref = getSharedPreferences(String.valueOf(R.string.login_info), MODE_PRIVATE);
        try{
            Toast.makeText(this, sharedPref.getString(String.valueOf(R.string.login_email), "default"), Toast.LENGTH_SHORT).show();
            if(sharedPref.contains(String.valueOf(R.string.login_email)) && sharedPref.contains(String.valueOf(R.string.login_password))){
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                //Toast.makeText(this, "sending", Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                //Toast.makeText(this, "else", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception E){
            //Toast.makeText(this, "error found", Toast.LENGTH_SHORT).show();
        }

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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        try {
                            saveData();
                        } catch (GeneralSecurityException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void saveData() throws GeneralSecurityException, IOException {

        /*String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

        SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                "secret_shared_prefs",
                masterKeyAlias,
                getApplicationContext(),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );*/

        SharedPreferences.Editor editor = getSharedPreferences(String.valueOf(R.string.login_info), MODE_PRIVATE).edit();
        editor.putString(String.valueOf(R.string.login_email), logName.getText().toString());
        editor.putString(String.valueOf(R.string.login_password), logPassword.getText().toString());
        editor.apply();
        //Toast.makeText(this, sharedPref.getString(String.valueOf(R.string.login_email), "default"), Toast.LENGTH_SHORT).show();

    }

}