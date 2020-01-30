package com.example.gitsearchappp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqliteHelper extends SQLiteOpenHelper {
    private static final String dbName = "favoriteDirectory.db";
    private static final int dbVersion =1;
    private final String tablename ="Favourite";
     public SqliteHelper(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
        //SQLiteDatabase db =this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
                 final String createTable = "Create Table "+tablename+"(id INTEGER,directoryname text,language text,description text)";
                 sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists "+tablename);
         onCreate(sqLiteDatabase);
    }

    public void addFavouriteDirectory(int id,String directoryName, String language,String description)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put("id",id);
        cv.put("directoryname",directoryName);
        cv.put("language",language);
        cv.put("description",description);
        db.insert(tablename,null,cv);
    }

    public Cursor showFavourite()
    {
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from "+tablename,null);
        return  res;
    }
    public Void deleteFavourite(int id)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        db.execSQL("Delete From "+tablename+" where id="+id);
        return null;
    }
}
