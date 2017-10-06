package com.herokuapp.veekay.recommend.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.herokuapp.veekay.recommend.R;
import com.herokuapp.veekay.recommend.adapters.HomeTabsPagerAdapter;
import com.herokuapp.veekay.recommend.models.User;
import com.herokuapp.veekay.recommend.utils.Constants;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private ViewPager mViewPager;
    private HomeTabsPagerAdapter homeTabsPagerAdapter;
    private TabLayout homeNavTabLayout;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView userEmailAddress;
    private TextView userFullName;
    private NavigationView nav_view;
    private View  headerView;
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MyTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nav_view = findViewById(R.id.nav_view);
        headerView = nav_view.getHeaderView(0);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        for(int i=0; i<30;i++){
            Log.d("text", "text"+i);
        }


        userEmailAddress = headerView.findViewById(R.id.userEmailAddress);
        userFullName = headerView.findViewById(R.id.userFullName);
        if(currentUser!=null){
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(Constants.USERS_DB_KEY);
            Query query = userRef.orderByChild("userId").equalTo(currentUser.getUid());
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    User dbUser = dataSnapshot.getValue(User.class);
//                String fullName = dbUser.getFullName();
                    userFullName.setText(dbUser.getFullName());
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            userEmailAddress.setText(currentUser.getEmail());
        }else{
            userFullName.setText("");
            userEmailAddress.setText("");
        }



        mDrawerLayout = findViewById(R.id.main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        homeNavTabLayout = findViewById(R.id.homeNavTabLayout);
        mViewPager = findViewById(R.id.mainActivityViewPager);
        homeTabsPagerAdapter = new HomeTabsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(homeTabsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(homeNavTabLayout));
        homeNavTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        createAuthStateListener();

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mAuthListener);
    }
    public void onStop(){
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);

        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.dot_menu, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeTabFragment/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.openProfile) {

        } else if (id == R.id.login) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.createAccount) {
            Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
            startActivity(intent);

        } else if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            recreate();
        }

        DrawerLayout drawer = findViewById(R.id.main_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void createAuthStateListener(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
//                    nav_view.addHeaderView(headerView);
//                    userEmailAddress.setText(user.getEmail());
//                    userFullName.setText(user.getEmail());
                }else{
                    nav_view.removeHeaderView(headerView);
                }
            }
        };
    }


}
