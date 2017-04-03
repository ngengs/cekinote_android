package com.ngengs.android.cekinote.data.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

/**
 * Created by ngengs on 12/15/2016.
 */

@SuppressWarnings({"WeakerAccess", "unused"})
@Entity
public class Player {

    @Id
    private String id;
    @NotNull
    private String name;
    @NotNull
    private Date date;
    @NotNull
    private boolean active;
    
    @Generated(hash = 988514407)
    public Player(String id, @NotNull String name, @NotNull Date date,
            boolean active) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.active = active;
    }
    @Generated(hash = 30709322)
    public Player() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public boolean getActive() {
        return this.active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}
