package co.banglabs.pips_lover.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.banglabs.pips_lover.R;
import co.banglabs.pips_lover.datahandle.FeedbackBundle;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener {
    EditText revew;
    Button clear, send;
    DatabaseReference feedback_reference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_contact);

        feedback_reference = FirebaseDatabase.getInstance().getReference("Feedback");

        Toolbar toolbar = findViewById(R.id.toolbar_contact);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        revew = findViewById(R.id.user_revew_box_id);

        clear = findViewById(R.id.clear_btn_id);
        send = findViewById(R.id.send_btn_id);

        clear.setOnClickListener(this);
        send.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.clear_btn_id) {


            revew.setText("");

        } else if (view.getId() == R.id.send_btn_id) {


            try {

                if (!revew.getText().toString().isEmpty()) {
                    FeedbackBundle feedbackBundle = new FeedbackBundle(user.getEmail(), revew.getText().toString());
                    feedback_reference.child(user.getUid()).setValue(feedbackBundle);
                    Toast.makeText(this, "Thank you for your cooperation", Toast.LENGTH_SHORT).show();

                    this.finish();
                    this.finish();


                } else {
                    Toast.makeText(this, "Please fill all the information correctly", Toast.LENGTH_SHORT).show();
                }


            } catch (Exception ignored) {

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