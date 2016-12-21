package com.ngengs.android.cekinote;

import android.content.Intent;
import android.os.Bundle;
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
import com.ngengs.android.cekinote.globals.Tag;
import com.ngengs.android.cekinote.model.DaoSession;
import com.ngengs.android.cekinote.model.Game;
import com.ngengs.android.cekinote.model.GameDao;
import com.ngengs.android.cekinote.model.Player;
import com.ngengs.android.cekinote.model.PlayerDao;

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


    PlayerDao playerDao;
    GameDao gameDao;
    List<Player> playerData;

    String idPlayer1, idPlayer2, idPlayer3, idPlayer4;
    int gameNumber;

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
        gameNumber = getIntent().getIntExtra(Tag.GAME_NUMBER, -1);

        DaoSession session = app.getDaoSession();
        playerDao = session.getPlayerDao();
        gameDao = session.getGameDao();
        playerData = new ArrayList<>();
        updateDataPlayer();
        if(gameNumber == -1){
            gameNumber = gameDao.queryBuilder().orderDesc(GameDao.Properties.DateStart).list().size() + 1;
        }
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

    @OnClick(R.id.player1)
    protected void clickPlayer1() {
        final List<String> playerName = generatePlayer(true);
        final List<String> playerId = generatePlayer(false);
        new MaterialDialog.Builder(this)
                .title(String.format(getString(R.string.title_select_player), 1))
                .items(playerName)
                .positiveColorRes(R.color.colorPrimary)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (which == playerName.size() - 1) {
                            createNewPlayer(1);
                        } else {
                            setPlayer(1, playerName.get(which), playerId.get(which));
                        }
                    }
                })
                .show();
    }

    @OnClick(R.id.player2)
    protected void clickPlayer2() {
        final List<String> playerName = generatePlayer(true);
        final List<String> playerId = generatePlayer(false);
        new MaterialDialog.Builder(this)
                .title(String.format(getString(R.string.title_select_player), 2))
                .items(playerName)
                .positiveColorRes(R.color.colorPrimary)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (which == playerName.size() - 1) {
                            createNewPlayer(2);
                        } else {
                            setPlayer(2, playerName.get(which), playerId.get(which));
                        }
                    }
                })
                .show();
    }

    @OnClick(R.id.player3)
    protected void clickPlayer3() {
        final List<String> playerName = generatePlayer(true);
        final List<String> playerId = generatePlayer(false);
        new MaterialDialog.Builder(this)
                .title(String.format(getString(R.string.title_select_player), 3))
                .items(playerName)
                .positiveColorRes(R.color.colorPrimary)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (which == playerName.size() - 1) {
                            createNewPlayer(3);
                        } else {
                            setPlayer(3, playerName.get(which), playerId.get(which));
                        }
                    }
                })
                .show();
    }

    @OnClick(R.id.player4)
    protected void clickPlayer4() {
        final List<String> playerName = generatePlayer(true);
        final List<String> playerId = generatePlayer(false);
        new MaterialDialog.Builder(this)
                .title(String.format(getString(R.string.title_select_player), 4))
                .items(playerName)
                .positiveColorRes(R.color.colorPrimary)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (which == playerName.size() - 1) {
                            createNewPlayer(4);
                        } else {
                            setPlayer(4, playerName.get(which), playerId.get(which));
                        }
                    }
                })
                .show();
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

    private List<String> generatePlayer(boolean isName) {
        List<String> name = new ArrayList<>();
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
                if (isName) name.add(player.getName());
                else name.add(tempId);
            }
        }
        name.add(getString(R.string.menu_add_player));
        return name;
    }

    private void createNewPlayer(final int forPlayer) {
        new MaterialDialog.Builder(this)
                .title(String.format(getString(R.string.title_create_player), forPlayer))
                .inputType(InputType.TYPE_CLASS_TEXT)
                .positiveColorRes(R.color.colorPrimary)
                .input(getString(R.string.text_player_name), null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
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
