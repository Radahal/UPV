package labs.sdm.millionaires.database;

import android.widget.Button;

/**
 * Created by Rafal on 2018-02-26.
 */

public class Reward {

    private String rewardValue;
    private Boolean isActive;

    public Reward(String rewardValue, Boolean isActive) {
        setActive(isActive);
        setRewardValue(rewardValue);
    }

    public String getRewardValue() {
        return rewardValue;
    }

    public void setRewardValue(String rewardValue) {
        this.rewardValue = rewardValue;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }


}
