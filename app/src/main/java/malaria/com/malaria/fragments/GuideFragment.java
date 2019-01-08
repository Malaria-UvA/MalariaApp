package malaria.com.malaria.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.main.MainActivity;
import malaria.com.malaria.utils.MainPreferences;

/**
 * Created by zenbook on 11/02/14.
 */
public class GuideFragment extends Fragment implements View.OnClickListener {

    private String content;
    private int imageref;
    private boolean lastFragment;

    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.imageView)
    ImageView image;

    @BindView(R.id.exitbutton)
    Button exitButton;

    public GuideFragment() {}

    public static GuideFragment newInstance(String content, int imageref, boolean lastFragment) {
        GuideFragment fragment = new GuideFragment();

        fragment.content = content;
        fragment.imageref = imageref;
        fragment.lastFragment = lastFragment;

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_guide, container, false);
        ButterKnife.bind(this, v);

        exitButton.setOnClickListener(this);

        //image.getLayoutParams().height = (int) (0.6f* Utils.heightScreen);
        textView.setText(content);
        image.setImageResource(imageref);

        if(lastFragment) {
            exitButton.setVisibility(View.VISIBLE);
            textView.setVisibility(View.INVISIBLE);
        }else{
            exitButton.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        MainPreferences preferences = new MainPreferences(getActivity());
        boolean firstTime = preferences.getBoolean(MainPreferences.FIRST_TIME_APP, true);
        if(firstTime) {
            startActivity(new Intent(getActivity(), MainActivity.class));
            preferences.putBoolean(MainPreferences.FIRST_TIME_APP, false);
        }
        getActivity().finish();
    }
}
