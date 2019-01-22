package malaria.com.malaria.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;

import malaria.com.malaria.R;
import malaria.com.malaria.fragments.CalibrationFragment;
import malaria.com.malaria.fragments.GuideFragment;
import malaria.com.malaria.interfaces.OnSwipeRightListener;

public class TutorialAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;

    public TutorialAdapter(FragmentActivity activity) {
        super(activity.getSupportFragmentManager());

        String understoodText = activity.getString(R.string.understood);

        fragments = new Fragment[]{
                GuideFragment.newInstance(activity.getString(R.string.guide_step_5), R.drawable.guide_5_focus, false, false, understoodText),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_6), R.drawable.guide_6_move, false, false, understoodText),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_7), R.drawable.guide_7_nottouch, false, false, understoodText),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_8), R.drawable.guide_8_progress, false, false, understoodText),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_9), R.drawable.guide_9_results, false, false, understoodText),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_10), R.drawable.guide_mosquito, false, true, null)
        };
    }

    @Override
    public Fragment getItem(int i) {
        return fragments[i];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }


}
