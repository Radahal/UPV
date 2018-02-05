package labs.sdm.seminar03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class QuotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        // set references to views
        final TextView tvQuotation = findViewById(R.id.quotation_tv_quotation);
        final TextView tvAuthor = findViewById(R.id.quotation_tv_author);
        ImageButton ibRefresh = findViewById(R.id.quotation_ib_refresh);


        // getData from intent
        String username = getIntent().getStringExtra("username");

        // set value of views
        tvQuotation.setText( getString(R.string.quotation_tv_instruction,username) );

        // set listeners and actions
        ibRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvQuotation.setText(R.string.quotation_tv_sampleQuotation);
                tvAuthor.setText(R.string.quotation_tv_sampleAuthor);
            }
        });
    }
}
