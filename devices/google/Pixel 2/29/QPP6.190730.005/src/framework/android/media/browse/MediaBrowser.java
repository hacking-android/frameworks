/*
 * Decompiled with CFR 0.145.
 */
package android.media.browse;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ParceledListSlice;
import android.media.MediaDescription;
import android.media.browse.MediaBrowserUtils;
import android.media.session.MediaSession;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.service.media.IMediaBrowserService;
import android.service.media.IMediaBrowserServiceCallbacks;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class MediaBrowser {
    private static final int CONNECT_STATE_CONNECTED = 3;
    private static final int CONNECT_STATE_CONNECTING = 2;
    private static final int CONNECT_STATE_DISCONNECTED = 1;
    private static final int CONNECT_STATE_DISCONNECTING = 0;
    private static final int CONNECT_STATE_SUSPENDED = 4;
    private static final boolean DBG = false;
    public static final String EXTRA_PAGE = "android.media.browse.extra.PAGE";
    public static final String EXTRA_PAGE_SIZE = "android.media.browse.extra.PAGE_SIZE";
    private static final String TAG = "MediaBrowser";
    private final ConnectionCallback mCallback;
    private final Context mContext;
    private volatile Bundle mExtras;
    private final Handler mHandler = new Handler();
    private volatile MediaSession.Token mMediaSessionToken;
    private final Bundle mRootHints;
    private volatile String mRootId;
    private IMediaBrowserService mServiceBinder;
    private IMediaBrowserServiceCallbacks mServiceCallbacks;
    private final ComponentName mServiceComponent;
    private MediaServiceConnection mServiceConnection;
    private volatile int mState = 1;
    private final ArrayMap<String, Subscription> mSubscriptions = new ArrayMap();

    public MediaBrowser(Context object, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
        if (object != null) {
            if (componentName != null) {
                if (connectionCallback != null) {
                    this.mContext = object;
                    this.mServiceComponent = componentName;
                    this.mCallback = connectionCallback;
                    object = bundle == null ? null : new Bundle(bundle);
                    this.mRootHints = object;
                    return;
                }
                throw new IllegalArgumentException("connection callback must not be null");
            }
            throw new IllegalArgumentException("service component must not be null");
        }
        throw new IllegalArgumentException("context must not be null");
    }

    private void forceCloseConnection() {
        MediaServiceConnection mediaServiceConnection = this.mServiceConnection;
        if (mediaServiceConnection != null) {
            try {
                this.mContext.unbindService(mediaServiceConnection);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        this.mState = 1;
        this.mServiceConnection = null;
        this.mServiceBinder = null;
        this.mServiceCallbacks = null;
        this.mRootId = null;
        this.mMediaSessionToken = null;
    }

    private ServiceCallbacks getNewServiceCallbacks() {
        return new ServiceCallbacks(this);
    }

    private static String getStateLabel(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("UNKNOWN/");
                            stringBuilder.append(n);
                            return stringBuilder.toString();
                        }
                        return "CONNECT_STATE_SUSPENDED";
                    }
                    return "CONNECT_STATE_CONNECTED";
                }
                return "CONNECT_STATE_CONNECTING";
            }
            return "CONNECT_STATE_DISCONNECTED";
        }
        return "CONNECT_STATE_DISCONNECTING";
    }

    private boolean isCurrent(IMediaBrowserServiceCallbacks object, String string2) {
        if (this.mServiceCallbacks == object && this.mState != 0 && this.mState != 1) {
            return true;
        }
        if (this.mState != 0 && this.mState != 1) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" for ");
            ((StringBuilder)object).append(this.mServiceComponent);
            ((StringBuilder)object).append(" with mServiceConnection=");
            ((StringBuilder)object).append(this.mServiceCallbacks);
            ((StringBuilder)object).append(" this=");
            ((StringBuilder)object).append(this);
            Log.i(TAG, ((StringBuilder)object).toString());
        }
        return false;
    }

    private void onConnectionFailed(final IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
        this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onConnectFailed for ");
                stringBuilder.append(MediaBrowser.this.mServiceComponent);
                Log.e(MediaBrowser.TAG, stringBuilder.toString());
                if (!MediaBrowser.this.isCurrent(iMediaBrowserServiceCallbacks, "onConnectFailed")) {
                    return;
                }
                if (MediaBrowser.this.mState != 2) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("onConnect from service while mState=");
                    stringBuilder.append(MediaBrowser.getStateLabel(MediaBrowser.this.mState));
                    stringBuilder.append("... ignoring");
                    Log.w(MediaBrowser.TAG, stringBuilder.toString());
                    return;
                }
                MediaBrowser.this.forceCloseConnection();
                MediaBrowser.this.mCallback.onConnectionFailed();
            }
        });
    }

    private void onLoadChildren(final IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks, final String string2, final ParceledListSlice parceledListSlice, final Bundle bundle) {
        this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                SubscriptionCallback subscriptionCallback;
                if (!MediaBrowser.this.isCurrent(iMediaBrowserServiceCallbacks, "onLoadChildren")) {
                    return;
                }
                Object object = (Subscription)MediaBrowser.this.mSubscriptions.get(string2);
                if (object != null && (subscriptionCallback = ((Subscription)object).getCallback(MediaBrowser.this.mContext, bundle)) != null) {
                    object = parceledListSlice;
                    object = object == null ? null : ((ParceledListSlice)object).getList();
                    Bundle bundle2 = bundle;
                    if (bundle2 == null) {
                        if (object == null) {
                            subscriptionCallback.onError(string2);
                        } else {
                            subscriptionCallback.onChildrenLoaded(string2, (List<MediaItem>)object);
                        }
                    } else if (object == null) {
                        subscriptionCallback.onError(string2, bundle2);
                    } else {
                        subscriptionCallback.onChildrenLoaded(string2, (List<MediaItem>)object, bundle2);
                    }
                    return;
                }
            }
        });
    }

    private void onServiceConnected(final IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks, final String string2, final MediaSession.Token token, final Bundle bundle) {
        this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                if (!MediaBrowser.this.isCurrent(iMediaBrowserServiceCallbacks, "onConnect")) {
                    return;
                }
                if (MediaBrowser.this.mState != 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onConnect from service while mState=");
                    stringBuilder.append(MediaBrowser.getStateLabel(MediaBrowser.this.mState));
                    stringBuilder.append("... ignoring");
                    Log.w(MediaBrowser.TAG, stringBuilder.toString());
                    return;
                }
                MediaBrowser.this.mRootId = string2;
                MediaBrowser.this.mMediaSessionToken = token;
                MediaBrowser.this.mExtras = bundle;
                MediaBrowser.this.mState = 3;
                MediaBrowser.this.mCallback.onConnected();
                for (Map.Entry entry : MediaBrowser.this.mSubscriptions.entrySet()) {
                    String string22 = (String)entry.getKey();
                    Object object = (Subscription)entry.getValue();
                    List<SubscriptionCallback> object2 = ((Subscription)object).getCallbacks();
                    object = ((Subscription)object).getOptionsList();
                    for (int i = 0; i < object2.size(); ++i) {
                        try {
                            MediaBrowser.this.mServiceBinder.addSubscription(string22, object2.get((int)i).mToken, (Bundle)object.get(i), MediaBrowser.this.mServiceCallbacks);
                            continue;
                        }
                        catch (RemoteException remoteException) {
                            StringBuilder remoteException2 = new StringBuilder();
                            remoteException2.append("addSubscription failed with RemoteException parentId=");
                            remoteException2.append(string22);
                            Log.d(MediaBrowser.TAG, remoteException2.toString());
                        }
                    }
                }
            }
        });
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void subscribeInternal(String var1_1, Bundle var2_2, SubscriptionCallback var3_5) {
        if (TextUtils.isEmpty(var1_1) != false) throw new IllegalArgumentException("parentId cannot be empty.");
        if (var3_5 == null) throw new IllegalArgumentException("callback cannot be null");
        var5_7 = var4_6 = this.mSubscriptions.get(var1_1);
        if (var4_6 == null) {
            var5_7 = new Subscription();
            this.mSubscriptions.put(var1_1, var5_7);
        }
        var5_7.putCallback(this.mContext, var2_2, var3_5);
        if (this.isConnected() == false) return;
        if (var2_2 != null) ** GOTO lbl13
        try {
            this.mServiceBinder.addSubscriptionDeprecated(var1_1, this.mServiceCallbacks);
lbl13: // 2 sources:
            this.mServiceBinder.addSubscription(var1_1, var3_5.mToken, var2_2, this.mServiceCallbacks);
            return;
        }
        catch (RemoteException var2_3) {
            var2_4 = new StringBuilder();
            var2_4.append("addSubscription failed with RemoteException parentId=");
            var2_4.append(var1_1);
            Log.d("MediaBrowser", var2_4.toString());
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void unsubscribeInternal(String var1_1, SubscriptionCallback var2_2) {
        block7 : {
            if (TextUtils.isEmpty(var1_1) != false) throw new IllegalArgumentException("parentId cannot be empty.");
            var3_3 = this.mSubscriptions.get(var1_1);
            if (var3_3 == null) {
                return;
            }
            if (var2_2 != null) ** GOTO lbl11
            try {
                if (this.isConnected()) {
                    this.mServiceBinder.removeSubscriptionDeprecated(var1_1, this.mServiceCallbacks);
                    this.mServiceBinder.removeSubscription(var1_1, null, this.mServiceCallbacks);
                }
                break block7;
lbl11: // 1 sources:
                var4_4 = var3_3.getCallbacks();
                var5_7 = var3_3.getOptionsList();
                for (var6_8 = var4_4.size() - 1; var6_8 >= 0; --var6_8) {
                    if (var4_4.get(var6_8) != var2_2) continue;
                    if (this.isConnected()) {
                        this.mServiceBinder.removeSubscription(var1_1, var2_2.mToken, this.mServiceCallbacks);
                    }
                    var4_4.remove(var6_8);
                    var5_7.remove(var6_8);
                }
            }
            catch (RemoteException var4_5) {
                var4_6 = new StringBuilder();
                var4_6.append("removeSubscription failed with RemoteException parentId=");
                var4_6.append(var1_1);
                Log.d("MediaBrowser", var4_6.toString());
            }
        }
        if (!var3_3.isEmpty()) {
            if (var2_2 != null) return;
        }
        this.mSubscriptions.remove(var1_1);
    }

    public void connect() {
        if (this.mState != 0 && this.mState != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("connect() called while neither disconnecting nor disconnected (state=");
            stringBuilder.append(MediaBrowser.getStateLabel(this.mState));
            stringBuilder.append(")");
            throw new IllegalStateException(stringBuilder.toString());
        }
        this.mState = 2;
        this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                if (MediaBrowser.this.mState == 0) {
                    return;
                }
                MediaBrowser.this.mState = 2;
                if (MediaBrowser.this.mServiceBinder == null) {
                    if (MediaBrowser.this.mServiceCallbacks == null) {
                        Intent intent = new Intent("android.media.browse.MediaBrowserService");
                        intent.setComponent(MediaBrowser.this.mServiceComponent);
                        MediaBrowser mediaBrowser = MediaBrowser.this;
                        mediaBrowser.mServiceConnection = mediaBrowser.new MediaServiceConnection();
                        boolean bl = false;
                        try {
                            boolean bl2;
                            bl = bl2 = MediaBrowser.this.mContext.bindService(intent, MediaBrowser.this.mServiceConnection, 1);
                        }
                        catch (Exception exception) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Failed binding to service ");
                            stringBuilder.append(MediaBrowser.this.mServiceComponent);
                            Log.e(MediaBrowser.TAG, stringBuilder.toString());
                        }
                        if (!bl) {
                            MediaBrowser.this.forceCloseConnection();
                            MediaBrowser.this.mCallback.onConnectionFailed();
                        }
                        return;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("mServiceCallbacks should be null. Instead it is ");
                    stringBuilder.append(MediaBrowser.this.mServiceCallbacks);
                    throw new RuntimeException(stringBuilder.toString());
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("mServiceBinder should be null. Instead it is ");
                stringBuilder.append(MediaBrowser.this.mServiceBinder);
                throw new RuntimeException(stringBuilder.toString());
            }
        });
    }

    public void disconnect() {
        this.mState = 0;
        this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                if (MediaBrowser.this.mServiceCallbacks != null) {
                    try {
                        MediaBrowser.this.mServiceBinder.disconnect(MediaBrowser.this.mServiceCallbacks);
                    }
                    catch (RemoteException remoteException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("RemoteException during connect for ");
                        stringBuilder.append(MediaBrowser.this.mServiceComponent);
                        Log.w(MediaBrowser.TAG, stringBuilder.toString());
                    }
                }
                int n = MediaBrowser.this.mState;
                MediaBrowser.this.forceCloseConnection();
                if (n != 0) {
                    MediaBrowser.this.mState = n;
                }
            }
        });
    }

    void dump() {
        Log.d(TAG, "MediaBrowser...");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  mServiceComponent=");
        stringBuilder.append(this.mServiceComponent);
        Log.d(TAG, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  mCallback=");
        stringBuilder.append(this.mCallback);
        Log.d(TAG, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  mRootHints=");
        stringBuilder.append(this.mRootHints);
        Log.d(TAG, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  mState=");
        stringBuilder.append(MediaBrowser.getStateLabel(this.mState));
        Log.d(TAG, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  mServiceConnection=");
        stringBuilder.append(this.mServiceConnection);
        Log.d(TAG, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  mServiceBinder=");
        stringBuilder.append(this.mServiceBinder);
        Log.d(TAG, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  mServiceCallbacks=");
        stringBuilder.append(this.mServiceCallbacks);
        Log.d(TAG, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  mRootId=");
        stringBuilder.append(this.mRootId);
        Log.d(TAG, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  mMediaSessionToken=");
        stringBuilder.append(this.mMediaSessionToken);
        Log.d(TAG, stringBuilder.toString());
    }

    public Bundle getExtras() {
        if (this.isConnected()) {
            return this.mExtras;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getExtras() called while not connected (state=");
        stringBuilder.append(MediaBrowser.getStateLabel(this.mState));
        stringBuilder.append(")");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public void getItem(final String string2, final ItemCallback itemCallback) {
        if (!TextUtils.isEmpty(string2)) {
            if (itemCallback != null) {
                if (this.mState != 3) {
                    Log.i(TAG, "Not connected, unable to retrieve the MediaItem.");
                    this.mHandler.post(new Runnable(){

                        @Override
                        public void run() {
                            itemCallback.onError(string2);
                        }
                    });
                    return;
                }
                ResultReceiver resultReceiver = new ResultReceiver(this.mHandler){

                    @Override
                    protected void onReceiveResult(int n, Bundle bundle) {
                        if (!MediaBrowser.this.isConnected()) {
                            return;
                        }
                        if (n == 0 && bundle != null && bundle.containsKey("media_item")) {
                            if ((bundle = bundle.getParcelable("media_item")) != null && !(bundle instanceof MediaItem)) {
                                itemCallback.onError(string2);
                                return;
                            }
                            itemCallback.onItemLoaded((MediaItem)((Object)bundle));
                            return;
                        }
                        itemCallback.onError(string2);
                    }
                };
                try {
                    this.mServiceBinder.getMediaItem(string2, resultReceiver, this.mServiceCallbacks);
                }
                catch (RemoteException remoteException) {
                    Log.i(TAG, "Remote error getting media item.");
                    this.mHandler.post(new Runnable(){

                        @Override
                        public void run() {
                            itemCallback.onError(string2);
                        }
                    });
                }
                return;
            }
            throw new IllegalArgumentException("cb cannot be null.");
        }
        throw new IllegalArgumentException("mediaId cannot be empty.");
    }

    public String getRoot() {
        if (this.isConnected()) {
            return this.mRootId;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getRoot() called while not connected (state=");
        stringBuilder.append(MediaBrowser.getStateLabel(this.mState));
        stringBuilder.append(")");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public ComponentName getServiceComponent() {
        if (this.isConnected()) {
            return this.mServiceComponent;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getServiceComponent() called while not connected (state=");
        stringBuilder.append(this.mState);
        stringBuilder.append(")");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public MediaSession.Token getSessionToken() {
        if (this.isConnected()) {
            return this.mMediaSessionToken;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getSessionToken() called while not connected (state=");
        stringBuilder.append(this.mState);
        stringBuilder.append(")");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public boolean isConnected() {
        boolean bl = this.mState == 3;
        return bl;
    }

    public void subscribe(String string2, SubscriptionCallback subscriptionCallback) {
        this.subscribeInternal(string2, null, subscriptionCallback);
    }

    public void subscribe(String string2, Bundle bundle, SubscriptionCallback subscriptionCallback) {
        if (bundle != null) {
            this.subscribeInternal(string2, new Bundle(bundle), subscriptionCallback);
            return;
        }
        throw new IllegalArgumentException("options cannot be null");
    }

    public void unsubscribe(String string2) {
        this.unsubscribeInternal(string2, null);
    }

    public void unsubscribe(String string2, SubscriptionCallback subscriptionCallback) {
        if (subscriptionCallback != null) {
            this.unsubscribeInternal(string2, subscriptionCallback);
            return;
        }
        throw new IllegalArgumentException("callback cannot be null");
    }

    public static class ConnectionCallback {
        public void onConnected() {
        }

        public void onConnectionFailed() {
        }

        public void onConnectionSuspended() {
        }
    }

    public static abstract class ItemCallback {
        public void onError(String string2) {
        }

        public void onItemLoaded(MediaItem mediaItem) {
        }
    }

    public static class MediaItem
    implements Parcelable {
        public static final Parcelable.Creator<MediaItem> CREATOR = new Parcelable.Creator<MediaItem>(){

            @Override
            public MediaItem createFromParcel(Parcel parcel) {
                return new MediaItem(parcel);
            }

            public MediaItem[] newArray(int n) {
                return new MediaItem[n];
            }
        };
        public static final int FLAG_BROWSABLE = 1;
        public static final int FLAG_PLAYABLE = 2;
        private final MediaDescription mDescription;
        private final int mFlags;

        public MediaItem(MediaDescription mediaDescription, int n) {
            if (mediaDescription != null) {
                if (!TextUtils.isEmpty(mediaDescription.getMediaId())) {
                    this.mFlags = n;
                    this.mDescription = mediaDescription;
                    return;
                }
                throw new IllegalArgumentException("description must have a non-empty media id");
            }
            throw new IllegalArgumentException("description cannot be null");
        }

        private MediaItem(Parcel parcel) {
            this.mFlags = parcel.readInt();
            this.mDescription = MediaDescription.CREATOR.createFromParcel(parcel);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public MediaDescription getDescription() {
            return this.mDescription;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public String getMediaId() {
            return this.mDescription.getMediaId();
        }

        public boolean isBrowsable() {
            int n = this.mFlags;
            boolean bl = true;
            if ((n & 1) == 0) {
                bl = false;
            }
            return bl;
        }

        public boolean isPlayable() {
            boolean bl = (this.mFlags & 2) != 0;
            return bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("MediaItem{");
            stringBuilder.append("mFlags=");
            stringBuilder.append(this.mFlags);
            stringBuilder.append(", mDescription=");
            stringBuilder.append(this.mDescription);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mFlags);
            this.mDescription.writeToParcel(parcel, n);
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface Flags {
        }

    }

    private class MediaServiceConnection
    implements ServiceConnection {
        private MediaServiceConnection() {
        }

        private boolean isCurrent(String string2) {
            if (MediaBrowser.this.mServiceConnection == this && MediaBrowser.this.mState != 0 && MediaBrowser.this.mState != 1) {
                return true;
            }
            if (MediaBrowser.this.mState != 0 && MediaBrowser.this.mState != 1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append(" for ");
                stringBuilder.append(MediaBrowser.this.mServiceComponent);
                stringBuilder.append(" with mServiceConnection=");
                stringBuilder.append(MediaBrowser.this.mServiceConnection);
                stringBuilder.append(" this=");
                stringBuilder.append(this);
                Log.i(MediaBrowser.TAG, stringBuilder.toString());
            }
            return false;
        }

        private void postOrRun(Runnable runnable) {
            if (Thread.currentThread() == MediaBrowser.this.mHandler.getLooper().getThread()) {
                runnable.run();
            } else {
                MediaBrowser.this.mHandler.post(runnable);
            }
        }

        @Override
        public void onServiceConnected(final ComponentName componentName, final IBinder iBinder) {
            this.postOrRun(new Runnable(){

                @Override
                public void run() {
                    if (!MediaServiceConnection.this.isCurrent("onServiceConnected")) {
                        return;
                    }
                    MediaBrowser.this.mServiceBinder = IMediaBrowserService.Stub.asInterface(iBinder);
                    MediaBrowser.this.mServiceCallbacks = MediaBrowser.this.getNewServiceCallbacks();
                    MediaBrowser.this.mState = 2;
                    try {
                        MediaBrowser.this.mServiceBinder.connect(MediaBrowser.this.mContext.getPackageName(), MediaBrowser.this.mRootHints, MediaBrowser.this.mServiceCallbacks);
                    }
                    catch (RemoteException remoteException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("RemoteException during connect for ");
                        stringBuilder.append(MediaBrowser.this.mServiceComponent);
                        Log.w(MediaBrowser.TAG, stringBuilder.toString());
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(final ComponentName componentName) {
            this.postOrRun(new Runnable(){

                @Override
                public void run() {
                    if (!MediaServiceConnection.this.isCurrent("onServiceDisconnected")) {
                        return;
                    }
                    MediaBrowser.this.mServiceBinder = null;
                    MediaBrowser.this.mServiceCallbacks = null;
                    MediaBrowser.this.mState = 4;
                    MediaBrowser.this.mCallback.onConnectionSuspended();
                }
            });
        }

    }

    private static class ServiceCallbacks
    extends IMediaBrowserServiceCallbacks.Stub {
        private WeakReference<MediaBrowser> mMediaBrowser;

        ServiceCallbacks(MediaBrowser mediaBrowser) {
            this.mMediaBrowser = new WeakReference<MediaBrowser>(mediaBrowser);
        }

        @Override
        public void onConnect(String string2, MediaSession.Token token, Bundle bundle) {
            MediaBrowser mediaBrowser = (MediaBrowser)this.mMediaBrowser.get();
            if (mediaBrowser != null) {
                mediaBrowser.onServiceConnected(this, string2, token, bundle);
            }
        }

        @Override
        public void onConnectFailed() {
            MediaBrowser mediaBrowser = (MediaBrowser)this.mMediaBrowser.get();
            if (mediaBrowser != null) {
                mediaBrowser.onConnectionFailed(this);
            }
        }

        @Override
        public void onLoadChildren(String string2, ParceledListSlice parceledListSlice) {
            this.onLoadChildrenWithOptions(string2, parceledListSlice, null);
        }

        @Override
        public void onLoadChildrenWithOptions(String string2, ParceledListSlice parceledListSlice, Bundle bundle) {
            MediaBrowser mediaBrowser = (MediaBrowser)this.mMediaBrowser.get();
            if (mediaBrowser != null) {
                mediaBrowser.onLoadChildren(this, string2, parceledListSlice, bundle);
            }
        }
    }

    private static class Subscription {
        private final List<SubscriptionCallback> mCallbacks = new ArrayList<SubscriptionCallback>();
        private final List<Bundle> mOptionsList = new ArrayList<Bundle>();

        Subscription() {
        }

        public SubscriptionCallback getCallback(Context context, Bundle bundle) {
            if (bundle != null) {
                bundle.setClassLoader(context.getClassLoader());
            }
            for (int i = 0; i < this.mOptionsList.size(); ++i) {
                if (!MediaBrowserUtils.areSameOptions(this.mOptionsList.get(i), bundle)) continue;
                return this.mCallbacks.get(i);
            }
            return null;
        }

        public List<SubscriptionCallback> getCallbacks() {
            return this.mCallbacks;
        }

        public List<Bundle> getOptionsList() {
            return this.mOptionsList;
        }

        public boolean isEmpty() {
            return this.mCallbacks.isEmpty();
        }

        public void putCallback(Context context, Bundle bundle, SubscriptionCallback subscriptionCallback) {
            if (bundle != null) {
                bundle.setClassLoader(context.getClassLoader());
            }
            for (int i = 0; i < this.mOptionsList.size(); ++i) {
                if (!MediaBrowserUtils.areSameOptions(this.mOptionsList.get(i), bundle)) continue;
                this.mCallbacks.set(i, subscriptionCallback);
                return;
            }
            this.mCallbacks.add(subscriptionCallback);
            this.mOptionsList.add(bundle);
        }
    }

    public static abstract class SubscriptionCallback {
        Binder mToken = new Binder();

        public void onChildrenLoaded(String string2, List<MediaItem> list) {
        }

        public void onChildrenLoaded(String string2, List<MediaItem> list, Bundle bundle) {
        }

        public void onError(String string2) {
        }

        public void onError(String string2, Bundle bundle) {
        }
    }

}

