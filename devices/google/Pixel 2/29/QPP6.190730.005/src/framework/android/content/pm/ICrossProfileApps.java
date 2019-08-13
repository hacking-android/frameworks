/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.app.IApplicationThread;
import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import java.util.ArrayList;
import java.util.List;

public interface ICrossProfileApps
extends IInterface {
    public List<UserHandle> getTargetUserProfiles(String var1) throws RemoteException;

    public void startActivityAsUser(IApplicationThread var1, String var2, ComponentName var3, int var4, boolean var5) throws RemoteException;

    public static class Default
    implements ICrossProfileApps {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public List<UserHandle> getTargetUserProfiles(String string2) throws RemoteException {
            return null;
        }

        @Override
        public void startActivityAsUser(IApplicationThread iApplicationThread, String string2, ComponentName componentName, int n, boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICrossProfileApps {
        private static final String DESCRIPTOR = "android.content.pm.ICrossProfileApps";
        static final int TRANSACTION_getTargetUserProfiles = 2;
        static final int TRANSACTION_startActivityAsUser = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICrossProfileApps asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICrossProfileApps) {
                return (ICrossProfileApps)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICrossProfileApps getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "getTargetUserProfiles";
            }
            return "startActivityAsUser";
        }

        public static boolean setDefaultImpl(ICrossProfileApps iCrossProfileApps) {
            if (Proxy.sDefaultImpl == null && iCrossProfileApps != null) {
                Proxy.sDefaultImpl = iCrossProfileApps;
                return true;
            }
            return false;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public String getTransactionName(int n) {
            return Stub.getDefaultTransactionName(n);
        }

        @Override
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    parcel.writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getTargetUserProfiles(((Parcel)object).readString());
                parcel.writeNoException();
                parcel.writeTypedList(object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
            String string2 = ((Parcel)object).readString();
            ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
            n = ((Parcel)object).readInt();
            boolean bl = ((Parcel)object).readInt() != 0;
            this.startActivityAsUser(iApplicationThread, string2, componentName, n, bl);
            parcel.writeNoException();
            return true;
        }

        private static class Proxy
        implements ICrossProfileApps {
            public static ICrossProfileApps sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public List<UserHandle> getTargetUserProfiles(String arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getTargetUserProfiles((String)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(UserHandle.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void startActivityAsUser(IApplicationThread iApplicationThread, String string2, ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iApplicationThread != null ? iApplicationThread.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startActivityAsUser(iApplicationThread, string2, componentName, n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

