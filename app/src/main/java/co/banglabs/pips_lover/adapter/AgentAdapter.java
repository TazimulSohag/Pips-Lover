package co.banglabs.pips_lover.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

import co.banglabs.pips_lover.AgentPayDialogClass;
import co.banglabs.pips_lover.CustomDialogClass;
import co.banglabs.pips_lover.R;
import co.banglabs.pips_lover.datahandle.AgentBundle;

public class AgentAdapter extends ArrayAdapter {

    private Activity context;
    private List<AgentBundle> agentList;

    public AgentAdapter(Activity context, List<AgentBundle> agentList) {
        super(context, R.layout.agent_base_layout, agentList);
        this.context = context;
        this.agentList = agentList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        @SuppressLint({"ViewHolder", "InflateParams"}) View listviewitem = inflater.inflate(R.layout.agent_base_layout, null, true);

        TextView name = listviewitem.findViewById(R.id.holder_name_id);
        TextView token = listviewitem.findViewById(R.id.holder_token_id);
        TextView recent_user = listviewitem.findViewById(R.id.new_member_id);
        TextView total_user = listviewitem.findViewById(R.id.total_member_id);
        Button pay_button = listviewitem.findViewById(R.id.pay_btn_id);


        AgentBundle agentBundle = agentList.get(position);
        name.setText(agentBundle.getAgent_name());
        token.setText(agentBundle.getAgent_token());
        recent_user.setText(agentBundle.getNew_users());
        total_user.setText(agentBundle.getTotal_users());


        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("agentl", "1");
                AgentPayDialogClass apdc=new AgentPayDialogClass(context, agentBundle.getAgent_token(), agentBundle.getAgent_name(), agentBundle.getNew_users());
                apdc.show();

            }
        });
        /*if(agentBundle.getUpdate_time().equals(currentDateandTime)){
            if(agentBundle.getPair_action().equals("BUY")){
                parentv.setBackgroundColor(Color.parseColor("#CBFBD3"));
            }
            else if(agentBundle.getPair_action().equals("SELL")){
                parentv.setBackgroundColor(Color.parseColor("#FBD8E4"));
            }
        }*/

        /*listviewitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProvideSignal.class);
                intent.putExtra("pair_name", name.getText().toString());
                //Toast.makeText(context, name.getText().toString(), Toast.LENGTH_SHORT).show();
                getContext().startActivity(intent);
            }
        });*/

        return listviewitem;

    }

}
