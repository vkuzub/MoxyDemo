package com.moxydemo.ui.favourites_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.moxydemo.R;
import com.moxydemo.base.BaseMvpAdapter;
import com.moxydemo.base.OnAdapterEmptyListener;
import com.moxydemo.data.db.model.City;
import com.moxydemo.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Vyacheslav on 06.06.2017.
 */

public class CitiesFavouritesAdapter extends BaseMvpAdapter<CitiesFavouritesAdapter.ViewHolder> implements CitiesFavouritesView {

    public static final String CHILD_ID = "20";

    @InjectPresenter
    CitiesFavouritesPresenter citiesFavouritesPresenter;

    private OnAdapterEmptyListener adapterEmptyListener;
    private List<City> data;

    void addData(List<City> cities) {
        data = new ArrayList<>(cities);
        notifyDataSetChanged();
        getMvpDelegate().onAttach();
    }

    public CitiesFavouritesAdapter(MvpDelegate<?> parentDelegate, String childId, OnAdapterEmptyListener listener) {
        super(parentDelegate, childId);
        this.adapterEmptyListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_favourited, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        City city = data.get(position);
        if (city != null) {
            holder.tvCityName.setText(city.getCity());
            holder.ivFavourite.setOnClickListener(v -> onStarClick(city));
        }
    }

    void onStarClick(City city) {
        citiesFavouritesPresenter.removeFromFavourites(city);
    }

    @Override
    public int getItemCount() {
        if (!CollectionUtils.isNullOrEmpty(data)) {
            return data.size();
        }
        return 0;
    }

    @Override
    public void removeRow(City city) {
        Timber.i("Remove row %s", city);
        if (city != null) {
            int pos = data.indexOf(city);
            if (pos != -1) {
                data.remove(city);
                notifyItemRemoved(pos);
                if (CollectionUtils.isNullOrEmpty(data)) {
                    adapterEmptyListener.onAdapterEmpty();
                }
            } else {
                Timber.i("pos == -1");
            }
        }
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
