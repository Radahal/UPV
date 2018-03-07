package labs.sdm.millionaires.connection;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import labs.sdm.millionaires.GameActivity;
import labs.sdm.millionaires.database.Score;

/**
 * Created by Rafal on 2018-03-05.
 */

public class MyPutScoreAsyncTask extends AsyncTask<Void, Void, HighScore> {

    private WeakReference<GameActivity> weakReference;

    public MyPutScoreAsyncTask(GameActivity activity) {
        weakReference = new WeakReference<GameActivity>(activity);
    }

    @Override
    protected HighScore doInBackground(Void... voids) {
//https://wwtbamandroid.appspot.com/rest/highscores/
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https");
        builder.authority("wwtbamandroid.appspot.com");
        builder.appendPath("rest");
        builder.appendPath("highscores");

        String username = weakReference.get().getUsername();
        String score = weakReference.get().getScore();

        //builder.appendQueryParameter("name", username);
        //builder.appendQueryParameter("score", score);
        String body = "name="+username+"&score="+score;
        try {

            URL url = new URL(builder.build().toString());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("PUT");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();
            //wirte stream, build new JSON object

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
    protected void onPostExecute(HighScore highScore) {
        super.onPostExecute(highScore);
    }
}
