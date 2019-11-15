package com.unsw.valerie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private RequestQueue mRequestQueue;
    private RecyclerView rv;
    private Button search;
    String url = "https://api.thecatapi.com/v1/breeds/search";
    private ApiAdapter rvAdapter;
    private ArrayList<CatApi> dataList;
    private EditText content;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home, container, false);
        rv = view.findViewById(R.id.rv);
        content = view.findViewById(R.id.content);
        search = view.findViewById(R.id.search);
        search.setOnClickListener(this);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRequestQueue = Volley.newRequestQueue(getActivity());
    }
    private void fetchJsonResponse(String content) {
        final String reqUrl =url+ "?q="+content+"&api_key=e9ae50fc-094c-4fce-9bd9-dfaa2384472d";

        StringRequest req =
                new StringRequest(Request.Method.GET, reqUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ta",response);
                        Gson gson =  new Gson();
                        CatApi[] objectsArray = gson.fromJson(response, CatApi[].class);
                        List<CatApi> objectsList = Arrays.asList(objectsArray);
                        dataList.clear();
                        dataList.addAll(objectsList);
                        rvAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });


        mRequestQueue.add(req);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search:
                String string = content.getText().toString();
                fetchJsonResponse(string);
                break;
        }
    }

}
