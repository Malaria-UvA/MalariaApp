package malaria.com.malaria.activities.guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.rd.PageIndicatorView;

import javax.inject.Inject;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.activities.camera.AnalysisCameraActivity;
import malaria.com.malaria.activities.results.ResultsActivity;
import malaria.com.malaria.adapters.GuideAdapter;
import malaria.com.malaria.constants.SwipeDirection;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.fragments.GuideFragment;
import malaria.com.malaria.interfaces.ICalibrationService;
import malaria.com.malaria.interfaces.IMainPreferences;
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

    @Inject
    IMainPreferences mainPreferences;


    private int lastIndexFragment;
    private GuideAdapter adapter;

    public GuideActivity() {
        super(R.layout.activity_guide);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //pageIndicatorView.setCount(5); // specify total count of indicators
        //pageIndicatorView.setSelection(2);
        binds();
        initViews();
    }

    @SuppressWarnings("ConstantConditions")
    private void initViews() {
        adapter = new GuideAdapter(this);
        viewPager.setAdapter(adapter);
        setAllowedSwipeDirection(SwipeDirection.left);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position < lastIndexFragment) {
                    setAllowedSwipeDirection(SwipeDirection.all);
                } else {
                    setAllowedSwipeDirection(SwipeDirection.left);
                }
                boolean firstTime = mainPreferences.getBoolean(IMainPreferences.FIRST_TIME_APP, true);
                if (firstTime) {
                    skipBtn.setVisibility(View.GONE);
                } else {
                    if (position >= 0 && position <= 5) {
                        skipBtn.setVisibility(View.GONE);
                    } else {
                        skipBtn.setVisibility(View.VISIBLE);
                        viewPager.setAllowedSwipeDirection(SwipeDirection.all);
                    }
                }

                if (position == adapter.getCount() - 1) {
                    skipBtn.setVisibility(View.GONE);
                    mainPreferences.putBoolean(IMainPreferences.FIRST_TIME_APP, false);
                }

                lastIndexFragment = Math.max(lastIndexFragment, position);
            }
        });

        boolean isCalibrated = !Double.isNaN(calibrationService.getThreshold());
        if (!isCalibrated) {
            skipBtn.setVisibility(View.GONE);
        }
    }

    private void binds() {
        skipBtn.setOnClickListener(v -> {
            // hack: start the activity here doesn't work for some weird reason
            Fragment f = adapter.getItem(viewPager.getCurrentItem());
            if(f instanceof GuideFragment){
                ((GuideFragment)f).startAnalysisCameraActivity();
            }
        });
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    public void swipeRight() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }

    public void moveToSecondSlide() {
        viewPager.setCurrentItem(1);
    }

    public void setAllowedSwipeDirection(SwipeDirection direction) {
        viewPager.setAllowedSwipeDirection(direction);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (requestCode == REQUEST_CODES.SECOND_SLIDE) {
            moveToSecondSlide();
        }
    }

    public class REQUEST_CODES {
        public static final int SECOND_SLIDE = 1;
    }
}
