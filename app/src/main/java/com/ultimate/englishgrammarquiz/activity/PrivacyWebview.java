package com.ultimate.englishgrammarquiz.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ultimate.englishgrammarquiz.R;

/**
 * Created by Legandan on 27/02/2021.
 */

public class PrivacyWebview extends AppCompatActivity {


    WebView webView;
    TextView ToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_webview);
        setupToolbar();

       webView = (WebView)findViewById(R.id.web);
       webView.getSettings().setBuiltInZoomControls(true);
       webView.loadUrl("file:///android_asset/privacy/Privacy.htm");



    }

    private void setupToolbar() {
        if (Build.VERSION.SDK_INT <= 22) { getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); }
        Toolbar toolbar = findViewById(R.id.toolbar);
        ToolbarTitle = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ToolbarTitle.setTextColor(getResources().getColor(R.color.white));
        ToolbarTitle.setText("Privacy Policy");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }

}
