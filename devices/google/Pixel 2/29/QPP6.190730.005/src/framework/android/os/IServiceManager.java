/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IPermissionController;
import android.os.RemoteException;

public interface IServiceManager
extends IInterface {
    public static final int ADD_SERVICE_TRANSACTION = 3;
    public static final int CHECK_SERVICES_TRANSACTION = 5;
    public static final int CHECK_SERVICE_TRANSACTION = 2;
    public static final int DUMP_FLAG_PRIORITY_ALL = 15;
    public static final int DUMP_FLAG_PRIORITY_CRITICAL = 1;
    public static final int DUMP_FLAG_PRIORITY_DEFAULT = 8;
    public static final int DUMP_FLAG_PRIORITY_HIGH = 2;
    public static final int DUMP_FLAG_PRIORITY_NORMAL = 4;
    public static final int DUMP_FLAG_PROTO = 16;
    public static final int GET_SERVICE_TRANSACTION = 1;
    public static final int LIST_SERVICES_TRANSACTION = 4;
    public static final int SET_PERMISSION_CONTROLLER_TRANSACTION = 6;
    public static final String descriptor = "android.os.IServiceManager";

    public void addService(String var1, IBinder var2, boolean var3, int var4) throws RemoteException;

    @UnsupportedAppUsage
    public IBinder checkService(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public IBinder getService(String var1) throws RemoteException;

    public String[] listServices(int var1) throws RemoteException;

    public void setPermissionController(IPermissionController var1) throws RemoteException;
}

