package com.example.firbase_authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

FirebaseAuth mauth;
EditText login,password;
Button loginbutton;
 Button tv_signup;
public  static final String DEFAULT="NA";
ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating");
        mauth= FirebaseAuth.getInstance();
        login= findViewById(R.id.et_login);
        password=findViewById(R.id.password);
        loginbutton=findViewById(R.id.signup);
        tv_signup=findViewById(R.id.signup);
        progressbaroff();
        final SharedPreferences shared=getSharedPreferences("shared_data",MODE_PRIVATE);
        //shared.edit().putString("f_time","1").commit();
        String shared_name=shared.getString("usernmae",DEFAULT);
        String f_time=shared.getString("f_time",DEFAULT);
//
//        if(!(shared_name.equals(DEFAULT))&&(!(f_time.equals(DEFAULT)))){
//            shared.edit().putString("f_time","0").commit();
//            Toast.makeText(getApplicationContext(),"Login Sucessfully",Toast.LENGTH_SHORT).show();
//            Intent intent=new Intent(login.this,MainActivity.class);
//            startActivity(intent);
//        }else{
//
//
//        }
        //all code for shared prefrences and user login n logout activity



        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email,pass;
                email=login.getText().toString().trim();
                pass=password.getText().toString().trim();

                if(email.isEmpty()){
                    login.setError("Enter Email id");
                    login.requestFocus();

                }
                else if(pass.isEmpty()){
                    password.setError("Enter Password");
                    password.requestFocus();

                }else if(email.isEmpty()&&pass.isEmpty()){
                    login.setError("Enter Email id");
                    login.requestFocus();
                    password.setError("Enter Password");
                    password.requestFocus();

                }
                else if(!(email.isEmpty()&&pass.isEmpty())){
                    progreebaron();

                    mauth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                shared.edit().putString("f_time","0").commit();
                                shared.edit().putString("usernmae",email).commit();//code for shared prepfrences

                                Toast.makeText(getApplicationContext(),"Your sucessfully logged in" ,Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(login.this,customerpage.class);
                                intent.putExtra("username",email);




                                startActivity(intent);
                                progressbaroff();

                            }else
                            {
                                progressbaroff();
                                Toast.makeText(getApplicationContext(),"try again" ,Toast.LENGTH_SHORT).show();
                                shared.edit().clear().commit();
                            }

                        }
                    });




                }



            }
        });//end of login onclick listner












    }//end of on create

    public void progreebaron(){
progressDialog.show();
    }

    public void progressbaroff(){
progressDialog.dismiss();
    }


    public void signup(View view){
        Intent intent = new Intent(com.example.firbase_authentication.login.this, com.example.firbase_authentication.signup.class);
        startActivity(intent);
    }


}//end of class
