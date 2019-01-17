package malaria.com.malaria.activities.guide;

import android.os.Bundle;

import com.rd.PageIndicatorView;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.adapters.GuideAdapter;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.OnSwipeRightListener;
import malaria.com.malaria.views.CustomViewPager;

public class GuideActivity extends BaseActivity implements OnSwipeRightListener {

    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;

    @BindView(R.id.viewPager)
    CustomViewPager viewPager;

    public GuideActivity() {
        super(R.layout.activity_guide);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //pageIndicatorView.setCount(5); // specify total count of indicators
        //pageIndicatorView.setSelection(2);
        initViews();
    }

    @SuppressWarnings("ConstantConditions")
    private void initViews() {
        GuideAdapter adapter = new GuideAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    public void onSwipeRight() {
        //viewPager.arrowScroll(View.FOCUS_RIGHT);
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }
}
