package adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flistermovieapp.R;

import java.util.List;

import models.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    public interface OnClickListener{
        void onItemClicked(int position);
    }


    Context context;
    List<Movie> movies;
    OnClickListener onClickListener;

    public MovieAdapter(Context context, List<Movie> movies, OnClickListener onClickListener) {
        this.context = context;
        this.movies = movies;
        this.onClickListener = onClickListener;
    }

      //Usually involves inflating the layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent,false);
        return  new ViewHolder(movieView);
    }

    //Involves populating the data into the item through the holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Get the movie at the passed position
        Movie movie = movies.get(position);

        //Bind the movie data into the View hold
        holder.bind(movie);

    }

    //Returns the total items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);


        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            int gofu;
            //If phone is in landscape then we do imageUrl

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.getBackdropPath();
                gofu = R.drawable.flicks_backdrop_placeholder;
            }else{
                imageUrl = movie.getPosterPath();
                gofu = R.drawable.hshdhdhdhd;
            }


            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(gofu)
                    .error(gofu)
                    .into(ivPoster);


        }
    }
}
