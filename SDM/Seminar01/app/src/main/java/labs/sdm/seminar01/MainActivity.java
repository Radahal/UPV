package labs.sdm.seminar01;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "LIFECYCLE";
    private static final int INTENT_CODE = 1;

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
        Button btImplicit = findViewById(R.id.button_implicitIntent);


        btCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,R.string.toast_btnCode,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        btImplicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.upv.es"));

                Intent chooser = Intent.createChooser(intent,getResources().getString(R.string.chooser));

                if(intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }

            }
        });

    }

    public void displayMessage(View v) {
        Toast.makeText(MainActivity.this,R.string.toast_btnLayout,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("name","Rafał");
        startActivityForResult(intent,INTENT_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case INTENT_CODE:
                String message = data.getStringExtra("message");
                Toast.makeText(MainActivity.this,message, Toast.LENGTH_SHORT).show();
                break;
        }
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
