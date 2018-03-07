package labs.sdm.millionaires.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import labs.sdm.millionaires.R;
import labs.sdm.millionaires.database.Friend;
import labs.sdm.millionaires.database.Friendship;
import labs.sdm.millionaires.database.MillionaireDatabase;

/**
 * Created by Rafal on 2018-03-03.
 */

public class FriendsArrayAdapter extends ArrayAdapter {

    private Context context;
    private int resource;
    private List<Friend> friends;
    private ArrayList<Integer> checked;
    private boolean isMultipleChoiceMode;

    private String username;


    public FriendsArrayAdapter(@NonNull Context context, int resource, List<Friend> friends, String username) {
        super(context,resource,friends);
        this.context = context;
        this.resource = resource;
        this.friends = friends;

        this.username = username;
        checked = new ArrayList<Integer>();
        isMultipleChoiceMode= false;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource,null);
        }

        TextView tv_friend = view.findViewById(R.id.friends_tv_name);
        final CheckBox cb_friend = view.findViewById(R.id.friends_cb);
        final ImageButton ib_remove = view.findViewById(R.id.friends_ib_remove);

        tv_friend.setText(friends.get(position).getUsername());


        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setMultipleChoiceMode(true);
                cb_friend.setChecked(true);
                notifyDataSetChanged();
                return false;
            }
        });


        cb_friend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    addToList(position);
                }   else {
                    removeFromList(position);
                }
            }
        });

        ib_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String friend_name = friends.get(position).getUsername();
                friends.remove(position);
                MillionaireDatabase.getInstance(context).millionaireDao().deleteFriendship(username, friend_name);
                notifyDataSetChanged();
            }
        });

        if(isMultipleChoiceMode) {
            //showMultipleChoiceMode();
            cb_friend.setVisibility(View.VISIBLE);
            ib_remove.setVisibility(View.INVISIBLE);
        } else {
            //hideMultipleChoiceMode();
            cb_friend.setVisibility(View.INVISIBLE);
            cb_friend.setChecked(false);
            ib_remove.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private void addToList(int value) {
        if(!(checked.contains(value))) {
            checked.add(value);
        }
    }

    private void removeFromList(int value) {
        if(checked.contains(value)) {
            checked.remove( checked.indexOf(value) );
            if(checked.isEmpty()) {
                isMultipleChoiceMode = false;
                notifyDataSetChanged();
            }
        }
    }

    public void setMultipleChoiceMode(boolean multipleChoiceMode) {
        isMultipleChoiceMode = multipleChoiceMode;
    }

    public boolean getMultipleChoiceMode() {
        return isMultipleChoiceMode;
    }

    public ArrayList<Integer> getChecked() {
        return checked;
    }


}
