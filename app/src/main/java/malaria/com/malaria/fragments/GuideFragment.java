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

import javax.inject.Inject;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.main.MainActivity;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.IMainPreferences;
import malaria.com.malaria.interfaces.OnSwipeRightListener;
import malaria.com.malaria.services.MainPreferences;

/**
 * Created by zenbook on 11/02/14.
 */
public class GuideFragment extends BaseFragmentV4 implements View.OnClickListener {

    private String content;
    private String doneOrUnderstoodBtnText;
    private int imageref;
    private boolean lastFragment;
    private OnSwipeRightListener listener;

    @BindView(R.id.guideTxt)
    TextView textView;

    @BindView(R.id.imageView)
    ImageView image;

    @BindView(R.id.exitBtn)
    Button exitBtn;

    @BindView(R.id.doneOrUnderstoodBtn)
    Button doneOrUnderstoodBtn;

    @Inject()
    IMainPreferences preferences;

    public GuideFragment() {
        super(R.layout.fragment_guide);
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    public static GuideFragment newInstance(String content, int imageref, boolean lastFragment, String textButton, OnSwipeRightListener listener) {
        GuideFragment fragment = new GuideFragment();

        fragment.content = content;
        fragment.imageref = imageref;
        fragment.lastFragment = lastFragment;
        fragment.doneOrUnderstoodBtnText = textButton;
        fragment.listener = listener;

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        exitBtn.setOnClickListener(this);
        doneOrUnderstoodBtn.setOnClickListener(this);

        //image.getLayoutParams().height = (int) (0.6f* Utils.heightScreen);
        textView.setText(content);
        image.setImageResource(imageref);
        doneOrUnderstoodBtn.setText(doneOrUnderstoodBtnText);

        if (lastFragment) {
            exitBtn.setVisibility(View.VISIBLE);
            textView.setVisibility(View.INVISIBLE);
            doneOrUnderstoodBtn.setVisibility(View.INVISIBLE);
        } else {
            exitBtn.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
            doneOrUnderstoodBtn.setVisibility(View.VISIBLE);
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exitBtn:
                boolean firstTime = preferences.getBoolean(MainPreferences.FIRST_TIME_APP, true);
                if (firstTime) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    preferences.putBoolean(MainPreferences.FIRST_TIME_APP, false);
                }
                if (getActivity() != null) {
                    getActivity().finish();
                }
                break;
            case R.id.doneOrUnderstoodBtn:
                listener.onSwipeRight();
                break;
        }
    }
}
