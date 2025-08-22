package com.ultimate.englishgrammarquiz;  // This should be the first line

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ayoubfletcher.consentsdk.ConsentSDK;
import com.ultimate.englishgrammarquiz.activity.MainActivity;
import com.ultimate.englishgrammarquiz.activity.SettingSectioned;
import com.google.android.gms.ads.AdView;

import com.ultimate.englishgrammarquiz.R;


/**
 * Created by Legandan on 27/02/2021.
 */

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    Button start, settings, share, more;
    AdView adView;
    private TextView ToolbarTitle;
    private Toolbar toolbar;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        init();
        // setupToolbar();

        AdView adView = findViewById(R.id.adView);
        // You have to pass the AdRequest from ConsentSDK.getAdRequest(this) because it handles the right way to load the ad
        adView.loadAd(ConsentSDK.getAdRequest(this));
    }

    private void init() {
        start = findViewById(R.id.buttonStart);
        settings = findViewById(R.id.buttonSettings);
        share = findViewById(R.id.buttonShare);
        more = findViewById(R.id.buttonMore);

        start.setOnClickListener(this);
        settings.setOnClickListener(this);
        share.setOnClickListener(this);
        more.setOnClickListener(this);
    }

    private void setupToolbar() {
        if (Build.VERSION.SDK_INT <= 22) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        toolbar = findViewById(R.id.toolbar);
        ToolbarTitle = findViewById(R.id.toolbar_title);
        ToolbarTitle.setTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        ToolbarTitle.setText(R.string.app_name);
        ActionBar actionBar = getSupportActionBar();
        //   actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setElevation(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonStart:
                Intent start = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(start);
                break;

            case R.id.buttonSettings:
                Intent Settings = new Intent(getApplicationContext(), SettingSectioned.class);
                startActivity(Settings);
                break;

            case R.id.buttonShare:
                shareApp();
                break;

            case R.id.buttonMore:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.mystore))));
                break;
            default:
                break;
        }
    }

    private void shareApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this " + getString(R.string.app_name) + " app at: https://play.google.com/store/apps/details?id=" + this.getPackageName());
        sendIntent.setType("text/plain");
        this.startActivity(sendIntent);
    }

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            //  Toast.makeText(this, getResources().getString(R.string.MsgExit), Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        doExitApp();
        super.onBackPressed();
    }
}
