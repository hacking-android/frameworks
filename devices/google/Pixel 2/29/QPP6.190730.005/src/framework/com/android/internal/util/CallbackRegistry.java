/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import java.util.ArrayList;
import java.util.List;

public class CallbackRegistry<C, T, A>
implements Cloneable {
    private static final String TAG = "CallbackRegistry";
    private List<C> mCallbacks = new ArrayList<C>();
    private long mFirst64Removed = 0L;
    private int mNotificationLevel;
    private final NotifierCallback<C, T, A> mNotifier;
    private long[] mRemainderRemoved;

    public CallbackRegistry(NotifierCallback<C, T, A> notifierCallback) {
        this.mNotifier = notifierCallback;
    }

    private boolean isRemovedLocked(int n) {
        boolean bl = true;
        boolean bl2 = true;
        if (n < 64) {
            if ((this.mFirst64Removed & 1L << n) == 0L) {
                bl2 = false;
            }
            return bl2;
        }
        long[] arrl = this.mRemainderRemoved;
        if (arrl == null) {
            return false;
        }
        int n2 = n / 64 - 1;
        if (n2 >= arrl.length) {
            return false;
        }
        bl2 = (arrl[n2] & 1L << n % 64) != 0L ? bl : false;
        return bl2;
    }

    private void notifyCallbacksLocked(T t, int n, A a, int n2, int n3, long l) {
        long l2 = 1L;
        while (n2 < n3) {
            if ((l & l2) == 0L) {
                this.mNotifier.onNotifyCallback(this.mCallbacks.get(n2), t, n, a);
            }
            l2 <<= 1;
            ++n2;
        }
    }

    private void notifyFirst64Locked(T t, int n, A a) {
        this.notifyCallbacksLocked(t, n, a, 0, Math.min(64, this.mCallbacks.size()), this.mFirst64Removed);
    }

    private void notifyRecurseLocked(T t, int n, A a) {
        int n2 = this.mCallbacks.size();
        long[] arrl = this.mRemainderRemoved;
        int n3 = arrl == null ? -1 : arrl.length - 1;
        this.notifyRemainderLocked(t, n, a, n3);
        this.notifyCallbacksLocked(t, n, a, (n3 + 2) * 64, n2, 0L);
    }

    private void notifyRemainderLocked(T t, int n, A a, int n2) {
        if (n2 < 0) {
            this.notifyFirst64Locked(t, n, a);
        } else {
            long l = this.mRemainderRemoved[n2];
            int n3 = (n2 + 1) * 64;
            int n4 = Math.min(this.mCallbacks.size(), n3 + 64);
            this.notifyRemainderLocked(t, n, a, n2 - 1);
            this.notifyCallbacksLocked(t, n, a, n3, n4, l);
        }
    }

    private void removeRemovedCallbacks(int n, long l) {
        long l2 = Long.MIN_VALUE;
        for (int i = n + 64 - 1; i >= n; --i) {
            if ((l & l2) != 0L) {
                this.mCallbacks.remove(i);
            }
            l2 >>>= 1;
        }
    }

    private void setRemovalBitLocked(int n) {
        if (n < 64) {
            this.mFirst64Removed |= 1L << n;
        } else {
            int n2 = n / 64 - 1;
            long[] arrl = this.mRemainderRemoved;
            if (arrl == null) {
                this.mRemainderRemoved = new long[this.mCallbacks.size() / 64];
            } else if (arrl.length < n2) {
                arrl = new long[this.mCallbacks.size() / 64];
                long[] arrl2 = this.mRemainderRemoved;
                System.arraycopy(arrl2, 0, arrl, 0, arrl2.length);
                this.mRemainderRemoved = arrl;
            }
            arrl = this.mRemainderRemoved;
            arrl[n2] = arrl[n2] | 1L << n % 64;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void add(C c) {
        synchronized (this) {
            int n = this.mCallbacks.lastIndexOf(c);
            if (n < 0 || this.isRemovedLocked(n)) {
                this.mCallbacks.add(c);
            }
            return;
        }
    }

    public void clear() {
        synchronized (this) {
            block6 : {
                if (this.mNotificationLevel == 0) {
                    this.mCallbacks.clear();
                    break block6;
                }
                if (this.mCallbacks.isEmpty()) break block6;
                for (int i = this.mCallbacks.size() - 1; i >= 0; --i) {
                    this.setRemovalBitLocked(i);
                }
            }
            return;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public CallbackRegistry<C, T, A> clone() {
        CallbackRegistry callbackRegistry;
        // MONITORENTER : this
        CallbackRegistry callbackRegistry2 = null;
        callbackRegistry2 = callbackRegistry = (CallbackRegistry)super.clone();
        callbackRegistry.mFirst64Removed = 0L;
        callbackRegistry2 = callbackRegistry;
        callbackRegistry.mRemainderRemoved = null;
        callbackRegistry2 = callbackRegistry;
        callbackRegistry.mNotificationLevel = 0;
        callbackRegistry2 = callbackRegistry;
        callbackRegistry2 = callbackRegistry;
        ArrayList<C> arrayList = new ArrayList<C>();
        callbackRegistry2 = callbackRegistry;
        callbackRegistry.mCallbacks = arrayList;
        callbackRegistry2 = callbackRegistry;
        int n = this.mCallbacks.size();
        int n2 = 0;
        while (n2 < n) {
            callbackRegistry2 = callbackRegistry;
            if (!this.isRemovedLocked(n2)) {
                callbackRegistry2 = callbackRegistry;
                callbackRegistry.mCallbacks.add(this.mCallbacks.get(n2));
            }
            ++n2;
        }
        return callbackRegistry;
        {
            catch (Throwable throwable) {
                throw throwable;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {}
            {
                cloneNotSupportedException.printStackTrace();
                // MONITOREXIT : this
            }
        }
        return callbackRegistry2;
    }

    public ArrayList<C> copyListeners() {
        synchronized (this) {
            ArrayList<C> arrayList = new ArrayList<C>(this.mCallbacks.size());
            int n = this.mCallbacks.size();
            for (int i = 0; i < n; ++i) {
                if (this.isRemovedLocked(i)) continue;
                arrayList.add(this.mCallbacks.get(i));
            }
            return arrayList;
        }
    }

    public boolean isEmpty() {
        synchronized (this) {
            int n;
            boolean bl;
            block8 : {
                block7 : {
                    bl = this.mCallbacks.isEmpty();
                    if (!bl) break block7;
                    return true;
                }
                n = this.mNotificationLevel;
                if (n != 0) break block8;
                return false;
            }
            int n2 = this.mCallbacks.size();
            for (n = 0; n < n2; ++n) {
                bl = this.isRemovedLocked(n);
                if (bl) continue;
                return false;
            }
            return true;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void notifyCallbacks(T t, int n, A a) {
        synchronized (this) {
            int n2;
            void var3_3;
            ++this.mNotificationLevel;
            this.notifyRecurseLocked(t, n2, var3_3);
            --this.mNotificationLevel;
            if (this.mNotificationLevel == 0) {
                if (this.mRemainderRemoved != null) {
                    for (n2 = this.mRemainderRemoved.length - 1; n2 >= 0; --n2) {
                        long l = this.mRemainderRemoved[n2];
                        if (l == 0L) continue;
                        this.removeRemovedCallbacks((n2 + 1) * 64, l);
                        this.mRemainderRemoved[n2] = 0L;
                    }
                }
                if (this.mFirst64Removed != 0L) {
                    this.removeRemovedCallbacks(0, this.mFirst64Removed);
                    this.mFirst64Removed = 0L;
                }
            }
            return;
        }
    }

    public void remove(C c) {
        synchronized (this) {
            if (this.mNotificationLevel == 0) {
                this.mCallbacks.remove(c);
            } else {
                int n = this.mCallbacks.lastIndexOf(c);
                if (n >= 0) {
                    this.setRemovalBitLocked(n);
                }
            }
            return;
        }
    }

    public static abstract class NotifierCallback<C, T, A> {
        public abstract void onNotifyCallback(C var1, T var2, int var3, A var4);
    }

}

