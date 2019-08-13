/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.NativeAllocationRegistry
 */
package android.os;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.RemoteException;
import java.util.NoSuchElementException;
import libcore.util.NativeAllocationRegistry;

@SystemApi
public abstract class HwBinder
implements IHwBinder {
    private static final String TAG = "HwBinder";
    private static final NativeAllocationRegistry sNativeRegistry;
    private long mNativeContext;

    static {
        long l = HwBinder.native_init();
        sNativeRegistry = new NativeAllocationRegistry(HwBinder.class.getClassLoader(), l, 128L);
    }

    public HwBinder() {
        this.native_setup();
        sNativeRegistry.registerNativeAllocation((Object)this, this.mNativeContext);
    }

    public static final native void configureRpcThreadpool(long var0, boolean var2);

    public static void enableInstrumentation() {
        HwBinder.native_report_sysprop_change();
    }

    public static final IHwBinder getService(String string2, String string3) throws RemoteException, NoSuchElementException {
        return HwBinder.getService(string2, string3, false);
    }

    public static final native IHwBinder getService(String var0, String var1, boolean var2) throws RemoteException, NoSuchElementException;

    public static final native void joinRpcThreadpool();

    private static final native long native_init();

    private static native void native_report_sysprop_change();

    private final native void native_setup();

    @UnsupportedAppUsage
    public static void reportSyspropChanged() {
        HwBinder.native_report_sysprop_change();
    }

    public abstract void onTransact(int var1, HwParcel var2, HwParcel var3, int var4) throws RemoteException;

    public final native void registerService(String var1) throws RemoteException;

    @Override
    public final native void transact(int var1, HwParcel var2, HwParcel var3, int var4) throws RemoteException;
}

