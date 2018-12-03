package malaria.com.malaria.activities.results;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.IMalariaKBSService;
import malaria.com.malaria.mail.GMailSender;
import malaria.com.malaria.models.Analysis;
import malaria.com.malaria.models.Features;
import malaria.com.malaria.models.Result;
import malaria.com.malaria.models.Test;
import malaria.com.malaria.models.ThickFeatures;
import malaria.com.malaria.models.ThinFeatures;

public class ResultsActivity extends BaseActivity {
    @BindView(R.id.chart)
    LineChart chart;

    @BindView(R.id.calculationTxt)
    TextView calculationTxt;

    @BindView(R.id.closeBtn)
    Button closeBtn;

    @BindView(R.id.emailResultsBtn)
    Button emailResultsBtn;

    @Inject()
    IMalariaKBSService malariaKBSService;

    public ResultsActivity() {
        super(R.layout.activity_results);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.drawLineChart();
        this.binds();

        malariaKBSService.getAnalysis().calculateResult();
        Result r = malariaKBSService.getResult();
        calculationTxt.setText(String.format("%s parasites/\u00B5L", r.getParasites_per_microlitre()));
    }

    private void binds() {
        this.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        this.emailResultsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 new SendEmailTask().execute(malariaKBSService.getTest());
            }
        });
    }

    private void drawLineChart() {
        Analysis analysis = malariaKBSService.getAnalysis();

        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<Entry> entries2 = new ArrayList<>();

        int i = 0;
        for (Features f : analysis.getFeaturesList()) {
            float y, y2;
            if(analysis.getType() == Analysis.TypeEnum.THICK){
                ThickFeatures tf = (ThickFeatures) f;
                y = tf.getN_parasites();
                y2 = tf.getN_whiteBloodCells();
            }else{
                ThinFeatures tf = (ThinFeatures) f;
                y = tf.getN_redBloodCells();
                y2 = tf.getN_infected_rbc();
            }

            entries.add(new Entry(i, y));
            entries2.add(new Entry(i, y2));
            i++;
        }
        String ds1Title, ds2Title;
        if(analysis.getType() == Analysis.TypeEnum.THICK){
            ds1Title = "Number of parasites";
            ds2Title = "Number of white blood cells";
        }else{
            ds1Title = "Number of red blood cells";
            ds2Title = "Number of infected red blood cells";
        }


        LineDataSet dataSet = new LineDataSet(entries, ds1Title);
        LineDataSet dataSet2 = new LineDataSet(entries2, ds2Title);

        dataSet.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        dataSet.setValueTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        dataSet2.setColor(ContextCompat.getColor(this, R.color.colorAccent));
        dataSet2.setValueTextColor(ContextCompat.getColor(this, R.color.colorAccentDark));

        //****
        // Controlling X axis
        XAxis xAxis = chart.getXAxis();
        // Set the xAxis position to bottom. Default is top
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1

        //***
        // Controlling right side of y axis
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        //***
        // Controlling left side of y axis
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setGranularity(1f);

        chart.getDescription().setEnabled(false);

        // Setting Data
        LineData data = new LineData(dataSet);
        data.addDataSet(dataSet2);

        chart.setData(data);
        chart.animateX(2500);
        //refresh
        chart.invalidate();
    }

    @Override
    protected void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    private class SendEmailTask extends AsyncTask<Test, Void, Void> {

        @Override
        protected Void doInBackground(Test... tests) {

            Test test = tests[0];
            Result r = test.getAnalysis().getResult();

            try {
                String content = getString(R.string.email_content);
                content = String.format(content,
                        test.getNurse().getName(),
                        test.getPatient().getName(),
                        r.getParasites_per_microlitre(),
                        test.getMicroscopist().getName());

                GMailSender sender = new GMailSender("microscopistmalaria@gmail.com", "xxxxxxxxx"); //TODO: put a local config file
                sender.sendMail("Malaria Diagnosis Results",
                        content,
                        "microscopistmalaria@gmail.com",
                        test.getNurse().getEmail());
                //System.err.print("EMAIL SENT");


            } catch (Exception e) {
                e.printStackTrace();
                //Toast.makeText(ResultsActivity.this, "The email couldn't be sent", Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    }
}
