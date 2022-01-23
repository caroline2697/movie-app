package net.caroline.movieapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import net.caroline.movieapp.DbHelper;
import net.caroline.movieapp.Movie;
import net.caroline.movieapp.MovieAdapter;
import net.caroline.movieapp.R;

import java.util.ArrayList;

public class WatchlistActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private ArrayList<Movie> movies;
    private DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);
        dbHelper = new DbHelper(this);
        movies = dbHelper.getAllMovies();

        //Mengirimkan data movies kedalam adapter
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        adapter = new MovieAdapter(movies);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(WatchlistActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onResume(){
        super.onResume();
        movies = dbHelper.getAllMovies();
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        adapter = new MovieAdapter(movies);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(WatchlistActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}


