package com.ngengs.android.cekinote.detailgame;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ngengs.android.cekinote.App;
import com.ngengs.android.cekinote.R;
import com.ngengs.android.cekinote.data.Tag;
import com.ngengs.android.cekinote.data.model.DaoSession;
import com.ngengs.android.cekinote.utils.ResourceHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailGameActivity extends AppCompatActivity implements DetailGameContract.View {

    @BindView(R.id.game_detail_toolbar)
    Toolbar gameDetailToolbar;
    @BindView(R.id.game_detail_tab)
    TabLayout gameDetailTab;
    @BindView(R.id.game_detail_pager)
    ViewPager gameDetailPager;

    private DetailGamePagerAdapter detailGamePagerAdapter;
    private int positionGame;
    private int numberGame;

    private DetailGameContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_game);
        ButterKnife.bind(this);
        setSupportActionBar(gameDetailToolbar);
        ActionBar actionBar = getSupportActionBar();

        positionGame = getIntent().getIntExtra(Tag.GAME_POSITION, -1);
        numberGame = getIntent().getIntExtra(Tag.GAME_NUMBER, -1);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(String.format(getString(R.string.page_title_detail_game), numberGame));
        }

        App app = (App) getApplication();

        DaoSession session = app.getDaoSession();

        mPresenter = new DetailGamePresenter(this, session.getGameDao(), session.getScoreDao(), getIntent().getStringExtra(Tag.GAME_ID));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mPresenter.isGameEmpty() && !mPresenter.isGameFinish()) {
            getMenuInflater().inflate(R.menu.detail_game, menu);
            return true;
        } else return false;
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

    private void endGame() {
        if (!mPresenter.isGameFinish()) {
            mPresenter.finishGame();
            invalidateOptionsMenu();

            Intent intent = new Intent(this, DetailGameActivity.class);
            intent.putExtra(Tag.GAME_ID, mPresenter.gameId());
            intent.putExtra(Tag.GAME_POSITION, positionGame);
            intent.putExtra(Tag.GAME_NUMBER, numberGame);
            startActivityForResult(intent, Tag.REQUEST_DETAIL_GAME);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void addScore(@NonNull Integer scorePlayer1, @NonNull Integer scorePlayer2, @NonNull Integer scorePlayer3, @NonNull Integer scorePlayer4) {
        mPresenter.insertScore(scorePlayer1, scorePlayer2, scorePlayer3, scorePlayer4);
    }

    @Override
    public void setPresenter(DetailGameContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void manipulateColorHeader() {
        int color, colorDark, colorIndicator;
        if (mPresenter.isGameFinish()) {
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
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorDark);
        }
    }

    @Override
    public void startGame() {
        String[] usedTabTitle;
        if (mPresenter.isGameFinish()) {
            usedTabTitle = getResources().getStringArray(R.array.detail_game_finish);
        } else {
            usedTabTitle = getResources().getStringArray(R.array.detail_game_active);
        }

        detailGamePagerAdapter = new DetailGamePagerAdapter(getSupportFragmentManager(), usedTabTitle, mPresenter.gameData(), mPresenter.scorePlayer(1), mPresenter.scorePlayer(2), mPresenter.scorePlayer(3), mPresenter.scorePlayer(4));
        gameDetailPager.setAdapter(detailGamePagerAdapter);
        gameDetailTab.setupWithViewPager(gameDetailPager);

        Intent data = new Intent();
        data.putExtra(Tag.GAME_POSITION, positionGame);
        setResult(RESULT_OK, data);
        if (detailGamePagerAdapter.getCount() == 1) {
            gameDetailTab.setVisibility(View.GONE);
        }
    }

    @Override
    public void finishing() {
        finish();
    }

    @Override
    public void updateScore(@NonNull Integer score1, @NonNull Integer score2, @NonNull Integer score3, @NonNull Integer score4) {
        detailGamePagerAdapter.updateDataScore(score1, score2, score3, score4);
    }
}
