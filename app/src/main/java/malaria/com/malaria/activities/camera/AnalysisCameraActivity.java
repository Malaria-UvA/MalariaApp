package malaria.com.malaria.activities.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.cameraview.CameraView;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.results.ResultsActivity;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.IAnalysisService;
import malaria.com.malaria.interfaces.ICalibrationService;
import malaria.com.malaria.interfaces.IModelAnalysisService;

public class AnalysisCameraActivity extends BaseCameraActivity {

    @Inject
    IAnalysisService analysisService;

    @Inject
    ICalibrationService calibrationService;

    @Inject
    IModelAnalysisService modelAnalysisService;

    @BindView(R.id.numberPictures)
    TextView numberPicturesTV;

    @BindView(R.id.statusTV)
    TextView statusTV;

    @BindView(R.id.statusTV2)
    TextView statusTV2;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private int numberOfPicturesTaken;
    private Timer pictureTimer;

    enum Status {
        PICTURE_TAKEN,
        FOCUSING
    }

    public AnalysisCameraActivity() {
        super(R.layout.activity_camera);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStatus(Status.FOCUSING);
        numberOfPicturesTaken = 0;
        pictureTimer = new Timer();
        pictureTimer.schedule(new PictureTask(), 500, 500);
    }

    @Override
    public void onPictureTaken(CameraView cameraView, Bitmap bitmap) {

        boolean isBlurry = calibrationService.isBlurry(bitmap);
        if (isBlurry) return;
        boolean isAdded = analysisService.addPicture(bitmap);
        if (!isAdded) return;

        // TODO take a look to the concurrency of this methods and the used structures
        new Thread(new ModelTask()).run();

        numberOfPicturesTaken++;
        numberPicturesTV.setText(String.valueOf(numberOfPicturesTaken));
        changeStatus(Status.PICTURE_TAKEN);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> changeStatus(Status.FOCUSING));
            }
        }, 1000);
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    private void changeStatus(Status status) {
        switch (status) {
            case PICTURE_TAKEN:
                statusTV.setText(R.string.picture_taken);
                statusTV2.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                break;
            case FOCUSING:
                statusTV.setText(R.string.focusing);
                statusTV2.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                break;
        }
    }

    private class PictureTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(() -> mCameraView.takePicture());
        }
    }

    private class ModelTask extends TimerTask {

        @Override
        public void run() {
            Bitmap bitmap = analysisService.getImageFromBuffer();
            if (bitmap == null) return;
            modelAnalysisService.processImage(bitmap);
            boolean stop = modelAnalysisService.checkStopCondition();
            if (stop) {
                pictureTimer.cancel();
                pictureTimer.purge();
                runOnUiThread(() -> startActivity(new Intent(AnalysisCameraActivity.this, ResultsActivity.class)));
            }
        }
    }
}
