package com.ngengs.android.cekinote;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ngengs.android.cekinote.adapter.GameDetailPagerAdapter;
import com.ngengs.android.cekinote.globals.Tag;
import com.ngengs.android.cekinote.model.DaoSession;
import com.ngengs.android.cekinote.model.Game;
import com.ngengs.android.cekinote.model.GameDao;
import com.ngengs.android.cekinote.model.Score;
import com.ngengs.android.cekinote.model.ScoreDao;
import com.ngengs.android.cekinote.utils.ResourceHelper;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailGameActivity extends AppCompatActivity {

    @BindView(R.id.game_detail_toolbar)
    Toolbar gameDetailToolbar;
    @BindView(R.id.game_detail_tab)
    TabLayout gameDetailTab;
    @BindView(R.id.game_detail_appbar)
    AppBarLayout gameDetailAppbar;
    @BindView(R.id.activity_detail_game)
    CoordinatorLayout activityDetailGame;
    @BindView(R.id.game_detail_pager)
    ViewPager gameDetailPager;

    GameDao gameDao;
    ScoreDao scoreDao;
    Game gameData;
    GameDetailPagerAdapter gameDetailPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_game);
        ButterKnife.bind(this);
        setSupportActionBar(gameDetailToolbar);
        ActionBar actionBar = getSupportActionBar();
        String idGame = getIntent().getStringExtra(Tag.GAME_ID);
        int positionGame = getIntent().getIntExtra(Tag.GAME_POSITION, -1);
        int numberGame = getIntent().getIntExtra(Tag.GAME_NUMBER, -1);

        App app = (App) getApplication();

        DaoSession session = app.getDaoSession();
        gameDao = session.getGameDao();
        scoreDao = session.getScoreDao();
        List<Game> temp = gameDao.queryBuilder().where(GameDao.Properties.Id.eq(idGame)).list();
        if (temp.size() > 0) {
            gameData = temp.get(0);
            manipulateColorHeader();
            createTabPage();
            Intent data = new Intent();
            data.putExtra(Tag.GAME_POSITION, positionGame);
            setResult(RESULT_OK, data);
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle(String.format(getString(R.string.page_title_detail_game), numberGame));
            }
            if (gameDetailPagerAdapter.getCount() == 1) {
                gameDetailTab.setVisibility(View.GONE);
            }
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void createTabPage() {
        String[] defaultTabTitle = getResources().getStringArray(R.array.detail_game_active);
        String[] usedTabTitle;
        if (isGameFinish()) {
            usedTabTitle = getResources().getStringArray(R.array.detail_game_finish);
        } else {
            usedTabTitle = defaultTabTitle;
        }
        final String idGame = gameData.getId();
        final List<Score> scorePlayer1 = scoreDao.queryBuilder().where(ScoreDao.Properties.IdGame.eq(idGame), ScoreDao.Properties.IdPlayer.eq(gameData.getIdPlayer1())).orderAsc(ScoreDao.Properties.Date).list();
        final List<Score> scorePlayer2 = scoreDao.queryBuilder().where(ScoreDao.Properties.IdGame.eq(idGame), ScoreDao.Properties.IdPlayer.eq(gameData.getIdPlayer2())).orderAsc(ScoreDao.Properties.Date).list();
        final List<Score> scorePlayer3 = scoreDao.queryBuilder().where(ScoreDao.Properties.IdGame.eq(idGame), ScoreDao.Properties.IdPlayer.eq(gameData.getIdPlayer3())).orderAsc(ScoreDao.Properties.Date).list();
        final List<Score> scorePlayer4 = scoreDao.queryBuilder().where(ScoreDao.Properties.IdGame.eq(idGame), ScoreDao.Properties.IdPlayer.eq(gameData.getIdPlayer4())).orderAsc(ScoreDao.Properties.Date).list();
        gameDetailPagerAdapter = new GameDetailPagerAdapter(getSupportFragmentManager(), usedTabTitle, defaultTabTitle, gameData, scorePlayer1, scorePlayer2, scorePlayer3, scorePlayer4);
        gameDetailPager.setAdapter(gameDetailPagerAdapter);
        gameDetailTab.setupWithViewPager(gameDetailPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (gameData != null) {
            if (!isGameFinish()) {
                getMenuInflater().inflate(R.menu.detail_game, menu);
                return true;
            } else return false;
        } else return false;
    }

    private void manipulateColorHeader() {
        int color, colorDark, colorIndicator;
        if (isGameFinish()) {
            color = ResourceHelper.getColor(this, R.color.colorAccent);
            colorDark = ResourceHelper.getColor(this, R.color.colorAccentDark);
            colorIndicator = ResourceHelper.getColor(this, R.color.colorPrimary);
        } else {
            color = ResourceHelper.getColor(this, R.color.colorPrimary);
            colorDark = ResourceHelper.getColor(this, R.color.colorPrimaryDark);
            colorIndicator = ResourceHelper.getColor(this, R.color.colorAccent);
        }
        gameDetailToolbar.setBackgroundColor(color);
        gameDetailTab.setBackgroundColor(color);
        gameDetailTab.setSelectedTabIndicatorColor(colorIndicator);
        manipulateStatusBarColor(colorDark);
    }

    private boolean isGameFinish() {
        return gameData.getDateFinish() != null;
    }

    private void manipulateStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(color);
        }
    }

    public void addScore(int scorePlayer1, int scorePlayer2, int scorePlayer3, int scorePlayer4) {
        Score score1 = new Score(UUID.randomUUID().toString(), gameData.getIdPlayer1(), gameData.getId(), scorePlayer1, new Date());
        Score score2 = new Score(UUID.randomUUID().toString(), gameData.getIdPlayer2(), gameData.getId(), scorePlayer2, new Date());
        Score score3 = new Score(UUID.randomUUID().toString(), gameData.getIdPlayer3(), gameData.getId(), scorePlayer3, new Date());
        Score score4 = new Score(UUID.randomUUID().toString(), gameData.getIdPlayer4(), gameData.getId(), scorePlayer4, new Date());
        scoreDao.insert(score1);
        scoreDao.insert(score2);
        scoreDao.insert(score3);
        scoreDao.insert(score4);
        gameDetailPagerAdapter.updateDataScore(score1, score2, score3, score4);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_end_game:
                endGame();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void endGame() {
        if (!isGameFinish()) {
            gameData.setDateFinish(new Date());
            gameDao.update(gameData);
            invalidateOptionsMenu();
            manipulateColorHeader();
            /*
             Not work for now
             */
//            gameDetailPagerAdapter.changeToEndGame();
            finish();
        }
    }
}