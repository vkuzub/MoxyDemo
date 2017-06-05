package com.moxydemo.ui.cities_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.moxydemo.R;
import com.moxydemo.data.db.model.City;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Vyacheslav on 01.06.2017.
 */

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> implements CitiesStarView {

    public static final String CHILD_ID = "10";

    @InjectPresenter
    CitiesStarPresenter citiesStarPresenter;

    public static final int TYPE_NON_FAVOURITED = 0;
    public static final int TYPE_FAVOURITED = 1;

    private MvpDelegate<CitiesAdapter> mvpDelegate;
    private MvpDelegate<?> parentDelegate;
    private String childId;
    private boolean isPresenterAttached;

    private List<City> data = new ArrayList<>();

    public CitiesAdapter(MvpDelegate<?> parentDelegate, String childId) {
        this.parentDelegate = parentDelegate;
        this.childId = childId;

        getMvpDelegate().onCreate();
    }

    public MvpDelegate<CitiesAdapter> getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new MvpDelegate<>(this);
            mvpDelegate.setParentDelegate(parentDelegate, childId);
        }
        return mvpDelegate;
    }

    public void addData(List<City> data) {
        this.data.addAll(data);
        citiesStarPresenter.setDataCount(this.data.size());
        notifyDataSetChanged();
        isAllDataToAdapterAdded();
        Timber.d("Added more data %s", this.data.toString());
    }

    //check is all data was added to adapter
    // FIXME: 05.06.2017
    public void isAllDataToAdapterAdded() {
        if (!isPresenterAttached)
            if (data.size() == citiesStarPresenter.getDataCount()) {
                getMvpDelegate().onAttach();
                isPresenterAttached = true;
            }
    }

    public void clear() {
        data.clear();
        citiesStarPresenter.resetDataCount();
        notifyDataSetChanged();
    }

    @Override
    public CitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_FAVOURITED:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_favourited, parent, false);
                break;
            case TYPE_NON_FAVOURITED:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CitiesAdapter.ViewHolder holder, int position) {
        City city = data.get(position);
        if (city != null) {
            holder.tvCityName.setText(city.getCity());
            holder.ivFavourite.setOnClickListener(v -> onUpdateCityLike(city));
        }
    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).getFavourited()) {
            return TYPE_FAVOURITED;
        }
        return TYPE_NON_FAVOURITED;
    }

    @Override
    public void onUpdateCityLike(City city) {
        citiesStarPresenter.updateLike(city);
    }

    @Override
    public void updateRow(City city) {
        Timber.i("Update row %s", city);
        int pos = data.indexOf(city);
        if (pos != -1) {
            data.remove(city);
            data.add(pos, city);
            notifyItemChanged(pos);
        } else {
            Timber.e("Pos == -1 %s", city);
        }
    }

    @Override
    public void clearQueue() {
        Timber.i("clear queue");
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        Timber.i("OnDetachFromRecyclerView");
        getMvpDelegate().onDetach();
        getMvpDelegate().onDestroy();
        super.onDetachedFromRecyclerView(recyclerView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvCityName)
        TextView tvCityName;
        @BindView(R.id.ivFavourite)
        ImageView ivFavourite;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
