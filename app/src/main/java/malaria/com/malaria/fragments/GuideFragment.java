package malaria.com.malaria.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.camera.AnalysisCameraActivity;
import malaria.com.malaria.activities.guide.GuideActivity;
import malaria.com.malaria.constants.SwipeDirection;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.OnSwipeRightListener;

/**
 * Created by zenbook on 11/02/14.
 */
public class GuideFragment extends BaseFragmentV4 implements View.OnClickListener {
    @BindView(R.id.titleTxt)
    TextView titleTxt;
    @BindView(R.id.guideTxt)
    TextView textView;
    @BindView(R.id.imageView)
    ImageView image;
    @BindView(R.id.exitBtn)
    Button exitBtn;
    @BindView(R.id.doneOrUnderstoodBtn)
    Button doneOrUnderstoodBtn;
    private String content;
    private String doneOrUnderstoodBtnText;
    private int imageref;
    private boolean firstFragment, lastFragment;
    private OnSwipeRightListener listener;

    public GuideFragment() {
        super(R.layout.fragment_guide);
    }

    public static GuideFragment newInstance(String content, int imageref, boolean firstFragment, boolean lastFragment, String textButton) {
        GuideFragment fragment = new GuideFragment();

        fragment.content = content;
        fragment.imageref = imageref;
        fragment.firstFragment = firstFragment;
        fragment.lastFragment = lastFragment;
        fragment.doneOrUnderstoodBtnText = textButton;

        return fragment;
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        listener = (OnSwipeRightListener) getActivity();
        exitBtn.setOnClickListener(this);
        doneOrUnderstoodBtn.setOnClickListener(this);

        //image.getLayoutParams().height = (int) (0.6f* Utils.heightScreen);
        textView.setText(content);
        image.setImageResource(imageref);
        doneOrUnderstoodBtn.setText(doneOrUnderstoodBtnText);

        if (!firstFragment && !lastFragment) {
            titleTxt.setVisibility(View.INVISIBLE);
        }

        if (lastFragment) {
            exitBtn.setVisibility(View.VISIBLE);
            doneOrUnderstoodBtn.setVisibility(View.INVISIBLE);
            setAllowedSwipeDirection(SwipeDirection.all);
        } else {
            exitBtn.setVisibility(View.INVISIBLE);
            doneOrUnderstoodBtn.setVisibility(View.VISIBLE);
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exitBtn:
                GuideActivity activity = (GuideActivity) getActivity();
                startActivity(new Intent(activity, AnalysisCameraActivity.class));
                if (activity != null) {
                    activity.finish();
                }
                break;
            case R.id.doneOrUnderstoodBtn:
                listener.swipeRight();
                break;
        }
    }

    private void setAllowedSwipeDirection(SwipeDirection direction) {
        ((GuideActivity) listener).setAllowedSwipeDirection(direction);
    }
}
