package com.ngengs.android.cekinote.creategame;

import com.ngengs.android.cekinote.BasePresenter;
import com.ngengs.android.cekinote.BaseView;
import com.ngengs.android.cekinote.data.model.Player;

import java.util.List;

/**
 * Created by ngengs on 4/4/2017.
 */

interface CreateGameContract {
    interface View extends BaseView<Presenter> {
        void applyPlayerName(int position, String name);
    }

    interface Presenter extends BasePresenter {
        List<List<String>> generatePlayer(String emptyName);

        String addPlayer(String name);

        boolean isPlayerComplete();

        String insertGame(String location);

        void setPlayer(Integer position, String name, String id);

        List<Player> getPlayer();

        void setPlayerData(List player);

        void updatePlayerData();

        void setPlayerId(Integer position, String id);

        String getPlayerId(Integer position);

        int gameSize();
    }
}
