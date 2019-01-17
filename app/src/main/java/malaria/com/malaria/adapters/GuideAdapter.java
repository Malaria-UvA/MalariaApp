package malaria.com.malaria.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;

import malaria.com.malaria.R;
import malaria.com.malaria.fragments.GuideFragment;
import malaria.com.malaria.interfaces.OnSwipeRightListener;

/**
 * Created by zenbook on 11/02/14.
 */
public class GuideAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;

    public GuideAdapter(FragmentActivity activity) {
        super(activity.getSupportFragmentManager());

        String doneText = activity.getString(R.string.done);
        String understoodText = activity.getString(R.string.understood);

        OnSwipeRightListener listener = (OnSwipeRightListener) activity;

        fragments = new Fragment[]{
                GuideFragment.newInstance(activity.getString(R.string.guide_step_1), R.drawable.guide_1_clean, false, doneText, listener),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_2), R.drawable.guide_2_microscope, false, doneText, listener),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_3), R.drawable.guide_3_battery, false, doneText, listener),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_4), R.drawable.guide_4_blood_smear, false, doneText, listener),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_5), R.drawable.guide_5_focus, false, understoodText, listener),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_6), R.drawable.guide_6_move, false, understoodText, listener),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_7), R.drawable.guide_7_nottouch, false, understoodText, listener),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_8), R.drawable.guide_8_progress, false, understoodText, listener),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_9), R.drawable.guide_9_results, false, understoodText, listener),
                GuideFragment.newInstance("", R.drawable.guide_mosquito, true, null, listener)
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
