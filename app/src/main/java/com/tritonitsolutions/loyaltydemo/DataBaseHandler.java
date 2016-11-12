package com.tritonitsolutions.loyaltydemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tritonitsolutions.datamodel.CheckOutData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TritonDev on 15/10/2015.
 */
public class DataBaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "checkOutManager";
    private static final String TABLE_CHECKOUT = "checkout";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_NAME = "name";
    private static final String KEY_SIZE = "size";
    private static final String KEY_PRICE = "price";
    private static final String KEY_COUNT = "count";
    private static final String KEY_TOTAL = "total";
    private static  final String KEY_COUPCODE="coupcode";
    private static final String KEY_ID="id";
    String CREATE_CHECKOUT_TABLE = "CREATE TABLE " + TABLE_CHECKOUT + "("
            + KEY_IMAGE + " TEXT," + KEY_NAME + " TEXT,"
            + KEY_SIZE + " TEXT," + KEY_PRICE + " TEXT," + KEY_COUNT + " TEXT," + KEY_TOTAL + " TEXT," + KEY_COUPCODE +" TEXT,"+ KEY_ID + " TEXT" + ")";



    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     db.execSQL(CREATE_CHECKOUT_TABLE);

       System.out.println("DABLE---->" + CREATE_CHECKOUT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKOUT);
//        if (newVersion > oldVersion) {
//            db.execSQL("ALTER TABLE foo ADD COLUMN new_column INTEGER DEFAULT 0");
//        }
        System.out.println("data abse adjusted");
        onCreate(db);

    }
    public void addData(CheckOutData checkOutData){

        SQLiteDatabase db=this.getWritableDatabase();
      //  db.execSQL("ALTER TABLE "+TABLE_CHECKOUT+" ADD COLUMN "+KEY_COUPCODE +" TEXT");
        System.out.println("updated sucessfully");
    //  String st= db.execSQL("DESC " + TABLE_CHECKOUT);
        ContentValues values=new ContentValues();
     //   PRAGMA table_info(TABLENAME);
        values.put(KEY_IMAGE,checkOutData.getPro_img());
        values.put(KEY_NAME,checkOutData.getPro_name());
        values.put(KEY_SIZE,checkOutData.getPro_size());
        values.put(KEY_PRICE,checkOutData.getPro_price());
        values.put(KEY_COUNT, checkOutData.getPro_count());
        values.put(KEY_TOTAL,checkOutData.getPro_total());
        values.put(KEY_COUPCODE,checkOutData.getPro_coup());
        values.put(KEY_ID,checkOutData.getPro_id());
System.out.println(" copu valu from cart--------------------------"+checkOutData.getPro_coup());
        int i =db.update(TABLE_CHECKOUT, values, KEY_ID + " = ?",
                new String[]{String.valueOf(checkOutData.getPro_id())});
        if(i==0) {

            db.insert(TABLE_CHECKOUT, null, values);
        }
        System.out.println("DATA------>" + values);
        db.close();
    }
    public List<CheckOutData> getAllData(){
        List<CheckOutData> data_list=new ArrayList<CheckOutData>();
        String selectQuery = "SELECT  * FROM " + TABLE_CHECKOUT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                CheckOutData outData=new CheckOutData();
                outData.setPro_img(cursor.getString(0));
                outData.setPro_name(cursor.getString(1));
                outData.setPro_size(cursor.getString(2));
                outData.setPro_price(cursor.getString(3));
                outData.setPro_count(cursor.getString(4));
                outData.setPro_total(cursor.getString(5));
                outData.setPro_coup(cursor.getString(6));
                outData.setPro_id(cursor.getString(7));
                data_list.add(outData);
            }while (cursor.moveToNext());
        }
        return data_list;
    }
    public void delete(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM "+TABLE_CHECKOUT);

    }

}
