package labs.sdm.millionaires;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import labs.sdm.millionaires.authorization.AuthorizationObject;

public class LoginActivity extends AppCompatActivity {

    private final int LENGTH_LONG = 3500;
    private final int LENGTH_SHORT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupWindowAnimations();

        //hide ActionBar
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
       // getSupportActionBar().hide();


        //references
        final ConstraintLayout cl_form = findViewById(R.id.login_form);
        final ConstraintLayout cl_background = findViewById(R.id.login_background);
        final ConstraintLayout cl_logo = findViewById(R.id.login_logoBox);
        final ImageView iv_logo = findViewById(R.id.login_logo);
        final ProgressBar progressBar = findViewById(R.id.login_pg);
        final TextView form_tv_info = findViewById(R.id.login_tvInfo);
        final EditText form_pt_name = findViewById(R.id.login_ptName);
        final EditText form_pt_password = findViewById(R.id.login_ptPassword);
        final CheckBox form_cb_keep = findViewById(R.id.login_cbKeep);
        final Button form_btn_login = findViewById(R.id.login_btn);

        SharedPreferences sharedPreferences = this.getSharedPreferences("user_information",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final boolean isLogged = sharedPreferences.getBoolean("isLogged",false);

        progressBar.getIndeterminateDrawable().setColorFilter(getColor(R.color.btnActiveReward), PorterDuff.Mode.MULTIPLY);

        //LogoAnimation
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isLogged) {
                    //run menuActivity
                    TransitionManager.beginDelayedTransition(cl_logo);
                    progressBar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(intent);
                } else {
                    //show login form
                    TransitionManager.beginDelayedTransition(cl_background);
                    cl_form.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    iv_logo.setMinimumWidth(512);
                    iv_logo.setMinimumHeight(512);
                }
            }
        },LENGTH_LONG);

        //login
        form_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = form_pt_name.getText().toString();
                String pwd = form_pt_password.getText().toString();

                if(form_cb_keep.isChecked() ) {
                    editor.putBoolean("isLogged", true);
                    editor.commit();
                }
                AuthorizationObject authorizationObject = new AuthorizationObject(login,pwd);

                if(authorizationObject.check()) {
                    editor.putString("login", login);
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(intent);

                } else {
                    TransitionManager.beginDelayedTransition(cl_form);
                    form_tv_info.setText(R.string.login_tv_infoUserName);
                    form_tv_info.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            TransitionManager.beginDelayedTransition(cl_form);
                            form_tv_info.setVisibility(View.INVISIBLE);
                        }
                    }, LENGTH_SHORT);

                }

            }
        });

    }

    private void setupWindowAnimations() {
        Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        getWindow().setExitTransition(slide);
    }
}
