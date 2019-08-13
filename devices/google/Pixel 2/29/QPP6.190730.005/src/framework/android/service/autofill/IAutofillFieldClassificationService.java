/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.view.autofill.AutofillValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IAutofillFieldClassificationService
extends IInterface {
    public void calculateScores(RemoteCallback var1, List<AutofillValue> var2, String[] var3, String[] var4, String var5, Bundle var6, Map var7, Map var8) throws RemoteException;

    public static class Default
    implements IAutofillFieldClassificationService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void calculateScores(RemoteCallback remoteCallback, List<AutofillValue> list, String[] arrstring, String[] arrstring2, String string2, Bundle bundle, Map map, Map map2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAutofillFieldClassificationService {
        private static final String DESCRIPTOR = "android.service.autofill.IAutofillFieldClassificationService";
        static final int TRANSACTION_calculateScores = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAutofillFieldClassificationService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAutofillFieldClassificationService) {
                return (IAutofillFieldClassificationService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAutofillFieldClassificationService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "calculateScores";
        }

        public static boolean setDefaultImpl(IAutofillFieldClassificationService iAutofillFieldClassificationService) {
            if (Proxy.sDefaultImpl == null && iAutofillFieldClassificationService != null) {
                Proxy.sDefaultImpl = iAutofillFieldClassificationService;
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
            object = parcel.readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel(parcel) : null;
            ArrayList<AutofillValue> arrayList = parcel.createTypedArrayList(AutofillValue.CREATOR);
            String[] arrstring = parcel.createStringArray();
            String[] arrstring2 = parcel.createStringArray();
            String string2 = parcel.readString();
            Bundle bundle = parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null;
            ClassLoader classLoader = this.getClass().getClassLoader();
            this.calculateScores((RemoteCallback)object, arrayList, arrstring, arrstring2, string2, bundle, parcel.readHashMap(classLoader), parcel.readHashMap(classLoader));
            return true;
        }

        private static class Proxy
        implements IAutofillFieldClassificationService {
            public static IAutofillFieldClassificationService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
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
            public void calculateScores(RemoteCallback remoteCallback, List<AutofillValue> list, String[] arrstring, String[] arrstring2, String string2, Bundle bundle, Map map, Map map2) throws RemoteException {
                Parcel parcel;
                void var1_6;
                block13 : {
                    block12 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (remoteCallback != null) {
                            parcel.writeInt(1);
                            remoteCallback.writeToParcel(parcel, 0);
                            break block12;
                        }
                        parcel.writeInt(0);
                    }
                    try {
                        parcel.writeTypedList(list);
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                    try {
                        parcel.writeStringArray(arrstring);
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                    try {
                        parcel.writeStringArray(arrstring2);
                        parcel.writeString(string2);
                        if (bundle != null) {
                            parcel.writeInt(1);
                            bundle.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        parcel.writeMap(map);
                        parcel.writeMap(map2);
                        if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().calculateScores(remoteCallback, list, arrstring, arrstring2, string2, bundle, map, map2);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block13;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_6;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }
        }

    }

}

