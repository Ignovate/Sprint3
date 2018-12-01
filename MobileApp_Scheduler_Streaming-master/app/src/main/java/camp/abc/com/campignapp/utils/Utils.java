package camp.abc.com.campignapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

    public static final String BASE_URL = "http://209.97.130.34/lectrefy-api-service/api/v1.0/scheduler?spotTime=3&spotDate=20181109";

    public static boolean isInternetConnected(Context ctx) {
        ConnectivityManager connectivityMgr = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi != null) {
            if (wifi.isConnected()) {
                return true;
            }
        }
        if (mobile != null) {
            if (mobile.isConnected()) {
                return true;
            }
        }
        return false;
    }

}
