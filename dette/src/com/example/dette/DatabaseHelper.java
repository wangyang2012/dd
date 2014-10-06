package com.example.dette;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	final static String DATABASENAME		= "db_tetee";
	final static int VERSION				= 1;
	final static String TABLENAME			= "tetee";
	final static String col_date			= "date";
	final static String col_heure			= "heure";
	final static String col_gauche			= "gauche";
	final static String col_droite			= "droite";
	final static String col_debut			= "debut";
	final static String col_pipi			= "pipi";
	final static String col_caca			= "caca";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASENAME, null, VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE `" + TABLENAME + "` (`" + col_date + "` DATE NOT NULL, `" + col_heure + "` TIME NOT NULL, `" + col_gauche + "` INT, `" + col_droite + "` INT, `" + col_debut + "` CHAR NULL, `" + col_pipi + "` TINYINT NULL, `" + col_caca + "` TINYINT NULL, PRIMARY KEY (`" + col_date + "`, `" + col_heure + "`));";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
