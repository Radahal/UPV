package labs.sdm.millionaires;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import labs.sdm.millionaires.connection.HighScore;
import labs.sdm.millionaires.connection.HighScoreList;
import labs.sdm.millionaires.connection.MyGetScoresAsyncTask;
import labs.sdm.millionaires.database.Friend;
import labs.sdm.millionaires.database.Friendship;
import labs.sdm.millionaires.database.MillionaireDatabase;
import labs.sdm.millionaires.database.Score;
import labs.sdm.millionaires.adapters.ScoreArrayAdapter;

public class ScoreActivity extends AppCompatActivity {

    private ArrayList<Score> scores;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private ScoreArrayAdapter adapter;

    private String username;
    private String activeUsername;
    private int score;
    private boolean isLocal;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        //preferences
        sharedPreferences = this.getSharedPreferences("user_information", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //value from preferences
        activeUsername = sharedPreferences.getString("login","");

        //other values
        isLocal = true;
        scores = new ArrayList<>();

        //references
        final Button btn_local = findViewById(R.id.score_btn_local);
        final Button btn_global = findViewById(R.id.score_btn_global);
        Toolbar toolbar = findViewById(R.id.score_toolbar);
        ListView score_lv = findViewById(R.id.score_lv);

        //toolbar
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        //listview
        if(isLocal) {
            setLocalScores();
            btn_local.setBackground(getDrawable(R.drawable.btn_score_switch_active));
        } else {
            setGlobalScores();
            btn_global.setBackground(getDrawable(R.drawable.btn_score_switch_active));
        }
        adapter = new ScoreArrayAdapter(ScoreActivity.this, R.layout.score_list_item, scores);
        score_lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        //listeners
        btn_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isLocal) {
                    isLocal=!isLocal;
                    setLocalScores();
                    adapter.setScores(scores);
                    adapter.notifyDataSetChanged();
                    btn_local.setBackground(getDrawable(R.drawable.btn_score_switch_active));
                    btn_global.setBackground(getDrawable(R.drawable.btn_score_switch));
                }
            }
        });

        btn_global.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLocal) {
                    isLocal=!isLocal;
                    setGlobalScores();
                    adapter.setScores(scores);
                    adapter.notifyDataSetChanged();
                    btn_local.setBackground(getDrawable(R.drawable.btn_score_switch));
                    btn_global.setBackground(getDrawable(R.drawable.btn_score_switch_active));

                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.score_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_score_remove:
                 MillionaireDatabase.getInstance(ScoreActivity.this).millionaireDao().deleteAllScores();
                 scores.clear();
                 adapter.notifyDataSetChanged();
                 return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    private void setLocalScores() {
        scores.clear();
        List<Score> db_scores = MillionaireDatabase.getInstance(ScoreActivity.this).millionaireDao().getScore(activeUsername);

        for(int i=0; i<db_scores.size(); i++) {
            scores.add(new Score(db_scores.get(i).getUsername(), db_scores.get(i).getScore()));
        }
    }

    private void setGlobalScores() {
        scores.clear();
        //List<Friendship> friendships = MillionaireDatabase.getInstance(ScoreActivity.this).millionaireDao().getFriendship(activeUsername);
        MyGetScoresAsyncTask myGetScoresAsyncTask = new MyGetScoresAsyncTask(this);
        HighScoreList highScoreList = new HighScoreList();
        try {

            highScoreList = myGetScoresAsyncTask.execute().get();

            for(int i=0; i<highScoreList.getScores().size(); i++) {

                String friend_username = highScoreList.getScores().get(i).getName();
                String friend_score = highScoreList.getScores().get(i).getScoring();
                if(!(friend_score==null || friend_username==null))
                    scores.add(new Score(friend_username, Integer.parseInt(friend_score)));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



       // return scores;
    }


    public String getUsername() {
        return  activeUsername;
    }
}
