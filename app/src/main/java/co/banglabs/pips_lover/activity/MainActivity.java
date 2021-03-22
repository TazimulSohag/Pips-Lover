package co.banglabs.pips_lover.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.banglabs.pips_lover.AddPairDialog;
import co.banglabs.pips_lover.R;
import co.banglabs.pips_lover.adapter.PairAdapter;
import co.banglabs.pips_lover.datahandle.PairBundle;
import co.banglabs.pips_lover.datahandle.StatisticsBundle;
import de.hdodenhof.circleimageview.BuildConfig;

public class MainActivity extends AppCompatActivity {

    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    private RequestQueue mQueue;
    private final int REQUEST_CODE = 11;
    String currentDateandTime;
    Button add_item;
    ListView pairs;
    DatabaseReference pair_reference, admin_reference, schedule_reference, statistics_reference;
    DataSnapshot admin_snapshot, pair_snapshot, statistics_snapshot;
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

        mQueue = Volley.newRequestQueue(this);
        timer_view = findViewById(R.id.timer);

        admin_reference = FirebaseDatabase.getInstance().getReference("PairList");
        statistics_reference = FirebaseDatabase.getInstance().getReference("Statistics");
        //date_reference = FirebaseDatabase.getInstance().getReference().child("UpdateDate");
        pair_reference = FirebaseDatabase.getInstance().getReference("Pairs").child(currentDateandTime);
        schedule_reference = FirebaseDatabase.getInstance().getReference("scheduler");

        pair_list = new ArrayList<>();

        add_item = findViewById(R.id.new_paer_btn);
        pairs = findViewById(R.id.paer_list);

        add_item.setOnClickListener(v -> {

            AddPairDialog cdd = new AddPairDialog(MainActivity.this);
            cdd.show();
            /*
            AddPairs addPairs = new AddPairs();
            addPairs.show(getSupportFragmentManager(), "example dialog");*/
        });


        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(result -> {

            if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                try {
                    appUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, MainActivity.this, REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }

        });

