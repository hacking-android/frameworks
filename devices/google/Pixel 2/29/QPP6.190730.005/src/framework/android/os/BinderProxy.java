/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.-$
 *  android.os.-$$Lambda
 *  android.os.-$$Lambda$BinderProxy
 *  android.os.-$$Lambda$BinderProxy$ProxyMap
 *  android.os.-$$Lambda$BinderProxy$ProxyMap$aKNUVKkR8bNu2XRFxaO2PW1AFBA
 *  libcore.util.NativeAllocationRegistry
 */
package android.os;

import android.os.-$;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.Trace;
import android.os._$$Lambda$BinderProxy$ProxyMap$aKNUVKkR8bNu2XRFxaO2PW1AFBA;
import android.util.Log;
import android.util.SparseIntArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.os.BinderInternal;
import java.io.FileDescriptor;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import libcore.util.NativeAllocationRegistry;

public final class BinderProxy
implements IBinder {
    private static final int NATIVE_ALLOCATION_SIZE = 1000;
    @GuardedBy(value={"sProxyMap"})
    private static final ProxyMap sProxyMap;
    private static volatile Binder.ProxyTransactListener sTransactListener;
    private final long mNativeData;
    volatile boolean mWarnOnBlocking = Binder.sWarnOnBlocking;

    static {
        sTransactListener = null;
        sProxyMap = new ProxyMap();
    }

    private BinderProxy(long l) {
        this.mNativeData = l;
    }

    static /* synthetic */ long access$500() {
        return BinderProxy.getNativeFinalizer();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void dumpProxyDebugInfo() {
        if (!Build.IS_DEBUGGABLE) return;
        ProxyMap proxyMap = sProxyMap;
        synchronized (proxyMap) {
            BinderProxy.sProxyMap.dumpProxyInterfaceCounts();
            BinderProxy.sProxyMap.dumpPerUidProxyCounts();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static BinderProxy getInstance(long l, long l2) {
        ProxyMap proxyMap = sProxyMap;
        synchronized (proxyMap) {
            BinderProxy binderProxy;
            block6 : {
                try {
                    binderProxy = sProxyMap.get(l2);
                    if (binderProxy == null) break block6;
                }
                catch (Throwable throwable) {
                    NativeAllocationRegistry.applyFreeFunction((long)NoImagePreloadHolder.sNativeFinalizer, (long)l);
                    throw throwable;
                }
                return binderProxy;
            }
            binderProxy = new BinderProxy(l);
            NoImagePreloadHolder.sRegistry.registerNativeAllocation((Object)binderProxy, l);
            sProxyMap.set(l2, binderProxy);
            return binderProxy;
        }
    }

    private static native long getNativeFinalizer();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int getProxyCount() {
        ProxyMap proxyMap = sProxyMap;
        synchronized (proxyMap) {
            return BinderProxy.sProxyMap.size();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static InterfaceCount[] getSortedInterfaceCounts(int n) {
        ProxyMap proxyMap = sProxyMap;
        synchronized (proxyMap) {
            return BinderProxy.sProxyMap.getSortedInterfaceCounts(n);
        }
    }

    private static void sendDeathNotice(IBinder.DeathRecipient deathRecipient) {
        try {
            deathRecipient.binderDied();
        }
        catch (RuntimeException runtimeException) {
            Log.w("BinderNative", "Uncaught exception from death notification", runtimeException);
        }
    }

    public static void setTransactListener(Binder.ProxyTransactListener proxyTransactListener) {
        sTransactListener = proxyTransactListener;
    }

    @Override
    public void dump(FileDescriptor fileDescriptor, String[] arrstring) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        parcel.writeFileDescriptor(fileDescriptor);
        parcel.writeStringArray(arrstring);
        try {
            this.transact(1598311760, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public void dumpAsync(FileDescriptor fileDescriptor, String[] arrstring) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        parcel.writeFileDescriptor(fileDescriptor);
        parcel.writeStringArray(arrstring);
        try {
            this.transact(1598311760, parcel, parcel2, 1);
            return;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public native String getInterfaceDescriptor() throws RemoteException;

    @Override
    public native boolean isBinderAlive();

    @Override
    public native void linkToDeath(IBinder.DeathRecipient var1, int var2) throws RemoteException;

    @Override
    public native boolean pingBinder();

    @Override
    public IInterface queryLocalInterface(String string2) {
        return null;
    }

    @Override
    public void shellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] arrstring, ShellCallback shellCallback, ResultReceiver resultReceiver) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        parcel.writeFileDescriptor(fileDescriptor);
        parcel.writeFileDescriptor(fileDescriptor2);
        parcel.writeFileDescriptor(fileDescriptor3);
        parcel.writeStringArray(arrstring);
        ShellCallback.writeToParcel(shellCallback, parcel);
        resultReceiver.writeToParcel(parcel, 0);
        try {
            this.transact(1598246212, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public boolean transact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        Object object;
        Object object2;
        boolean bl;
        Binder.checkParcel(this, n, parcel, "Unreasonably large binder buffer");
        if (this.mWarnOnBlocking && (n2 & 1) == 0) {
            this.mWarnOnBlocking = false;
            Log.w("Binder", "Outgoing transactions from this process must be FLAG_ONEWAY", new Throwable());
        }
        if (bl = Binder.isTracingEnabled()) {
            object = new Throwable();
            Binder.getTransactionTracker().addTrace((Throwable)object);
            object = ((Throwable)object).getStackTrace()[1];
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(((StackTraceElement)object).getClassName());
            ((StringBuilder)object2).append(".");
            ((StringBuilder)object2).append(((StackTraceElement)object).getMethodName());
            Trace.traceBegin(1L, ((StringBuilder)object2).toString());
        }
        Binder.ProxyTransactListener proxyTransactListener = sTransactListener;
        object = null;
        if (proxyTransactListener != null) {
            int n3 = Binder.getCallingWorkSourceUid();
            object2 = proxyTransactListener.onTransactStarted(this, n);
            int n4 = Binder.getCallingWorkSourceUid();
            object = object2;
            if (n3 != n4) {
                parcel.replaceCallingWorkSourceUid(n4);
                object = object2;
            }
        }
        try {
            boolean bl2 = this.transactNative(n, parcel, parcel2, n2);
            return bl2;
        }
        finally {
            if (proxyTransactListener != null) {
                proxyTransactListener.onTransactEnded(object);
            }
            if (bl) {
                Trace.traceEnd(1L);
            }
        }
    }

    public native boolean transactNative(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException;

    @Override
    public native boolean unlinkToDeath(IBinder.DeathRecipient var1, int var2);

    public static final class InterfaceCount {
        private final int mCount;
        private final String mInterfaceName;

        InterfaceCount(String string2, int n) {
            this.mInterfaceName = string2;
            this.mCount = n;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.mInterfaceName);
            stringBuilder.append(" x");
            stringBuilder.append(Integer.toString(this.mCount));
            return stringBuilder.toString();
        }
    }

    private static class NoImagePreloadHolder {
        public static final long sNativeFinalizer = BinderProxy.access$500();
        public static final NativeAllocationRegistry sRegistry = new NativeAllocationRegistry(BinderProxy.class.getClassLoader(), sNativeFinalizer, 1000L);

        private NoImagePreloadHolder() {
        }
    }

    private static final class ProxyMap {
        private static final int CRASH_AT_SIZE = 20000;
        private static final int LOG_MAIN_INDEX_SIZE = 8;
        private static final int MAIN_INDEX_MASK = 255;
        private static final int MAIN_INDEX_SIZE = 256;
        static final int MAX_NUM_INTERFACES_TO_DUMP = 10;
        private static final int WARN_INCREMENT = 10;
        private final Long[][] mMainIndexKeys = new Long[256][];
        private final ArrayList<WeakReference<BinderProxy>>[] mMainIndexValues = new ArrayList[256];
        private int mRandom;
        private int mWarnBucketSize = 20;

        private ProxyMap() {
        }

        private void dumpPerUidProxyCounts() {
            SparseIntArray sparseIntArray = BinderInternal.nGetBinderProxyPerUidCounts();
            if (sparseIntArray.size() == 0) {
                return;
            }
            Log.d("Binder", "Per Uid Binder Proxy Counts:");
            for (int i = 0; i < sparseIntArray.size(); ++i) {
                int n = sparseIntArray.keyAt(i);
                int n2 = sparseIntArray.valueAt(i);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("UID : ");
                stringBuilder.append(n);
                stringBuilder.append("  count = ");
                stringBuilder.append(n2);
                Log.d("Binder", stringBuilder.toString());
            }
        }

        private void dumpProxyInterfaceCounts() {
            InterfaceCount[] arrinterfaceCount = this.getSortedInterfaceCounts(10);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("BinderProxy descriptor histogram (top ");
            stringBuilder.append(Integer.toString(10));
            stringBuilder.append("):");
            Log.v("Binder", stringBuilder.toString());
            for (int i = 0; i < arrinterfaceCount.length; ++i) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(" #");
                stringBuilder.append(i + 1);
                stringBuilder.append(": ");
                stringBuilder.append(arrinterfaceCount[i]);
                Log.v("Binder", stringBuilder.toString());
            }
        }

        /*
         * WARNING - void declaration
         */
        private InterfaceCount[] getSortedInterfaceCounts(int n) {
            if (n >= 0) {
                Object object;
                HashMap<void, Integer> hashMap = new HashMap<void, Integer>();
                for (ArrayList<WeakReference<BinderProxy>> throwable : this.mMainIndexValues) {
                    if (throwable == null) continue;
                    Iterator<WeakReference<BinderProxy>> iterator = throwable.iterator();
                    while (iterator.hasNext()) {
                        void var6_15;
                        block13 : {
                            BinderProxy binderProxy = (BinderProxy)iterator.next().get();
                            if (binderProxy == null) {
                                String string2 = "<cleared weak-ref>";
                            } else {
                                block12 : {
                                    object = binderProxy.getInterfaceDescriptor();
                                    if (object == null) break block12;
                                    Object object2 = object;
                                    if (!((String)object).isEmpty()) break block13;
                                }
                                Object object3 = object;
                                try {
                                    if (!binderProxy.isBinderAlive()) {
                                        String string3 = "<proxy to dead node>";
                                    }
                                }
                                catch (Throwable throwable2) {
                                    String string4 = "<exception during getDescriptor>";
                                }
                            }
                        }
                        object = (Integer)hashMap.get(var6_15);
                        if (object == null) {
                            hashMap.put(var6_15, 1);
                            continue;
                        }
                        hashMap.put(var6_15, (Integer)object + 1);
                    }
                }
                object = hashMap.entrySet().toArray(new Map.Entry[hashMap.size()]);
                Arrays.sort(object, _$$Lambda$BinderProxy$ProxyMap$aKNUVKkR8bNu2XRFxaO2PW1AFBA.INSTANCE);
                int n2 = Math.min(n, ((Map.Entry[])object).length);
                InterfaceCount[] arrinterfaceCount = new InterfaceCount[n2];
                for (n = 0; n < n2; ++n) {
                    arrinterfaceCount[n] = new InterfaceCount((String)object[n].getKey(), (Integer)object[n].getValue());
                }
                return arrinterfaceCount;
            }
            throw new IllegalArgumentException("negative interface count");
        }

        private static int hash(long l) {
            return (int)(l >> 2 ^ l >> 10) & 255;
        }

        static /* synthetic */ int lambda$getSortedInterfaceCounts$0(Map.Entry entry, Map.Entry entry2) {
            return ((Integer)entry2.getValue()).compareTo((Integer)entry.getValue());
        }

        private void remove(int n, int n2) {
            Long[] arrlong = this.mMainIndexKeys[n];
            ArrayList<WeakReference<BinderProxy>> arrayList = this.mMainIndexValues[n];
            n = arrayList.size();
            if (n2 != n - 1) {
                arrlong[n2] = arrlong[n - 1];
                arrayList.set(n2, arrayList.get(n - 1));
            }
            arrayList.remove(n - 1);
        }

        private int size() {
            int n = 0;
            for (ArrayList<WeakReference<BinderProxy>> arrayList : this.mMainIndexValues) {
                int n2 = n;
                if (arrayList != null) {
                    n2 = n + arrayList.size();
                }
                n = n2;
            }
            return n;
        }

        private int unclearedSize() {
            int n = 0;
            for (ArrayList<WeakReference<BinderProxy>> arrayList : this.mMainIndexValues) {
                int n2 = n;
                if (arrayList != null) {
                    Iterator<WeakReference<BinderProxy>> iterator = arrayList.iterator();
                    do {
                        n2 = n;
                        if (!iterator.hasNext()) break;
                        n2 = n;
                        if (iterator.next().get() != null) {
                            n2 = n + 1;
                        }
                        n = n2;
                    } while (true);
                }
                n = n2;
            }
            return n;
        }

        BinderProxy get(long l) {
            int n = ProxyMap.hash(l);
            Long[] arrlong = this.mMainIndexKeys[n];
            if (arrlong == null) {
                return null;
            }
            Object object = this.mMainIndexValues[n];
            int n2 = ((ArrayList)object).size();
            for (int i = 0; i < n2; ++i) {
                if (l != arrlong[i]) continue;
                if ((object = (BinderProxy)((ArrayList)object).get(i).get()) != null) {
                    return object;
                }
                this.remove(n, i);
                return null;
            }
            return null;
        }

        void set(long l, BinderProxy object) {
            int n;
            int n2 = ProxyMap.hash(l);
            ArrayList<WeakReference<BinderProxy>>[] arrarrayList = this.mMainIndexValues;
            ArrayList<WeakReference<BinderProxy>> arrayList = arrarrayList[n2];
            ArrayList<WeakReference<BinderProxy>> arrayList2 = arrayList;
            if (arrayList == null) {
                arrayList2 = new ArrayList();
                arrarrayList[n2] = arrayList2;
                this.mMainIndexKeys[n2] = new Long[1];
            }
            int n3 = arrayList2.size();
            object = new WeakReference<BinderProxy>((BinderProxy)object);
            for (n = 0; n < n3; ++n) {
                if (arrayList2.get(n).get() != null) continue;
                arrayList2.set(n, (WeakReference<BinderProxy>)object);
                this.mMainIndexKeys[n2][n] = l;
                if (n < n3 - 1) {
                    int n4;
                    this.mRandom = n4 = this.mRandom + 1;
                    if (arrayList2.get(n + 1 + (n3 = Math.floorMod(n4, n3 - (n + 1)))).get() == null) {
                        this.remove(n2, n + 1 + n3);
                    }
                }
                return;
            }
            arrayList2.add(n3, (WeakReference<BinderProxy>)object);
            object = this.mMainIndexKeys[n2];
            if (((Long[])object).length == n3) {
                arrayList2 = new Long[n3 / 2 + n3 + 2];
                System.arraycopy(object, 0, arrayList2, 0, n3);
                arrayList2[n3] = l;
                this.mMainIndexKeys[n2] = arrayList2;
            } else {
                object[n3] = l;
            }
            if (n3 >= this.mWarnBucketSize) {
                n = this.size();
                object = new StringBuilder();
                ((StringBuilder)object).append("BinderProxy map growth! bucket size = ");
                ((StringBuilder)object).append(n3);
                ((StringBuilder)object).append(" total = ");
                ((StringBuilder)object).append(n);
                Log.v("Binder", ((StringBuilder)object).toString());
                this.mWarnBucketSize += 10;
                if (Build.IS_DEBUGGABLE && n >= 20000) {
                    n2 = this.unclearedSize();
                    if (n2 < 20000) {
                        if (n > n2 * 3 / 2) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("BinderProxy map has many cleared entries: ");
                            ((StringBuilder)object).append(n - n2);
                            ((StringBuilder)object).append(" of ");
                            ((StringBuilder)object).append(n);
                            ((StringBuilder)object).append(" are cleared");
                            Log.v("Binder", ((StringBuilder)object).toString());
                        }
                    } else {
                        this.dumpProxyInterfaceCounts();
                        this.dumpPerUidProxyCounts();
                        Runtime.getRuntime().gc();
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Binder ProxyMap has too many entries: ");
                        ((StringBuilder)object).append(n);
                        ((StringBuilder)object).append(" (total), ");
                        ((StringBuilder)object).append(n2);
                        ((StringBuilder)object).append(" (uncleared), ");
                        ((StringBuilder)object).append(this.unclearedSize());
                        ((StringBuilder)object).append(" (uncleared after GC). BinderProxy leak?");
                        throw new AssertionError((Object)((StringBuilder)object).toString());
                    }
                }
            }
        }
    }

}

