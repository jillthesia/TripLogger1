package com.example.jillthesia.trip2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Data.DatabaseHandler;
import Model.MyTrips;

public class log extends AppCompatActivity {

    private EditText title,date,tripType,destination, duration, comment;
    private Button save,cancel;

    private DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        title=(EditText)findViewById(R.id.editTitleText);
        date=(EditText)findViewById(R.id.editDateText);
        tripType=(EditText)findViewById(R.id.editTripTypeText);
        destination=(EditText)findViewById(R.id.editDestinationText);
        duration=(EditText)findViewById(R.id.editDurationText);
        comment=(EditText)findViewById(R.id.editCommentText);

        save=(Button)findViewById(R.id.saveButton);
        cancel=(Button)findViewById(R.id.cancelButton);

        dba = new DatabaseHandler(log.this);

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                addTrips();



            }
        });


        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                clearTrips();

            }
        });
    }

    private void clearTrips() {

        title.setText("");
        date.setText("");
        tripType.setText("");
        destination.setText("");
        duration.setText("");
        comment.setText("");


        Intent intent = new Intent(log.this,MainActivity.class);
        startActivity(intent);
    }

    private void addTrips() {

        MyTrips trips = new MyTrips();

        trips.setTitle(title.getText().toString().trim());
        trips.setDate(date.getText().toString().trim());
        trips.setTripType(tripType.getText().toString().trim());
        trips.setDestination(destination.getText().toString().trim());
        trips.setDuration(duration.getText().toString().trim());
        trips.setComment(comment.getText().toString().trim());

        dba.addTrips(trips);
        dba.close();


        title.setText("");
        date.setText("");
        tripType.setText("");
        destination.setText("");
        duration.setText("");
        comment.setText("");


        Intent intent = new Intent(log.this,MainActivity.class);
        startActivity(intent);
    }
}
