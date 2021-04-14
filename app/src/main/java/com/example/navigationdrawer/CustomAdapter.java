package com.example.navigationdrawer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList zipline_alarm, zipline_destination;



    CustomAdapter(Activity activity, Context context, ArrayList zipline_alarm, ArrayList zipline_destination){
        this.activity = activity;
        this.context = context;

        this.zipline_alarm = zipline_alarm;
        this.zipline_destination = zipline_destination;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
       // final RecyclerView itemList = listItems.get(position);
        holder.txtTitle.setText(String.valueOf(zipline_alarm.get(position)));
        holder.txtDescription.setText(String.valueOf(zipline_destination.get(position)));

        //Recyclerview onClickListener
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("alarm", String.valueOf(zipline_alarm.get(position)));
                intent.putExtra("destination", String.valueOf(zipline_destination.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zipline_alarm.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context,"Deleted",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return zipline_alarm.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle ,txtDescription,edit,delete;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            edit = itemView.findViewById(R.id.txtedit);
            delete = itemView.findViewById(R.id.txtdelete);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }

}
