package com.ngengs.android.cekinote.main;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.ngengs.android.cekinote.data.Tag;
import com.ngengs.android.cekinote.data.model.Game;
import com.ngengs.android.cekinote.data.model.GameDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ngengs on 4/3/2017.
 */

class MainPresenter implements MainContract.Presenter {

    private final MainContract.View mView;

    private GameDao gameDao;
    private List<Game> gameData;
    private List<Integer> selectedGame;

    MainPresenter(@NonNull MainContract.View mView, @NonNull GameDao gameDao) {
        this.mView = mView;
        this.gameDao = gameDao;
        start();
    }

    @Override
    public void start() {
        gameData = new ArrayList<>();
        selectedGame = new ArrayList<>();
        mView.setPresenter(this);
        mView.changeHeaderTitle();
        mView.changeHeaderColor();
    }

    @Override
    public boolean menuEndGame() {
        Date now = new Date();
        List<Game> tempGame = new ArrayList<>();
        for (int position : selectedGame) {
            Game game = gameData.get(position);
            game.setDateFinish(now);
            tempGame.add(game);
            mView.updateData(game, position);
            mView.removeSelected(position);
        }
        gameDao.updateInTx(tempGame);
        selectedGame.clear();
        mView.changeHeaderTitle();
        mView.changeHeaderLook(false);

        return true;
    }

    @Override
    public int gameSize() {
        return gameData.size();
    }

    @Override
    public int selectedSize() {
        return selectedGame.size();
    }

    @Override
    public void updateDataGame() {
        gameData.clear();
        gameData.addAll(gameDao.queryBuilder().orderDesc(GameDao.Properties.DateStart).list());
        mView.updateData(gameData);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void generateFromSavedInstanceState(@NonNull Bundle savedInstanceState) {
        Serializable temp = savedInstanceState.getSerializable(Tag.GAME_DATA);
        if (temp != null) {
            gameData.clear();
            gameData.addAll((List) temp);
            mView.updateData(gameData);
        } else {
            updateDataGame();
        }

        Serializable tempSelected = savedInstanceState.getSerializable(Tag.GAME_SELECTED);
        if (tempSelected != null) {
            selectedGame.addAll((List) tempSelected);
            if (!selectedGame.isEmpty()) {
                for (int i : selectedGame) {
                    mView.addSelected(i);
                }
                mView.changeHeaderTitle();
                mView.changeHeaderLook(true);
                mView.clearOptionMenu();
            }
        }
    }

    @Override
    public void refreshGameDao(@NonNull Integer position) {
        gameDao.refresh(gameData.get(position));
        mView.updateData(gameData.get(position), position);
    }

    @Override
    public void backPressed() {
        if (!selectedGame.isEmpty()) {
            for (int position : selectedGame) {
                mView.removeSelected(position);
            }
            selectedGame.clear();
            mView.changeHeaderTitle();
            mView.changeHeaderLook(false);
        }
    }

    @Override
    public void itemClick(@NonNull Integer position) {
        if (!selectedGame.isEmpty()) {
            if (gameData.get(position).getDateFinish() == null)
                longPressGameActivity(position);
        } else
            mView.startDetailGame(position);
    }

    @Override
    public List<Game> getGame() {
        return gameData;
    }

    @Override
    public List<Integer> getSelected() {
        return selectedGame;
    }

    @Override
    public boolean itemLongClick(@NonNull Integer position) {
        if (gameData.get(position).getDateFinish() == null && selectedGame.isEmpty()) {
            longPressGameActivity(position);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isSelectedEmpty() {
        return selectedGame.isEmpty();
    }

    @Override
    public String getGameId(@NonNull Integer position) {
        return gameData.get(position).getId();
    }

    private void longPressGameActivity(@NonNull Integer position) {
        if (selectedGame.contains(position)) {
            selectedGame.remove(selectedGame.indexOf(position));
            mView.removeSelected(position);
        } else {
            selectedGame.add(position);
            mView.addSelected(position);
            mView.clearOptionMenu();
        }
        mView.changeHeaderTitle();
        mView.changeHeaderLook(false);
    }
}
