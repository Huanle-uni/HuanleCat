package com.unsw.valerie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class CatActivity extends AppCompatActivity {

    private CatApi catApi;
    private ImageView image;
    private TextView name;
    private TextView tv_des;
    private TextView tvOrigin;
    private TextView tvCountryCode;
    private TextView life_span;
    private TextView wikipedia_url;
    private TextView temperament;
    private TextView shedding_level;
    private TextView dog_friendly;
    private TextView weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);
        catApi = (CatApi) getIntent().getSerializableExtra("cat");

         image = findViewById(R.id.image);
         name = findViewById(R.id.name);
         tv_des = findViewById(R.id.tv_des);
         tvOrigin = findViewById(R.id.origin);
         tvCountryCode = findViewById(R.id.country_code);
         life_span = findViewById(R.id.life_span);
         wikipedia_url = findViewById(R.id.wikipedia_url);
         temperament = findViewById(R.id.temperament);
         shedding_level = findViewById(R.id.shedding_level);
         dog_friendly = findViewById(R.id.dog_friendly);
         weight = findViewById(R.id.weight);

        initContent(catApi);

    }

    private void initContent(CatApi catApi) {
        name.setText(catApi.name);
        tv_des.setText(catApi.description);
        tvOrigin.setText("Origin :"+catApi.origin);
        tvCountryCode.setText("Country code :"+catApi.country_code);
        life_span.setText("Life span :"+catApi.life_span);
        wikipedia_url.setText("Wiki :"+catApi.wikipedia_url);
        temperament.setText("Temperament :"+catApi.temperament);
        shedding_level.setText("Shedding level :"+catApi.shedding_level);
        dog_friendly.setText("Dog friendly :"+catApi.dog_friendly);
        weight.setText("weight :"+catApi.weight.metric+"(metric)  "+catApi.weight.imperial+"(imperial)  ");
        getImage(catApi.id);
    }

    public void getImage(String id) {


        String  url = "https://api.thecatapi.com/v1/images/search?breed_ids="+id+"&api_key=r3NyamjQQ643Hgy9cZauDFwqR6z4GBTB";

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest req =
                new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson =  new Gson();
                        ImageApi[] arr = gson.fromJson(response, ImageApi[].class);
                        if (arr!=null&&arr.length>0){
                            Glide.with(CatActivity.this).load(arr[0].url).into(image);
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(req);
    }

    public void collect(View view) {
        CollHelper collHelper = new CollHelper(this);
        SQLiteDatabase db = collHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", catApi.id);
        values.put("name", catApi.name);
        values.put("country_code", catApi.country_code);
        values.put("description", catApi.description);
        values.put("life_span", catApi.life_span);
        values.put("wikipedia_url", catApi.wikipedia_url);
        values.put("shedding_level", catApi.shedding_level);
        values.put("temperament", catApi.temperament);
        values.put("dog_friendly", catApi.dog_friendly);
        values.put("origin", catApi.origin);
        values.put("imperial", catApi.weight.imperial);
        values.put("metric", catApi.weight.imperial);
        db.insert("cat", null,values);
    }
}
