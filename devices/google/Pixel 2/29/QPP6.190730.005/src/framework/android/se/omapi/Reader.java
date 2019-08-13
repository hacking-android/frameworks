/*
 * Decompiled with CFR 0.145.
 */
package android.se.omapi;

import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.se.omapi.ISecureElementReader;
import android.se.omapi.ISecureElementSession;
import android.se.omapi.SEService;
import android.se.omapi.Session;
import android.util.Log;
import java.io.IOException;

public final class Reader {
    private static final String TAG = "OMAPI.Reader";
    private final Object mLock = new Object();
    private final String mName;
    private ISecureElementReader mReader;
    private final SEService mService;

    Reader(SEService sEService, String string2, ISecureElementReader iSecureElementReader) {
        if (iSecureElementReader != null && sEService != null && string2 != null) {
            this.mName = string2;
            this.mService = sEService;
            this.mReader = iSecureElementReader;
            return;
        }
        throw new IllegalArgumentException("Parameters cannot be null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void closeSessions() {
        if (!this.mService.isConnected()) {
            Log.e(TAG, "service is not connected");
            return;
        }
        Object object = this.mLock;
        synchronized (object) {
            try {
                try {
                    this.mReader.closeSessions();
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    public String getName() {
        return this.mName;
    }

    public SEService getSEService() {
        return this.mService;
    }

    public boolean isSecureElementPresent() {
        if (this.mService.isConnected()) {
            try {
                boolean bl = this.mReader.isSecureElementPresent();
                return bl;
            }
            catch (RemoteException remoteException) {
                throw new IllegalStateException("Error in isSecureElementPresent()");
            }
        }
        throw new IllegalStateException("service is not connected");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Session openSession() throws IOException {
        if (!this.mService.isConnected()) {
            throw new IllegalStateException("service is not connected");
        }
        Object object = this.mLock;
        synchronized (object) {
            try {
                block7 : {
                    ISecureElementSession iSecureElementSession;
                    try {
                        iSecureElementSession = this.mReader.openSession();
                        if (iSecureElementSession == null) break block7;
                    }
                    catch (RemoteException remoteException) {
                        IllegalStateException illegalStateException = new IllegalStateException(remoteException.getMessage());
                        throw illegalStateException;
                    }
                    catch (ServiceSpecificException serviceSpecificException) {
                        IOException iOException = new IOException(serviceSpecificException.getMessage());
                        throw iOException;
                    }
                    return new Session(this.mService, iSecureElementSession, this);
                }
                IOException iOException = new IOException("service session is null.");
                throw iOException;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }
}

