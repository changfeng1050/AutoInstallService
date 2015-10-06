package com.tongda.autoinstallservice;

import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class WindowAccessibilityService extends AccessibilityService {

    public static final String TAG = WindowAccessibilityService.class.getSimpleName();

    private static final String installPackage[] ={"com.android.packageinstaller"};
    private static final String INSTALL = "安装";

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        //可用代码配置当前Service的信息
        //		AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        //		info.packageNames = installPackge; //监听过滤的包名
        //		info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK; //监听哪些行为
        //		info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN; //反馈
        //		info.notificationTimeout = 100; //通知的时间
        //		setServiceInfo(info);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getSource() != null) {
            findAndPerformAction(getString(R.string.install));
            findAndPerformAction(getString(R.string.next));
//            findAndPerformAction(getString(R.string.open));
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
            for (int i = 0; i < nodes.size(); i++) {
                AccessibilityNodeInfo node = nodes.get(i);
                // 执行按钮点击行为
                if (node.getClassName().equals("android.widget.Button") && node.isEnabled()) {
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
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
