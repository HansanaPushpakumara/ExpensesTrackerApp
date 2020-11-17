package com.example.expensesapp1;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by tutlane on 06-01-2018.
 */

public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "expenseDb";
    private static final String TABLE_Expenses = "expenses";
    private static final String KEY_ID = "id";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_TOTAL = "total";
    private static final String KEY_CREATED = "created_at";
    private static final String KEY_UPDATED = "updated_at";
    public DbHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Expenses + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CATEGORY + " TEXT,"
                + KEY_TOTAL + " TEXT,"
                + KEY_CREATED + " TEXT,"
                + KEY_UPDATED + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Expenses);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new Category
    void addCategory(String category, String total){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        String updated_at = formatTime();

        String created_at = updated_at;

        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_CATEGORY, category);
        cValues.put(KEY_TOTAL, total);
        cValues.put(KEY_CREATED, created_at);
        cValues.put(KEY_UPDATED, updated_at);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Expenses,null, cValues);
        db.close();
    }
    // Get All Expenses Details
    public ArrayList<HashMap<String, String>> GetExpenses(){
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<HashMap<String, String>> expenseList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_Expenses;
        Cursor cursor = db.rawQuery(query,null);
            while (cursor.moveToNext()){
                System.out.println(cursor.getString(cursor.getColumnIndex(KEY_ID)));

                HashMap<String,String> expense = new HashMap<>();
                expense.put("id",cursor.getString(cursor.getColumnIndex(KEY_ID)));
                expense.put("category",cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));
                expense.put("total",cursor.getString(cursor.getColumnIndex(KEY_TOTAL)));
                expense.put("updated_at",cursor.getString(cursor.getColumnIndex(KEY_UPDATED)));
                expense.put("created_at",cursor.getString(cursor.getColumnIndex(KEY_CREATED)));
                expenseList.add(expense);
            }

        return  expenseList;
    }

    //-------cal total--------//

    public float calTotal(){
        SQLiteDatabase db = this.getWritableDatabase();

        float totalcost = 0;

        String query = "SELECT * FROM "+ TABLE_Expenses;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            totalcost = totalcost+Float.parseFloat(cursor.getString(cursor.getColumnIndex(KEY_TOTAL)));
        }

        return  totalcost;
    }



    // Get Expenses Details based on id
    public ArrayList<HashMap<String, String>> GetExpenseById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> expenseList = new ArrayList<>();

        Cursor cursor =  db.rawQuery( "select * from expenses where id="+id+"", null );
        //return res;

        //String query = "SELECT category, total, updated_at FROM "+ TABLE_Expenses;
        //Cursor cursor = db.query(TABLE_Expenses, new String[]{ KEY_CATEGORY, KEY_TOTAL, KEY_UPDATED}, KEY_ID+ "=?",new String[]{String.valueOf(id)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> expense = new HashMap<>();
            ///expense.put("id",cursor.getString(cursor.getColumnIndex(KEY_ID)));
            expense.put("category",cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));
            expense.put("total",cursor.getString(cursor.getColumnIndex(KEY_TOTAL)));
            expense.put("updated_at",cursor.getString(cursor.getColumnIndex(KEY_CREATED)));
            expenseList.add(expense);
        }
        return  expenseList;
    }

    public void clearTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Expenses, null ,null);
        db.close();
    }
    // Delete Expenses Details
    public void DeleteExpense(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Expenses, KEY_ID+" = ?",new String[]{String.valueOf(id)});
        db.close();
    }
    // Update Expenses Details
    public int UpdateExpenseDetails(String category, String total, int id){

        String updated_at = formatTime();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_CATEGORY, category);
        cVals.put(KEY_TOTAL, total);
        cVals.put(KEY_UPDATED, updated_at);
        int count = db.update(TABLE_Expenses, cVals, KEY_ID+" = ?",new String[]{String.valueOf(id)});
        return  count;
    }

    public String formatTime(){
        Date created_time = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy MMM dd hh:mm:ss a");
        String created_at = format.format(created_time);
        return  created_at;
    }
}