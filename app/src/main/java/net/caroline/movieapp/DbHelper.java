package net.caroline.movieapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "MovieDb";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_STD = "movies";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_RATING = "vote_average";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_RELEASE = "release_date";
    private static final String KEY_POSTER = "poster_path";
    private static final String KEY_BACKDROP = "backdrop_path";


    private static final String CREATE_TABLE_MOVIES = "CREATE TABLE "
            + TABLE_STD + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TITLE + " TEXT, " + KEY_RELEASE + " TEXT, "
            + KEY_RATING + " NUMBER, " + KEY_POSTER + " TEXT, "+ KEY_BACKDROP + " TEXT, "
            + KEY_OVERVIEW + " TEXT );";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_STD + "'");
        onCreate(db);
    }


    public long addMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, movie.getId());
        values.put(KEY_TITLE, movie.getTitle());
        values.put(KEY_POSTER, movie.getPoster());
        values.put(KEY_BACKDROP, movie.getPoster());
        values.put(KEY_OVERVIEW, movie.getOverview());
        values.put(KEY_RATING, movie.getRating());
        values.put(KEY_RELEASE, movie.getRelease());

        long insert = db.insert(TABLE_STD, null, values);

        return insert;
    }

    //Menampilkan semua movie dalam watchlit
    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> movieArrayList = new ArrayList<Movie>();

        String selectQuery = "SELECT * FROM " + TABLE_STD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                String title = c.getString(c.getColumnIndex(KEY_TITLE));
                String overview = c.getString(c.getColumnIndex(KEY_OVERVIEW));
                String release = c.getString(c.getColumnIndex(KEY_RELEASE));
                String backdrop = c.getString(c.getColumnIndex(KEY_POSTER));
                String poster = c.getString(c.getColumnIndex(KEY_BACKDROP));
                Double rating = c.getDouble(c.getColumnIndex(KEY_RATING));

                Movie movie = new Movie(id, title, poster, overview, backdrop, release, rating);

                movieArrayList.add(movie);
            } while (c.moveToNext());
        }
        return movieArrayList;
    }

    //Menambahkan movie kedalam database menggunakan id
    public boolean getMovieById(int id){
        String selectQuery = "SELECT id FROM " + TABLE_STD + " WHERE id=="+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.getCount() <= 0){
            return false;
        }
       return true;
    }

    public void deleteMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
         db.delete(TABLE_STD, KEY_ID + " = ?",
         new String[]{String.valueOf(id)});
    }
}
