package com.ultimate.englishgrammarquiz.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ayoubfletcher.consentsdk.ConsentSDK;
import com.ultimate.englishgrammarquiz.R;

import java.util.ArrayList;
import java.util.List;
import com.ultimate.englishgrammarquiz.R;


/**
 * Created by Legandan on 27/02/2021.
 */

public class SettingSectioned extends AppCompatActivity implements View.OnClickListener{


    private ImageView ic_back;
    Dialog myDialog;
    private TextView messageTxt;
    private ConsentSDK consentSDK = null;
    private TextView txt_no_item,consent_setting_btn;
    private List<String> items = new ArrayList<>();
    private ArrayAdapter adapter;
    TextView ToolbarTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile_light);
        myDialog = new Dialog(this);
        //  loadInterstitial();
    //    loadBanner();
        setupToolbar();




        TextView consent_setting_btn=(TextView)findViewById(R.id.consent_setting_btn);
        LinearLayout privacy=(LinearLayout)findViewById(R.id.layoutprivacy);


        //  back.setOnClickListener(this);


        consent_setting_btn.setOnClickListener(this);
        privacy.setOnClickListener(this);


        ///////////////////////////////// Inside On Creat Method ////////////////////////////


//        // Initialize a dummy banner using the default test banner id provided by google to get the device id from logcat using 'Ads' tag
//        ConsentSDK.initDummyBanner(this);

        // Initialize ConsentSDK
        final ConsentSDK consentSDK = new ConsentSDK.Builder(this)
                //  .addTestDeviceId("5582245367B339176AD783C536320D97") // Add your test device id "Remove addTestDeviceId on production!"
                .addCustomLogTag("ID_LOG") // Add custom tag default: ID_LOG
                .addPrivacyPolicy(getString(R.string.privacyPolicyLink)) // Add your privacy policy url
                .addPublisherId(getString(R.string.Admob_publisher_id)) // Add your admob publisher id
                .build();

        // To check the consent and to move to GDPR after everything is fine :).
        consentSDK.checkConsent(new ConsentSDK.ConsentCallback() {
            @Override
            public void onResult(boolean isRequestLocationInEeaOrUnknown) {
                // goToMain();
            }
        });


        ///////////////////////////////////

        // Initialize Views
        messageTxt = (TextView) findViewById(R.id.message);
        // Initialize ConsentSDK
        initConsentSDK(this);

// Checking the status of the user
        if(ConsentSDK.isUserLocationWithinEea(this)) {
            String choice = ConsentSDK.isConsentPersonalized(this)? "Personalize": "Non-Personalize";
            messageTxt.setText(getString(R.string.user_within_eea_msg) + choice);
            findViewById(R.id.consent_setting_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check Consent SDK
//                    // Request the consent without callback
//                    consentSDK.requestConsent(null);
                    //To get the result of the consent
                    consentSDK.requestConsent(new ConsentSDK.ConsentStatusCallback() {
                        @Override
                        public void onResult(boolean isRequestLocationInEeaOrUnknown, int isConsentPersonalized) {
                            String choice = "";
                            switch (isConsentPersonalized) {
                                case 0:
                                    choice = "Non-Personalize";
                                    break;
                                case 1:
                                    choice = "Personalized";
                                    break;
                                case -1:
                                    choice = "Error accrued";
                            }
                            // Update the message
                            messageTxt.setText(getString(R.string.user_within_eea_msg) + choice);
                        }
                    });
                }
            });
        } else {
            messageTxt.setText(getString(R.string.user_not_within_eea_msg));
            ((LinearLayout) findViewById(R.id.message_container)).removeView(findViewById(R.id.consent_setting_btn));
        }

    }


    private void setupToolbar() {
        if (Build.VERSION.SDK_INT <= 22) { getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); }
        Toolbar toolbar = findViewById(R.id.toolbar);
        ToolbarTitle = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ToolbarTitle.setTextColor(getResources().getColor(R.color.white));
        ToolbarTitle.setText("General Settings");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }
    //////////////////////////////////////////////Out Side On Creat Method /////////////////////////////////////////////////////

    // Initialize consent
    private void initConsentSDK(Context context) {
        // Initialize ConsentSDK
        consentSDK = new ConsentSDK.Builder(context)
                //    .addTestDeviceId("5582245367B339176AD783C536320D97") // Add your test device id "Remove addTestDeviceId on production!"
                .addCustomLogTag("ID_LOG") // Add custom tag default: ID_LOG
                .addPrivacyPolicy(getString(R.string.privacyPolicyLink)) // Add your privacy policy url
                .addPublisherId(getString(R.string.Admob_publisher_id)) // Add your admob publisher id
                .build();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void onClick(View v) {
        switch (v.getId()) {





            case 123456: // replace with the actual constant ID number
                Intent privacy = new Intent(SettingSectioned.this, PrivacyWebview.class);
                startActivity(privacy);
                break;



        }

    }
/*
    // Load banner ads
    private void loadBanner() {
        AdView adView = findViewById(R.id.adView);
        // You have to pass the AdRequest from ConsentSDK.getAdRequest(this) because it handle the right way to load the ad
        adView.loadAd(ConsentSDK.getAdRequest(this));
    }
*/





}
