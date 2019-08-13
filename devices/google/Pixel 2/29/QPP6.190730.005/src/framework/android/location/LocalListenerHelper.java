/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.content.Context;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

abstract class LocalListenerHelper<TListener> {
    private final Context mContext;
    private final HashMap<TListener, Handler> mListeners = new HashMap();
    private final String mTag;

    protected LocalListenerHelper(Context context, String string2) {
        Preconditions.checkNotNull(string2);
        this.mContext = context;
        this.mTag = string2;
    }

    private void executeOperation(ListenerOperation<TListener> listenerOperation, TListener TListener) {
        try {
            listenerOperation.execute(TListener);
        }
        catch (RemoteException remoteException) {
            Log.e(this.mTag, "Error in monitored listener.", remoteException);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public boolean add(TListener var1_1, Handler var2_3) {
        Preconditions.checkNotNull(var1_1);
        var3_4 = this.mListeners;
        // MONITORENTER : var3_4
        var4_5 = this.mListeners.isEmpty();
        if (var4_5) {
            try {
                var4_5 = this.registerWithServer();
                ** if (var4_5) goto lbl-1000
            }
            catch (RemoteException var1_2) {
                Log.e(this.mTag, "Error handling first listener.", var1_2);
                // MONITOREXIT : var3_4
                return false;
            }
lbl-1000: // 1 sources:
            {
                Log.e(this.mTag, "Unable to register listener transport.");
                // MONITOREXIT : var3_4
                return false;
            }
lbl-1000: // 1 sources:
            {
            }
        }
        if (this.mListeners.containsKey(var1_1)) {
            // MONITOREXIT : var3_4
            return true;
        }
        this.mListeners.put(var1_1, var2_3);
        // MONITOREXIT : var3_4
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void foreach(final ListenerOperation<TListener> listenerOperation) {
        Object object;
        Object object2 = this.mListeners;
        synchronized (object2) {
            object = new ArrayList(this.mListeners.entrySet());
        }
        object = object.iterator();
        while (object.hasNext()) {
            object2 = (Map.Entry)object.next();
            if (object2.getValue() == null) {
                this.executeOperation(listenerOperation, object2.getKey());
                continue;
            }
            ((Handler)object2.getValue()).post(new Runnable((Map.Entry)object2){
                final /* synthetic */ Map.Entry val$listener;
                {
                    this.val$listener = entry;
                }

                @Override
                public void run() {
                    LocalListenerHelper.this.executeOperation(listenerOperation, this.val$listener.getKey());
                }
            });
        }
        return;
    }

    protected Context getContext() {
        return this.mContext;
    }

    protected abstract boolean registerWithServer() throws RemoteException;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void remove(TListener TListener) {
        Preconditions.checkNotNull(TListener);
        HashMap<TListener, Handler> hashMap = this.mListeners;
        synchronized (hashMap) {
            boolean bl = this.mListeners.containsKey(TListener);
            this.mListeners.remove(TListener);
            if (!bl) return;
            bl = this.mListeners.isEmpty();
            if (!bl) return;
            boolean bl2 = true;
            if (!bl2) return;
            try {
                this.unregisterFromServer();
            }
            catch (RemoteException remoteException) {
                Log.v(this.mTag, "Error handling last listener removal", remoteException);
            }
            return;
        }
    }

    protected abstract void unregisterFromServer() throws RemoteException;

    protected static interface ListenerOperation<TListener> {
        public void execute(TListener var1) throws RemoteException;
    }

}

