package com.cpacm.moemusic.ui.beats;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cpacm.core.bean.AccountBean;
import com.cpacm.core.mvp.views.BeatsIView;
import com.cpacm.moemusic.MoeApplication;
import com.cpacm.moemusic.R;
import com.cpacm.moemusic.ui.AbstractAppActivity;
import com.cpacm.moemusic.ui.widgets.CircleImageView;

public class BeatsActivity extends AbstractAppActivity implements NavigationView.OnNavigationItemSelectedListener, BeatsIView {

    private DrawerLayout drawerLayout;
    private BeatsPresenter beatsPresenter;
    private NavigationView navigationView;
    private CircleImageView avatar, userImg;
    private TextView nicknameTv, aboutTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_drawer_home);
        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beatsPresenter.getAccountDetail();
            }
        });

        initDrawer();
        getData();
        initData(MoeApplication.getInstance().getAccountBean());
    }

    private void initDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_string, R.string.close_string);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        userImg = (CircleImageView) findViewById(R.id.user_icon);

        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        avatar = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.drawer_avatar);
        nicknameTv = (TextView) navigationView.getHeaderView(0).findViewById(R.id.drawer_nickname);
        aboutTv = (TextView) navigationView.getHeaderView(0).findViewById(R.id.drawer_about);
    }

    private void initData(AccountBean accountBean) {
        Glide.with(this)
                .load(accountBean.getUser_avatar().getMedium())
                .into(userImg);
        Glide.with(this)
                .load(accountBean.getUser_avatar().getLarge())
                .into(avatar);
        String nickname = accountBean.getUser_nickname();
        if (TextUtils.isEmpty(nickname)) {
            nickname = accountBean.getUser_name();
        }
        nicknameTv.setText(nickname);
        if (TextUtils.isEmpty(accountBean.getAbout())) {
            aboutTv.setVisibility(View.GONE);
        } else {
            aboutTv.setVisibility(View.VISIBLE);
            aboutTv.setText(accountBean.getAbout());
        }
    }

    private void getData() {
        beatsPresenter = new BeatsPresenter(this);
        beatsPresenter.getAccountDetail();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawers();
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_account) {
            // Handle the camera action
        } else if (id == R.id.nav_free) {

        } else if (id == R.id.nav_study) {

        } else if (id == R.id.nav_contract) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_api) {

        }
        //关闭侧滑栏
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setUserDetail(AccountBean accountBean) {
        initData(accountBean);
    }

    @Override
    public void getUserFail(String msg) {
        showSnackBar(msg);
    }
}
