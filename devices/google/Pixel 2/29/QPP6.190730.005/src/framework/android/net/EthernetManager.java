/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.net.IEthernetManager;
import android.net.IEthernetServiceListener;
import android.net.IpConfiguration;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;

public class EthernetManager {
    private static final int MSG_AVAILABILITY_CHANGED = 1000;
    private static final String TAG = "EthernetManager";
    private final Context mContext;
    private final Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message message) {
            if (message.what == 1000) {
                int n = message.arg1;
                boolean bl = true;
                if (n != 1) {
                    bl = false;
                }
                Iterator iterator = EthernetManager.this.mListeners.iterator();
                while (iterator.hasNext()) {
                    ((Listener)iterator.next()).onAvailabilityChanged((String)message.obj, bl);
                }
            }
        }
    };
    private final ArrayList<Listener> mListeners = new ArrayList();
    private final IEthernetManager mService;
    private final IEthernetServiceListener.Stub mServiceListener = new IEthernetServiceListener.Stub(){

        @Override
        public void onAvailabilityChanged(String string2, boolean bl) {
            EthernetManager.this.mHandler.obtainMessage(1000, (int)bl, 0, string2).sendToTarget();
        }
    };

    public EthernetManager(Context context, IEthernetManager iEthernetManager) {
        this.mContext = context;
        this.mService = iEthernetManager;
    }

    @UnsupportedAppUsage
    public void addListener(Listener listener) {
        if (listener != null) {
            this.mListeners.add(listener);
            if (this.mListeners.size() == 1) {
                try {
                    this.mService.addListener(this.mServiceListener);
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            return;
        }
        throw new IllegalArgumentException("listener must not be null");
    }

    @UnsupportedAppUsage
    public String[] getAvailableInterfaces() {
        try {
            String[] arrstring = this.mService.getAvailableInterfaces();
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    @UnsupportedAppUsage
    public IpConfiguration getConfiguration(String object) {
        try {
            object = this.mService.getConfiguration((String)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public boolean isAvailable() {
        boolean bl = this.getAvailableInterfaces().length > 0;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isAvailable(String string2) {
        try {
            boolean bl = this.mService.isAvailable(string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void removeListener(Listener listener) {
        if (listener != null) {
            this.mListeners.remove(listener);
            if (this.mListeners.isEmpty()) {
                try {
                    this.mService.removeListener(this.mServiceListener);
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            return;
        }
        throw new IllegalArgumentException("listener must not be null");
    }

    @UnsupportedAppUsage
    public void setConfiguration(String string2, IpConfiguration ipConfiguration) {
        try {
            this.mService.setConfiguration(string2, ipConfiguration);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static interface Listener {
        @UnsupportedAppUsage
        public void onAvailabilityChanged(String var1, boolean var2);
    }

}

