package adapters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flistermovieapp.R;
import com.example.flistermovieapp.databinding.ActivityMovieDetailsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import models.Movie;
import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;
    /*TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivPoster; //Change this to thumbnail
    TextView Rdate;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());

        // layout of activity is stored in a special property called root
        View view = binding.getRoot();
        setContentView(view);

        //setContentView(R.layout.activity_movie_details);


        // Unwrap the movie sent via intent
        //movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        //Log.i("MovieDetailsActivity", String.format("Showing Details for %s", movie.getTitle()));

        //Unwrap the movie parsed in via parcel using its simple name as key
        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        binding.tvTitle.setText(movie.getTitle());
        binding.tvOverview.setText(movie.getOverview());
        binding.Rdate.setText(movie.getReleased());

        float voteAverage = movie.getVoteAverage().floatValue();
        binding.rbVoteAverage.setRating(voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

        String imageUrl;
        int gofu;
        //If phone is in landscape then we do imageUrl

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            imageUrl = movie.getBackdropPath();
            gofu = R.drawable.flicks_backdrop_placeholder;
        }else{
            imageUrl = movie.getBackdropPath();
            gofu = R.drawable.flicks_backdrop_placeholder;
        }


        Glide.with(this)
                .load(imageUrl)
                .placeholder(gofu)
                .error(gofu)
                .into(binding.ivPoster);

        AsyncHttpClient client = new AsyncHttpClient();
        String URL = "https://api.themoviedb.org/3/movie/"+ movie.getId()+ "/videos?api_key=c622cdaa84b981fece025799de236e2b";
        client.get(URL, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i("Movie", "onSuccess");

                        JSONObject jsonObject = json.jsonObject;
                        try {

                            final String youtubeKey = jsonObject.getJSONArray("results").getJSONObject(0).getString("key");

                            binding.ivPoster.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(MovieDetailsActivity.this, "Testing", Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                                    i.putExtra("kes", youtubeKey);
                                    startActivity(i);

                                }
                            });


                        } catch (JSONException e) {
                            Log.e("Movie", "Hit Json exception, e");

                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e("mOVIE", "oNfailure", throwable);
                    }
        });


   }


}