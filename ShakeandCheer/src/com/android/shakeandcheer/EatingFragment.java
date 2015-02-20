package com.android.shakeandcheer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class EatingFragment extends Fragment {
	 private SensorManager mSensorManager;

	  private ShakeControl mSensorListener;
	 
	  private String  [] incoming;
	  private String [] selectedmodels;
	  private MediaPlayer tplayer;
	  private int [] tmpids;
	  private int baslangic=0;
	  private Timer myTimer;
	  private int precounter=5;
	  private int txtcounter=3;
	  private int currentposition=0;
	  private int count=30;
	  private TextView description;
	  private TextView timertext;
	  private Random myrand=new Random();
	  private Switch pausebutton;
	  private Button tryagainbutton;

	  
	  public EatingFragment(){}
	
	
	
	  @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.eating, container, false);
	        tplayer=MediaPlayer.create(getActivity(), R.raw.menutimer);
	        selectedmodels=new String[7];
	        tmpids=new int[7];
	     description=(TextView)rootView.findViewById(R.id.eadesc);
	     timertext=(TextView)rootView.findViewById(R.id.eatimer);
	     pausebutton=(Switch)rootView.findViewById(R.id.easwitch1);
	     tryagainbutton=(Button)rootView.findViewById(R.id.eayeniden);
	    
	     incoming=getResources().getStringArray(R.array.eating);
	    myTimer=new Timer();
	     if(DefaultManager.ea_isBreak && !DefaultManager.ea_isExit){
            final Dialog infodialog=new Dialog(getActivity());
            DefaultManager.ea_isExit=false;
            infodialog.setTitle(getResources().getString(R.string.warning));
            infodialog.setContentView(R.layout.goahead);
            final TextView infotext=(TextView)infodialog.findViewById(R.id.wartextView2);
            final TextView infoque=(TextView)infodialog.findViewById(R.id.wartextView1);
            final Button infoyes=(Button)infodialog.findViewById(R.id.warbutton1);
            final Button infono=(Button)infodialog.findViewById(R.id.warbutton2);
            DefaultManager.ea_isBreak=false;
            infotext.setText(getResources().getString(R.string.existing));
            infoque.setText(getResources().getString(R.string.condo));
            infoyes.setText(getResources().getString(R.string.evet));
            infono.setText(getResources().getString(R.string.hayir));
            infodialog.show();
            infoyes.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 currentposition=DefaultManager.ea_lastposition;
			    	 count=DefaultManager.ea_remaning;
			    	 DefaultManager.ea_beginning=false;
			    	 DefaultManager.ea_isBreak=false;
			    	 DefaultManager.ea_pretimer=false;
			    	 DefaultManager.ea_tryagain=false;
			    	 DefaultManager.ea_isClicked=false;
			    	 DefaultManager.ea_shaked=false;
			    	 DefaultManager.ea_isFinish=false;
			    	 DefaultManager.ea_isExit=false;
			    	 DefaultManager.ea_isPaused=false;
			    	 selectedmodels=DefaultManager.ea_currentdescs;
			    	
			    	 description.setText(DefaultManager.ea_currentdescs[currentposition]);
			
			     myTimer=new Timer();
			         myTimer.schedule(new TimerTask() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Timer_Method();
						}
					},0,1000);
			    	 		infodialog.dismiss();
				}
			});
            
            infono.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 DefaultManager.ea_lastposition=0;
			    	 currentposition=DefaultManager.ea_lastposition;
			    	 DefaultManager.ea_remaning=30;
			    	 count=DefaultManager.ea_remaning;
			    	 
			    	 DefaultManager.ea_beginning=true;
			    	 DefaultManager.ea_pretimer=true;
			    	 DefaultManager.ea_tryagain=false;
			    	 DefaultManager.ea_isClicked=false;
			    	 DefaultManager.ea_shaked=false;
			    	 DefaultManager.ea_isFinish=false;
			    	 DefaultManager.ea_isExit=true;
			    	 DefaultManager.ea_isPaused=false;
			    	 DefaultManager.ea_isBreak=false;
			    	 DefaultManager.ea_correct=0;
			    	 DefaultManager.ea_false=0;
			    	 selectedmodels=pickSeven(incoming);
			    	
			    	 description.setText(getResources().getString(R.string.waiting));
			    	 
			    	 myTimer=new Timer();
			    	 myTimer.schedule(new TimerTask() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Timer_Method();
						}
					},0,1000);
			    	 infodialog.dismiss();
				}
			});
            
            
	    	
	    	 
	     }else{
	    	 DefaultManager.ea_lastposition=0;
	    	 currentposition=DefaultManager.ea_lastposition;
	    	 DefaultManager.ea_remaning=30;
	          DefaultManager.ea_isExit=false;
	    	 count=DefaultManager.ea_remaning;
	    	// Toast.makeText(getActivity(), "Burada", Toast.LENGTH_SHORT).show();
	    	 DefaultManager.ea_beginning=true;
	    	 DefaultManager.ea_pretimer=true;
	    	 DefaultManager.ea_tryagain=false;
	    	 DefaultManager.ea_isClicked=false;
	    	 DefaultManager.ea_shaked=false;
	    	 DefaultManager.ea_isFinish=false;
	    	 DefaultManager.ea_isExit=true;
	    	 DefaultManager.ea_isPaused=false;
	    	 DefaultManager.ea_isBreak=false;
	    	 DefaultManager.ea_correct=0;
	    	 DefaultManager.ea_false=0;
	    	 selectedmodels=pickSeven(incoming);
	    	 
	    	 description.setText(getResources().getString(R.string.waiting));
	    	
	    	myTimer=new Timer();
	    	 myTimer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Timer_Method();
				}
			},0,1000);
	     }
	     
	     
	     
	     mSensorManager=(SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
	        mSensorListener=new ShakeControl();
	     
	        mSensorListener.controlShake(new ShakeControl.OnShakeListener() {

	            public void onShake() {
	            DefaultManager.ea_shaked=true;
	            }
	          });
	        
	        
	        tryagainbutton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					DefaultManager.ea_tryagain=true;
			
				}
			});
	        pausebutton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	        	 
	        	   @Override
	        	   public void onCheckedChanged(CompoundButton buttonView,
	        	     boolean isChecked) {
	        	 
	        	    if(isChecked){
	        	    DefaultManager.ea_isPaused=true;
	        	    }else{
	        	     DefaultManager.ea_isPaused=false;
	        	    }
	        	 
	        	   }
	        	  });
	        	   
	        
	         return rootView;
	    }
		
	  public void Timer_Method(){
		  getActivity().runOnUiThread(TIMER_TICK);
	  }
	  
	  public Runnable TIMER_TICK=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(DefaultManager.ea_pretimer && count==30){
				//Toast.makeText(getActivity(), "2.burada",Toast.LENGTH_SHORT).show();
				if(precounter>2){
				timertext.setText(String.valueOf(txtcounter));
				txtcounter--;
				}else if(precounter<=2 && precounter>0){
				timertext.setText(getResources().getString(R.string.basliyor));
				}else if(precounter<=0){
					DefaultManager.ea_pretimer=false;
					DefaultManager.ea_beginning=false;
					timertext.setText(String.valueOf(count));
					
					description.setText(selectedmodels[currentposition]);
				}
				precounter--;
			}else{
				if(count<=5 && count>=0 && !DefaultManager.ea_isPaused){
					tplayer.start();
					}
				
				if(count>0 && !DefaultManager.ea_isFinish && !DefaultManager.ea_isPaused){
					timertext.setText(String.valueOf(count));
					count--;
					
				}
				
				if(count==0 && !DefaultManager.ea_isFinish && !DefaultManager.ea_isPaused){
					timertext.setText(String.valueOf(count));
					DefaultManager.ea_isFinish=true;
					DefaultManager.ea_isPaused=true;
				}
				if(DefaultManager.ea_isFinish){
				//	Toast.makeText(getActivity(), "Finished",Toast.LENGTH_LONG).show();
					
					DefaultManager.ea_isFinish=false;
					DefaultManager.ea_isPaused=true;
					final Dialog finishdialog=new Dialog(getActivity());
					finishdialog.setTitle(getResources().getString(R.string.status));
					finishdialog.setContentView(R.layout.outcome);
					final TextView congre=(TextView)finishdialog.findViewById(R.id.contextView1);
					final TextView cortext=(TextView)finishdialog.findViewById(R.id.contextView2);
					final TextView faltext=(TextView)finishdialog.findViewById(R.id.contextView3);
					final TextView againque=(TextView)finishdialog.findViewById(R.id.contextView4);
					final Button ybutton=(Button)finishdialog.findViewById(R.id.conbutton1);
					final Button nbutton=(Button)finishdialog.findViewById(R.id.conbutton2);
					final RatingBar rate=(RatingBar)finishdialog.findViewById(R.id.conratingBar1);
					
					finishdialog.show();
					float size=0;
					if(DefaultManager.ea_correct>DefaultManager.default_score)
						DefaultManager.default_score=DefaultManager.ea_correct;
					if(DefaultManager.ea_correct>=1 && DefaultManager.ea_correct<=2)
					size=1;
					else if(DefaultManager.ea_correct>2 && DefaultManager.ea_correct<=3)
						size=2;
					else if(DefaultManager.ea_correct>3 && DefaultManager.ea_correct<=4)
						size=3;
					else if(DefaultManager.ea_correct>4 && DefaultManager.ea_correct<=6)
						size=4;
					else if(DefaultManager.ea_correct>5)
						size=5;
					
					
					cortext.setText(String.valueOf(DefaultManager.ea_correct)+" "+getResources().getString(R.string.dogri));
					faltext.setText(String.valueOf(DefaultManager.ea_false)+" "+getResources().getString(R.string.pas));
					againque.setText(getResources().getString(R.string.yenidenistermisini));
					ybutton.setText(getResources().getString(R.string.evet));
					nbutton.setText(getResources().getString(R.string.hayir));
					if(size>=4)
						congre.setText(getResources().getString(R.string.tebrikler));
					else
						congre.setText(getResources().getString(R.string.basarisiz));
					rate.setRating(size);
					
		               ybutton.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
						DefaultManager.ea_tryagain=true;	
						finishdialog.dismiss();
						}
					});
		               
		            nbutton.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							myTimer.cancel();
						DefaultManager.ea_isExit=true;
						
					finishdialog.dismiss();
						Fragment myfragment=new HomeFragment();
                      
						if (myfragment != null) {
							FragmentManager fragmentManager = getFragmentManager();
							fragmentManager.beginTransaction()
									.replace(R.id.frame_container, myfragment).commit();
						}else{
							Log.e("MainActivity", "Error in creating fragment");
						}
						}
					});
					
					
				   
					

				}
				
				
				if(DefaultManager.ea_isClicked && !DefaultManager.ea_isPaused && !DefaultManager.ea_isFinish){
					DefaultManager.ea_isClicked=false;
					DefaultManager.ea_shaked=false;
				if(currentposition<6){
					currentposition++;
				DefaultManager.ea_correct++;
					description.setText(selectedmodels[currentposition]);
					
				}else{
					DefaultManager.ea_isFinish=true;
					
				}
				}
				
				if(DefaultManager.ea_shaked && !DefaultManager.ea_isPaused && !DefaultManager.ea_isFinish){
					DefaultManager.ea_shaked=false;
					if(currentposition<6){
						currentposition++;
						DefaultManager.ea_false++;
						description.setText(selectedmodels[currentposition]);
					
					}else{
						DefaultManager.ea_isFinish=true;
			
					}
				}
				
				
				if(DefaultManager.ea_tryagain){
		            
					DefaultManager.ea_isClicked=false;
					DefaultManager.ea_beginning=true;
					DefaultManager.ea_pretimer=true;
					DefaultManager.ea_isFinish=false;
					DefaultManager.ea_isPaused=false;
					DefaultManager.ea_isExit=false;
					description.setText(getResources().getString(R.string.waiting));
		
					DefaultManager.ea_remaning=30;
					count=DefaultManager.ea_remaning;
					DefaultManager.ea_shaked=false;
					DefaultManager.ea_tryagain=false;
					selectedmodels=pickSeven(incoming);
			
				    DefaultManager.ea_isBreak=false;
				    DefaultManager.ea_lastposition=0;
				    currentposition=DefaultManager.ea_lastposition;
				    DefaultManager.ea_correct=0;
				    DefaultManager.ea_false=0;
				}
				
				
				
			}
		}
	};
			  
	  
	public String [] pickSeven(String [] in){
		List<String> tmpincoming=new ArrayList<String>();
		
		
		String [] tmppick=new String[7];
			for(int i=0;i<incoming.length;i++){
				tmpincoming.add(in[i]);
			}
				
	       for(int j=0;j<7;j++){
	    	   int tmpid=myrand.nextInt(tmpincoming.size());
	    	   tmppick[j]=tmpincoming.get(tmpid);
	    	   tmpincoming.remove(tmpid);  
	    	  
	       }
	      	
		return tmppick;
		
	}
	
	
	
	@Override
	public void onResume() {
	    super.onResume();
	    mSensorManager.registerListener(mSensorListener,
	        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	        SensorManager.SENSOR_DELAY_UI);
	    
	  }

	  @Override
	public void onPause() {
	    mSensorManager.unregisterListener(mSensorListener);
	    super.onPause();
	    myTimer.cancel();
	    DefaultManager.ea_tryagain=false;
	    DefaultManager.ea_isPaused=true;
	    DefaultManager.ea_isBreak=true;
	    DefaultManager.ea_isFinish=false;
	    DefaultManager.ea_currentdescs=selectedmodels;
	   	    DefaultManager.ea_lastposition=currentposition;
	    DefaultManager.ea_remaning=count;
	    DefaultManager.ea_isExit=false;
	    Fragment myfragment=new HomeFragment();
        
		if (myfragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, myfragment).commit();
		}else{
			Log.e("MainActivity", "Error in creating fragment");
		}
	  }
}
