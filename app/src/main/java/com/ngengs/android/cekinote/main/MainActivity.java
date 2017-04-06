package com.ngengs.android.cekinote.main;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ngengs.android.cekinote.App;
import com.ngengs.android.cekinote.R;
import com.ngengs.android.cekinote.creategame.CreateGameActivity;
import com.ngengs.android.cekinote.data.Tag;
import com.ngengs.android.cekinote.data.Value;
import com.ngengs.android.cekinote.data.model.Game;
import com.ngengs.android.cekinote.detailgame.DetailGameActivity;
import com.ngengs.android.cekinote.utils.ResourceHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.game_list)
    RecyclerView gameRecyclerView;

    private GameListAdapter adapter;
    private TextView applicationVersion;
    private ActionBar actionBar;

    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        applicationVersion = ButterKnife.findById(navigationView.getHeaderView(0), R.id.app_version);
        applicationVersion.setText(getVersion());

        adapter = new GameListAdapter(this, null);
        adapter.setClickListener(new GameListAdapter.GameItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                mPresenter.itemClick(position);
            }

            @Override
            public boolean onItemLongClick(int position, View view) {
                return mPresenter.itemLongClick(position);
            }
        });
        gameRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        gameRecyclerView.setLayoutManager(layoutManager);
        gameRecyclerView.setAdapter(adapter);

        App app = (App) getApplication();
        mPresenter = new MainPresenter(this, app.getDaoSession().getGameDao());

        if (savedInstanceState != null) {
            mPresenter.generateFromSavedInstanceState(savedInstanceState);
        } else {
            mPresenter.updateDataGame();
        }
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Tag.GAME_DATA, new ArrayList<>(mPresenter.getGame()));
        outState.putSerializable(Tag.GAME_SELECTED, new ArrayList<>(mPresenter.getSelected()));
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mPresenter.isSelectedEmpty()) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_end_game) {
            return mPresenter.menuEndGame();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_contact) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", Value.CONTACT_EMAIL, null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Ceki Note Android");
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        } else if (id == R.id.nav_repo) {
            final String repoUrl = Value.URL_REPO;
            openBrowser(repoUrl);
            item.setChecked(false);
        } else if (id == R.id.nav_update) {
            final String updateUrl = Value.URL_UPDATE;
            String version = applicationVersion.getText().toString();
            openBrowser(updateUrl + version);
            item.setChecked(false);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.fab)
    public void fabOnClick() {
        Intent i = new Intent(this, CreateGameActivity.class);
        i.putExtra(Tag.GAME_NUMBER, mPresenter.gameSize() + 1);
        startActivityForResult(i, Tag.REQUEST_CREATE_GAME);
    }

    private void openBrowser(@NonNull String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == Tag.REQUEST_CREATE_GAME) {
            if (resultCode == RESULT_OK) {
                mPresenter.updateDataGame();
            }
        } else if (requestCode == Tag.REQUEST_DETAIL_GAME) {
            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra(Tag.GAME_POSITION, -1);
                if (position > -1) {
                    mPresenter.refreshGameDao(position);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            mPresenter.backPressed();
            super.onBackPressed();
        }
    }

    private String getVersion() {
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = "";
        if (pInfo != null) version = pInfo.versionName;
        return version;
    }

    @Override
    public void changeHeaderLook(@NonNull Boolean forceChange) {
        if (!mPresenter.isSelectedEmpty() && (mPresenter.selectedSize() == 1 || forceChange)) {
            fab.hide();
        } else if (mPresenter.isSelectedEmpty()) {
            invalidateOptionsMenu();
            fab.show();
        }
        changeHeaderColor();
    }

    @Override
    public void changeHeaderTitle() {
        if (!mPresenter.isSelectedEmpty())
            actionBar.setTitle(String.format(getString(R.string.page_title_list_game_selected), mPresenter.selectedSize()));
        else actionBar.setTitle(R.string.page_title_list_game);
    }

    @Override
    public void changeHeaderColor() {
        int colorToolbar;
        int colorStatusBar;
        if (mPresenter.isSelectedEmpty()) {
            colorToolbar = ResourceHelper.getColor(this, R.color.colorPrimary);
            colorStatusBar = ResourceHelper.getColor(this, R.color.colorPrimaryDark);
        } else {
            colorToolbar = ResourceHelper.getColor(this, R.color.colorAccent);
            colorStatusBar = ResourceHelper.getColor(this, R.color.colorAccentDark);
        }

        if (Build.VERSION.SDK_INT >= 21 && colorStatusBar != 0) {
            getWindow().setStatusBarColor(colorStatusBar);
        }
        if (colorToolbar != 0) {
            toolbar.setBackgroundColor(colorToolbar);
        }
    }

    @Override
    public void updateData(@NonNull List<Game> data) {
        adapter.updateAllData(data);
    }

    @Override
    public void updateData(@NonNull Game data, @NonNull Integer position) {
        adapter.updateData(data, position);
    }

    @Override
    public void addSelected(@NonNull Integer position) {
        adapter.addSelected(position);
    }

    @Override
    public void removeSelected(@NonNull Integer position) {
        adapter.removeSelected(position);
    }

    @Override
    public void clearOptionMenu() {
        invalidateOptionsMenu();
    }

    @Override
    public void startDetailGame(@NonNull Integer position) {
        Intent intent = new Intent(this, DetailGameActivity.class);
        intent.putExtra(Tag.GAME_ID, mPresenter.getGameId(position));
        intent.putExtra(Tag.GAME_POSITION, position);
        intent.putExtra(Tag.GAME_NUMBER, mPresenter.gameSize());
        startActivityForResult(intent, Tag.REQUEST_DETAIL_GAME);
    }

    @Override
    public void setPresenter(@NonNull MainContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
