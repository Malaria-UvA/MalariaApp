package malaria.com.malaria.activities.guide;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.rd.PageIndicatorView;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.adapters.GuideAdapter;
import malaria.com.malaria.dagger.MalariaComponent;

public class GuideActivity extends BaseActivity {

    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

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
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }
}
