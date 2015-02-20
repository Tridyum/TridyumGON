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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AnimalFragment extends Fragment {
	 private SensorManager mSensorManager;

	  private ShakeControl mSensorListener;
	 
	  private String  [] incoming;
	  private String [] selectedmodels;

	  private int [] tmpids;
	  private int baslangic=0;
	  private MediaPlayer tplayer;
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

	  
	  public AnimalFragment(){}
	
	
	
	  @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.animal, container, false);
	        tplayer=MediaPlayer.create(getActivity(), R.raw.menutimer);
	        selectedmodels=new String[7];
	        tmpids=new int[7];
	     description=(TextView)rootView.findViewById(R.id.andesc);
	     timertext=(TextView)rootView.findViewById(R.id.antimer);
	     pausebutton=(Switch)rootView.findViewById(R.id.answitch1);
	     tryagainbutton=(Button)rootView.findViewById(R.id.anyeniden);
	    
	     incoming=getResources().getStringArray(R.array.animal);
	    myTimer=new Timer();
	     if(DefaultManager.an_isBreak && !DefaultManager.an_isExit){
             final Dialog infodialog=new Dialog(getActivity());
             DefaultManager.an_isExit=false;
             infodialog.setTitle(getResources().getString(R.string.warning));
             infodialog.setContentView(R.layout.goahead);
             final TextView infotext=(TextView)infodialog.findViewById(R.id.wartextView2);
             final TextView infoque=(TextView)infodialog.findViewById(R.id.wartextView1);
             final Button infoyes=(Button)infodialog.findViewById(R.id.warbutton1);
             final Button infono=(Button)infodialog.findViewById(R.id.warbutton2);
             DefaultManager.an_isBreak=false;
             infotext.setText(getResources().getString(R.string.existing));
             infoque.setText(getResources().getString(R.string.condo));
             infoyes.setText(getResources().getString(R.string.evet));
             infono.setText(getResources().getString(R.string.hayir));
             infodialog.show();
             infoyes.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 currentposition=DefaultManager.an_lastposition;
			    	 count=DefaultManager.an_remaning;
			    	 DefaultManager.an_beginning=false;
			    	 DefaultManager.an_isBreak=false;
			    	 DefaultManager.an_pretimer=false;
			    	 DefaultManager.an_tryagain=false;
			    	 DefaultManager.an_isClicked=false;
			    	 DefaultManager.an_shaked=false;
			    	 DefaultManager.an_isFinish=false;
			    	 DefaultManager.an_isExit=false;
			    	 DefaultManager.an_isPaused=false;
			    	 selectedmodels=DefaultManager.an_currentdescs;
			    	
			    	 description.setText(DefaultManager.an_currentdescs[currentposition]);
			
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
					 DefaultManager.an_lastposition=0;
			    	 currentposition=DefaultManager.an_lastposition;
			    	 DefaultManager.an_remaning=30;
			    	 count=DefaultManager.an_remaning;
			    	 
			    	 DefaultManager.an_beginning=true;
			    	 DefaultManager.an_pretimer=true;
			    	 DefaultManager.an_tryagain=false;
			    	 DefaultManager.an_isClicked=false;
			    	 DefaultManager.an_shaked=false;
			    	 DefaultManager.an_isFinish=false;
			    	 DefaultManager.an_isExit=true;
			    	 DefaultManager.an_isPaused=false;
			    	 DefaultManager.an_isBreak=false;
			    	 DefaultManager.an_correct=0;
			    	 DefaultManager.an_false=0;
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
	    	 DefaultManager.an_lastposition=0;
	    	 currentposition=DefaultManager.an_lastposition;
	    	 DefaultManager.an_remaning=30;
	          DefaultManager.an_isExit=false;
	    	 count=DefaultManager.an_remaning;
	    	// Toast.makeText(getActivity(), "Burada", Toast.LENGTH_SHORT).show();
	    	 DefaultManager.an_beginning=true;
	    	 DefaultManager.an_pretimer=true;
	    	 DefaultManager.an_tryagain=false;
	    	 DefaultManager.an_isClicked=false;
	    	 DefaultManager.an_shaked=false;
	    	 DefaultManager.an_isFinish=false;
	    	 DefaultManager.an_isExit=true;
	    	 DefaultManager.an_isPaused=false;
	    	 DefaultManager.an_isBreak=false;
	    	 DefaultManager.an_correct=0;
	    	 DefaultManager.an_false=0;
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
	            DefaultManager.an_shaked=true;
	            }
	          });
	        
	        
	        tryagainbutton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					DefaultManager.an_tryagain=true;
			
				}
			});
	        pausebutton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	        	 
	        	   @Override
	        	   public void onCheckedChanged(CompoundButton buttonView,
	        	     boolean isChecked) {
	        	 
	        	    if(isChecked){
	        	    DefaultManager.an_isPaused=true;
	        	    }else{
	        	     DefaultManager.an_isPaused=false;
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
			if(DefaultManager.an_pretimer && count==30){
				//Toast.makeText(getActivity(), "2.burada",Toast.LENGTH_SHORT).show();
				if(precounter>2){
				timertext.setText(String.valueOf(txtcounter));
				txtcounter--;
				}else if(precounter<=2 && precounter>0){
				timertext.setText(getResources().getString(R.string.basliyor));
				}else if(precounter<=0){
					DefaultManager.an_pretimer=false;
					DefaultManager.an_beginning=false;
					timertext.setText(String.valueOf(count));
					
					description.setText(selectedmodels[currentposition]);
				}
				precounter--;
			}else{
				
				if(count<=5 && count>=0 && !DefaultManager.an_isPaused){
					tplayer.start();
					}
				
				if(count>0 && !DefaultManager.an_isFinish && !DefaultManager.an_isPaused){
					timertext.setText(String.valueOf(count));
					count--;
					
				}
				
				if(count==0 && !DefaultManager.an_isFinish && !DefaultManager.an_isPaused){
					timertext.setText(String.valueOf(count));
					DefaultManager.an_isFinish=true;
					DefaultManager.an_isPaused=true;
				}
				if(DefaultManager.an_isFinish){
				//	Toast.makeText(getActivity(), "Finished",Toast.LENGTH_LONG).show();
					
					DefaultManager.an_isFinish=false;
					DefaultManager.an_isPaused=true;
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
					if(DefaultManager.an_correct>DefaultManager.default_score)
						DefaultManager.default_score=DefaultManager.an_correct;
					
					if(DefaultManager.an_correct>=1 && DefaultManager.an_correct<=2)
					size=1;
					else if(DefaultManager.an_correct>2 && DefaultManager.an_correct<=3)
						size=2;
					else if(DefaultManager.an_correct>3 && DefaultManager.an_correct<=4)
						size=3;
					else if(DefaultManager.an_correct>4 && DefaultManager.an_correct<=6)
						size=4;
					else if(DefaultManager.an_correct>5)
						size=5;
					
					
					
					cortext.setText(String.valueOf(DefaultManager.an_correct)+" "+getResources().getString(R.string.dogri));
					faltext.setText(String.valueOf(DefaultManager.an_false)+" "+getResources().getString(R.string.pas));
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
						DefaultManager.an_tryagain=true;	
						finishdialog.dismiss();
						}
					});
		               
		            nbutton.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							myTimer.cancel();
						DefaultManager.an_isExit=true;
						
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
				
				
				if(DefaultManager.an_isClicked && !DefaultManager.an_isPaused && !DefaultManager.an_isFinish){
					DefaultManager.an_isClicked=false;
					DefaultManager.an_shaked=false;
				if(currentposition<6){
					currentposition++;
				DefaultManager.an_correct++;
					description.setText(selectedmodels[currentposition]);
					
				}else{
					DefaultManager.an_isFinish=true;
					
				}
				}
				
				if(DefaultManager.an_shaked && !DefaultManager.an_isPaused && !DefaultManager.an_isFinish){
					DefaultManager.an_shaked=false;
					if(currentposition<6){
						currentposition++;
						DefaultManager.an_false++;
						description.setText(selectedmodels[currentposition]);
					
					}else{
						DefaultManager.an_isFinish=true;
			
					}
				}
				
				
				if(DefaultManager.an_tryagain){
		            
					DefaultManager.an_isClicked=false;
					DefaultManager.an_beginning=true;
					DefaultManager.an_pretimer=true;
					DefaultManager.an_isFinish=false;
					DefaultManager.an_isPaused=false;
					DefaultManager.an_isExit=false;
					description.setText(getResources().getString(R.string.waiting));
		
					DefaultManager.an_remaning=30;
					count=DefaultManager.an_remaning;
					DefaultManager.an_shaked=false;
					DefaultManager.an_tryagain=false;
					selectedmodels=pickSeven(incoming);
			
				    DefaultManager.an_isBreak=false;
				    DefaultManager.an_lastposition=0;
				    currentposition=DefaultManager.an_lastposition;
				    DefaultManager.an_correct=0;
				    DefaultManager.an_false=0;
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
	    DefaultManager.an_tryagain=false;
	    DefaultManager.an_isPaused=true;
	    DefaultManager.an_isBreak=true;
	    DefaultManager.an_isExit=false;
	    DefaultManager.an_isFinish=false;
	    DefaultManager.an_currentdescs=selectedmodels;
	   	    DefaultManager.an_lastposition=currentposition;
	    DefaultManager.an_remaning=count;
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
