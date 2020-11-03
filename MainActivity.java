package com.example.csminiproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    Button btn_admin;
    Button btn_student;
    private GoogleApiClient googleApiClient;
    private static final int SIGN_IN_STUDENT = 1;
    private static final int SIGN_IN_ADMIN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        btn_student=findViewById(R.id.btn_student);
        btn_admin=findViewById(R.id.btn_admin);

        btn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Intent i=new Intent(MainActivity.this, Admin_login.class);
                startActivity(i);
            }
        });

        btn_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);

                startActivityForResult(intent,SIGN_IN_STUDENT);
            }


        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== SIGN_IN_STUDENT){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            System.out.println("1111111111111111111111111");
            System.out.println(result.isSuccess());
            if(result.isSuccess()){
                System.out.println("222222222222222222222222");
                startActivity(new Intent(MainActivity.this, StudentMainPage.class));
                finish();
            }else{
                System.out.println("3333333333333333333333333333333");
                Toast.makeText(this, "Login failed",Toast.LENGTH_SHORT).show();
                System.out.println(result.getStatus());

            }
        }

        if(requestCode== SIGN_IN_ADMIN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if(result.isSuccess()){
                startActivity(new Intent(MainActivity.this, Report_generate_year.class));
                finish();
            }else{
                Toast.makeText(this, "Login failed",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
