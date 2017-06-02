package com.moxydemo.data.prefs;

/**
 * Created by Vyacheslav on 01.06.2017.
 */

public interface PreferenceHelper {

    boolean isUserAuthorized();

    void saveUserToken(String token);

}
