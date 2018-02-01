package labs.sdm.seminar01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public final String TAG = "LIFECYCLE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);
    
        
        TextView tvDynamicTextInt = findViewById(R.id.tvDynamicTextInt);
        TextView tvDynamicTextString = findViewById(R.id.tvDynamicTextString);

        tvDynamicTextInt.setText(R.string.dynamic_int);
        tvDynamicTextString.setText(R.string.dynamic_string);

        Button btCode = findViewById(R.id.button_ListenerByCode);
        Button btLayout = findViewById(R.id.button_ListenerFromLayout);

        btCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,R.string.toast_btnCode,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

    }

    public void displayMessage(View v) {
        Toast.makeText(MainActivity.this,R.string.toast_btnLayout,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("name","Rafał");
        startActivity(intent);

    }




    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }



    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }



}
