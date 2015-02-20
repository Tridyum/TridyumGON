package com.android.shakeandcheer;

import java.util.Locale;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

public class LanguageFragment extends Fragment {
private Spinner languageopt;
private TextView langtitle;
private Configuration myconf;
	  @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.languageoption, container, false);
	        languageopt=(Spinner)rootView.findViewById(R.id.lspinner1);
	        langtitle=(TextView)rootView.findViewById(R.id.textView1);
	        myconf=new Configuration(getResources().getConfiguration());
	      languageopt.setSelection(DefaultManager.default_language_opt);
	      langtitle.setText(getResources().getString(R.string.langtitle));
	        languageopt.setOnItemSelectedListener(new OnItemSelectedListener() {
	            @Override
	            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
	                // your code here
	             	if(position==0){
	                      myconf.locale=Locale.getDefault();      
 	            	}else if(position==1){ 
	            		myconf.locale=Locale.ENGLISH;
	            	}else if(position==2){
	            		myconf.locale=Locale.GERMAN;
	            	}else if(position==3){
	            		myconf.locale=Locale.JAPANESE;
	            	}
	             	getResources().updateConfiguration(myconf,getResources().getDisplayMetrics());
	             	DefaultManager.default_language_opt=position;
	            }

	            @Override
	            public void onNothingSelected(AdapterView<?> parentView) {
	                // your code here
	            }

	        });
	        return rootView;
}
	  
	  
}
