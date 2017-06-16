package com.ngengs.android.cekinote.detailgame;

import android.support.annotation.NonNull;

import com.ngengs.android.cekinote.data.model.Game;
import com.ngengs.android.cekinote.data.model.GameDao;
import com.ngengs.android.cekinote.data.model.Score;
import com.ngengs.android.cekinote.data.model.ScoreDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by ngengs on 4/5/2017.
 */

class DetailGamePresenter implements DetailGameContract.Presenter {

    private final DetailGameContract.View mView;
    private final String idGame;
    private GameDao gameDao;
    private ScoreDao scoreDao;
    private Game gameData;

    DetailGamePresenter(@NonNull DetailGameContract.View mView, @NonNull GameDao gameDao, @NonNull ScoreDao scoreDao, @NonNull String idGame) {
        this.mView = mView;
        this.gameDao = gameDao;
        this.scoreDao = scoreDao;
        this.idGame = idGame;
        start();
    }

    @Override
    public void start() {
        mView.setPresenter(this);
        List<Game> temp = gameDao.queryBuilder().where(GameDao.Properties.Id.eq(idGame)).list();
        if (temp.size() > 0) {
            gameData = temp.get(0);
            mView.startGame();
            mView.manipulateColorHeader();
        } else {
            mView.finishing();
        }
    }

    @Override
    public String gameId() {
        return gameData.getId();
    }

    @Override
    public List<Integer> scorePlayer(@NonNull Integer playerNumber) {
        final String idPlayer;
        switch (playerNumber) {
            case 1:
                idPlayer = gameData.getIdPlayer1();
                break;
            case 2:
                idPlayer = gameData.getIdPlayer2();
                break;
            case 3:
                idPlayer = gameData.getIdPlayer3();
                break;
            case 4:
                idPlayer = gameData.getIdPlayer4();
                break;
            default:
                return null;
        }
        List<Score> scores = scoreDao.queryBuilder().where(ScoreDao.Properties.IdGame.eq(idGame), ScoreDao.Properties.IdPlayer.eq(idPlayer)).orderAsc(ScoreDao.Properties.Date).list();
        List<Integer> returnData = new ArrayList<>();
        for (Score score : scores) {
            returnData.add(score.getScore());
        }
        return returnData;
    }

    @Override
    public Game gameData() {
        return gameData;
    }

    @Override
    public boolean isGameEmpty() {
        return (gameData == null);
    }

    @Override
    public boolean isGameFinish() {
        return (gameData.getDateFinish() != null);
    }

    @Override
    public void finishGame() {
        gameData.setDateFinish(new Date());
        gameDao.update(gameData);
        mView.manipulateColorHeader();
    }

    @Override
    public void insertScore(@NonNull Integer scorePlayer1, @NonNull Integer scorePlayer2, @NonNull Integer scorePlayer3, @NonNull Integer scorePlayer4) {
        final Date date = new Date();
        Score score1 = new Score(UUID.randomUUID().toString(), gameData.getIdPlayer1(), gameData.getId(), scorePlayer1, date);
        Score score2 = new Score(UUID.randomUUID().toString(), gameData.getIdPlayer2(), gameData.getId(), scorePlayer2, date);
        Score score3 = new Score(UUID.randomUUID().toString(), gameData.getIdPlayer3(), gameData.getId(), scorePlayer3, date);
        Score score4 = new Score(UUID.randomUUID().toString(), gameData.getIdPlayer4(), gameData.getId(), scorePlayer4, date);
        scoreDao.insertInTx(score1, score2, score3, score4);
        mView.updateScore(scorePlayer1, scorePlayer2, scorePlayer3, scorePlayer4);
    }
}
