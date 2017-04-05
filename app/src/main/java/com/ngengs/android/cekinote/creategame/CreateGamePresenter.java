package com.ngengs.android.cekinote.creategame;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ngengs.android.cekinote.data.model.Game;
import com.ngengs.android.cekinote.data.model.GameDao;
import com.ngengs.android.cekinote.data.model.Player;
import com.ngengs.android.cekinote.data.model.PlayerDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by ngengs on 4/4/2017.
 */

class CreateGamePresenter implements CreateGameContract.Presenter {

    private final CreateGameContract.View mView;

    private PlayerDao playerDao;
    private GameDao gameDao;
    private List<Player> playerData;

    private String idPlayer1, idPlayer2, idPlayer3, idPlayer4;

    CreateGamePresenter(@NonNull CreateGameContract.View mView, @NonNull PlayerDao playerDao, @NonNull GameDao gameDao) {
        this.mView = mView;
        this.gameDao = gameDao;
        this.playerDao = playerDao;
        start();
    }

    @Override
    public void start() {
        playerData = new ArrayList<>();
        mView.setPresenter(this);
    }

    @Override
    public List<List<String>> generatePlayer(@NonNull String emptyName) {

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
        name.add(emptyName);
        allData.add(0, name);
        allData.add(1, id);
        return allData;
    }

    @Override
    public String addPlayer(@NonNull String name) {
        List<Player> tmpCheck = playerDao.queryBuilder().where(PlayerDao.Properties.Name.eq(name)).orderAsc(PlayerDao.Properties.Name).list();
        if (tmpCheck.isEmpty()) {
            Player player = new Player(UUID.randomUUID().toString(), name, new Date(), true);
            playerDao.insert(player);
            updatePlayerData();
            return player.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean isPlayerComplete() {
        return (idPlayer1 != null && idPlayer2 != null && idPlayer3 != null && idPlayer4 != null);
    }

    @Override
    public String insertGame(@Nullable String location) {
        if (location != null && location.equals("")) location = null;
        Game game = new Game(UUID.randomUUID().toString(), new Date(), null, location, idPlayer1, idPlayer2, idPlayer3, idPlayer4);
        gameDao.insert(game);
        return game.getId();
    }

    @Override
    public void setPlayer(Integer position, String name, String id) {
        if (id != null) {
            switch (position) {
                case 1:
                    idPlayer1 = id;
                    break;
                case 2:
                    idPlayer2 = id;
                    break;
                case 3:
                    idPlayer3 = id;
                    break;
                case 4:
                    idPlayer4 = id;
                    break;
            }
            mView.applyPlayerName(position, name);
        } else {
            mView.addPlayerError(name);
        }
    }

    @Override
    public List<Player> getPlayer() {
        return playerData;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setPlayerData(List player) {
        playerData.addAll(player);
    }

    @Override
    public void updatePlayerData() {
        playerData.clear();
        playerData.addAll(playerDao.queryBuilder().orderAsc(PlayerDao.Properties.Name).list());
    }

    @Override
    public void setPlayerId(Integer position, String id) {
        switch (position) {
            case 1:
                idPlayer1 = id;
                break;
            case 2:
                idPlayer2 = id;
                break;
            case 3:
                idPlayer3 = id;
                break;
            case 4:
                idPlayer4 = id;
                break;
        }
    }

    @Override
    public String getPlayerId(Integer position) {
        switch (position) {
            case 1:
                return idPlayer1;
            case 2:
                return idPlayer2;
            case 3:
                return idPlayer3;
            case 4:
                return idPlayer4;
            default:
                return null;
        }
    }

    @Override
    public int gameSize() {
        return gameDao.queryBuilder().orderDesc(GameDao.Properties.DateStart).list().size();
    }
}
