package co.banglabs.pips_lover.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import co.banglabs.pips_lover.R;
import co.banglabs.pips_lover.datahandle.StatisticsBundle;

public class ChartActivity extends AppCompatActivity {

    DatabaseReference statistics_reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Toolbar toolbar = findViewById(R.id.toolbar_chart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        statistics_reference = FirebaseDatabase.getInstance().getReference("Statistics");


        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        //AnyChartView anyChartView2 = findViewById(R.id.any_chart_view2);

        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
        //anyChartView2.setProgressBar(findViewById(R.id.progress_bar));

        Pie pie = AnyChart.pie();

        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {

            @Override
            public void onClick(Event event) {
                //Toast.makeText(ChartActivity.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });


        //anyChartView2.setChart(pie);
        statistics_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StatisticsBundle statisticsBundle = snapshot.getValue(StatisticsBundle.class);


                List<DataEntry> data = new ArrayList<>();
                data.add(new ValueDataEntry("TP 1", statisticsBundle.getRecent_tp1()));
                data.add(new ValueDataEntry("TP 2", statisticsBundle.getRecent_tp2()));
                data.add(new ValueDataEntry("SL", statisticsBundle.getRecent_sl()));
                data.add(new ValueDataEntry("Unhitted Signal", statisticsBundle.getRecent_untouched()));

                pie.data(data);

                pie.title("Signal Accurecy Chart of Last 7 days");

                pie.labels().position("outside");

                pie.legend().title().enabled(true);
                pie.legend().title()
                        .text("Retail channels")
                        .padding(0d, 0d, 10d, 0d);

                pie.legend()
                        .position("center-bottom")
                        .itemsLayout(LegendLayout.HORIZONTAL)
                        .align(Align.CENTER);

                anyChartView.setChart(pie);

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