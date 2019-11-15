package com.unsw.valerie;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CollFragment extends Fragment {
    private RecyclerView rv;
    private ArrayList<CatApi> dataList;
    private ApiAdapter rvAdapter;

    public static CollFragment newInstance() {
        return new CollFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_coll, container, false);
        rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(dividerItemDecoration);
        dataList = new ArrayList<>();
        rvAdapter = new ApiAdapter(getActivity(), dataList);
        rv.setAdapter(rvAdapter);
        rvAdapter.setOnItemClick(new ApiAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(),CatActivity.class);
                intent.putExtra("cat", dataList.get(position));
                startActivity(intent);
            }
        });
        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        dataList.clear();
        CollHelper collHelper = new CollHelper(getActivity());
        SQLiteDatabase db = collHelper.getReadableDatabase();
        Cursor cursor = db.query("cat", null, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() && (cursor.getString(1) != null)){
            CatApi cat = new CatApi();
            cat.id = cursor.getString(cursor.getColumnIndex("id"));
            cat.name = cursor.getString(cursor.getColumnIndex("name"));
            cat.country_code = cursor.getString(cursor.getColumnIndex("country_code"));
            cat.description = cursor.getString(cursor.getColumnIndex("description"));
            cat.life_span = cursor.getString(cursor.getColumnIndex("life_span"));
            cat.wikipedia_url = cursor.getString(cursor.getColumnIndex("wikipedia_url"));
            cat.shedding_level = cursor.getString(cursor.getColumnIndex("shedding_level"));
            cat.temperament = cursor.getString(cursor.getColumnIndex("temperament"));
            cat.dog_friendly = cursor.getString(cursor.getColumnIndex("dog_friendly"));
            cat.origin = cursor.getString(cursor.getColumnIndex("origin"));
            CatApi.Weight weight = new CatApi.Weight();
            weight.imperial = cursor.getString(cursor.getColumnIndex("imperial"));
            weight.metric = cursor.getString(cursor.getColumnIndex("metric"));
            cat.weight = weight;
            dataList.add(cat);
            cursor.moveToNext();
        }
        rvAdapter.notifyDataSetChanged();
    }
}
