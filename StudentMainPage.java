package com.example.csminiproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class StudentMainPage extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
Button btn_report;
    Button btn_submitted;
    Button btn_fill_form;
    ImageView logout;
    TextView name;
    TextView id;
    String sId;
    String sName;
    //TextView ans_name;

    //Google Sign In Variables
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main_page);

        btn_report= findViewById(R.id.btn_report);
        btn_submitted=findViewById(R.id.btn_submitted);
        btn_fill_form=findViewById(R.id.btn_fill_form);
        logout = findViewById(R.id.logout);
        name = findViewById(R.id.txt_name);
        id = findViewById(R.id.txt_id);

btn_report.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(StudentMainPage.this,Student_report.class);
        startActivity(i);
    }
});
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API , gso).build();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if(status.isSuccess()) {
                            gotoMainActivity();
                            finish();
                        }
                        else{
                            Toast.makeText(StudentMainPage.this,"Log Out Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void gotoMainActivity() {
        Toast.makeText(StudentMainPage.this,"Logged Out Successfully",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(StudentMainPage.this, MainActivity.class));
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);

        if(opr.isDone()){
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult result) {
                    handleSignInResult(result);
                }
            });
        }
    }


    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();


            String email1 = account.getEmail();
            String[] e_split = email1.split("@");
            String domain = e_split[1];

            //Storing ID from email address into Cred Class
            sId = e_split[0];
            Creds.sId = sId;

            //if(domain.equalsIgnoreCase("charusat.edu.in")){

            //Displaying ID from email
            id.setText(account.getEmail());

            //Storing Name into Cred Class
            sName = account.getDisplayName();

            String[] n_split = sName.split(" ");

            char firstch = n_split[0].charAt(0);
            char secondch = n_split[0].charAt(1);
            if((firstch >= '0' && firstch <= '9') || (secondch>='0' && secondch <='9')){
                String combinedName = n_split[1];
                for(int i = 2; i<n_split.length; i++){
                    combinedName = combinedName + " "+ n_split[i];
                }

                //Storing and displaying the user name
                Creds.sName= combinedName;
                name.setText(combinedName);

            }
            else {
                //Storing the user name and displaying it
                name.setText(account.getDisplayName());
                Creds.sName = sName;
            }

            Toast.makeText(StudentMainPage.this,"You are Logged In", Toast.LENGTH_SHORT).show();

            //onClick Listeners will only work if valid charusat email is typed in
            btn_fill_form.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent form=new Intent(StudentMainPage.this, Achievement_Form.class);
                    startActivity(form);
                }
            });

            btn_submitted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent recycler = new Intent(StudentMainPage.this, demo.class);
                    startActivity(recycler);
                }
            });



        }
        else {
            gotoMainActivity();
        }
    }

}

