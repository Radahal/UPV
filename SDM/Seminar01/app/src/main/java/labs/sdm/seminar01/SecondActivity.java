package labs.sdm.seminar01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView tv = findViewById(R.id.tvMessage);

        if(getIntent().getAction() != Intent.ACTION_VIEW) {

            String val = getString(R.string.second_welcome2, getIntent().getStringExtra("name"));
            tv.setText(val);
        } else {
            String val = getIntent().getData().toString();
            tv.setText(val);
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        intent.putExtra("message", "Come back soon!");
        setResult(RESULT_OK,intent);
        super.onBackPressed();
    }
}
