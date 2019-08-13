/*
 * Decompiled with CFR 0.145.
 */
package android.se.omapi;

import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.se.omapi.Channel;
import android.se.omapi.ISecureElementChannel;
import android.se.omapi.ISecureElementListener;
import android.se.omapi.ISecureElementSession;
import android.se.omapi.Reader;
import android.se.omapi.SEService;
import android.util.Log;
import java.io.IOException;
import java.util.NoSuchElementException;

public final class Session {
    private static final String TAG = "OMAPI.Session";
    private final Object mLock = new Object();
    private final Reader mReader;
    private final SEService mService;
    private final ISecureElementSession mSession;

    Session(SEService sEService, ISecureElementSession iSecureElementSession, Reader reader) {
        if (sEService != null && reader != null && iSecureElementSession != null) {
            this.mService = sEService;
            this.mReader = reader;
            this.mSession = iSecureElementSession;
            return;
        }
        throw new IllegalArgumentException("Parameters cannot be null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void close() {
        if (!this.mService.isConnected()) {
            Log.e(TAG, "service not connected to system");
            return;
        }
        Object object = this.mLock;
        synchronized (object) {
            try {
                try {
                    this.mSession.close();
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "Error closing session", remoteException);
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void closeChannels() {
        if (!this.mService.isConnected()) {
            Log.e(TAG, "service not connected to system");
            return;
        }
        Object object = this.mLock;
        synchronized (object) {
            try {
                try {
                    this.mSession.closeChannels();
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "Error closing channels", remoteException);
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    public byte[] getATR() {
        if (this.mService.isConnected()) {
            try {
                byte[] arrby = this.mSession.getAtr();
                return arrby;
            }
            catch (RemoteException remoteException) {
                throw new IllegalStateException(remoteException.getMessage());
            }
        }
        throw new IllegalStateException("service not connected to system");
    }

    public Reader getReader() {
        return this.mReader;
    }

    public boolean isClosed() {
        try {
            boolean bl = this.mSession.isClosed();
            return bl;
        }
        catch (RemoteException remoteException) {
            return true;
        }
    }

    public Channel openBasicChannel(byte[] arrby) throws IOException {
        return this.openBasicChannel(arrby, (byte)0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Channel openBasicChannel(byte[] object, byte by) throws IOException {
        if (!this.mService.isConnected()) {
            throw new IllegalStateException("service not connected to system");
        }
        Object object2 = this.mLock;
        synchronized (object2) {
            try {
                block10 : {
                    object = this.mSession.openBasicChannel((byte[])object, by, this.mReader.getSEService().getListener());
                    if (object != null) break block10;
                    return null;
                }
                try {
                    return new Channel(this.mService, this, (ISecureElementChannel)object);
                }
                catch (RemoteException remoteException) {
                    object = new IllegalStateException(remoteException.getMessage());
                    throw object;
                }
                catch (ServiceSpecificException serviceSpecificException) {
                    if (serviceSpecificException.errorCode == 1) {
                        IOException iOException = new IOException(serviceSpecificException.getMessage());
                        throw iOException;
                    }
                    if (serviceSpecificException.errorCode == 2) {
                        NoSuchElementException noSuchElementException = new NoSuchElementException(serviceSpecificException.getMessage());
                        throw noSuchElementException;
                    }
                    IllegalStateException illegalStateException = new IllegalStateException(serviceSpecificException.getMessage());
                    throw illegalStateException;
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    public Channel openLogicalChannel(byte[] arrby) throws IOException {
        return this.openLogicalChannel(arrby, (byte)0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Channel openLogicalChannel(byte[] object, byte by) throws IOException {
        if (!this.mService.isConnected()) {
            throw new IllegalStateException("service not connected to system");
        }
        Object object2 = this.mLock;
        synchronized (object2) {
            try {
                Object object3;
                block10 : {
                    object3 = this.mSession.openLogicalChannel((byte[])object, by, this.mReader.getSEService().getListener());
                    if (object3 != null) break block10;
                    return null;
                }
                try {
                    return new Channel(this.mService, this, (ISecureElementChannel)object3);
                }
                catch (RemoteException remoteException) {
                    object = new IllegalStateException(remoteException.getMessage());
                    throw object;
                }
                catch (ServiceSpecificException serviceSpecificException) {
                    if (serviceSpecificException.errorCode == 1) {
                        object3 = new IOException(serviceSpecificException.getMessage());
                        throw object3;
                    }
                    if (serviceSpecificException.errorCode == 2) {
                        object3 = new NoSuchElementException(serviceSpecificException.getMessage());
                        throw object3;
                    }
                    object3 = new IllegalStateException(serviceSpecificException.getMessage());
                    throw object3;
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }
}

