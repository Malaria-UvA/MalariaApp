package malaria.com.malaria.activities.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.activities.input.InputDataActivity;
import malaria.com.malaria.activities.main.MainActivity;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.IMalariaKBSService;
import malaria.com.malaria.models.Microscopist;
import malaria.com.malaria.models.Nurse;
import malaria.com.malaria.models.Patient;

public class TestSettingActivity extends BaseActivity {
    @BindView(R.id.patientsNameEdText)
    EditText patientsNameEdText;

    @BindView(R.id.microscopistsNameEdText)
    EditText microscopistsNameEdText;

    @BindView(R.id.nursesNameEdText)
    EditText nursesNameEdText;

    @BindView(R.id.nursesEmailEdText)
    EditText nursesEmailEdText;

    @BindView(R.id.nextTestBtn)
    Button nextTestBtn;

    @Inject()
    IMalariaKBSService malariaKBSService;

    public TestSettingActivity() {
        super(R.layout.activity_test_setting);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binds();
    }

    @Override
    protected void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    private void binds(){
        this.nextTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestSettingActivity that = TestSettingActivity.this;

                String patientsName = that.patientsNameEdText.getText().toString();
                String microscopistName = that.microscopistsNameEdText.getText().toString();
                String nurseName = that.nursesNameEdText.getText().toString();
                String nurseEmail = that.nursesEmailEdText.getText().toString();

                if(patientsName.isEmpty() || microscopistName.isEmpty() || nurseName.isEmpty() || nurseEmail.isEmpty()){
                    return;
                }
                Nurse nurse = new Nurse();
                nurse.setName(nurseName);
                nurse.setEmail(nurseEmail);

                Patient patient = new Patient();
                patient.setName(patientsName);

                Microscopist microscopist = new Microscopist();
                microscopist.setName(microscopistName);

                malariaKBSService.createTest(nurse, microscopist, patient);

                startActivity(new Intent(that, InputDataActivity.class));
            }
        });
    }
}


