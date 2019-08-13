/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.content.ComponentName;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.autofill.UserData;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import android.view.autofill.IAutoFillManagerClient;
import com.android.internal.os.IResultReceiver;
import java.util.ArrayList;
import java.util.List;

public interface IAutoFillManager
extends IInterface {
    public void addClient(IAutoFillManagerClient var1, ComponentName var2, int var3, IResultReceiver var4) throws RemoteException;

    public void cancelSession(int var1, int var2) throws RemoteException;

    public void disableOwnedAutofillServices(int var1) throws RemoteException;

    public void finishSession(int var1, int var2) throws RemoteException;

    public void getAutofillServiceComponentName(IResultReceiver var1) throws RemoteException;

    public void getAvailableFieldClassificationAlgorithms(IResultReceiver var1) throws RemoteException;

    public void getDefaultFieldClassificationAlgorithm(IResultReceiver var1) throws RemoteException;

    public void getFillEventHistory(IResultReceiver var1) throws RemoteException;

    public void getUserData(IResultReceiver var1) throws RemoteException;

    public void getUserDataId(IResultReceiver var1) throws RemoteException;

    public void isFieldClassificationEnabled(IResultReceiver var1) throws RemoteException;

    public void isServiceEnabled(int var1, String var2, IResultReceiver var3) throws RemoteException;

    public void isServiceSupported(int var1, IResultReceiver var2) throws RemoteException;

    public void onPendingSaveUi(int var1, IBinder var2) throws RemoteException;

    public void removeClient(IAutoFillManagerClient var1, int var2) throws RemoteException;

    public void restoreSession(int var1, IBinder var2, IBinder var3, IResultReceiver var4) throws RemoteException;

    public void setAugmentedAutofillWhitelist(List<String> var1, List<ComponentName> var2, IResultReceiver var3) throws RemoteException;

    public void setAuthenticationResult(Bundle var1, int var2, int var3, int var4) throws RemoteException;

    public void setAutofillFailure(int var1, List<AutofillId> var2, int var3) throws RemoteException;

    public void setHasCallback(int var1, int var2, boolean var3) throws RemoteException;

    public void setUserData(UserData var1) throws RemoteException;

    public void startSession(IBinder var1, IBinder var2, AutofillId var3, Rect var4, AutofillValue var5, int var6, boolean var7, int var8, ComponentName var9, boolean var10, IResultReceiver var11) throws RemoteException;

    public void updateSession(int var1, AutofillId var2, Rect var3, AutofillValue var4, int var5, int var6, int var7) throws RemoteException;

    public static class Default
    implements IAutoFillManager {
        @Override
        public void addClient(IAutoFillManagerClient iAutoFillManagerClient, ComponentName componentName, int n, IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancelSession(int n, int n2) throws RemoteException {
        }

        @Override
        public void disableOwnedAutofillServices(int n) throws RemoteException {
        }

        @Override
        public void finishSession(int n, int n2) throws RemoteException {
        }

        @Override
        public void getAutofillServiceComponentName(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public void getAvailableFieldClassificationAlgorithms(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public void getDefaultFieldClassificationAlgorithm(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public void getFillEventHistory(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public void getUserData(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public void getUserDataId(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public void isFieldClassificationEnabled(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public void isServiceEnabled(int n, String string2, IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public void isServiceSupported(int n, IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public void onPendingSaveUi(int n, IBinder iBinder) throws RemoteException {
        }

        @Override
        public void removeClient(IAutoFillManagerClient iAutoFillManagerClient, int n) throws RemoteException {
        }

        @Override
        public void restoreSession(int n, IBinder iBinder, IBinder iBinder2, IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public void setAugmentedAutofillWhitelist(List<String> list, List<ComponentName> list2, IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public void setAuthenticationResult(Bundle bundle, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void setAutofillFailure(int n, List<AutofillId> list, int n2) throws RemoteException {
        }

        @Override
        public void setHasCallback(int n, int n2, boolean bl) throws RemoteException {
        }

        @Override
        public void setUserData(UserData userData) throws RemoteException {
        }

        @Override
        public void startSession(IBinder iBinder, IBinder iBinder2, AutofillId autofillId, Rect rect, AutofillValue autofillValue, int n, boolean bl, int n2, ComponentName componentName, boolean bl2, IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public void updateSession(int n, AutofillId autofillId, Rect rect, AutofillValue autofillValue, int n2, int n3, int n4) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAutoFillManager {
        private static final String DESCRIPTOR = "android.view.autofill.IAutoFillManager";
        static final int TRANSACTION_addClient = 1;
        static final int TRANSACTION_cancelSession = 9;
        static final int TRANSACTION_disableOwnedAutofillServices = 12;
        static final int TRANSACTION_finishSession = 8;
        static final int TRANSACTION_getAutofillServiceComponentName = 20;
        static final int TRANSACTION_getAvailableFieldClassificationAlgorithms = 21;
        static final int TRANSACTION_getDefaultFieldClassificationAlgorithm = 22;
        static final int TRANSACTION_getFillEventHistory = 4;
        static final int TRANSACTION_getUserData = 16;
        static final int TRANSACTION_getUserDataId = 17;
        static final int TRANSACTION_isFieldClassificationEnabled = 19;
        static final int TRANSACTION_isServiceEnabled = 14;
        static final int TRANSACTION_isServiceSupported = 13;
        static final int TRANSACTION_onPendingSaveUi = 15;
        static final int TRANSACTION_removeClient = 2;
        static final int TRANSACTION_restoreSession = 5;
        static final int TRANSACTION_setAugmentedAutofillWhitelist = 23;
        static final int TRANSACTION_setAuthenticationResult = 10;
        static final int TRANSACTION_setAutofillFailure = 7;
        static final int TRANSACTION_setHasCallback = 11;
        static final int TRANSACTION_setUserData = 18;
        static final int TRANSACTION_startSession = 3;
        static final int TRANSACTION_updateSession = 6;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAutoFillManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAutoFillManager) {
                return (IAutoFillManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAutoFillManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 23: {
                    return "setAugmentedAutofillWhitelist";
                }
                case 22: {
                    return "getDefaultFieldClassificationAlgorithm";
                }
                case 21: {
                    return "getAvailableFieldClassificationAlgorithms";
                }
                case 20: {
                    return "getAutofillServiceComponentName";
                }
                case 19: {
                    return "isFieldClassificationEnabled";
                }
                case 18: {
                    return "setUserData";
                }
                case 17: {
                    return "getUserDataId";
                }
                case 16: {
                    return "getUserData";
                }
                case 15: {
                    return "onPendingSaveUi";
                }
                case 14: {
                    return "isServiceEnabled";
                }
                case 13: {
                    return "isServiceSupported";
                }
                case 12: {
                    return "disableOwnedAutofillServices";
                }
                case 11: {
                    return "setHasCallback";
                }
                case 10: {
                    return "setAuthenticationResult";
                }
                case 9: {
                    return "cancelSession";
                }
                case 8: {
                    return "finishSession";
                }
                case 7: {
                    return "setAutofillFailure";
                }
                case 6: {
                    return "updateSession";
                }
                case 5: {
                    return "restoreSession";
                }
                case 4: {
                    return "getFillEventHistory";
                }
                case 3: {
                    return "startSession";
                }
                case 2: {
                    return "removeClient";
                }
                case 1: 
            }
            return "addClient";
        }

        public static boolean setDefaultImpl(IAutoFillManager iAutoFillManager) {
            if (Proxy.sDefaultImpl == null && iAutoFillManager != null) {
                Proxy.sDefaultImpl = iAutoFillManager;
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
            if (n != 1598968902) {
                boolean bl = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setAugmentedAutofillWhitelist(((Parcel)object).createStringArrayList(), ((Parcel)object).createTypedArrayList(ComponentName.CREATOR), IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getDefaultFieldClassificationAlgorithm(IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getAvailableFieldClassificationAlgorithms(IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getAutofillServiceComponentName(IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.isFieldClassificationEnabled(IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? UserData.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setUserData((UserData)object);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getUserDataId(IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getUserData(IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onPendingSaveUi(((Parcel)object).readInt(), ((Parcel)object).readStrongBinder());
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.isServiceEnabled(((Parcel)object).readInt(), ((Parcel)object).readString(), IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.isServiceSupported(((Parcel)object).readInt(), IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disableOwnedAutofillServices(((Parcel)object).readInt());
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        this.setHasCallback(n, n2, bl);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setAuthenticationResult((Bundle)object2, ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancelSession(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.finishSession(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setAutofillFailure(((Parcel)object).readInt(), ((Parcel)object).createTypedArrayList(AutofillId.CREATOR), ((Parcel)object).readInt());
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readInt() != 0 ? AutofillId.CREATOR.createFromParcel((Parcel)object) : null;
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        AutofillValue autofillValue = ((Parcel)object).readInt() != 0 ? AutofillValue.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateSession(n, (AutofillId)object2, rect, autofillValue, ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.restoreSession(((Parcel)object).readInt(), ((Parcel)object).readStrongBinder(), ((Parcel)object).readStrongBinder(), IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getFillEventHistory(IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        IBinder iBinder2 = ((Parcel)object).readStrongBinder();
                        object2 = ((Parcel)object).readInt() != 0 ? AutofillId.CREATOR.createFromParcel((Parcel)object) : null;
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        AutofillValue autofillValue = ((Parcel)object).readInt() != 0 ? AutofillValue.CREATOR.createFromParcel((Parcel)object) : null;
                        n2 = ((Parcel)object).readInt();
                        bl = ((Parcel)object).readInt() != 0;
                        n = ((Parcel)object).readInt();
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        boolean bl2 = ((Parcel)object).readInt() != 0;
                        this.startSession(iBinder, iBinder2, (AutofillId)object2, rect, autofillValue, n2, bl, n, componentName, bl2, IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeClient(IAutoFillManagerClient.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                IAutoFillManagerClient iAutoFillManagerClient = IAutoFillManagerClient.Stub.asInterface(((Parcel)object).readStrongBinder());
                object2 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                this.addClient(iAutoFillManagerClient, (ComponentName)object2, ((Parcel)object).readInt(), IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IAutoFillManager {
            public static IAutoFillManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void addClient(IAutoFillManagerClient iAutoFillManagerClient, ComponentName componentName, int n, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAutoFillManagerClient != null ? iAutoFillManagerClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    iBinder = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().addClient(iAutoFillManagerClient, componentName, n, iResultReceiver);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void cancelSession(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelSession(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void disableOwnedAutofillServices(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(12, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableOwnedAutofillServices(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void finishSession(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finishSession(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void getAutofillServiceComponentName(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(20, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getAutofillServiceComponentName(iResultReceiver);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void getAvailableFieldClassificationAlgorithms(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(21, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getAvailableFieldClassificationAlgorithms(iResultReceiver);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void getDefaultFieldClassificationAlgorithm(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(22, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getDefaultFieldClassificationAlgorithm(iResultReceiver);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void getFillEventHistory(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getFillEventHistory(iResultReceiver);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void getUserData(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(16, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getUserData(iResultReceiver);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void getUserDataId(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(17, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getUserDataId(iResultReceiver);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void isFieldClassificationEnabled(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(19, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().isFieldClassificationEnabled(iResultReceiver);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void isServiceEnabled(int n, String string2, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    IBinder iBinder = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(14, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().isServiceEnabled(n, string2, iResultReceiver);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void isServiceSupported(int n, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(13, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().isServiceSupported(n, iResultReceiver);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPendingSaveUi(int n, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(15, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPendingSaveUi(n, iBinder);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removeClient(IAutoFillManagerClient iAutoFillManagerClient, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAutoFillManagerClient != null ? iAutoFillManagerClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().removeClient(iAutoFillManagerClient, n);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void restoreSession(int n, IBinder iBinder, IBinder iBinder2, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeStrongBinder(iBinder2);
                    IBinder iBinder3 = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder3);
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().restoreSession(n, iBinder, iBinder2, iResultReceiver);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setAugmentedAutofillWhitelist(List<String> list, List<ComponentName> list2, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringList(list);
                    parcel.writeTypedList(list2);
                    IBinder iBinder = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(23, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setAugmentedAutofillWhitelist(list, list2, iResultReceiver);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setAuthenticationResult(Bundle bundle, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAuthenticationResult(bundle, n, n2, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setAutofillFailure(int n, List<AutofillId> list, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeTypedList(list);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAutofillFailure(n, list, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setHasCallback(int n, int n2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setHasCallback(n, n2, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setUserData(UserData userData) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (userData != null) {
                        parcel.writeInt(1);
                        userData.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(18, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUserData(userData);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void startSession(IBinder var1_1, IBinder var2_6, AutofillId var3_7, Rect var4_8, AutofillValue var5_9, int var6_10, boolean var7_11, int var8_12, ComponentName var9_13, boolean var10_14, IResultReceiver var11_15) throws RemoteException {
                block23 : {
                    block24 : {
                        block22 : {
                            block21 : {
                                block20 : {
                                    block25 : {
                                        block19 : {
                                            block18 : {
                                                var12_16 = Parcel.obtain();
                                                var12_16.writeInterfaceToken("android.view.autofill.IAutoFillManager");
                                                var12_16.writeStrongBinder(var1_1);
                                                var12_16.writeStrongBinder(var2_6);
                                                var13_17 = 0;
                                                if (var3_7 == null) break block18;
                                                try {
                                                    var12_16.writeInt(1);
                                                    var3_7.writeToParcel(var12_16, 0);
                                                    break block19;
                                                }
                                                catch (Throwable var1_2) {
                                                    break block23;
                                                }
                                            }
                                            var12_16.writeInt(0);
                                        }
                                        if (var4_8 != null) {
                                            var12_16.writeInt(1);
                                            var4_8.writeToParcel(var12_16, 0);
                                        } else {
                                            var12_16.writeInt(0);
                                        }
                                        if (var5_9 == null) break block25;
                                        var12_16.writeInt(1);
                                        var5_9.writeToParcel(var12_16, 0);
                                        ** GOTO lbl33
                                    }
                                    var12_16.writeInt(0);
lbl33: // 2 sources:
                                    var12_16.writeInt(var6_10);
                                    var14_18 = var7_11 != false ? 1 : 0;
                                    var12_16.writeInt(var14_18);
                                    var12_16.writeInt(var8_12);
                                    if (var9_13 == null) break block20;
                                    var12_16.writeInt(1);
                                    var9_13.writeToParcel(var12_16, 0);
                                    ** GOTO lbl45
                                }
                                var12_16.writeInt(0);
lbl45: // 2 sources:
                                var14_18 = var13_17;
                                if (var10_14) {
                                    var14_18 = 1;
                                }
                                var12_16.writeInt(var14_18);
                                if (var11_15 == null) break block21;
                                var15_19 = var11_15.asBinder();
                                break block22;
                            }
                            var15_19 = null;
                        }
                        var12_16.writeStrongBinder((IBinder)var15_19);
                        if (this.mRemote.transact(3, var12_16, null, 1) || Stub.getDefaultImpl() == null) break block24;
                        var15_19 = Stub.getDefaultImpl();
                        try {
                            var15_19.startSession(var1_1, var2_6, var3_7, var4_8, var5_9, var6_10, var7_11, var8_12, var9_13, var10_14, var11_15);
                            var12_16.recycle();
                            return;
                        }
                        catch (Throwable var1_3) {}
                        break block23;
                    }
                    var12_16.recycle();
                    return;
                    catch (Throwable var1_4) {
                        // empty catch block
                    }
                }
                var12_16.recycle();
                throw var1_5;
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
            public void updateSession(int n, AutofillId autofillId, Rect rect, AutofillValue autofillValue, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel;
                void var2_7;
                block15 : {
                    block14 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel.writeInt(n);
                            if (autofillId != null) {
                                parcel.writeInt(1);
                                autofillId.writeToParcel(parcel, 0);
                            } else {
                                parcel.writeInt(0);
                            }
                            if (rect != null) {
                                parcel.writeInt(1);
                                rect.writeToParcel(parcel, 0);
                            } else {
                                parcel.writeInt(0);
                            }
                            if (autofillValue != null) {
                                parcel.writeInt(1);
                                autofillValue.writeToParcel(parcel, 0);
                                break block14;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n3);
                        parcel.writeInt(n4);
                        if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().updateSession(n, autofillId, rect, autofillValue, n2, n3, n4);
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
                throw var2_7;
            }
        }

    }

}

