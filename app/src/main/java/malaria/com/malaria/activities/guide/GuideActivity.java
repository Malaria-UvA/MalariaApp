package malaria.com.malaria.activities.guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.rd.PageIndicatorView;

import javax.inject.Inject;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.adapters.GuideAdapter;
import malaria.com.malaria.constants.SwipeDirection;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.ICalibrationService;
import malaria.com.malaria.interfaces.IMainPreferences;
import malaria.com.malaria.interfaces.OnSwipeRightListener;
import malaria.com.malaria.views.CustomViewPager;

public class GuideActivity extends BaseActivity implements OnSwipeRightListener {

    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;

    @BindView(R.id.viewPager)
    CustomViewPager viewPager;

    @Inject
    ICalibrationService calibrationService;

    @Inject
    IMainPreferences mainPreferences;


    private int lastIndexFragment;

    public GuideActivity() {
        super(R.layout.activity_guide);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @SuppressWarnings("ConstantConditions")
    private void initViews() {
        GuideAdapter adapter = new GuideAdapter(this);
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
                if (!firstTime) {
                    if (!(position >= 0 && position <= 5)) {
                        viewPager.setAllowedSwipeDirection(SwipeDirection.all);
                    }
                }

                if (position == adapter.getCount() - 1) {
                    mainPreferences.putBoolean(IMainPreferences.FIRST_TIME_APP, false);
                }

                lastIndexFragment = Math.max(lastIndexFragment, position);
            }
        });
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    @Override
    public void swipeRight() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }

    public void moveToSecondSlide() {
        viewPager.setCurrentItem(1);
    }

    @Override
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
