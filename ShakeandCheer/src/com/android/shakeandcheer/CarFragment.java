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
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class CarFragment extends Fragment {
	 private SensorManager mSensorManager;

	  private ShakeControl mSensorListener;
	  private Integer [] images={R.drawable.abarth,R.drawable.alfa,R.drawable.asia,R.drawable.aston,R.drawable.audi,R.drawable.austin,R.drawable.autobic,R.drawable.bentley,R.drawable.bmw,R.drawable.bugatti,R.drawable.buick,R.drawable.cadillac,R.drawable.carver,R.drawable.chevrolet,R.drawable.chrysler,R.drawable.citroen,R.drawable.corvette,R.drawable.dacia,R.drawable.daewoo,R.drawable.daiha,R.drawable.daimler,R.drawable.datsun,R.drawable.dodge,R.drawable.donker,R.drawable.ferrari,R.drawable.fiat,R.drawable.fisker,R.drawable.ford,R.drawable.fso,R.drawable.galloper,R.drawable.honda,R.drawable.hummer,R.drawable.hydun,R.drawable.infiniti,R.drawable.innocenti,R.drawable.iveco,R.drawable.jaguar,R.drawable.jeep,R.drawable.josse,R.drawable.kia,R.drawable.ktm,R.drawable.landrover,R.drawable.landwind,R.drawable.lexus,R.drawable.lincoln,R.drawable.lotus,R.drawable.marccus,R.drawable.maserati,R.drawable.maybach,R.drawable.mazda,R.drawable.mega,R.drawable.mercedes,R.drawable.pontiac,R.drawable.porsche,R.drawable.princess,R.drawable.renault,R.drawable.rollsroyce,R.drawable.rover,R.drawable.saab,R.drawable.seat,R.drawable.skoda,R.drawable.smart,R.drawable.spectre,R.drawable.ssangyoung,R.drawable.subaru,R.drawable.suzuki,R.drawable.talbot,R.drawable.tesla,R.drawable.think,R.drawable.toyota,R.drawable.triumph,R.drawable.tvr,R.drawable.volksvogen,R.drawable.volvo};
	  
	  private String  [] incoming;
	  private String [] selectedmodels;
	  private Integer [] selectedimages;
	  private int [] tmpids;
	  private int baslangic=0;
	  private Timer myTimer;
	  private MediaPlayer tplayer;
	  private int precounter=5;
	  private int txtcounter=3;
	  private int currentposition=0;
	  private int count=30;
	  private TextView description;
	  private TextView timertext;
	  private Random myrand=new Random();
	  private Switch pausebutton;
	  private Button tryagainbutton;
	  private ImageView imview;
	  
	  public CarFragment(){}
	
	
	
	  @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		  tplayer=MediaPlayer.create(getActivity(), R.raw.menutimer);
	        View rootView = inflater.inflate(R.layout.car, container, false);
	        selectedimages=new Integer[7];
	        selectedmodels=new String[7];
	        tmpids=new int[7];
	     description=(TextView)rootView.findViewById(R.id.cadesc);
	     timertext=(TextView)rootView.findViewById(R.id.catimer);
	     pausebutton=(Switch)rootView.findViewById(R.id.caswitch1);
	     tryagainbutton=(Button)rootView.findViewById(R.id.cayeniden);
	     imview=(ImageView)rootView.findViewById(R.id.caimage); 
	     incoming=getResources().getStringArray(R.array.car);
	    myTimer=new Timer();
	     if(DefaultManager.car_isBreak && !DefaultManager.car_isExit){
              final Dialog infodialog=new Dialog(getActivity());
              DefaultManager.car_isExit=false;
              infodialog.setTitle(getResources().getString(R.string.warning));
              infodialog.setContentView(R.layout.goahead);
              final TextView infotext=(TextView)infodialog.findViewById(R.id.wartextView2);
              final TextView infoque=(TextView)infodialog.findViewById(R.id.wartextView1);
              final Button infoyes=(Button)infodialog.findViewById(R.id.warbutton1);
              final Button infono=(Button)infodialog.findViewById(R.id.warbutton2);
              DefaultManager.car_isBreak=false;
              infotext.setText(getResources().getString(R.string.existing));
              infoque.setText(getResources().getString(R.string.condo));
              infoyes.setText(getResources().getString(R.string.evet));
              infono.setText(getResources().getString(R.string.hayir));
              infodialog.show();
              infoyes.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 currentposition=DefaultManager.car_lastposition;
			    	 count=DefaultManager.car_remaning;
			    	 DefaultManager.car_beginning=false;
			    	 DefaultManager.car_isBreak=false;
			    	 DefaultManager.car_pretimer=false;
			    	 DefaultManager.car_tryagain=false;
			    	 DefaultManager.car_isClicked=false;
			    	 DefaultManager.car_shaked=false;
			    	 DefaultManager.car_isFinish=false;
			    	 DefaultManager.car_isExit=false;
			    	 DefaultManager.car_isPaused=false;
			    	 selectedmodels=DefaultManager.car_currentdescs;
			    	 selectedimages=DefaultManager.car_currentimages;
			    	 description.setText(DefaultManager.car_currentdescs[currentposition]);
			    	 imview.setImageResource(selectedimages[currentposition]);
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
					 DefaultManager.car_lastposition=0;
			    	 currentposition=DefaultManager.car_lastposition;
			    	 DefaultManager.car_remaning=30;
			    	 count=DefaultManager.car_remaning;
			    	 
			    	 DefaultManager.car_beginning=true;
			    	 DefaultManager.car_pretimer=true;
			    	 DefaultManager.car_tryagain=false;
			    	 DefaultManager.car_isClicked=false;
			    	 DefaultManager.car_shaked=false;
			    	 DefaultManager.car_isFinish=false;
			    	 DefaultManager.car_isExit=true;
			    	 DefaultManager.car_isPaused=false;
			    	 DefaultManager.car_isBreak=false;
			    	 DefaultManager.car_correct=0;
			    	 DefaultManager.car_false=0;
			    	 selectedmodels=pickSeven(incoming);
			    	selectedimages=pickSeveni(images);
			    	 description.setText(getResources().getString(R.string.waiting));
			    	 imview.setImageResource(R.drawable.ozan);
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
	    	 DefaultManager.car_lastposition=0;
	    	 currentposition=DefaultManager.car_lastposition;
	    	 DefaultManager.car_remaning=30;
	          DefaultManager.car_isExit=false;
	    	 count=DefaultManager.car_remaning;
	    	// Toast.makeText(getActivity(), "Burada", Toast.LENGTH_SHORT).show();
	    	 DefaultManager.car_beginning=true;
	    	 DefaultManager.car_pretimer=true;
	    	 DefaultManager.car_tryagain=false;
	    	 DefaultManager.car_isClicked=false;
	    	 DefaultManager.car_shaked=false;
	    	 DefaultManager.car_isFinish=false;
	    	 DefaultManager.car_isExit=true;
	    	 DefaultManager.car_isPaused=false;
	    	 DefaultManager.car_isBreak=false;
	    	 DefaultManager.car_correct=0;
	    	 DefaultManager.car_false=0;
	    	 selectedmodels=pickSeven(incoming);
	    	 selectedimages=pickSeveni(images);
	    	 description.setText(getResources().getString(R.string.waiting));
	    	 imview.setImageResource(R.drawable.ozan);
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
	            DefaultManager.car_shaked=true;
	            }
	          });
	        
	        
	        tryagainbutton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					DefaultManager.car_tryagain=true;
			
				}
			});
	        pausebutton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	        	 
	        	   @Override
	        	   public void onCheckedChanged(CompoundButton buttonView,
	        	     boolean isChecked) {
	        	 
	        	    if(isChecked){
	        	    DefaultManager.car_isPaused=true;
	        	    }else{
	        	     DefaultManager.car_isPaused=false;
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
			if(DefaultManager.car_pretimer && count==30){
				//Toast.makeText(getActivity(), "2.burada",Toast.LENGTH_SHORT).show();
				if(precounter>2){
				timertext.setText(String.valueOf(txtcounter));
				txtcounter--;
				}else if(precounter<=2 && precounter>0){
				timertext.setText(getResources().getString(R.string.basliyor));
				}else if(precounter<=0){
					DefaultManager.car_pretimer=false;
					DefaultManager.car_beginning=false;
					timertext.setText(String.valueOf(count));
					imview.setImageResource(selectedimages[currentposition]);
					description.setText(selectedmodels[currentposition]);
				}
				precounter--;
			}else{
				if(count<=5 && count>=0 && !DefaultManager.car_isPaused){
					tplayer.start();
					}
				if(count>0 && !DefaultManager.car_isFinish && !DefaultManager.car_isPaused){
					timertext.setText(String.valueOf(count));
					count--;
					//Toast.makeText(getActivity(), "o", Toast.LENGTH_SHORT).show();
				}
				
				if(count==0 && !DefaultManager.car_isFinish && !DefaultManager.car_isPaused){
					timertext.setText(String.valueOf(count));
					DefaultManager.car_isFinish=true;
					DefaultManager.car_isPaused=true;
				}
				if(DefaultManager.car_isFinish){
					
					DefaultManager.car_isPaused=true;
					DefaultManager.car_isFinish=false;
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
					if(DefaultManager.car_correct>DefaultManager.default_score)
						DefaultManager.default_score=DefaultManager.car_correct;
					if(DefaultManager.car_correct>=1 && DefaultManager.car_correct<=2)
					size=1;
					else if(DefaultManager.car_correct>2 && DefaultManager.car_correct<=3)
						size=2;
					else if(DefaultManager.car_correct>3 && DefaultManager.car_correct<=4)
						size=3;
					else if(DefaultManager.car_correct>4 && DefaultManager.car_correct<=6)
						size=4;
					else if(DefaultManager.car_correct>5)
						size=5;
					
					
					
					cortext.setText(String.valueOf(DefaultManager.car_correct)+" "+getResources().getString(R.string.dogri));
					faltext.setText(String.valueOf(DefaultManager.car_false)+" "+getResources().getString(R.string.pas));
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
						DefaultManager.car_tryagain=true;	
						finishdialog.dismiss();
						}
					});
		               
		            nbutton.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							myTimer.cancel();
						DefaultManager.car_isExit=true;
						
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
				
				
				if(DefaultManager.car_isClicked && !DefaultManager.car_isPaused && !DefaultManager.car_isFinish){
					DefaultManager.car_isClicked=false;
					DefaultManager.car_shaked=false;
				if(currentposition<6){
					currentposition++;
				DefaultManager.car_correct++;
					description.setText(selectedmodels[currentposition]);
					imview.setImageResource(selectedimages[currentposition]);
				}else{
					DefaultManager.car_isFinish=true;
					
				}
				}
				
				if(DefaultManager.car_shaked && !DefaultManager.car_isPaused && !DefaultManager.car_isFinish){
					DefaultManager.car_shaked=false;
					if(currentposition<6){
						currentposition++;
						DefaultManager.car_false++;
						description.setText(selectedmodels[currentposition]);
						imview.setImageResource(selectedimages[currentposition]);
					}else{
						DefaultManager.car_isFinish=true;
			
					}
				}
				
				
				if(DefaultManager.car_tryagain){
		            
					DefaultManager.car_isClicked=false;
					DefaultManager.car_beginning=true;
					DefaultManager.car_pretimer=true;
					DefaultManager.car_isFinish=false;
					DefaultManager.car_isPaused=false;
					DefaultManager.car_isExit=false;
					description.setText(getResources().getString(R.string.waiting));
					imview.setImageResource(R.drawable.ozan);
					DefaultManager.car_remaning=30;
					count=DefaultManager.car_remaning;
					DefaultManager.car_shaked=false;
					DefaultManager.car_tryagain=false;
					selectedmodels=pickSeven(incoming);
			        selectedimages=pickSeveni(images);
			        
				    DefaultManager.car_isBreak=false;
				    DefaultManager.car_lastposition=0;
				    currentposition=DefaultManager.car_lastposition;
				    DefaultManager.car_correct=0;
				    DefaultManager.car_false=0;
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
	    	   tmpids[j]=tmpid;
	    	   tmpincoming.remove(tmpid);  
	    	  
	       }
	      	
		return tmppick;
		
	}
	
	public Integer [] pickSeveni(Integer [] in){
		
		List<Integer> tmpimp=new ArrayList<Integer>();
		
		Integer [] tmppick=new Integer[7];
			for(int i=0;i<in.length;i++){
				
				tmpimp.add(in[i]);
			}
				
	       for(int j=0;j<7;j++){
	   
	    	   tmppick[j]=tmpimp.get(tmpids[j]);
	    	   tmpimp.remove(tmpids[j]);
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
	    DefaultManager.car_tryagain=false;
	    DefaultManager.car_isPaused=true;
	    DefaultManager.car_isBreak=true;
	    DefaultManager.car_isFinish=false;
	    DefaultManager.car_currentdescs=selectedmodels;
	    DefaultManager.car_currentimages=selectedimages;
	    DefaultManager.car_lastposition=currentposition;
	    DefaultManager.car_isExit=false;
	    DefaultManager.car_remaning=count;
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
