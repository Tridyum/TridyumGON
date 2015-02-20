package com.android.shakeandcheer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.pushbots.push.Pushbots;

import android.app.Dialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Files;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {
	
	  private TextView nickname;
	  private TextView header_nickname;
	  private TextView header_bestscore;
	  private TextView bestscore;
	  private ImageView userview;
	  private Button photobutton;
	  private boolean isRegistered=false;
	  private String phonePath=Environment.getExternalStorageDirectory().getAbsolutePath().toString();
	  
	  public HomeFragment(){}
	
	
	
	  @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.home, container, false);
	        nickname=(TextView)rootView.findViewById(R.id.hometextView2);
	         userview=(ImageView)rootView.findViewById(R.id.homeimageView1);
	         header_nickname=(TextView)rootView.findViewById(R.id.hometextView1);
	         bestscore=(TextView)rootView.findViewById(R.id.hometextView4);
	         header_bestscore=(TextView)rootView.findViewById(R.id.hometextView3);
	         photobutton=(Button)rootView.findViewById(R.id.homebutton1);
	         header_nickname.setText(getResources().getString(R.string.nickname));
	         header_bestscore.setText(getResources().getString(R.string.bestscore));
	         isRegistered=checkUserRegistration();
	         
	         bestscore.setText(String.valueOf(DefaultManager.default_score));
	         if(new File(phonePath+"/ShakeandCheer/").exists()==false){
	        	 new File(phonePath+"/ShakeandCheer/").mkdir();
	         }
	         
	         if(!isRegistered){
	        	 final Dialog regdialog=new Dialog(getActivity());
	        	 regdialog.setTitle(getResources().getString(R.string.nickname));
	        	 regdialog.setContentView(R.layout.register);
	        	 final EditText register_entry=(EditText)regdialog.findViewById(R.id.regeditText1);
	        	 final TextView register_title=(TextView)regdialog.findViewById(R.id.regtextView1);
	        	 final Button register_button=(Button)regdialog.findViewById(R.id.regbutton1);
	        	 register_title.setText(getResources().getString(R.string.regitertitle));
	        regdialog.show();
	        	 register_button.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(register_entry.getText().toString().equals("")==false && register_entry.getText().toString().equals(" ")==false && register_entry!=null){
						
							SQLiteOpenHelper hlp=new Ozandb(getActivity());
							SQLiteDatabase dbt=hlp.getWritableDatabase();
							ContentValues val=new ContentValues();
							val.put(Ozandb.username,register_entry.getText().toString());
							val.put(Ozandb.userpoint,0);
							long row=dbt.insert(Ozandb.tableName,null,val);
							Toast.makeText(getActivity(),String.valueOf(row),Toast.LENGTH_SHORT).show();
							nickname.setText(register_entry.getText().toString());
							bestscore.setText("0");
						    isRegistered=true;	
							regdialog.dismiss();
						}else{
							Toast.makeText(getActivity(),getResources().getString(R.string.registerinfo),Toast.LENGTH_SHORT).show();
						}
					}
				});
	         }else{
	        
	        	bringUser();
	       File myfile=new File(phonePath+"/ShakeandCheer/");
	       File [] fss=myfile.listFiles();
	       if(myfile.exists()){
	       if(fss.length>=1){
	    	   Bitmap dbtmap=BitmapFactory.decodeFile(phonePath+"/ShakeandCheer/user.png");
	    	   if(userview!=null){
	    		   userview.setImageBitmap(dbtmap);
	    	   }
	       }
	       }
	         }
	        	 
	         photobutton.setText(getResources().getString(R.string.fotograf));
	         
	         photobutton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final Intent photointent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						startActivityForResult(photointent, 1);
					}
				});
	            
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
	  
	  
	  public boolean checkUserRegistration(){
		  SQLiteOpenHelper reghelper=new Ozandb(getActivity());
		  SQLiteDatabase regdb=reghelper.getReadableDatabase();
		  final String regQuery="SELECT * FROM "+Ozandb.tableName;
		  Cursor regcursor=regdb.rawQuery(regQuery, null);
		  if(regcursor.moveToFirst()==false){
			  return false;
		  }
		  return true;
	  }

      @Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
Bundle extras = data.getExtras();
Bitmap imageBitmap = (Bitmap) extras.get("data");
userview.setImageBitmap(imageBitmap);

try {
	imageBitmap.compress(CompressFormat.PNG,90,new FileOutputStream(new File(phonePath.toString()+"/ShakeandCheer/"+"user.png")));
} catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}
}
	  public void bringUser(){
		  SQLiteOpenHelper reghelper=new Ozandb(getActivity());
		  SQLiteDatabase regdb=reghelper.getReadableDatabase();
		  final String regQuery="SELECT * FROM "+Ozandb.tableName;
		  Cursor regcursor=regdb.rawQuery(regQuery, null);
		  if(regcursor.moveToFirst()){
			  if(nickname!=null && bestscore!=null){
			nickname.setText(regcursor.getString(1));
		    bestscore.setText(String.valueOf(regcursor.getInt(2)));
			  }
		  }
		
	  }
	  
}
