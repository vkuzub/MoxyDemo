package com.moxydemo.data.prefs;

import android.content.SharedPreferences;
import android.text.TextUtils;

import javax.inject.Inject;

/**
 * Created by Vyacheslav on 01.06.2017.
 */

public class PreferenceHelperImpl implements PreferenceHelper {

    private static final String TOKEN = "token";

    private SharedPreferences preferences;

    @Inject
    public PreferenceHelperImpl(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public boolean isUserAuthorized() {
        return !TextUtils.isEmpty(preferences.getString(TOKEN, ""));
    }

    @Override
    public void saveUserToken(String token) {
        preferences.edit().putString(TOKEN, token).apply();
    }
}
