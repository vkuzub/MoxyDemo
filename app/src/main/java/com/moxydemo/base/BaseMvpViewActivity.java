package com.moxydemo.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.moxydemo.App;
import com.moxydemo.R;
import com.moxydemo.base.presenter.HideShowContentPresenter;
import com.moxydemo.base.view.HideShowContentInteractionView;
import com.moxydemo.base.view.HideShowContentView;
import com.moxydemo.utils.ToastUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import icepick.Icepick;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Vyacheslav on 22.05.2017.
 */

public abstract class BaseMvpViewActivity extends MvpAppCompatActivity
        implements HideShowContentView, HideShowContentInteractionView, RxSupport, IcepickSupport {

    @InjectPresenter
    HideShowContentPresenter hscPresenter;

    @Nullable
    @BindView(R.id.loadingView)
    View loadingView;
    @Nullable
    @BindView(R.id.errorView)
    View errorView;
    @Nullable
    @BindView(R.id.emptyView)
    View emptyView;
    @Nullable
    @BindView(R.id.contentView)
    View contentView;

    @Inject
    ToastUtils toastUtils;

    private CompositeSubscription compositeSubscription;

    protected View getLoadingView() {
        return loadingView;
    }

    protected View getContentView() {
        return contentView;
    }

    protected View getErrorView() {
        return errorView;
    }

    protected View getEmptyView() {
        return emptyView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        initRxSubscriptions();
    }

    @Override
    public void rxUnsubscribe() {
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
    }

    @Override
    public void rxAddSubscription(Subscription sub) {
        if (compositeSubscription != null) {
            compositeSubscription.add(sub);
        }
    }

    @Override
    public void initRxSubscriptions() {
    }

    @Override
    public void showContent() {
        if (getContentView() != null) {
            setVisibility(getContentView(), View.VISIBLE);
            setVisibility(getLoadingView(), View.GONE);
            setVisibility(getErrorView(), View.GONE);
            setVisibility(getEmptyView(), View.GONE);
        }
    }

    @Override
    public void showLoading() {
        if (getLoadingView() != null) {
            setVisibility(getContentView(), View.GONE);
            setVisibility(getLoadingView(), View.VISIBLE);
            setVisibility(getErrorView(), View.GONE);
            setVisibility(getEmptyView(), View.GONE);
        }
    }

    @Override
    public void showError(String msg) {
        if (getErrorView() != null) {
            setVisibility(getContentView(), View.GONE);
            setVisibility(getLoadingView(), View.GONE);
            setVisibility(getErrorView(), View.VISIBLE);
            setVisibility(getEmptyView(), View.GONE);

            ((TextView) getErrorView()).setText(msg);
        }
    }

    @Override
    public void showError(@StringRes int msg) {
        showError(getString(msg));
    }

    @Override
    public void showEmpty(String msg) {
        if (getEmptyView() != null) {
            setVisibility(getContentView(), View.GONE);
            setVisibility(getLoadingView(), View.GONE);
            setVisibility(getErrorView(), View.GONE);
            setVisibility(getEmptyView(), View.VISIBLE);

            ((TextView) getEmptyView()).setText(msg);
        }
    }

    @Override
    public void showEmpty(@StringRes int msg) {
        showEmpty(getString(msg));
    }

    @Override
    public void showMessage(String msg) {
        toastUtils.show(msg);
    }

    @Override
    public void showMessage(int msg) {
        toastUtils.show(msg);
    }

    @Optional
    @OnClick(R.id.errorView)
    public void onErrorViewClick() {
        //do nothing
    }

    @Optional
    @OnClick(R.id.emptyView)
    public void onEmptyViewClick() {
        //do nothing
    }

    @Override
    public <T> void restoreInstanceState(T t, Bundle savedInstanceState) {
        Icepick.restoreInstanceState(t, savedInstanceState);
    }

    @Override
    public <T> void saveInstanceState(T t, Bundle savedInstanceState) {
        Icepick.saveInstanceState(t, savedInstanceState);
    }

    public void setVisibility(View view, int visibility) {
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

    public HideShowContentPresenter getHscPresenter() {
        return hscPresenter;
    }
}
