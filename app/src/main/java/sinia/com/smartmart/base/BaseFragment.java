package sinia.com.smartmart.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import sinia.com.smartmart.R;
import sinia.com.smartmart.utils.StringUtil;
import sinia.com.smartmart.view.loadingview.LoadingView;


/**
 * Created by 忧郁的眼神 on 2016/7/29.
 */
public class BaseFragment extends Fragment {
    private Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int id) {
        showToast(id + "");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private View mDialogContentView;
    private LoadingView mLoadingView;

    public void showLoad(String text) {
        dialog = new Dialog(getActivity(), R.style.custom_dialog);
        mDialogContentView = LayoutInflater.from(getActivity()).inflate(
                R.layout.layout_loading_dialog, null);
        mLoadingView = (LoadingView) mDialogContentView
                .findViewById(R.id.loadView);
        if (StringUtil.isEmpty(text)) {
            mLoadingView.setLoadingText("加载中...");
        } else {
            mLoadingView.setLoadingText(text);
        }
        Display d = getActivity().getWindowManager().getDefaultDisplay();
        dialog.show();
        dialog.setContentView(mDialogContentView, new ViewGroup.LayoutParams((int) (d.getWidth() * 0.5),
                (int) (d.getHeight() * 0.3)));
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setCanceledOnTouchOutside(false);
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
