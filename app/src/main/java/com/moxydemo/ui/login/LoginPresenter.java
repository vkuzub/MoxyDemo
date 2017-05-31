package com.moxydemo.ui.login;

import android.widget.EditText;

import com.moxydemo.data.network.model.LoginResponse;

/**
 * Created by Vyacheslav on 31.05.2017.
 */

public interface LoginPresenter {

    void onButtonLoginClick(String email, String password);

    void initRxSubscriptions(EditText etPassword, EditText etEmail);

    boolean fakeCheckAccountData(String email, String password);

    void doLogin();

    void doLoginFail();

    void onLoginSuccess(LoginResponse loginResponse);
}
