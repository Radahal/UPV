package labs.sdm.millionaires.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import labs.sdm.millionaires.R;
import labs.sdm.millionaires.database.Score;

/**
 * Created by Rafal on 2018-03-04.
 */

public class ScoreArrayAdapter extends ArrayAdapter {

    private Context context;
    private int resource;
    private ArrayList<Score> scores;

    public ScoreArrayAdapter(@NonNull Context context, int resource, ArrayList<Score> scores) {
        super(context,resource,scores);
        this.context = context;
        this.resource = resource;
        this.scores = scores;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource,null);
        }

        TextView tv_username = view.findViewById(R.id.score_tv_name);
        TextView tv_score = view.findViewById(R.id.score_tv_value);

        tv_username.setText(scores.get(position).getUsername());
        tv_score.setText(""+scores.get(position).getScore());

        return view;
    }

    public void setScores(ArrayList<Score> scores) {
        this.scores = scores;
    }
}
