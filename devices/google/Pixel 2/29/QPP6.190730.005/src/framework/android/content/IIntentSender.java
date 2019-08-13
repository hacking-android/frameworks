/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.content.IIntentReceiver;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IIntentSender
extends IInterface {
    public void send(int var1, Intent var2, String var3, IBinder var4, IIntentReceiver var5, String var6, Bundle var7) throws RemoteException;

    public static class Default
    implements IIntentSender {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void send(int n, Intent intent, String string2, IBinder iBinder, IIntentReceiver iIntentReceiver, String string3, Bundle bundle) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IIntentSender {
        private static final String DESCRIPTOR = "android.content.IIntentSender";
        static final int TRANSACTION_send = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IIntentSender asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IIntentSender) {
                return (IIntentSender)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IIntentSender getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "send";
        }

        public static boolean setDefaultImpl(IIntentSender iIntentSender) {
            if (Proxy.sDefaultImpl == null && iIntentSender != null) {
                Proxy.sDefaultImpl = iIntentSender;
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
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                }
                ((Parcel)object2).writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            n = ((Parcel)object).readInt();
            object2 = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
            String string2 = ((Parcel)object).readString();
            IBinder iBinder = ((Parcel)object).readStrongBinder();
            IIntentReceiver iIntentReceiver = IIntentReceiver.Stub.asInterface(((Parcel)object).readStrongBinder());
            String string3 = ((Parcel)object).readString();
            object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
            this.send(n, (Intent)object2, string2, iBinder, iIntentReceiver, string3, (Bundle)object);
            return true;
        }

        private static class Proxy
        implements IIntentSender {
            public static IIntentSender sDefaultImpl;
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
            public void send(int n, Intent intent, String string2, IBinder iBinder, IIntentReceiver iIntentReceiver, String string3, Bundle bundle) throws RemoteException {
                Parcel parcel;
                void var2_8;
                block15 : {
                    block14 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel.writeInt(n);
                            if (intent != null) {
                                parcel.writeInt(1);
                                intent.writeToParcel(parcel, 0);
                                break block14;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeStrongBinder(iBinder);
                        IBinder iBinder2 = iIntentReceiver != null ? iIntentReceiver.asBinder() : null;
                        parcel.writeStrongBinder(iBinder2);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeString(string3);
                        if (bundle != null) {
                            parcel.writeInt(1);
                            bundle.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().send(n, intent, string2, iBinder, iIntentReceiver, string3, bundle);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var2_8;
            }
        }

    }

}

