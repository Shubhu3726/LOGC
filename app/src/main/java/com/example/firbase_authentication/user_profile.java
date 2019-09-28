package com.example.firbase_authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class user_profile extends AppCompatActivity {
    EditText fname,lname,counry,lang,mobno;
    TextView email;
    Button update,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        FirebaseAuth  mauth;
        final DatabaseReference ref;
        mauth=FirebaseAuth.getInstance();
        update= findViewById(R.id.btn_update);
        back=findViewById(R.id.btn_back);

        final String userid=mauth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("users").child(userid).child("personal_info");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //code for getting data from firebase it just put data in their fields
                //initialized objects
                email= findViewById(R.id.tv_email);
                fname=findViewById(R.id.et_fname);
                lname=findViewById(R.id.et_lname);
                counry=findViewById(R.id.et_country);
                lang=findViewById(R.id.et_lang);
                mobno=findViewById(R.id.et_mobileno);


                String v_fname,v_lname,v_email,v_country,v_lang,v_mobno;
                //declared varibales for storing data whicg is get from firebase database

                v_email=dataSnapshot.child("Email_id").getValue().toString().trim();
                v_fname=dataSnapshot.child("First_Name").getValue().toString().trim();
                v_lname=dataSnapshot.child("Last_Name").getValue().toString().trim();
               v_country=dataSnapshot.child("Country").getValue().toString().trim();
               v_lang=dataSnapshot.child("Language").getValue().toString().trim();
                v_mobno=dataSnapshot.child("Mob_no").getValue().toString().trim();
                //set data in their appropriate fields
                email.setText(v_email);
                fname.setText(v_fname);
                lname.setText(v_lname);
                counry.setText(v_country);
                lang.setText(v_lang);
               mobno.setText(v_mobno);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this code take input from fields n send to firebase database

                DatabaseReference current_user;
                FirebaseAuth mauth;
                mauth=FirebaseAuth.getInstance();
                //getting firebase instance
                String userid=mauth.getCurrentUser().getUid();
                //getting fb userid
                current_user=FirebaseDatabase.getInstance().getReference().child("users").child(userid).child("personal_info");
                //ref from firebae db node  path

                email= findViewById(R.id.tv_email);
                fname=findViewById(R.id.et_fname);
                lname=findViewById(R.id.et_lname);
                counry=findViewById(R.id.et_country);
                lang=findViewById(R.id.et_lang);
                mobno=findViewById(R.id.et_mobileno);
                //declaring objects

                String v_email,v_fname,v_lname,v_country,v_lang,v_mobno;

                v_email=email.getText().toString().trim();
                v_fname=fname.getText().toString().trim();
                v_lname=lname.getText().toString().trim();
                v_country=counry.getText().toString().trim();
                v_lang=lang.getText().toString().trim();
                v_mobno=mobno.getText().toString().trim();

                Map newpost=new HashMap();

                newpost.put("Email_id",v_email);

                newpost.put("First_Name",v_fname);
                newpost.put("Last_Name",v_lname);
                newpost.put("Mob_no",v_mobno);
                newpost.put("Country",v_country);
                newpost.put("Language",v_lang);

                current_user.setValue(newpost);
                //funcction for setting n storing new values
                Toast.makeText(getApplicationContext(),"Data updated",Toast.LENGTH_SHORT).show();


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });






    }
}
