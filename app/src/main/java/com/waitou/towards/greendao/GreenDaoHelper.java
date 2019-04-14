package com.waitou.towards.greendao;

import android.database.sqlite.SQLiteDatabase;

import com.blankj.utilcode.util.Utils;

/**
 * Created by waitou on 17/3/21.
 * greenDao 帮助类
 */

public class GreenDaoHelper {

    private static final String DATABASE_NAME = "towards_db";

    private static GreenDaoHelper sGreenDaoHelper;
    private        DaoSession     mDaoSession;

    public static GreenDaoHelper getDaoHelper() {
        if (sGreenDaoHelper == null) {
            synchronized (GreenDaoHelper.class) {
                if (sGreenDaoHelper == null) {
                    sGreenDaoHelper = new GreenDaoHelper();
                }
            }
        }
        return sGreenDaoHelper;
    }

    private GreenDaoHelper() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(Utils.getApp(), DATABASE_NAME, null);
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        mDaoSession = daoMaster.newSession();
    }

    public LogoImgDao getLogoImgDao() {
        return mDaoSession.getLogoImgDao();
    }
}
