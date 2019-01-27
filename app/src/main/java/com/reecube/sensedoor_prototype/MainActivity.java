package com.reecube.sensedoor_prototype;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;

    private Fragment[] fragments;

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_DASHBOARD = 1;
    private static final int FRAGMENT_MATRIX = 2;
    private static final int FRAGMENT_JOYSTICK = 3;

    private void loadFragment(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragments[index]);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle(R.string.title_home);
                    loadFragment(FRAGMENT_HOME);
                    return true;
                case R.id.navigation_dashboard:
                    toolbar.setTitle(R.string.title_dashboard);
                    loadFragment(FRAGMENT_DASHBOARD);
                    return true;
                case R.id.navigation_matrix:
                    toolbar.setTitle(R.string.title_matrix);
                    loadFragment(FRAGMENT_MATRIX);
                    return true;
                case R.id.navigation_joystick:
                    toolbar.setTitle(R.string.title_joystick);
                    loadFragment(FRAGMENT_JOYSTICK);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        fragments = new Fragment[]{
                new HomeFragment(),
                new DashboardFragment(),
                new MatrixFragment(),
                new JoystickFragment(),
        };

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar.setTitle(R.string.title_home);

        loadFragment(FRAGMENT_HOME);
    }

}
