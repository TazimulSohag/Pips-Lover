package co.banglabs.pips_lover.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.banglabs.pips_lover.R;
import co.banglabs.pips_lover.datahandle.PairBundle;
import co.banglabs.pips_lover.datahandle.UserBundle;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    private TextView regUserName, regUserPassword, regEmail;
    private Button reg_button;
    private FirebaseAuth mAuth;
    DatabaseReference user_reference;
    DataSnapshot user_snapshot;

    @Override
    protected void onStart() {
        super.onStart();

        user_reference = FirebaseDatabase.getInstance().getReference("UserInfo");

        user_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user_snapshot = dataSnapshot;
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
               // boolean value = dataSnapshot.hasChild(user_name);
                //Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }

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

        if(check_name_validity(name)){
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        UserBundle userBundle = new UserBundle(name, email);
                        user_reference.child(name).setValue(userBundle);
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(Registration.this, "Registration Sucessfull.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Registration.this, Login.class);
                        startActivity(intent);
                    } else {
                        regEmail.setError("This email is already registered");
                        regEmail.requestFocus();
                        // If sign in fails, display a message to the user.
                        Toast.makeText(Registration.this, "Register is not Sucessfull.", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }
        else{
            regUserName.setError("This name is already taken");
            regUserName.requestFocus();
        }



    }

    boolean check_name_validity(String user_name){

        if(user_snapshot.hasChild(user_name)){
            return false;
        }
        else{
            return true;
        }
        //Log.d("dataval", String.valueOf(user_snapshot.hasChild(user_name)));
        //Toast.makeText(this, String.valueOf(user_snapshot.hasChild(user_name)), Toast.LENGTH_SHORT).show();
        //return false;
    }

}