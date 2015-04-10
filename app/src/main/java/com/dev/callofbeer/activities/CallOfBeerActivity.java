package com.dev.callofbeer.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dev.callofbeer.R;
import com.dev.callofbeer.authentication.utils.Config;
import com.dev.callofbeer.fragments.CobFloatingMenu;
import com.dev.callofbeer.fragments.AuthenticationFragment;
import com.dev.callofbeer.fragments.CreateEventFragment;
import com.dev.callofbeer.fragments.MapFragment;
import com.dev.callofbeer.models.authentication.Authentication;
import com.dev.callofbeer.models.authentication.User;
import com.dev.callofbeer.models.authentication.UserSave;
import com.dev.callofbeer.authentication.utils.UserManager;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;

/**
 * Created by matth on 04/03/15.
 */

/**
 *
 * MainActivity
 *
 *
 */
public class CallOfBeerActivity extends FragmentActivity {

    public static FragmentManager fragmentManager;

    private SlidingUpPanelLayout mSlidingLayout;
    private CobFloatingMenu menu;

    private UserManager userManager;

    private RelativeLayout loadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingProgress = (RelativeLayout) findViewById(R.id.globalProgress);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.relativelayout_map, new MapFragment());
        fragmentTransaction.commit();

        mSlidingLayout = (SlidingUpPanelLayout) findViewById(R.id.slidinguppanellayout_layout);
        mSlidingLayout.setAnchorPoint(0.7f);
        mSlidingLayout.setPanelSlideListener(new SlidingUpPanelView());

        mSlidingLayout.setDragView(findViewById(R.id.cob_draggable_panel));

        menu = (CobFloatingMenu) findViewById(R.id.multiple_actions);
        menu.setActivity(this);

        FragmentTransaction fragmentTransactionPanel = fragmentManager.beginTransaction();
        fragmentTransactionPanel.replace(R.id.main_container, new CreateEventFragment());
        fragmentTransactionPanel.commit();
    }

    public UserManager getUserManager() {
        return userManager;
    }

    private class SlidingUpPanelView implements SlidingUpPanelLayout.PanelSlideListener {
        @Override
        public void onPanelSlide(View view, float v) {
            if(v >= 0.7f) {
                mSlidingLayout.expandPanel(0.7f);
            }
        }

        @Override
        public void onPanelCollapsed(View view) {}
        @Override
        public void onPanelExpanded(View view) {}
        @Override
        public void onPanelAnchored(View view) {}
        @Override
        public void onPanelHidden(View view) {}
    }

    public void forcedSlidingUp(){
        mSlidingLayout.expandPanel(0.7f);
    }

    public void startLogin() {
        AccountManager accountManager = AccountManager.get(this);
        accountManager.addAccount(Config.COB_USER_TYPE, "access_token", null, null, this, null, null);
        /*AuthenticationFragment authenticationFragment = AuthenticationFragment.newInstance(true);
        getFragmentManager().beginTransaction().replace(R.id.main_container, authenticationFragment).commit();
        mSlidingLayout.expandPanel(0.7f);*/
    }

    public void toogleProgressBar(boolean b) {
        if (b) {
            loadingProgress.setVisibility(View.VISIBLE);
        } else {
            loadingProgress.setVisibility(View.GONE);
        }
    }
}
