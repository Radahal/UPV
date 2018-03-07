package labs.sdm.millionaires.connection;

import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import labs.sdm.millionaires.FriendsActivity;
import labs.sdm.millionaires.R;
import labs.sdm.millionaires.database.Friendship;

/**
 * Created by Rafal on 2018-03-05.
 */

public class MyFriendshipAsyncTask extends AsyncTask<Void, Void, Friendship> {

    private WeakReference<FriendsActivity> weakReference;

    public MyFriendshipAsyncTask(FriendsActivity activity) {
        weakReference = new WeakReference<FriendsActivity>(activity);
    }

    @Override
    protected Friendship doInBackground(Void... voids) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https");
        builder.authority("wwtbamandroid.appspot.com");
        builder.appendPath("rest");
        builder.appendPath("friends");
        String body = "name="+weakReference.get().getUsername()+"&friend_name="+weakReference.get().getFriendUsername();
        //builder.appendQueryParameter("name", weakReference.get().getUsername());
        //builder.appendQueryParameter("friend_name", weakReference.get().getFriendUsername());

            try {

                URL url = new URL(builder.build().toString());
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);


                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(body);
                writer.flush();
                writer.close();

                int responseCode = connection.getResponseCode();



                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }




        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Friendship friendship) {
        weakReference.get().refreshListState();
        super.onPostExecute(friendship);
    }
}
