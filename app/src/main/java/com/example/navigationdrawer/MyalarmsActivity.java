package com.example.navigationdrawer;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyalarmsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    CustomAdapter CustomAdapter;
    DatabaseHelper myDB;
    ArrayList<String> zipline_alarm, zipline_destination;
    ImageView empty_imageview;
    TextView no_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myalarms);


//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//

//        empty_imageview = findViewById(R.id.empty_imageview);
//        no_data = findViewById(R.id.no_data);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyalarmsActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });
        myDB = new DatabaseHelper(MyalarmsActivity.this);
        zipline_alarm = new ArrayList<>();
        zipline_destination = new ArrayList<>();

        storeDataInArrays();

        recyclerView = findViewById(R.id.recyclerview);
        CustomAdapter = new CustomAdapter(MyalarmsActivity.this, this, zipline_alarm, zipline_destination);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(CustomAdapter);


//        recyclerView = findViewById(R.id.recyclerview);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        recyclerView.setAdapter(CustomAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

      void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                zipline_alarm.add(cursor.getString(0));
                zipline_destination.add(cursor.getString(1));
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper myDB = new DatabaseHelper(MyalarmsActivity.this);
                myDB.deleteAllData();
                //Refresh Activity
                Intent intent = new Intent(MyalarmsActivity.this, MyalarmsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
