package com.prohua.debrower.view;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.prohua.debrower.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 网页界面
 * Created by Deep on 2017/9/19 0019.
 */

public class WebFragment extends SupportFragment {

    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.web_view_back)
    ImageView webViewBack;

    public static WebFragment newInstance() {
        return new WebFragment();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);

        ButterKnife.bind(this, view);

        webView.loadUrl("http://m.baidu.com/");

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });

        webViewBack.setOnClickListener(view1 -> {
            if(webView.canGoBack()) {
                webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                webView.goBack();
            }
        });

        return view;
    }

    /**
     * 处理回退事件
     *
     * @return true
     */
    @Override
    public boolean onBackPressedSupport() {
        if (_mActivity.getSupportFragmentManager().getBackStackEntryCount() > 2) {
            pop();
        } else {
            if (webView.canGoBack()) {
                webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                webView.goBack();
            }
        }
        return true;
    }
}

