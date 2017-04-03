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
import com.ngengs.android.cekinote.data.model.Game;
import com.ngengs.android.cekinote.data.model.GameDao;
import com.ngengs.android.cekinote.data.model.Player;
import com.ngengs.android.cekinote.data.model.PlayerDao;
import com.ngengs.android.cekinote.detailgame.DetailGameActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateGameActivity extends AppCompatActivity {

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


    private PlayerDao playerDao;
    private GameDao gameDao;
    private List<Player> playerData;

    private String idPlayer1, idPlayer2, idPlayer3, idPlayer4;
    private int gameNumber;

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
        playerDao = session.getPlayerDao();
        gameDao = session.getGameDao();
        playerData = new ArrayList<>();
        if (savedInstanceState != null) {
            generateFromSavedInstanceState(savedInstanceState);
        } else {
            updateDataPlayer();
            gameNumber = getIntent().getIntExtra(Tag.GAME_NUMBER, -1);
            if (gameNumber == -1) {
                gameNumber = gameDao.queryBuilder().orderDesc(GameDao.Properties.DateStart).list().size() + 1;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Tag.PLAYER_DATA, new ArrayList<>(playerData));
        outState.putString(Tag.PLAYER_DATA1, idPlayer1);
        outState.putString(Tag.PLAYER_DATA2, idPlayer2);
        outState.putString(Tag.PLAYER_DATA3, idPlayer3);
        outState.putString(Tag.PLAYER_DATA4, idPlayer4);
        outState.putString(Tag.PLAYER_NAME1, player1.getText().toString());
        outState.putString(Tag.PLAYER_NAME2, player2.getText().toString());
        outState.putString(Tag.PLAYER_NAME3, player3.getText().toString());
        outState.putString(Tag.PLAYER_NAME4, player4.getText().toString());
        outState.putString(Tag.GAME_LOCATION, gameLocation.getText().toString());
        outState.putInt(Tag.GAME_NUMBER, gameNumber);
        super.onSaveInstanceState(outState);
    }

    @SuppressWarnings("unchecked")
    private void generateFromSavedInstanceState(Bundle savedInstanceState){
        Serializable temp = savedInstanceState.getSerializable(Tag.PLAYER_DATA);
        if (temp != null) playerData.addAll((List) temp);
        idPlayer1 = savedInstanceState.getString(Tag.PLAYER_DATA1);
        idPlayer2 = savedInstanceState.getString(Tag.PLAYER_DATA2);
        idPlayer3 = savedInstanceState.getString(Tag.PLAYER_DATA3);
        idPlayer4 = savedInstanceState.getString(Tag.PLAYER_DATA4);
        player1.setText(savedInstanceState.getString(Tag.PLAYER_NAME1));
        player2.setText(savedInstanceState.getString(Tag.PLAYER_NAME2));
        player3.setText(savedInstanceState.getString(Tag.PLAYER_NAME3));
        player4.setText(savedInstanceState.getString(Tag.PLAYER_NAME4));
        gameLocation.setText(savedInstanceState.getString(Tag.GAME_LOCATION));
        gameNumber = savedInstanceState.getInt(Tag.GAME_NUMBER);
    }

    private void updateDataPlayer() {
        playerData.clear();
        playerData.addAll(playerDao.queryBuilder().orderAsc(PlayerDao.Properties.Name).list());
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
        final List<List<String>> player = generatePlayer();
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
        if (idPlayer1 != null && idPlayer2 != null && idPlayer3 != null && idPlayer4 != null) {
            String location = null;
            if (!gameLocation.getText().toString().equals(""))
                location = gameLocation.getText().toString();
            Game game = new Game(UUID.randomUUID().toString(), new Date(), null, location, idPlayer1, idPlayer2, idPlayer3, idPlayer4);
            gameDao.insert(game);

            Intent intent = new Intent(this, DetailGameActivity.class);
            intent.putExtra(Tag.GAME_ID, game.getId());
            intent.putExtra(Tag.GAME_POSITION, 0);
            intent.putExtra(Tag.GAME_NUMBER, gameNumber);
            setResult(RESULT_OK);
            startActivityForResult(intent, Tag.REQUEST_DETAIL_GAME);
            finish();
        } else {
            Snackbar.make(createGame, R.string.natice_complete_select_player, Snackbar.LENGTH_SHORT).show();
        }
    }

    private List<List<String>> generatePlayer() {
        List<List<String>> allData = new ArrayList<>(2);
        List<String> name = new ArrayList<>();
        List<String> id = new ArrayList<>();
        for (Player player : playerData) {
            String tempId = player.getId();
            boolean process = true;
            if (idPlayer1 != null) {
                if (idPlayer1.equals(tempId)) process = false;
            }
            if (idPlayer2 != null && process) {
                if (idPlayer2.equals(tempId)) process = false;
            }
            if (idPlayer3 != null && process) {
                if (idPlayer3.equals(tempId)) process = false;
            }
            if (idPlayer4 != null && process) {
                if (idPlayer4.equals(tempId)) process = false;
            }
            if (process) {
                name.add(player.getName());
                id.add(tempId);
            }
        }
        name.add(getString(R.string.menu_add_player));
        allData.add(0, name);
        allData.add(1, id);
        return allData;
    }

    private void selectPlayerDialog(final int playerNumber, final List<String> playerName, final List<String> playerId) {
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
                            setPlayer(playerNumber, playerName.get(which), playerId.get(which));
                        }
                    }
                })
                .show();
    }

    private void createNewPlayer(final int forPlayer) {
        new MaterialDialog.Builder(this)
                .title(String.format(getString(R.string.title_create_player), forPlayer))
                .inputType(InputType.TYPE_CLASS_TEXT)
                .positiveColorRes(R.color.colorPrimary)
                .input(getString(R.string.text_player_name), null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        setPlayer(forPlayer, input.toString(), addPlayer(input.toString()));
                    }
                })
                .show();
    }

    private String addPlayer(String name) {
        Player player = new Player(UUID.randomUUID().toString(), name, new Date(), true);
        playerDao.insert(player);
        updateDataPlayer();
        return player.getId();
    }

    private void setPlayer(int position, String name, String idPlayer) {
        final String text = String.format(getString(R.string.player_number_format), position, name);
        switch (position) {
            case 1:
                player1.setText(text);
                idPlayer1 = idPlayer;
                break;
            case 2:
                player2.setText(text);
                idPlayer2 = idPlayer;
                break;
            case 3:
                player3.setText(text);
                idPlayer3 = idPlayer;
                break;
            case 4:
                player4.setText(text);
                idPlayer4 = idPlayer;
                break;
        }
    }
}
