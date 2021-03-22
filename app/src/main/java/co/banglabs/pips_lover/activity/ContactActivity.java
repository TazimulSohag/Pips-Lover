package co.banglabs.pips_lover.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import co.banglabs.pips_lover.DataClass;
import co.banglabs.pips_lover.R;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener {
    EditText email, revew;
    Button clear, send;
    DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = findViewById(R.id.toolbar_contact);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        email = findViewById(R.id.feedback_email_id);
        revew = findViewById(R.id.user_revew_box_id);

        clear = findViewById(R.id.clear_btn_id);
        send = findViewById(R.id.send_btn_id);

        clear.setOnClickListener(this);
        send.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.clear_btn_id) {

            email.setText("");
            revew.setText("");

        } else if (view.getId() == R.id.send_btn_id) {
            try {

                if (!email.getText().toString().isEmpty() && email.getText().toString().contains("@") && email.getText().toString().toLowerCase().contains(".com") && !revew.getText().toString().isEmpty()) {

                    if (DataClass.check(ContactActivity.this)) {
                        dr.push().setValue(email.getText().toString() + " : " + revew.getText().toString());
                        Toast.makeText(this, "Thank you for your oppnion", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(this, "Please fill all the information correctly", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {


    }
}