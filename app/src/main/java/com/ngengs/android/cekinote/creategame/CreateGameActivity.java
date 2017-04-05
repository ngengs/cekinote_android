package com.ngengs.android.cekinote.creategame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ngengs.android.cekinote.App;
import com.ngengs.android.cekinote.R;
import com.ngengs.android.cekinote.data.Tag;
import com.ngengs.android.cekinote.data.model.DaoSession;
import com.ngengs.android.cekinote.detailgame.DetailGameActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateGameActivity extends AppCompatActivity implements CreateGameContract.View {

    @BindView(R.id.player1)
    TextView player1;
    @BindView(R.id.player2)
    TextView player2;
    @BindView(R.id.player3)
    TextView player3;
    @BindView(R.id.player4)
    TextView player4;
    @BindView(R.id.gameLocation)
    EditText gameLocation;
    @BindView(R.id.createGame)
    FloatingActionButton createGame;

    private int gameNumber;

    private CreateGameContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.page_title_create_game);
        }
        App app = (App) getApplication();

        DaoSession session = app.getDaoSession();
        mPresenter = new CreateGamePresenter(this, session.getPlayerDao(), session.getGameDao());
        if (savedInstanceState != null) {
            generateFromSavedInstanceState(savedInstanceState);
        } else {
            mPresenter.updatePlayerData();
            gameNumber = getIntent().getIntExtra(Tag.GAME_NUMBER, -1);
            if (gameNumber == -1) {
                gameNumber = mPresenter.gameSize() + 1;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Tag.PLAYER_DATA, new ArrayList<>(mPresenter.getPlayer()));
        outState.putString(Tag.PLAYER_DATA1, mPresenter.getPlayerId(1));
        outState.putString(Tag.PLAYER_DATA2, mPresenter.getPlayerId(2));
        outState.putString(Tag.PLAYER_DATA3, mPresenter.getPlayerId(3));
        outState.putString(Tag.PLAYER_DATA4, mPresenter.getPlayerId(4));
        outState.putString(Tag.PLAYER_NAME1, player1.getText().toString());
        outState.putString(Tag.PLAYER_NAME2, player2.getText().toString());
        outState.putString(Tag.PLAYER_NAME3, player3.getText().toString());
        outState.putString(Tag.PLAYER_NAME4, player4.getText().toString());
        outState.putString(Tag.GAME_LOCATION, gameLocation.getText().toString());
        outState.putInt(Tag.GAME_NUMBER, gameNumber);
        super.onSaveInstanceState(outState);
    }

    @SuppressWarnings("unchecked")
    private void generateFromSavedInstanceState(@NonNull Bundle savedInstanceState) {
        Serializable temp = savedInstanceState.getSerializable(Tag.PLAYER_DATA);
        if (temp != null) mPresenter.setPlayerData((List) temp);
        mPresenter.setPlayerId(1, savedInstanceState.getString(Tag.PLAYER_DATA1));
        mPresenter.setPlayerId(2, savedInstanceState.getString(Tag.PLAYER_DATA2));
        mPresenter.setPlayerId(3, savedInstanceState.getString(Tag.PLAYER_DATA3));
        mPresenter.setPlayerId(4, savedInstanceState.getString(Tag.PLAYER_DATA4));
        player1.setText(savedInstanceState.getString(Tag.PLAYER_NAME1));
        player2.setText(savedInstanceState.getString(Tag.PLAYER_NAME2));
        player3.setText(savedInstanceState.getString(Tag.PLAYER_NAME3));
        player4.setText(savedInstanceState.getString(Tag.PLAYER_NAME4));
        gameLocation.setText(savedInstanceState.getString(Tag.GAME_LOCATION));
        gameNumber = savedInstanceState.getInt(Tag.GAME_NUMBER);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.player1, R.id.player2, R.id.player3, R.id.player4})
    protected void clickPlayer(View view) {
        final List<List<String>> player = mPresenter.generatePlayer(getString(R.string.menu_add_player));
        switch (view.getId()) {
            case R.id.player1:
                selectPlayerDialog(1, player.get(0), player.get(1));
                break;
            case R.id.player2:
                selectPlayerDialog(2, player.get(0), player.get(1));
                break;
            case R.id.player3:
                selectPlayerDialog(3, player.get(0), player.get(1));
                break;
            case R.id.player4:
                selectPlayerDialog(4, player.get(0), player.get(1));
                break;
        }
    }

    @OnClick(R.id.createGame)
    protected void createTheGame() {
        if (mPresenter.isPlayerComplete()) {
            String gameId = mPresenter.insertGame(gameLocation.getText().toString());

            Intent intent = new Intent(this, DetailGameActivity.class);
            intent.putExtra(Tag.GAME_ID, gameId);
            intent.putExtra(Tag.GAME_POSITION, 0);
            intent.putExtra(Tag.GAME_NUMBER, gameNumber);
            setResult(RESULT_OK);
            startActivityForResult(intent, Tag.REQUEST_DETAIL_GAME);
            finish();
        } else {
            Snackbar.make(createGame, R.string.natice_complete_select_player, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void selectPlayerDialog(@NonNull final Integer playerNumber, @NonNull final List<String> playerName, @NonNull final List<String> playerId) {
        new MaterialDialog.Builder(this)
                .title(String.format(getString(R.string.title_select_player), playerNumber))
                .items(playerName)
                .positiveColorRes(R.color.colorPrimary)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (which == playerName.size() - 1) {
                            createNewPlayer(playerNumber);
                        } else {
                            mPresenter.setPlayer(playerNumber, playerName.get(which), playerId.get(which));
                        }
                    }
                })
                .show();
    }

    private void createNewPlayer(@NonNull final int forPlayer) {
        new MaterialDialog.Builder(this)
                .title(String.format(getString(R.string.title_create_player), forPlayer))
                .inputType(InputType.TYPE_CLASS_TEXT)
                .positiveColorRes(R.color.colorPrimary)
                .input(getString(R.string.text_player_name), null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        mPresenter.setPlayer(forPlayer, input.toString(), mPresenter.addPlayer(input.toString()));
                    }
                })
                .show();
    }

    @Override
    public void setPresenter(CreateGameContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void applyPlayerName(@NonNull Integer position, @NonNull String name) {
        final String text = String.format(getString(R.string.player_number_format), position, name);
        switch (position) {
            case 1:
                player1.setText(text);
                break;
            case 2:
                player2.setText(text);
                break;
            case 3:
                player3.setText(text);
                break;
            case 4:
                player4.setText(text);
                break;
        }
    }

}
