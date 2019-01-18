package malaria.com.malaria.activities.camera;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.cameraview.CameraView;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.guide.GuideActivity;
import malaria.com.malaria.activities.results.ResultsActivity;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.fragments.GuideFragment;
import malaria.com.malaria.interfaces.IAnalysisService;
import malaria.com.malaria.interfaces.ICalibrationService;
import malaria.com.malaria.interfaces.IModelAnalysisService;
import malaria.com.malaria.interfaces.OnPictureTakenListener;

public class AnalysisCameraActivity extends BaseCameraActivity implements OnPictureTakenListener {
    private static final long DELAY_PICTURE_MS = 3000L;
    private static final long PERIOD_PICTURE_MS = 3000L;

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
    private final Timer pictureTimer = new Timer();

    private boolean isDialogOpened;

    public AnalysisCameraActivity() {
        super(R.layout.activity_camera);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStatus(Status.FOCUSING);
        numberOfPicturesTaken = 0;
        numberPicturesTV.setText(String.valueOf(numberOfPicturesTaken));
        analysisService.initialize();
        pictureTimer.schedule(new PictureTask(), DELAY_PICTURE_MS, PERIOD_PICTURE_MS);
    }

    @Override
    public void onPictureTaken(CameraView cameraView, Bitmap bitmap) {
        //TODO resize images to 300x300 or 640x640. Depending on the model used
        new ModelTask(this).execute(bitmap);
    }

    @Override
    public Activity getActivity() {
        return this;
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
        numberPicturesTV.setText(String.valueOf(numberOfPicturesTaken));
        changeStatus(AnalysisCameraActivity.Status.PICTURE_TAKEN);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> changeStatus(AnalysisCameraActivity.Status.FOCUSING));
            }
        }, 2000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            this.isDialogOpened = true;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> {
                        startActivity(new Intent(this, GuideActivity.class));
                        AnalysisCameraActivity.this.finish();
                    })
                    .setNegativeButton("No", (dialog, id) -> {
                        dialog.cancel();
                        isDialogOpened = false;
                    }).show();
        }
        return super.onKeyDown(keyCode, event);
    }

    enum Status {
        PICTURE_TAKEN,
        FOCUSING
    }

    private static class ModelTask extends AsyncTask<Bitmap, Void, Boolean> {
        private WeakReference<AnalysisCameraActivity> weakAct;

        ModelTask(AnalysisCameraActivity weakReference) {
            this.weakAct = new WeakReference<>(weakReference);
        }

        @Override
        protected Boolean doInBackground(Bitmap... bitmaps) {
            AnalysisCameraActivity act = weakAct.get();
            if (act != null) {
                Bitmap bitmap = bitmaps[0];
                boolean isBlurry = act.calibrationService.isBlurry(bitmap);
                if (isBlurry) return false;
                boolean isTaken = act.analysisService.isPictureAlreadyTaken(bitmap);
                if (isTaken) return false;

                act.numberOfPicturesTaken += 1;
                act.runOnUiThread(act::refreshPictureTaken);

                act.modelAnalysisService.processImage(bitmap);
                return act.modelAnalysisService.checkStopCondition();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean stop) {
            AnalysisCameraActivity act = weakAct.get();
            if (stop && act != null) {
                act.pictureTimer.cancel();
                act.startActivity(new Intent(act, ResultsActivity.class));
                act.finish();
            }
        }
    }

    private class PictureTask extends TimerTask {

        @Override
        public void run() {
            if (!AnalysisCameraActivity.this.isFinishing() && !isDialogOpened) {
                runOnUiThread(cameraService::takePicture);
            }
        }
    }
}
