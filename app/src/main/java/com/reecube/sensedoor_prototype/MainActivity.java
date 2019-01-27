package com.reecube.sensedoor_prototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;

    private AbstractFirebaseFragment[] fragments;

    private FirebaseAuth mAuth;

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

    private static final int RC_SIGN_IN = 9001;

    private void startSignIn() {
        // Build FirebaseUI sign in intent. For documentation on this operation and all
        // possible customization see: https://github.com/firebase/firebaseui-android
        Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                .setAvailableProviders(Collections.singletonList(
                        new AuthUI.IdpConfig.EmailBuilder().build()))
                .setLogo(R.mipmap.ic_launcher)
                .build();

        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign in succeeded
                onUserUpdated(mAuth.getCurrentUser());
            } else {
                // Sign in failed
                Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                onUserUpdated(null);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        fragments = new AbstractFirebaseFragment[4];

        fragments[FRAGMENT_HOME] = new HomeFragment();
        fragments[FRAGMENT_DASHBOARD] = new DashboardFragment();
        fragments[FRAGMENT_JOYSTICK] = new JoystickFragment();
        fragments[FRAGMENT_MATRIX] = new MatrixFragment();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar.setTitle(R.string.title_home);

        loadFragment(FRAGMENT_HOME);
    }

    @Override
    protected void onStart() {
        super.onStart();

        onUserUpdated(mAuth.getCurrentUser());
    }

    private void onUserUpdated(FirebaseUser user) {
        if (user == null) {
            startSignIn();

            return;
        }

        for (AbstractFirebaseFragment fragment:fragments) {
            fragment.onUserUpdated(user);
        }
    }

    /**
     * TODO: implement this
     */
    private void signOut() {
        AuthUI.getInstance().signOut(this);
        onUserUpdated(null);
    }
}
