package com.moxydemo.ui.login;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.arellomobile.mvp.InjectViewState;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.moxydemo.App;
import com.moxydemo.R;
import com.moxydemo.base.BasePresenter;
import com.moxydemo.data.DataManager;
import com.moxydemo.data.network.model.LoginResponse;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Vyacheslav on 22.05.2017.
 */

@InjectViewState
public class LoginPresenterImpl extends BasePresenter<LoginView> implements LoginPresenter {

    @Inject
    Context context;
    @Inject
    DataManager dataManager;

    @Override
    public void attachView(LoginView view) {
        super.attachView(view);
        App.getAppComponent().inject(this);
        if (dataManager.isUserAuthorized()) {
            getViewState().startCitiesListActivity();
        }
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showContent();
        getViewState().setButtonLoginDisabled();
    }

    @Override
    public void onButtonLoginClick(String email, String password) {
        getViewState().showLoading();
        if (fakeCheckAccountData(email, password)) {
            doLogin();
        } else {
            doLoginFail();
        }
    }

    public void initRxSubscriptions(EditText etPassword, EditText etEmail) {

        int strLength = 4;

        Observable<CharSequence> availablePassword = RxTextView.textChanges(etPassword);

        Observable<CharSequence> availableEmail = RxTextView.textChanges(etEmail);

        Observable<Boolean> availableLogin = Observable.combineLatest(availableEmail, availablePassword, (s1, s2) ->
                s1.length() >= strLength && s2.length() >= strLength
        );

        rxAddSubscription(availablePassword.subscribe(
                s -> {
                    if (TextUtils.isEmpty(s) || s.length() >= strLength) {
                        getViewState().clearPasswordError();
                    } else {
                        getViewState().showPasswordError("Min length: " + strLength);
                    }
                }
        ));
        rxAddSubscription(availableEmail.subscribe(
                s -> {
                    if (TextUtils.isEmpty(s) || s.length() >= strLength) {
                        getViewState().clearEmailError();
                    } else if (s.length() < strLength) {
                        getViewState().showEmailError("Min length: " + strLength);
                    }
                }
        ));
        rxAddSubscription(availableLogin.subscribe(b -> {
                    if (b) {
                        getViewState().setButtonLoginEnabled();
                    } else {
                        getViewState().setButtonLoginDisabled();
                    }
                }
        ));
    }

    @Override
    public boolean fakeCheckAccountData(String email, String password) {
        String fakeEmail = context.getString(R.string.hint_email);
        String fakePass = context.getString(R.string.hint_password);
        if (email.equals(fakeEmail) && password.equals(fakePass)) {
            return true;
        }
        return false;
    }

    @Override
    public void doLogin() {
        rxAddSubscription(
                dataManager.doLogin()
                        .delay(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .subscribe(loginResponse -> {
                                    onLoginSuccess(loginResponse);
                                },
                                throwable -> {
                                    getViewState().showError(R.string.oops_something_went_wrong);
                                    logException(throwable);
                                })
        );
    }

    @Override
    public void doLoginFail() {
        rxAddSubscription(
                dataManager.doLoginFailed()
                        .delay(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .subscribe(loginResponse -> {
                                    onLoginSuccess(loginResponse);
                                },
                                throwable -> {
                                    getViewState().showError(R.string.oops_something_went_wrong);
                                    logException(throwable);
                                })
        );
    }

    @Override
    public void onLoginSuccess(LoginResponse loginResponse) {
        if (loginResponse != null) {
            if (loginResponse.getStatus().equals("ok")) {
                getViewState().showMessage(context.getString(R.string.login_success));
                dataManager.saveUserToken(loginResponse.getToken());
                getViewState().startCitiesListActivity();
            } else if (loginResponse.getStatus().equals("fail")) {
                getViewState().showMessage(loginResponse.getMessage());
            }
        }
    }

    @Override
    public void onErrorViewClick() {
        getViewState().showContent();
    }
}
