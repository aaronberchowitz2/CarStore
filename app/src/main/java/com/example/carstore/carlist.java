package com.example.carstore;

import static com.example.carstore.DBmain.TABLENAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class carlist extends AppCompatActivity {

    DBmain dBmain;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    Myadapter myadapter;
    FloatingActionButton fa;
    Model model;
    ArrayList<Model> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carlist);
        dBmain = new DBmain(this);
        Model model = new Model();
        models = new ArrayList<>();

        recyclerView = findViewById(R.id.recview);
        fa = findViewById(R.id.fabutton);
        DBmain dBmain = new DBmain(this);
        display();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
             fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(carlist.this, addcar.class));
            }
        });


    }

    private void display() {

        sqLiteDatabase = dBmain.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLENAME + "", null);
        ArrayList<Model> models = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            byte[] avatar = cursor.getBlob(1);
            String brand = cursor.getString(2);
            String model = cursor.getString(3);
            String year = cursor.getString(4);
            String price = cursor.getString(5);

            models.add(new Model(id, avatar, brand, model, year, price));

        }
        cursor.close();
        myadapter = new Myadapter(carlist.this, R.layout.carlistitem, models, sqLiteDatabase);
        recyclerView.setAdapter(myadapter);

    }
}