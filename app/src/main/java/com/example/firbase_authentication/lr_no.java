package com.example.firbase_authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class lr_no extends AppCompatActivity {


    EditText enter_lrno;
    Button submit;
    TextView transportname,weight,boxes,vehicleno,sender,date,drivername,vehicletype,driverno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lr_no);
        enter_lrno=findViewById(R.id.et_lrno_enterlrno);
        submit=findViewById(R.id.btn_lrno_submit);


        String orderno = null;
        FirebaseAuth  mauth;
        final DatabaseReference ref;
        mauth=FirebaseAuth.getInstance();

       mauth = FirebaseAuth.getInstance();
        final String userid=mauth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("company").child(orderno);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                transportname=findViewById(R.id.tv_lrno_transport_name);
                weight=findViewById(R.id.tv_lrno_weight);
                boxes=findViewById(R.id.tv_lrno_boxes);
                vehicleno=findViewById(R.id.tv_lrno_vehicleno);
                sender=findViewById(R.id.tv_lrno_sender);
                date=findViewById(R.id.tv_lrno_date);
                drivername=findViewById(R.id.tv_lrno_drivername);
                driverno=findViewById(R.id.tv_lrno_driverno);

                String v_transportname,v_weight,v_boxes,v_vehiclenno,v_sender,v_date,v_drivername,v_driverno;

//                v_transportname=dataSnapshot.child("Transport_Name").getValue().toString().trim();
//                v_weight=dataSnapshot.child("Weight").getValue().toString().trim();
//                v_boxes=dataSnapshot.child("Boxes").getValue().toString().trim();
//                v_vehiclenno=dataSnapshot.child("Veicle_No").getValue().toString().trim();
//                v_sender=dataSnapshot.child("Sender").getValue().toString().trim();
//                v_date=dataSnapshot.child("Date").getValue().toString().trim();
//                v_drivername=dataSnapshot.child("Driver_Name").getValue().toString().trim();
//                v_driverno=dataSnapshot.child("Driver_No").getValue().toString().trim();
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        HashMap<String,String> map = (HashMap<String, String>) dataSnapshot.getValue();
                        transportname.setText(map.get("Transport_Name"));
                        weight.setText(map.get("Weight"));
                        boxes.setText(map.get("Boxes"));
                        vehicleno.setText(map.get("Vehicle_No"));
                        sender.setText(map.get("Sender"));
                        date.setText(map.get("Date"));
                        drivername.setText(map.get("Driver_name"));
                        driverno.setText(map.get("Driver_No"));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                });

//                transportname.setText(v_transportname);
//                weight.setText(v_weight);
//                boxes.setText(v_boxes);
//                vehicleno.setText(v_vehiclenno);
//                sender.setText(v_sender);
//                date.setText(v_date);
//                drivername.setText(v_drivername);
//                driverno.setText(v_driverno);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//end of data getting code











    }
}
