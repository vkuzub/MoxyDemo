package com.moxydemo.ui.cities_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moxydemo.R;
import com.moxydemo.data.db.model.City;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vyacheslav on 01.06.2017.
 */

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    private List<City> data = new ArrayList<>();

    public void addData(List<City> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    @Override
    public CitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CitiesAdapter.ViewHolder holder, int position) {
        City city = data.get(position);
        if (city != null) {
            holder.tvCityName.setText(city.getCity());
        }
    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        return 0;
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
