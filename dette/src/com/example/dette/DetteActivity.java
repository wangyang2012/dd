package com.example.dette;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class DetteActivity extends Activity {

	private boolean isPausedGauche;
	private boolean isPausedDroite;
	private boolean isGaucheRunning;
	private boolean isDroiteRunning;
	private String timeUsedGauche;
	private String timeUsedDroite;
	private int timeUsedInsecGauche;
	private int timeUsedInsecDroite;
	private TextView tvGauche;
	private TextView tvDroite;
	private Button btnGauche;
	private Button btnDroite;
	private boolean isGaucheFirst;
	private Boolean isFirstStart;
	
	private RadioButton radioButtonGauche;
	private RadioButton radioButtonDroite;

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
		uiHandle.sendEmptyMessageDelayed(1, 1000);
		if (isFirstStart) {
			isGaucheFirst = true;
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
	}
	
	private void startDroite() {
		isPausedDroite = false;
		uiHandle.sendEmptyMessageDelayed(2, 1000);
		if (isFirstStart) {
			isGaucheFirst = false;
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dette);
		
		isFirstStart = true;
		isPausedGauche = true;
		isPausedDroite = true;
		isGaucheRunning = false;
		isDroiteRunning = false;

		/* Date */
		TextView tvDate = (TextView) findViewById(R.id.newDate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		tvDate.setText(dateFormat.format(new Date()));
		
		/* Heure */
		TextView tvHeure = (TextView) findViewById(R.id.newHeure);
		SimpleDateFormat heureFormat = new SimpleDateFormat("hh:mm");
		tvHeure.setText(heureFormat.format(new Date()));

		/* Gauche && Droite */
		tvGauche = (TextView) findViewById(R.id.newGauche);
		tvDroite = (TextView) findViewById(R.id.newDroite);
		
		/* Ordre */
		radioButtonGauche = (RadioButton) findViewById(R.id.newParGauche);
		radioButtonDroite = (RadioButton) findViewById(R.id.newParDroite);

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
		Button btnValider = (Button) findViewById(R.id.newValider);
		btnValider.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (isPausedGauche && isPausedDroite) {
					Toast.makeText(getApplicationContext(), "Commenc¨¦ par: " + getFirst(), Toast.LENGTH_SHORT).show();
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "Pas encore fini!", Toast.LENGTH_SHORT).show();
				}
				
			}
		});

		/* Annuler */
		Button btnAnnuler = (Button) findViewById(R.id.newAnnuler);
		btnAnnuler.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (isPausedGauche && isPausedDroite) {
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "Pas encore fini!", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private String getFirst() {
		if (isFirstStart) {
			return "rien";
		} else {
			return isGaucheFirst ? "Gauche" : "Droite";
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
