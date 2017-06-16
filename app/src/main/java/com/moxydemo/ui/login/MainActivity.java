package com.moxydemo.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.moxydemo.R;
import com.moxydemo.base.BaseMvpViewActivity;
import com.moxydemo.ui.cities_list.CitiesListActivity;
import com.moxydemo.utils.ButtonTintUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseMvpViewActivity implements LoginView {

    @InjectPresenter
    LoginPresenterImpl loginPresenter;

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.tilEmail)
    TextInputLayout tilEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.tilPassword)
    TextInputLayout tilPassword;
    @BindView(R.id.btnLogIn)
    Button btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initRxSubscriptions() {
        loginPresenter.initRxSubscriptions(etPassword, etEmail);
    }

    @Override
    public void onErrorViewClick() {
        loginPresenter.onErrorViewClick();
    }

    @Override
    public void showEmailError(String str) {
        tilEmail.setError(str);
    }

    @Override
    public void showPasswordError(String str) {
        tilPassword.setError(str);
    }

    @Override
    public void clearEmailError() {
        tilEmail.setError("");
    }

    @Override
    public void clearPasswordError() {
        tilPassword.setError("");
    }

    @Override
    public void setButtonLoginEnabled() {
        ButtonTintUtils.setButtonEnabled(btnLogIn, true, R.color.colorPrimary, android.R.color.darker_gray);
    }

    @Override
    public void setButtonLoginDisabled() {
        ButtonTintUtils.setButtonEnabled(btnLogIn, false, R.color.colorPrimary, android.R.color.darker_gray);
    }

    @OnClick(R.id.btnLogIn)
    public void onButtonLoginClick() {
        loginPresenter.onButtonLoginClick(etEmail.getText().toString(), etPassword.getText().toString());
    }

    @Override
    public void startCitiesListActivity() {
        startActivity(new Intent(this, CitiesListActivity.class));
        finish();
    }
}
