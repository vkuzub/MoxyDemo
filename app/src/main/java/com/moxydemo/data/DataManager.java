package com.moxydemo.data;


import com.moxydemo.data.db.DBHelper;
import com.moxydemo.data.network.ApiHelper;
import com.moxydemo.data.prefs.PreferenceHelper;

/**
 * Created by Vyacheslav on 31.05.2017.
 */

public interface DataManager extends ApiHelper, PreferenceHelper, DBHelper {
}
