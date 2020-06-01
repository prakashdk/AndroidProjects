package com.vannalamobileapps.flashcardsenglishlessonfree.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class CreateDB extends SQLiteOpenHelper {

    CreateDB(Context context){
        super(context,"Manager",null,1);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE firstdb(id INTEGER PRIMARY KEY,name TEXT,amount TEXT,date TEXT)");
    }
    public void onUpgrade(SQLiteDatabase db,int a,int b){
        db.execSQL("DROP TABLE IF EXISTS firstdb");
    }
    public void add(String name,String amount,String date){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",name);
        values.put("amount",amount);
        values.put("date",date);
        db.insert("firstdb",null,values);
        db.close();
    }
    public String getColumn(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM firstdb",null);
        String data="";
        if (cursor.moveToFirst()){
            do {

                data+=cursor.getString(0)+"."+cursor.getString(1)+" | "+cursor.getString(2)+" | "+cursor.getString(3)+"\n";
            }while (cursor.moveToNext());
        }
        else {
            data="No Data Found";
        }
        cursor.close();
        db.close();
        return data;
    }
    public String searchByName(String name){
        String data="";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM firstdb",null);
        if(cursor.moveToFirst()){
            do {
                if(cursor.getString(0).equalsIgnoreCase(name)||cursor.getString(1).equalsIgnoreCase(name)||
                        cursor.getString(2).equalsIgnoreCase(name)||cursor.getString(3).equalsIgnoreCase(name)){
                    data+=cursor.getString(0)+"."+cursor.getString(1)+" | "+cursor.getString(2)+" | "+cursor.getString(3)+"\n";
                }
            }while (cursor.moveToNext());
        }
        if(data.isEmpty()){
            data+=name+" not found";
        }
        return data;
    }

    public void deleteAll(){

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS firstdb");
        db.execSQL("CREATE TABLE firstdb(id INTEGER PRIMARY KEY,name TEXT,amount TEXT,date TEXT)");

    }
    public void replace(String id,String oldString,String newString){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM firstdb",null);
        ContentValues values=new ContentValues();
        if(cursor.moveToFirst()){
            do {
                if(cursor.getString(1).equalsIgnoreCase(oldString)&&cursor.getString(0).equals(id)){
                    values.put("name",newString);
                    db.update("firstdb",values,"id="+cursor.getString(0),null);
                    break;
                }
                else if(cursor.getString(2).equalsIgnoreCase(oldString)&&cursor.getString(0).equals(id)){
                    values.put("amount",newString);
                    db.update("firstdb",values,"id="+cursor.getString(0),null);
                    break;
                }

            }while (cursor.moveToNext());
        }
    }

    public void remove(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("firstdb","id="+id,null);
        Cursor cursor=db.rawQuery("SELECT * FROM firstdb",null);
        ContentValues values=new ContentValues();
        if(cursor.moveToFirst()){
            do{
                if(Integer.parseInt(id)<=Integer.parseInt(cursor.getString(0))){
                    values.put("id",Integer.parseInt(cursor.getString(0))-1);
                    db.update("firstdb",values,"id="+cursor.getString(0),null);
                }
            }while (cursor.moveToNext());
        }
    }

}
