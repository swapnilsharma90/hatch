package dd.android.hatch.ui.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import dd.android.hatch.R;


/**
 * Created by swapsharma on 4/7/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResourceForView());
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void closeActivity() {
        finish();
    }


    /*****************************************/
    /* methods for subclasses       */

    /*****************************************/
    protected int layoutResourceForView() {
        throw new RuntimeException("Please override layoutResourceForView() and specify layout.");
    }

    protected void showWikiSearchActivity(Context context) {
        Intent splashToSearchIntent = new Intent(context, WikiSearchActivity.class);
        startActivity(splashToSearchIntent);
        finish();
    }

    protected void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
    }

    public void showErrorToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}