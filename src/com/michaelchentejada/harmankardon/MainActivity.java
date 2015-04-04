package com.michaelchentejada.harmankardon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	private final static String EXTRA_STATE = "extra_state";
	
	private Button mPlayButton;
	boolean isPlay = false;
	
	View.OnClickListener mTogglePlayButton = new View.OnClickListener(){

	    @Override
	    public void onClick(View v){
	        // change your button background
	    	isPlay = !isPlay; // reverse
	    	
	        if(isPlay){ 
	            v.setBackgroundResource(R.drawable.playicon);
	            Intent i = new Intent(MainActivity.this, MainService.class);
	            i.setAction(MainService.STOP_INTENT);
	    		startService(i);
	        }else{
	            v.setBackgroundResource(R.drawable.stopicon);
	            Intent i = new Intent(MainActivity.this, MainService.class);
	            i.setAction(MainService.START_INTENT);
	    		startService(i);
	        }

	        
	    }
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    mPlayButton = (Button) findViewById(R.id.playbutton);
	    // Default button, if need set it in xml via background="@drawable/default"
	    mPlayButton.setBackgroundResource(R.drawable.stopicon);
	    mPlayButton.setOnClickListener(mTogglePlayButton);
	    
		Intent i = new Intent(this, MainService.class);
		startService(i);
		
		if (savedInstanceState != null){
			isPlay = savedInstanceState.getBoolean(EXTRA_STATE);
			
			if(isPlay){ 
				mPlayButton.setBackgroundResource(R.drawable.playicon);
	    		startService(i);
	        }else{
	        	mPlayButton.setBackgroundResource(R.drawable.stopicon);
	    		startService(i);
	        }
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean(EXTRA_STATE, isPlay);
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
