package malaria.com.malaria.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.cameraview.CameraView;

import javax.inject.Inject;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.ICameraService;
import malaria.com.malaria.interfaces.OnPictureTakenListener;

public abstract class BaseCameraFragment extends BaseFragmentV4 implements OnPictureTakenListener {

    @Inject
    ICameraService cameraService;

    @BindView(R.id.camera)
    CameraView mCameraView;

    public BaseCameraFragment() {
        setUpCameraService();
    }

    @SuppressLint("ValidFragment")
    public BaseCameraFragment(int layoutId) {
        super(layoutId);
    }

    private void setUpCameraService() {
        cameraService.setListener(this);
        cameraService.setUpCameraView(mCameraView);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        setUpCameraService();
        return v;
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        cameraService.checkCameraPermissions();
    }

    @Override
    public void onPause() {
        cameraService.stopCameraView();
        super.onPause();
    }
}
