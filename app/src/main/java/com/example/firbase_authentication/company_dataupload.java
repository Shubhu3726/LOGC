package com.example.firbase_authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class company_dataupload extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_dataupload);
        final EditText transportname = findViewById(R.id.transport_name);
        final EditText weight = findViewById(R.id.weight);
        final EditText packing_place = findViewById(R.id.packing_place);
        final EditText reciever = findViewById(R.id.reciver);
        final EditText drop_address = findViewById(R.id.drop_add);


        final DatabaseReference referenceOrder = FirebaseDatabase.getInstance().getReference().child("orders").child(company_landing.dateSelected);

        Button button = findViewById(R.id.approved);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Approving order...!");
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("approved");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String,String> map = new HashMap<>();
                map.put("trans",transportname.getText().toString());
                map.put("weight",weight.getText().toString());
                map.put("pac_place",packing_place.getText().toString());
                map.put("reci",reciever.getText().toString());
                map.put("drop_add",drop_address.getText().toString());

                progressDialog.show();
                reference.child(company_landing.idSelect).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(company_dataupload.this,"Approved",Toast.LENGTH_SHORT).show();
                            referenceOrder.child(company_landing.idSelect).setValue("1");
                            progressDialog.dismiss();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(company_dataupload.this,"Failed",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });


    }
}
