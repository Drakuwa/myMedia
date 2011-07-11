package com.app.mymedia;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter 
{
    public static final String MOVIE_ROWID = "_id";
    public static final String MOVIE_TITLE = "title";
    
    public static final String GAME_ROWID = "_id";
    public static final String GAME_TITLE = "title";
    
    public static final String BOOK_ROWID = "_id";
    public static final String BOOK_TITLE = "title";
    
    public static final String TRACK_ROWID = "_id";
    public static final String TRACK_TITLE = "title";
    
    private static final String DATABASE_NAME = "media";
    
    private static final String DATABASE_TABLE1 = "games";
    private static final String DATABASE_TABLE2 = "movies";
    private static final String DATABASE_TABLE3 = "books";
    private static final String DATABASE_TABLE4 = "tracks";
    
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE1 =
        "create table games (_id integer primary key autoincrement, "
        + "title text not null);";
    
    private static final String DATABASE_CREATE2 =
        "create table movies (_id integer primary key autoincrement, "
        + "title text not null);";
    
    private static final String DATABASE_CREATE3 =
        "create table books (_id integer primary key autoincrement, "
        + "title text not null);";
    
    private static final String DATABASE_CREATE4 =
        "create table tracks (_id integer primary key autoincrement, "
        + "title text not null);";
        
    private final Context context; 
    
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
        
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
            db.execSQL(DATABASE_CREATE1);
            db.execSQL(DATABASE_CREATE2);
            db.execSQL(DATABASE_CREATE3);
            db.execSQL(DATABASE_CREATE4);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
        int newVersion) 
        {
            db.execSQL("DROP TABLE IF EXISTS games");
            db.execSQL("DROP TABLE IF EXISTS movies");
            db.execSQL("DROP TABLE IF EXISTS books");
            db.execSQL("DROP TABLE IF EXISTS tracks");
            onCreate(db);
        }
    }    
    
    //---opens the database---
    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---    
    public void close() 
    {
        DBHelper.close();
    }
    
    //---insert a title into the database---
    public long insertGame(String title) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(GAME_TITLE, title);
        return db.insert(DATABASE_TABLE1, null, initialValues);
    }
    public long insertBook(String title) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(BOOK_TITLE, title);
        return db.insert(DATABASE_TABLE3, null, initialValues);
    }
    
    public void insertAllBooks(ArrayList<String> books) 
    {
    	db.delete(DATABASE_TABLE3, null, null);
        ContentValues initialValues = new ContentValues();
        for(int i=0; i<books.size();i++) {
        	String book = books.get(i);
        	initialValues.put("title", book);
            db.insert(DATABASE_TABLE3, null, initialValues);
        	}
    }
    public void insertAllGames(ArrayList<String> games) 
    {
    	db.delete(DATABASE_TABLE1, null, null);
        ContentValues initialValues = new ContentValues();
        for(int i=0; i<games.size();i++) {
        	String game = games.get(i);
        	initialValues.put("title", game);
            db.insert(DATABASE_TABLE1, null, initialValues);
        	}
    }
    public void insertAllMovies(ArrayList<String> movies) 
    {
    	db.delete(DATABASE_TABLE2, null, null);
        ContentValues initialValues = new ContentValues();
        for(int i=0; i<movies.size();i++) {
        	String movie = movies.get(i);
        	initialValues.put("title", movie);
            db.insert(DATABASE_TABLE2, null, initialValues);
        	}
    }
    public void insertAllTracks(ArrayList<String> tracks) 
    {
    	db.delete(DATABASE_TABLE4, null, null);
        ContentValues initialValues = new ContentValues();
        for(int i=0; i<tracks.size();i++) {
        	String track = tracks.get(i);
        	initialValues.put("title", track);
            db.insert(DATABASE_TABLE4, null, initialValues);
        	}
    }
    
    
    public long insertMovie(String title) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(MOVIE_TITLE, title);
        return db.insert(DATABASE_TABLE2, null, initialValues);
    }
    public long insertTrack(String title, String artist) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(TRACK_TITLE, title);
        return db.insert(DATABASE_TABLE4, null, initialValues);
    }

    //---deletes a particular title---
    public boolean deleteGame(long rowId) 
    {
        return db.delete(DATABASE_TABLE1, GAME_ROWID + 
        		"=" + rowId, null) > 0;
    }
    public boolean deleteMovie(long rowId) 
    {
        return db.delete(DATABASE_TABLE2, MOVIE_ROWID + 
        		"=" + rowId, null) > 0;
    }
    public boolean deleteBook(long rowId) 
    {
        return db.delete(DATABASE_TABLE3, BOOK_ROWID + 
        		"=" + rowId, null) > 0;
    }
    public boolean deleteTrack(long rowId) 
    {
        return db.delete(DATABASE_TABLE4, TRACK_ROWID + 
        		"=" + rowId, null) > 0;
    }

    //---retrieves all the titles---
    public Cursor getAllGames() 
    {
        return db.query(DATABASE_TABLE1, new String[] {
        		GAME_ROWID, 
        		GAME_TITLE}, 
                null, 
                null, 
                null, 
                null, 
                null);
    }
    public Cursor getAllMovies() 
    {
        return db.query(DATABASE_TABLE2, new String[] {
        		MOVIE_ROWID, 
        		MOVIE_TITLE}, 
                null, 
                null, 
                null, 
                null, 
                null);
    }
    public Cursor getAllBooks() 
    {
        return db.query(DATABASE_TABLE3, new String[] {
        		BOOK_ROWID, 
        		BOOK_TITLE}, 
                null, 
                null, 
                null, 
                null, 
                null);
    }
    public Cursor getAllTracks() 
    {
        return db.query(DATABASE_TABLE4, new String[] {
        		TRACK_ROWID, 
        		TRACK_TITLE}, 
                null, 
                null, 
                null, 
                null, 
                null);
    }

    //---updates a title---
    public boolean updateGame(long rowId, String title) 
    {
        ContentValues args = new ContentValues();
        args.put(GAME_TITLE, title);
        return db.update(DATABASE_TABLE1, args, 
                         GAME_ROWID + "=" + rowId, null) > 0;
    }
  //---updates a title---
    public boolean updateMovie(long rowId, String title) 
    {
        ContentValues args = new ContentValues();
        args.put(MOVIE_TITLE, title);
        return db.update(DATABASE_TABLE2, args, 
                         MOVIE_ROWID + "=" + rowId, null) > 0;
    }
  //---updates a title---
    public boolean updateBook(long rowId, String title) 
    {
        ContentValues args = new ContentValues();
        args.put(BOOK_TITLE, title);
        return db.update(DATABASE_TABLE3, args, 
                         BOOK_ROWID + "=" + rowId, null) > 0;
    }
  //---updates a title---
    public boolean updateTrack(long rowId, String title, String artist) 
    {
        ContentValues args = new ContentValues();
        args.put(TRACK_TITLE, title);
        return db.update(DATABASE_TABLE4, args, 
                         GAME_ROWID + "=" + rowId, null) > 0;
    }
    public void switchID(int rowID, int lastID){
    
    }
}
