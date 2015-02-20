
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

public class ClothFragment extends Fragment {
	 private SensorManager mSensorManager;

	  private ShakeControl mSensorListener;
	  private Integer [] images={R.drawable.suit,R.drawable.shirt,R.drawable.tie,R.drawable.dress,R.drawable.blouse,R.drawable.skirt,R.drawable.coat,R.drawable.jacket,R.drawable.tshirt,R.drawable.trousers,R.drawable.jeans,R.drawable.shorts,R.drawable.pullover,R.drawable.cardigan,R.drawable.sweatshirt,R.drawable.gloves,R.drawable.mittens,R.drawable.romper,R.drawable.nappies,R.drawable.trunks,R.drawable.bikini,R.drawable.nightdress,R.drawable.pyjamas,R.drawable.dressgown,R.drawable.boxers,R.drawable.yfronts,R.drawable.bra,R.drawable.pants,R.drawable.stocking,R.drawable.tights,R.drawable.vest,R.drawable.shoes,R.drawable.sandal,R.drawable.boot,R.drawable.wellingtons,R.drawable.slippers,R.drawable.socks,R.drawable.beret,R.drawable.bowler,R.drawable.trilby,R.drawable.woolen,R.drawable.woolen,R.drawable.woolen,R.drawable.belt,R.drawable.handbag,R.drawable.suit,R.drawable.scarf,R.drawable.glasses,R.drawable.sunglasses,R.drawable.watch,R.drawable.gloves,R.drawable.hat,R.drawable.hanger,R.drawable.bracelet,R.drawable.necklace,R.drawable.ring,R.drawable.earings};	
	  private String  [] incoming;
	  private String [] selectedmodels;
	  private Integer [] selectedimages;
	  private int [] tmpids;
	  private int baslangic=0;
	  private Timer myTimer;
	  private int precounter=5;
	  private int txtcounter=3;
	  private int currentposition=0;
	  private int count=30;
	  private TextView description;
	  private MediaPlayer tplayer;
	  private TextView timertext;
	  private Random myrand=new Random();
	  private Switch pausebutton;
	  private Button tryagainbutton;
	  private ImageView imview;
	  
	  public ClothFragment(){}
	
	
	
