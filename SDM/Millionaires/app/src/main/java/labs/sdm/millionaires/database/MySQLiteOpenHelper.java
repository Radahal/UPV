package labs.sdm.millionaires.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rafal on 2018-03-04.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    

    public MySQLiteOpenHelper(Context context) {
        super(context,"millionaire_db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //
        try {
            //create DB - table with results
            db.execSQL("CREATE TABLE score (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, username TEXT NOT NULL, score TEXT NOT NULL);");
            db.execSQL("CREATE TABLE friendship (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, username1 TEXT NOT NULL, username2 TEXT NOT NULL);");

            db.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //
    }


}
