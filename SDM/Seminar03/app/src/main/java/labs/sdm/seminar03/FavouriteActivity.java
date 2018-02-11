package labs.sdm.seminar03;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import labs.sdm.myClasses.MyArrayAdapter;
import labs.sdm.myClasses.Quotation;

public class FavouriteActivity extends AppCompatActivity {

    private final String URL = "http://en.wikipedia.org/wiki/Special:Search?search=";
    private ArrayList<Quotation> quotationArrayList= new ArrayList<Quotation>();
    private ArrayAdapter myArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        quotationArrayList = getMockQuotations();



        myArrayAdapter = new MyArrayAdapter(FavouriteActivity.this, R.layout.quotation_list_row, quotationArrayList);

        //Button btAuthorInfo = findViewById(R.id.favourite_bt_authorInfo);

        final ListView lvQuotationList = findViewById(R.id.quotation_lv_quotations);
        lvQuotationList.setAdapter(myArrayAdapter);

        lvQuotationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvAuthor = view.findViewById(R.id.list_row_author);
                if(tvAuthor.getText() != null ) {

                    Intent intentAuthorInfo = new Intent();
                    intentAuthorInfo.setAction(Intent.ACTION_VIEW);
                    intentAuthorInfo.setData(Uri.parse( URL+tvAuthor.getText() ));
                    startActivity(intentAuthorInfo);

                } else {
                    Toast.makeText(FavouriteActivity.this, R.string.favourite_toast_emptyAuthor, Toast.LENGTH_SHORT).show();
                }
            }
        });

        lvQuotationList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FavouriteActivity.this);
                builder.setMessage(R.string.favourite_ad_remove);

                builder.setPositiveButton(R.string.favourite_ad_btnPositive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        quotationArrayList.remove(position);
                        //parent.removeViewAt(position);

                        //

                        //  !!!    ASK ABOUT REMOVING FROM ADAPTER (is neccesary to remove directly from data storage or is possible to do that throught the adapter)

                        //
                        myArrayAdapter.notifyDataSetChanged();


                    }
                });

                builder.setNegativeButton(R.string.favourite_ad_btnNegative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();
                return true;
            }
        });

/*
        btAuthorInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //actually copied into onItemClickListener
                Intent intentAuthorInfo = new Intent();
                intentAuthorInfo.setAction(Intent.ACTION_VIEW);
                intentAuthorInfo.setData(Uri.parse( URL+authorName ));
                startActivity(intentAuthorInfo);
            }
        });
*/



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favourite,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menuFavourite_remove:
                AlertDialog.Builder builder = new AlertDialog.Builder(FavouriteActivity.this);
                builder.setMessage(R.string.menu_ad_remove);
                builder.setPositiveButton(R.string.menu_ad_btnPositive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        quotationArrayList.clear();
                        myArrayAdapter.notifyDataSetChanged();
                        item.setVisible(false);
                    }
                });

                builder.setNegativeButton(R.string.menu_ad_btnNegative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Quotation> getMockQuotations() {
        ArrayList<Quotation> quotations = new ArrayList<>();

        for(int i = 0; i<10; i++) {
            Quotation quotation = new Quotation("Author", "quotation");
            quotations.add(quotation);
        }

        return quotations;
    }


}
