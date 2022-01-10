package com.example.carstore;

import static com.example.carstore.DBmain.TABLENAME;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class addcar extends AppCompatActivity {
    ImageView avatar;
    EditText brand, model, year, price;
    Button add, choose,edit;
    SQLiteDatabase sqLiteDatabase;
    Uri filepath;
    Bitmap bitmap;
   int id=0;

    DBmain dBmain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcar);
         dBmain= new DBmain(this);

        avatar = findViewById(R.id.addimage);
        brand = findViewById(R.id.addname);
        model = findViewById(R.id.addmodel);
        year = findViewById(R.id.addyear);
        price = findViewById(R.id.addprice);
        add = findViewById(R.id.add);
        choose=findViewById(R.id.lisdisp);
        edit=findViewById(R.id.edit);
        editdata();
        insertdata();

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "please select file"), 101);

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                        permissionToken.continuePermissionRequest();

                    }
                }).check();
            }

        });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addcar.this,carlist.class));
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                cv.put("avatar", ImageviewTOByte(avatar));
                cv.put("brand", brand.getText().toString());
                cv.put("model", model.getText().toString());
                cv.put("year", year.getText().toString());
                cv.put("price", price.getText().toString());

                sqLiteDatabase = dBmain.getWritableDatabase();

                long recinsert= sqLiteDatabase.update(TABLENAME,cv,"id="+id,null);

                if(recinsert!=-1)
                {
                    Toast.makeText(addcar.this, "Successfully Inserted", Toast.LENGTH_SHORT).show();
                    avatar.setImageResource(R.drawable.ic_baseline_camera_alt_24);
                    brand.setText("");
                    model.setText("");
                    year.setText("");
                    price.setText("");
                    edit.setVisibility(View.GONE);
                    add.setVisibility(View.VISIBLE);
                }
                else{
                    Toast.makeText(addcar.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void editdata() {

        if(getIntent().getBundleExtra("userdata")!=null){

            Bundle bundle= getIntent().getBundleExtra("userdata");
            id=bundle.getInt("id");
            byte[]bytes =bundle.getByteArray("avatar");
            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            avatar.setImageBitmap(bitmap);
            brand.setText(bundle.getString("brand"));
            model.setText(bundle.getString("model"));
            year.setText(bundle.getString("year"));
            price.setText(bundle.getString("price"));

            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);

        }
    }

    private void insertdata() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                cv.put("avatar", ImageviewTOByte(avatar));
                cv.put("brand", brand.getText().toString());
                cv.put("model", model.getText().toString());
                cv.put("year", year.getText().toString());
                cv.put("price", price.getText().toString());

             sqLiteDatabase = dBmain.getWritableDatabase();

            Long recinsert= sqLiteDatabase.insert(TABLENAME,null,cv);

            if(recinsert!=null)
            {
                Toast.makeText(addcar.this, "Successfully Inserted", Toast.LENGTH_SHORT).show();
            avatar.setImageResource(R.drawable.ic_baseline_camera_alt_24);
            brand.setText("");
            model.setText("");
            year.setText("");
            price.setText("");
            }
            else{
                Toast.makeText(addcar.this, "Failed", Toast.LENGTH_SHORT).show();
            }
            }
        });

    }

    private byte[] ImageviewTOByte(ImageView avatar) {
        Bitmap bitmap = ((BitmapDrawable) avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101 && resultCode==RESULT_OK)
        {
         filepath =data.getData();
            try {
                InputStream inputStream= getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                avatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}