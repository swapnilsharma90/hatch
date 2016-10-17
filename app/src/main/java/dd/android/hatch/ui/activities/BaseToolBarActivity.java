package dd.android.hatch.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import dd.android.hatch.R;


/**
 * Created by swapsharma on 4/7/16.
 */
public abstract class BaseToolBarActivity extends BaseActivity {

    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Set the toolbar.
        setSupportActionBar(toolbar);

    }

}