	  @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.cloth, container, false);
	        tplayer=MediaPlayer.create(getActivity(), R.raw.menutimer);
	        selectedimages=new Integer[7];
	        selectedmodels=new String[7];
	        tmpids=new int[7];
	     description=(TextView)rootView.findViewById(R.id.cdesc);
	     timertext=(TextView)rootView.findViewById(R.id.ctimer);
	     pausebutton=(Switch)rootView.findViewById(R.id.cswitch1);
	     tryagainbutton=(Button)rootView.findViewById(R.id.cyeniden);
	     imview=(ImageView)rootView.findViewById(R.id.cimage); 
	     incoming=getResources().getStringArray(R.array.cloth);
	    myTimer=new Timer();
	     if(DefaultManager.cloth_isBreak && !DefaultManager.cloth_isExit){
             final Dialog infodialog=new Dialog(getActivity());
             DefaultManager.cloth_isExit=false;
             infodialog.setTitle(getResources().getString(R.string.warning));
             infodialog.setContentView(R.layout.goahead);
             final TextView infotext=(TextView)infodialog.findViewById(R.id.wartextView2);
             final TextView infoque=(TextView)infodialog.findViewById(R.id.wartextView1);
             final Button infoyes=(Button)infodialog.findViewById(R.id.warbutton1);
             final Button infono=(Button)infodialog.findViewById(R.id.warbutton2);
             DefaultManager.cloth_isBreak=false;
             infotext.setText(getResources().getString(R.string.existing));
             infoque.setText(getResources().getString(R.string.condo));
             infoyes.setText(getResources().getString(R.string.evet));
             infono.setText(getResources().getString(R.string.hayir));
             infodialog.show();
             infoyes.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 currentposition=DefaultManager.cloth_lastposition;
			    	 count=DefaultManager.cloth_remaning;
			    	 DefaultManager.cloth_beginning=false;
			    	 DefaultManager.cloth_isBreak=false;
			    	 DefaultManager.cloth_pretimer=false;
			    	 DefaultManager.cloth_tryagain=false;
			    	 DefaultManager.cloth_isClicked=false;
			    	 DefaultManager.cloth_shaked=false;
			    	 DefaultManager.cloth_isFinish=false;
			    	 DefaultManager.cloth_isExit=false;
			    	 DefaultManager.cloth_isPaused=false;
			    	 selectedmodels=DefaultManager.cloth_currentdescs;
			    	 selectedimages=DefaultManager.cloth_currentimages;
			    	 description.setText(DefaultManager.cloth_currentdescs[currentposition]);
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
					 DefaultManager.cloth_lastposition=0;
			    	 currentposition=DefaultManager.cloth_lastposition;
			    	 DefaultManager.cloth_remaning=30;
			    	 count=DefaultManager.cloth_remaning;
			    	 
			    	 DefaultManager.cloth_beginning=true;
			    	 DefaultManager.cloth_pretimer=true;
			    	 DefaultManager.cloth_tryagain=false;
			    	 DefaultManager.cloth_isClicked=false;
			    	 DefaultManager.cloth_shaked=false;
			    	 DefaultManager.cloth_isFinish=false;
			    	 DefaultManager.cloth_isExit=true;
			    	 DefaultManager.cloth_isPaused=false;
			    	 DefaultManager.cloth_isBreak=false;
			    	 DefaultManager.cloth_correct=0;
			    	 DefaultManager.cloth_false=0;
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
	    	 DefaultManager.cloth_lastposition=0;
	    	 currentposition=DefaultManager.cloth_lastposition;
	    	 DefaultManager.cloth_remaning=30;
	          DefaultManager.cloth_isExit=false;
	    	 count=DefaultManager.cloth_remaning;
	    	 //Toast.makeText(getActivity(), "Burada", Toast.LENGTH_SHORT).show();
	    	 DefaultManager.cloth_beginning=true;
	    	 DefaultManager.cloth_pretimer=true;
	    	 DefaultManager.cloth_tryagain=false;
	    	 DefaultManager.cloth_isClicked=false;
	    	 DefaultManager.cloth_shaked=false;
	    	 DefaultManager.cloth_isFinish=false;
	    	 DefaultManager.cloth_isExit=true;
	    	 DefaultManager.cloth_isPaused=false;
	    	 DefaultManager.cloth_isBreak=false;
	    	 DefaultManager.cloth_correct=0;
	    	 DefaultManager.cloth_false=0;
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
	            DefaultManager.cloth_shaked=true;
	            }
	          });
	        
	        
	        tryagainbutton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					DefaultManager.cloth_tryagain=true;
			
				}
			});
	        pausebutton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	        	 
	        	   @Override
	        	   public void onCheckedChanged(CompoundButton buttonView,
	        	     boolean isChecked) {
	        	 
	        	    if(isChecked){
	        	    DefaultManager.cloth_isPaused=true;
	        	    }else{
	        	     DefaultManager.cloth_isPaused=false;
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
			if(DefaultManager.cloth_pretimer && count==30){
				//Toast.makeText(getActivity(), "2.burada",Toast.LENGTH_SHORT).show();
				if(precounter>2){
				timertext.setText(String.valueOf(txtcounter));
				txtcounter--;
				}else if(precounter<=2 && precounter>0){
				timertext.setText(getResources().getString(R.string.basliyor));
				}else if(precounter<=0){
					DefaultManager.cloth_pretimer=false;
					DefaultManager.cloth_beginning=false;
					timertext.setText(String.valueOf(count));
					imview.setImageResource(selectedimages[currentposition]);
					description.setText(selectedmodels[currentposition]);
				}
				precounter--;
			}else{
				if(count<=5 && count>=0 && !DefaultManager.cloth_isPaused){
					tplayer.start();
					}
				if(count>0 && !DefaultManager.cloth_isFinish && !DefaultManager.cloth_isPaused){
					timertext.setText(String.valueOf(count));
					count--;
					//Toast.makeText(getActivity(), "o", Toast.LENGTH_SHORT).show();
				}
				
				if(count==0 && !DefaultManager.cloth_isFinish && !DefaultManager.cloth_isPaused){
					timertext.setText(String.valueOf(count));
					DefaultManager.cloth_isFinish=true;
					DefaultManager.cloth_isPaused=true;
				}
				if(DefaultManager.cloth_isFinish){
					//Toast.makeText(getActivity(), "Finished",Toast.LENGTH_LONG).show();
					
					DefaultManager.cloth_isFinish=false;
					DefaultManager.cloth_isPaused=true;
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
					if(DefaultManager.cloth_correct>DefaultManager.default_score)
						DefaultManager.default_score=DefaultManager.cloth_correct;
					if(DefaultManager.cloth_correct>=1 && DefaultManager.cloth_correct<=2)
					size=1;
					else if(DefaultManager.cloth_correct>2 && DefaultManager.cloth_correct<=3)
						size=2;
					else if(DefaultManager.cloth_correct>3 && DefaultManager.cloth_correct<=4)
						size=3;
					else if(DefaultManager.cloth_correct>4 && DefaultManager.cloth_correct<=6)
						size=4;
					else if(DefaultManager.cloth_correct>5)
						size=5;
					
					
					
					cortext.setText(String.valueOf(DefaultManager.cloth_correct)+" "+getResources().getString(R.string.dogri));
					faltext.setText(String.valueOf(DefaultManager.cloth_false)+" "+getResources().getString(R.string.pas));
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
						DefaultManager.cloth_tryagain=true;	
						finishdialog.dismiss();
						}
					});
		               
		            nbutton.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							myTimer.cancel();
						DefaultManager.cloth_isExit=true;
						
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
				
				
				if(DefaultManager.cloth_isClicked && !DefaultManager.cloth_isPaused && !DefaultManager.cloth_isFinish){
					DefaultManager.cloth_isClicked=false;
					DefaultManager.cloth_shaked=false;
				if(currentposition<6){
					currentposition++;
				DefaultManager.cloth_correct++;
					description.setText(selectedmodels[currentposition]);
					imview.setImageResource(selectedimages[currentposition]);
				}else{
					DefaultManager.cloth_isFinish=true;
					
				}
				}
				
				if(DefaultManager.cloth_shaked && !DefaultManager.cloth_isPaused && !DefaultManager.cloth_isFinish){
					DefaultManager.cloth_shaked=false;
					if(currentposition<6){
						currentposition++;
						DefaultManager.cloth_false++;
						description.setText(selectedmodels[currentposition]);
						imview.setImageResource(selectedimages[currentposition]);
					}else{
						DefaultManager.cloth_isFinish=true;
			
					}
				}
				
				
				if(DefaultManager.cloth_tryagain){
		            
					DefaultManager.cloth_isClicked=false;
					DefaultManager.cloth_beginning=true;
					DefaultManager.cloth_pretimer=true;
					DefaultManager.cloth_isFinish=false;
					DefaultManager.cloth_isPaused=false;
					DefaultManager.cloth_isExit=false;
					description.setText(getResources().getString(R.string.waiting));
					imview.setImageResource(R.drawable.ozan);
					DefaultManager.cloth_remaning=30;
					count=DefaultManager.cloth_remaning;
					DefaultManager.cloth_shaked=false;
					DefaultManager.cloth_tryagain=false;
					selectedmodels=pickSeven(incoming);
			selectedimages=pickSeveni(images);
				    DefaultManager.cloth_isBreak=false;
				    DefaultManager.cloth_lastposition=0;
				    currentposition=DefaultManager.cloth_lastposition;
				    DefaultManager.cloth_correct=0;
				    DefaultManager.cloth_false=0;
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
	    DefaultManager.cloth_tryagain=false;
	    DefaultManager.cloth_isPaused=true;
	    DefaultManager.cloth_isBreak=true;
	    DefaultManager.cloth_isFinish=false;
	    DefaultManager.cloth_currentdescs=selectedmodels;
	    DefaultManager.cloth_currentimages=selectedimages;
	    DefaultManager.cloth_lastposition=currentposition;
	    DefaultManager.cloth_remaning=count;
	    DefaultManager.cloth_isExit=false;
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
