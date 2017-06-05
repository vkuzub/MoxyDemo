package com.moxydemo.ui.cities_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.moxydemo.R;
import com.moxydemo.base.BaseMvpViewActivity;
import com.moxydemo.data.db.model.City;
import com.moxydemo.ui.login.MainActivity;
import com.moxydemo.utils.EndlessRecyclerViewScrollListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CitiesListActivity extends BaseMvpViewActivity implements CitiesListView {

    @InjectPresenter
    CitiesListPresenterImpl citiesListPresenter;


    @BindView(R.id.rvData)
    RecyclerView rvData;
    @BindView(R.id.srlData)
    SwipeRefreshLayout srlData;

    EndlessRecyclerViewScrollListener rvScrollListener;
    CitiesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_list);
        ButterKnife.bind(this);
        initViews();
    }

    public void initViews() {
        srlData.setOnRefreshListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvData.setLayoutManager(manager);
        rvData.addItemDecoration(new DividerItemDecoration(this, manager.getOrientation()));
        rvData.setItemAnimator(new DefaultItemAnimator());
        adapter = new CitiesAdapter(getMvpDelegate(), CitiesAdapter.CHILD_ID);
        rvData.setAdapter(adapter);
        rvScrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore() {
                citiesListPresenter.onListToEndScrolled();
            }
        };
        rvData.addOnScrollListener(rvScrollListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cities_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logOut:
                onLogOutClick();
                break;
            case R.id.favourites:
                onFavouritesClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLogOutClick() {
        citiesListPresenter.logOut();
    }

    @Override
    public void onFavouritesClick() {

    }

    @Override
    public void showSwipeRefresh(boolean show) {
        srlData.setRefreshing(show);
    }

    @Override
    public void fillContent(List<City> list) {
        adapter.addData(list);
    }

    @Override
    public void clearContent() {
        adapter.clear();
    }

    @Override
    public void startLoginActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void resetPaginationState() {
        rvScrollListener.resetState();
    }

    @Override
    public void onRefresh() {
        citiesListPresenter.onRefresh();
    }
}
