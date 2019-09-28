package com.example.firbase_authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    EditText emailid,repassword,password,firstname,lastname;
    FirebaseAuth mauth;

    Button signup;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);



         progressDialog = new ProgressDialog(this);
         progressDialog.setMessage("Creating account");

         mauth=FirebaseAuth.getInstance();
         signup=(Button) findViewById(R.id.signup);

         progresbaeoff();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailid=(EditText)  findViewById(R.id.et_driver_email);
                password=(EditText) findViewById(R.id.et_driver_password);
                repassword=(EditText) findViewById(R.id.et_driver_re_password);
                firstname=findViewById(R.id.et_driver_firstname);
                lastname=findViewById(R.id.et_driver_lastname);

                final String email,passs,re_pass;
                email= emailid.getText().toString().trim();
                passs=password.getText().toString().trim();
                re_pass=repassword.getText().toString().trim();
                progressbaron();




                if(email.isEmpty()){

                    emailid.setError("Enter email id");
                    emailid.requestFocus();
                    progresbaeoff();

                }
                else if(passs.isEmpty()){
                    password.setError("Enter Password");
                    password.requestFocus();
                    progresbaeoff();

                }
                else if(email.isEmpty()&&passs.isEmpty()){
                    emailid.setError("Enter email id");
                    emailid.requestFocus();
                    password.setError("Enter Password");
                    password.requestFocus();
                    progresbaeoff();

                }
                else if(!(email.isEmpty()&&passs.isEmpty()&&re_pass.isEmpty())){


                    {


                        mauth.createUserWithEmailAndPassword(email, passs).addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    String fname,lname,password,email1;

                                    String userid=mauth.getCurrentUser().getUid();
                                    DatabaseReference current_user_db =  FirebaseDatabase.getInstance().getReference().child("users").child(userid).child("personal_info");

                                    fname=firstname.getText().toString().trim();
                                    lname=lastname.getText().toString().trim();
                                    email1=emailid.getText().toString().trim();
                                    password=repassword.getText().toString().trim();



                                    Map newpost=new HashMap();
                                    newpost.put("Email_id",email1);
                                    newpost.put("Passsword",password);
                                    newpost.put("First_Name",fname);
                                    newpost.put("Last_Name",lname);


                                    current_user_db.setValue(newpost);
                                    //stored data on database
                                    Toast.makeText(getApplicationContext(), "Sucessfull", Toast.LENGTH_SHORT).show();
                                    progresbaeoff();
                                    finish();
                                    Intent intent = new Intent(signup.this, login.class);
                                    startActivity(intent);
                                } else if (!(task.isSuccessful())) {
                                    progresbaeoff();
                                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();

                                }


                            }
                        });
                    }



                }


            }
        });//end of  signup onclick listner




    }//end of main function

    public void  progressbaron(){

        progressDialog.show();

    }
    public void progresbaeoff(){
        progressDialog.dismiss();


    }

}
