/*
 * Decompiled with CFR 0.145.
 */
package android.service.notification;

import android.annotation.SystemApi;
import android.app.INotificationManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.service.notification.Adjustment;
import android.service.notification.INotificationListener;
import android.service.notification.IStatusBarNotificationHolder;
import android.service.notification.NotificationListenerService;
import android.service.notification.NotificationStats;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.internal.os.SomeArgs;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.List;

@SystemApi
public abstract class NotificationAssistantService
extends NotificationListenerService {
    public static final String SERVICE_INTERFACE = "android.service.notification.NotificationAssistantService";
    public static final int SOURCE_FROM_APP = 0;
    public static final int SOURCE_FROM_ASSISTANT = 1;
    private static final String TAG = "NotificationAssistants";
    protected Handler mHandler;

    private void setAdjustmentIssuer(Adjustment adjustment) {
        if (adjustment != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.getOpPackageName());
            stringBuilder.append("/");
            stringBuilder.append(this.getClass().getName());
            adjustment.setIssuer(stringBuilder.toString());
        }
    }

    public final void adjustNotification(Adjustment adjustment) {
        if (!this.isBound()) {
            return;
        }
        try {
            this.setAdjustmentIssuer(adjustment);
            this.getNotificationInterface().applyEnqueuedAdjustmentFromAssistant(this.mWrapper, adjustment);
            return;
        }
        catch (RemoteException remoteException) {
            Log.v(TAG, "Unable to contact notification manager", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public final void adjustNotifications(List<Adjustment> list) {
        if (!this.isBound()) {
            return;
        }
        try {
            Iterator<Adjustment> iterator = list.iterator();
            while (iterator.hasNext()) {
                this.setAdjustmentIssuer(iterator.next());
            }
            this.getNotificationInterface().applyAdjustmentsFromAssistant(this.mWrapper, list);
            return;
        }
        catch (RemoteException remoteException) {
            Log.v(TAG, "Unable to contact notification manager", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        this.mHandler = new MyHandler(this.getContext().getMainLooper());
    }

    public void onActionInvoked(String string2, Notification.Action action, int n) {
    }

    public void onAllowedAdjustmentsChanged() {
    }

    @Override
    public final IBinder onBind(Intent intent) {
        if (this.mWrapper == null) {
            this.mWrapper = new NotificationAssistantServiceWrapper();
        }
        return this.mWrapper;
    }

    public void onNotificationDirectReplied(String string2) {
    }

    public abstract Adjustment onNotificationEnqueued(StatusBarNotification var1);

    public Adjustment onNotificationEnqueued(StatusBarNotification statusBarNotification, NotificationChannel notificationChannel) {
        return this.onNotificationEnqueued(statusBarNotification);
    }

    public void onNotificationExpansionChanged(String string2, boolean bl, boolean bl2) {
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, NotificationStats notificationStats, int n) {
        this.onNotificationRemoved(statusBarNotification, rankingMap, n);
    }

    public abstract void onNotificationSnoozedUntilContext(StatusBarNotification var1, String var2);

    public void onNotificationsSeen(List<String> list) {
    }

    public void onSuggestedReplySent(String string2, CharSequence charSequence, int n) {
    }

    public final void unsnoozeNotification(String string2) {
        if (!this.isBound()) {
            return;
        }
        try {
            this.getNotificationInterface().unsnoozeNotificationFromAssistant(this.mWrapper, string2);
        }
        catch (RemoteException remoteException) {
            Log.v("NotificationAssistants", "Unable to contact notification manager", remoteException);
        }
    }

    private final class MyHandler
    extends Handler {
        public static final int MSG_ON_ACTION_INVOKED = 7;
        public static final int MSG_ON_ALLOWED_ADJUSTMENTS_CHANGED = 8;
        public static final int MSG_ON_NOTIFICATIONS_SEEN = 3;
        public static final int MSG_ON_NOTIFICATION_DIRECT_REPLY_SENT = 5;
        public static final int MSG_ON_NOTIFICATION_ENQUEUED = 1;
        public static final int MSG_ON_NOTIFICATION_EXPANSION_CHANGED = 4;
        public static final int MSG_ON_NOTIFICATION_SNOOZED = 2;
        public static final int MSG_ON_SUGGESTED_REPLY_SENT = 6;

        public MyHandler(Looper looper) {
            super(looper, null, false);
        }

        @Override
        public void handleMessage(Message object) {
            switch (((Message)object).what) {
                default: {
                    break;
                }
                case 8: {
                    NotificationAssistantService.this.onAllowedAdjustmentsChanged();
                    break;
                }
                case 7: {
                    SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                    String string2 = (String)someArgs.arg1;
                    object = (Notification.Action)someArgs.arg2;
                    int n = someArgs.argi2;
                    someArgs.recycle();
                    NotificationAssistantService.this.onActionInvoked(string2, (Notification.Action)object, n);
                    break;
                }
                case 6: {
                    SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                    object = (String)someArgs.arg1;
                    CharSequence charSequence = (CharSequence)someArgs.arg2;
                    int n = someArgs.argi2;
                    someArgs.recycle();
                    NotificationAssistantService.this.onSuggestedReplySent((String)object, charSequence, n);
                    break;
                }
                case 5: {
                    object = (SomeArgs)((Message)object).obj;
                    String string3 = (String)((SomeArgs)object).arg1;
                    ((SomeArgs)object).recycle();
                    NotificationAssistantService.this.onNotificationDirectReplied(string3);
                    break;
                }
                case 4: {
                    object = (SomeArgs)((Message)object).obj;
                    String string4 = (String)((SomeArgs)object).arg1;
                    int n = ((SomeArgs)object).argi1;
                    boolean bl = false;
                    boolean bl2 = n == 1;
                    if (((SomeArgs)object).argi2 == 1) {
                        bl = true;
                    }
                    ((SomeArgs)object).recycle();
                    NotificationAssistantService.this.onNotificationExpansionChanged(string4, bl2, bl);
                    break;
                }
                case 3: {
                    object = (SomeArgs)((Message)object).obj;
                    List list = (List)((SomeArgs)object).arg1;
                    ((SomeArgs)object).recycle();
                    NotificationAssistantService.this.onNotificationsSeen(list);
                    break;
                }
                case 2: {
                    SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                    object = (StatusBarNotification)someArgs.arg1;
                    String string5 = (String)someArgs.arg2;
                    someArgs.recycle();
                    NotificationAssistantService.this.onNotificationSnoozedUntilContext((StatusBarNotification)object, string5);
                    break;
                }
                case 1: {
                    SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                    object = (StatusBarNotification)someArgs.arg1;
                    NotificationChannel notificationChannel = (NotificationChannel)someArgs.arg2;
                    someArgs.recycle();
                    object = NotificationAssistantService.this.onNotificationEnqueued((StatusBarNotification)object, notificationChannel);
                    NotificationAssistantService.this.setAdjustmentIssuer((Adjustment)object);
                    if (object == null) break;
                    if (!NotificationAssistantService.this.isBound()) {
                        Log.w(NotificationAssistantService.TAG, "MSG_ON_NOTIFICATION_ENQUEUED: service not bound, skip.");
                        return;
                    }
                    try {
                        NotificationAssistantService.this.getNotificationInterface().applyEnqueuedAdjustmentFromAssistant(NotificationAssistantService.this.mWrapper, (Adjustment)object);
                        break;
                    }
                    catch (SecurityException securityException) {
                        Log.w(NotificationAssistantService.TAG, "Enqueue adjustment failed; no longer connected", securityException);
                        break;
                    }
                    catch (RemoteException remoteException) {
                        Log.v(NotificationAssistantService.TAG, "Unable to contact notification manager", remoteException);
                        throw remoteException.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    private class NotificationAssistantServiceWrapper
    extends NotificationListenerService.NotificationListenerWrapper {
        private NotificationAssistantServiceWrapper() {
        }

        @Override
        public void onActionClicked(String string2, Notification.Action action, int n) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = string2;
            someArgs.arg2 = action;
            someArgs.argi2 = n;
            NotificationAssistantService.this.mHandler.obtainMessage(7, someArgs).sendToTarget();
        }

        @Override
        public void onAllowedAdjustmentsChanged() {
            NotificationAssistantService.this.mHandler.obtainMessage(8).sendToTarget();
        }

        @Override
        public void onNotificationDirectReply(String string2) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = string2;
            NotificationAssistantService.this.mHandler.obtainMessage(5, someArgs).sendToTarget();
        }

        @Override
        public void onNotificationEnqueuedWithChannel(IStatusBarNotificationHolder object, NotificationChannel notificationChannel) {
            try {
                object = object.get();
            }
            catch (RemoteException remoteException) {
                Log.w(NotificationAssistantService.TAG, "onNotificationEnqueued: Error receiving StatusBarNotification", remoteException);
                return;
            }
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = object;
            someArgs.arg2 = notificationChannel;
            NotificationAssistantService.this.mHandler.obtainMessage(1, someArgs).sendToTarget();
        }

        @Override
        public void onNotificationExpansionChanged(String string2, boolean bl, boolean bl2) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = string2;
            someArgs.argi1 = bl ? 1 : 0;
            someArgs.argi2 = bl2 ? 1 : 0;
            NotificationAssistantService.this.mHandler.obtainMessage(4, someArgs).sendToTarget();
        }

        @Override
        public void onNotificationSnoozedUntilContext(IStatusBarNotificationHolder object, String string2) {
            StatusBarNotification statusBarNotification;
            try {
                statusBarNotification = object.get();
            }
            catch (RemoteException remoteException) {
                Log.w(NotificationAssistantService.TAG, "onNotificationSnoozed: Error receiving StatusBarNotification", remoteException);
                return;
            }
            object = SomeArgs.obtain();
            ((SomeArgs)object).arg1 = statusBarNotification;
            ((SomeArgs)object).arg2 = string2;
            NotificationAssistantService.this.mHandler.obtainMessage(2, object).sendToTarget();
        }

        @Override
        public void onNotificationsSeen(List<String> list) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = list;
            NotificationAssistantService.this.mHandler.obtainMessage(3, someArgs).sendToTarget();
        }

        @Override
        public void onSuggestedReplySent(String string2, CharSequence charSequence, int n) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = string2;
            someArgs.arg2 = charSequence;
            someArgs.argi2 = n;
            NotificationAssistantService.this.mHandler.obtainMessage(6, someArgs).sendToTarget();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Source {
    }

}

