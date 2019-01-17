package malaria.com.malaria.activities.guide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rd.PageIndicatorView;

import javax.inject.Inject;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.activities.camera.AnalysisCameraActivity;
import malaria.com.malaria.adapters.GuideAdapter;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.ICalibrationService;
import malaria.com.malaria.interfaces.OnSwipeRightListener;
import malaria.com.malaria.views.CustomViewPager;

public class GuideActivity extends BaseActivity implements OnSwipeRightListener {

    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;

    @BindView(R.id.viewPager)
    CustomViewPager viewPager;

    @BindView(R.id.skipBtn)
    Button skipBtn;

    @Inject
    ICalibrationService calibrationService;

    private GuideAdapter adapter;

    public GuideActivity() {
        super(R.layout.activity_guide);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //pageIndicatorView.setCount(5); // specify total count of indicators
        //pageIndicatorView.setSelection(2);
        initViews();
        binds();
    }

    @SuppressWarnings("ConstantConditions")
    private void initViews() {
        adapter = new GuideAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);

        boolean isCalibrated = !Double.isNaN(calibrationService.getThreshold());
        if (!isCalibrated) {
            skipBtn.setVisibility(View.GONE);
        }
    }

    private void binds() {
        skipBtn.setOnClickListener(v -> startActivity(new Intent(GuideActivity.this, AnalysisCameraActivity.class)));
        //skipBtn.setOnClickListener(v -> viewPager.setCurrentItem(adapter.getCount() - 1));
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    public void onSwipeRight() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }
}
