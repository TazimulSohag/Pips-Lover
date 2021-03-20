package co.banglabs.pips_lover.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import co.banglabs.pips_lover.R;
import co.banglabs.pips_lover.adapter.AgentAdapter;
import co.banglabs.pips_lover.datahandle.AgentBundle;

public class ReferenceMonitorActivity extends AppCompatActivity {
    private ListView agentlistview;
    private DatabaseReference agent_reference;
    List<AgentBundle> agent_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_monitor);

        Toolbar toolbar = findViewById(R.id.toolbar_refmon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        agent_reference =  FirebaseDatabase.getInstance().getReference("Reference");

        agent_list = new ArrayList<>();

        agentlistview = findViewById(R.id.agent_list);

    }

    @Override
    protected void onStart() {
        super.onStart();

        agent_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                agent_list.clear();

                for(DataSnapshot agentSnapshot : snapshot.getChildren()){

                    //Log.d("output", String.valueOf(snapshot.getChildrenCount()));
                    AgentBundle agentBundle = agentSnapshot.getValue(AgentBundle.class);

                    agent_list.add(agentBundle);
                }

                AgentAdapter adapter = new AgentAdapter(ReferenceMonitorActivity.this, agent_list);

                agentlistview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
