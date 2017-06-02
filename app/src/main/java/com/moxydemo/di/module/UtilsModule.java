package com.moxydemo.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.moxydemo.Const;
import com.moxydemo.data.DataManager;
import com.moxydemo.data.DataManagerImpl;
import com.moxydemo.data.db.DBHelper;
import com.moxydemo.data.db.DBHelperImpl;
import com.moxydemo.data.db.model.DaoMaster;
import com.moxydemo.data.db.model.DaoSession;
import com.moxydemo.data.network.ApiHelper;
import com.moxydemo.data.network.ApiHelperImpl;
import com.moxydemo.data.network.Client;
import com.moxydemo.data.network.ServiceGenerator;
import com.moxydemo.data.prefs.PreferenceHelper;
import com.moxydemo.data.prefs.PreferenceHelperImpl;
import com.moxydemo.utils.ToastUtils;
import com.moxydemo.utils.impl.ToastUtilsImpl;

import org.greenrobot.greendao.database.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Vyacheslav on 31.05.2017.
 */
@Module
public class UtilsModule {

    @Singleton
    @Provides
    public ToastUtils provideToastUtils(ToastUtilsImpl impl) {
        return impl;
    }

    @Singleton
    @Provides
    public ServiceGenerator serviceGenerator() {
        return new ServiceGenerator();
    }

    @Singleton
    @Provides
    public Client provideClient(ServiceGenerator serviceGenerator) {
        return serviceGenerator.createService(Client.class);
    }

    @Singleton
    @Provides
    public ApiHelper provideApiHelper(ApiHelperImpl impl) {
        return impl;
    }

    @Singleton
    @Provides
    public DataManager provideDataManager(DataManagerImpl impl) {
        return impl;
    }

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Singleton
    @Provides
    public PreferenceHelper providePreferenceHelper(PreferenceHelperImpl impl) {
        return impl;
    }

    @Singleton
    @Provides
    public DaoSession provideDaoSession(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, Const.DB_NAME);
        Database database = helper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(database);
        return daoMaster.newSession();
    }

    @Singleton
    @Provides
    public DBHelper provideDBHelper(DBHelperImpl impl) {
        return impl;
    }

}
