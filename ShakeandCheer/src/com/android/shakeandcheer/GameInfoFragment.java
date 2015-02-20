package com.android.shakeandcheer;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class GameInfoFragment extends Fragment {
	
	
	  
	  public GameInfoFragment(){}
	
	
	
	  @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.ginfo, container, false);
	        
	         return rootView;
	    }
		
	
	
	@Override
	public void onResume() {
	    super.onResume();
	    
	    
	  }

	  @Override
	public void onPause() {
	   
	    super.onPause();
	  }
}
