package co.banglabs.pips_lover.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;

import com.google.android.play.core.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.banglabs.pips_lover.AddPairDialog;
import co.banglabs.pips_lover.AddPairs;
import co.banglabs.pips_lover.CustomDialogClass;
import co.banglabs.pips_lover.R;
import co.banglabs.pips_lover.datahandle.PairBundle;
import co.banglabs.pips_lover.adapter.PairAdapter;

public class MainActivity extends AppCompatActivity {

    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    private final int REQUEST_CODE=11;
    String currentDateandTime;
    Button add_item;
    ListView pairs;
    DatabaseReference pair_reference, admin_reference;
    List<PairBundle> pair_list;
    TextView timer_view;
    /*SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);*/

    //String defaultValue = getResources().getInteger(R.integer.saved_high_score_default_key);
    /*String log_pass = sharedPref.getString(getString(R.string.login_password), " ");
    String log_name = sharedPref.getString(getString(R.string.login_email), " ");*/

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        currentDateandTime = sdf.format(new Date());


        timer_view = findViewById(R.id.timer);

        admin_reference = FirebaseDatabase.getInstance().getReference("PairList");
        //date_reference = FirebaseDatabase.getInstance().getReference().child("UpdateDate");
        pair_reference = FirebaseDatabase.getInstance().getReference("Pairs").child(currentDateandTime);
        pair_list = new ArrayList<>();

        add_item = findViewById(R.id.new_paer_btn);
        pairs = findViewById(R.id.paer_list);

        add_item.setOnClickListener(v -> {

            AddPairDialog cdd=new AddPairDialog(MainActivity.this);
            cdd.show();
            /*
            AddPairs addPairs = new AddPairs();
            addPairs.show(getSupportFragmentManager(), "example dialog");*/
        });


        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(result -> {

            if(result.updateAvailability()== UpdateAvailability.UPDATE_AVAILABLE
            && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ){
                try {
                    appUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, MainActivity.this, REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }

        });

        //getSupportActionBar().hide();

        toolbar=findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.navmenu);
        drawerLayout=findViewById(R.id.drawerlayout_id);

        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {

            Intent intent;
            switch (item.getItemId()){

                case R.id.profile:
                    intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    //Toast.makeText(MainActivity.this, "About", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.privecy:
                    intent = new Intent(MainActivity.this, PrivacyPolicy.class);
                    startActivity(intent);
                    //Toast.makeText(MainActivity.this, "About", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.risk_ratio:
                    intent = new Intent(MainActivity.this, RiskRatioActivity.class);
                    startActivity(intent);
                    //Toast.makeText(MainActivity.this, "About", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.contact:
                    //Toast.makeText(MainActivity.this, "Dashboard", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.chart:
                    //Toast.makeText(MainActivity.this, "Chart", Toast.LENGTH_SHORT).show();
                    intent = new Intent(MainActivity.this, ChartActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.referd_monitor:
                    //Toast.makeText(MainActivity.this, "Chart", Toast.LENGTH_SHORT).show();
                    intent = new Intent(MainActivity.this, ReferenceMonitorActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.logout:
                    SharedPreferences preferences = getSharedPreferences(String.valueOf(R.string.login_info), MODE_PRIVATE);
                    preferences.edit().clear().apply();
                    intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                    finish();
                    //drawerLayout.closeDrawer(GravityCompat.START);
                    break;
            }

            return true;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(REQUEST_CODE == requestCode){
            Toast.makeText(this, "Download start", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onStart() {
        super.onStart();


        admin_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pair_list.clear();

                //String database_Date = snapshot.getChildren().
                //Log.d("output", String.valueOf(snapshot.child("UpdateDate").getValue()));

                for(DataSnapshot pairsnapshot : snapshot.getChildren()){
                    //Log.d("output", String.valueOf(snapshot.getChildrenCount()));
                    PairBundle pairBundle = pairsnapshot.getValue(PairBundle.class);

                    pair_list.add(pairBundle);
                }

                PairAdapter adapter = new PairAdapter(MainActivity.this, pair_list);

                pairs.setAdapter(adapter);

                /*long length = snapshot.getChildrenCount();
                Log.d("out1", "ok");
                for(int i = 1;i<=length;i++){
                    PairBundle pairBundle = new PairBundle((String) snapshot.child(String.valueOf(i)).getValue());
                    Log.d("out1", pairBundle.getPair_name());
                    pair_list.add(pairBundle);
                }
                PairAdapter adapter = new PairAdapter(MainActivity.this, pair_list);
                pairs.setAdapter(adapter);*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}