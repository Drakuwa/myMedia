package com.app.mymedia;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class main extends TabActivity{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, movies.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("movies").setIndicator("Movies",
                res.getDrawable(R.drawable.ic_tab_movies))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, tracks.class);
        spec = tabHost.newTabSpec("tracks").setIndicator("Tracks",
                res.getDrawable(R.drawable.ic_tab_tracks))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, games.class);
        spec = tabHost.newTabSpec("games").setIndicator("Games",
                res.getDrawable(R.drawable.ic_tab_games))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, books.class);
        spec = tabHost.newTabSpec("books").setIndicator("Books",
                res.getDrawable(R.drawable.ic_tab_books))
                      .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);

        
    }

}
