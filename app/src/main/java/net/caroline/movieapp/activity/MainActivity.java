package net.caroline.movieapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.caroline.movieapp.HorizontalAdapter;
import net.caroline.movieapp.Movie;
import net.caroline.movieapp.MovieAdapter;
import net.caroline.movieapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private RecyclerView horizontalView;
    private HorizontalAdapter horizontalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //profile
        CircleImageView iconProfile = findViewById(R.id.profile_icon);
        iconProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        //Mengarahkan ke halaman pencarian
        EditText searchBar = (EditText) findViewById(R.id.search_bar);

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String query = (v.getText().length() != 0) ? v.getText().toString() : "";
                    startActivity(new Intent(v.getContext(), SearchActivity.class).putExtra("query", query));
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchBar.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        //function ambil data
        fetchTrending();
        fetchMovie();
    }

    private void fetchMovie() {
        //Mengambil data JSOn menggunakan Volley
        String apiKey = "?api_key=2421e2d5987df3690e18c04c6b28aa02";
        String baseUrl = "https://api.themoviedb.org/3/";
        String latest = "movie/top_rated";
        String URL = baseUrl + latest + apiKey;
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        //Membuat ArrayList(dinamis) movieList
        final ArrayList<Movie> movieList = new ArrayList<>();
        StringRequest request = new StringRequest(com.android.volley.Request.Method.GET, URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Mengambil data dari array "results" pada JSON
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("results");

                    //Perulangan untuk mengambil movie atribut dan memasukan kedalam movieList
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String title = jsonObject.getString("title");
                        String overview = jsonObject.getString("overview");
                        String release = jsonObject.getString("release_date");
                        String poster = jsonObject.getString("poster_path");
                        Double rating = jsonObject.getDouble("vote_average");
                        String backdrop = jsonObject.getString("backdrop_path");
                        movieList.add(new Movie(id, title, poster, overview, backdrop, release, rating));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Mengirimdata ArrayList<Movie> kedalam adapter
                sendToAdapter(movieList);
//
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);
    }

    private void fetchTrending() {
        //Mengambil data JSOn menggunakan Volley
        String apiKey = "?api_key=2421e2d5987df3690e18c04c6b28aa02";
        String baseUrl = "https://api.themoviedb.org/3/";
        String popular = "movie/popular";
        String URL = baseUrl + popular + apiKey;
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        //Membuat ArrayList(dinamis) movieList
        final ArrayList<Movie> movieList = new ArrayList<>();
        StringRequest request = new StringRequest(com.android.volley.Request.Method.GET, URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Mengambil data dari array "results" pada JSON
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("results");

                    //Perulangan untuk mengambil movie atribut dan memasukan kedalam movieList
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String title = jsonObject.getString("title");
                        String overview = jsonObject.getString("overview");
                        String release = jsonObject.getString("release_date");
                        String poster = jsonObject.getString("poster_path");
                        Double rating = jsonObject.getDouble("vote_average");
                        String backdrop = jsonObject.getString("backdrop_path");
                        movieList.add(new Movie(id, title, poster, overview, backdrop, release, rating));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Mengirimdata ArrayList<Movie> kedalam adapter
                sendToAdapterHorizontal(movieList);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);
    }

    private void sendToAdapterHorizontal(ArrayList<Movie> movies) {
        //Mengirimkan data movies kedalam adapter
        horizontalView = (RecyclerView) findViewById(R.id.recyclehorizontal);
        horizontalAdapter = new HorizontalAdapter(movies);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        horizontalView.setLayoutManager(layoutManager);
        horizontalView.setAdapter(horizontalAdapter);
    }

    private void sendToAdapter(ArrayList<Movie> movies) {
        //Mengirimkan data movies kedalam adapter
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        adapter = new MovieAdapter(movies);
        //LinearLayoutManager.HORIZONTAL, false
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}