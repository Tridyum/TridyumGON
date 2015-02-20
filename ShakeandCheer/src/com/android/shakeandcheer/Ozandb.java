package com.android.shakeandcheer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Ozandb extends SQLiteOpenHelper {
private static final String dbName="ShakeDbb";
public static final String tableName="Shaketable";
public static final String username="username";
public static final String userpoint="userpoint";
public static final String userid="userid";
private static final String update_query="DROP TABLE IF  EXISTS "+tableName;
private static final String register_query="CREATE TABLE "+tableName+" ( "+userid+"INTEGER PRIMARY KEY "+", "+username+" TEXT , "+userpoint+" INTEGER "+")";
private static final int version=1;

	public Ozandb(Context context) {
		super(context,dbName, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(register_query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(update_query);
		onCreate(db);
	}

}
