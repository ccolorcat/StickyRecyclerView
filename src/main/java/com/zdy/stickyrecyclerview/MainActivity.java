package com.zdy.stickyrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<CityEntity> cityEntities = new ArrayList<>();
    private StickyRecyclerAdapter stickyRecyclerAdapter;
    private StickyItemDecoration stickyItemDecoration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.sticky_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        stickyItemDecoration = StickyItemDecoration.Builder.init(new StickyItemDecoration.TitleListener() {
            @Override
            public String getTitleContent(int position) {
                return stickyRecyclerAdapter.getTitleContent(position);
            }

            @Override
            public View getTitleView(int position) {
                return stickyRecyclerAdapter.getTitleView(position, getLayoutInflater());
            }
        }).build();
        recyclerView.addItemDecoration(stickyItemDecoration);
        recyclerView.setAdapter(stickyRecyclerAdapter = new StickyRecyclerAdapter(cityEntities));
    }


    private void initData() {
        try {
            InputStream inputStream = getResources().getAssets().open("city.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                CityEntity cityEntity = new CityEntity();
                cityEntity.setProvinceName(readLine.substring(0, readLine.indexOf("&")));
                cityEntity.setCityName(readLine.substring(readLine.indexOf("&") + 1, readLine.indexOf("*")));
                cityEntity.setCityPlateNumber(readLine.substring(readLine.indexOf("*") + 1));
                cityEntities.add(cityEntity);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
