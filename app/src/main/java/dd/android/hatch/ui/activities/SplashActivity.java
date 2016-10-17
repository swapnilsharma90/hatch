package dd.android.hatch.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import java.lang.ref.WeakReference;

import dd.android.hatch.R;


/**
 * Created by swapsharma on 4/7/16.
 */
public class SplashActivity extends BaseActivity {

    private MyHandler splashHandler = new MyHandler(this);
    private static Context context;
    private static WeakReference<SplashActivity> weakSplashActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        splashHandler.postDelayed(sRunnable, 3000);
    }

    protected int layoutResourceForView() {
        return R.layout.activity_splash;
    }

    private static final Runnable sRunnable = new Runnable() {
        @Override
        public void run() {
            weakSplashActivity.get().showWikiSearchActivity(weakSplashActivity.get().context);
        }
    };

    private static class MyHandler extends Handler {
        public MyHandler(SplashActivity activity) {
            weakSplashActivity = new WeakReference<SplashActivity>(activity);
        }
    }
}
