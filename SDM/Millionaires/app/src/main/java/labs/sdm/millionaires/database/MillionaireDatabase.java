package labs.sdm.millionaires.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import java.util.List;

/**
 * Created by Rafal on 2018-03-04.
 */

@Database(entities = {Score.class, Friendship.class}, version = 1)
public abstract class MillionaireDatabase extends RoomDatabase {

    private static MillionaireDatabase millionaireDatabase;

    public synchronized static MillionaireDatabase getInstance(Context context) {

        if(millionaireDatabase==null) {
            millionaireDatabase = Room
                    .databaseBuilder(context, MillionaireDatabase.class, "millionaire_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return millionaireDatabase;
    }

    public abstract MillionaireDAO millionaireDao();

}
