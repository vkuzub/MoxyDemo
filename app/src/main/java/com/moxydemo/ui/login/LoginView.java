package com.moxydemo.ui.login;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.moxydemo.base.view.HideShowContentView;

/**
 * Created by Vyacheslav on 22.05.2017.
 */

@StateStrategyType(OneExecutionStateStrategy.class)
public interface LoginView extends MvpView, HideShowContentView {

    void showEmailError(String str);

    void showPasswordError(String str);

    void clearEmailError();

    void clearPasswordError();

    void setButtonLoginEnabled();

    void setButtonLoginDisabled();

    void startCitiesListActivity();
}
