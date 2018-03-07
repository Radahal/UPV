package labs.sdm.millionaires;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Button menu_btn_play;
    private Button menu_btn_friends;
    private Button menu_btn_scores;
    private Button menu_btn_credits;
    private Button menu_btn_logout;
    private boolean isGameActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        // preferences
        sharedPreferences = this.getSharedPreferences("user_information",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        isGameActive = sharedPreferences.getBoolean("isGameActive",false);

        //references
        menu_btn_play = findViewById(R.id.menu_btn_play);
        menu_btn_friends = findViewById(R.id.menu_btn_friends);
        menu_btn_scores = findViewById(R.id.menu_btn_scores);
        Button menu_btn_settings = findViewById(R.id.menu_btn_settings);
        menu_btn_credits = findViewById(R.id.menu_btn_credits);
        menu_btn_logout = findViewById(R.id.menu_btn_logout);


        //views
        if(isGameActive)
            menu_btn_play.setText(getText(R.string.menu_btn_continue));


        //listeners

        //play
        menu_btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isGameActive) {
                    editor.putBoolean("isGameActive", true);
                    editor.putInt("questionCounter",0);
                    editor.commit();
                }

                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        //friends
        menu_btn_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, FriendsActivity.class);
                startActivity(intent);
            }
        });

        //scores
        menu_btn_scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ScoreActivity.class);
                startActivity(intent);
            }
        });

        //settings


        //credits
        menu_btn_credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CreditsActivity.class);
                startActivity(intent);
            }
        });

        //logout
        menu_btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.remove("isLogged");
                editor.commit();
                finish();
            }
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        isGameActive = sharedPreferences.getBoolean("isGameActive",false);

        if(isGameActive)
            menu_btn_play.setText(getText(R.string.menu_btn_continue));
        else
            menu_btn_play.setText(getText(R.string.menu_btn_play));

    }
}
