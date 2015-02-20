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

public class PokemonFragment extends Fragment{
	 private SensorManager mSensorManager;

	  private ShakeControl mSensorListener;
	  private Integer [] images={R.drawable.bulbasaur,R.drawable.ivy,R.drawable.vena,R.drawable.charmendar,R.drawable.charmelon,R.drawable.charizard,R.drawable.squirtle,R.drawable.wartortle,R.drawable.blasto,R.drawable.cater,R.drawable.meta,R.drawable.butter,R.drawable.weedl,R.drawable.kakuna,R.drawable.beedri,R.drawable.pidgey,R.drawable.pidgeotto,R.drawable.pidgeot,R.drawable.rattata,R.drawable.raticate,R.drawable.spearow,R.drawable.fear,R.drawable.ekans,R.drawable.arbok,R.drawable.pikachu,R.drawable.raic,R.drawable.sandshrew,R.drawable.sandslash,R.drawable.nidoran,R.drawable.nidorina,R.drawable.nidoqueen,R.drawable.nidorane,R.drawable.nidorino,R.drawable.nidoking,R.drawable.clefairy,R.drawable.clefable,R.drawable.vulpix,R.drawable.ninetales,R.drawable.jiggle,R.drawable.wigglyt,R.drawable.zubat,R.drawable.golbat,R.drawable.oddish,R.drawable.gloom,R.drawable.vileplume,R.drawable.paras,R.drawable.parasect,R.drawable.venomoth,R.drawable.diglett,R.drawable.dugtrio,R.drawable.meowth,R.drawable.persian,R.drawable.psy,R.drawable.mankey,R.drawable.primeape,R.drawable.growlithe,R.drawable.arcanine,R.drawable.poliwag,R.drawable.poliwhirl,R.drawable.poliwrath,R.drawable.abra,R.drawable.kadab,R.drawable.alakazam,R.drawable.machop,R.drawable.machoke,R.drawable.machamp,R.drawable.bellsprout,R.drawable.weepinbell,R.drawable.victreebel,R.drawable.tentacool,R.drawable.tentacruel,R.drawable.graveer,R.drawable.golem,R.drawable.ponyta,R.drawable.rapidash,R.drawable.slowpoke,R.drawable.slowbro,R.drawable.magnemite,R.drawable.magneton,R.drawable.farfetch,R.drawable.doduo,R.drawable.dodrio,R.drawable.seel,R.drawable.dewgong,R.drawable.grimer,R.drawable.muk,R.drawable.shellder,R.drawable.cloyster,R.drawable.gastly,R.drawable.haunter,R.drawable.gengar,R.drawable.onix,R.drawable.drowzee,R.drawable.hypno,R.drawable.krabby,R.drawable.kingler,R.drawable.voltorb,R.drawable.electrode,R.drawable.exegg,R.drawable.cubone,R.drawable.marowak,R.drawable.hitmonlee,R.drawable.hitmonchan,R.drawable.lickitung,R.drawable.koffing,R.drawable.weezing,R.drawable.rhy,R.drawable.rhydon,R.drawable.channing,R.drawable.kangaskhan,R.drawable.horsea,R.drawable.seadra,R.drawable.goldeen,R.drawable.seaking,R.drawable.staryu,R.drawable.starme,R.drawable.mime,R.drawable.scyther,R.drawable.jynx,R.drawable.electrabuzz,R.drawable.magmar,R.drawable.pinsir,R.drawable.tauros,R.drawable.magikarp,R.drawable.gyara,R.drawable.lapras,R.drawable.ditto,R.drawable.eevee,R.drawable.vaporeon,R.drawable.jolteon,R.drawable.flareon,R.drawable.flareon,R.drawable.porygon,R.drawable.oman,R.drawable.omastar,R.drawable.kabutops,R.drawable.aerodactyl,R.drawable.snorlax,R.drawable.articuno,R.drawable.zapdos,R.drawable.moltres,R.drawable.dratini,R.drawable.dragonair,R.drawable.dragonite,R.drawable.mewtwo,R.drawable.mew};
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
	  private MediaPlayer tplayer;
	  private Button tryagainbutton;
	  private ImageView imview;
	  
	  public PokemonFragment(){}
	
	
	
