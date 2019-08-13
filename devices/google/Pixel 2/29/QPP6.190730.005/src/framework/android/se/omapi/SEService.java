/*
 * Decompiled with CFR 0.145.
 */
package android.se.omapi;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.se.omapi.ISecureElementListener;
import android.se.omapi.ISecureElementReader;
import android.se.omapi.ISecureElementService;
import android.se.omapi.Reader;
import android.util.Log;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.Executor;

public final class SEService {
    public static final int IO_ERROR = 1;
    public static final int NO_SUCH_ELEMENT_ERROR = 2;
    private static final String TAG = "OMAPI.SEService";
    private ServiceConnection mConnection;
    private final Context mContext;
    private final Object mLock = new Object();
    private final HashMap<String, Reader> mReaders = new HashMap();
    private SEListener mSEListener = new SEListener();
    private volatile ISecureElementService mSecureElementService;

    public SEService(Context object, Executor executor, OnConnectedListener onConnectedListener) {
        if (object != null && onConnectedListener != null && executor != null) {
            this.mContext = object;
            object = this.mSEListener;
            ((SEListener)object).mListener = onConnectedListener;
            ((SEListener)object).mExecutor = executor;
            this.mConnection = new ServiceConnection(){

                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    synchronized (this) {
                        SEService.this.mSecureElementService = ISecureElementService.Stub.asInterface(iBinder);
                        if (SEService.this.mSEListener != null) {
                            SEService.this.mSEListener.onConnected();
                        }
                        Log.i(SEService.TAG, "Service onServiceConnected");
                        return;
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    SEService.this.mSecureElementService = null;
                    Log.i(SEService.TAG, "Service onServiceDisconnected");
                }
            };
            object = new Intent(ISecureElementService.class.getName());
            ((Intent)object).setClassName("com.android.se", "com.android.se.SecureElementService");
            if (this.mContext.bindService((Intent)object, this.mConnection, 1)) {
                Log.i(TAG, "bindService successful");
            }
            return;
        }
        throw new NullPointerException("Arguments must not be null");
    }

    private ISecureElementReader getReader(String object) {
        try {
            object = this.mSecureElementService.getReader((String)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw new IllegalStateException(remoteException.getMessage());
        }
    }

    ISecureElementListener getListener() {
        return this.mSEListener;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public Reader[] getReaders() {
        String[] arrstring;
        if (this.mSecureElementService == null) throw new IllegalStateException("service not connected to system");
        try {
            arrstring = this.mSecureElementService.getReaders();
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
        Reader[] arrreader = new Reader[arrstring.length];
        int n = 0;
        int n2 = arrstring.length;
        int n3 = 0;
        while (n3 < n2) {
            block9 : {
                String string2 = arrstring[n3];
                if (this.mReaders.get(string2) == null) {
                    Object object;
                    HashMap<String, Reader> hashMap;
                    block10 : {
                        hashMap = this.mReaders;
                        object = new Reader(this, string2, this.getReader(string2));
                        hashMap.put(string2, (Reader)object);
                        int n22 = n + 1;
                        try {
                            arrreader[n] = this.mReaders.get(string2);
                            n = n22;
                            break block9;
                        }
                        catch (Exception exception) {
                            n = n22;
                            break block10;
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Error adding Reader: ");
                    ((StringBuilder)object).append(string2);
                    Log.e(TAG, ((StringBuilder)object).toString(), hashMap);
                } else {
                    arrreader[n] = this.mReaders.get(string2);
                    ++n;
                }
            }
            ++n3;
        }
        return arrreader;
    }

    public String getVersion() {
        return "3.3";
    }

    public boolean isConnected() {
        boolean bl = this.mSecureElementService != null;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void shutdown() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mSecureElementService != null) {
                for (Reader reader : this.mReaders.values()) {
                    try {
                        reader.closeSessions();
                    }
                    catch (Exception exception) {}
                }
            }
            try {
                this.mContext.unbindService(this.mConnection);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
            this.mSecureElementService = null;
            return;
        }
    }

    public static interface OnConnectedListener {
        public void onConnected();
    }

    private class SEListener
    extends ISecureElementListener.Stub {
        public Executor mExecutor = null;
        public OnConnectedListener mListener = null;

        private SEListener() {
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        public void onConnected() {
            Executor executor;
            if (this.mListener != null && (executor = this.mExecutor) != null) {
                executor.execute(new Runnable(){

                    @Override
                    public void run() {
                        SEListener.this.mListener.onConnected();
                    }
                });
            }
        }

    }

}

