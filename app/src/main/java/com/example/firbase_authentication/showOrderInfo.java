package com.example.firbase_authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;

public class showOrderInfo extends AppCompatActivity {

    TextView name;
    TextView mobileNo;
    TextView weight;
    TextView dropingPlace;
    IntentIntegrator qrCode;
    String[] cityNameArray = {"Please select your city","Aurangabad","Pune","Mumbai","Washim","Nanded","haidrabad","Delhi"};
    String citySelected;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order_info);

        qrCode = new IntentIntegrator(this);
        qrCode.setBeepEnabled(true);
        name = findViewById(R.id.name);
        mobileNo = findViewById(R.id.mobileno);
        weight = findViewById(R.id.weight);
        dropingPlace = findViewById(R.id.dropingPlace);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching info");
        progressDialog.show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(shipping.idSelected).child("personal_info");
        Toast.makeText(showOrderInfo.this,shipping.idSelected.toString(),Toast.LENGTH_SHORT).show();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                HashMap<String,String> map = (HashMap<String, String>) dataSnapshot.getValue();
                name.setText(map.get("First_Name") + " " + map.get("Last_Name"));
                mobileNo.setText(map.get("Mob_no"));
                weight.setText(shipping.mapToSend.get("weight"));
                dropingPlace.setText(shipping.mapToSend.get("drop_add"));
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(showOrderInfo.this,"Failed",Toast.LENGTH_SHORT).show();
            }
        });


        Button scanButton = findViewById(R.id.scanbutton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(citySelected != null) {

                    qrCode.initiateScan();
                    progressDialog.setMessage("Submitting status...");
                    progressDialog.show();
                }else{
                    Toast.makeText(showOrderInfo.this,"Please select your HUB",Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Spinner cityName = findViewById(R.id.spinner);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,cityNameArray);
        arrayAdapter.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
        cityName.setAdapter ( arrayAdapter );
        cityName.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener ( ) {
            @Override
            public void onItemSelected( AdapterView <?> parent , View view , int position , long id ) {

                citySelected = cityNameArray[position];


            }

            @Override
            public void onNothingSelected( AdapterView <?> parent ) {

            }
        } );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {


                    Toast.makeText(showOrderInfo.this,result.getContents().toString(),Toast.LENGTH_SHORT).show();
                    if(result.getContents().equals(shipping.idSelected)){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(shipping.idSelected).child("track");
                        reference.setValue(citySelected).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                               progressDialog.dismiss();
                               Toast.makeText(showOrderInfo.this,"Submitted!",Toast.LENGTH_SHORT).show();

                           }else{
                               progressDialog.dismiss();
                               Toast.makeText(showOrderInfo.this,"Please try again!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }

                } catch (Exception e) {
                    e.printStackTrace();

                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }    }
}
