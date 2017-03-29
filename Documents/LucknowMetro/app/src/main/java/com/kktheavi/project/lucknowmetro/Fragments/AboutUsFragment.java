package com.kktheavi.project.lucknowmetro.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.kktheavi.project.lucknowmetro.MainActivity;
import com.kktheavi.project.lucknowmetro.R;

/**
 * Created by kktheavi on 3/27/2017.
 */

public class AboutUsFragment extends android.app.Fragment {
    View view;
    WebView webView;
    MainActivity activity;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about_us, container, false);
        activity=(MainActivity)getActivity();
        webView = (WebView) view.findViewById(R.id.webView_about);
//        activity.getWindow().requestFeature(Window.FEATURE_PROGRESS);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        startWebView("http://www.lmrcl.com/about.aspx");
//        anotherWebView();
        return view;
    }

    private void startWebView(String url) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webView.setWebViewClient(new WebViewClient() {

            //Show loader on url load
            public void onLoadResource(WebView view, String url) {


            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }
        });

        // Javascript inabled on webview
        webView.getSettings().setJavaScriptEnabled(true);

        // Other webview options

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);





        //Load url in webview
        webView.loadUrl(url);


    }


}