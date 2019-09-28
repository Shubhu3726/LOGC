package com.example.firbase_authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class select_user extends AppCompatActivity {

    Button btn_driver,btn_customer,btn_company;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
        btn_customer=findViewById(R.id.btn_customer);
        btn_driver=findViewById(R.id.btn_driver);
        btn_company=findViewById(R.id.btn_company);


        btn_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),login.class);
                startActivity(intent);
            }
        });

        btn_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(getApplicationContext(),shipping.class);
                startActivity(intent);
            }
        });

        btn_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),company_landing.class);
                startActivity(intent);
            }
        });





    }
}
