/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IIntentReceiver
extends IInterface {
    @UnsupportedAppUsage
    public void performReceive(Intent var1, int var2, String var3, Bundle var4, boolean var5, boolean var6, int var7) throws RemoteException;

    public static class Default
    implements IIntentReceiver {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void performReceive(Intent intent, int n, String string2, Bundle bundle, boolean bl, boolean bl2, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IIntentReceiver {
        private static final String DESCRIPTOR = "android.content.IIntentReceiver";
        static final int TRANSACTION_performReceive = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IIntentReceiver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IIntentReceiver) {
                return (IIntentReceiver)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IIntentReceiver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "performReceive";
        }

        public static boolean setDefaultImpl(IIntentReceiver iIntentReceiver) {
            if (Proxy.sDefaultImpl == null && iIntentReceiver != null) {
                Proxy.sDefaultImpl = iIntentReceiver;
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
        public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, (Parcel)object, n2);
                }
                ((Parcel)object).writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            object = parcel.readInt() != 0 ? Intent.CREATOR.createFromParcel(parcel) : null;
            n = parcel.readInt();
            String string2 = parcel.readString();
            Bundle bundle = parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null;
            boolean bl = parcel.readInt() != 0;
            boolean bl2 = parcel.readInt() != 0;
            this.performReceive((Intent)object, n, string2, bundle, bl, bl2, parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IIntentReceiver {
            public static IIntentReceiver sDefaultImpl;
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

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void performReceive(Intent intent, int n, String string2, Bundle bundle, boolean bl, boolean bl2, int n2) throws RemoteException {
                Parcel parcel;
                void var1_7;
                block16 : {
                    int n3;
                    block15 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        n3 = 0;
                        if (intent != null) {
                            parcel.writeInt(1);
                            intent.writeToParcel(parcel, 0);
                            break block15;
                        }
                        parcel.writeInt(0);
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel.writeString(string2);
                        if (bundle != null) {
                            parcel.writeInt(1);
                            bundle.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        int n4 = bl ? 1 : 0;
                        parcel.writeInt(n4);
                        n4 = n3;
                        if (bl2) {
                            n4 = 1;
                        }
                        parcel.writeInt(n4);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().performReceive(intent, n, string2, bundle, bl, bl2, n2);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_7;
            }
        }

    }

}

