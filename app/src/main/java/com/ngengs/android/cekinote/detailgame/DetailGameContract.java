package com.ngengs.android.cekinote.detailgame;

import com.ngengs.android.cekinote.BasePresenter;
import com.ngengs.android.cekinote.BaseView;
import com.ngengs.android.cekinote.data.model.Game;

import java.util.List;

/**
 * Created by ngengs on 4/5/2017.
 */

interface DetailGameContract {
    interface View extends BaseView<Presenter> {
        void manipulateColorHeader();

        void startGame();

        void finishing();

        void updateScore(Integer score1, Integer score2, Integer score3, Integer score4);
    }

    interface Presenter extends BasePresenter {
        String gameId();

        List<Integer> scorePlayer(Integer playerNumber);

        Game gameData();

        boolean isGameEmpty();

        boolean isGameFinish();

        void finishGame();

        void insertScore(Integer scorePlayer1, Integer scorePlayer2, Integer scorePlayer3, Integer scorePlayer4);
    }
}
