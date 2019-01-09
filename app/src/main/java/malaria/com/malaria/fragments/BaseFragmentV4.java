package malaria.com.malaria.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import malaria.com.malaria.dagger.DependencyInjector;
import malaria.com.malaria.interfaces.Injector;

public abstract class BaseFragmentV4 extends Fragment implements Injector {

    protected Context context;
    private Integer layoutId;

    public BaseFragmentV4() {
    }

    public BaseFragmentV4(int layoutId) {
        this.layoutId = layoutId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(this.layoutId, container, false);
        ButterKnife.bind(this, v);
        onInject(DependencyInjector.applicationComponent());
        context = getContext();
        return v;
    }
}
