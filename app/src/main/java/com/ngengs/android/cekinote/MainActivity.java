package com.ngengs.android.cekinote;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

import com.ngengs.android.cekinote.adapter.GameListAdapter;
import com.ngengs.android.cekinote.globals.Tag;
import com.ngengs.android.cekinote.globals.Value;
import com.ngengs.android.cekinote.model.DaoSession;
import com.ngengs.android.cekinote.model.Game;
import com.ngengs.android.cekinote.model.GameDao;
import com.ngengs.android.cekinote.utils.ResourceHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
    TextView applicationVersion;


    GameDao gameDao;
    List<Game> gameData;

    GameListAdapter adapter;
    List<Integer> selectedGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setTitle(R.string.page_title_list_game);
        applicationVersion = ButterKnife.findById(navigationView.getHeaderView(0), R.id.app_version);
        applicationVersion.setText(getVersion());
        App app = (App) getApplication();

        DaoSession session = app.getDaoSession();
        gameDao = session.getGameDao();

        adapter = new GameListAdapter(this, null);
        adapter.setClickListener(new GameListAdapter.GameItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                if (!selectedGame.isEmpty()) {
                    if (gameData.get(position).getDateFinish() == null)
                        longPressGameActivity(position);
                } else
                    detailGameActivity(position);
            }

            @Override
            public boolean onItemLongClick(int position, View view) {
                if (gameData.get(position).getDateFinish() == null && selectedGame.isEmpty()) {
                    longPressGameActivity(position);
                    return true;
                } else {
                    return false;
                }
            }
        });
        gameData = new ArrayList<>();
        selectedGame = new ArrayList<>();
        updateDataGame();
        gameRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        gameRecyclerView.setLayoutManager(layoutManager);
        gameRecyclerView.setAdapter(adapter);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        manipulateHeaderColor(ResourceHelper.getColor(this, R.color.colorPrimary), ResourceHelper.getColor(this, R.color.colorPrimaryDark));
    }

    private void longPressGameActivity(final int position) {
        if (selectedGame.contains(position)) {
            selectedGame.remove(selectedGame.indexOf(position));
            adapter.removeSelected(position);
        } else {
            selectedGame.add(position);
            adapter.addSelected(position);
            invalidateOptionsMenu();
        }
        changeHeaderLook();
    }

    private void detailGameActivity(int position) {
        Intent intent = new Intent(this, DetailGameActivity.class);
        intent.putExtra(Tag.GAME_ID, gameData.get(position).getId());
        intent.putExtra(Tag.GAME_POSITION, position);
        intent.putExtra(Tag.GAME_NUMBER, gameData.size() - position);
        startActivityForResult(intent, Tag.REQUEST_DETAIL_GAME);
    }

    private void updateDataGame() {
        gameData.clear();
        gameData.addAll(gameDao.queryBuilder().orderDesc(GameDao.Properties.DateStart).list());
        adapter.updateAllData(gameData);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!selectedGame.isEmpty()) {
            for (int position : selectedGame) {
                adapter.removeSelected(position);
            }
            selectedGame.clear();
            changeHeaderLook();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!selectedGame.isEmpty()) {
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
            Date now = new Date();
            for (int position : selectedGame) {
                Game game = gameData.get(position);
                game.setDateFinish(now);
                adapter.removeSelected(position);
                gameDao.update(game);
                gameDao.refresh(game);
            }
            selectedGame.clear();
            changeHeaderLook();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
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
            String version = getVersion();
            openBrowser(updateUrl + version);
            item.setChecked(false);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.fab)
    public void fabOnClick() {
        Intent i = new Intent(this, CreateGameActivity.class);
        i.putExtra(Tag.GAME_NUMBER, gameData.size() + 1);
        startActivityForResult(i, Tag.REQUEST_CREATE_GAME);
    }

    private void openBrowser(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == Tag.REQUEST_CREATE_GAME) {
            if (resultCode == RESULT_OK) {
                updateDataGame();
            }
        } else if (requestCode == Tag.REQUEST_DETAIL_GAME) {
            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra(Tag.GAME_POSITION, -1);
                if (position > -1) {
                    gameDao.refresh(gameData.get(position));
                    adapter.updateData(gameData.get(position), position);
                }
            }
        }
    }

    public void changeHeaderLook() {
        if (!selectedGame.isEmpty() && selectedGame.size() == 1) {
            manipulateHeaderColor(ResourceHelper.getColor(this, R.color.colorAccent), ResourceHelper.getColor(this, R.color.colorAccentDark));
            fab.hide();
        } else if (selectedGame.isEmpty()) {
            manipulateHeaderColor(ResourceHelper.getColor(this, R.color.colorPrimary), ResourceHelper.getColor(this, R.color.colorPrimaryDark));
            invalidateOptionsMenu();
            fab.show();
        }
    }

    public void manipulateHeaderColor(int colorToolbar, int colorStatusBar) {
        if (Build.VERSION.SDK_INT >= 21 && colorStatusBar != 0) {
            getWindow().setStatusBarColor(colorStatusBar);
        }
        if (colorToolbar != 0) {
            toolbar.setBackgroundColor(colorToolbar);
        }
    }

    public void manioulateHeaderTitle(String title) {
        toolbar.setTitle(title);
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
}