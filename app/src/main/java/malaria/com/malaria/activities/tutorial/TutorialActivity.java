package malaria.com.malaria.activities.tutorial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;

import com.rd.PageIndicatorView;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.adapters.TutorialAdapter;
import malaria.com.malaria.constants.SwipeDirection;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.fragments.GuideFragment;
import malaria.com.malaria.interfaces.OnSwipeRightListener;
import malaria.com.malaria.views.CustomViewPager;

public class TutorialActivity extends BaseActivity implements OnSwipeRightListener {

    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;

    @BindView(R.id.viewPager)
    CustomViewPager viewPager;

    @BindView(R.id.skipBtn)
    Button skipBtn;

    private TutorialAdapter adapter;

    public TutorialActivity() {
        super(R.layout.activity_tutorial);
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
        adapter = new TutorialAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setAllowedSwipeDirection(SwipeDirection.all);
    }

    private void binds() {
        skipBtn.setOnClickListener(v -> {
            // hack: start the activity here doesn't work for some weird reason
            Fragment f = adapter.getItem(viewPager.getCurrentItem());
            if (f instanceof GuideFragment) {
                ((GuideFragment) f).startAnalysisCameraActivity();
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

    public void setAllowedSwipeDirection(SwipeDirection direction) {
        viewPager.setAllowedSwipeDirection(direction);
    }
}
