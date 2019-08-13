/*
 * Decompiled with CFR 0.145.
 */
package android.se.omapi;

import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.se.omapi.ISecureElementChannel;
import android.se.omapi.SEService;
import android.se.omapi.Session;
import android.util.Log;
import java.io.IOException;

public final class Channel
implements java.nio.channels.Channel {
    private static final String TAG = "OMAPI.Channel";
    private final ISecureElementChannel mChannel;
    private final Object mLock = new Object();
    private final SEService mService;
    private Session mSession;

    Channel(SEService sEService, Session session, ISecureElementChannel iSecureElementChannel) {
        if (sEService != null && session != null && iSecureElementChannel != null) {
            this.mService = sEService;
            this.mSession = session;
            this.mChannel = iSecureElementChannel;
            return;
        }
        throw new IllegalArgumentException("Parameters cannot be null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        if (!this.isOpen()) return;
        Object object = this.mLock;
        synchronized (object) {
            try {
                try {
                    this.mChannel.close();
                }
                catch (Exception exception) {
                    Log.e(TAG, "Error closing channel", exception);
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    public byte[] getSelectResponse() {
        if (this.mService.isConnected()) {
            byte[] arrby;
            block4 : {
                byte[] arrby2;
                try {
                    arrby = arrby2 = this.mChannel.getSelectResponse();
                    if (arrby2 == null) break block4;
                    arrby = arrby2;
                }
                catch (RemoteException remoteException) {
                    throw new IllegalStateException(remoteException.getMessage());
                }
                if (arrby2.length == 0) {
                    arrby = null;
                }
            }
            return arrby;
        }
        throw new IllegalStateException("service not connected to system");
    }

    public Session getSession() {
        return this.mSession;
    }

    public boolean isBasicChannel() {
        if (this.mService.isConnected()) {
            try {
                boolean bl = this.mChannel.isBasicChannel();
                return bl;
            }
            catch (RemoteException remoteException) {
                throw new IllegalStateException(remoteException.getMessage());
            }
        }
        throw new IllegalStateException("service not connected to system");
    }

    @Override
    public boolean isOpen() {
        if (!this.mService.isConnected()) {
            Log.e(TAG, "service not connected to system");
            return false;
        }
        try {
            boolean bl = this.mChannel.isClosed();
            return bl ^ true;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Exception in isClosed()");
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public boolean selectNext() throws IOException {
        if (!this.mService.isConnected()) throw new IllegalStateException("service not connected to system");
        try {
            Object object = this.mLock;
            // MONITORENTER : object
        }
        catch (RemoteException remoteException) {
            throw new IllegalStateException(remoteException.getMessage());
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw new IOException(serviceSpecificException.getMessage());
        }
        boolean bl = this.mChannel.selectNext();
        // MONITOREXIT : object
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public byte[] transmit(byte[] object) throws IOException {
        if (!this.mService.isConnected()) {
            throw new IllegalStateException("service not connected to system");
        }
        Object object2 = this.mLock;
        synchronized (object2) {
            try {
                block8 : {
                    object = this.mChannel.transmit((byte[])object);
                    if (object == null) break block8;
                    return object;
                }
                try {
                    object = new IOException("Error in communicating with Secure Element");
                    throw object;
                }
                catch (RemoteException remoteException) {
                    IllegalStateException illegalStateException = new IllegalStateException(remoteException.getMessage());
                    throw illegalStateException;
                }
                catch (ServiceSpecificException serviceSpecificException) {
                    IOException iOException = new IOException(serviceSpecificException.getMessage());
                    throw iOException;
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }
}

