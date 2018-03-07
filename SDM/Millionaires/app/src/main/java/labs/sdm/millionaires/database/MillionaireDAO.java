package labs.sdm.millionaires.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

/**
 * Created by Rafal on 2018-03-04.
 */

@Dao
public interface MillionaireDAO {

    @Query("SELECT * FROM score_table")
    List<Score> getAllScore();

    @Query("SELECT * FROM score_table WHERE username = :username")
    List<Score> getScore(String username);

    @Query("SELECT MAX(value) FROM score_table WHERE username = :username")
    int getHighScore(String username);

    @Insert
    void addScore(Score score);

    @Delete
    void deleteScore(Score score);

    @Query("DELETE FROM score_table")
    void deleteAllScores();


    @Query("SELECT * FROM friends_table")
    List<Friendship> getAllFriendship();

    @Query("SELECT * FROM friends_table WHERE username1 = :username")
    List<Friendship> getFriendship(String username);

    @Insert
    void addFriendship(Friendship friendship);

    @Query("DELETE FROM friends_table WHERE username1 = :username AND username2 = :friend")
    void deleteFriendship(String username, String friend);

    @Query("DELETE FROM friends_table")
    void deleteAllFriendship();

}
