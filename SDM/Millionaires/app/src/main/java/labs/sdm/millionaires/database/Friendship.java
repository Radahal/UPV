package labs.sdm.millionaires.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Rafal on 2018-03-04.
 */

@Entity(tableName = "friends_table", indices = {@Index("username1")})
public class Friendship {

    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name="username1")
    String username1;
    @ColumnInfo(name="username2")
    String username2;

    public Friendship(String username1, String username2) {
        this.username1 = username1;
        this.username2 = username2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }
}
