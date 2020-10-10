package com.example.hhghhghghgh;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class list extends AppCompatActivity {
    public ListView listView;
    public Button delele;
    public Button edit;
    public Button show;
    public EditText lan;
    public EditText longt;
    public com.example.hhghhghghgh.Db_class Database;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final EditText lan = (EditText) findViewById(R.id. lan);
        final EditText longt = (EditText) findViewById(R.id. longt);
        Button show = findViewById(R.id. btnShow);
        Button edit = findViewById(R.id. btnEdit);
        Button delele = findViewById(R.id. btnDelete);
        Database = new com.example.hhghhghghgh.Db_class(this);


    }
}
