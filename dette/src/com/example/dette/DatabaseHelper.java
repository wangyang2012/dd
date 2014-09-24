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
//		String sql = "CREATE TABLE `tetee`.`tetee` (`date` DATE NOT NULL, `heure` TIME NOT NULL, `gauche` TIME NULL, `droite` TIME NULL, `debut` CHAR NULL, `pipi` TINYINT NULL, `caca` TINYINT NULL, PRIMARY KEY (`date`, `heure`));";
		String sql = "CREATE TABLE `" + TABLENAME + "` (`" + col_date + "` DATE NOT NULL, `" + col_heure + "` TIME NOT NULL, `" + col_gauche + "` TIME NULL, `" + col_droite + "` TIME NULL, `" + col_debut + "` CHAR NULL, `" + col_pipi + "` TINYINT NULL, `" + col_caca + "` TINYINT NULL, PRIMARY KEY (`" + col_date + "`, `" + col_heure + "`));";
		db.execSQL(sql);
		
//		String sqlInsert = "INSERT INTO " + TABLENAME + " VALUES ('2011-7-8', '25:00', '10:00', '15:00', 'G', TRUE, FALSE);";
		String sqlInsert = "INSERT INTO `" + TABLENAME + "` (`" + col_date + "`, `" + col_heure + "`, `" + col_gauche + "`, `" + col_droite + "`, `" + col_debut + "`, `" + col_pipi + "`, `" + col_caca + "`) VALUES ('2014-10-10', '11:11', '10:00', '11:00', 'G', '1', '0');";
		db.execSQL(sqlInsert);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
