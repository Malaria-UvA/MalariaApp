package malaria.com.malaria.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;

import malaria.com.malaria.R;
import malaria.com.malaria.fragments.CalibrationFragment;
import malaria.com.malaria.fragments.GuideFragment;
import malaria.com.malaria.interfaces.OnSwipeRightListener;

public class GuideAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;

    public GuideAdapter(FragmentActivity activity) {
        super(activity.getSupportFragmentManager());

        String doneText = activity.getString(R.string.done);
        String understoodText = activity.getString(R.string.understood);

        OnSwipeRightListener listener = (OnSwipeRightListener) activity;

        fragments = new Fragment[]{
                GuideFragment.newInstance(activity.getString(R.string.guide_step_0), R.drawable.guide_mosquito, true, false, understoodText),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_1), R.drawable.guide_1_clean, false, false, doneText),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_2), R.drawable.guide_2_microscope, false, false, doneText),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_3), R.drawable.guide_3_battery, false, false, doneText),
                GuideFragment.newInstance(activity.getString(R.string.guide_step_4), R.drawable.guide_4_blood_smear, false, false, doneText),
                new CalibrationFragment(listener),
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
