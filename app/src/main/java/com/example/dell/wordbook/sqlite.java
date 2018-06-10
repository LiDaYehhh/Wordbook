package com.example.dell.wordbook;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by DELL on 2018/4/15.
 */

public class sqlite {
    public static SQLiteDatabase writedb=null;
    public static SQLiteDatabase readdb=null;
    public static wordsdbhelp wordsdbhelp;
    public static boolean insert(String word,String meaning,String sample)
    {
        String ssql="select * from wordstable where word=\""+word+"\"";
        Cursor cursor=sqlite.readdb.rawQuery(ssql,null);
        if(cursor.getCount()==0) {
            String sql = "insert into wordstable(word,meaning,sample) values(?,?,?)";
            sqlite.writedb.execSQL(sql, new String[]{word, meaning, sample});
            return true;
        }
        else
        {
            return false;
        }

    }

}
