/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.IMediaScannerListener;
import android.media.IMediaScannerService;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;

public class MediaScannerConnection
implements ServiceConnection {
    private static final String TAG = "MediaScannerConnection";
    private MediaScannerConnectionClient mClient;
    private boolean mConnected;
    private Context mContext;
    private final IMediaScannerListener.Stub mListener = new IMediaScannerListener.Stub(){

        @Override
        public void scanCompleted(String string2, Uri uri) {
            MediaScannerConnectionClient mediaScannerConnectionClient = MediaScannerConnection.this.mClient;
            if (mediaScannerConnectionClient != null) {
                mediaScannerConnectionClient.onScanCompleted(string2, uri);
            }
        }
    };
    private IMediaScannerService mService;

    public MediaScannerConnection(Context context, MediaScannerConnectionClient mediaScannerConnectionClient) {
        this.mContext = context;
        this.mClient = mediaScannerConnectionClient;
    }

    public static void scanFile(Context object, String[] object2, String[] arrstring, OnScanCompletedListener onScanCompletedListener) {
        object2 = new ClientProxy((String[])object2, arrstring, onScanCompletedListener);
        object2.mConnection = object = new MediaScannerConnection((Context)object, (MediaScannerConnectionClient)object2);
        ((MediaScannerConnection)object).connect();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void connect() {
        synchronized (this) {
            if (!this.mConnected) {
                Intent intent = new Intent(IMediaScannerService.class.getName());
                ComponentName componentName = new ComponentName("com.android.providers.media", "com.android.providers.media.MediaScannerService");
                intent.setComponent(componentName);
                this.mContext.bindService(intent, this, 1);
                this.mConnected = true;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void disconnect() {
        synchronized (this) {
            boolean bl = this.mConnected;
            if (bl) {
                try {
                    this.mContext.unbindService(this);
                    if (this.mClient instanceof ClientProxy) {
                        this.mClient = null;
                    }
                    this.mService = null;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    // empty catch block
                }
                this.mConnected = false;
            }
            return;
        }
    }

    public boolean isConnected() {
        synchronized (this) {
            boolean bl;
            bl = this.mService != null && (bl = this.mConnected);
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this) {
            this.mService = IMediaScannerService.Stub.asInterface(iBinder);
            if (this.mService != null && this.mClient != null) {
                this.mClient.onMediaScannerConnected();
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
    public void onServiceDisconnected(ComponentName componentName) {
        synchronized (this) {
            this.mService = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void scanFile(String object, String string2) {
        synchronized (this) {
            boolean bl;
            if (this.mService != null && (bl = this.mConnected)) {
                try {
                    this.mService.requestScanFile((String)object, string2, this.mListener);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
                return;
            }
            object = new IllegalStateException("not connected to MediaScannerService");
            throw object;
        }
    }

    static class ClientProxy
    implements MediaScannerConnectionClient {
        final OnScanCompletedListener mClient;
        MediaScannerConnection mConnection;
        final String[] mMimeTypes;
        int mNextPath;
        final String[] mPaths;

        ClientProxy(String[] arrstring, String[] arrstring2, OnScanCompletedListener onScanCompletedListener) {
            this.mPaths = arrstring;
            this.mMimeTypes = arrstring2;
            this.mClient = onScanCompletedListener;
        }

        @Override
        public void onMediaScannerConnected() {
            this.scanNextPath();
        }

        @Override
        public void onScanCompleted(String string2, Uri uri) {
            OnScanCompletedListener onScanCompletedListener = this.mClient;
            if (onScanCompletedListener != null) {
                onScanCompletedListener.onScanCompleted(string2, uri);
            }
            this.scanNextPath();
        }

        void scanNextPath() {
            int n = this.mNextPath;
            int n2 = this.mPaths.length;
            String string2 = null;
            if (n >= n2) {
                this.mConnection.disconnect();
                this.mConnection = null;
                return;
            }
            String[] arrstring = this.mMimeTypes;
            if (arrstring != null) {
                string2 = arrstring[n];
            }
            this.mConnection.scanFile(this.mPaths[this.mNextPath], string2);
            ++this.mNextPath;
        }
    }

    public static interface MediaScannerConnectionClient
    extends OnScanCompletedListener {
        public void onMediaScannerConnected();

        @Override
        public void onScanCompleted(String var1, Uri var2);
    }

    public static interface OnScanCompletedListener {
        public void onScanCompleted(String var1, Uri var2);
    }

}

