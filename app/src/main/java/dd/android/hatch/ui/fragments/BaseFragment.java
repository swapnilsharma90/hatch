package dd.android.hatch.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import dd.android.hatch.R;
import dd.android.hatch.entities.model.Thumbnail;
import dd.android.hatch.utils.WikiAppUtils;

/**
 * Created by swapsharma on 4/7/16.
 */
public class BaseFragment extends Fragment {
    private BottomSheetDialog mBottomSheetDialog;

    public void showErrorToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    //unused
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return context != null && activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void updateProgressBar(boolean shouldShow) {
        ProgressBar progressView = (ProgressBar) getActivity().findViewById(R.id.progressBar);
        if (null != progressView) {
            if (shouldShow) {
                progressView.setVisibility(View.VISIBLE);
            } else {
                progressView.setVisibility(View.GONE);
            }
        }
    }

    @SuppressLint("InflateParams")
    protected void showBottomSheetDialog(Thumbnail thumbnail) {
        mBottomSheetDialog = new BottomSheetDialog(getActivity());
        final View view = getActivity().getLayoutInflater().inflate(R.layout.sheet, null);
        ImageView enlargedIv = (ImageView) view.findViewById(R.id.enlargedIv);
        TextView dismissTv = (TextView) view.findViewById(R.id.dismissTv);

        view.startAnimation(WikiAppUtils.getScaleAnimation(1200));
        enlargedIv.startAnimation(WikiAppUtils.getScaleAnimation(600));

        String url = thumbnail.source;
        Picasso.with(getActivity()).load(url).into(enlargedIv);
        dismissTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottomSheetDialog != null) {
                    mBottomSheetDialog.dismiss();
                }
            }
        });
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }
}
