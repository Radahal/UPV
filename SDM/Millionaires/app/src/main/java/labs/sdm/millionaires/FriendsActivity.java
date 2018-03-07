package labs.sdm.millionaires;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import labs.sdm.millionaires.connection.MyFriendshipAsyncTask;
import labs.sdm.millionaires.database.Friend;
import labs.sdm.millionaires.adapters.FriendsArrayAdapter;
import labs.sdm.millionaires.database.Friendship;
import labs.sdm.millionaires.database.MillionaireDatabase;

public class FriendsActivity extends AppCompatActivity {

    private ListView friends_lv;
    private ArrayList<Friend> friends;
    private ArrayList<Integer> checked;
    private Menu menu;
    private MenuItem mi_removeAll;
    private MenuItem mi_add;
    private FriendsArrayAdapter adapter;

    private boolean isMultipleChoiceMode;
    private String newFriend_name;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);


        //references
        friends_lv = findViewById(R.id.friends_lv_list);
        Toolbar toolbar = findViewById(R.id.friends_toolbar);

        //preferences
        sharedPreferences = this.getSharedPreferences("user_information", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //get value from preferences
        username = sharedPreferences.getString("login","");

        //initialize friends list
        friends = getFriendsList();
        isMultipleChoiceMode = false;

        //toolbar
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);



        //listView
        adapter = new FriendsArrayAdapter(FriendsActivity.this, R.layout.friend_list_item, friends, username);
        friends_lv.setAdapter(adapter);
        friends_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mi_removeAll.setVisible(true);
                mi_add.setVisible(false);
                return false;
            }
        });

        //listeners

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.friends_multiple_menu, menu);
        this.menu = menu;
        mi_removeAll = menu.findItem(R.id.menu_friends_remove);
        mi_add = menu.findItem(R.id.menu_friends_add);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_friends_add:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.add_newFriend));

                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton(getText(R.string.add), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newFriend_name = input.getText().toString();
                        addNewFriend();
                    }
                });

                builder.setNegativeButton(getText(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                break;
            case R.id.menu_friends_remove:
                ArrayList<Integer> checked = adapter.getChecked();
                Collections.sort(checked);
                while (!checked.isEmpty()) {
                    int position = checked.get(checked.size()-1);
                    String friend_name = friends.get(position).getUsername();
                    MillionaireDatabase.getInstance(FriendsActivity.this).millionaireDao().deleteFriendship(username,friend_name);
                    friends.remove(position);
                    checked.remove(checked.size()-1);
                }
                adapter.setMultipleChoiceMode(false);
                adapter.notifyDataSetChanged();
                mi_removeAll.setVisible(false);
                mi_add.setVisible(true);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private ArrayList<Friend> getFriendsList() {
        ArrayList<Friend> friends = new ArrayList<>();
        List<Friendship> friendships = MillionaireDatabase.getInstance(FriendsActivity.this).millionaireDao().getFriendship(username);

        for(int i = 0; i<friendships.size(); i++) {
            String friend_name = friendships.get(i).getUsername2();
            friends.add(new Friend(friend_name,0));
        }

        return friends;
    }

    private void addNewFriend() {
        int reward = 0;
        Friend friend = new Friend(newFriend_name, reward);
        friends.add(friend);
        MillionaireDatabase.getInstance(FriendsActivity.this).millionaireDao().addFriendship(new Friendship(username, newFriend_name));

        MyFriendshipAsyncTask asyncTask = new MyFriendshipAsyncTask(this);
        asyncTask.execute();

        adapter.notifyDataSetChanged();
    }

    public String getUsername() {
        return username;
    }
    public String getFriendUsername() {return newFriend_name;}

    public void refreshListState() { adapter.notifyDataSetChanged(); }

}
