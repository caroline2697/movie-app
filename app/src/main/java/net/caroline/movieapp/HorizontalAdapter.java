package net.caroline.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import net.caroline.movieapp.activity.DetailActivity;

import java.util.ArrayList;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalHolder> {
    private ArrayList<Movie> movieList;
    public HorizontalAdapter(ArrayList<Movie> movies){
        this.movieList = movies;
    }
    @NonNull
    @Override

    public HorizontalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.trending_movie, parent, false);
        return new HorizontalHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalHolder holder, int position) {
        final Movie movie = movieList.get(position);
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
    public int getItemCount() {
        return (movieList != null) ? movieList.size() : 0 ;
    }

    public class HorizontalHolder extends RecyclerView.ViewHolder{
        ImageView poster;
        LinearLayout linearLayout;
        public HorizontalHolder(@NonNull View itemView) {
            super(itemView);
            //Menampilkan data pada layout_id
            poster=itemView.findViewById(R.id.posterMovie);
            linearLayout = itemView.findViewById(R.id.horizontalList);

        }
    }
}
