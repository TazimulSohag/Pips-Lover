package co.banglabs.pips_lover.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import co.banglabs.pips_lover.datahandle.CurrencyConverter;
import co.banglabs.pips_lover.datahandle.DataAPI;
import co.banglabs.pips_lover.datahandle.PairBundle;
import co.banglabs.pips_lover.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProvideSignal extends AppCompatActivity {
    TextView pair_nametv, date_view;
    EditText tp1, tp2, sl, current_value;
    RadioGroup signal;
    Button submit;
    DatabaseReference pair_reference;

    String takep1, takep2, stopl, signaloption, open_price, pair_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_provide_signal);

        //getData();

        tp1 = findViewById(R.id.tp1);
        tp2 = findViewById(R.id.tp2);
        sl = findViewById(R.id.sl);

        current_value = findViewById(R.id.current_value);
        submit = findViewById(R.id.submit_btn_id);
        signal = findViewById(R.id.action_group_id);
        pair_nametv = findViewById(R.id.pair_name_id);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = sdf.format(new Date());


        pair_reference = FirebaseDatabase.getInstance().getReference("Pairs").child(currentDateandTime);
        String pair_selected = getIntent().getStringExtra("pair_name");
        getSupportActionBar().setTitle("Set Signal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //setting initial values for tp1, tp2 and sl
        pair_nametv.setText(pair_selected);
        open_price = current_value.getText().toString();
        tp1.setText(open_price);
        tp2.setText(open_price);
        sl.setText(open_price);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                pair_name = pair_nametv.getText().toString();

                if(!(TextUtils.isEmpty(takep1) || TextUtils.isEmpty(takep2) || TextUtils.isEmpty(stopl) || TextUtils.isEmpty(signaloption))){

                    addSignal();

                    Toast.makeText(ProvideSignal.this, "Signal updated!!!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(ProvideSignal.this, "Please fill all the info!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addSignal() {

        PairBundle pairBundle = new PairBundle(pair_name, "Online", signaloption, open_price, stopl, takep1, takep2, "Waiting", Runtime.getRuntime().toString());
        pair_reference.child(pair_name).setValue(pairBundle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    private void getData(){

        Call<CurrencyConverter> converted = DataAPI.getConverterScrvice().getPearValue();
        converted.enqueue(new Callback<CurrencyConverter>() {
            @Override
            public void onResponse(Call<CurrencyConverter> call, Response<CurrencyConverter> response) {
                CurrencyConverter converter = response.body();
                Toast.makeText(ProvideSignal.this, "sucess", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CurrencyConverter> call, Throwable t) {

                Toast.makeText(ProvideSignal.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

}