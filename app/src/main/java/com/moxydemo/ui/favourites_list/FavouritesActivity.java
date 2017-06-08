package com.moxydemo.ui.favourites_list;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.moxydemo.R;
import com.moxydemo.base.BaseMvpViewActivity;
import com.moxydemo.data.db.model.City;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class FavouritesActivity extends BaseMvpViewActivity implements FavouritesView {

    @InjectPresenter
    FavouritesPresenter presenter;

    @BindView(R.id.contentView)
    RecyclerView rvData;
    private CitiesFavouritesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adapter = new CitiesFavouritesAdapter(getMvpDelegate(), CitiesFavouritesAdapter.CHILD_ID);
        rvData.setLayoutManager(linearLayoutManager);
        rvData.setItemAnimator(new DefaultItemAnimator());
        rvData.setAdapter(adapter);
    }

    @Override
    public void fillContent(List<City> list) {
        Timber.i("fill content");
        adapter.addData(list);
    }
}
