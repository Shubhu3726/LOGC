package com.example.firbase_authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class company_landing extends AppCompatActivity {

    Button btn_submit;
    static String idSelect;
    EditText date;
    static String dateSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_landing);
        btn_submit=findViewById(R.id.btn_submit);


        date = findViewById(R.id.date);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(company_landing.this);
                progressDialog.setMessage("Fetching orders");
                progressDialog.show();

                if(!TextUtils.isEmpty(date.getText().toString())){

                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("orders");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(date.getText().toString())){
                                DatabaseReference reference1 = reference.child(date.getText().toString().trim());
                                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        System.out.print(dataSnapshot.getValue().toString());
                                        Log.i("Data",dataSnapshot.getValue().toString());
                                        Toast.makeText(company_landing.this,"succ",Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();

                                        HashMap<String,String> map = (HashMap<String, String>) dataSnapshot.getValue();
                                        List<String> scoreList = new ArrayList<>(map.values());
                                        final List<String> idList = new ArrayList<>(map.keySet());
                                        Log.i("KeyNames",idList.toString());
                                        Log.i("Result2",scoreList.toString());

                                        int j;
                                        for(j = 0;j<scoreList.size();j++){
                                            if(scoreList.get(j).equals("1")){
                                                idList.remove(j);
                                            }
                                        }

                                        List<String> islis2 = new ArrayList<>();
                                        int i;
                                        for(i=1;i<=idList.size();i++){
                                            islis2.add("Order No : " + i);
                                        }
                                      ListView listView = findViewById(R.id.listView);

                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(company_landing.this,
                                                R.layout.listviewcustom, R.id.list_content, islis2);

                                        listView.setAdapter(adapter);
                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                Toast.makeText(company_landing.this, idList.get(position),Toast.LENGTH_SHORT).show();
                                                idSelect = idList.get(position);
                                                Intent intent = new Intent(company_landing.this,company_dataupload.class);
                                                startActivity(intent);
                                                dateSelected = date.getText().toString();


                                            }
                                        });


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(company_landing.this,"Failed",Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();

                                    }
                                });

                            }else {
                                Toast.makeText(company_landing.this,"Failed",Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(company_landing.this,"Failed",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    });

                }



            }
        });


    }
}
