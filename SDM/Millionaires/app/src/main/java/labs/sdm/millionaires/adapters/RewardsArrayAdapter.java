package labs.sdm.millionaires.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

import labs.sdm.millionaires.GameActivity;
import labs.sdm.millionaires.R;
import labs.sdm.millionaires.database.Reward;

/**
 * Created by Rafal on 2018-02-26.
 */

public class RewardsArrayAdapter extends ArrayAdapter {


    private Context context;
    private int resource;
    private List<Reward> rewards;
    private int questionCounter;
    private boolean isQuestionStarted;


    public RewardsArrayAdapter(@NonNull Context context, int resource, List<Reward> rewards) {
        super(context,resource,rewards);
        this.context = context;
        this.resource = resource;
        this.rewards = rewards;
        this.questionCounter = rewards.size()-1;
        this.isQuestionStarted=false;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource,null);
        }

        Button rewardBtn = view.findViewById(R.id.list_reward_item);

        rewardBtn.setText(rewards.get(questionCounter-position).getRewardValue());
        //rewardBtn.setTextAppearance(R.style.RewardWonText);
        if(rewards.get(questionCounter-position).getActive()) {
            rewardBtn.setBackground(context.getResources().getDrawable(R.drawable.btn_reward_active));
            rewardBtn.setTextColor(context.getResources().getColor(R.color.white));
            rewardBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get frame and close, set swip mode on, unable click on this button (boolean)
                    //show button - cancel
                    if(!isQuestionStarted) {
                        GameActivity gameActivity = (GameActivity) context;
                        gameActivity.closeDrawerLayout();
                        gameActivity.generateQuestion(questionCounter);
                        isQuestionStarted = !isQuestionStarted;
                    }
                }
            });

        } else {
            String rewardValue = rewards.get(questionCounter-position).getRewardValue();
           if(rewardValue =="1 000" || rewardValue =="32 000")
            {
                    rewardBtn.setBackground(context.getResources().getDrawable(R.drawable.btn_reward_guarantee));
                    rewardBtn.setTextColor(context.getResources().getColor(R.color.btnGuaranteeReward));
            } else {
               rewardBtn.setBackground(context.getResources().getDrawable(R.drawable.btn_reward_won));
               rewardBtn.setTextColor(context.getResources().getColor(R.color.white));
           }


        }
        return view;
    }

    public void setQuestionCounter(int questionCounter) {
        this.questionCounter = questionCounter;
    }
}
