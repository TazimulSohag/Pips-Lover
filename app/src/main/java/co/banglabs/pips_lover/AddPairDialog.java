package co.banglabs.pips_lover;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import co.banglabs.pips_lover.datahandle.PairBundle;

public class AddPairDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button cancle, create;
    EditText pair_name;
    DatabaseReference pair_reference, admin_reference;

    public AddPairDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_paer_dialog);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = sdf.format(new Date());

        pair_name = findViewById(R.id.input_paer_name);
        pair_reference = FirebaseDatabase.getInstance().getReference("Pairs").child(currentDateandTime);
        admin_reference = FirebaseDatabase.getInstance().getReference("PairList");

        cancle = (Button) findViewById(R.id.cancle_btn_id);
        create = (Button) findViewById(R.id.create_btn_id);
        cancle.setOnClickListener(this);
        create.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_btn_id:

                addPair();
                break;
            case R.id.cancle_btn_id:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }


    private void addPair() {

        String name = pair_name.getText().toString();
        if(!TextUtils.isEmpty(name)){

            PairBundle pairBundle = new PairBundle(name);
            admin_reference.child(name).setValue(pairBundle);
        }
        else{
            Toast.makeText(getContext(), "Pair Name Is Empty", Toast.LENGTH_SHORT).show();
        }
    }


}
