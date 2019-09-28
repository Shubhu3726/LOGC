package com.example.firbase_authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class customerpage extends AppCompatActivity {

    Spinner spinner;
    String[] productName = {"product1","Product2","Product3","Product4","Product5"};
    EditText address;
    EditText mobileno;
    String productselected;
    EditText Date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerpage);


        Date = findViewById(R.id.Date);
        address = findViewById(R.id.address);
        mobileno = findViewById(R.id.mobileno);
        spinner = findViewById(R.id.productSpinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,productName);
        arrayAdapter.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
        spinner.setAdapter ( arrayAdapter );
        spinner.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener ( ) {
            @Override
            public void onItemSelected(AdapterView <?> parent , View view , int position , long id ) {

                Toast.makeText(customerpage.this,productName[position],Toast.LENGTH_SHORT).show();
                productselected = productName[position];

            }

            @Override
            public void onNothingSelected( AdapterView <?> parent ) {

            }
        } );


        Button place = findViewById(R.id.place);
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(address.getText().toString()) || TextUtils.isEmpty(mobileno.getText().toString()) || TextUtils.isEmpty(Date.getText().toString()) ) {
                  Toast.makeText(customerpage.this,"Please enter all details!",Toast.LENGTH_SHORT).show();


                }else{
                    final ProgressDialog progressDialog = new ProgressDialog(customerpage.this);
                    progressDialog.setMessage("Placing order...");
                    progressDialog.show();
                    final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getCurrentUser().getUid()).child("personal_info");
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("orders");

                    DatabaseReference refForMobile = current_user_db.child("mobileNo");
                    DatabaseReference referenceAddress = current_user_db.child("address");
                    DatabaseReference refproduct = current_user_db.child("product");
                    DatabaseReference refDate = current_user_db.child("Date");

                    refproduct.setValue(productselected);
                    String date = Date.getText().toString().replaceAll("_","");
                    refDate.setValue(date.replaceAll("_",""));
                    referenceAddress.setValue(address.getText().toString());
                    refForMobile.setValue(mobileno.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                DatabaseReference reference1 = reference.child(Date.getText().toString()).child(firebaseAuth.getCurrentUser().getUid());
                                reference1.setValue("0");
                                Toast.makeText(customerpage.this,"Placed",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(customerpage.this,"Failed",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                
                            }
                        }
                    });


                }
            }
        });


    }

    public void trackOrder(View view){


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getCurrentUser().getUid()).child("track");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AlertDialog.Builder builder = new AlertDialog.Builder(customerpage.this);
                builder.setTitle("Your order is in :");
                builder.setMessage(dataSnapshot.getValue().toString());
                builder.setCancelable(true);
                builder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




}
