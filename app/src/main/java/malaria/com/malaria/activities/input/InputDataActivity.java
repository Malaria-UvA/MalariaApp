package malaria.com.malaria.activities.input;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.activities.results.ResultsActivity;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.errors.InvalidFeatureValuesException;
import malaria.com.malaria.interfaces.IMalariaKBSService;
import malaria.com.malaria.models.Analysis;
import malaria.com.malaria.models.Features;
import malaria.com.malaria.models.ThickFeatures;
import malaria.com.malaria.models.ThinFeatures;

public class InputDataActivity extends BaseActivity {

    @BindView(R.id.numberOfFieldsTxt)
    TextView numberOfFieldsTxt;

    @BindView(R.id.topTxt)
    TextView topTxt;

    @BindView(R.id.bottomTxt)
    TextView bottomTxt;

    @BindView(R.id.errorTxt)
    TextView errorTxt;

    @BindView(R.id.editTextTop)
    EditText editTextTop;

    @BindView(R.id.editTextBottom)
    EditText editTextBottom;

    @BindView(R.id.clearBtn)
    Button clearBtn;

    @BindView(R.id.cancelBtn)
    Button cancelBtn;

    @BindView(R.id.nextBtn)
    Button nextBtn;

    @Inject()
    IMalariaKBSService malariaKBSService;

    public InputDataActivity() {
        super(R.layout.activity_input_data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setError("");
        this.binds();
        setNumberOfFields(malariaKBSService.getAnalysis().getNumberOfFeatures());
    }

    @Override
    protected void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    private void binds() {
        this.clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputDataActivity.this.clearFields();
            }
        });
        this.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        this.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputDataActivity that = InputDataActivity.this;
                Analysis analysis = that.malariaKBSService.getAnalysis();
                setError("");
                Features f;
                int top, bottom;
                try {
                    String topText = that.editTextTop.getText().toString();
                    String bottomText = that.editTextBottom.getText().toString();

                    top = Integer.parseInt(topText);
                    bottom = Integer.parseInt(bottomText);

                } catch (Exception e) {
                    setError("One of the input fields is empty");
                    return;
                }
                if (analysis.getType() == Analysis.TypeEnum.THICK) {
                    f = new ThickFeatures(top, bottom);
                } else {
                    try{
                        f = new ThinFeatures(top, bottom);
                    }catch (InvalidFeatureValuesException ex){
                        setError(ex.getMessage());
                        return;
                    }

                }
                analysis.addFeature(f);
                that.setNumberOfFields(analysis.getNumberOfFeatures());

                if (analysis.stopConditionMet()) {

                    if (analysis.getType() == Analysis.TypeEnum.THICK) {
                        boolean analysisValid = analysis.thickAnalysisValid();
                        if (!analysisValid) { // change to thin analysis
                            changeToThinAnalysis(analysis);
                            return;
                        }
                    }

                    startActivity(new Intent(that, ResultsActivity.class));
                    finish();
                    return;
                }
                that.clearFields();


            }
        });
    }
    private void setNumberOfFields(int n){
        this.numberOfFieldsTxt.setText(String.format("%s %s ", getString(R.string.number_of_fields), n));
    }
    private void clearFields() {
        this.editTextTop.setText("");
        this.editTextBottom.setText("");
    }

    private void setError(String txt){
        this.errorTxt.setText(txt);
    }
    private void changeToThinAnalysis(Analysis analysis){
        analysis.setType(Analysis.TypeEnum.THIN);
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().setTitle(R.string.thinAnalysis);
        }
        this.topTxt.setText(getString(R.string.red_blood_cells));
        this.bottomTxt.setText(getString(R.string.inf_red_blood_cells));
        this.setNumberOfFields(analysis.getNumberOfFeatures());
        this.clearFields();

        new AlertDialog.Builder(this)
                .setTitle("Thick film analysis is not valid")
                .setMessage("A thin film analysis has to be performed instead due to the number of parasites detected.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
