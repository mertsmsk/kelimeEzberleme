package com.example.merts.kelimeezberleme;

import android.content.Context;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class database extends SQLiteOpenHelper {
    private static final String dbName="veritabani.db";
    private static final int dbVersion=1;
    Context context;
    private static final String DB_PATH = "data/data/com.example.merts.kelimeezberleme/databases";
    SQLiteDatabase sqLiteDatabase;


    public database(Context mcontext) {
        super(mcontext, dbName, null, dbVersion);
        mcontext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}


    //veritabanının olup olmadığını kontrol edicez.varsa pass geçicek yoksa kopyalayıp oluşturacak.
    public void createDB() throws IOException{
        boolean dbExist = checkDB();
        if (dbExist){
            //veritabanı varsa
        }
        else {
            //veritabanı yoksa
            getReadableDatabase();
            copyDB();
        }
    }

    //veritabanı kontrol metodunu yazalım.
    public boolean checkDB(){
        SQLiteDatabase checkDb = null;
        try {
            String myPath = DB_PATH + dbName;
            checkDb = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
        }
        catch (SQLiteException e){}

        if(checkDb != null){
            checkDb.close();
        }
        return checkDb != null ? true : false;
    }

    public void copyDB() throws IOException{
        try {
            InputStream myInput = context.getAssets().open(dbName);
            String outFileName = DB_PATH + dbName;
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer))>0){
                myOutput.write(buffer,0,length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDB() throws IOException{
        String myPath = DB_PATH + dbName;
        sqLiteDatabase = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close(){
        if (sqLiteDatabase != null)
            sqLiteDatabase.close();
        super.close();
    }

    public SQLiteDatabase getDB(){
        return sqLiteDatabase;
    }
}

