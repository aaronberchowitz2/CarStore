package com.example.carstore;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class cardetail extends AppCompatActivity {

    ImageView img;
    TextView t1, t2, t3, t4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardetail);
        img = findViewById(R.id.imgdetail);
        t1 = findViewById(R.id.cname);
        t2 = findViewById(R.id.cmodel);
        t3 = findViewById(R.id.cyear);
        t4 = findViewById(R.id.cprice);

        editdata();
    }

    private void editdata() {

        if (getIntent().getBundleExtra("userdata") != null) {

            Bundle bundle = getIntent().getBundleExtra("userdata");
            //id=bundle.getInt("id");
            byte[] bytes = bundle.getByteArray("avatar");
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            img.setImageBitmap(bitmap);
            t1.setText(bundle.getString("brand"));
            t2.setText(bundle.getString("model"));
            t3.setText(bundle.getString("year"));
            t4.setText("$"+bundle.getString("price"));

        }
    }
}