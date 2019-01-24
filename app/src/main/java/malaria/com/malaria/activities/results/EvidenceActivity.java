package malaria.com.malaria.activities.results;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.adapters.EvidenceAdapter;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.interfaces.IModelAnalysisService;

public class EvidenceActivity extends BaseActivity {

    @BindView(R.id.simpleListView)
    ListView listView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.currentItemTV)
    TextView currentItemTV;

    @Inject
    IModelAnalysisService modelAnalysisService;

    public EvidenceActivity() {
        super(R.layout.activity_evidence);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Bitmap> images = modelAnalysisService.getProcessedImages();

        EvidenceAdapter adapter = new EvidenceAdapter(this, images);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) { }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount == 0) return;
                int currentFirstPicture = firstVisibleItem + 1;
                int mostVisibleItem = currentFirstPicture;
                if (visibleItemCount == 3) {
                    mostVisibleItem = currentFirstPicture + 1;
                }
                if (visibleItemCount == 2 && currentFirstPicture == totalItemCount - 1) {
                    mostVisibleItem = totalItemCount;
                }
                String t = getResources().getString(R.string.seen_pictures, mostVisibleItem, totalItemCount);
                currentItemTV.setText(t);
            }
        });
        listView.setAdapter(adapter);

        fab.setOnClickListener(view -> finish());
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }
}
