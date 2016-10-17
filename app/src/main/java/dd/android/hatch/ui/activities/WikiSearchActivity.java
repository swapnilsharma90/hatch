package dd.android.hatch.ui.activities;

import android.os.Bundle;

import dd.android.hatch.R;
import dd.android.hatch.ui.fragments.WikiSearchFragment;


/**
 * Created by swapsharma on 4/7/16.
 */
public class WikiSearchActivity extends BaseToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction().addToBackStack(null)
                    .add(R.id.content_frame, WikiSearchFragment.newInstance())
                    .commit();
        }
    }

    protected int layoutResourceForView() {
        return R.layout.activity_search;
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }


}
