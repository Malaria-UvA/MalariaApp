package malaria.com.malaria.activities.guide;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.activities.main.MainActivity;
import malaria.com.malaria.activities.test.TestSettingActivity;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.IMalariaKBSService;

public class GuideActivity extends BaseActivity {

    @BindView(R.id.step3_tv)
    TextView step3;

    @BindView(R.id.startBtn)
    Button startBtn;

    @Inject()
    IMalariaKBSService malariaKBSService;

    public GuideActivity() {
        super(R.layout.activity_guide);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        step3.setMovementMethod(LinkMovementMethod.getInstance());

        this.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this, TestSettingActivity.class));

            }
        });
    }

    @Override
    protected void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }
}
