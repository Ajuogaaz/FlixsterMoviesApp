package adapters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flistermovieapp.R;

import org.parceler.Parcels;

import models.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivPoster;
    TextView Rdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        // Unwrap the movie sent via intent
        //movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        //Log.d("MovieDetailsActivity", String.format("Showing Details for %s", movie.getTitle()));
        tvTitle = findViewById(R.id.tvTitle);
        rbVoteAverage = findViewById(R.id.rbVoteAverage);
        tvOverview = findViewById(R.id.tvOverview);
        ivPoster = findViewById(R.id.ivPoster);
        Rdate = findViewById(R.id.Rdate);

        //Unwrap the movie parsed in via parcel using its simple name as key
        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        Rdate.setText(movie.getReleased());

        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

        String imageUrl;
        int gofu;
        //If phone is in landscape then we do imageUrl

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            imageUrl = movie.getBackdropPath();
            gofu = R.drawable.flicks_backdrop_placeholder;
        }else{
            imageUrl = movie.getPosterPath();
            gofu = R.drawable.hshdhdhdhd;
        }


        Glide.with(this)
                .load(imageUrl)
                .placeholder(gofu)
                .error(gofu)
                .into(ivPoster);

   }
}