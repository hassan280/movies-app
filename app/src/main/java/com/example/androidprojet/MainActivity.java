package com.example.androidprojet;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidprojet.Adapters.RecyclerViewAdapter;
import com.example.androidprojet.R;
import com.example.androidprojet.models.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener {
    private JsonArrayRequest request ;
    private RequestQueue requestQueue ;
    private ArrayList<MovieModel> movielist ;
    private RecyclerView recyclerView ;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActionBar actionBar;
        actionBar = getSupportActionBar();


        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#000000"));


        actionBar.setBackgroundDrawable(colorDrawable);




        actionBar.setTitle("Playing Now");


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        movielist = new ArrayList<>() ;

        requestQueue = Volley.newRequestQueue(this);


        parseJSON();



    }


    private void parseJSON(){
       for(int i=1;i<11;i++){
            String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=1780e10e6015df85f3b6dbc8060120f2&language=en-US&page="+i;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            JSONArray jsonArray = null;
                            try {
                                jsonArray = response.getJSONArray("results");
                                for(int i = 0 ; i < jsonArray.length() ; i++){
                                    JSONObject result = jsonArray.getJSONObject(i);

                                    String Title = result.getString("title");
                                    String ImageUrl = result.getString("poster_path");
                                    String Rating = String.valueOf(result.getDouble("vote_average"));
                                    String ID = String.valueOf(result.getInt("id"));
                                    String Language = result.getString("original_language");
                                    String Overview = result.getString("overview");
                                    String Release_Date = result.getString("release_date");
                                    String voteCount = result.getString("vote_count");

                                    movielist.add(new MovieModel(ID, Title, Language, Overview, ImageUrl, Release_Date, Rating, voteCount));
                                }

                                recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, movielist);
                                recyclerView.setAdapter(recyclerViewAdapter);


                                recyclerViewAdapter.setOnItemClickListener(MainActivity.this);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
           requestQueue.add(request);
        }

    }

    @Override
    public void onItemClick(int position) {
        Intent SingleMovieIntent = new Intent(this, Single_Movie.class);
        MovieModel clickedItem = movielist.get(position);

        SingleMovieIntent.putExtra("Extra_poster", clickedItem.getPoster_path());
        SingleMovieIntent.putExtra("Extra_language", clickedItem.getLanguage());
        SingleMovieIntent.putExtra("Extra_overview", clickedItem.getOverview());
        SingleMovieIntent.putExtra("Extra_release", clickedItem.getRelease_date());
        SingleMovieIntent.putExtra("Extra_rating", clickedItem.getRating());
        SingleMovieIntent.putExtra("Extra_title", clickedItem.getTitle());
        SingleMovieIntent.putExtra("Extra_Vote_Count", clickedItem.getVote_count());
        SingleMovieIntent.putExtra("Extra_id", clickedItem.getId());


        startActivity(SingleMovieIntent);
    }
}