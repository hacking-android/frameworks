/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import android.view.autofill.IAutofillWindowPresenter;
import com.android.internal.os.IResultReceiver;
import java.util.ArrayList;
import java.util.List;

public interface IAutoFillManagerClient
extends IInterface {
    public void authenticate(int var1, int var2, IntentSender var3, Intent var4) throws RemoteException;

    public void autofill(int var1, List<AutofillId> var2, List<AutofillValue> var3) throws RemoteException;

    public void dispatchUnhandledKey(int var1, AutofillId var2, KeyEvent var3) throws RemoteException;

    public void getAugmentedAutofillClient(IResultReceiver var1) throws RemoteException;

    public void notifyNoFillUi(int var1, AutofillId var2, int var3) throws RemoteException;

    public void requestHideFillUi(int var1, AutofillId var2) throws RemoteException;

    public void requestShowFillUi(int var1, AutofillId var2, int var3, int var4, Rect var5, IAutofillWindowPresenter var6) throws RemoteException;

    public void setSaveUiState(int var1, boolean var2) throws RemoteException;

    public void setSessionFinished(int var1, List<AutofillId> var2) throws RemoteException;

    public void setState(int var1) throws RemoteException;

    public void setTrackedViews(int var1, AutofillId[] var2, boolean var3, boolean var4, AutofillId[] var5, AutofillId var6) throws RemoteException;

    public void startIntentSender(IntentSender var1, Intent var2) throws RemoteException;

    public static class Default
    implements IAutoFillManagerClient {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void authenticate(int n, int n2, IntentSender intentSender, Intent intent) throws RemoteException {
        }

        @Override
        public void autofill(int n, List<AutofillId> list, List<AutofillValue> list2) throws RemoteException {
        }

        @Override
        public void dispatchUnhandledKey(int n, AutofillId autofillId, KeyEvent keyEvent) throws RemoteException {
        }

        @Override
        public void getAugmentedAutofillClient(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public void notifyNoFillUi(int n, AutofillId autofillId, int n2) throws RemoteException {
        }

        @Override
        public void requestHideFillUi(int n, AutofillId autofillId) throws RemoteException {
        }

        @Override
        public void requestShowFillUi(int n, AutofillId autofillId, int n2, int n3, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) throws RemoteException {
        }

        @Override
        public void setSaveUiState(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setSessionFinished(int n, List<AutofillId> list) throws RemoteException {
        }

        @Override
        public void setState(int n) throws RemoteException {
        }

        @Override
        public void setTrackedViews(int n, AutofillId[] arrautofillId, boolean bl, boolean bl2, AutofillId[] arrautofillId2, AutofillId autofillId) throws RemoteException {
        }

        @Override
        public void startIntentSender(IntentSender intentSender, Intent intent) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAutoFillManagerClient {
        private static final String DESCRIPTOR = "android.view.autofill.IAutoFillManagerClient";
        static final int TRANSACTION_authenticate = 3;
        static final int TRANSACTION_autofill = 2;
        static final int TRANSACTION_dispatchUnhandledKey = 8;
        static final int TRANSACTION_getAugmentedAutofillClient = 12;
        static final int TRANSACTION_notifyNoFillUi = 7;
        static final int TRANSACTION_requestHideFillUi = 6;
        static final int TRANSACTION_requestShowFillUi = 5;
        static final int TRANSACTION_setSaveUiState = 10;
        static final int TRANSACTION_setSessionFinished = 11;
        static final int TRANSACTION_setState = 1;
        static final int TRANSACTION_setTrackedViews = 4;
        static final int TRANSACTION_startIntentSender = 9;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAutoFillManagerClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAutoFillManagerClient) {
                return (IAutoFillManagerClient)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAutoFillManagerClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 12: {
                    return "getAugmentedAutofillClient";
                }
                case 11: {
                    return "setSessionFinished";
                }
                case 10: {
                    return "setSaveUiState";
                }
                case 9: {
                    return "startIntentSender";
                }
                case 8: {
                    return "dispatchUnhandledKey";
                }
                case 7: {
                    return "notifyNoFillUi";
                }
                case 6: {
                    return "requestHideFillUi";
                }
                case 5: {
                    return "requestShowFillUi";
                }
                case 4: {
                    return "setTrackedViews";
                }
                case 3: {
                    return "authenticate";
                }
                case 2: {
                    return "autofill";
                }
                case 1: 
            }
            return "setState";
        }

        public static boolean setDefaultImpl(IAutoFillManagerClient iAutoFillManagerClient) {
            if (Proxy.sDefaultImpl == null && iAutoFillManagerClient != null) {
                Proxy.sDefaultImpl = iAutoFillManagerClient;
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
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getAugmentedAutofillClient(IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setSessionFinished(((Parcel)object).readInt(), ((Parcel)object).createTypedArrayList(AutofillId.CREATOR));
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        this.setSaveUiState(n, bl);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? IntentSender.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startIntentSender((IntentSender)object2, (Intent)object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readInt() != 0 ? AutofillId.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? KeyEvent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.dispatchUnhandledKey(n, (AutofillId)object2, (KeyEvent)object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readInt() != 0 ? AutofillId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.notifyNoFillUi(n, (AutofillId)object2, ((Parcel)object).readInt());
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? AutofillId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.requestHideFillUi(n, (AutofillId)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n2 = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readInt() != 0 ? AutofillId.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        int n3 = ((Parcel)object).readInt();
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        this.requestShowFillUi(n2, (AutofillId)object2, n, n3, rect, IAutofillWindowPresenter.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        AutofillId[] arrautofillId = ((Parcel)object).createTypedArray(AutofillId.CREATOR);
                        bl = ((Parcel)object).readInt() != 0;
                        boolean bl2 = ((Parcel)object).readInt() != 0;
                        object2 = ((Parcel)object).createTypedArray(AutofillId.CREATOR);
                        object = ((Parcel)object).readInt() != 0 ? AutofillId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setTrackedViews(n, arrautofillId, bl, bl2, (AutofillId[])object2, (AutofillId)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readInt() != 0 ? IntentSender.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.authenticate(n2, n, (IntentSender)object2, (Intent)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.autofill(((Parcel)object).readInt(), ((Parcel)object).createTypedArrayList(AutofillId.CREATOR), ((Parcel)object).createTypedArrayList(AutofillValue.CREATOR));
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.setState(((Parcel)object).readInt());
                return true;
            }
            object2.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IAutoFillManagerClient {
            public static IAutoFillManagerClient sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void authenticate(int n, int n2, IntentSender intentSender, Intent intent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (intentSender != null) {
                        parcel.writeInt(1);
                        intentSender.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().authenticate(n, n2, intentSender, intent);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void autofill(int n, List<AutofillId> list, List<AutofillValue> list2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeTypedList(list);
                    parcel.writeTypedList(list2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().autofill(n, list, list2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void dispatchUnhandledKey(int n, AutofillId autofillId, KeyEvent keyEvent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (autofillId != null) {
                        parcel.writeInt(1);
                        autofillId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (keyEvent != null) {
                        parcel.writeInt(1);
                        keyEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchUnhandledKey(n, autofillId, keyEvent);
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
            public void getAugmentedAutofillClient(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(12, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getAugmentedAutofillClient(iResultReceiver);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void notifyNoFillUi(int n, AutofillId autofillId, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (autofillId != null) {
                        parcel.writeInt(1);
                        autofillId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyNoFillUi(n, autofillId, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void requestHideFillUi(int n, AutofillId autofillId) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (autofillId != null) {
                        parcel.writeInt(1);
                        autofillId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestHideFillUi(n, autofillId);
                        return;
                    }
                    return;
                }
                finally {
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
                void var2_8;
                Parcel parcel;
                block15 : {
                    block14 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel.writeInt(n);
                            if (autofillId != null) {
                                parcel.writeInt(1);
                                autofillId.writeToParcel(parcel, 0);
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
                        if (rect != null) {
                            parcel.writeInt(1);
                            rect.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        IBinder iBinder = iAutofillWindowPresenter != null ? iAutofillWindowPresenter.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().requestShowFillUi(n, autofillId, n2, n3, rect, iAutofillWindowPresenter);
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

            @Override
            public void setSaveUiState(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSaveUiState(n, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setSessionFinished(int n, List<AutofillId> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSessionFinished(n, list);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setState(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setState(n);
                        return;
                    }
                    return;
                }
                finally {
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
            public void setTrackedViews(int n, AutofillId[] arrautofillId, boolean bl, boolean bl2, AutofillId[] arrautofillId2, AutofillId autofillId) throws RemoteException {
                void var2_8;
                Parcel parcel;
                block13 : {
                    block12 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel.writeInt(n);
                        }
                        catch (Throwable throwable) {
                            break block13;
                        }
                        try {
                            parcel.writeTypedArray((Parcelable[])arrautofillId, 0);
                            int n2 = bl ? 1 : 0;
                            parcel.writeInt(n2);
                            n2 = bl2 ? 1 : 0;
                            parcel.writeInt(n2);
                        }
                        catch (Throwable throwable) {}
                        try {
                            parcel.writeTypedArray((Parcelable[])arrautofillId2, 0);
                            if (autofillId != null) {
                                parcel.writeInt(1);
                                autofillId.writeToParcel(parcel, 0);
                                break block12;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().setTrackedViews(n, arrautofillId, bl, bl2, arrautofillId2, autofillId);
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
                throw var2_8;
            }

            @Override
            public void startIntentSender(IntentSender intentSender, Intent intent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intentSender != null) {
                        parcel.writeInt(1);
                        intentSender.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startIntentSender(intentSender, intent);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

