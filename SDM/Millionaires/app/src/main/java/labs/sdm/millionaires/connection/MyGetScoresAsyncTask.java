package labs.sdm.millionaires.connection;

import android.net.Uri;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import labs.sdm.millionaires.ScoreActivity;

/**
 * Created by Rafal on 2018-03-05.
 */

public class MyGetScoresAsyncTask extends AsyncTask<Void, Void, HighScoreList> {


    private WeakReference<ScoreActivity> weakReference;
    private String username;

    public MyGetScoresAsyncTask(ScoreActivity activity) {
        weakReference = new WeakReference<ScoreActivity>(activity);
    }

    @Override
    protected HighScoreList doInBackground(Void... voids) {

        HighScoreList highScoreList = new HighScoreList();

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https");
        builder.authority("wwtbamandroid.appspot.com");
        builder.appendPath("rest");
        builder.appendPath("highscores");
        builder.appendQueryParameter("name", weakReference.get().getUsername());

        try {

            URL url = new URL(builder.build().toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            //get data
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            GsonBuilder gsonBuilder = new GsonBuilder();

            Gson gson = gsonBuilder.create();
            highScoreList = gson.fromJson(reader, HighScoreList.class);

            connection.disconnect();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return highScoreList;
    }

}
