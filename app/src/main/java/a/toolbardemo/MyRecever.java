package a.toolbardemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by 我 on 2016/8/14.
 */
public class MyRecever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null&&networkInfo.isAvailable()){
            Toast.makeText(context,"网络连接成功",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"请检查网络连接，网络连接失败",Toast.LENGTH_SHORT).show();
        }

    }
}
