package com.example.dette;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.example.teteeRecord.R;

public class MainActivity extends Activity {
	
	private SQLiteDatabase db;
	
	private SimpleAdapter adapter;
	
	@Override
	protected void onResume() {
		super.onResume();
		doListView();
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /* Button quitter */
        Button btnQuitter = (Button) this.findViewById(R.id.btn_quitter);
        btnQuitter.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				finish();
			}
        });
        
        /* Button commencer */
        Button btnDette = (Button) this.findViewById(R.id.btn_dette);
        btnDette.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				createDette();
			}
        });
        
        /* liste view */
        doListView();
    }


	private void doListView() {
		ListView listView = (ListView) this.findViewById(R.id.listView);  
          
        //获取到集合数据
        List<Dette> dettes = getData();  
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
        
        for(Dette dette : dettes){  
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put("date", DateUtils.dateToString(dette.getDate()));  
            item.put("heure", DateUtils.heureToString(dette.getHeure()));  
            item.put("sein_gauche", dette.getSeinGauche());
            item.put("sein_droite", dette.getSeinDroite());
            item.put("commencer", dette.getCommencer());
            item.put("pipi", dette.getPipi());
            item.put("caca", dette.getCaca());
            data.add(item);
        }

       //创建SimpleAdapter适配器将数据绑定到item显示控件上  
       adapter = new SimpleAdapter(this, data, R.layout.item, new String[]{"date", "heure", "sein_gauche", "sein_droite", "commencer", "pipi", "caca"}, new int[]{R.id.date, R.id.heure, R.id.sein_gauche, R.id.sein_droit, R.id.commencer, R.id.pipi, R.id.caca});

       //实现列表的显示  
       listView.setAdapter(adapter);  
	}

	private List<Dette> getData() {
		List<Dette> dettes = new ArrayList<Dette>();
		DatabaseHelper helper = new DatabaseHelper(getBaseContext());
		db = helper.getReadableDatabase();
		Cursor cursor = db.query(DatabaseHelper.TABLENAME, new String[]{DatabaseHelper.col_date, DatabaseHelper.col_heure, DatabaseHelper.col_gauche, DatabaseHelper.col_droite, DatabaseHelper.col_debut, DatabaseHelper.col_pipi, DatabaseHelper.col_caca}, null, null, null, null, null);
		cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Dette dette = cursorToDette(cursor);
	      dettes.add(dette);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
		return dettes;
	}
	
	private Dette cursorToDette(Cursor cursor) {
	    Dette dette = new Dette();
	    try {
			dette.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(0)));
		} catch (ParseException e) {
			dette.setDate(new Date());
		}
	    try {
	    	dette.setHeure(new SimpleDateFormat("HH:mm").parse(cursor.getString(1)));
	    } catch (ParseException e) {
	    	dette.setHeure(new Date());
	    }
	    dette.setSeinGauche(Integer.valueOf(cursor.getInt(2)));
	    dette.setSeinDroite(Integer.valueOf(cursor.getInt(3)));
	    dette.setCommencer(cursor.getString(4));
	    dette.setPipi(cursor.getInt(5) == 1);
	    dette.setCaca(cursor.getInt(6) == 1);
	    return dette;
	  }
	
    private void createDette() {
    	Intent intent = new Intent(MainActivity.this, DetteActivity.class);
    	startActivity(intent);
    }
    
    private final class ItemClickListener implements OnItemClickListener{  
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  
            ListView listView = (ListView) parent;  
            @SuppressWarnings("unchecked")
			HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);  
            String heure = data.get("duree").toString();  
            Toast.makeText(getApplicationContext(), heure, Toast.LENGTH_SHORT).show();  
        }  
    }  

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
