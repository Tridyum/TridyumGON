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

public class ActorFragment extends Fragment {
	
	 private SensorManager mSensorManager;

	  private ShakeControl mSensorListener;
	  private Integer [] images={R.drawable.johnnydept,R.drawable.arnoldschwar,R.drawable.jimcarrey,R.drawable.emmewatson,R.drawable.danielradcli,R.drawable.leonardo,R.drawable.tomcruise,R.drawable.bradpitt,R.drawable.charleschaplin,R.drawable.morganfreee,R.drawable.tomhank,R.drawable.hughjack,R.drawable.mattdam,R.drawable.sylvesterstal,R.drawable.willsmith,R.drawable.clinteastwoo,R.drawable.camerondiaz,R.drawable.georgeclooney,R.drawable.stevenspielberg,R.drawable.harrisonford,R.drawable.robertdeniro,R.drawable.alpacino,R.drawable.robertdowney,R.drawable.russelcrowe,R.drawable.liamneeson,R.drawable.katewinslet,R.drawable.markwah,R.drawable.natalieport,R.drawable.piercebrosnan,R.drawable.seanconnery,R.drawable.orlando,R.drawable.dwayne,R.drawable.jackiechan,R.drawable.angelinajol,R.drawable.adamsandler,R.drawable.scarlettjohanso,R.drawable.heathledger,R.drawable.anne,R.drawable.jessica,R.drawable.edward,R.drawable.keira,R.drawable.bradleycooper,R.drawable.willferrell,R.drawable.juliaroberts,R.drawable.nicolascage,R.drawable.keanu,R.drawable.ian,R.drawable.halle,R.drawable.brucewillis,R.drawable.samualj,R.drawable.benstiller,R.drawable.tommylee,R.drawable.antonio,R.drawable.denzelwash,R.drawable.stevecarell,R.drawable.shialabeouf,R.drawable.megan,R.drawable.jamesfranco,R.drawable.mel,R.drawable.vin,R.drawable.timalle,R.drawable.robin,R.drawable.kevin,R.drawable.jason,R.drawable.seann,R.drawable.jeanclaude,R.drawable.zach,R.drawable.owen,R.drawable.christianbale,R.drawable.peterjack,R.drawable.sandrabullock,R.drawable.brucelee,R.drawable.drewbarrymore,R.drawable.macaulayculkin,R.drawable.jacknicholson,R.drawable.billmurray,R.drawable.sigourneyh,R.drawable.jake,R.drawable.kamal,R.drawable.jasonstat,R.drawable.jet,R.drawable.katebeck,R.drawable.rowanatkinson,R.drawable.marlon,R.drawable.johntravolta,R.drawable.channing,R.drawable.bena,R.drawable.shahruk,R.drawable.jenniferaniston,R.drawable.emma,R.drawable.chrishems,R.drawable.jamesmcavoy,R.drawable.jamescameron,R.drawable.amitabhbach,R.drawable.brendanfraser,R.drawable.rachelmcadams,R.drawable.tomhiddleston,R.drawable.aamirkhan,R.drawable.rajinik};
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
	  private TextView timertext;
	  private Random myrand=new Random();
	  private Switch pausebutton;
	  private Button tryagainbutton;
	  private ImageView imview;
	  private MediaPlayer tplayer;
	  
	  public ActorFragment(){}
	
	
	
