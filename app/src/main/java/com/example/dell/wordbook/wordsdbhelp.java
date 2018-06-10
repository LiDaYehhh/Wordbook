package com.example.dell.wordbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DELL on 2018/4/15.
 */

public class wordsdbhelp extends SQLiteOpenHelper {
    public wordsdbhelp(Context context)
    {
        super(context,"mydb",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
