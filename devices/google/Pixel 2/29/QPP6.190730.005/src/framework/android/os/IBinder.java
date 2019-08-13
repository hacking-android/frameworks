/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import java.io.FileDescriptor;

public interface IBinder {
    public static final int DUMP_TRANSACTION = 1598311760;
    public static final int FIRST_CALL_TRANSACTION = 1;
    public static final int FLAG_ONEWAY = 1;
    public static final int INTERFACE_TRANSACTION = 1598968902;
    public static final int LAST_CALL_TRANSACTION = 16777215;
    public static final int LIKE_TRANSACTION = 1598835019;
    public static final int MAX_IPC_SIZE = 65536;
    public static final int PING_TRANSACTION = 1599098439;
    public static final int SHELL_COMMAND_TRANSACTION = 1598246212;
    @UnsupportedAppUsage
    public static final int SYSPROPS_TRANSACTION = 1599295570;
    public static final int TWEET_TRANSACTION = 1599362900;

    public void dump(FileDescriptor var1, String[] var2) throws RemoteException;

    public void dumpAsync(FileDescriptor var1, String[] var2) throws RemoteException;

    public String getInterfaceDescriptor() throws RemoteException;

    public boolean isBinderAlive();

    public void linkToDeath(DeathRecipient var1, int var2) throws RemoteException;

    public boolean pingBinder();

    public IInterface queryLocalInterface(String var1);

    public void shellCommand(FileDescriptor var1, FileDescriptor var2, FileDescriptor var3, String[] var4, ShellCallback var5, ResultReceiver var6) throws RemoteException;

    public boolean transact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException;

    public boolean unlinkToDeath(DeathRecipient var1, int var2);

    public static interface DeathRecipient {
        public void binderDied();
    }

}

