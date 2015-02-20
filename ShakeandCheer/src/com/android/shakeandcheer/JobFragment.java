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

public class JobFragment extends Fragment {
	 private SensorManager mSensorManager;

	  private ShakeControl mSensorListener;
	 
	  private String  [] incoming;
	  private String [] selectedmodels;

	  private int [] tmpids;
	  private int baslangic=0;
	  private Timer myTimer;
	  private int precounter=5;
	  private int txtcounter=3;
	  private int currentposition=0;
	  private MediaPlayer tplayer;
	  private int count=30;
	  private TextView description;
	  private TextView timertext;
	  private Random myrand=new Random();
	  private Switch pausebutton;
	  private Button tryagainbutton;

	  
	  public JobFragment(){}
	
	
	
	  @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.job, container, false);
	        tplayer=MediaPlayer.create(getActivity(), R.raw.menutimer);
	        selectedmodels=new String[7];
	        tmpids=new int[7];
	     description=(TextView)rootView.findViewById(R.id.jdesc);
	     timertext=(TextView)rootView.findViewById(R.id.jtimer);
	     pausebutton=(Switch)rootView.findViewById(R.id.jswitch1);
	     tryagainbutton=(Button)rootView.findViewById(R.id.jyeniden);
	    
	     incoming=getResources().getStringArray(R.array.job);
	    myTimer=new Timer();
	     if(DefaultManager.jo_isBreak && !DefaultManager.jo_isExit){
            final Dialog infodialog=new Dialog(getActivity());
            DefaultManager.jo_isExit=false;
            infodialog.setTitle(getResources().getString(R.string.warning));
            infodialog.setContentView(R.layout.goahead);
            final TextView infotext=(TextView)infodialog.findViewById(R.id.wartextView2);
            final TextView infoque=(TextView)infodialog.findViewById(R.id.wartextView1);
            final Button infoyes=(Button)infodialog.findViewById(R.id.warbutton1);
            final Button infono=(Button)infodialog.findViewById(R.id.warbutton2);
            DefaultManager.jo_isBreak=false;
            infotext.setText(getResources().getString(R.string.existing));
            infoque.setText(getResources().getString(R.string.condo));
            infoyes.setText(getResources().getString(R.string.evet));
            infono.setText(getResources().getString(R.string.hayir));
            infodialog.show();
            infoyes.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 currentposition=DefaultManager.jo_lastposition;
			    	 count=DefaultManager.jo_remaning;
			    	 DefaultManager.jo_beginning=false;
			    	 DefaultManager.jo_isBreak=false;
			    	 DefaultManager.jo_pretimer=false;
			    	 DefaultManager.jo_tryagain=false;
			    	 DefaultManager.jo_isClicked=false;
			    	 DefaultManager.jo_shaked=false;
			    	 DefaultManager.jo_isFinish=false;
			    	 DefaultManager.jo_isExit=false;
			    	 DefaultManager.jo_isPaused=false;
			    	 selectedmodels=DefaultManager.jo_currentdescs;
			    	
			    	 description.setText(DefaultManager.jo_currentdescs[currentposition]);
			
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
					 DefaultManager.jo_lastposition=0;
			    	 currentposition=DefaultManager.jo_lastposition;
			    	 DefaultManager.jo_remaning=30;
			    	 count=DefaultManager.jo_remaning;
			    	 
			    	 DefaultManager.jo_beginning=true;
			    	 DefaultManager.jo_pretimer=true;
			    	 DefaultManager.jo_tryagain=false;
			    	 DefaultManager.jo_isClicked=false;
			    	 DefaultManager.jo_shaked=false;
			    	 DefaultManager.jo_isFinish=false;
			    	 DefaultManager.jo_isExit=true;
			    	 DefaultManager.jo_isPaused=false;
			    	 DefaultManager.jo_isBreak=false;
			    	 DefaultManager.jo_correct=0;
			    	 DefaultManager.jo_false=0;
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
	    	 DefaultManager.jo_lastposition=0;
	    	 currentposition=DefaultManager.jo_lastposition;
	    	 DefaultManager.jo_remaning=30;
	          DefaultManager.jo_isExit=false;
	    	 count=DefaultManager.jo_remaning;
	    	// Toast.makeText(getActivity(), "Burada", Toast.LENGTH_SHORT).show();
	    	 DefaultManager.jo_beginning=true;
	    	 DefaultManager.jo_pretimer=true;
	    	 DefaultManager.jo_tryagain=false;
	    	 DefaultManager.jo_isClicked=false;
	    	 DefaultManager.jo_shaked=false;
	    	 DefaultManager.jo_isFinish=false;
	    	 DefaultManager.jo_isExit=true;
	    	 DefaultManager.jo_isPaused=false;
	    	 DefaultManager.jo_isBreak=false;
	    	 DefaultManager.jo_correct=0;
	    	 DefaultManager.jo_false=0;
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
	            DefaultManager.jo_shaked=true;
	            }
	          });
	        
	        
	        tryagainbutton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					DefaultManager.jo_tryagain=true;
			
				}
			});
	        pausebutton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	        	 
	        	   @Override
	        	   public void onCheckedChanged(CompoundButton buttonView,
	        	     boolean isChecked) {
	        	 
	        	    if(isChecked){
	        	    DefaultManager.jo_isPaused=true;
	        	    }else{
	        	     DefaultManager.jo_isPaused=false;
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
			if(DefaultManager.jo_pretimer && count==30){
				//Toast.makeText(getActivity(), "2.burada",Toast.LENGTH_SHORT).show();
				if(precounter>2){
				timertext.setText(String.valueOf(txtcounter));
				txtcounter--;
				}else if(precounter<=2 && precounter>0){
				timertext.setText(getResources().getString(R.string.basliyor));
				}else if(precounter<=0){
					DefaultManager.jo_pretimer=false;
					DefaultManager.jo_beginning=false;
					timertext.setText(String.valueOf(count));
					
					description.setText(selectedmodels[currentposition]);
				}
				precounter--;
			}else{
				if(count<=5 && count>=0 && !DefaultManager.jo_isPaused){
					tplayer.start();
					}
				if(count>0 && !DefaultManager.jo_isFinish && !DefaultManager.jo_isPaused){
					timertext.setText(String.valueOf(count));
					count--;
					
				}
				
				if(count==0 && !DefaultManager.jo_isFinish && !DefaultManager.jo_isPaused){
					timertext.setText(String.valueOf(count));
					DefaultManager.jo_isFinish=true;
					DefaultManager.jo_isPaused=true;
				}
				if(DefaultManager.jo_isFinish){
				//	Toast.makeText(getActivity(), "Finished",Toast.LENGTH_LONG).show();
					
					DefaultManager.jo_isFinish=false;
					DefaultManager.jo_isPaused=true;
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
					if(DefaultManager.jo_correct>DefaultManager.default_score)
						DefaultManager.default_score=DefaultManager.jo_correct;
					if(DefaultManager.jo_correct>=1 && DefaultManager.jo_correct<=2)
					size=1;
					else if(DefaultManager.jo_correct>2 && DefaultManager.jo_correct<=3)
						size=2;
					else if(DefaultManager.jo_correct>3 && DefaultManager.jo_correct<=4)
						size=3;
					else if(DefaultManager.jo_correct>4 && DefaultManager.jo_correct<=6)
						size=4;
					else if(DefaultManager.jo_correct>5)
						size=5;
					
					
				
					cortext.setText(String.valueOf(DefaultManager.jo_correct)+" "+getResources().getString(R.string.dogri));
					faltext.setText(String.valueOf(DefaultManager.jo_false)+" "+getResources().getString(R.string.pas));
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
						DefaultManager.jo_tryagain=true;	
						finishdialog.dismiss();
						}
					});
		               
		            nbutton.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							myTimer.cancel();
						DefaultManager.jo_isExit=true;
						
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
				
				
				if(DefaultManager.jo_isClicked && !DefaultManager.jo_isPaused && !DefaultManager.jo_isFinish){
					DefaultManager.jo_isClicked=false;
					DefaultManager.jo_shaked=false;
				if(currentposition<6){
					currentposition++;
				DefaultManager.jo_correct++;
					description.setText(selectedmodels[currentposition]);
					
				}else{
					DefaultManager.jo_isFinish=true;
					
				}
				}
				
				if(DefaultManager.jo_shaked && !DefaultManager.jo_isPaused && !DefaultManager.jo_isFinish){
					DefaultManager.jo_shaked=false;
					if(currentposition<6){
						currentposition++;
						DefaultManager.jo_false++;
						description.setText(selectedmodels[currentposition]);
					
					}else{
						DefaultManager.jo_isFinish=true;
			
					}
				}
				
				
				if(DefaultManager.jo_tryagain){
		            
					DefaultManager.jo_isClicked=false;
					DefaultManager.jo_beginning=true;
					DefaultManager.jo_pretimer=true;
					DefaultManager.jo_isFinish=false;
					DefaultManager.jo_isPaused=false;
					DefaultManager.jo_isExit=false;
					description.setText(getResources().getString(R.string.waiting));
		
					DefaultManager.jo_remaning=30;
					count=DefaultManager.jo_remaning;
					DefaultManager.jo_shaked=false;
					DefaultManager.jo_tryagain=false;
					selectedmodels=pickSeven(incoming);
			
				    DefaultManager.jo_isBreak=false;
				    DefaultManager.jo_lastposition=0;
				    currentposition=DefaultManager.jo_lastposition;
				    DefaultManager.jo_correct=0;
				    DefaultManager.jo_false=0;
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
	    DefaultManager.jo_tryagain=false;
	    DefaultManager.jo_isPaused=true;
	    DefaultManager.jo_isBreak=true;
	    DefaultManager.jo_isFinish=false;
	    DefaultManager.jo_currentdescs=selectedmodels;
	   	    DefaultManager.jo_lastposition=currentposition;
	    DefaultManager.jo_remaning=count;
	    DefaultManager.jo_isExit=false;
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
