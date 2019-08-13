/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.annotation.UnsupportedAppUsage;
import android.database.IContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.UserHandle;

public abstract class ContentObserver {
    Handler mHandler;
    private final Object mLock = new Object();
    private Transport mTransport;

    public ContentObserver(Handler handler) {
        this.mHandler = handler;
    }

    private void dispatchChange(boolean bl, Uri uri, int n) {
        Handler handler = this.mHandler;
        if (handler == null) {
            this.onChange(bl, uri, n);
        } else {
            handler.post(new NotificationRunnable(bl, uri, n));
        }
    }

    public boolean deliverSelfNotifications() {
        return false;
    }

    @Deprecated
    public final void dispatchChange(boolean bl) {
        this.dispatchChange(bl, null);
    }

    public final void dispatchChange(boolean bl, Uri uri) {
        this.dispatchChange(bl, uri, UserHandle.getCallingUserId());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public IContentObserver getContentObserver() {
        Object object = this.mLock;
        synchronized (object) {
            Transport transport;
            if (this.mTransport != null) return this.mTransport;
            this.mTransport = transport = new Transport(this);
            return this.mTransport;
        }
    }

    public void onChange(boolean bl) {
    }

    public void onChange(boolean bl, Uri uri) {
        this.onChange(bl);
    }

    public void onChange(boolean bl, Uri uri, int n) {
        this.onChange(bl, uri);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public IContentObserver releaseContentObserver() {
        Object object = this.mLock;
        synchronized (object) {
            Transport transport = this.mTransport;
            if (transport != null) {
                transport.releaseContentObserver();
                this.mTransport = null;
            }
            return transport;
        }
    }

    private final class NotificationRunnable
    implements Runnable {
        private final boolean mSelfChange;
        private final Uri mUri;
        private final int mUserId;

        public NotificationRunnable(boolean bl, Uri uri, int n) {
            this.mSelfChange = bl;
            this.mUri = uri;
            this.mUserId = n;
        }

        @Override
        public void run() {
            ContentObserver.this.onChange(this.mSelfChange, this.mUri, this.mUserId);
        }
    }

    private static final class Transport
    extends IContentObserver.Stub {
        private ContentObserver mContentObserver;

        public Transport(ContentObserver contentObserver) {
            this.mContentObserver = contentObserver;
        }

        @Override
        public void onChange(boolean bl, Uri uri, int n) {
            ContentObserver contentObserver = this.mContentObserver;
            if (contentObserver != null) {
                contentObserver.dispatchChange(bl, uri, n);
            }
        }

        public void releaseContentObserver() {
            this.mContentObserver = null;
        }
    }

}

