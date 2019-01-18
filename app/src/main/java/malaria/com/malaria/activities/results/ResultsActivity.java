package malaria.com.malaria.activities.results;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.activities.camera.AnalysisCameraActivity;
import malaria.com.malaria.activities.guide.GuideActivity;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.IModelAnalysisService;
import malaria.com.malaria.models.ImageFeature;

public class ResultsActivity extends BaseActivity {

    @BindView(R.id.wbcTV)
    TextView wbcTV;

    @BindView(R.id.parasitesTV)
    TextView parasitesTV;

    @BindView(R.id.fieldsTV)
    TextView fieldsTV;

    @BindView(R.id.resultTV)
    TextView resultTV;

    @BindView(R.id.startOverBtn)
    Button startOverBtn;

    @Inject
    IModelAnalysisService service;


    public ResultsActivity() {
        super(R.layout.activity_results);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageFeature features = service.getTotalAggregation();
        int nWBC = features.getnWhiteBloodCells();
        int nParasites = features.getnParasites();
        int fields = service.getTotalFields();
        int result = service.getParasitePerMicrolitre();

        wbcTV.setText(String.valueOf(nWBC));
        parasitesTV.setText(String.valueOf(nParasites));
        fieldsTV.setText(String.valueOf(fields));
        resultTV.setText(String.valueOf(result));

        startOverBtn.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, GuideActivity.class), GuideActivity.REQUEST_CODES.SECOND_SLIDE);
            finish();
        });
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            startActivity(new Intent(this, GuideActivity.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
