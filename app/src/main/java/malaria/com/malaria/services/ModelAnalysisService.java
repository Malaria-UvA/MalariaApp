package malaria.com.malaria.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import malaria.com.malaria.Classifier;
import malaria.com.malaria.ImageUtils;
import malaria.com.malaria.TFLiteObjectDetectionAPIModel;
import malaria.com.malaria.interfaces.IModelAnalysisService;
import malaria.com.malaria.models.ImageFeature;

public class ModelAnalysisService implements IModelAnalysisService {

    private static final String TF_OD_API_MODEL_FILE = "model.tflite";
    private static final String TF_OD_API_LABELS_FILE = "labels_list.txt";
    private static final int TF_OD_API_INPUT_SIZE = 300;
    private static final boolean TF_OD_API_IS_QUANTIZED = false;

    private static final String WBC = "red";
    private static final String PARASITE = "mal";

    private ArrayList<ImageFeature> features;

    private Classifier detector;

    @Inject
    public ModelAnalysisService(Context context) {

        try {
            detector =
                    TFLiteObjectDetectionAPIModel.create(
                            context.getAssets(),
                            TF_OD_API_MODEL_FILE,
                            TF_OD_API_LABELS_FILE,
                            TF_OD_API_INPUT_SIZE,
                            TF_OD_API_IS_QUANTIZED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialize();
    }

    @Override
    public void initialize() {
        this.features = new ArrayList<>();
    }

    @Override
    public ImageFeature processImage(Bitmap image) {
        // TODO Call model.predict() to obtain number of parasites and WBC
        //ImageUtils.saveBitmap(image, "image_before_changing_size.png");
        Bitmap croppedImage = resizeBitmap(image);
        //ImageUtils.saveBitmap(croppedImage, "image_after_changing_size.png");
        final List<Classifier.Recognition> results = detector.recognizeImage(croppedImage);
        //saveImageWithRect(croppedImage, results);

        int nWBC = 0;
        int nParasites = 0;
        for (Classifier.Recognition recognition: results) {
            if (recognition.getTitle().equals(WBC)) {
                nWBC++;
            } else if (recognition.getTitle().equals(PARASITE)) {
                nParasites++;
            }
        }

        ImageFeature imageFeature = new ImageFeature(nWBC, nParasites);
        features.add(imageFeature);
        return imageFeature;
    }

    private void saveImageWithRect(Bitmap image, List<Classifier.Recognition> results) {
        // warning: if this method is called, the permission of external storage must be added
        // on AndroidManifest
        Canvas c = new Canvas(image);
        Paint wbcPaint = new Paint();
        wbcPaint.setColor(Color.BLUE);
        wbcPaint.setStyle(Paint.Style.STROKE);

        Paint parasitePaint = new Paint();
        parasitePaint.setColor(Color.GREEN);
        parasitePaint.setStyle(Paint.Style.STROKE);

        for (Classifier.Recognition recognition: results) {
            if (recognition.getTitle().equals(WBC)) {
                c.drawRect(recognition.getLocation(), wbcPaint);
            } else if (recognition.getTitle().equals(PARASITE)) {
                c.drawRect(recognition.getLocation(), parasitePaint);
            }

        }
        ImageUtils.saveBitmap(image, String.format("image_after_recognition_%d.png", features.size()));
    }

    private Bitmap resizeBitmap(Bitmap bitmap) {
        Bitmap croppedBitmap = Bitmap.createBitmap(TF_OD_API_INPUT_SIZE, TF_OD_API_INPUT_SIZE, Bitmap.Config.ARGB_8888);

        Matrix frameToCropTransform =
                ImageUtils.getTransformationMatrix(
                        bitmap.getWidth(), bitmap.getHeight(),
                        TF_OD_API_INPUT_SIZE, TF_OD_API_INPUT_SIZE,
                        90, false);

        Matrix cropToFrameTransform = new Matrix();
        frameToCropTransform.invert(cropToFrameTransform);

        final Canvas canvas = new Canvas(croppedBitmap);
        canvas.drawBitmap(bitmap, frameToCropTransform, null);

        return croppedBitmap;
    }

    @Override
    public boolean checkStopCondition() {
        ImageFeature aggregation = getTotalAggregation();
        // TODO change the stop conditions
        return aggregation.getnParasites() >= 12;
        /*return aggregation.getnParasites() >= 100 && aggregation.getnWhiteBloodCells() >= 200 ||
                aggregation.getnParasites() <= 99 && aggregation.getnWhiteBloodCells() >= 500;*/
    }

    @Override
    public ImageFeature getTotalAggregation() {
        int nParasitesTotal = 0;
        int nWBC = 0;
        for (ImageFeature f : features) {
            nParasitesTotal += f.getnParasites();
            nWBC += f.getnWhiteBloodCells();
        }
        return new ImageFeature(nWBC, nParasitesTotal);
    }

    @Override
    public int getTotalFields() {
        return features.size();
    }

    @Override
    public int getParasitePerMicrolitre() {
        ImageFeature aggregation = getTotalAggregation();
        return (int) Math.ceil(8000f * aggregation.getnParasites() / aggregation.getnWhiteBloodCells());
    }
}
