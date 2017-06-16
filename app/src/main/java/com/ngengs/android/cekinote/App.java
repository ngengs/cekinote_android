package com.ngengs.android.cekinote;

import android.app.Application;

import com.ngengs.android.cekinote.data.model.DaoMaster;
import com.ngengs.android.cekinote.data.model.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by ngengs on 12/15/2016.
 */

public class App extends Application {
    /**
     * A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher.
     * If se true dont forget to add net.zetetic:android-database-sqlcipher to dependencies
     */
    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    public App() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "ceki-note-db-encrypted" : "ceki-note-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("superSecret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        if (daoSession == null) onCreate();
        return daoSession;
    }
}
