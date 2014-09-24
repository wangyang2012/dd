package com.example.dette;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private SQLiteDatabase db;
	
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
        
        
//        doListViewTable();
        /* liste view */
        doListView();
    }

//	private void doListViewTable() {
//		TableLayout tableLayout = (TableLayout) findViewById(R.id.table);
//		tableLayout.setStretchAllColumns(true);
//		List<Dette> dettes = getScrollData(0, 50);
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat dureeFormat = new SimpleDateFormat("hh:mm");
//        
//		for (Dette dette : dettes) {
//			TableRow row = new TableRow(getApplicationContext());
//			
//			/* date */
//			TextView date = new TextView(getApplicationContext());
//			date.setText(dateFormat.format(dette.getDate()));
//			
//			/* heure */
//			TextView heure = new TextView(getApplicationContext());
//			heure.setText(dureeFormat.format(dette.getHeure()));
//			
//			/* durée */
//			TextView duree = new TextView(getApplicationContext());
//			duree.setText(dureeFormat.format(dette.getDuree()));
//			
//			row.addView(date);
//			row.addView(heure);
//			row.addView(duree);
//			tableLayout.addView(row);
//		}
//	}

	private void doListView() {
		ListView listView = (ListView) this.findViewById(R.id.listView);  
          
        //获取到集合数据
		// TODO: à supprimer
//        List<Dette> dettes = getScrollData(0, 30);  
        List<Dette> dettes = getData();  
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm");
        
        for(Dette dette : dettes){  
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put("date", dateFormat.format(dette.getDate()));  
            item.put("heure", heureFormat.format(dette.getHeure()));  
            item.put("sein_gauche", dette.getSeinGauche());
            item.put("sein_droite", dette.getSeinDroite());
            item.put("commencer", dette.getCommencer());
            item.put("pipi", dette.getPipi());
            item.put("caca", dette.getCaca());
            data.add(item);
        }
        
       //创建SimpleAdapter适配器将数据绑定到item显示控件上  
       SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item, new String[]{"date", "heure", "sein_gauche", "sein_droite", "commencer", "pipi", "caca"}, new int[]{R.id.date, R.id.heure, R.id.sein_gauche, R.id.sein_droit, R.id.commencer, R.id.pipi, R.id.caca});  

       //实现列表的显示  
       listView.setAdapter(adapter);  
       
       //条目点击事件  
       listView.setOnItemClickListener(new ItemClickListener());
	}

	private List<Dette> getData() {
		List<Dette> dettes = new ArrayList<Dette>();
		DatabaseHelper helper = new DatabaseHelper(getBaseContext());
		db = helper.getReadableDatabase();
		Cursor cursor = db.query(DatabaseHelper.TABLENAME, new String[]{DatabaseHelper.col_date, DatabaseHelper.col_heure, DatabaseHelper.col_gauche, DatabaseHelper.col_droite, DatabaseHelper.col_debut, DatabaseHelper.col_pipi, DatabaseHelper.col_caca}, null, null, null, null, null);
		if (cursor.getCount() > 0) {
			Dette dette = new Dette();
			for (String colName : cursor.getColumnNames()) {
				if (DatabaseHelper.col_date.equals(colName)) {
					dette.setDate(new Date());
				} else if (DatabaseHelper.col_heure.equals(colName)) {
					dette.setHeure(new Date());
				} else if (DatabaseHelper.col_gauche.equals(colName)) {
					dette.setSeinGauche(15);
				} else if (DatabaseHelper.col_droite.equals(colName)) {
					dette.setSeinDroite(10);
				}
				dette.setCommencer("D");
				dette.setPipi(true);
				dette.setCaca(false);
			}
			dettes.add(dette);
		}
		return dettes;
	}
	
	// TODO: à supprimer
    private List<Dette> getScrollData(int min, int max) {
		List<Dette> dettes = new ArrayList<Dette>();
		for (int i = min; i < max; i++) {
			dettes.add(new Dette(new Date(), new Date(), 10, 15 , "G", true, true));
		}
		return dettes;
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
