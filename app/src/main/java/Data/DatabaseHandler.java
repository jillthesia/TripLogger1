package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import Model.MyTrips;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String CREATE_TRIPS_TABLE="CREATE TABLE " +Constants.TABLE_TRIPS+
            "(" +Constants.KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
            Constants.TITLE +" TEXT," +
            Constants.DATE +" TEXT,"+
            Constants.TRIP_TYPE +" TEXT,"+
            Constants.DESTINATION +" TEXT,"+
            Constants.DURATION +" TEXT,"+
            Constants.COMMENT +" TEXT,"+
            Constants.RECORD_DATE +" LONG);";

    private static final String CREATE_SETTINGS_TABLE = "CREATE TABLE " +Constants.TABLE_SETTINGS+
            "(" +Constants.KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
            Constants.NAME +" TEXT," +
            Constants.EMAIL+" TEXT,"+
            Constants.GENDER +" TEXT,"+
            Constants.COMMENT+" TEXT,"+
            Constants.RECORD_DATE +" LONG);";

    public DatabaseHandler(Context context) {
        super(context,Constants.DATABASE_NAME,null,Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TRIPS_TABLE);
        db.execSQL(CREATE_SETTINGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_TRIPS);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_SETTINGS);

        Log.v("ONUPGRADE", "DROPING THE TABLE AND CREATING A NEW ONE!");

        onCreate(db);
    }


    public void addTrips(MyTrips trips){
        SQLiteDatabase db =this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(Constants.KEY_ID,wish.getItemId());
        values.put(Constants.TITLE,trips.getTitle());
        values.put(Constants.DATE,trips.getDate());
        values.put(Constants.DESTINATION, trips.getDestination());
        values.put(Constants.DURATION,trips.getDuration());
        values.put(Constants.COMMENT,trips.getComment());
        values.put(Constants.RECORD_DATE,java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_TRIPS,null,values);

        Log.v("Trip Successfully added","Yes!!!");

        db.close();
    }



    private ArrayList<MyTrips> tripsList=new ArrayList<>();

    public ArrayList<MyTrips> getTrips(){

        tripsList.clear();

        String selectQuery= "SELECT * FROM " + Constants.TABLE_TRIPS;

        SQLiteDatabase db= this.getReadableDatabase();

        Cursor cursor= db.query(Constants.TABLE_TRIPS,new String[]{
                        Constants.KEY_ID,Constants.TITLE,
                        Constants.DATE,Constants.DESTINATION,
                        Constants.DURATION,Constants.COMMENT,Constants.RECORD_DATE},
                null,null,null,null,Constants.RECORD_DATE+" DESC");


        if(cursor.moveToFirst()){
            do{
                MyTrips trips=new MyTrips();

                trips.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));
                trips.setTitle(cursor.getString(cursor.getColumnIndex(Constants.TITLE)));
                trips.setDate(cursor.getString(cursor.getColumnIndex(Constants.DATE)));
                trips.setDestination(cursor.getString(cursor.getColumnIndex(Constants.DESTINATION)));
                trips.setDuration(cursor.getString(cursor.getColumnIndex(Constants.DURATION)));
                trips.setComment(cursor.getString(cursor.getColumnIndex(Constants.COMMENT)));
                trips.setDestination(cursor.getString(cursor.getColumnIndex(Constants.DESTINATION)));



                java.text.DateFormat dateFormat=java.text.DateFormat.getDateInstance();
                String dateData= dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.RECORD_DATE))).getTime());
                trips.setRecordDate(dateData);

                tripsList.add(trips);


            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();



        return tripsList;
    }



    public void deleteTrip(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Constants.TABLE_TRIPS,Constants.KEY_ID +" =?",
                new String[]{String.valueOf(id)});

        db.close();

    }


}
