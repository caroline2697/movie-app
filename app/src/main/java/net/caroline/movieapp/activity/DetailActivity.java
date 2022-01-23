package net.caroline.movieapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.caroline.movieapp.DbHelper;
import net.caroline.movieapp.Movie;
import net.caroline.movieapp.R;

public class DetailActivity extends AppCompatActivity {
    private Button addWatchList;
    private DbHelper dbHelper;
    private int mId;
    private String mPoster, mTitle, mBackdrop, mOverview, mRelease;
    private Double mRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dbHelper = new DbHelper(this);

        //layout id
        ImageView poster = findViewById(R.id.posterMovie);
        ImageView backdrop = findViewById(R.id.backdropMovie);
        TextView title = findViewById(R.id.movieTitle);
        TextView overview = findViewById(R.id.movieOverview);
        TextView rating = findViewById(R.id.movieRating);
        TextView release = findViewById(R.id.realeseDate);

        addWatchList = findViewById(R.id.add_Watchlist);


        //data dari intent
        Bundle bundle = getIntent().getExtras();
        mId = bundle.getInt("id");
        mPoster = bundle.getString("poster");
        mBackdrop = bundle.getString("backdrop");
        mTitle = bundle.getString("title");
        mOverview = bundle.getString("overview");
        mRelease = bundle.getString("release");
        mRating = bundle.getDouble("rating".toString());

        boolean isSaved = dbHelper.getMovieById(mId);

        if(isSaved){
            addWatchList.setText(getString(R.string.savedWatchlist));
        } else {
            addWatchList.setText(getString(R.string.addToWatchlist));
        }

        //inputin
        title.setText(mTitle);
        overview.setText(mOverview);
        rating.setText(mRating.toString());
        release.setText(mRelease);

        Picasso.get()
                .load("https://themoviedb.org/t/p/w500/"+ mPoster)
                .fit().centerCrop()
                .into(poster);

        Picasso.get()
                .load("https://themoviedb.org/t/p/w500/"+ mBackdrop)
                .fit().centerCrop()
                .into(backdrop);


        //ADD TO WATCHLIST
        addWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DeleteorSave().execute();

            }
        });

    }

    private class DeleteorSave extends AsyncTask<Integer, Integer, Boolean>{

        @Override
        protected Boolean doInBackground(Integer... integers) {
            return dbHelper.getMovieById(mId);
        }

        @Override
        protected void onPostExecute(Boolean isSaved){
            if(isSaved){
                addWatchList.setText(getString(R.string.savedWatchlist));
                dbHelper.deleteMovie(mId);
                addWatchList.setText(getString(R.string.addToWatchlist));
            } else{
                addWatchList.setText(getString(R.string.addToWatchlist));
                dbHelper.addMovie(new Movie(mId, mTitle, mPoster, mOverview, mBackdrop, mRelease, mRating));
                addWatchList.setText(getString(R.string.savedWatchlist));
            }
        }
    }


}