package com.example.firbase_authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class shipping extends AppCompatActivity {

    ProgressDialog progressDialog;
    static HashMap<String,String> mapToSend;
    static String idSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching list");
        progressDialog.show();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("approved");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String,HashMap<String,String>> map = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
                List<String> scoreList = new ArrayList<String>(map.keySet());
                final List<String> idList = new ArrayList<>(map.keySet());
                final List<HashMap<String,String>> mapArray = new ArrayList<>(map.values());
                ListView listView = findViewById(R.id.listView);
                int j;
                ArrayList<String> arrayList = new ArrayList<>();

                for(j = 1;j<=idList.size();j++){
                    arrayList.add("Order no: " + j);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(shipping.this,
                        R.layout.listviewcustom, R.id.list_content, arrayList);
                progressDialog.dismiss();



                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Toast.makeText(shipping.this,idList.get(position),Toast.LENGTH_SHORT).show();
                        mapToSend = mapArray.get(position);
                        idSelected = idList.get(position);
                        Intent intent = new Intent(shipping.this,showOrderInfo.class);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                progressDialog.dismiss();
            }
        });


    }
}
