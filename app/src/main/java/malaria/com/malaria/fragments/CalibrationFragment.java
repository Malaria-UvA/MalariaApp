package malaria.com.malaria.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.cameraview.CameraView;

import javax.inject.Inject;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.ICalibrationService;
import malaria.com.malaria.interfaces.OnSwipeRightListener;

public class CalibrationFragment extends BaseCameraFragment {

    private OnSwipeRightListener listener;
    @BindView(R.id.calibrateBtn)
    Button calibrateBtn;

    @Inject
    ICalibrationService calibrationService;

    public CalibrationFragment() {
    }

    @SuppressLint("ValidFragment")
    public CalibrationFragment(OnSwipeRightListener listener) {
        super(R.layout.fragment_calibration);
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        calibrateBtn.setOnClickListener(view -> cameraService.takePicture());
        return v;
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    @Override
    public void onPictureTaken(CameraView cameraView, Bitmap bitmap) {
        Toast.makeText(getActivity(), R.string.device_calibrated, Toast.LENGTH_SHORT).show();
        calibrationService.calculateAndSaveThreshold(bitmap);
        if(listener != null){
            listener.onSwipeRight();
        }
    }
}
