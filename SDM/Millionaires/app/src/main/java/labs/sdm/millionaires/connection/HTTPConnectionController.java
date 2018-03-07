package labs.sdm.millionaires.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Rafal on 2018-03-05.
 */

public class HTTPConnectionController {

    public static boolean isNetworkConnected(Context context, int network ) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info;
        switch (network) {
            case 0:
                info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                break;
            case 1:
                info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                break;
            default:
                info = manager.getActiveNetworkInfo();
                break;
        }
        return ((info!=null) && (info.isConnected()));

    }

}
