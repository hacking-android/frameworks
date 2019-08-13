/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

public class BinderDeathDispatcher<T extends IInterface> {
    private static final String TAG = "BinderDeathDispatcher";
    private final Object mLock = new Object();
    @GuardedBy(value={"mLock"})
    private final ArrayMap<IBinder, BinderDeathDispatcher<T>> mTargets = new ArrayMap();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dump(PrintWriter printWriter, String object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            printWriter.print((String)object);
            printWriter.print("# of watched binders: ");
            printWriter.println(this.mTargets.size());
            printWriter.print((String)object);
            printWriter.print("# of death recipients: ");
            int n = 0;
            object = this.mTargets.values().iterator();
            do {
                if (!object.hasNext()) {
                    printWriter.println(n);
                    return;
                }
                n += ((RecipientsInfo)object.next()).mRecipients.size();
            } while (true);
        }
    }

    @VisibleForTesting
    public ArrayMap<IBinder, BinderDeathDispatcher<T>> getTargetsForTest() {
        return this.mTargets;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int linkToDeath(T object, IBinder.DeathRecipient deathRecipient) {
        IBinder iBinder = object.asBinder();
        Object object2 = this.mLock;
        synchronized (object2) {
            RecipientsInfo recipientsInfo = (RecipientsInfo)((Object)this.mTargets.get(iBinder));
            object = recipientsInfo;
            if (recipientsInfo == null) {
                object = new Object(iBinder);
                try {
                    iBinder.linkToDeath((IBinder.DeathRecipient)object, 0);
                    this.mTargets.put(iBinder, (BinderDeathDispatcher<T>)object);
                }
                catch (RemoteException remoteException) {
                    return -1;
                }
            }
            ((RecipientsInfo)object).mRecipients.add(deathRecipient);
            return ((RecipientsInfo)object).mRecipients.size();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unlinkToDeath(T object, IBinder.DeathRecipient deathRecipient) {
        Object object2 = object.asBinder();
        object = this.mLock;
        synchronized (object) {
            object2 = (RecipientsInfo)((Object)this.mTargets.get(object2));
            if (object2 == null) {
                return;
            }
            if (((RecipientsInfo)object2).mRecipients.remove(deathRecipient) && ((RecipientsInfo)object2).mRecipients.size() == 0) {
                ((RecipientsInfo)object2).mTarget.unlinkToDeath((IBinder.DeathRecipient)object2, 0);
                this.mTargets.remove(((RecipientsInfo)object2).mTarget);
            }
            return;
        }
    }

    @VisibleForTesting
    class RecipientsInfo
    implements IBinder.DeathRecipient {
        @GuardedBy(value={"mLock"})
        ArraySet<IBinder.DeathRecipient> mRecipients = new ArraySet();
        final IBinder mTarget;

        private RecipientsInfo(IBinder iBinder) {
            this.mTarget = iBinder;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void binderDied() {
            Object object = BinderDeathDispatcher.this.mLock;
            // MONITORENTER : object
            ArraySet<IBinder.DeathRecipient> arraySet = this.mRecipients;
            this.mRecipients = null;
            BinderDeathDispatcher.this.mTargets.remove(this.mTarget);
            // MONITOREXIT : object
            if (arraySet == null) {
                return;
            }
            int n = arraySet.size();
            int n2 = 0;
            while (n2 < n) {
                arraySet.valueAt(n2).binderDied();
                ++n2;
            }
        }
    }

}

