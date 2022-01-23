package net.caroline.movieapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import net.caroline.movieapp.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //change language button
        Button buttonLang = (Button) findViewById(R.id.buttonLang);
        buttonLang.setOnClickListener(v->{
            //mengarahkan ke setting
            Intent intentLang = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intentLang);
        });

        //Watchlist
        Button btn_watchlist = (Button) findViewById(R.id.btn_watchlist);
        btn_watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, WatchlistActivity.class));
            }
        });
    }
}