package co.banglabs.pips_lover.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import co.banglabs.pips_lover.datahandle.PairBundle;
import co.banglabs.pips_lover.R;


public class ProvideSignal extends AppCompatActivity implements View.OnClickListener {
    TextView pair_nametv;
    EditText tp1, tp2, sl, current_value;
    RadioGroup signal;
    Button submit;
    DatabaseReference pair_reference, admin_reference;
    DataSnapshot admin_snapshot;
    private RequestQueue mQueue;
    ImageButton copy_pest;

    String takep1, takep2, stopl, signaloption, open_price, pair_name, currentDateandTime;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_provide_signal);

        mQueue = Volley.newRequestQueue(this);

        Toolbar toolbar = findViewById(R.id.toolbar_provide_signal);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Boolean has_previous_value;

        //getData();
        copy_pest = findViewById(R.id.copy_pest_id);
        tp1 = findViewById(R.id.tp1);
        tp2 = findViewById(R.id.tp2);
        sl = findViewById(R.id.sl);

        current_value = findViewById(R.id.current_value);
        submit = findViewById(R.id.submit_btn_id);
        signal = findViewById(R.id.action_group_id);
        pair_nametv = findViewById(R.id.pair_name_id);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        currentDateandTime = sdf.format(new Date());

        pair_reference = FirebaseDatabase.getInstance().getReference("Pairs").child(currentDateandTime);
        admin_reference = FirebaseDatabase.getInstance().getReference("PairList");

        String pair_selected = getIntent().getStringExtra("pair_name");


        //setting initial values for tp1, tp2 and sl
        pair_nametv.setText(pair_selected);
        pair_name = pair_nametv.getText().toString();
        get_PairValue_From_API(pair_name.replace(" ", "_"));

        copy_pest.setOnClickListener(this);
        submit.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();


        admin_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                admin_snapshot = snapshot;
                //updatingStatas(pair_name, 84.150);
                Log.d("outmsg2", "0");
                PairBundle pairBundle = snapshot.child(pair_name).getValue(PairBundle.class);
                Log.d("outmsg2", "1");
                if(!pairBundle.getTake_profit_1().equals("null")){
                    Log.d("outmsg2", "2");

                    tp1.setText(pairBundle.getTake_profit_1());
                    tp2.setText(pairBundle.getTake_profit_2());
                    sl.setText(pairBundle.getStop_loss());
                    current_value.setText(pairBundle.getOpen_price());
                }
                else{
                    Log.d("outmsg2", "3");

                    /*tp1.setText("");
                    tp2.setText("");
                    sl.setText("");
                    current_value.setText("");*/

                }


                if(pairBundle.getPair_action().equals("BUY")){

                    signal.check(R.id.buy_option_id);
                }else{
                    signal.check(R.id.sell_option_id);
                }
                //Log.d("out2", "value "+pairBundle.getPair_name());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("outmsg2", "4");
            }
        });

    }

    private void get_PairValue_From_API(String name) {
        String url = "https://free.currconv.com/api/v7/convert?apiKey=2ec64af2c4a5303dd43f&q=" + name;
        Log.d("newurl", url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            try {

                JSONObject obj = response.getJSONObject("results");
                JSONObject obj2 = obj.getJSONObject(name);

                Double exchangeRate = obj2.getDouble("val");
                current_value.setText(String.valueOf(exchangeRate));



            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("outmsg2", String.valueOf(e));
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);


    }


    private void addSignal() {

        PairBundle pairBundle = new PairBundle(pair_name, "Online", signaloption, open_price, stopl, takep1, takep2, "Waiting", currentDateandTime);
        pair_reference.child(pair_name).setValue(pairBundle);
        admin_reference.child(pair_name).setValue(pairBundle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.submit_btn_id){

            try{
                int id;
                id = signal.getCheckedRadioButtonId();
                RadioButton selected = findViewById(id);
                signaloption = selected.getText().toString();
            }catch (Exception e){
                Toast.makeText(ProvideSignal.this, "Please select action", Toast.LENGTH_SHORT).show();
            }


            takep1 = tp1.getText().toString();
            takep2 = tp2.getText().toString();
            stopl = sl.getText().toString();
            open_price = current_value.getText().toString();


            if(!(TextUtils.isEmpty(takep1) || TextUtils.isEmpty(takep2) || TextUtils.isEmpty(stopl) || TextUtils.isEmpty(signaloption))){

                addSignal();

                Toast.makeText(ProvideSignal.this, "Signal updated!!!", Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                Toast.makeText(ProvideSignal.this, "Please fill all the info!!!", Toast.LENGTH_SHORT).show();
            }

        }
        else if(v.getId()==R.id.copy_pest_id){

            sl.setText(current_value.getText().toString());
            tp1.setText(current_value.getText().toString());
            tp2.setText(current_value.getText().toString());

        }
    }
}