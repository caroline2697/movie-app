package net.caroline.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import net.caroline.movieapp.activity.DetailActivity;

import java.util.ArrayList;
//Adapter
//Digunakan untuk menghubungkan data movie dan menampilkan datanya kedalam view holder

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
        private ArrayList<Movie> movieList;
    public MovieAdapter(ArrayList<Movie> movies){
        this.movieList = movies;
    }

    @NonNull
    @Override

    //Membuat satu tampilan dan akan dikembalikan
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_list, parent, false);
        return new MovieHolder(view);
    }

    @Override
    //Menghubungkan data dengan view holder pada posisi di RecycleView
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        //Holder
        //Berisi tampilan informasi -> menampilkan satu item dari layout item
        final Movie movie = movieList.get(position);
        holder.rating.setText(movie.getRating().toString());
        holder.title.setText(movie.getTitle());
        holder.overview.setText(movie.getOverview());

        Picasso.get()
                .load("https://themoviedb.org/t/p/w500/"+movie.getPoster())
                .into(holder.poster);

        //Click  -> Mengarahkan ke DetailActivity
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent data movies
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", movie.getId());
                bundle.putString("poster", movie.getPoster());
                bundle.putString("backdrop", movie.getBackdrop());
                bundle.putString("title", movie.getTitle());
                bundle.putString("overview", movie.getOverview());
                bundle.putString("release", movie.getRelease());
                bundle.putDouble("rating", movie.getRating());

                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    //Mengembalikan data sejumlah item yang dapat ditampilkan
    public int getItemCount() {
        return (movieList != null) ? movieList.size() : 0 ;
    }

    public class MovieHolder extends RecyclerView.ViewHolder{
        ImageView poster;
        TextView title, overview, rating, release;
        LinearLayout linearLayout;
        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            //Menampilkan data pada layout_id
            poster=itemView.findViewById(R.id.posterMovie);
            title=itemView.findViewById(R.id.movieTitle);
            overview=itemView.findViewById(R.id.movieOverview);
            release=itemView.findViewById(R.id.realeseDate);
            rating=itemView.findViewById(R.id.movieRating);
            linearLayout = itemView.findViewById(R.id.linearList);

        }
    }
}
