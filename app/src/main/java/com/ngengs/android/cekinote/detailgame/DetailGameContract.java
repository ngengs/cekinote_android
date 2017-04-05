package com.ngengs.android.cekinote.detailgame;

import com.ngengs.android.cekinote.BasePresenter;
import com.ngengs.android.cekinote.BaseView;
import com.ngengs.android.cekinote.data.model.Game;
import com.ngengs.android.cekinote.data.model.Score;

import java.util.List;

/**
 * Created by ngengs on 4/5/2017.
 */

interface DetailGameContract {
    interface View extends BaseView<Presenter> {
        void manipulateColorHeader();

        void startGame();

        void finishing();

        void updateScore(Score score1, Score score2, Score score3, Score score4);
    }

    interface Presenter extends BasePresenter {
        String gameId();

        List<Score> scorePlayer(Integer playerNumber);

        Game gameData();

        boolean isGameEmpty();

        boolean isGameFinish();

        void finishGame();

        void insertScore(Integer scorePlayer1, Integer scorePlayer2, Integer scorePlayer3, Integer scorePlayer4);
    }
}
