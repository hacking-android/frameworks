/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Debug;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Slog;
import java.io.PrintWriter;
import java.util.function.Consumer;

public class RemoteCallbackList<E extends IInterface> {
    private static final String TAG = "RemoteCallbackList";
    private Object[] mActiveBroadcast;
    private int mBroadcastCount = -1;
    @UnsupportedAppUsage
    ArrayMap<IBinder, RemoteCallbackList<E>> mCallbacks = new ArrayMap();
    private boolean mKilled = false;
    private StringBuilder mRecentCallers;

    private void logExcessiveCallbacks() {
        long l = this.mCallbacks.size();
        if (l >= 3000L) {
            StringBuilder stringBuilder;
            if (l == 3000L && this.mRecentCallers == null) {
                this.mRecentCallers = new StringBuilder();
            }
            if ((stringBuilder = this.mRecentCallers) != null && (long)stringBuilder.length() < 1000L) {
                this.mRecentCallers.append(Debug.getCallers(5));
                this.mRecentCallers.append('\n');
                if ((long)this.mRecentCallers.length() >= 1000L) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("More than 3000 remote callbacks registered. Recent callers:\n");
                    stringBuilder.append(this.mRecentCallers.toString());
                    Slog.wtf(TAG, stringBuilder.toString());
                    this.mRecentCallers = null;
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int beginBroadcast() {
        ArrayMap<IBinder, RemoteCallbackList<E>> arrayMap = this.mCallbacks;
        synchronized (arrayMap) {
            int n;
            block11 : {
                Object[] arrobject;
                Object[] arrobject2;
                block10 : {
                    if (this.mBroadcastCount > 0) {
                        IllegalStateException illegalStateException = new IllegalStateException("beginBroadcast() called while already in a broadcast");
                        throw illegalStateException;
                    }
                    this.mBroadcastCount = n = this.mCallbacks.size();
                    if (n <= 0) {
                        return 0;
                    }
                    arrobject2 = this.mActiveBroadcast;
                    if (arrobject2 == null) break block10;
                    arrobject = arrobject2;
                    if (arrobject2.length >= n) break block11;
                }
                arrobject = arrobject2 = new Object[n];
                this.mActiveBroadcast = arrobject2;
            }
            int n2 = 0;
            while (n2 < n) {
                arrobject[n2] = this.mCallbacks.valueAt(n2);
                ++n2;
            }
            return n;
        }
    }

    public void broadcast(Consumer<E> consumer) {
        int n = this.beginBroadcast();
        for (int i = 0; i < n; ++i) {
            try {
                consumer.accept(this.getBroadcastItem(i));
            }
            catch (Throwable throwable) {
                this.finishBroadcast();
                throw throwable;
            }
        }
        this.finishBroadcast();
    }

    public <C> void broadcastForEachCookie(Consumer<C> consumer) {
        int n = this.beginBroadcast();
        for (int i = 0; i < n; ++i) {
            try {
                consumer.accept(this.getBroadcastCookie(i));
            }
            catch (Throwable throwable) {
                this.finishBroadcast();
                throw throwable;
            }
        }
        this.finishBroadcast();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dump(PrintWriter printWriter, String string2) {
        ArrayMap<IBinder, RemoteCallbackList<E>> arrayMap = this.mCallbacks;
        synchronized (arrayMap) {
            printWriter.print(string2);
            printWriter.print("callbacks: ");
            printWriter.println(this.mCallbacks.size());
            printWriter.print(string2);
            printWriter.print("killed: ");
            printWriter.println(this.mKilled);
            printWriter.print(string2);
            printWriter.print("broadcasts count: ");
            printWriter.println(this.mBroadcastCount);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void finishBroadcast() {
        ArrayMap<IBinder, RemoteCallbackList<E>> arrayMap = this.mCallbacks;
        synchronized (arrayMap) {
            if (this.mBroadcastCount < 0) {
                IllegalStateException illegalStateException = new IllegalStateException("finishBroadcast() called outside of a broadcast");
                throw illegalStateException;
            }
            Object[] arrobject = this.mActiveBroadcast;
            if (arrobject != null) {
                int n = this.mBroadcastCount;
                for (int i = 0; i < n; ++i) {
                    arrobject[i] = null;
                }
            }
            this.mBroadcastCount = -1;
            return;
        }
    }

    public Object getBroadcastCookie(int n) {
        return ((Callback)this.mActiveBroadcast[n]).mCookie;
    }

    public E getBroadcastItem(int n) {
        return ((Callback)this.mActiveBroadcast[n]).mCallback;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Object getRegisteredCallbackCookie(int n) {
        ArrayMap<IBinder, RemoteCallbackList<E>> arrayMap = this.mCallbacks;
        synchronized (arrayMap) {
            if (!this.mKilled) return ((Callback)this.mCallbacks.valueAt((int)n)).mCookie;
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getRegisteredCallbackCount() {
        ArrayMap<IBinder, RemoteCallbackList<E>> arrayMap = this.mCallbacks;
        synchronized (arrayMap) {
            if (!this.mKilled) return this.mCallbacks.size();
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public E getRegisteredCallbackItem(int n) {
        ArrayMap<IBinder, RemoteCallbackList<E>> arrayMap = this.mCallbacks;
        synchronized (arrayMap) {
            if (this.mKilled) {
                return null;
            }
            Object e = ((Callback)this.mCallbacks.valueAt((int)n)).mCallback;
            return e;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void kill() {
        ArrayMap<IBinder, RemoteCallbackList<E>> arrayMap = this.mCallbacks;
        synchronized (arrayMap) {
            int n = this.mCallbacks.size() - 1;
            do {
                if (n < 0) {
                    this.mCallbacks.clear();
                    this.mKilled = true;
                    return;
                }
                Callback callback = (Callback)((Object)this.mCallbacks.valueAt(n));
                callback.mCallback.asBinder().unlinkToDeath(callback, 0);
                --n;
            } while (true);
        }
    }

    public void onCallbackDied(E e) {
    }

    public void onCallbackDied(E e, Object object) {
        this.onCallbackDied(e);
    }

    public boolean register(E e) {
        return this.register(e, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean register(E e, Object object) {
        ArrayMap<IBinder, RemoteCallbackList<E>> arrayMap = this.mCallbacks;
        synchronized (arrayMap) {
            if (this.mKilled) {
                return false;
            }
            this.logExcessiveCallbacks();
            IBinder iBinder = e.asBinder();
            try {
                Callback callback = new Callback(this, e, object);
                iBinder.linkToDeath(callback, 0);
                this.mCallbacks.put(iBinder, (RemoteCallbackList<E>)((Object)callback));
                return true;
            }
            catch (RemoteException remoteException) {
                return false;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean unregister(E object) {
        ArrayMap<IBinder, RemoteCallbackList<E>> arrayMap = this.mCallbacks;
        synchronized (arrayMap) {
            object = (Callback)((Object)this.mCallbacks.remove(object.asBinder()));
            if (object != null) {
                ((Callback)object).mCallback.asBinder().unlinkToDeath((IBinder.DeathRecipient)object, 0);
                return true;
            }
            return false;
        }
    }

    private final class Callback
    implements IBinder.DeathRecipient {
        final E mCallback;
        final Object mCookie;

        Callback(E e, Object object) {
            this.mCallback = e;
            this.mCookie = object;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void binderDied() {
            ArrayMap arrayMap = this$0.mCallbacks;
            synchronized (arrayMap) {
                this$0.mCallbacks.remove(this.mCallback.asBinder());
            }
            this$0.onCallbackDied(this.mCallback, this.mCookie);
        }
    }

}

