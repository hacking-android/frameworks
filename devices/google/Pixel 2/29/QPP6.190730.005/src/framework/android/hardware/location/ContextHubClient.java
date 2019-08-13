/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.hardware.location;

import android.annotation.SystemApi;
import android.hardware.location.ContextHubInfo;
import android.hardware.location.IContextHubClient;
import android.hardware.location.NanoAppMessage;
import android.os.RemoteException;
import com.android.internal.util.Preconditions;
import dalvik.system.CloseGuard;
import java.io.Closeable;
import java.util.concurrent.atomic.AtomicBoolean;

@SystemApi
public class ContextHubClient
implements Closeable {
    private final ContextHubInfo mAttachedHub;
    private IContextHubClient mClientProxy = null;
    private final CloseGuard mCloseGuard;
    private final AtomicBoolean mIsClosed = new AtomicBoolean(false);
    private final boolean mPersistent;

    ContextHubClient(ContextHubInfo contextHubInfo, boolean bl) {
        this.mAttachedHub = contextHubInfo;
        this.mPersistent = bl;
        if (this.mPersistent) {
            this.mCloseGuard = null;
        } else {
            this.mCloseGuard = CloseGuard.get();
            this.mCloseGuard.open("close");
        }
    }

    @Override
    public void close() {
        if (!this.mIsClosed.getAndSet(true)) {
            CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                closeGuard.close();
            }
            try {
                this.mClientProxy.close();
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            if (!this.mPersistent) {
                this.close();
            }
            return;
        }
        finally {
            super.finalize();
        }
    }

    public ContextHubInfo getAttachedHub() {
        return this.mAttachedHub;
    }

    public int sendMessageToNanoApp(NanoAppMessage nanoAppMessage) {
        Preconditions.checkNotNull(nanoAppMessage, "NanoAppMessage cannot be null");
        try {
            int n = this.mClientProxy.sendMessageToNanoApp(nanoAppMessage);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    void setClientProxy(IContextHubClient iContextHubClient) {
        Preconditions.checkNotNull(iContextHubClient, "IContextHubClient cannot be null");
        if (this.mClientProxy == null) {
            this.mClientProxy = iContextHubClient;
            return;
        }
        throw new IllegalStateException("Cannot change client proxy multiple times");
    }
}

