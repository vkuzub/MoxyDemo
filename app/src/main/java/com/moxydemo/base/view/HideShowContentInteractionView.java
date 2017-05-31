package com.moxydemo.base.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Vyacheslav on 22.05.2017.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface HideShowContentInteractionView extends MvpView {

    void onErrorViewClick();

    void onEmptyViewClick();

}
