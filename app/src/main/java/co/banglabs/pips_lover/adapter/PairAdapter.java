package co.banglabs.pips_lover.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import co.banglabs.pips_lover.datahandle.PairBundle;
import co.banglabs.pips_lover.R;
import co.banglabs.pips_lover.activity.ProvideSignal;

public class PairAdapter extends ArrayAdapter {

    private Activity context;
    private List<PairBundle> pairlist;

    public PairAdapter(Activity context, List<PairBundle> pairlist) {
        super(context, R.layout.pair_base_layout, pairlist);
        this.context = context;
        this.pairlist = pairlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listviewitem = inflater.inflate(R.layout.pair_base_layout, null, true);

        TextView name = listviewitem.findViewById(R.id.pair_name);
        TextView statas = listviewitem.findViewById(R.id.statas);
        TextView current_position = listviewitem.findViewById(R.id.current_position);
        TextView pswitch = listviewitem.findViewById(R.id.switch_id);
        LinearLayout parentv = listviewitem.findViewById(R.id.parent_view);


        PairBundle pairBundle = pairlist.get(position);
        name.setText(pairBundle.getPair_name());
        statas.setText(pairBundle.getPair_statas());
        current_position.setText(pairBundle.getTrade_result());
        pswitch.setText(pairBundle.getPair_action());

        if(pairBundle.getPair_action().equals("BUY")){
            parentv.setBackgroundColor(Color.parseColor("#CBFBD3"));
        }
        else if(pairBundle.getPair_action().equals("SELL")){
            parentv.setBackgroundColor(Color.parseColor("#FBD8E4"));
        }


        
        listviewitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProvideSignal.class);
                intent.putExtra("pair_name", name.getText().toString());
                //Toast.makeText(context, name.getText().toString(), Toast.LENGTH_SHORT).show();
                getContext().startActivity(intent);
            }
        });

        return listviewitem;

    }

}
