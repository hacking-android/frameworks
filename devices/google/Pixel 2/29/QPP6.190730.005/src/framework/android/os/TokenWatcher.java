/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public abstract class TokenWatcher {
    private volatile boolean mAcquired = false;
    private Handler mHandler;
    private int mNotificationQueue = -1;
    private Runnable mNotificationTask = new Runnable(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            WeakHashMap weakHashMap = TokenWatcher.this.mTokens;
            // MONITORENTER : weakHashMap
            int n = TokenWatcher.this.mNotificationQueue;
            TokenWatcher.this.mNotificationQueue = -1;
            // MONITOREXIT : weakHashMap
            if (n == 1) {
                TokenWatcher.this.acquired();
                return;
            }
            if (n != 0) return;
            TokenWatcher.this.released();
        }
    };
    private String mTag;
    private WeakHashMap<IBinder, Death> mTokens = new WeakHashMap();

    public TokenWatcher(Handler handler, String string2) {
        this.mHandler = handler;
        if (string2 == null) {
            string2 = "TokenWatcher";
        }
        this.mTag = string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ArrayList<String> dumpInternal() {
        ArrayList<String> arrayList = new ArrayList<String>();
        WeakHashMap<IBinder, Death> weakHashMap = this.mTokens;
        synchronized (weakHashMap) {
            Object object = this.mTokens.keySet();
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("Token count: ");
            ((StringBuilder)object2).append(this.mTokens.size());
            arrayList.add(((StringBuilder)object2).toString());
            int n = 0;
            object = object.iterator();
            while (object.hasNext()) {
                object2 = (IBinder)object.next();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("[");
                stringBuilder.append(n);
                stringBuilder.append("] ");
                stringBuilder.append(this.mTokens.get((Object)object2).tag);
                stringBuilder.append(" - ");
                stringBuilder.append(object2);
                arrayList.add(stringBuilder.toString());
                ++n;
            }
            return arrayList;
        }
    }

    private void sendNotificationLocked(boolean n) {
        int n2 = this.mNotificationQueue;
        if (n2 == -1) {
            this.mNotificationQueue = n;
            this.mHandler.post(this.mNotificationTask);
        } else if (n2 != n) {
            this.mNotificationQueue = -1;
            this.mHandler.removeCallbacks(this.mNotificationTask);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void acquire(IBinder iBinder, String string2) {
        WeakHashMap<IBinder, Death> weakHashMap = this.mTokens;
        synchronized (weakHashMap) {
            if (this.mTokens.containsKey(iBinder)) {
                return;
            }
            int n = this.mTokens.size();
            Death death = new Death(iBinder, string2);
            try {
                iBinder.linkToDeath(death, 0);
                this.mTokens.put(iBinder, death);
            }
            catch (RemoteException remoteException) {
                return;
            }
            if (n == 0 && !this.mAcquired) {
                this.sendNotificationLocked(true);
                this.mAcquired = true;
            }
            return;
        }
    }

    public abstract void acquired();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void cleanup(IBinder object, boolean bl) {
        WeakHashMap<IBinder, Death> weakHashMap = this.mTokens;
        synchronized (weakHashMap) {
            object = this.mTokens.remove(object);
            if (bl && object != null) {
                ((Death)object).token.unlinkToDeath((IBinder.DeathRecipient)object, 0);
                ((Death)object).token = null;
            }
            if (this.mTokens.size() == 0 && this.mAcquired) {
                this.sendNotificationLocked(false);
                this.mAcquired = false;
            }
            return;
        }
    }

    public void dump() {
        for (String string2 : this.dumpInternal()) {
            Log.i(this.mTag, string2);
        }
    }

    public void dump(PrintWriter printWriter) {
        Iterator<String> iterator = this.dumpInternal().iterator();
        while (iterator.hasNext()) {
            printWriter.println(iterator.next());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isAcquired() {
        WeakHashMap<IBinder, Death> weakHashMap = this.mTokens;
        synchronized (weakHashMap) {
            return this.mAcquired;
        }
    }

    public void release(IBinder iBinder) {
        this.cleanup(iBinder, true);
    }

    public abstract void released();

    private class Death
    implements IBinder.DeathRecipient {
        String tag;
        IBinder token;

        Death(IBinder iBinder, String string2) {
            this.token = iBinder;
            this.tag = string2;
        }

        @Override
        public void binderDied() {
            TokenWatcher.this.cleanup(this.token, false);
        }

        protected void finalize() throws Throwable {
            try {
                if (this.token != null) {
                    String string2 = TokenWatcher.this.mTag;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("cleaning up leaked reference: ");
                    stringBuilder.append(this.tag);
                    Log.w(string2, stringBuilder.toString());
                    TokenWatcher.this.release(this.token);
                }
                return;
            }
            finally {
                super.finalize();
            }
        }
    }

}

