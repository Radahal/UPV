package labs.sdm.seminar03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    private String username = "Nameless One";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void runNewActivity(View v) {

        switch (v.getId()) {

            case R.id.btn_dashboard_get:
                Intent intentGet = new Intent(DashboardActivity.this, QuotationActivity.class);
                intentGet.putExtra("username",username);
                startActivity(intentGet);
                break;
            case R.id.btn_dashboard_favourite:
                Intent intentFavourite = new Intent(DashboardActivity.this, FavouriteActivity.class);
                startActivity(intentFavourite);
                break;
            case R.id.btn_dashboard_settings:
                Intent intentSettings = new Intent(DashboardActivity.this, SettingsActivity.class);
                startActivity(intentSettings);
                break;
            case R.id.btn_dashboard_about:
                Intent intentAbout = new Intent(DashboardActivity.this, AboutActivity.class);
                startActivity(intentAbout);
                break;
        }

    }


}
