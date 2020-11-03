package com.example.csminiproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminMainPage extends AppCompatActivity {
CardView viewform ;
    EditText e1;
    Button b1;
    ImageView loagout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);
        loagout =findViewById(R.id.logout);
        final FirebaseAuth fAuth = FirebaseAuth.getInstance();
        loagout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fAuth.signOut();
                Toast.makeText(AdminMainPage.this,"Logged Out Successfully",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(AdminMainPage.this, MainActivity.class));
                String year = e1.getText().toString();
                Intent i = new Intent(AdminMainPage.this,Report_generate.class);
                i.putExtra("year",year);
                finish();
            }
        });

    }
}
