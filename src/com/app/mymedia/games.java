package com.app.mymedia;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class games extends Activity{
	
	private ArrayAdapter<String> adapter;
	private DBAdapter db = new DBAdapter(this);
	public ArrayList<String> igri = new ArrayList<String>();
	private ListView lv;
	private static final int MENU_ADD_ITEM = Menu.FIRST;
	private static final int MENU_EXIT = MENU_ADD_ITEM+1;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        lv=(ListView)findViewById(R.id.ListView01);
        
        db.open();
        Cursor c = db.getAllGames();
        
        if (c.moveToFirst())
        {
            do {
                igri.add(c.getString(1));
            } while (c.moveToNext());
        }
        db.close();
        
        adapter=new ArrayAdapter<String>(this,R.layout.game_list_tem, igri);
        lv.setAdapter(adapter);
        
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
        	  createClickAlert(position, id);
            Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
          }
        });
	}
	
	public void onStop(){
		super.onStop();
		db.open();
		db.insertAllGames(igri);
		db.close();
		}
	
	public void onPause(){
		super.onPause();
		db.open();
		db.insertAllGames(igri);
		db.close();
		}
	
	private void createClickAlert(final int position, long id){  
	   	 AlertDialog.Builder builder = new AlertDialog.Builder(this);
	   	 builder.setMessage("Do you want to delete the selected game: "+igri.get(position)+"?")
	   	 	  .setIcon(R.drawable.icon)
	          .setTitle(R.string.app_name)
	   	      .setCancelable(false)  
	   	      .setPositiveButton("Yes",  
	   	           new DialogInterface.OnClickListener(){  
	   	           public void onClick(DialogInterface dialog, int id){
	   	        	   igri.remove(position);
	   	        	   adapter.notifyDataSetChanged();//za promena na sostojba na lista...
	   	           }  
	   	      });  
	   	      builder.setNegativeButton("Cancel",  
	   	           new DialogInterface.OnClickListener(){  
	   	           public void onClick(DialogInterface dialog, int id){  
	   	                dialog.cancel();  
	   	           }  
	   	      });  
	   	 AlertDialog alert = builder.create();
	   	 alert.show();  
	   	 }
	
	
    @Override
    public boolean onCreateOptionsMenu(final Menu pMenu) {
    pMenu.add(0, MENU_ADD_ITEM, Menu.NONE, "Add Game!").setIcon(android.R.drawable.ic_menu_mylocation);
    pMenu.add(0, MENU_EXIT, Menu.NONE, "Exit").setIcon(android.R.drawable.ic_menu_mylocation);
    return true;
    }
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
            switch(item.getItemId()) {
                    case MENU_ADD_ITEM:
                    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    	alert.setTitle("");
                    	alert.setMessage("Enter title: ");

                    	// Set an EditText view to get user input 
                    	final EditText input = new EditText(this);
                    	alert.setView(input);

                    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    	public void onClick(DialogInterface dialog, int whichButton) {
                    	  Editable value = input.getText();
                    	  igri.add(value.toString());
                    	  adapter.notifyDataSetChanged();
                    	  }
                    	});
                    	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    	  public void onClick(DialogInterface dialog, int whichButton) {
                    	    dialog.cancel();
                    	  }
                    	});

                    	alert.show();
                    	return true;
                    case MENU_EXIT:
                    	finish();
                    	return true;
                    default: 
                            return true;
            }
    }
}
