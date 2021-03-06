package com.gracecode.android.gojuon.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.gracecode.android.gojuon.R;
import com.gracecode.android.gojuon.adapter.CharactersFragmentAdapter;
import com.gracecode.android.gojuon.common.Gojuon;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class MainActivity extends BaseActivity {
    private CharactersFragmentAdapter mCharactersFragmentAdapter;

    private static String KEY_AUTO_RESUME_SAVED = "key_auto_resume_saved";

    @InjectView(R.id.pager)
    ViewPager mViewPager;

    @InjectView(R.id.smart_tab_layout)
    SmartTabLayout mSmartTabLayout;

    @InjectView(R.id.toolbar)
    Toolbar mToolbarView;

    @InjectView(R.id.container)
    View mContainerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbarView);

        mCharactersFragmentAdapter = new CharactersFragmentAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(mCharactersFragmentAdapter);
        mSmartTabLayout.setViewPager(mViewPager);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewPager.setCurrentItem(getSavedResumePage(), false);
        if (mSharedPreferences.getBoolean(Gojuon.KEY_KEEP_SCREEN, true)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setResumePage();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private int getSavedResumePage() {
        String savedIndex = mSharedPreferences.getString(Gojuon.KEY_AUTO_RESUME, Gojuon.DEFAULT_RESUME_INDEX);
        int index = Integer.parseInt(savedIndex);
        if (index == Integer.parseInt(Gojuon.DEFAULT_RESUME_INDEX)) {
            return mSharedPreferences.getInt(KEY_AUTO_RESUME_SAVED, 0);
        }

        return index;
    }

    private void setResumePage() {
        String savedIndex = mSharedPreferences.getString(Gojuon.KEY_AUTO_RESUME, Gojuon.DEFAULT_RESUME_INDEX);
        int index = Integer.parseInt(savedIndex);
        if (index == Integer.parseInt(Gojuon.DEFAULT_RESUME_INDEX)) {
            mGojunon.putSharedPreferencesInt(KEY_AUTO_RESUME_SAVED, mViewPager.getCurrentItem());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