	  @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.pokemon, container, false);
	        tplayer=MediaPlayer.create(getActivity(), R.raw.menutimer);
	        selectedimages=new Integer[7];
	        selectedmodels=new String[7];
	        tmpids=new int[7];
	     description=(TextView)rootView.findViewById(R.id.pdesc);
	     timertext=(TextView)rootView.findViewById(R.id.ptimer);
	     pausebutton=(Switch)rootView.findViewById(R.id.pswitch1);
	     tryagainbutton=(Button)rootView.findViewById(R.id.pyeniden);
	     imview=(ImageView)rootView.findViewById(R.id.pimage); 
	     incoming=getResources().getStringArray(R.array.pokemon);
	    myTimer=new Timer();
	     if(DefaultManager.poke_isBreak && !DefaultManager.poke_isExit){
             final Dialog infodialog=new Dialog(getActivity());
             DefaultManager.poke_isExit=false;
             infodialog.setTitle(getResources().getString(R.string.warning));
             infodialog.setContentView(R.layout.goahead);
             final TextView infotext=(TextView)infodialog.findViewById(R.id.wartextView2);
             final TextView infoque=(TextView)infodialog.findViewById(R.id.wartextView1);
             final Button infoyes=(Button)infodialog.findViewById(R.id.warbutton1);
             final Button infono=(Button)infodialog.findViewById(R.id.warbutton2);
             DefaultManager.poke_isBreak=false;
             infotext.setText(getResources().getString(R.string.existing));
             infoque.setText(getResources().getString(R.string.condo));
             infoyes.setText(getResources().getString(R.string.evet));
             infono.setText(getResources().getString(R.string.hayir));
             infodialog.show();
             infoyes.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 currentposition=DefaultManager.poke_lastposition;
			    	 count=DefaultManager.poke_remaning;
			    	 DefaultManager.poke_beginning=false;
			    	 DefaultManager.poke_isBreak=false;
			    	 DefaultManager.poke_pretimer=false;
			    	 DefaultManager.poke_tryagain=false;
			    	 DefaultManager.poke_isClicked=false;
			    	 DefaultManager.poke_shaked=false;
			    	 DefaultManager.poke_isFinish=false;
			    	 DefaultManager.poke_isExit=false;
			    	 DefaultManager.poke_isPaused=false;
			    	 selectedmodels=DefaultManager.poke_currentdescs;
			    	 selectedimages=DefaultManager.poke_currentimages;
			    	 description.setText(DefaultManager.poke_currentdescs[currentposition]);
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
					 DefaultManager.poke_lastposition=0;
			    	 currentposition=DefaultManager.poke_lastposition;
			    	 DefaultManager.poke_remaning=30;
			    	 count=DefaultManager.poke_remaning;
			    	 
			    	 DefaultManager.poke_beginning=true;
			    	 DefaultManager.poke_pretimer=true;
			    	 DefaultManager.poke_tryagain=false;
			    	 DefaultManager.poke_isClicked=false;
			    	 DefaultManager.poke_shaked=false;
			    	 DefaultManager.poke_isFinish=false;
			    	 DefaultManager.poke_isExit=true;
			    	 DefaultManager.poke_isPaused=false;
			    	 DefaultManager.poke_isBreak=false;
			    	 DefaultManager.poke_correct=0;
			    	 DefaultManager.poke_false=0;
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
	    	 DefaultManager.poke_lastposition=0;
	    	 currentposition=DefaultManager.poke_lastposition;
	    	 DefaultManager.poke_remaning=30;
	          DefaultManager.poke_isExit=false;
	    	 count=DefaultManager.poke_remaning;
	    	 //Toast.makeText(getActivity(), "Burada", Toast.LENGTH_SHORT).show();
	    	 DefaultManager.poke_beginning=true;
	    	 DefaultManager.poke_pretimer=true;
	    	 DefaultManager.poke_tryagain=false;
	    	 DefaultManager.poke_isClicked=false;
	    	 DefaultManager.poke_shaked=false;
	    	 DefaultManager.poke_isFinish=false;
	    	 DefaultManager.poke_isExit=true;
	    	 DefaultManager.poke_isPaused=false;
	    	 DefaultManager.poke_isBreak=false;
	    	 DefaultManager.poke_correct=0;
	    	 DefaultManager.poke_false=0;
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
	            DefaultManager.poke_shaked=true;
	            }
	          });
	        
	        
	        tryagainbutton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					DefaultManager.poke_tryagain=true;
			
				}
			});
	        pausebutton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	        	 
	        	   @Override
	        	   public void onCheckedChanged(CompoundButton buttonView,
	        	     boolean isChecked) {
	        	 
	        	    if(isChecked){
	        	    DefaultManager.poke_isPaused=true;
	        	    }else{
	        	     DefaultManager.poke_isPaused=false;
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
			if(DefaultManager.poke_pretimer && count==30){
				//Toast.makeText(getActivity(), "2.burada",Toast.LENGTH_SHORT).show();
				if(precounter>2){
				timertext.setText(String.valueOf(txtcounter));
				txtcounter--;
				}else if(precounter<=2 && precounter>0){
				timertext.setText(getResources().getString(R.string.basliyor));
				}else if(precounter<=0){
					DefaultManager.poke_pretimer=false;
					DefaultManager.poke_beginning=false;
					timertext.setText(String.valueOf(count));
					imview.setImageResource(selectedimages[currentposition]);
					description.setText(selectedmodels[currentposition]);
				}
				precounter--;
			}else{
				if(count<=5 && count>=0 && !DefaultManager.poke_isPaused){
					tplayer.start();
					}
				
				if(count>0 && !DefaultManager.poke_isFinish && !DefaultManager.poke_isPaused){
					timertext.setText(String.valueOf(count));
					count--;
					//Toast.makeText(getActivity(), "o", Toast.LENGTH_SHORT).show();
				}
				
				if(count==0 && !DefaultManager.poke_isFinish && !DefaultManager.poke_isPaused){
					timertext.setText(String.valueOf(count));
					DefaultManager.poke_isFinish=true;
					DefaultManager.poke_isPaused=true;
				}
				if(DefaultManager.poke_isFinish){
					//Toast.makeText(getActivity(), "Finished",Toast.LENGTH_LONG).show();
					
					DefaultManager.poke_isFinish=false;
					DefaultManager.poke_isPaused=true;
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
					if(DefaultManager.poke_correct>=1 && DefaultManager.poke_correct<=2)
					size=1;
					else if(DefaultManager.poke_correct>2 && DefaultManager.poke_correct<=3)
						size=2;
					else if(DefaultManager.poke_correct>3 && DefaultManager.poke_correct<=4)
						size=3;
					else if(DefaultManager.poke_correct>4 && DefaultManager.poke_correct<=6)
						size=4;
					else if(DefaultManager.poke_correct>5)
						size=5;
					
					
					if(DefaultManager.poke_correct>DefaultManager.default_score)
						DefaultManager.default_score=DefaultManager.poke_correct;
					
					cortext.setText(String.valueOf(DefaultManager.poke_correct)+" "+getResources().getString(R.string.dogri));
					faltext.setText(String.valueOf(DefaultManager.poke_false)+" "+getResources().getString(R.string.pas));
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
						DefaultManager.poke_tryagain=true;	
						finishdialog.dismiss();
						}
					});
		               
		            nbutton.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							myTimer.cancel();
						DefaultManager.poke_isExit=true;
						
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
				
				
				if(DefaultManager.poke_isClicked && !DefaultManager.poke_isPaused && !DefaultManager.poke_isFinish){
					DefaultManager.poke_isClicked=false;
					DefaultManager.poke_shaked=false;
				if(currentposition<6){
					currentposition++;
				DefaultManager.poke_correct++;
					description.setText(selectedmodels[currentposition]);
					imview.setImageResource(selectedimages[currentposition]);
				}else{
					DefaultManager.poke_isFinish=true;
					
				}
				}
				
				if(DefaultManager.poke_shaked && !DefaultManager.poke_isPaused && !DefaultManager.poke_isFinish){
					DefaultManager.poke_shaked=false;
					if(currentposition<6){
						currentposition++;
						DefaultManager.poke_false++;
						description.setText(selectedmodels[currentposition]);
						imview.setImageResource(selectedimages[currentposition]);
					}else{
						DefaultManager.poke_isFinish=true;
			
					}
				}
				
				
				if(DefaultManager.poke_tryagain){
		            
					DefaultManager.poke_isClicked=false;
					DefaultManager.poke_beginning=true;
					DefaultManager.poke_pretimer=true;
					DefaultManager.poke_isFinish=false;
					DefaultManager.poke_isPaused=false;
					DefaultManager.poke_isExit=false;
					description.setText(getResources().getString(R.string.waiting));
					imview.setImageResource(R.drawable.ozan);
					DefaultManager.poke_remaning=30;
					count=DefaultManager.poke_remaning;
					DefaultManager.poke_shaked=false;
					DefaultManager.poke_tryagain=false;
					selectedmodels=pickSeven(incoming);
			selectedimages=pickSeveni(images);
				    DefaultManager.poke_isBreak=false;
				    DefaultManager.poke_lastposition=0;
				    currentposition=DefaultManager.poke_lastposition;
				    DefaultManager.poke_correct=0;
				    DefaultManager.poke_false=0;
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
	    DefaultManager.poke_tryagain=false;
	    DefaultManager.poke_isPaused=true;
	    DefaultManager.poke_isBreak=true;
	    DefaultManager.poke_isExit=false;
	    DefaultManager.poke_isFinish=false;
	    DefaultManager.poke_currentdescs=selectedmodels;
	    DefaultManager.poke_currentimages=selectedimages;
	    DefaultManager.poke_lastposition=currentposition;
	    DefaultManager.poke_remaning=count;
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