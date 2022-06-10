package com.example.androidprojet;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidprojet.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Single_Movie extends AppCompatActivity {


    private JsonArrayRequest request ;
    private RequestQueue requestQueue ;
    public String movie_id, movie_revenue, movie_budget, movie_runTime;
    public TextView budget, revenue, runTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);


        ActionBar actionBar;
        actionBar = getSupportActionBar();


        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#725E5A"));


        actionBar.setBackgroundDrawable(colorDrawable);


        actionBar.setTitle("Movie Details");



        Intent intent = getIntent();
        String poster = intent.getStringExtra("Extra_poster");
        String title = intent.getStringExtra("Extra_title");
        String language = intent.getStringExtra("Extra_language");
        String overview = intent.getStringExtra("Extra_overview");
        String release = intent.getStringExtra("Extra_release");
        String rating = intent.getStringExtra("Extra_rating");
        String count = intent.getStringExtra("Extra_Vote_Count");
        movie_id = intent.getStringExtra("Extra_id");

        TextView name, lang, over, rel, rat, vote;
        ImageView post;

        post = findViewById(R.id.iv_movie_poster);
        name = findViewById(R.id.movie_title);
        lang = findViewById(R.id.movie_language);
        over = findViewById(R.id.movie_overview);
        rel = findViewById(R.id.movie_release_date);
        rat = findViewById(R.id.movie_rating);
        vote = findViewById(R.id.movie_vote_count);
        revenue = findViewById(R.id.movie_revenue);
        budget = findViewById(R.id.movie_budget);
        runTime = findViewById(R.id.movie_runtime);
        String image_url = "https://image.tmdb.org/t/p/w780/" + poster;

        Picasso.with(this).load(image_url).fit().centerInside().into(post);
        name.setText(title);
        lang.setText(language);
        over.setText(overview);
        rel.setText(release);
        rat.setText(rating);
        vote.setText(count);

        requestQueue = Volley.newRequestQueue(this);
        parseJSON();


    }



    private void parseJSON(){
        String url = "https://api.themoviedb.org/3/movie/" + movie_id + "?api_key=1780e10e6015df85f3b6dbc8060120f2";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            try {
                                movie_budget = String.valueOf(response.getLong("budget"));
                                movie_revenue = String.valueOf(response.getLong("revenue"));
                                movie_runTime = String.valueOf(response.getInt("runtime"));

                                budget.setText(movie_budget);
                                revenue.setText(movie_revenue);
                                runTime.setText(movie_runTime);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Single_Movie.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

}
