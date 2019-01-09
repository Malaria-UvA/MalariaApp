/*
 * Copyright 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package malaria.com.malaria.activities.camera;

import android.os.Bundle;

import malaria.com.malaria.R;
import malaria.com.malaria.activities.base.BaseActivity;
import malaria.com.malaria.constants.IntentKeys;
import malaria.com.malaria.dagger.MalariaComponent;
import malaria.com.malaria.fragments.CameraFragment;

/**
 * Activity displaying a fragment that implements RAW photo captures.
 */
public class CameraActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        String behaviourStr = getIntent().getStringExtra(IntentKeys.ACTIVITY_BEHAVIOUR);
        IntentKeys behaviour = IntentKeys.valueOf(behaviourStr);
        if (behaviour == IntentKeys.CALIBRATION){
            this.setTitle(getString(R.string.calibration));
        }
        Bundle bundle = new Bundle();
        bundle.putString(IntentKeys.ACTIVITY_BEHAVIOUR, behaviourStr);
        if (null == savedInstanceState) {
            CameraFragment fragment = CameraFragment.newInstance();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public void onInject(MalariaComponent applicationComponent) {
        applicationComponent.inject(this);
    }
}