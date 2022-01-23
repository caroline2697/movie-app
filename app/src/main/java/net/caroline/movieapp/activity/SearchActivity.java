package net.caroline.movieapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.caroline.movieapp.Movie;
import net.caroline.movieapp.MovieAdapter;
import net.caroline.movieapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //GET Data Intent
        String q = (String) getIntent().getSerializableExtra("query");

        //Search Function
        EditText searchBar = (EditText) findViewById(R.id.search_bar);
        searchBar.setText(q);

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String query = (v.getText().length() != 0) ? v.getText().toString() : "";
                    fetchMovie(query);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchBar.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        fetchMovie(q);
    }
    private void fetchMovie(String query) {
        //Mengambil data JSOn menggunakan Volley
        String apiKey = "?api_key=2421e2d5987df3690e18c04c6b28aa02";
        String baseUrl = "https://api.themoviedb.org/3/";
        String search = "search/movie";
        String keyword = "&query="+query;
        String URL = baseUrl+search+apiKey+keyword;
        RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);

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

    private void sendToAdapter(ArrayList<Movie> movies) {
        //Mengirimkan data movies kedalam adapter
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        adapter = new MovieAdapter(movies);
        //LinearLayoutManager.HORIZONTAL, false
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


}