        //getSupportActionBar().hide();

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navmenu);
        drawerLayout = findViewById(R.id.drawerlayout_id);

        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {

            Intent intent;
            switch (item.getItemId()) {

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
                    intent = new Intent(MainActivity.this, ContactActivity.class);
                    startActivity(intent);

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


                case R.id.share:
                    try {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                        String shareMessage = "";
                        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                        startActivity(Intent.createChooser(shareIntent, "choose one"));
                    } catch (Exception e) {
                        //e.toString();
                    }
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


        //database methods


        admin_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                admin_snapshot = snapshot;

                pair_list.clear();

                for (DataSnapshot pairsnapshot : snapshot.getChildren()) {
                    //Log.d("output", String.valueOf(snapshot.getChildrenCount()));
                    PairBundle pairBundle = pairsnapshot.getValue(PairBundle.class);

                    pair_list.add(pairBundle);
                }

                PairAdapter adapter = new PairAdapter(MainActivity.this, pair_list);

                pairs.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        //Checking weither to update last exchange or not.
        schedule_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                call_statistics();
                call_pair_reference();

                String check = snapshot.getValue(String.class);
                if (check.equals("false")) {
                    Log.d("newurl", "2");
                    //retriving current all pair list
                    derive_PairList(admin_snapshot);
                } else {
                    Log.d("newurl", "scheduler true found ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void call_pair_reference() {
        pair_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pair_snapshot = snapshot;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void call_statistics() {

        statistics_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                statistics_snapshot = snapshot;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (REQUEST_CODE == requestCode) {
            Toast.makeText(this, "Download start", Toast.LENGTH_SHORT).show();
        }

    }


    private void derive_PairList(DataSnapshot snapshot) {


        Log.d("newurl", "starting process");
        try {

            for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                String name = postSnapshot.getKey();

                if (pair_snapshot.hasChild(name) && admin_snapshot.child(name).child("trade_result").getValue(String.class).equals("Waiting")) {
                    Log.d("newurl", "getting api");
                    getandset_PairValue_From_API(name.replace(" ", "_"));
                } else {
                    Log.d("newurl", "else");
                }

                Log.d("newurl", name);
            }
            schedule_reference.setValue("true");

        } catch (Exception e) {
            schedule_reference.setValue("true");
            Log.d("newurl", String.valueOf(e));
        }


    }

    private void getandset_PairValue_From_API(String name) {
        String url = "https://free.currconv.com/api/v7/convert?apiKey=2ec64af2c4a5303dd43f&q=" + name;
        Log.d("newurl", url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            try {

                JSONObject obj = response.getJSONObject("results");
                JSONObject obj2 = obj.getJSONObject(name);

                Double exchangeRate = obj2.getDouble("val");
                String pair_name = name.replace("_", " ");

                admin_reference.child(pair_name).child("open_price").setValue(String.valueOf(exchangeRate));
                pair_reference.child(pair_name).child("open_price").setValue(String.valueOf(exchangeRate));

                updatingStatas(pair_name, exchangeRate);

                Log.d("newurl", name + "= " + String.valueOf(exchangeRate));


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

    private void updatingStatas(String pair_name, Double exchangeRate) {

        Log.d("newurl", pair_name);
        String ssll = admin_snapshot.child(pair_name).child("stop_loss").getValue(String.class);
        String stp1 = admin_snapshot.child(pair_name).child("take_profit_1").getValue(String.class);
        String stp2 = admin_snapshot.child(pair_name).child("take_profit_2").getValue(String.class);
        String signal = admin_snapshot.child(pair_name).child("pair_action").getValue(String.class);
        if (TextUtils.isEmpty(ssll) || TextUtils.isEmpty(stp1) || TextUtils.isEmpty(stp2) || TextUtils.isEmpty(signal)) {

            Log.d("newurl", "blank found");
            //Toast.makeText(this, "blank found", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("newurl", ssll + " " + stp1 + " " + stp2 + " " + signal);
            Double dsl = Double.parseDouble(ssll);
            Double dtp1 = Double.parseDouble(stp1);
            Double dtp2 = Double.parseDouble(stp2);

            String result = "Waiting";
            if (signal.equals("BUY")) {

                if (exchangeRate <= dsl) result = "Stop Loss";
                else if (exchangeRate >= dtp1 && exchangeRate < dtp2) result = "Take Profit 1";
                else if (exchangeRate >= dtp2) result = "Take Profit 2";
            } else if (signal.equals("SELL")) {

                if (exchangeRate >= dsl) result = "Stop Loss";
                else if (exchangeRate <= dtp1 && exchangeRate > dtp2) result = "Take Profit 1";
                else if (exchangeRate <= dtp2) result = "Take Profit 2";
            }
            if (result.equals("Stop Loss") || result.equals("Take Profit 2")) {
                admin_reference.child(pair_name).child("trade_result").setValue("Expired");
                pair_reference.child(pair_name).child("trade_result").setValue("Expired");
            }
            int current_value, history_value;
            StatisticsBundle statisticsBundle = statistics_snapshot.getValue(StatisticsBundle.class);

            if (result.equals("Stop Loss")) {

                current_value = statisticsBundle.getRecent_sl();
                history_value = statisticsBundle.getHistory_sl();
                current_value++;
                history_value++;
                statistics_reference.child("recent_sl").setValue(current_value);
                statistics_reference.child("history_sl").setValue(history_value);
                Log.d("logout", "al");

            } else if (result.equals("Take Profit 1") && !admin_snapshot.child(pair_name).child("pair_statas").getValue(String.class).equals("Take Profit 1")) {

                current_value = statisticsBundle.getRecent_tp1();
                history_value = statisticsBundle.getHistory_tp1();
                Log.d("logout", current_value + " " + history_value);
                current_value++;
                history_value++;
                statistics_reference.child("recent_tp1").setValue(current_value);
                statistics_reference.child("history_tp1").setValue(history_value);
                Log.d("logout", "tp1");

            } else if (result.equals("Take Profit 2")) {

                current_value = statisticsBundle.getRecent_tp2();
                history_value = statisticsBundle.getHistory_tp2();
                current_value++;
                history_value++;
                statistics_reference.child("recent_tp2").setValue(current_value);
                statistics_reference.child("history_tp2").setValue(history_value);
                //reducing value of tp1
                current_value = statisticsBundle.getRecent_tp1();
                history_value = statisticsBundle.getHistory_tp1();
                current_value--;
                history_value--;
                statistics_reference.child("recent_tp1").setValue(current_value);
                statistics_reference.child("history_tp1").setValue(history_value);
                Log.d("logout", "tp2");

            }

            admin_reference.child(pair_name).child("pair_statas").setValue(result);
            pair_reference.child(pair_name).child("pair_statas").setValue(result);
            Log.d("newurl", result);

        }

    }

}