/*
 * Decompiled with CFR 0.145.
 */
package android.nfc.tech;

import android.nfc.INfcTag;
import android.nfc.Tag;
import android.nfc.TransceiveResult;
import android.nfc.tech.TagTechnology;
import android.os.RemoteException;
import android.util.Log;
import java.io.IOException;

abstract class BasicTagTechnology
implements TagTechnology {
    private static final String TAG = "NFC";
    boolean mIsConnected;
    int mSelectedTechnology;
    final Tag mTag;

    BasicTagTechnology(Tag tag, int n) throws RemoteException {
        this.mTag = tag;
        this.mSelectedTechnology = n;
    }

    void checkConnected() {
        if (this.mTag.getConnectedTechnology() == this.mSelectedTechnology && this.mTag.getConnectedTechnology() != -1) {
            return;
        }
        throw new IllegalStateException("Call connect() first!");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() throws IOException {
        try {
            try {
                this.mTag.getTagService().resetTimeouts();
                this.mTag.getTagService().reconnect(this.mTag.getServiceHandle());
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "NFC service dead", remoteException);
            }
            this.mIsConnected = false;
            this.mTag.setTechnologyDisconnected();
            return;
        }
        catch (Throwable throwable2) {}
        this.mIsConnected = false;
        this.mTag.setTechnologyDisconnected();
        throw throwable2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void connect() throws IOException {
        try {
            int n = this.mTag.getTagService().connect(this.mTag.getServiceHandle(), this.mSelectedTechnology);
            if (n == 0) {
                this.mTag.setConnectedTechnology(this.mSelectedTechnology);
                this.mIsConnected = true;
                return;
            }
            if (n == -21) {
                UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException("Connecting to this technology is not supported by the NFC adapter.");
                throw unsupportedOperationException;
            }
            IOException iOException = new IOException();
            throw iOException;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "NFC service dead", remoteException);
            throw new IOException("NFC service died");
        }
    }

    int getMaxTransceiveLengthInternal() {
        try {
            int n = this.mTag.getTagService().getMaxTransceiveLength(this.mSelectedTechnology);
            return n;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "NFC service dead", remoteException);
            return 0;
        }
    }

    @Override
    public Tag getTag() {
        return this.mTag;
    }

    @Override
    public boolean isConnected() {
        if (!this.mIsConnected) {
            return false;
        }
        try {
            boolean bl = this.mTag.getTagService().isPresent(this.mTag.getServiceHandle());
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "NFC service dead", remoteException);
            return false;
        }
    }

    @Override
    public void reconnect() throws IOException {
        if (this.mIsConnected) {
            try {
                if (this.mTag.getTagService().reconnect(this.mTag.getServiceHandle()) == 0) {
                    return;
                }
                this.mIsConnected = false;
                this.mTag.setTechnologyDisconnected();
                IOException iOException = new IOException();
                throw iOException;
            }
            catch (RemoteException remoteException) {
                this.mIsConnected = false;
                this.mTag.setTechnologyDisconnected();
                Log.e(TAG, "NFC service dead", remoteException);
                throw new IOException("NFC service died");
            }
        }
        throw new IllegalStateException("Technology not connected yet");
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    byte[] transceive(byte[] object, boolean bl) throws IOException {
        this.checkConnected();
        try {
            void var2_5;
            TransceiveResult transceiveResult = this.mTag.getTagService().transceive(this.mTag.getServiceHandle(), (byte[])object, (boolean)var2_5);
            if (transceiveResult != null) {
                return transceiveResult.getResponseOrThrow();
            }
            IOException iOException = new IOException("transceive failed");
            throw iOException;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "NFC service dead", remoteException);
            throw new IOException("NFC service died");
        }
    }
}

