package sinia.com.smartmart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.smartmart.R;
import sinia.com.smartmart.base.BaseFragment;

/**
 * Created by 忧郁的眼神 on 2016/8/10.
 */
public class BuildImageFragment extends BaseFragment {

    @Bind(R.id.webView)
    WebView webView;
    private View rootView;
    private boolean hasInited = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_build_image, null);
        ButterKnife.bind(this, rootView);
        initImg("http://s15.sinaimg.cn/mw690/003vNYwKgy6EdhlrawKae&690");
        return rootView;
    }

    public static BuildImageFragment newInstance() {
        BuildImageFragment fragment = new BuildImageFragment();
        return fragment;
    }

    public void initImg(String imgUrl) {
        if (null != webView && !hasInited) {
            hasInited = true;
            // 自适应屏幕
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.loadUrl(imgUrl);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
