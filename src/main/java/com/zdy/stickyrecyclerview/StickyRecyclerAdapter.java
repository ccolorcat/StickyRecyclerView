package com.zdy.stickyrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created with Android Studio.
 * Time: 9:13  2017/10/30
 * Author:ZhuangYuan
 */

public class StickyRecyclerAdapter extends RecyclerView.Adapter {

    private List<CityEntity> cityEntities;

    public StickyRecyclerAdapter(List<CityEntity> cityEntityList) {
        this.cityEntities = cityEntityList;
    }

    public String getTitleContent(int position) {
        return cityEntities.get(position).getProvinceName();
    }


    public View getTitleView(int position, LayoutInflater layoutInflater) {
        if (cityEntities.size() <= position) {
            return null;
        }
        View view = layoutInflater.inflate(R.layout.item_province, null, false);
        TextView textView = view.findViewById(R.id.item_province_name);
        textView.setText(cityEntities.get(position).getProvinceName());
        return view;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CityViewHolder cityViewHolder = (CityViewHolder) holder;
        cityViewHolder.resetView(position);
    }

    @Override
    public int getItemCount() {
        return cityEntities.size();
    }

    class CityViewHolder extends RecyclerView.ViewHolder {
        private TextView cityName;
        private TextView cityPlateNumber;

        public CityViewHolder(View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.item_city_name);
            cityPlateNumber = itemView.findViewById(R.id.item_city_plate_number);
        }

        public void resetView(int postion) {
            cityName.setText(cityEntities.get(postion).getCityName());
            cityPlateNumber.setText(cityEntities.get(postion).getCityPlateNumber());
        }
    }
}
