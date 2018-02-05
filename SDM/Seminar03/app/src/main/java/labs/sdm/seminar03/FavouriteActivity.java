package labs.sdm.seminar03;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FavouriteActivity extends AppCompatActivity {

    private String authorName = "Albert Einstein";
    private final String URL = "http://en.wikipedia.org/wiki/Special:Search?search=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        Button btAuthorInfo = findViewById(R.id.favourite_bt_authorInfo);
        btAuthorInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentAuthorInfo = new Intent();
                intentAuthorInfo.setAction(Intent.ACTION_VIEW);
                intentAuthorInfo.setData(Uri.parse( URL+authorName ));
                startActivity(intentAuthorInfo);
            }
        });

    }
}
