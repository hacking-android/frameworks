/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.SystemApi;
import android.os.HwParcel;
import android.os.IHwInterface;
import android.os.RemoteException;

@SystemApi
public interface IHwBinder {
    public boolean linkToDeath(DeathRecipient var1, long var2);

    public IHwInterface queryLocalInterface(String var1);

    public void transact(int var1, HwParcel var2, HwParcel var3, int var4) throws RemoteException;

    public boolean unlinkToDeath(DeathRecipient var1);

    public static interface DeathRecipient {
        public void serviceDied(long var1);
    }

}

