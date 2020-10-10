package com.example.hhghhghghgh;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import static com.example.hhghhghghgh.Db_class.Latitude;
import static com.example.hhghhghghgh.Db_class.Longitude;
import static com.example.hhghhghghgh.Db_class.Table;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private EditText searchView;
    private String search = "";
    private double lan = 0;
    private double longt = 0;
    public com.example.hhghhghghgh.Db_class Database;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
               //     Database.insert(lan,longt);
                    SQLiteDatabase sqLiteDatabase = Database.getWritable();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(Latitude, lan);
                    contentValues.put(Longitude, longt);
                    sqLiteDatabase.insert(Table,null,contentValues);
                    return true;
                case R.id.navigation_dashboard:
                    LayoutInflater li = LayoutInflater.from(MainActivity.this);
                    View search_form = li.inflate(R.layout.search_form, null);
                    final AlertDialog.Builder builder_search = new AlertDialog.Builder(MainActivity.this);
                    builder_search.setView(search_form);
                    final EditText searchView = (EditText)search_form.findViewById(R.id. search_view);
                    builder_search.setCancelable(true);
                    builder_search.setTitle("Поиск места");
                    builder_search.setPositiveButton("Найти", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(searchView.getText().toString().equals("")) {
                                Toast.makeText(getApplicationContext(), "Ошибка, укажите адрес!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Ищем", Toast.LENGTH_LONG).show();
                                Geocoder geocoder = new Geocoder(MainActivity.this);
                                List<Address> address_ = null;
                                try {
                                    address_ = geocoder.getFromLocationName(searchView.getText().toString(), 1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Address address = address_.get(0);
                                LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                                lan = address.getLatitude();
                                longt = address.getLongitude();
                                mMap.clear();
                                mMap.addMarker(new MarkerOptions().position(latLng).title(searchView.getText().toString()));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
                            }
                        }
                    });
                    builder_search.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog_search = builder_search.create();
                    dialog_search.show();
                    return true;
               //     return true;
                case R.id.navigation_notifications:
                    Intent intent = new Intent(MainActivity.this, com.example.hhghhghghgh.list_.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // иницилизируем карты
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Database = new com.example.hhghhghghgh.Db_class(this);
        // получаем данные для отображения с листа
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null) {
            lan = (double) arguments.get("lan");
            longt = (double) arguments.get("longt");
            Toast.makeText(getApplicationContext(), "Широта: " + lan + " Долгота" + longt + " Отображаем... ", Toast.LENGTH_LONG).show();
            LatLng latLng = new LatLng(lan,longt);
            mMap.addMarker(new MarkerOptions().position(latLng).title(""));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
        }
    }
}
