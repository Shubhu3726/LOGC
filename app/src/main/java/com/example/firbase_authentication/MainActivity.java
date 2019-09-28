package com.example.firbase_authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.example.firbase_authentication.signup;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mauthe;
    DatabaseReference ref;
    TextView email1,firstname,lastname;
    TextView nemalid,nfname,nlname;
    Button btn_paintings,btn_artists;
    public  static final String DEFAULT="NA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        nemalid=findViewById(R.id.tv_emailid);
        nfname=findViewById(R.id.tv_fname);
        nlname=findViewById(R.id.tv_lname);
       // btn_paintings=findViewById(R.id.btn_painting);
        //btn_artists=findViewById(R.id.btn_artists);

        setSupportActionBar(toolbar);
        mauthe=FirebaseAuth.getInstance();

        //mauthe=FirebaseAuth.getInstance();
         final String userid=mauthe.getCurrentUser().getUid();
        ref= FirebaseDatabase.getInstance().getReference().child("users").child(userid).child("personal_info");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                email1=  findViewById(R.id.tv_emailid);
                firstname= findViewById(R.id.tv_fname);
                lastname=findViewById(R.id.tv_lname);
                String email,fname,lname;
                fname=dataSnapshot.child("First_Name").getValue().toString().trim();
                lname=dataSnapshot.child("Last_Name").getValue().toString().trim();
                email=dataSnapshot.child("Email_id").getValue().toString().trim();


                email1.setText(""+email);
                firstname.setText(""+fname);
                lastname.setText(""+lname);
                System.out.println(fname);
                System.out.println(lname);
                System.out.println(email);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



        /*nemalid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,user_profile.class);
                startActivity(intent);
            }
        });
        nfname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,user_profile.class);
                startActivity(intent);
            }
        });
        nlname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,user_profile.class);
                startActivity(intent);
            }
        });

         */









    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
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

        //userinfo();
        int id = item.getItemId();

        if (id == R.id.nav_signup) {
            Intent intent =new Intent(this,signup.class);
            startActivity(intent);



        }
        else if (id == R.id.nav_login) {


            Intent intent=new Intent(this,login.class);
            startActivity(intent);



        }
        else if (id == R.id.nav_signout) {

            final SharedPreferences shared=getSharedPreferences("shared_data",MODE_PRIVATE);
            shared.edit().putString("f_time","1").commit();
            shared.edit().putString("usernmae",DEFAULT).commit();
            Toast.makeText(this,"Youre logged out ",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,select_user.class);
            startActivity(intent);


        }
        else if (id == R.id.nav_profile) {
            Intent intent=new Intent(this,user_profile.class);
            startActivity(intent);



        }
        else if(id==R.id.nav_credits){
            Intent intent=new Intent(this  ,credits.class);
            startActivity(intent);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
