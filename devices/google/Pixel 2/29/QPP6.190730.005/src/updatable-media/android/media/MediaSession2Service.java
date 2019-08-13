/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.NotificationManager
 *  android.app.Service
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.media.session.MediaSessionManager
 *  android.media.session.MediaSessionManager$RemoteUserInfo
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.util.ArrayMap
 *  android.util.Log
 */
package android.media;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Controller2Link;
import android.media.IMediaSession2Service;
import android.media.MediaSession2;
import android.media._$$Lambda$MediaSession2Service$MediaSession2ServiceStub$lmqGgEmF_eznMgcMPXx8eX7uOKs;
import android.media.session.MediaSessionManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.ArrayMap;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class MediaSession2Service
extends Service {
    private static final boolean DEBUG = Log.isLoggable((String)"MediaSession2Service", (int)3);
    public static final String SERVICE_INTERFACE = "android.media.MediaSession2Service";
    private static final String TAG = "MediaSession2Service";
    private final MediaSession2.ForegroundServiceEventCallback mForegroundServiceEventCallback = new MediaSession2.ForegroundServiceEventCallback(){

        @Override
        public void onPlaybackActiveChanged(MediaSession2 mediaSession2, boolean bl) {
            MediaSession2Service.this.onPlaybackActiveChanged(mediaSession2, bl);
        }

        @Override
        public void onSessionClosed(MediaSession2 mediaSession2) {
            MediaSession2Service.this.removeSession(mediaSession2);
        }
    };
    private final Object mLock = new Object();
    private MediaSessionManager mMediaSessionManager;
    private NotificationManager mNotificationManager;
    private Map<MediaSession2, MediaNotification> mNotifications = new ArrayMap();
    private Map<String, MediaSession2> mSessions = new ArrayMap();
    private Intent mStartSelfIntent;
    private MediaSession2ServiceStub mStub;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void addSession(MediaSession2 mediaSession2) {
        if (mediaSession2 == null) {
            throw new IllegalArgumentException("session shouldn't be null");
        }
        if (mediaSession2.isClosed()) {
            throw new IllegalArgumentException("session is already closed");
        }
        Object object = this.mLock;
        synchronized (object) {
            MediaSession2 mediaSession22 = this.mSessions.get(mediaSession2.getId());
            if (mediaSession22 == null) {
                this.mSessions.put(mediaSession2.getId(), mediaSession2);
                mediaSession2.setForegroundServiceEventCallback(this.mForegroundServiceEventCallback);
                return;
            }
            if (mediaSession22 != mediaSession2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Session ID should be unique, ID=");
                stringBuilder.append(mediaSession2.getId());
                stringBuilder.append(", previous=");
                stringBuilder.append(mediaSession22);
                stringBuilder.append(", session=");
                stringBuilder.append(mediaSession2);
                Log.w((String)TAG, (String)stringBuilder.toString());
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    MediaSessionManager getMediaSessionManager() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mMediaSessionManager;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final List<MediaSession2> getSessions() {
        ArrayList<MediaSession2> arrayList = new ArrayList<MediaSession2>();
        Object object = this.mLock;
        synchronized (object) {
            arrayList.addAll(this.mSessions.values());
            return arrayList;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public IBinder onBind(Intent object) {
        if (!SERVICE_INTERFACE.equals(object.getAction())) return null;
        object = this.mLock;
        synchronized (object) {
            return this.mStub;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onCreate() {
        super.onCreate();
        Object object = this.mLock;
        synchronized (object) {
            MediaSession2ServiceStub mediaSession2ServiceStub;
            this.mStub = mediaSession2ServiceStub = new MediaSession2ServiceStub(this);
            mediaSession2ServiceStub = new Intent((Context)this, ((Object)((Object)this)).getClass());
            this.mStartSelfIntent = mediaSession2ServiceStub;
            this.mNotificationManager = (NotificationManager)this.getSystemService("notification");
            this.mMediaSessionManager = (MediaSessionManager)this.getSystemService("media_session");
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onDestroy() {
        super.onDestroy();
        Object object = this.mLock;
        synchronized (object) {
            Iterator<MediaSession2> iterator = this.getSessions().iterator();
            do {
                if (!iterator.hasNext()) {
                    this.mSessions.clear();
                    this.mNotifications.clear();
                    // MONITOREXIT [2, 3, 4] lbl9 : MonitorExitStatement: MONITOREXIT : var1_1
                    this.mStub.close();
                    return;
                }
                this.removeSession(iterator.next());
            } while (true);
        }
    }

    public abstract MediaSession2 onGetSession(MediaSession2.ControllerInfo var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void onPlaybackActiveChanged(MediaSession2 mediaSession2, boolean bl) {
        MediaNotification mediaNotification = this.onUpdateNotification(mediaSession2);
        if (mediaNotification == null) {
            return;
        }
        Object object = this.mLock;
        synchronized (object) {
            this.mNotifications.put(mediaSession2, mediaNotification);
        }
        int n = mediaNotification.getNotificationId();
        mediaSession2 = mediaNotification.getNotification();
        if (!bl) {
            this.mNotificationManager.notify(n, (Notification)mediaSession2);
            return;
        }
        this.startForegroundService(this.mStartSelfIntent);
        this.startForeground(n, (Notification)mediaSession2);
    }

    public abstract MediaNotification onUpdateNotification(MediaSession2 var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void removeSession(MediaSession2 mediaSession2) {
        MediaNotification mediaNotification;
        if (mediaSession2 == null) {
            throw new IllegalArgumentException("session shouldn't be null");
        }
        Object object = this.mLock;
        synchronized (object) {
            if (this.mSessions.get(mediaSession2.getId()) != mediaSession2) {
                return;
            }
            this.mSessions.remove(mediaSession2.getId());
            mediaNotification = this.mNotifications.remove(mediaSession2);
        }
        mediaSession2.setForegroundServiceEventCallback(null);
        if (mediaNotification != null) {
            this.mNotificationManager.cancel(mediaNotification.getNotificationId());
        }
        if (this.getSessions().isEmpty()) {
            this.stopForeground(false);
        }
    }

    public static class MediaNotification {
        private final Notification mNotification;
        private final int mNotificationId;

        public MediaNotification(int n, Notification notification) {
            if (notification != null) {
                this.mNotificationId = n;
                this.mNotification = notification;
                return;
            }
            throw new IllegalArgumentException("notification shouldn't be null");
        }

        public Notification getNotification() {
            return this.mNotification;
        }

        public int getNotificationId() {
            return this.mNotificationId;
        }
    }

    private static final class MediaSession2ServiceStub
    extends IMediaSession2Service.Stub
    implements AutoCloseable {
        final Handler mHandler;
        final WeakReference<MediaSession2Service> mService;

        MediaSession2ServiceStub(MediaSession2Service mediaSession2Service) {
            this.mService = new WeakReference<MediaSession2Service>(mediaSession2Service);
            this.mHandler = new Handler(mediaSession2Service.getMainLooper());
        }

        @Override
        public void close() {
            this.mHandler.removeCallbacksAndMessages(null);
            this.mService.clear();
        }

        @Override
        public void connect(Controller2Link controller2Link, int n, Bundle bundle) {
            if (this.mService.get() == null) {
                if (DEBUG) {
                    Log.d((String)MediaSession2Service.TAG, (String)"Service is already destroyed");
                }
                return;
            }
            if (controller2Link != null && bundle != null) {
                int n2 = Binder.getCallingPid();
                int n3 = Binder.getCallingUid();
                long l = Binder.clearCallingIdentity();
                try {
                    Handler handler = this.mHandler;
                    _$$Lambda$MediaSession2Service$MediaSession2ServiceStub$lmqGgEmF_eznMgcMPXx8eX7uOKs _$$Lambda$MediaSession2Service$MediaSession2ServiceStub$lmqGgEmF_eznMgcMPXx8eX7uOKs = new _$$Lambda$MediaSession2Service$MediaSession2ServiceStub$lmqGgEmF_eznMgcMPXx8eX7uOKs(this, bundle, n2, n3, controller2Link, n);
                    handler.post((Runnable)_$$Lambda$MediaSession2Service$MediaSession2ServiceStub$lmqGgEmF_eznMgcMPXx8eX7uOKs);
                    return;
                }
                finally {
                    Binder.restoreCallingIdentity((long)l);
                }
            }
            if (DEBUG) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Ignoring calls with illegal arguments, caller=");
                stringBuilder.append(controller2Link);
                stringBuilder.append(", connectionRequest=");
                stringBuilder.append((Object)bundle);
                Log.d((String)MediaSession2Service.TAG, (String)stringBuilder.toString());
            }
        }

        /*
         * Loose catch block
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public /* synthetic */ void lambda$connect$0$MediaSession2Service$MediaSession2ServiceStub(Bundle object, int n, int n2, Controller2Link controller2Link, int n3) {
            void var1_12;
            block35 : {
                int n4;
                block36 : {
                    MediaSession2Service mediaSession2Service;
                    Object object2;
                    int n6;
                    int n5;
                    block34 : {
                        block33 : {
                            n5 = 1;
                            n6 = 1;
                            mediaSession2Service = (MediaSession2Service)((Object)this.mService.get());
                            if (mediaSession2Service != null) break block33;
                            if (DEBUG) {
                                Log.d((String)MediaSession2Service.TAG, (String)"Service isn't available");
                            }
                            if (!true) return;
                            if (DEBUG) {
                                Log.d((String)MediaSession2Service.TAG, (String)"Notifying the controller of its disconnection");
                            }
                            try {
                                controller2Link.notifyDisconnected(0);
                                return;
                            }
                            catch (RuntimeException runtimeException) {
                                // empty catch block
                            }
                            return;
                        }
                        object2 = object.getString("android.media.key.PACKAGE_NAME");
                        int n7 = n == 0 ? object.getInt("android.media.key.PID") : n;
                        n4 = n5;
                        MediaSessionManager.RemoteUserInfo remoteUserInfo = new MediaSessionManager.RemoteUserInfo((String)object2, n7, n2);
                        n4 = n5;
                        object2 = object.getBundle("android.media.key.CONNECTION_HINTS");
                        if (object2 == null) {
                            n4 = n5;
                            Log.w((String)MediaSession2Service.TAG, (String)"connectionHints shouldn't be null.");
                            n4 = n5;
                            object2 = Bundle.EMPTY;
                        } else {
                            n4 = n5;
                            if (MediaSession2.hasCustomParcelable((Bundle)object2)) {
                                n4 = n5;
                                Log.w((String)MediaSession2Service.TAG, (String)"connectionHints contain custom parcelable. Ignoring.");
                                n4 = n5;
                                object2 = Bundle.EMPTY;
                            }
                        }
                        n4 = n5;
                        n4 = n5;
                        MediaSession2.ControllerInfo controllerInfo = new MediaSession2.ControllerInfo(remoteUserInfo, mediaSession2Service.getMediaSessionManager().isTrustedForMediaControl(remoteUserInfo), controller2Link, (Bundle)object2);
                        n4 = n5;
                        if (DEBUG) {
                            n4 = n5;
                            n4 = n5;
                            object2 = new StringBuilder();
                            n4 = n5;
                            ((StringBuilder)object2).append("Handling incoming connection request from the controller=");
                            n4 = n5;
                            ((StringBuilder)object2).append(controllerInfo);
                            n4 = n5;
                            Log.d((String)MediaSession2Service.TAG, (String)((StringBuilder)object2).toString());
                        }
                        n4 = n5;
                        object2 = mediaSession2Service.onGetSession(controllerInfo);
                        if (object2 != null) break block34;
                        n4 = n5;
                        if (DEBUG) {
                            n4 = n5;
                            n4 = n5;
                            object = new StringBuilder();
                            n4 = n5;
                            ((StringBuilder)object).append("Rejecting incoming connection request from the controller=");
                            n4 = n5;
                            ((StringBuilder)object).append(controllerInfo);
                            n4 = n5;
                            Log.d((String)MediaSession2Service.TAG, (String)((StringBuilder)object).toString());
                        }
                        if (!true) return;
                        if (DEBUG) {
                            Log.d((String)MediaSession2Service.TAG, (String)"Notifying the controller of its disconnection");
                        }
                        try {
                            controller2Link.notifyDisconnected(0);
                            return;
                        }
                        catch (RuntimeException runtimeException) {
                            // empty catch block
                        }
                        return;
                    }
                    n4 = n5;
                    mediaSession2Service.addSession((MediaSession2)object2);
                    n4 = 0;
                    ((MediaSession2)object2).onConnect(controller2Link, n, n2, n3, (Bundle)object);
                    if (!false) return;
                    if (DEBUG) {
                        Log.d((String)MediaSession2Service.TAG, (String)"Notifying the controller of its disconnection");
                    }
                    try {
                        controller2Link.notifyDisconnected(0);
                        return;
                    }
                    catch (RuntimeException runtimeException) {
                        return;
                    }
                    catch (Throwable throwable) {
                        n = n4;
                        break block35;
                    }
                    catch (Exception exception) {
                        n = 0;
                        break block36;
                    }
                    catch (Exception exception) {
                        n = n6;
                        break block36;
                    }
                    catch (Throwable throwable) {
                        n = 1;
                        break block35;
                    }
                    catch (Exception exception) {
                        n = n6;
                    }
                }
                n4 = n;
                Log.w((String)MediaSession2Service.TAG, (String)"Failed to add a session to session service", (Throwable)object);
                if (n == 0) return;
                if (DEBUG) {
                    Log.d((String)MediaSession2Service.TAG, (String)"Notifying the controller of its disconnection");
                }
                try {
                    controller2Link.notifyDisconnected(0);
                    return;
                }
                catch (RuntimeException runtimeException) {
                    return;
                }
                catch (Throwable throwable) {
                    n = n4;
                }
            }
            if (n == 0) throw var1_12;
            if (DEBUG) {
                Log.d((String)MediaSession2Service.TAG, (String)"Notifying the controller of its disconnection");
            }
            try {
                controller2Link.notifyDisconnected(0);
                throw var1_12;
            }
            catch (RuntimeException runtimeException) {
                // empty catch block
            }
            throw var1_12;
        }
    }

}

