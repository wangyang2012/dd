package com.example.dette;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.teteeRecord.R;

public class DetteActivity extends Activity {

	private boolean isPausedGauche;
	private boolean isPausedDroite;
	private boolean isGaucheRunning;
	private boolean isDroiteRunning;
	private String timeUsedGauche;
	private String timeUsedDroite;
	private int timeUsedInsecGauche;
	private int timeUsedInsecDroite;
	private EditText tvDate;
	private EditText tvHeure;
	private EditText tvGauche;
	private EditText tvDroite;
	private Button btnGauche;
	private Button btnDroite;
	private boolean isFirstStart;
	
	private RadioButton radioButtonGauche;
	private RadioButton radioButtonDroite;
	
	private CheckBox pipi;
	private CheckBox caca;
	
	private Button btnValider;
	private Button btnAnnuler;
	
	private SQLiteDatabase db;

	@SuppressLint("HandlerLeak")
	private Handler uiHandle = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 1:
					if (!isPausedGauche) {
						addTimeUsed(true);
						updateClockUI(true);
						uiHandle.sendEmptyMessageDelayed(1, 1000);
					}
					break;
				case 2:
					if (!isPausedDroite) {
						addTimeUsed(false);
						updateClockUI(false);
						uiHandle.sendEmptyMessageDelayed(2, 1000);
					}
					break;
				default:
					break;
			}
		}
	};

	private void clickGauche() {
		if (isGaucheRunning) {
			stopGauche();
		} else {
			startGauche();
		}
	}
	
	private void clickDroite() {
		if (isDroiteRunning) {
			stopDroite();
		} else {
			startDroite();
		}
	}
	
	private void startGauche() {
		isPausedGauche = false;
		btnValider.setEnabled(false);
		btnDroite.setEnabled(false);
		uiHandle.sendEmptyMessageDelayed(1, 1000);
		if (isFirstStart) {
			isFirstStart = false;
			radioButtonGauche.setChecked(true);
		}
		btnGauche.setText(R.string.stopGauche);
		isGaucheRunning = true;
	}

	private void stopGauche() {
		isPausedGauche = true;
		btnGauche.setText(R.string.startGauche);
		isGaucheRunning = false;
		btnValider.setEnabled(true);
		btnDroite.setEnabled(true);
	}
	
	private void startDroite() {
		isPausedDroite = false;
		btnValider.setEnabled(false);
		btnGauche.setEnabled(false);
		uiHandle.sendEmptyMessageDelayed(2, 1000);
		if (isFirstStart) {
			isFirstStart = false;
			radioButtonDroite.setChecked(true);
		}
		btnDroite.setText(R.string.stopDroite);
		isDroiteRunning = true;
	}
	
	private void stopDroite() {
		isPausedDroite = true;
		btnDroite.setText(R.string.startDroite);
		isDroiteRunning = false;
		btnValider.setEnabled(true);
		btnGauche.setEnabled(true);
	}

	private void updateClockUI(boolean gauche) {
		if (gauche) {
			tvGauche.setText(timeUsedGauche);
		} else {
			tvDroite.setText(timeUsedDroite);
		}
	}

	public void addTimeUsed(boolean gauche) {
		if (gauche) {
			timeUsedInsecGauche = timeUsedInsecGauche + 1;
			timeUsedGauche = this.getMin(timeUsedInsecGauche) + ":" + this.getSec(timeUsedInsecGauche);
		} else {
			timeUsedInsecDroite = timeUsedInsecDroite + 1;
			timeUsedDroite = this.getMin(timeUsedInsecDroite) + ":" + this.getSec(timeUsedInsecDroite);
		}
	}

	public CharSequence getMin(int time) {
		return String.valueOf(time / 60);
	}

	public CharSequence getSec(int time) {
		int sec = time % 60;
		return sec < 10 ? "0" + sec : String.valueOf(sec);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dette);
		
		isFirstStart = true;
		isPausedGauche = true;
		isPausedDroite = true;
		isGaucheRunning = false;
		isDroiteRunning = false;

		/* Date */
		tvDate = (EditText) findViewById(R.id.newDate);
		tvDate.setText(DateUtils.dateToString(new Date()));
		
		/* Heure */
		tvHeure = (EditText) findViewById(R.id.newHeure);
		tvHeure.setText(DateUtils.heureToString(new Date()));

		/* Gauche && Droite */
		tvGauche = (EditText) findViewById(R.id.newGauche);
		tvDroite = (EditText) findViewById(R.id.newDroite);
		
		
		tvGauche.setOnKeyListener(new OnKeyListener() {
			
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (isFirstStart) {
					isFirstStart = false;
					radioButtonGauche.setChecked(true);
				}
				stopGauche();
				return false;
			}
		});
		
		tvDroite.setOnKeyListener(new OnKeyListener() {
			
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (isFirstStart) {
					isFirstStart = false;
					radioButtonDroite.setChecked(true);
				}
				stopDroite();
				return false;
			}
		});
		
		/* Ordre */
		radioButtonGauche = (RadioButton) findViewById(R.id.newParGauche);
		radioButtonDroite = (RadioButton) findViewById(R.id.newParDroite);
		
		/* Pipi */
		pipi = (CheckBox) findViewById(R.id.newPipi);
		
		/* Caca */
		caca = (CheckBox) findViewById(R.id.newCaca);

		/* Button Gauche */
		btnGauche = (Button) findViewById(R.id.newStartGauche);
		btnGauche.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				clickGauche();
			}
		});
		
		/* Button Droite */
		btnDroite = (Button) findViewById(R.id.newStartDroite);
		btnDroite.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				clickDroite();
			}
		});
		
		/* Valider */
		btnValider = (Button) findViewById(R.id.newValider);
		btnValider.setEnabled(false);
		btnValider.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (isPausedGauche && isPausedDroite) {
					writeRecord();
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "Pas encore fini!", Toast.LENGTH_SHORT).show();
				}
				
			}
		});

		/* Annuler */
		btnAnnuler = (Button) findViewById(R.id.newAnnuler);
		btnAnnuler.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (isPausedGauche && isPausedDroite) {
					AlertDialog.Builder builder = new Builder(DetteActivity.this);
					builder.setMessage("Are you sure to reset?");
					builder.setTitle("Confirmation Dialog");

					builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});	

					builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});	

					builder.create().show();
				} else {
					Toast.makeText(getApplicationContext(), "Pas encore fini!", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	protected void writeRecord() {
		DatabaseHelper helper = new DatabaseHelper(getBaseContext());
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		String date = tvDate.getText().toString();
		if (date == null) {
			date = DateUtils.dateToString(new Date());
		}
		
		String heure = tvHeure.getText().toString();
		if (heure == null) {
			heure = DateUtils.heureToString(new Date());
		}
		
		Integer gauche = DateUtils.formatHeure(tvGauche.getText().toString());
		Integer droite = DateUtils.formatHeure(tvDroite.getText().toString());
		
		values.put(DatabaseHelper.col_date, date);
		values.put(DatabaseHelper.col_heure, heure);
		values.put(DatabaseHelper.col_gauche, gauche);
		values.put(DatabaseHelper.col_droite, droite);
		values.put(DatabaseHelper.col_debut, getFirst());
		values.put(DatabaseHelper.col_pipi, pipi.isChecked() ? 1 : 0);
		values.put(DatabaseHelper.col_caca, caca.isChecked() ? 1 : 0);
		db.insert(DatabaseHelper.TABLENAME, null, values);
	}
	

	private String getFirst() {
		if (radioButtonGauche.isChecked()) {
			return "G";
		} else {
			return "D";
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
