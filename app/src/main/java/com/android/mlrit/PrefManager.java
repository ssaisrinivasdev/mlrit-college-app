package com.android.mlrit;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.widget.EditText;

public class PrefManager {

    Context context;

    public PrefManager(Context context){
        this.context = context;
    }

    public void UpdateFirststatus(String Status){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Status",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LogStatus",Status);
        editor.apply();
    }
    public void UpdateDetails(String hallticket, String name){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Details",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("HallTicket", String.valueOf(hallticket));
        editor.putString("Name", String.valueOf(name));
        editor.apply();
    }
    public String returnDetails(String data){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Details",Context.MODE_PRIVATE);
        return sharedPreferences.getString(data,"");
    }

    public boolean FirstStatus(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Status",Context.MODE_PRIVATE);
        int n = sharedPreferences.getAll().size();
        return n != 0;
    }
    public String ReturnStatus(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Status",Context.MODE_PRIVATE);
        return sharedPreferences.getString("LogStatus","");
    }
    public boolean CheckDetails(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Details",Context.MODE_PRIVATE);
        int n = sharedPreferences.getAll().size();
        return n==0 ;
    }
    public boolean CheckUser(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails",Context.MODE_PRIVATE);
        int n = sharedPreferences.getAll().size();
        return n ==0;
    }
    public String returnUserDetails(String data){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails",Context.MODE_PRIVATE);
        return sharedPreferences.getString(data,"");
    }

    public void UpdateUserdetails(String Hallticket,String Password){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("HallTicket",Hallticket);
        editor.putString("Password",Password);
        editor.apply();
    }

    public void Logout(){
        SharedPreferences userDetails = context.getSharedPreferences("UserDetails",Context.MODE_PRIVATE);
        SharedPreferences.Editor user_editor = userDetails.edit();
        user_editor.remove("HallTicket");
        user_editor.remove("Password");
        user_editor.apply();

        SharedPreferences Details = context.getSharedPreferences("Details",Context.MODE_PRIVATE);
        SharedPreferences.Editor detailseditor = Details.edit();
        detailseditor.remove("HallTicket");
        detailseditor.remove("Name");
        detailseditor.apply();



    }



}
