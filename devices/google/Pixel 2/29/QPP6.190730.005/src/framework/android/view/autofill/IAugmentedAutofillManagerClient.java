/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import android.view.autofill.IAutofillWindowPresenter;
import java.util.ArrayList;
import java.util.List;

public interface IAugmentedAutofillManagerClient
extends IInterface {
    public void autofill(int var1, List<AutofillId> var2, List<AutofillValue> var3) throws RemoteException;

    public Rect getViewCoordinates(AutofillId var1) throws RemoteException;

    public void requestHideFillUi(int var1, AutofillId var2) throws RemoteException;

    public void requestShowFillUi(int var1, AutofillId var2, int var3, int var4, Rect var5, IAutofillWindowPresenter var6) throws RemoteException;

    public static class Default
    implements IAugmentedAutofillManagerClient {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void autofill(int n, List<AutofillId> list, List<AutofillValue> list2) throws RemoteException {
        }

        @Override
        public Rect getViewCoordinates(AutofillId autofillId) throws RemoteException {
            return null;
        }

        @Override
        public void requestHideFillUi(int n, AutofillId autofillId) throws RemoteException {
        }

        @Override
        public void requestShowFillUi(int n, AutofillId autofillId, int n2, int n3, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAugmentedAutofillManagerClient {
        private static final String DESCRIPTOR = "android.view.autofill.IAugmentedAutofillManagerClient";
        static final int TRANSACTION_autofill = 2;
        static final int TRANSACTION_getViewCoordinates = 1;
        static final int TRANSACTION_requestHideFillUi = 4;
        static final int TRANSACTION_requestShowFillUi = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAugmentedAutofillManagerClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAugmentedAutofillManagerClient) {
                return (IAugmentedAutofillManagerClient)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAugmentedAutofillManagerClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "requestHideFillUi";
                    }
                    return "requestShowFillUi";
                }
                return "autofill";
            }
            return "getViewCoordinates";
        }

        public static boolean setDefaultImpl(IAugmentedAutofillManagerClient iAugmentedAutofillManagerClient) {
            if (Proxy.sDefaultImpl == null && iAugmentedAutofillManagerClient != null) {
                Proxy.sDefaultImpl = iAugmentedAutofillManagerClient;
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
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 1598968902) {
                                return super.onTransact(n, (Parcel)object, parcel, n2);
                            }
                            parcel.writeString(DESCRIPTOR);
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? AutofillId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.requestHideFillUi(n, (AutofillId)object);
                        parcel.writeNoException();
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    n = ((Parcel)object).readInt();
                    AutofillId autofillId = ((Parcel)object).readInt() != 0 ? AutofillId.CREATOR.createFromParcel((Parcel)object) : null;
                    int n3 = ((Parcel)object).readInt();
                    n2 = ((Parcel)object).readInt();
                    Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                    this.requestShowFillUi(n, autofillId, n3, n2, rect, IAutofillWindowPresenter.Stub.asInterface(((Parcel)object).readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.autofill(((Parcel)object).readInt(), ((Parcel)object).createTypedArrayList(AutofillId.CREATOR), ((Parcel)object).createTypedArrayList(AutofillValue.CREATOR));
                parcel.writeNoException();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? AutofillId.CREATOR.createFromParcel((Parcel)object) : null;
            object = this.getViewCoordinates((AutofillId)object);
            parcel.writeNoException();
            if (object != null) {
                parcel.writeInt(1);
                ((Rect)object).writeToParcel(parcel, 1);
            } else {
                parcel.writeInt(0);
            }
            return true;
        }

        private static class Proxy
        implements IAugmentedAutofillManagerClient {
            public static IAugmentedAutofillManagerClient sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void autofill(int n, List<AutofillId> list, List<AutofillValue> list2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeTypedList(list);
                    parcel.writeTypedList(list2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().autofill(n, list, list2);
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public Rect getViewCoordinates(AutofillId parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((AutofillId)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Rect rect = Stub.getDefaultImpl().getViewCoordinates((AutofillId)parcelable);
                        parcel2.recycle();
                        parcel.recycle();
                        return rect;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        Rect rect = Rect.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public void requestHideFillUi(int n, AutofillId autofillId) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (autofillId != null) {
                        parcel.writeInt(1);
                        autofillId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestHideFillUi(n, autofillId);
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

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void requestShowFillUi(int n, AutofillId autofillId, int n2, int n3, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) throws RemoteException {
                Parcel parcel;
                void var2_8;
                Parcel parcel2;
                block16 : {
                    block15 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel2.writeInt(n);
                            if (autofillId != null) {
                                parcel2.writeInt(1);
                                autofillId.writeToParcel(parcel2, 0);
                                break block15;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n3);
                        if (rect != null) {
                            parcel2.writeInt(1);
                            rect.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        IBinder iBinder = iAutofillWindowPresenter != null ? iAutofillWindowPresenter.asBinder() : null;
                        parcel2.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(3, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().requestShowFillUi(n, autofillId, n2, n3, rect, iAutofillWindowPresenter);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var2_8;
            }
        }

    }

}

