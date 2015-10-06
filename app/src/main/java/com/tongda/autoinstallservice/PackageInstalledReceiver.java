package com.tongda.autoinstallservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by changfeng on 2015/10/6.
 */
public class PackageInstalledReceiver extends BroadcastReceiver{

    private static final String TAG = "PkgInstalledReceiver";

    private static final String PACKAGE_ADDED = "android.intent.action.PACKAGE_ADDED";
    private static final String KQTECH_PACKAGE = "kqtech";
    private static final String TONGDA_PACKAGE = "tongda";


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() package installed");

        if (intent.getAction().equals(PACKAGE_ADDED)) {
            String packageName = intent.getDataString().replace("package:", "");
            Log.d(TAG, "onReceive() package " + packageName+" installed");
            if (packageName.contains(TONGDA_PACKAGE) || packageName.contains(KQTECH_PACKAGE)) {
                Log.d(TAG, "onReceive() target package installed");
                PackageManager pm = context.getPackageManager();
                Intent i = pm.getLaunchIntentForPackage(packageName);
                if (i != null) {
                    context.startActivity(i);
                    Toast.makeText(context,"start app " + packageName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context,"Could not launch", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onReceive() Could not launch");
                }

            }
        }
    }
}
