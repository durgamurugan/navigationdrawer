package com.example.navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.navigationdrawer.ui.map.MapFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyalarmsActivity.class);
                startActivity(intent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

            }
        });
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_map, R.id.nav_create, R.id.nav_myalarms,R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_map:
                        MapFragment mapFragment = new MapFragment();
                        FragmentManager manager = getSupportFragmentManager();
                        manager.beginTransaction().replace(R.id.mainLayout, mapFragment).commit();
                        Toast.makeText(getApplicationContext(), "Map is Selected", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(MainActivity.this, MapsActivity.class);
                        startActivity(i);
                        break;
                    case R.id.nav_create:
                        Toast.makeText(getApplicationContext(), " create is Selected", Toast.LENGTH_LONG).show();
                        Intent j = new Intent(MainActivity.this, CreateActivity.class);
                       startActivity(j);
                        break;
                    case R.id.nav_myalarams:
                        // fragment = new Alarms();
                        Toast.makeText(getApplicationContext(), "Alarms is Selected", Toast.LENGTH_LONG).show();
                         Intent k = new Intent(MainActivity.this,MyalarmsActivity.class);
                        startActivity(k);
                        break;
                    case R.id.nav_settings:
                        //fragment = new settings();
                        Toast.makeText(getApplicationContext(), "Settings is Selected", Toast.LENGTH_LONG).show();
                        Intent l = new Intent(MainActivity.this,SettingsActivity.class);
                        startActivity(l);
//                    default:
//                    case R.id.fragment:
//                        FragmentManager fragmentManager = getSupportFragmentManager();
//                        FragmentTransaction ft = fragmentManager.beginTransaction();
//
//                        ft.replace(R.id.screen_area, fragment);
//                        ft.commit();
                }
                drawer.closeDrawers();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
