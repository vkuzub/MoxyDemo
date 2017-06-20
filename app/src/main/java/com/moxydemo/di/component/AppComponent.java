package com.moxydemo.di.component;

import com.moxydemo.base.BaseMvpViewActivity;
import com.moxydemo.di.module.AppModule;
import com.moxydemo.di.module.UtilsModule;
import com.moxydemo.ui.cities_list.CitiesListPresenterImpl;
import com.moxydemo.ui.cities_list.CitiesStarPresenter;
import com.moxydemo.ui.details.DetailsPresenter;
import com.moxydemo.ui.favourites_list.CitiesFavouritesPresenter;
import com.moxydemo.ui.favourites_list.FavouritesPresenter;
import com.moxydemo.ui.login.LoginPresenterImpl;
import com.moxydemo.ui.search.SearchActivity;
import com.moxydemo.ui.search.SearchPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Vyacheslav on 31.05.2017.
 */

@Component(modules = {AppModule.class, UtilsModule.class})
@Singleton
public interface AppComponent {

    void inject(BaseMvpViewActivity activity);
    void inject(SearchActivity activity);

    void inject(LoginPresenterImpl presenter);
    void inject(CitiesListPresenterImpl presenter);
    void inject(CitiesStarPresenter presenter);
    void inject(FavouritesPresenter presenter);
    void inject(CitiesFavouritesPresenter presenter);
    void inject(SearchPresenter presenter);
    void inject(DetailsPresenter presenter);

}