	  @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.actor, container, false);
	        selectedimages=new Integer[7];
	        selectedmodels=new String[7];
	        tmpids=new int[7];
	     tplayer=MediaPlayer.create(getActivity(), R.raw.menutimer);
	     description=(TextView)rootView.findViewById(R.id.adesc);
	     timertext=(TextView)rootView.findViewById(R.id.atimer);
	     pausebutton=(Switch)rootView.findViewById(R.id.switch1);
	     tryagainbutton=(Button)rootView.findViewById(R.id.ayeniden);
	     imview=(ImageView)rootView.findViewById(R.id.aimage); 
	     incoming=getResources().getStringArray(R.array.actor);
	    myTimer=new Timer();
	     if(DefaultManager.act_isBreak && !DefaultManager.act_isExit){
             final Dialog infodialog=new Dialog(getActivity());
             DefaultManager.act_isExit=false;
             infodialog.setTitle(getResources().getString(R.string.warning));
             infodialog.setContentView(R.layout.goahead); 
             final TextView infotext=(TextView)infodialog.findViewById(R.id.wartextView2);
             final TextView infoque=(TextView)infodialog.findViewById(R.id.wartextView1);
             final Button infoyes=(Button)infodialog.findViewById(R.id.warbutton1);
             final Button infono=(Button)infodialog.findViewById(R.id.warbutton2);
             DefaultManager.act_isBreak=false;
             infotext.setText(getResources().getString(R.string.existing));
             infoque.setText(getResources().getString(R.string.condo));
             infoyes.setText(getResources().getString(R.string.evet));
             infono.setText(getResources().getString(R.string.hayir));
             infodialog.show();
             infoyes.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 currentposition=DefaultManager.act_lastposition;
			    	 count=DefaultManager.act_remaning;
			    	 DefaultManager.act_beginning=false;
			    	 DefaultManager.act_isBreak=false;
			    	 DefaultManager.act_pretimer=false;
			    	 DefaultManager.act_tryagain=false;
			    	 DefaultManager.act_isClicked=false;
			    	 DefaultManager.act_shaked=false;
			    	 DefaultManager.act_isFinish=false;
			    	 DefaultManager.act_isExit=false;
			    	 DefaultManager.act_isPaused=false;
			    	 selectedmodels=DefaultManager.act_currentdescs;
			    	 selectedimages=DefaultManager.act_currentimages;
			    	 description.setText(DefaultManager.act_currentdescs[currentposition]);
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
					 DefaultManager.act_lastposition=0;
			    	 currentposition=DefaultManager.act_lastposition;
			    	 DefaultManager.act_remaning=30;
			    	 count=DefaultManager.act_remaning;
			    	 
			    	 DefaultManager.act_beginning=true;
			    	 DefaultManager.act_pretimer=true;
			    	 DefaultManager.act_tryagain=false;
			    	 DefaultManager.act_isClicked=false;
			    	 DefaultManager.act_shaked=false;
			    	 DefaultManager.act_isFinish=false;
			    	 DefaultManager.act_isExit=true;
			    	 DefaultManager.act_isPaused=false;
			    	 DefaultManager.act_isBreak=false;
			    	 DefaultManager.act_correct=0;
			    	 DefaultManager.act_false=0;
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
	    	 DefaultManager.act_lastposition=0;
	    	 currentposition=DefaultManager.act_lastposition;
	    	 DefaultManager.act_remaning=30;
	          DefaultManager.act_isExit=false;
	    	 count=DefaultManager.act_remaning;
	    	 //Toast.makeText(getActivity(), "Burada", Toast.LENGTH_SHORT).show();
	    	 DefaultManager.act_beginning=true;
	    	 DefaultManager.act_pretimer=true;
	    	 DefaultManager.act_tryagain=false;
	    	 DefaultManager.act_isClicked=false;
	    	 DefaultManager.act_shaked=false;
	    	 DefaultManager.act_isFinish=false;
	    	 DefaultManager.act_isExit=true;
	    	 DefaultManager.act_isPaused=false;
	    	 DefaultManager.act_isBreak=false;
	    	 DefaultManager.act_correct=0;
	    	 DefaultManager.act_false=0;
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
	            DefaultManager.act_shaked=true;
	            }
	          });
	        
	        
	        tryagainbutton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					DefaultManager.act_tryagain=true;
			
				}
			});
	        pausebutton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	        	 
	        	   @Override
	        	   public void onCheckedChanged(CompoundButton buttonView,
	        	     boolean isChecked) {
	        	 
	        	    if(isChecked){
	        	    DefaultManager.act_isPaused=true;
	        	    }else{
	        	     DefaultManager.act_isPaused=false;
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
			if(DefaultManager.act_pretimer && count==30){
				//Toast.makeText(getActivity(), "2.burada",Toast.LENGTH_SHORT).show();
				if(precounter>2){
				timertext.setText(String.valueOf(txtcounter));
				txtcounter--;
				}else if(precounter<=2 && precounter>0){
				timertext.setText(getResources().getString(R.string.basliyor));
				}else if(precounter<=0){
					DefaultManager.act_pretimer=false;
					DefaultManager.act_beginning=false;
					timertext.setText(String.valueOf(count));
					imview.setImageResource(selectedimages[currentposition]);
					description.setText(selectedmodels[currentposition]);
				}
				precounter--;
			}else{
				
				if(count>0 && !DefaultManager.act_isFinish && !DefaultManager.act_isPaused){
					timertext.setText(String.valueOf(count));
					count--;
					//Toast.makeText(getActivity(), "o", Toast.LENGTH_SHORT).show();
				}
				if(count<=5 && count>=0 && !DefaultManager.act_isPaused){
				tplayer.start();
				}
				
				if(count==0 && !DefaultManager.act_isFinish && !DefaultManager.act_isPaused){
					timertext.setText(String.valueOf(count));
					DefaultManager.act_isFinish=true;
					DefaultManager.act_isPaused=true;
				}
				
				
				if(DefaultManager.act_isFinish){
					//Toast.makeText(getActivity(), "Finished",Toast.LENGTH_LONG).show();
					DefaultManager.act_isPaused=true;
					DefaultManager.act_isFinish=false;
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
					if(DefaultManager.act_correct>DefaultManager.default_score)
						DefaultManager.default_score=DefaultManager.act_correct;
					if(DefaultManager.act_correct>=1 && DefaultManager.act_correct<=2)
					size=1;
					else if(DefaultManager.act_correct>2 && DefaultManager.act_correct<=3)
						size=2;
					else if(DefaultManager.act_correct>3 && DefaultManager.act_correct<=4)
						size=3;
					else if(DefaultManager.act_correct>4 && DefaultManager.act_correct<=6)
						size=4;
					else if(DefaultManager.act_correct>5)
						size=5;
					
					
					
					cortext.setText(String.valueOf(DefaultManager.act_correct)+" "+getResources().getString(R.string.dogri));
					faltext.setText(String.valueOf(DefaultManager.act_false)+" "+getResources().getString(R.string.pas));
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
						DefaultManager.act_tryagain=true;	
						finishdialog.dismiss();
						}
					});
		               
		            nbutton.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							myTimer.cancel();
						DefaultManager.act_isExit=true;
						
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
				
				
				if(DefaultManager.act_isClicked && !DefaultManager.act_isPaused && !DefaultManager.act_isFinish){
					DefaultManager.act_isClicked=false;
					DefaultManager.act_shaked=false;
				if(currentposition<6){
					currentposition++;
				DefaultManager.act_correct++;
					description.setText(selectedmodels[currentposition]);
					imview.setImageResource(selectedimages[currentposition]);
				}else{
					DefaultManager.act_isFinish=true;
					
				}
				}
				
				if(DefaultManager.act_shaked && !DefaultManager.act_isPaused && !DefaultManager.act_isFinish){
					DefaultManager.act_shaked=false;
					if(currentposition<6){
						currentposition++;
						DefaultManager.act_false++;
						description.setText(selectedmodels[currentposition]);
						imview.setImageResource(selectedimages[currentposition]);
					}else{
						DefaultManager.act_isFinish=true;
			
					}
				}
				
				
				if(DefaultManager.act_tryagain){
		            
					DefaultManager.act_isClicked=false;
					DefaultManager.act_beginning=true;
					DefaultManager.act_pretimer=true;
					DefaultManager.act_isFinish=false;
					DefaultManager.act_isPaused=false;
					DefaultManager.act_isExit=false;
					description.setText(getResources().getString(R.string.waiting));
					imview.setImageResource(R.drawable.ozan);
					DefaultManager.act_remaning=30;
					count=DefaultManager.act_remaning;
					DefaultManager.act_shaked=false;
					DefaultManager.act_tryagain=false;
					selectedmodels=pickSeven(incoming);
			        selectedimages=pickSeveni(images);
				    DefaultManager.act_isBreak=false;
				    DefaultManager.act_lastposition=0;
				    currentposition=DefaultManager.act_lastposition;
				    DefaultManager.act_correct=0;
				    DefaultManager.act_false=0;
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
	    DefaultManager.act_tryagain=false;
	    DefaultManager.act_isPaused=true;
	    DefaultManager.act_isBreak=true;
	    DefaultManager.act_isFinish=false;
	    DefaultManager.act_isExit=false;
	    DefaultManager.act_currentdescs=selectedmodels;
	    DefaultManager.act_currentimages=selectedimages;
	    DefaultManager.act_lastposition=currentposition;
	    DefaultManager.act_remaning=count;
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
