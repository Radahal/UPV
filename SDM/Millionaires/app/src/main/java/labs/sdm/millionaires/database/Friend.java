package labs.sdm.millionaires.database;

/**
 * Created by Rafal on 2018-03-03.
 */

public class Friend {

    private int id;
    private String username;
    private int rewardValue;

    public Friend(String username, int rewardValue) {
        this.username = username;
        this.rewardValue = rewardValue;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = username;
    }

    public int getRewardValue() {
        return rewardValue;
    }

    public void setRewardValue(int rewardValue) {
        this.rewardValue = rewardValue;
    }
}
