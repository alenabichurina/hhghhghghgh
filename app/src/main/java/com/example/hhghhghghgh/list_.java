package com.example.hhghhghghgh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.LongSparseArray;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.hhghhghghgh.Db_class.Latitude;
import static com.example.hhghhghghgh.Db_class.Longitude;
import static com.example.hhghhghghgh.Db_class.Table;

public class list_ extends AppCompatActivity {
    public ListView listView;
    public Button delele;
    public Button edit;
    public Button show;
    public EditText lan;
    public EditText longt;
    public com.example.hhghhghghgh.Db_class Database;
    public long a;
    ArrayList<String> listitem;
    ArrayAdapter adapter;
    Object ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_);

        final EditText lan = (EditText) findViewById(R.id. lan);
        final EditText longt = (EditText) findViewById(R.id. longt);
        final Button show = findViewById(R.id. btnShow);
        final Button edit = findViewById(R.id. btnEdit);
        final Button delele = findViewById(R.id. btnDelete);
        final ListView listView = findViewById(R.id. listView1);
        show.setEnabled(false);
        delele.setEnabled(false);
        edit.setEnabled(false);
        // создание объекта класса бд
        Database = new com.example.hhghhghghgh.Db_class(this);

        // заносим данные в массив для отображения
        listitem = new ArrayList<>();

        final LongSparseArray IDs = new LongSparseArray();
        final Cursor Data = Database.GetData();
        int i = 0;
        if(Data.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Данных нет" + a, Toast.LENGTH_LONG).show();
        } else {
            while(Data.moveToNext()) {
                listitem.add("Широта: " + Data.getString(1) + " Долгота: " + Data.getString(2));
                IDs.put(i,Data.getString(0));
                i++;
            }

            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listitem);
            listView.setAdapter(adapter);
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                show.setEnabled(true);
                delele.setEnabled(true);
                edit.setEnabled(true);

                for(int i = 0; i < IDs.size(); i++) {
                    if(IDs.keyAt(i) == position) {
                        ID = IDs.valueAt(i);
                        Toast.makeText(getApplicationContext(), ID.toString() + "Реальный id: " + id, Toast.LENGTH_LONG).show();
                    }
                }
                Cursor Data = Database.GetData();
                while(Data.moveToNext()) {
                    if(Data.getString(0).equals(ID.toString())) {
                        a = Data.getLong(0);
                        lan.setText(Data.getString(1));
                        longt.setText(Data.getString(2));
                        Toast.makeText(getApplicationContext(), "ID: " + a + "Широта: " + Data.getString(1) + " Долгота: " + Data.getString(2), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        edit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sqLiteDatabase = Database.getWritable();
                ContentValues contentValues = new ContentValues();
                contentValues.put(Latitude, Double.parseDouble(lan.getText().toString()));
                contentValues.put(Longitude, Double.parseDouble(longt.getText().toString()));
                sqLiteDatabase.update(Table, contentValues, "ID = ?", new String[] {ID.toString()});

                list_.this.adapter.clear();
                list_.this.adapter.notifyDataSetChanged();
                final LongSparseArray IDs = new LongSparseArray();
                final Cursor Data = Database.GetData();
                int i = 0;
                if(Data.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Данных нет" + a, Toast.LENGTH_LONG).show();
                } else {
                    while(Data.moveToNext()) {
                        listitem.add("Широта: " + Data.getString(1) + " Долгота: " + Data.getString(2));
                        IDs.put(i,Data.getString(0));
                        i++;
                    }

                    adapter = new ArrayAdapter(list_.this, android.R.layout.simple_list_item_1, listitem);
                    listView.setAdapter(adapter);
                }
            }
        });

        delele.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sqLiteDatabase = Database.getWritable();
                sqLiteDatabase.delete(Table, "ID = ?", new String[] {ID.toString()});
                list_.this.adapter.clear();
                list_.this.adapter.notifyDataSetChanged();
                final LongSparseArray IDs = new LongSparseArray();
                final Cursor Data = Database.GetData();
                int i = 0;
                if(Data.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Данных нет" + a, Toast.LENGTH_LONG).show();
                } else {
                    while(Data.moveToNext()) {
                        listitem.add("Широта: " + Data.getString(1) + " Долгота: " + Data.getString(2));
                        IDs.put(i,Data.getString(0));
                        i++;
                    }

                    adapter = new ArrayAdapter(list_.this, android.R.layout.simple_list_item_1, listitem);
                    listView.setAdapter(adapter);
                }
            }
        });

        show.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(list_.this, com.example.hhghhghghgh.MainActivity.class);
                intent.putExtra("lan", Double.parseDouble(lan.getText().toString()));
                intent.putExtra("longt", Double.parseDouble(longt.getText().toString()));
               startActivity(intent);
            }
        });

    }
}
