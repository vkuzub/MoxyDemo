package com.moxydemo.ui.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moxydemo.R;
import com.moxydemo.data.db.model.City;
import com.moxydemo.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vyacheslav on 12.06.2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<City> data = new ArrayList<>();

    public void setData(List<City> newData) {
        data.clear();
        if (newData != null)
            data.addAll(newData);
        notifyDataSetChanged();
    }

    public void clearData() {
        data.clear();
        notifyDataSetChanged();
    }

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_search, parent, false);
        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        City city = data.get(position);
        if (city != null) {
            holder.tvCityName.setText(city.getCity());
        }
    }


    @Override
    public int getItemCount() {
        if (!CollectionUtils.isNullOrEmpty(data)) {
            return data.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvCityName)
        TextView tvCityName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
