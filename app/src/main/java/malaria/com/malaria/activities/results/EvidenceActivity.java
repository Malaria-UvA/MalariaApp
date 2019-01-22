package malaria.com.malaria.activities.results;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.ListView;

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

        listView.setAdapter(adapter);

        fab.setOnClickListener(view -> finish());
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }
}
