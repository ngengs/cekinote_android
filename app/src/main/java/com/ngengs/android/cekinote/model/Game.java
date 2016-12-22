package com.ngengs.android.cekinote.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by ngengs on 12/15/2016.
 */

@SuppressWarnings({"WeakerAccess", "unused"})
@Entity
public class Game implements Parcelable{

    @Id
    private String id;
    
    @NotNull
    private Date dateStart;

    private Date dateFinish;

    private String location;

    @NotNull
    private String idPlayer1;

    @NotNull
    private String idPlayer2;

    @NotNull
    private String idPlayer3;

    @NotNull
    private String idPlayer4;

    @ToOne(joinProperty = "idPlayer1")
    private Player player1;

    @ToOne(joinProperty = "idPlayer2")
    private Player player2;

    @ToOne(joinProperty = "idPlayer3")
    private Player player3;

    @ToOne(joinProperty = "idPlayer4")
    private Player player4;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 359416843)
    private transient GameDao myDao;

    @Generated(hash = 788313244)
    public Game(String id, @NotNull Date dateStart, Date dateFinish,
            String location, @NotNull String idPlayer1, @NotNull String idPlayer2,
            @NotNull String idPlayer3, @NotNull String idPlayer4) {
        this.id = id;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.location = location;
        this.idPlayer1 = idPlayer1;
        this.idPlayer2 = idPlayer2;
        this.idPlayer3 = idPlayer3;
        this.idPlayer4 = idPlayer4;
    }

    @Generated(hash = 380959371)
    public Game() {
    }

    protected Game(Parcel in) {
        id = in.readString();
        location = in.readString();
        idPlayer1 = in.readString();
        idPlayer2 = in.readString();
        idPlayer3 = in.readString();
        idPlayer4 = in.readString();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateStart() {
        return this.dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateFinish() {
        return this.dateFinish;
    }

    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIdPlayer1() {
        return this.idPlayer1;
    }

    public void setIdPlayer1(String idPlayer1) {
        this.idPlayer1 = idPlayer1;
    }

    public String getIdPlayer2() {
        return this.idPlayer2;
    }

    public void setIdPlayer2(String idPlayer2) {
        this.idPlayer2 = idPlayer2;
    }

    public String getIdPlayer3() {
        return this.idPlayer3;
    }

    public void setIdPlayer3(String idPlayer3) {
        this.idPlayer3 = idPlayer3;
    }

    public String getIdPlayer4() {
        return this.idPlayer4;
    }

    public void setIdPlayer4(String idPlayer4) {
        this.idPlayer4 = idPlayer4;
    }

    @Generated(hash = 364892904)
    private transient String player1__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1890027380)
    public Player getPlayer1() {
        String __key = this.idPlayer1;
        if (player1__resolvedKey == null || player1__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PlayerDao targetDao = daoSession.getPlayerDao();
            Player player1New = targetDao.load(__key);
            synchronized (this) {
                player1 = player1New;
                player1__resolvedKey = __key;
            }
        }
        return player1;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 398435042)
    public void setPlayer1(@NotNull Player player1) {
        if (player1 == null) {
            throw new DaoException(
                    "To-one property 'idPlayer1' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.player1 = player1;
            idPlayer1 = player1.getId();
            player1__resolvedKey = idPlayer1;
        }
    }

    @Generated(hash = 221448252)
    private transient String player2__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2003156188)
    public Player getPlayer2() {
        String __key = this.idPlayer2;
        if (player2__resolvedKey == null || player2__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PlayerDao targetDao = daoSession.getPlayerDao();
            Player player2New = targetDao.load(__key);
            synchronized (this) {
                player2 = player2New;
                player2__resolvedKey = __key;
            }
        }
        return player2;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 457506305)
    public void setPlayer2(@NotNull Player player2) {
        if (player2 == null) {
            throw new DaoException(
                    "To-one property 'idPlayer2' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.player2 = player2;
            idPlayer2 = player2.getId();
            player2__resolvedKey = idPlayer2;
        }
    }

    @Generated(hash = 36569132)
    private transient String player3__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 522720963)
    public Player getPlayer3() {
        String __key = this.idPlayer3;
        if (player3__resolvedKey == null || player3__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PlayerDao targetDao = daoSession.getPlayerDao();
            Player player3New = targetDao.load(__key);
            synchronized (this) {
                player3 = player3New;
                player3__resolvedKey = __key;
            }
        }
        return player3;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2005230933)
    public void setPlayer3(@NotNull Player player3) {
        if (player3 == null) {
            throw new DaoException(
                    "To-one property 'idPlayer3' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.player3 = player3;
            idPlayer3 = player3.getId();
            player3__resolvedKey = idPlayer3;
        }
    }

    @Generated(hash = 1287327876)
    private transient String player4__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1366312990)
    public Player getPlayer4() {
        String __key = this.idPlayer4;
        if (player4__resolvedKey == null || player4__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PlayerDao targetDao = daoSession.getPlayerDao();
            Player player4New = targetDao.load(__key);
            synchronized (this) {
                player4 = player4New;
                player4__resolvedKey = __key;
            }
        }
        return player4;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1691577090)
    public void setPlayer4(@NotNull Player player4) {
        if (player4 == null) {
            throw new DaoException(
                    "To-one property 'idPlayer4' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.player4 = player4;
            idPlayer4 = player4.getId();
            player4__resolvedKey = idPlayer4;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(location);
        dest.writeString(idPlayer1);
        dest.writeString(idPlayer2);
        dest.writeString(idPlayer3);
        dest.writeString(idPlayer4);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 733596598)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getGameDao() : null;
    }
}
