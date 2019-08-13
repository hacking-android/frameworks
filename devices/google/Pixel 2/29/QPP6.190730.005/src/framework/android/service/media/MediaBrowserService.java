/*
 * Decompiled with CFR 0.145.
 */
package android.service.media;

import android.annotation.UnsupportedAppUsage;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.media.browse.MediaBrowser;
import android.media.browse.MediaBrowserUtils;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.service.media.IMediaBrowserService;
import android.service.media.IMediaBrowserServiceCallbacks;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class MediaBrowserService
extends Service {
    private static final boolean DBG = false;
    @UnsupportedAppUsage
    public static final String KEY_MEDIA_ITEM = "media_item";
    private static final int RESULT_ERROR = -1;
    private static final int RESULT_FLAG_ON_LOAD_ITEM_NOT_IMPLEMENTED = 2;
    private static final int RESULT_FLAG_OPTION_NOT_HANDLED = 1;
    private static final int RESULT_OK = 0;
    public static final String SERVICE_INTERFACE = "android.media.browse.MediaBrowserService";
    private static final String TAG = "MediaBrowserService";
    private ServiceBinder mBinder;
    private final ArrayMap<IBinder, ConnectionRecord> mConnections = new ArrayMap();
    private ConnectionRecord mCurConnection;
    private final Handler mHandler = new Handler();
    MediaSession.Token mSession;

    private void addSubscription(String string2, ConnectionRecord connectionRecord, IBinder iBinder, Bundle bundle) {
        List<Pair<IBinder, Bundle>> object2;
        List<Pair<IBinder, Bundle>> list = object2 = connectionRecord.subscriptions.get(string2);
        if (object2 == null) {
            list = new ArrayList<Pair<IBinder, Bundle>>();
        }
        for (Pair<IBinder, Bundle> pair : list) {
            if (iBinder != pair.first || !MediaBrowserUtils.areSameOptions(bundle, (Bundle)pair.second)) continue;
            return;
        }
        list.add(new Pair<IBinder, Bundle>(iBinder, bundle));
        connectionRecord.subscriptions.put(string2, list);
        this.performLoadChildren(string2, connectionRecord, bundle);
    }

    private List<MediaBrowser.MediaItem> applyOptions(List<MediaBrowser.MediaItem> list, Bundle bundle) {
        if (list == null) {
            return null;
        }
        int n = bundle.getInt("android.media.browse.extra.PAGE", -1);
        int n2 = bundle.getInt("android.media.browse.extra.PAGE_SIZE", -1);
        if (n == -1 && n2 == -1) {
            return list;
        }
        int n3 = n2 * n;
        int n4 = n3 + n2;
        if (n >= 0 && n2 >= 1 && n3 < list.size()) {
            n2 = n4;
            if (n4 > list.size()) {
                n2 = list.size();
            }
            return list.subList(n3, n2);
        }
        return Collections.EMPTY_LIST;
    }

    private boolean isValidPackage(String string2, int n) {
        if (string2 == null) {
            return false;
        }
        String[] arrstring = this.getPackageManager().getPackagesForUid(n);
        int n2 = arrstring.length;
        for (n = 0; n < n2; ++n) {
            if (!arrstring[n].equals(string2)) continue;
            return true;
        }
        return false;
    }

    private void notifyChildrenChangedInternal(final String string2, final Bundle bundle) {
        if (string2 != null) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    for (Object object : MediaBrowserService.this.mConnections.keySet()) {
                        object = (ConnectionRecord)MediaBrowserService.this.mConnections.get(object);
                        List<Pair<IBinder, Bundle>> list = ((ConnectionRecord)object).subscriptions.get(string2);
                        if (list == null) continue;
                        for (Pair<IBinder, Bundle> pair : list) {
                            if (!MediaBrowserUtils.hasDuplicatedItems(bundle, (Bundle)pair.second)) continue;
                            MediaBrowserService.this.performLoadChildren(string2, (ConnectionRecord)object, (Bundle)pair.second);
                        }
                    }
                }
            });
            return;
        }
        throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
    }

    private void performLoadChildren(final String string2, final ConnectionRecord connectionRecord, Bundle object) {
        Result<List<MediaBrowser.MediaItem>> result = new Result<List<MediaBrowser.MediaItem>>((Object)string2, (Bundle)object){
            final /* synthetic */ Bundle val$options;
            {
                this.val$options = bundle;
                super(object);
            }

            @Override
            void onResultSent(List<MediaBrowser.MediaItem> object, int n) {
                if (MediaBrowserService.this.mConnections.get(connectionRecord.callbacks.asBinder()) != connectionRecord) {
                    return;
                }
                if ((n & 1) != 0) {
                    object = MediaBrowserService.this.applyOptions(object, this.val$options);
                }
                object = object == null ? null : new ParceledListSlice<MediaBrowser.MediaItem>((List<MediaBrowser.MediaItem>)object);
                try {
                    connectionRecord.callbacks.onLoadChildrenWithOptions(string2, (ParceledListSlice)object, this.val$options);
                }
                catch (RemoteException remoteException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Calling onLoadChildren() failed for id=");
                    stringBuilder.append(string2);
                    stringBuilder.append(" package=");
                    stringBuilder.append(connectionRecord.pkg);
                    Log.w(MediaBrowserService.TAG, stringBuilder.toString());
                }
            }
        };
        this.mCurConnection = connectionRecord;
        if (object == null) {
            this.onLoadChildren(string2, result);
        } else {
            this.onLoadChildren(string2, result, (Bundle)object);
        }
        this.mCurConnection = null;
        if (result.isDone()) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("onLoadChildren must call detach() or sendResult() before returning for package=");
        ((StringBuilder)object).append(connectionRecord.pkg);
        ((StringBuilder)object).append(" id=");
        ((StringBuilder)object).append(string2);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    private void performLoadItem(String string2, ConnectionRecord object, ResultReceiver object2) {
        object2 = new Result<MediaBrowser.MediaItem>((Object)string2, (ConnectionRecord)object, string2, (ResultReceiver)object2){
            final /* synthetic */ ConnectionRecord val$connection;
            final /* synthetic */ String val$itemId;
            final /* synthetic */ ResultReceiver val$receiver;
            {
                this.val$connection = connectionRecord;
                this.val$itemId = string2;
                this.val$receiver = resultReceiver;
                super(object);
            }

            @Override
            void onResultSent(MediaBrowser.MediaItem mediaItem, int n) {
                if (MediaBrowserService.this.mConnections.get(this.val$connection.callbacks.asBinder()) != this.val$connection) {
                    return;
                }
                if ((n & 2) != 0) {
                    this.val$receiver.send(-1, null);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable(MediaBrowserService.KEY_MEDIA_ITEM, mediaItem);
                this.val$receiver.send(0, bundle);
            }
        };
        this.mCurConnection = object;
        this.onLoadItem(string2, (Result<MediaBrowser.MediaItem>)object2);
        this.mCurConnection = null;
        if (((Result)object2).isDone()) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("onLoadItem must call detach() or sendResult() before returning for id=");
        ((StringBuilder)object).append(string2);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    private boolean removeSubscription(String string2, ConnectionRecord connectionRecord, IBinder iBinder) {
        if (iBinder == null) {
            boolean bl = connectionRecord.subscriptions.remove(string2) != null;
            return bl;
        }
        boolean bl = false;
        boolean bl2 = false;
        List<Pair<IBinder, Bundle>> list = connectionRecord.subscriptions.get(string2);
        if (list != null) {
            Iterator<Pair<IBinder, Bundle>> iterator = list.iterator();
            while (iterator.hasNext()) {
                if (iBinder != iterator.next().first) continue;
                bl2 = true;
                iterator.remove();
            }
            bl = bl2;
            if (list.size() == 0) {
                connectionRecord.subscriptions.remove(string2);
                bl = bl2;
            }
        }
        return bl;
    }

    @Override
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
    }

    public final Bundle getBrowserRootHints() {
        Object object = this.mCurConnection;
        if (object != null) {
            object = ((ConnectionRecord)object).rootHints == null ? null : new Bundle(this.mCurConnection.rootHints);
            return object;
        }
        throw new IllegalStateException("This should be called inside of onGetRoot or onLoadChildren or onLoadItem methods");
    }

    public final MediaSessionManager.RemoteUserInfo getCurrentBrowserInfo() {
        ConnectionRecord connectionRecord = this.mCurConnection;
        if (connectionRecord != null) {
            return new MediaSessionManager.RemoteUserInfo(connectionRecord.pkg, this.mCurConnection.pid, this.mCurConnection.uid);
        }
        throw new IllegalStateException("This should be called inside of onGetRoot or onLoadChildren or onLoadItem methods");
    }

    public MediaSession.Token getSessionToken() {
        return this.mSession;
    }

    public void notifyChildrenChanged(String string2) {
        this.notifyChildrenChangedInternal(string2, null);
    }

    public void notifyChildrenChanged(String string2, Bundle bundle) {
        if (bundle != null) {
            this.notifyChildrenChangedInternal(string2, bundle);
            return;
        }
        throw new IllegalArgumentException("options cannot be null in notifyChildrenChanged");
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mBinder;
        }
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mBinder = new ServiceBinder();
    }

    public abstract BrowserRoot onGetRoot(String var1, int var2, Bundle var3);

    public abstract void onLoadChildren(String var1, Result<List<MediaBrowser.MediaItem>> var2);

    public void onLoadChildren(String string2, Result<List<MediaBrowser.MediaItem>> result, Bundle bundle) {
        result.setFlags(1);
        this.onLoadChildren(string2, result);
    }

    public void onLoadItem(String string2, Result<MediaBrowser.MediaItem> result) {
        result.setFlags(2);
        result.sendResult(null);
    }

    public void setSessionToken(final MediaSession.Token token) {
        if (token != null) {
            if (this.mSession == null) {
                this.mSession = token;
                this.mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        Iterator iterator = MediaBrowserService.this.mConnections.values().iterator();
                        while (iterator.hasNext()) {
                            ConnectionRecord connectionRecord = (ConnectionRecord)iterator.next();
                            try {
                                connectionRecord.callbacks.onConnect(connectionRecord.root.getRootId(), token, connectionRecord.root.getExtras());
                            }
                            catch (RemoteException remoteException) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Connection for ");
                                stringBuilder.append(connectionRecord.pkg);
                                stringBuilder.append(" is no longer valid.");
                                Log.w(MediaBrowserService.TAG, stringBuilder.toString());
                                iterator.remove();
                            }
                        }
                    }
                });
                return;
            }
            throw new IllegalStateException("The session token has already been set.");
        }
        throw new IllegalArgumentException("Session token may not be null.");
    }

    public static final class BrowserRoot {
        public static final String EXTRA_OFFLINE = "android.service.media.extra.OFFLINE";
        public static final String EXTRA_RECENT = "android.service.media.extra.RECENT";
        public static final String EXTRA_SUGGESTED = "android.service.media.extra.SUGGESTED";
        private final Bundle mExtras;
        private final String mRootId;

        public BrowserRoot(String string2, Bundle bundle) {
            if (string2 != null) {
                this.mRootId = string2;
                this.mExtras = bundle;
                return;
            }
            throw new IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead.");
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public String getRootId() {
            return this.mRootId;
        }
    }

    private class ConnectionRecord
    implements IBinder.DeathRecipient {
        IMediaBrowserServiceCallbacks callbacks;
        int pid;
        String pkg;
        BrowserRoot root;
        Bundle rootHints;
        HashMap<String, List<Pair<IBinder, Bundle>>> subscriptions = new HashMap();
        int uid;

        private ConnectionRecord() {
        }

        @Override
        public void binderDied() {
            MediaBrowserService.this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    MediaBrowserService.this.mConnections.remove(ConnectionRecord.this.callbacks.asBinder());
                }
            });
        }

    }

    public class Result<T> {
        private Object mDebug;
        private boolean mDetachCalled;
        @UnsupportedAppUsage
        private int mFlags;
        private boolean mSendResultCalled;

        Result(Object object) {
            this.mDebug = object;
        }

        public void detach() {
            if (!this.mDetachCalled) {
                if (!this.mSendResultCalled) {
                    this.mDetachCalled = true;
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("detach() called when sendResult() had already been called for: ");
                stringBuilder.append(this.mDebug);
                throw new IllegalStateException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("detach() called when detach() had already been called for: ");
            stringBuilder.append(this.mDebug);
            throw new IllegalStateException(stringBuilder.toString());
        }

        boolean isDone() {
            boolean bl = this.mDetachCalled || this.mSendResultCalled;
            return bl;
        }

        void onResultSent(T t, int n) {
        }

        public void sendResult(T object) {
            if (!this.mSendResultCalled) {
                this.mSendResultCalled = true;
                this.onResultSent(object, this.mFlags);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("sendResult() called twice for: ");
            ((StringBuilder)object).append(this.mDebug);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }

        void setFlags(int n) {
            this.mFlags = n;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    private static @interface ResultFlags {
    }

    private class ServiceBinder
    extends IMediaBrowserService.Stub {
        private ServiceBinder() {
        }

        @Override
        public void addSubscription(final String string2, final IBinder iBinder, final Bundle bundle, final IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
            MediaBrowserService.this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    Object object = iMediaBrowserServiceCallbacks.asBinder();
                    object = (ConnectionRecord)MediaBrowserService.this.mConnections.get(object);
                    if (object == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("addSubscription for callback that isn't registered id=");
                        ((StringBuilder)object).append(string2);
                        Log.w(MediaBrowserService.TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    MediaBrowserService.this.addSubscription(string2, (ConnectionRecord)object, iBinder, bundle);
                }
            });
        }

        @Override
        public void addSubscriptionDeprecated(String string2, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
        }

        @Override
        public void connect(final String string2, Bundle object, final IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
            final int n = Binder.getCallingPid();
            final int n2 = Binder.getCallingUid();
            if (MediaBrowserService.this.isValidPackage(string2, n2)) {
                MediaBrowserService.this.mHandler.post(new Runnable((Bundle)object){
                    final /* synthetic */ Bundle val$rootHints;
                    {
                        this.val$rootHints = bundle;
                    }

                    @Override
                    public void run() {
                        Object object = iMediaBrowserServiceCallbacks.asBinder();
                        MediaBrowserService.this.mConnections.remove(object);
                        ConnectionRecord connectionRecord = new ConnectionRecord();
                        connectionRecord.pkg = string2;
                        connectionRecord.pid = n;
                        connectionRecord.uid = n2;
                        connectionRecord.rootHints = this.val$rootHints;
                        connectionRecord.callbacks = iMediaBrowserServiceCallbacks;
                        MediaBrowserService.this.mCurConnection = connectionRecord;
                        connectionRecord.root = MediaBrowserService.this.onGetRoot(string2, n2, this.val$rootHints);
                        MediaBrowserService.this.mCurConnection = null;
                        if (connectionRecord.root == null) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("No root for client ");
                            ((StringBuilder)object).append(string2);
                            ((StringBuilder)object).append(" from service ");
                            ((StringBuilder)object).append(this.getClass().getName());
                            Log.i(MediaBrowserService.TAG, ((StringBuilder)object).toString());
                            try {
                                iMediaBrowserServiceCallbacks.onConnectFailed();
                            }
                            catch (RemoteException remoteException) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Calling onConnectFailed() failed. Ignoring. pkg=");
                                stringBuilder.append(string2);
                                Log.w(MediaBrowserService.TAG, stringBuilder.toString());
                            }
                        } else {
                            try {
                                MediaBrowserService.this.mConnections.put(object, connectionRecord);
                                object.linkToDeath(connectionRecord, 0);
                                if (MediaBrowserService.this.mSession != null) {
                                    iMediaBrowserServiceCallbacks.onConnect(connectionRecord.root.getRootId(), MediaBrowserService.this.mSession, connectionRecord.root.getExtras());
                                }
                            }
                            catch (RemoteException remoteException) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Calling onConnect() failed. Dropping client. pkg=");
                                stringBuilder.append(string2);
                                Log.w(MediaBrowserService.TAG, stringBuilder.toString());
                                MediaBrowserService.this.mConnections.remove(object);
                            }
                        }
                    }
                });
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Package/uid mismatch: uid=");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" package=");
            ((StringBuilder)object).append(string2);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        @Override
        public void disconnect(final IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
            MediaBrowserService.this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    Object object = iMediaBrowserServiceCallbacks.asBinder();
                    object = (ConnectionRecord)MediaBrowserService.this.mConnections.remove(object);
                    if (object != null) {
                        ((ConnectionRecord)object).callbacks.asBinder().unlinkToDeath((IBinder.DeathRecipient)object, 0);
                    }
                }
            });
        }

        @Override
        public void getMediaItem(final String string2, final ResultReceiver resultReceiver, final IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
            MediaBrowserService.this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    Object object = iMediaBrowserServiceCallbacks.asBinder();
                    object = (ConnectionRecord)MediaBrowserService.this.mConnections.get(object);
                    if (object == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("getMediaItem for callback that isn't registered id=");
                        ((StringBuilder)object).append(string2);
                        Log.w(MediaBrowserService.TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    MediaBrowserService.this.performLoadItem(string2, (ConnectionRecord)object, resultReceiver);
                }
            });
        }

        @Override
        public void removeSubscription(final String string2, final IBinder iBinder, final IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
            MediaBrowserService.this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    Object object = iMediaBrowserServiceCallbacks.asBinder();
                    object = (ConnectionRecord)MediaBrowserService.this.mConnections.get(object);
                    if (object == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("removeSubscription for callback that isn't registered id=");
                        ((StringBuilder)object).append(string2);
                        Log.w(MediaBrowserService.TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    if (!MediaBrowserService.this.removeSubscription(string2, (ConnectionRecord)object, iBinder)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("removeSubscription called for ");
                        ((StringBuilder)object).append(string2);
                        ((StringBuilder)object).append(" which is not subscribed");
                        Log.w(MediaBrowserService.TAG, ((StringBuilder)object).toString());
                    }
                }
            });
        }

        @Override
        public void removeSubscriptionDeprecated(String string2, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
        }

    }

}

