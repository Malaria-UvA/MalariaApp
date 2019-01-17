package malaria.com.malaria.activities.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
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
        refreshPictureTaken();
        analysisService.initialize();
        pictureTimer = new Timer();
        pictureTimer.schedule(new PictureTask(), 2000, 2000);
    }

    @Override
    public void onPictureTaken(CameraView cameraView, Bitmap bitmap) {
        // TODO take a look to the concurrency of this methods and the used structures

        //TODO resize images to 300x300 or 640x640. Depending on the model used
        new ModelTask().execute(bitmap);
    }

    @Override
    protected void onStop() {
        pictureTimer.cancel();
        super.onStop();
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

    private void refreshPictureTaken() {

        runOnUiThread(() -> {
            numberPicturesTV.setText(String.valueOf(numberOfPicturesTaken));
            changeStatus(AnalysisCameraActivity.Status.PICTURE_TAKEN);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> changeStatus(AnalysisCameraActivity.Status.FOCUSING));
                }
            }, 1000);
        });
    }

    private class PictureTask extends TimerTask {

        @Override
        public void run() {
            if (!AnalysisCameraActivity.this.isFinishing()) {
                runOnUiThread(() -> {
                    if (mCameraView != null) mCameraView.takePicture();
                });
            }
        }
    }

    private class ModelTask extends AsyncTask<Bitmap, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Bitmap... bitmaps) {
            Bitmap bitmap = bitmaps[0];
            boolean isBlurry = calibrationService.isBlurry(bitmap);
            if (isBlurry) return false;
            boolean isAdded = analysisService.addPicture(bitmap);
            if (!isAdded) return false;

            numberOfPicturesTaken += 1;
            refreshPictureTaken();

            modelAnalysisService.processImage(bitmap);
            return modelAnalysisService.checkStopCondition();
        }

        @Override
        protected void onPostExecute(Boolean stop) {
            if (stop) {
                pictureTimer.cancel();
                startActivity(new Intent(AnalysisCameraActivity.this, ResultsActivity.class));
                finish();
            }
        }
    }
}
