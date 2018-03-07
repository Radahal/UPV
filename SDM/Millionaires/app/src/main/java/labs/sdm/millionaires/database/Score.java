package labs.sdm.millionaires.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Rafal on 2018-03-04.
 */

@Entity(tableName = "score_table", indices = {@Index("username")})
public class Score {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="username")
    private String username;
    @ColumnInfo(name = "value")
    private int score;

    public Score(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
