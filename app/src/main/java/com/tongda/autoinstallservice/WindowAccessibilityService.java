package com.tongda.autoinstallservice;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.List;

public class WindowAccessibilityService extends AccessibilityService {

    public static final String TAG = WindowAccessibilityService.class.getSimpleName();

//    private static final String installPackage[] ={"com.android.packageinstaller"};

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

//        //可用代码配置当前Service的信息
//        		AccessibilityServiceInfo info = new AccessibilityServiceInfo();
//        		info.packageNames = installPackage; //监听过滤的包名
//        		info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK; //监听哪些行为
////        		info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN; //反馈
//        		info.notificationTimeout = 100; //通知的时间
//        		setServiceInfo(info);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getSource() != null) {
            Log.d(TAG,"onAccessibilityEvent() called");
            findAndPerformAction(getString(R.string.install));
            findAndPerformAction(getString(R.string.next));
//            findAndPerformAction(getString(R.string.open));
            findAndPerformAction(getString(R.string.app_not_installed));
            findAndPerformAction(getString(R.string.done));

        }
    }

    private void findAndPerformAction(String text)
    {
        try {
            // 查找当前窗口中包含“安装”文字的按钮
            if (getRootInActiveWindow() == null)
                return;
            //通过文字找到当前的节点
            List<AccessibilityNodeInfo> nodes = getRootInActiveWindow().findAccessibilityNodeInfosByText(text);

            if (nodes == null) {
                return;
            }

            if (nodes.isEmpty()) {
                return;
            }
            Log.d(TAG, "findAndPerformAction() text:" + text);
            if (text.equals(getString(R.string.app_not_installed))) {
                Toast.makeText(this, getString(R.string.app_not_installed), Toast.LENGTH_SHORT).show();




                Intent intent = new Intent("com.tongda.autoinstallservice.APP_INSTALL_RESULT");
                intent.putExtra("install_result", "install_failed");

                sendBroadcast(intent);

                Log.d(TAG,"findAndPerformAction() install failed");
                return;
            }

            for (int i = 0; i < nodes.size(); i++) {
                AccessibilityNodeInfo node = nodes.get(i);

                // 执行按钮点击行为
                if (node.getClassName().equals("android.widget.Button") && node.isEnabled()) {
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    return;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "findAndPerformAction()", e);
        }
    }



    @Override
    public void onInterrupt() {

    }
}
