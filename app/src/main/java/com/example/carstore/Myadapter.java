package com.example.carstore;

import static com.example.carstore.DBmain.TABLENAME;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Myadapter extends RecyclerView.Adapter<Myadapter.viewHolder> {
    Context context;
    int singledata;
    ArrayList<Model> modelArrayList;
    SQLiteDatabase sqLiteDatabase;

    public Myadapter(Context context, int singledata, ArrayList<Model> modelArrayList, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.singledata = singledata;
        this.modelArrayList = modelArrayList;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.carlistitem, parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {

        final Model model = modelArrayList.get(position);
        byte[] image = model.getProavatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.imgcar.setImageBitmap(bitmap);
        holder.t1.setText(model.getBrand());
        holder.t2.setText(model.getModel());
        holder.t3.setText(model.getYear());
        holder.t4.setText("$"+model.getPrice());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", model.getId());
                bundle.putByteArray("avatar", model.getProavatar());
                bundle.putString("brand", model.getBrand());
                bundle.putString("model", model.getModel());
                bundle.putString("year", model.getYear());
                bundle.putString("price", model.getPrice());
                Intent intent = new Intent(context, cardetail.class);
                intent.putExtra("userdata", bundle);
                context.startActivity(intent);
            }
        });

        holder.flowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(context, holder.flowbtn);
                menu.inflate(R.menu.flowmenu);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {

                            case R.id.edit:

                                Bundle bundle = new Bundle();
                                bundle.putInt("id", model.getId());
                                bundle.putByteArray("avatar", model.getProavatar());
                                bundle.putString("brand", model.getBrand());
                                bundle.putString("model", model.getModel());
                                bundle.putString("year", model.getYear());
                                bundle.putString("price", model.getPrice());
                                Intent intent = new Intent(context, addcar.class);
                                intent.putExtra("userdata", bundle);
                                context.startActivity(intent);

                                break;

                            case R.id.delete:
                                DBmain dBmain = new DBmain(context);
                                sqLiteDatabase = dBmain.getReadableDatabase();
                                long recdelete = sqLiteDatabase.delete(TABLENAME, "id=" + model.getId(), null);

                                if (recdelete != -1) {
                                    Toast.makeText(context, "Data Deleted", Toast.LENGTH_SHORT).show();
                                    modelArrayList.remove(position);
                                    notifyDataSetChanged();
                                }

                                break;

                            default:
                                return false;

                        }


                        return false;
                    }
                });
                menu.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public Model getnote(int position) {
        return modelArrayList.get(position);
    }


    public class viewHolder extends RecyclerView.ViewHolder {


        ImageView imgcar;
        TextView t1, t2, t3, t4;
        ImageButton flowbtn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgcar = itemView.findViewById(R.id.imagecar);
            t1 = itemView.findViewById(R.id.carname);
            t2 = itemView.findViewById(R.id.carmodel);
            t3 = itemView.findViewById(R.id.caryear);
            t4 = itemView.findViewById(R.id.carprice);
            flowbtn = itemView.findViewById(R.id.flowbtn);
        }
    }
}
