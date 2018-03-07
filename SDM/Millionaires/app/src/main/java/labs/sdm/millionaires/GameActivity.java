package labs.sdm.millionaires;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import labs.sdm.millionaires.connection.HighScore;
import labs.sdm.millionaires.connection.MyPutScoreAsyncTask;
import labs.sdm.millionaires.database.MillionaireDatabase;
import labs.sdm.millionaires.database.Reward;
import labs.sdm.millionaires.adapters.RewardsArrayAdapter;
import labs.sdm.millionaires.database.Score;
import labs.sdm.millionaires.game.Question;

public class GameActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView rewardListView;
    private ArrayList<Reward> rewardsArrayList = new ArrayList<Reward>();
    private ArrayList<String> rewardsValuesArrayList = new ArrayList<String>();
    private List<Question> questions;
    private final int questionCounterMax = 15;
    private Menu menu;
    private Toolbar toolbar;
    private RewardsArrayAdapter rewardsArrayAdapter;


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private boolean isGameActive;
    private boolean usedCall;
    private boolean usedStage;
    private boolean usedFifty;
    private String username;
    private String reached_score;

    private Button btn_answerA;
    private Button btn_answerB;
    private Button btn_answerC;
    private Button btn_answerD;
    private TextView tv_question;

    private int questionCounter;

    private final int LENGTH_LONG = 2500;
    private final int LENGTH_SHORT = 1500;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        //questions = new ArrayList<>();
        questions = generateQuestionList();
        //sharedPreferences
        sharedPreferences = this.getSharedPreferences("user_information", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        //values from preferences
        questionCounter = sharedPreferences.getInt("questionCounter",0);
        isGameActive = sharedPreferences.getBoolean("isGameActive",false);
        usedStage= sharedPreferences.getBoolean("usedStage",false);;
        usedCall = sharedPreferences.getBoolean("usedCall",false);;
        usedFifty = sharedPreferences.getBoolean("usedFifty",false);;
        username = sharedPreferences.getString("login", "");

        //references
        tv_question = findViewById(R.id.game_tv_question);
        btn_answerA = findViewById(R.id.game_btn_answerA);
        btn_answerB = findViewById(R.id.game_btn_answerB);
        btn_answerC = findViewById(R.id.game_btn_answerC);
        btn_answerD = findViewById(R.id.game_btn_answerD);
        toolbar = findViewById(R.id.game_toolbar);

        //toolbar
        mDrawerLayout = findViewById(R.id.game_dl);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,
                R.string.game_drawer_open,  /* "open drawer" description */
                R.string.game_drawer_close  /* "close drawer" description */
        ) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(R.string.app_name);
                //invalidateOptionsMenu();
                syncState();
                setHelpIconsPresence();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.app_name);
                //invalidateOptionsMenu();
                syncState();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle.syncState();
        if(!isGameActive)
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        else
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);



        //views
        rewardsValuesArrayList = initializeRewardsValues();
        generateRewardList();
        rewardListView = findViewById(R.id.reward_list);
        rewardsArrayAdapter = new RewardsArrayAdapter(GameActivity.this, R.layout.reward_list_item, rewardsArrayList);
        rewardListView.setAdapter(rewardsArrayAdapter);

        //references
        Button btn_gaveUp = findViewById(R.id.game_navigation_cancel);


        //listeners

        btn_gaveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.remove("isGameActive");
                editor.remove("questionCounter");
                editor.remove("usedCall");
                editor.remove("usedStage");
                editor.remove("usedFifty");
                editor.commit();
                if(questionCounter>0)
                    saveGameResult(rewardsArrayList.get(questionCounter-1).getRewardValue());
                else
                    saveGameResult("0");
                finish();
            }
        });


        btn_answerA.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                btn_answerA.setBackground(getDrawable(R.drawable.btn_millionaire_pressed));
                final Handler handler = new Handler();
                //zaczekaj
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkQuestion("1");
                    }
                }, LENGTH_LONG);

                return false;
            }
        });

        btn_answerB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                btn_answerB.setBackground(getDrawable(R.drawable.btn_millionaire_pressed));
                final Handler handler = new Handler();
                //zaczekaj
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkQuestion("2");
                    }
                }, LENGTH_LONG);
                return false;
            }
        });

        btn_answerC.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                btn_answerC.setBackground(getDrawable(R.drawable.btn_millionaire_pressed));
                final Handler handler = new Handler();
                //zaczekaj
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkQuestion("3");
                    }
                }, LENGTH_LONG);
                return false;
            }
        });

        btn_answerD.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                btn_answerD.setBackground(getDrawable(R.drawable.btn_millionaire_pressed));
                final Handler handler = new Handler();
                //zaczekaj
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkQuestion("4");
                    }
                }, LENGTH_LONG);
                return false;
            }
        });
        generateQuestion(questionCounter);

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void closeDrawerLayout() {
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_drawer, menu);
        this.menu = menu;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.START);
                return true;
            case R.id.menu_game_call:
                if(!usedCall) {
                    Question q = questions.get(questionCounter);
                    switch (q.getPhone()) {
                        case "1":
                            btn_answerA.setBackground(getDrawable(R.drawable.btn_millionaire_hint));
                            break;
                        case "2":
                            btn_answerB.setBackground(getDrawable(R.drawable.btn_millionaire_hint));
                            break;
                        case "3":
                            btn_answerC.setBackground(getDrawable(R.drawable.btn_millionaire_hint));
                            break;
                        case "4":
                            btn_answerD.setBackground(getDrawable(R.drawable.btn_millionaire_hint));
                            break;
                    }
                    usedCall=!usedCall;
                    setHelpIconsPresence();
                }
                return true;
            case R.id.menu_game_percent:
                if(!usedFifty) {
                    Question q = questions.get(questionCounter);
                    switch (q.getFifty1()) {
                        case "1":
                            btn_answerA.setVisibility(View.INVISIBLE);
                            break;
                        case "2":
                            btn_answerB.setVisibility(View.INVISIBLE);
                            break;
                        case "3":
                            btn_answerC.setVisibility(View.INVISIBLE);
                            break;
                        case "4":
                            btn_answerD.setVisibility(View.INVISIBLE);
                            break;
                    }
                    switch (q.getFifty2()) {
                        case "1":
                            btn_answerA.setVisibility(View.INVISIBLE);
                            break;
                        case "2":
                            btn_answerB.setVisibility(View.INVISIBLE);
                            break;
                        case "3":
                            btn_answerC.setVisibility(View.INVISIBLE);
                            break;
                        case "4":
                            btn_answerD.setVisibility(View.INVISIBLE);
                            break;
                    }
                    usedFifty=!usedFifty;
                    setHelpIconsPresence();

                }
                return true;
            case R.id.menu_game_stage:
                if(!usedStage) {
                    Question q = questions.get(questionCounter);
                    switch (q.getAudience()) {
                        case "1":
                            btn_answerA.setBackground(getDrawable(R.drawable.btn_millionaire_hint));
                            break;
                        case "2":
                            btn_answerB.setBackground(getDrawable(R.drawable.btn_millionaire_hint));
                            break;
                        case "3":
                            btn_answerC.setBackground(getDrawable(R.drawable.btn_millionaire_hint));
                            break;
                        case "4":
                            btn_answerD.setBackground(getDrawable(R.drawable.btn_millionaire_hint));
                            break;
                    }
                    usedStage=!usedStage;
                    setHelpIconsPresence();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    void generateRewardList() {
        rewardsArrayList.clear();

        for(int i = 0; i<questionCounter; i++) {
            Reward reward = new Reward(rewardsValuesArrayList.get(i),false);
            rewardsArrayList.add(reward);
        }
        if(questionCounter<questionCounterMax) {
            Reward reward = new Reward(rewardsValuesArrayList.get(questionCounter),true);
            rewardsArrayList.add(reward);
        }

    }

    ArrayList<String> initializeRewardsValues() {
        ArrayList<String> rewardValues = new ArrayList<>();
        //100, 200, 300, 500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 125000, 250000, 500000, 1000000

        rewardValues.add("100");
        rewardValues.add("200");
        rewardValues.add("300");
        rewardValues.add("500");
        rewardValues.add("1000");
        rewardValues.add("2000");
        rewardValues.add("4000");
        rewardValues.add("8000");
        rewardValues.add("16000");
        rewardValues.add("32000");
        rewardValues.add("64000");
        rewardValues.add("125000");
        rewardValues.add("200000");
        rewardValues.add("500000");
        rewardValues.add("1000000");

        return rewardValues;
    }


    public void generateQuestion(int position) {

        btn_answerA.setVisibility(View.VISIBLE);
        btn_answerB.setVisibility(View.VISIBLE);
        btn_answerC.setVisibility(View.VISIBLE);
        btn_answerD.setVisibility(View.VISIBLE);

        btn_answerA.setBackground(getDrawable(R.drawable.btn_millionaire));
        btn_answerB.setBackground(getDrawable(R.drawable.btn_millionaire));
        btn_answerC.setBackground(getDrawable(R.drawable.btn_millionaire));
        btn_answerD.setBackground(getDrawable(R.drawable.btn_millionaire));

        Question q = questions.get(position);

        tv_question.setText(q.getText());
        btn_answerA.setText(q.getAnswer1());
        btn_answerB.setText(q.getAnswer2());
        btn_answerC.setText(q.getAnswer3());
        btn_answerD.setText(q.getAnswer4());
    }


    protected void saveGameResult(String score) {
        MillionaireDatabase.getInstance(GameActivity.this).millionaireDao().addScore(new Score(username, Integer.parseInt(score)));
        reached_score = score;
        MyPutScoreAsyncTask asyncTask = new MyPutScoreAsyncTask(this);
        asyncTask.execute();
    }

    @Override
    public void onBackPressed() {
        editor.putBoolean("isGameActive", true);
        editor.putInt("questionCounter",questionCounter);
        editor.putBoolean("usedCall", usedCall);
        editor.putBoolean("usedStage", usedStage);
        editor.putBoolean("usedFifty",usedFifty);
        editor.commit();
        super.onBackPressed();
    }

    public void checkQuestion(String btn_value) {
        Question q = questions.get(questionCounter);

        //zaznacz poprawna
        switch (q.getRight()) {
            case "1":
                btn_answerA.setBackground(getDrawable(R.drawable.btn_millionaire_correct));
                break;
            case "2":
                btn_answerB.setBackground(getDrawable(R.drawable.btn_millionaire_correct));
                break;
            case "3":
                btn_answerC.setBackground(getDrawable(R.drawable.btn_millionaire_correct));
                break;
            case "4":
                btn_answerD.setBackground(getDrawable(R.drawable.btn_millionaire_correct));
                break;
        }


        final Handler handler = new Handler();
        if(btn_value.equals(q.getRight())) {

            //zaczekaj
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    questionCounter++;
                    generateNextQuestion();
                }
            }, LENGTH_SHORT);


            //right answer - next question if possible
        } else {
            //wrong answer - last guarantee reward, save result, finish
            //zaznacz na zielono poprawna
            //wyswietl info o koncu
            //zaczekaj
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    endGame();
                }
            }, LENGTH_SHORT);
            //zakoncz


        }
    }

    public void generateNextQuestion() {
        if(questionCounter<questionCounterMax) {
            //next question
            //zaczekaj
            rewardsArrayAdapter.setQuestionCounter(questionCounter);
            generateQuestion(questionCounter);
            generateRewardList();
            rewardsArrayAdapter.notifyDataSetChanged();
        } else {
            //win the game, save result, finish
            //zaczekaj
            //wyświetl info o wygranej
            editor.remove("isGameActive");
            editor.remove("questionCounter");
            editor.remove("usedCall");
            editor.remove("usedStage");
            editor.remove("usedFifty");
            editor.commit();
            saveGameResult(rewardsArrayList.get( rewardsArrayList.size()-1 ).getRewardValue());
            finish();
        }
    }

    public void endGame() {
        editor.remove("isGameActive");
        editor.remove("questionCounter");
        editor.remove("usedCall");
        editor.remove("usedStage");
        editor.remove("usedFifty");
        editor.commit();
        saveGameResult(getLastGuaranteeReward());
        finish();
    }


    public String getLastGuaranteeReward() {
        if(questionCounter<5) {
            return "0";
        } else if (questionCounter<10) {
            return "1000";
        } else if(questionCounter<15) {
            return "32000";
        } else {
            return "1000000";
        }
    }





    public List<Question> generateQuestionList() {
        List<Question> list = new ArrayList<>();
        Question q = null;

        try {
            Resources res = this.getResources();

            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            InputMethodSubtype ims = imm.getCurrentInputMethodSubtype();
            String localeString = ims.getLocale();
            Locale locale = new Locale(localeString);
            String currentLanguage = locale.getDisplayLanguage();

            XmlResourceParser parser = res.getXml(R.xml.game_questions_list);
            if(currentLanguage.contains("es")) {
                parser = res.getXml(R.xml.game_questions_list_spanish);
            }
            parser.next();
            int eventType = parser.getEventType();

            while (XmlPullParser.END_DOCUMENT != eventType) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if("question".equals(parser.getName())) {
                            String answer1 = parser.getAttributeValue(null,"answer1");
                            String answer2 = parser.getAttributeValue(null,"answer2");
                            String answer3 = parser.getAttributeValue(null,"answer3");
                            String answer4 = parser.getAttributeValue(null,"answer4");
                            String audience = parser.getAttributeValue(null,"audience");
                            String fifty1 = parser.getAttributeValue(null,"fifty1");
                            String fifty2 = parser.getAttributeValue(null,"fifty2");
                            String number = parser.getAttributeValue(null,"number");
                            String phone = parser.getAttributeValue(null,"phone");
                            String right = parser.getAttributeValue(null,"right");
                            String text = parser.getAttributeValue(null,"text");
                            list.add( new Question(number, text, answer1, answer2, answer3, answer4, right, audience, phone, fifty1, fifty2) );
                        }
                        break;
                }
                parser.next();
                eventType = parser.getEventType();
            }
        }
        catch (XmlPullParserException e) { e.printStackTrace(); }
        catch (FileNotFoundException e) { e.printStackTrace(); }
        catch (IOException e)  { e.printStackTrace(); }


        return list;
    }

    private void setHelpIconsPresence() {
        MenuItem mi_call = menu.findItem(R.id.menu_game_call);
        MenuItem mi_half = menu.findItem(R.id.menu_game_percent);
        MenuItem mi_stage = menu.findItem(R.id.menu_game_stage);

        ImageView iv_call = findViewById(R.id.header_help_call);
        ImageView iv_half = findViewById(R.id.header_help_half);
        ImageView iv_stage = findViewById(R.id.header_help_stage);

        if(usedCall) {
            mi_call.setIcon(getDrawable(R.drawable.help_call_inactive));
            mi_call.setVisible(false);
            iv_call.setImageDrawable(getDrawable(R.drawable.help_call_inactive));
        }

        if(usedFifty) {
            mi_half.setIcon(getDrawable(R.drawable.help_percent_inactive));
            mi_half.setVisible(false);
            iv_half.setImageDrawable(getDrawable(R.drawable.help_percent_inactive));
        }

        if(usedStage) {
            mi_stage.setIcon(getDrawable(R.drawable.help_stage_inactive));
            mi_stage.setVisible(false);
            iv_stage.setImageDrawable(getDrawable(R.drawable.help_stage_inactive));
        }

    }


    public HighScore getHighScore() {
        Integer result = MillionaireDatabase.getInstance(this).millionaireDao().getHighScore(username);
        String latitiude = "0";
        String longitiude = "0";
        HighScore highScore = new HighScore(username, result.toString(), longitiude, latitiude);
        return highScore;
    }

    public String getScore() {
        return reached_score;
    }

    public String getUsername() {
        return username;
    }



}
