package malaria.com.malaria.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import malaria.com.malaria.R;

public class EvidenceAdapter extends ArrayAdapter<Bitmap> {

    private Activity activity;

    public EvidenceAdapter(Activity activity, List<Bitmap> bitmapList) {
        super(activity, 0, bitmapList);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.evidence_list_item, null);
        }
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        final Bitmap image = getItem(position);
        final Bitmap finalImage = Bitmap.createScaledBitmap(image, width, width, true);
        final ImageView photo = row.findViewById(R.id.imageView);
        photo.setImageBitmap(finalImage);

        return row;
    }

}
