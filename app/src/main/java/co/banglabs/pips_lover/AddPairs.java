package co.banglabs.pips_lover;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import co.banglabs.pips_lover.datahandle.PairBundle;

public class AddPairs extends AppCompatDialogFragment {

    EditText pair_name;
    DatabaseReference pair_reference, admin_reference;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = sdf.format(new Date());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_add_pairs, null);

        pair_name = view.findViewById(R.id.pair_name_id);
        pair_reference = FirebaseDatabase.getInstance().getReference("Pairs").child(currentDateandTime);
        admin_reference = FirebaseDatabase.getInstance().getReference("PairList");

        builder.setView(view)
                .setTitle("Add Currency Pairs")
                .setNegativeButton("cancle", null)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                        addPair();
                        Toast.makeText(getContext(), "Pair has been added sucessfully!", Toast.LENGTH_SHORT).show();
                    }
                });

        return builder.create();
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