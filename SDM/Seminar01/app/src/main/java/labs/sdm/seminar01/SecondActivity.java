package labs.sdm.seminar01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView tv = findViewById(R.id.tvMessage);
        String val = getString(R.string.second_welcome2,  getIntent().getStringExtra("name"));

        tv.setText(val);
    }
}
