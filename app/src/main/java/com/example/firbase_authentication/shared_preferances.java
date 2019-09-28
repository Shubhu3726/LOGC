package com.example.firbase_authentication;

import android.content.Context;
import android.content.SharedPreferences;

public class shared_preferances {

    Context context;
    SharedPreferences sharedpreferances;
     private String name;


    shared_preferances( Context context  ){
        this.context=context;
        sharedpreferances=context.getSharedPreferences("shared",context.MODE_PRIVATE);
    }//end of constructor

    public String getName(){
        name = sharedpreferances.getString("username",name);
        return name;

    }//end of get function

    public void setName(String name){
        this.name=name;
        sharedpreferances.edit().putString("username",name).commit();

    }//end of setname

    public void removeshared(){

        sharedpreferances.edit().clear().commit();
    }//end of shared pref


}
