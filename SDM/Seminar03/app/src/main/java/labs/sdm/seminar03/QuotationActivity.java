package labs.sdm.seminar03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class QuotationActivity extends AppCompatActivity {

    private TextView tvQuotation;
    private TextView tvAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        // set references to views
         tvQuotation = findViewById(R.id.quotation_tv_quotation);
         tvAuthor = findViewById(R.id.quotation_tv_author);

        // getData from intent
        String username = getIntent().getStringExtra("username");

        // set value of views
        tvQuotation.setText( getString(R.string.quotation_tv_instruction,username) );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quotations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_add:
                return true;

            case R.id.menu_rotate:
                tvQuotation.setText(R.string.quotation_tv_sampleQuotation);
                tvAuthor.setText(R.string.quotation_tv_sampleAuthor);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
