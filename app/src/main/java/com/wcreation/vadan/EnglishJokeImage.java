package com.wcreation.vadan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.wcreation.vadan.adapter.EnglishImageRecyclerViewAdapter;
import com.wcreation.vadan.modal.Anime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EnglishJokeImage extends AppCompatActivity {

    private final String JSON_URL = "https://drive.google.com/u/3/uc?id=1_tK_YZLvpsHrTX_fiklRLE4xeqyrGRzh&export=download";
    private JsonArrayRequest request ;
    private RequestQueue requestQueue ;
    private List<Anime> lstAnime ;
    private RecyclerView recyclerView ;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_joke_image);


        progressBar = (ProgressBar) findViewById(R.id.progressbarEJoke);

        lstAnime = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerviewiEJoke);
        jsonrequest();


    }


    private void jsonrequest() {

        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject  = null ;

                for (int i = 0 ; i < response.length(); i++ ) {


                    try {
                        jsonObject = response.getJSONObject(i) ;
                        Anime anime = new Anime() ;
                        anime.setImage_URI(jsonObject.getString("img"));
                        lstAnime.add(anime);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                setuprecyclerview(lstAnime);
                progressBar.setVisibility(View.INVISIBLE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(EnglishJokeImage.this);
        requestQueue.add(request) ;
    }


    private void setuprecyclerview(List<Anime> lstAnime) {


        EnglishImageRecyclerViewAdapter myadapter = new EnglishImageRecyclerViewAdapter(this,lstAnime) ;
        recyclerView.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(myadapter);

    }


    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }
}
