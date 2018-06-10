package com.example.jillthesia.trip2;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Data.DatabaseHandler;
import Model.MyTrips;

public class MainActivity extends AppCompatActivity {

    private Button log;
    private Button settings;

    private DatabaseHandler dba;
    private ArrayList<MyTrips> dbtrips= new ArrayList<>();

    private TripAdapter tripAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.list);
        refreshData();

        log=(Button)findViewById(R.id.logButton);
        settings = (Button)findViewById(R.id.settingsButton);

        log.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,log.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,settings.class);
                startActivity(intent);
            }
        });




    }

    private void refreshData() {

        dbtrips.clear();
        dba=new DatabaseHandler(getApplicationContext());

        ArrayList<MyTrips> tripsFromDB= dba.getTrips();

        for(int i=0;i<tripsFromDB.size();i++){

            String title= tripsFromDB.get(i).getTitle();
            String dateText= tripsFromDB.get(i).getRecordDate();
            String destination = tripsFromDB.get(i).getDestination();
            int mid=tripsFromDB.get(i).getId();

            MyTrips mytrips = new MyTrips();

            mytrips.setTitle(title);
            mytrips.setDestination(destination);
            mytrips.setRecordDate(dateText);
            mytrips.setId(mid);

            dbtrips.add(mytrips);
        }

        dba.close();



        tripAdapter=new TripAdapter(MainActivity.this,R.layout.row,dbtrips);
        listView.setAdapter(tripAdapter);
        tripAdapter.notifyDataSetChanged();


    }

    public class TripAdapter extends ArrayAdapter<MyTrips> {

        Activity activity;
        int layoutResource;
        MyTrips trips;
        ArrayList<MyTrips> mData= new ArrayList<>();


        public TripAdapter(Activity act, @LayoutRes int resource, ArrayList<MyTrips> data) {
            super(act, resource, data);
            activity=act;
            layoutResource=resource;
            mData=data;

            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Nullable
        @Override
        public MyTrips getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getPosition(@Nullable MyTrips item) {
            return super.getPosition(item);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @NonNull
        @Override

        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View row = convertView;
            ViewHolder holder =null;

            if((row ==null) || (row.getTag() ==null)) {
                LayoutInflater inflater= LayoutInflater.from(activity);

                row= inflater.inflate(layoutResource,null);
                holder= new ViewHolder();

                holder.mTitle=(TextView)row.findViewById(R.id.name);

                row.setTag(holder);

            }
            else
            {
                holder=(ViewHolder)row.getTag();
            }
            holder.myTrips=getItem(position);
            holder.mTitle.setText(holder.myTrips.getTitle());


            final ViewHolder finalHolder = holder;
            holder.mTitle.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    String dataText=finalHolder.myTrips.getRecordDate().toString();
                    String title=finalHolder.myTrips.getTitle().toString();

                    int mid=finalHolder.myTrips.getId();

                    Intent i = new Intent(MainActivity.this, detail.class);

                    i.putExtra("Date",dataText);
                    i.putExtra("Title", title);

                    i.putExtra("id",mid);


                    startActivity(i);
                }
            });


            return row;


        }


        class ViewHolder{
            MyTrips myTrips;
            TextView mTitle;
            int mId;


        }
    }
}
