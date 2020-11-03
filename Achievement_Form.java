package com.example.csminiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.csminiproject.Creds.sId;


public class Achievement_Form extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener ,FormView{

    Spinner spinner_activity;
    static int i=1;
    EditText edt_id;
    EditText edt_year;
    EditText edt_name;
    EditText edt_sem;
    EditText edt_event_name;
    EditText edt_date_from;
    EditText edt_date_to;
    EditText edt_scholarship_charusat;
    EditText edt_scholarship_external;
    EditText edt_certificate;
    EditText edt_describe;
    Button btn_submit;
    String path = "AchievementForm";
    DatePickerDialog datePickerDialog;

    ProgressDialog progressDialog;


    FirebaseDatabase database ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement__form);
        database = FirebaseDatabase.getInstance();
        spinner_activity = findViewById(R.id.spinner_award);
        edt_year=findViewById(R.id.edt_year);
        edt_id=findViewById(R.id.edt_id);
        edt_name=findViewById(R.id.edt_name);
        edt_sem = findViewById(R.id.edt_sem);
        edt_event_name=findViewById(R.id.edt_event_name);
        edt_date_from = findViewById(R.id.edt_date_from);
        edt_date_to = findViewById(R.id.edt_date_to);
        edt_scholarship_charusat = findViewById(R.id.edt_scholarship_charusat);
        edt_scholarship_external = findViewById(R.id.edt_scholarship_external);
        edt_certificate=findViewById(R.id.edt_report);
        edt_describe=findViewById(R.id.edt_describe);
        btn_submit=findViewById(R.id.btn_submit);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        edt_id.setText(""+ sId.toUpperCase());
        edt_name.setText(""+Creds.sName);

        ArrayList<String> activity= new ArrayList<String>();
        activity.add("Choose");
        activity.add("Non Technical Event");
        activity.add("Technical Event");
        activity.add("Academic Certifications");
        activity.add("Internship");
        activity.add("Others");

        ArrayAdapter<String> adapter_activity = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, activity){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {

                    return false;
                }
                else
                {
                    return true;
                }

            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter_activity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_activity.setAdapter(adapter_activity);


        Calendar c = Calendar.getInstance();
        final int cYear = c.get(Calendar.YEAR);
        final int cMonth = c.get(Calendar.MONTH);
        final int cDay = c.get(Calendar.DAY_OF_MONTH);

        edt_date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(Achievement_Form.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edt_date_from.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },cYear,cMonth,cDay);
                datePickerDialog.show();
            }
        });


        edt_date_to.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                datePickerDialog= new DatePickerDialog(Achievement_Form.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edt_date_to.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },cYear,cMonth,cDay);

                datePickerDialog.show();

            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //ERROR MESSAGE HERE
                if(edt_year.getText().toString().isEmpty()){
                    edt_year.setError("Please fill in the ID");
                    edt_year.requestFocus();
                }
                else if(edt_id.getText().toString().isEmpty()){
                    edt_id.setError("Please fill in the ID");
                    edt_id.requestFocus();
                }
                else if(edt_name.getText().toString().isEmpty()){
                    edt_name.setError("Please fill in your Name");
                    edt_name.requestFocus();
                }
                else if(edt_sem.getText().toString().isEmpty()){
                    edt_sem.setError("Enter your Current Semester");
                    edt_sem.requestFocus();
                }
                else if(spinner_activity.getSelectedItem()== "Choose"){
                    Toast.makeText(Achievement_Form.this,
                            "Please Select the Event from Dropdown Menu",
                            Toast.LENGTH_SHORT).show();

                }
                else if(edt_event_name.getText().toString().isEmpty()){
                    edt_event_name.setError("Please fill in the Event Name");
                    edt_event_name.requestFocus();
                }
                else if(edt_date_from.getText().toString().matches("DD/MM/YY")){
                    edt_date_from.setError("Date Missing");
                    edt_date_from.requestFocus();
                }
                else if(edt_date_to.getText().toString().matches("DD/MM/YY")){
                    edt_date_to.setError("Date Missing");
                    edt_date_to.requestFocus();
                }
                else if(edt_scholarship_charusat.getText().toString().matches("")){
                    edt_scholarship_charusat.setError("Enter 0 if no amount allocated");
                    edt_scholarship_charusat.requestFocus();
                }
                else if(edt_scholarship_external.getText().toString().matches("")){
                    edt_scholarship_external.setError("Enter 0 if no amount allocated");
                    edt_scholarship_external.requestFocus();
                }
                else if(edt_describe.getText().toString().matches("")){
                    edt_describe.setError("Enter description");
                    edt_describe.requestFocus();
                }
                else {
                    String StdID = edt_id.getText().toString().trim();
                    String StdName = edt_name.getText().toString().trim();
                    String StdSem = edt_sem.getText().toString().trim();
                    String year = edt_year.getText().toString().trim();
                    String EventType = spinner_activity.getSelectedItem().toString().trim();
                    String EventName = edt_event_name.getText().toString().trim();
                    String FromDate = edt_date_from.getText().toString().trim();
                    String ToDate = edt_date_to.getText().toString().trim();
                    String ClgSch = edt_scholarship_charusat.getText().toString().trim();
                    String ExtSch = edt_scholarship_external.getText().toString().trim();
                    String Drive = edt_certificate.getText().toString().trim();
                    String Des = edt_describe.getText().toString().trim();
                    int Accept = 0;

                    Map<String, String> students = new HashMap<>();

                    students.put("StdID",StdID);
                    students.put("StdName",StdName);
                    students.put("StdSem",StdSem);
                    students.put("EventType",EventType);
                    students.put("EventName",EventName);
                    students.put("FromDate",FromDate);
                    students.put("ToDate",ToDate);
                    students.put("ClgScholarship",ClgSch);
                    students.put("ExtScholarship",ExtSch);
                    students.put("DriveLink",Drive);
                    students.put("year",year);
                    students.put("Description",Des);
                    DatabaseReference myRef = database.getReference("StudentForm").child(year).child(sId);
                   //myRef.child(sId).child(path).setValue(students);
                    myRef.push().setValue(students, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            Toast.makeText(Achievement_Form.this,"Inserted successfully",Toast.LENGTH_LONG).show();
                        }
                    });


                }
            }
        });
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }
    @Override
    public void hideProgress() {
        progressDialog.hide();
    }
@Override
    public void onRequestSuccess(String message) {
        Toast.makeText(Achievement_Form.this,
                message,
                Toast.LENGTH_SHORT).show();
        finish();
    }
@Override
    public void onRequestError(String message) {
        Toast.makeText(Achievement_Form.this,
                message,
                Toast.LENGTH_SHORT).show();
    }
}

