package com.jnu.student.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.jnu.student.R;
import android.widget.Button;

public class WebViewFragment extends Fragment {
    public static WebView webView;
    public WebViewFragment() {
    }
    public static WebViewFragment newInstance() {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {}
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_webview, container, false);
        webView = rootView.findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true); // 启用JavaScript支持
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:")||url.startsWith("https:")||url.startsWith("ftp")){
                    view.loadUrl(url);
                    return true;
                }
                else if (url.startsWith("scheme://")){  // 处理net::ERR_UNKNOWN_URL_SCHEME
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        }); // 设置WebView客户端
        webView.loadUrl("http://baidu.com");

        Button button_prior_page = rootView.findViewById(R.id.button_prior_page);
        button_prior_page.setOnClickListener(view -> {
            if (webView.canGoBack()) webView.goBack();
        });

        Button button_next_page = rootView.findViewById(R.id.button_next_page);
        button_next_page.setOnClickListener(view -> {
            if (webView.canGoForward()) webView.goForward();
        });

        return rootView;
    }
}