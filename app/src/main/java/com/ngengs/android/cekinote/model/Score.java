package com.ngengs.android.cekinote.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ngengs on 12/15/2016.
 */

@Entity
public class Score {

    @Id
    private String id;
    @NotNull
    private String idPlayer;
    @NotNull
    private String idGame;
    @NotNull
    private int score;
    @NotNull
    private Date date;
    @Generated(hash = 197544959)
    public Score(String id, @NotNull String idPlayer, @NotNull String idGame,
            int score, @NotNull Date date) {
        this.id = id;
        this.idPlayer = idPlayer;
        this.idGame = idGame;
        this.score = score;
        this.date = date;
    }
    @Generated(hash = 226049941)
    public Score() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getIdPlayer() {
        return this.idPlayer;
    }
    public void setIdPlayer(String idPlayer) {
        this.idPlayer = idPlayer;
    }
    public String getIdGame() {
        return this.idGame;
    }
    public void setIdGame(String idGame) {
        this.idGame = idGame;
    }
    public int getScore() {
        return this.score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
