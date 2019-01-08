package malaria.com.malaria.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;

import malaria.com.malaria.R;
import malaria.com.malaria.fragments.GuideFragment;

/**
 * Created by zenbook on 11/02/14.
 */
public class GuideAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;

    public GuideAdapter(FragmentActivity activity) {
        super(activity.getSupportFragmentManager());
        fragments = new Fragment[] {
                GuideFragment.newInstance(activity.getString(R.string.guide_step_1), R.drawable.guide_1_clean, false),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_2), R.drawable.guide_2_microscope, false),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_3), R.drawable.guide_3_battery, false),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_4), R.drawable.guide_4_blood_smear, false),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_5), R.drawable.guide_5_focus, false),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_6), R.drawable.guide_6_move, false),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_7), R.drawable.guide_7_nottouch, false),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_8), R.drawable.guide_8_progress, false),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_9), R.drawable.guide_9_results, false),
                GuideFragment.newInstance("", R.drawable.guide_mosquito, true)
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
