/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;

public class ScreenshotHelper {
    private static final String SYSUI_PACKAGE = "com.android.systemui";
    private static final String SYSUI_SCREENSHOT_ERROR_RECEIVER = "com.android.systemui.screenshot.ScreenshotServiceErrorReceiver";
    private static final String SYSUI_SCREENSHOT_SERVICE = "com.android.systemui.screenshot.TakeScreenshotService";
    private static final String TAG = "ScreenshotHelper";
    private final int SCREENSHOT_TIMEOUT_MS;
    private final Context mContext;
    private ServiceConnection mScreenshotConnection = null;
    private final Object mScreenshotLock = new Object();

    public ScreenshotHelper(Context context) {
        this.SCREENSHOT_TIMEOUT_MS = 10000;
        this.mContext = context;
    }

    private void notifyScreenshotError() {
        ComponentName componentName = new ComponentName(SYSUI_PACKAGE, SYSUI_SCREENSHOT_ERROR_RECEIVER);
        Intent intent = new Intent("android.intent.action.USER_PRESENT");
        intent.setComponent(componentName);
        intent.addFlags(335544320);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void takeScreenshot(final int var1_1, final boolean var2_2, final boolean var3_3, final Handler var4_4) {
        var5_6 = this.mScreenshotLock;
        // MONITORENTER : var5_6
        if (this.mScreenshotConnection != null) {
            // MONITOREXIT : var5_6
            return;
        }
        var6_7 = new ComponentName("com.android.systemui", "com.android.systemui.screenshot.TakeScreenshotService");
        var7_8 = new Intent();
        var8_9 = new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                Object object = ScreenshotHelper.this.mScreenshotLock;
                synchronized (object) {
                    if (ScreenshotHelper.this.mScreenshotConnection != null) {
                        ScreenshotHelper.this.mContext.unbindService(ScreenshotHelper.this.mScreenshotConnection);
                        ScreenshotHelper.this.mScreenshotConnection = null;
                        ScreenshotHelper.this.notifyScreenshotError();
                    }
                    return;
                }
            }
        };
        var7_8.setComponent((ComponentName)var6_7);
        var6_7 = new ServiceConnection(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onServiceConnected(ComponentName object, IBinder object2) {
                object = ScreenshotHelper.this.mScreenshotLock;
                synchronized (object) {
                    if (ScreenshotHelper.this.mScreenshotConnection != this) {
                        return;
                    }
                    Object object3 = new Messenger((IBinder)object2);
                    Message message = Message.obtain(null, var1_1);
                    Handler handler = new Handler(var4_4.getLooper()){

                        /*
                         * Enabled aggressive block sorting
                         * Enabled unnecessary exception pruning
                         * Enabled aggressive exception aggregation
                         */
                        @Override
                        public void handleMessage(Message object) {
                            object = ScreenshotHelper.this.mScreenshotLock;
                            synchronized (object) {
                                if (ScreenshotHelper.this.mScreenshotConnection == this) {
                                    ScreenshotHelper.this.mContext.unbindService(ScreenshotHelper.this.mScreenshotConnection);
                                    ScreenshotHelper.this.mScreenshotConnection = null;
                                    var4_4.removeCallbacks(var8_9);
                                }
                                return;
                            }
                        }
                    };
                    message.replyTo = object2 = new Messenger(handler);
                    boolean bl = var2_2;
                    int n = 1;
                    int n2 = bl ? 1 : 0;
                    message.arg1 = n2;
                    n2 = var3_3 ? n : 0;
                    message.arg2 = n2;
                    try {
                        ((Messenger)object3).send(message);
                    }
                    catch (RemoteException remoteException) {
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("Couldn't take screenshot: ");
                        ((StringBuilder)object3).append(remoteException);
                        Log.e(ScreenshotHelper.TAG, ((StringBuilder)object3).toString());
                    }
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onServiceDisconnected(ComponentName object) {
                object = ScreenshotHelper.this.mScreenshotLock;
                synchronized (object) {
                    if (ScreenshotHelper.this.mScreenshotConnection != null) {
                        ScreenshotHelper.this.mContext.unbindService(ScreenshotHelper.this.mScreenshotConnection);
                        ScreenshotHelper.this.mScreenshotConnection = null;
                        var4_4.removeCallbacks(var8_9);
                        ScreenshotHelper.this.notifyScreenshotError();
                    }
                    return;
                }
            }

        };
        if (!this.mContext.bindServiceAsUser(var7_8, var6_7, 33554433, UserHandle.CURRENT)) ** GOTO lbl17
        this.mScreenshotConnection = var6_7;
        var4_4.postDelayed(var8_9, 10000L);
lbl17: // 2 sources:
        // MONITOREXIT : var5_6
        return;
    }

}

