/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.NativeAllocationRegistry
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.RemoteException;
import libcore.util.NativeAllocationRegistry;

public class HwRemoteBinder
implements IHwBinder {
    private static final String TAG = "HwRemoteBinder";
    private static final NativeAllocationRegistry sNativeRegistry;
    private long mNativeContext;

    static {
        long l = HwRemoteBinder.native_init();
        sNativeRegistry = new NativeAllocationRegistry(HwRemoteBinder.class.getClassLoader(), l, 128L);
    }

    @UnsupportedAppUsage
    public HwRemoteBinder() {
        this.native_setup_empty();
        sNativeRegistry.registerNativeAllocation((Object)this, this.mNativeContext);
    }

    private static final native long native_init();

    private final native void native_setup_empty();

    private static final void sendDeathNotice(IHwBinder.DeathRecipient deathRecipient, long l) {
        deathRecipient.serviceDied(l);
    }

    public final native boolean equals(Object var1);

    public final native int hashCode();

    @Override
    public native boolean linkToDeath(IHwBinder.DeathRecipient var1, long var2);

    @Override
    public IHwInterface queryLocalInterface(String string2) {
        return null;
    }

    @Override
    public final native void transact(int var1, HwParcel var2, HwParcel var3, int var4) throws RemoteException;

    @Override
    public native boolean unlinkToDeath(IHwBinder.DeathRecipient var1);
}

