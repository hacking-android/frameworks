/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;
import com.android.internal.view.IInputContext;
import com.android.internal.view.IInputMethodClient;
import com.android.internal.view.InputBindResult;
import java.util.ArrayList;
import java.util.List;

public interface IInputMethodManager
extends IInterface {
    public void addClient(IInputMethodClient var1, IInputContext var2, int var3) throws RemoteException;

    public InputMethodSubtype getCurrentInputMethodSubtype() throws RemoteException;

    public List<InputMethodInfo> getEnabledInputMethodList(int var1) throws RemoteException;

    public List<InputMethodSubtype> getEnabledInputMethodSubtypeList(String var1, boolean var2) throws RemoteException;

    public List<InputMethodInfo> getInputMethodList(int var1) throws RemoteException;

    public int getInputMethodWindowVisibleHeight() throws RemoteException;

    public InputMethodSubtype getLastInputMethodSubtype() throws RemoteException;

    public boolean hideSoftInput(IInputMethodClient var1, int var2, ResultReceiver var3) throws RemoteException;

    public boolean isInputMethodPickerShownForTest() throws RemoteException;

    public void reportActivityView(IInputMethodClient var1, int var2, float[] var3) throws RemoteException;

    public void setAdditionalInputMethodSubtypes(String var1, InputMethodSubtype[] var2) throws RemoteException;

    public void showInputMethodAndSubtypeEnablerFromClient(IInputMethodClient var1, String var2) throws RemoteException;

    public void showInputMethodPickerFromClient(IInputMethodClient var1, int var2) throws RemoteException;

    public void showInputMethodPickerFromSystem(IInputMethodClient var1, int var2, int var3) throws RemoteException;

    public boolean showSoftInput(IInputMethodClient var1, int var2, ResultReceiver var3) throws RemoteException;

    public InputBindResult startInputOrWindowGainedFocus(int var1, IInputMethodClient var2, IBinder var3, int var4, int var5, int var6, EditorInfo var7, IInputContext var8, int var9, int var10) throws RemoteException;

    public static class Default
    implements IInputMethodManager {
        @Override
        public void addClient(IInputMethodClient iInputMethodClient, IInputContext iInputContext, int n) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public InputMethodSubtype getCurrentInputMethodSubtype() throws RemoteException {
            return null;
        }

        @Override
        public List<InputMethodInfo> getEnabledInputMethodList(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<InputMethodSubtype> getEnabledInputMethodSubtypeList(String string2, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public List<InputMethodInfo> getInputMethodList(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getInputMethodWindowVisibleHeight() throws RemoteException {
            return 0;
        }

        @Override
        public InputMethodSubtype getLastInputMethodSubtype() throws RemoteException {
            return null;
        }

        @Override
        public boolean hideSoftInput(IInputMethodClient iInputMethodClient, int n, ResultReceiver resultReceiver) throws RemoteException {
            return false;
        }

        @Override
        public boolean isInputMethodPickerShownForTest() throws RemoteException {
            return false;
        }

        @Override
        public void reportActivityView(IInputMethodClient iInputMethodClient, int n, float[] arrf) throws RemoteException {
        }

        @Override
        public void setAdditionalInputMethodSubtypes(String string2, InputMethodSubtype[] arrinputMethodSubtype) throws RemoteException {
        }

        @Override
        public void showInputMethodAndSubtypeEnablerFromClient(IInputMethodClient iInputMethodClient, String string2) throws RemoteException {
        }

        @Override
        public void showInputMethodPickerFromClient(IInputMethodClient iInputMethodClient, int n) throws RemoteException {
        }

        @Override
        public void showInputMethodPickerFromSystem(IInputMethodClient iInputMethodClient, int n, int n2) throws RemoteException {
        }

        @Override
        public boolean showSoftInput(IInputMethodClient iInputMethodClient, int n, ResultReceiver resultReceiver) throws RemoteException {
            return false;
        }

        @Override
        public InputBindResult startInputOrWindowGainedFocus(int n, IInputMethodClient iInputMethodClient, IBinder iBinder, int n2, int n3, int n4, EditorInfo editorInfo, IInputContext iInputContext, int n5, int n6) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IInputMethodManager {
        private static final String DESCRIPTOR = "com.android.internal.view.IInputMethodManager";
        static final int TRANSACTION_addClient = 1;
        static final int TRANSACTION_getCurrentInputMethodSubtype = 13;
        static final int TRANSACTION_getEnabledInputMethodList = 3;
        static final int TRANSACTION_getEnabledInputMethodSubtypeList = 4;
        static final int TRANSACTION_getInputMethodList = 2;
        static final int TRANSACTION_getInputMethodWindowVisibleHeight = 15;
        static final int TRANSACTION_getLastInputMethodSubtype = 5;
        static final int TRANSACTION_hideSoftInput = 7;
        static final int TRANSACTION_isInputMethodPickerShownForTest = 12;
        static final int TRANSACTION_reportActivityView = 16;
        static final int TRANSACTION_setAdditionalInputMethodSubtypes = 14;
        static final int TRANSACTION_showInputMethodAndSubtypeEnablerFromClient = 11;
        static final int TRANSACTION_showInputMethodPickerFromClient = 9;
        static final int TRANSACTION_showInputMethodPickerFromSystem = 10;
        static final int TRANSACTION_showSoftInput = 6;
        static final int TRANSACTION_startInputOrWindowGainedFocus = 8;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IInputMethodManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IInputMethodManager) {
                return (IInputMethodManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IInputMethodManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 16: {
                    return "reportActivityView";
                }
                case 15: {
                    return "getInputMethodWindowVisibleHeight";
                }
                case 14: {
                    return "setAdditionalInputMethodSubtypes";
                }
                case 13: {
                    return "getCurrentInputMethodSubtype";
                }
                case 12: {
                    return "isInputMethodPickerShownForTest";
                }
                case 11: {
                    return "showInputMethodAndSubtypeEnablerFromClient";
                }
                case 10: {
                    return "showInputMethodPickerFromSystem";
                }
                case 9: {
                    return "showInputMethodPickerFromClient";
                }
                case 8: {
                    return "startInputOrWindowGainedFocus";
                }
                case 7: {
                    return "hideSoftInput";
                }
                case 6: {
                    return "showSoftInput";
                }
                case 5: {
                    return "getLastInputMethodSubtype";
                }
                case 4: {
                    return "getEnabledInputMethodSubtypeList";
                }
                case 3: {
                    return "getEnabledInputMethodList";
                }
                case 2: {
                    return "getInputMethodList";
                }
                case 1: 
            }
            return "addClient";
        }

        public static boolean setDefaultImpl(IInputMethodManager iInputMethodManager) {
            if (Proxy.sDefaultImpl == null && iInputMethodManager != null) {
                Proxy.sDefaultImpl = iInputMethodManager;
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
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportActivityView(IInputMethodClient.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).createFloatArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getInputMethodWindowVisibleHeight();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setAdditionalInputMethodSubtypes(((Parcel)object).readString(), ((Parcel)object).createTypedArray(InputMethodSubtype.CREATOR));
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCurrentInputMethodSubtype();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((InputMethodSubtype)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isInputMethodPickerShownForTest() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.showInputMethodAndSubtypeEnablerFromClient(IInputMethodClient.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.showInputMethodPickerFromSystem(IInputMethodClient.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.showInputMethodPickerFromClient(IInputMethodClient.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        IInputMethodClient iInputMethodClient = IInputMethodClient.Stub.asInterface(((Parcel)object).readStrongBinder());
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        n2 = ((Parcel)object).readInt();
                        int n3 = ((Parcel)object).readInt();
                        int n4 = ((Parcel)object).readInt();
                        EditorInfo editorInfo = ((Parcel)object).readInt() != 0 ? EditorInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.startInputOrWindowGainedFocus(n, iInputMethodClient, iBinder, n2, n3, n4, editorInfo, IInputContext.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((InputBindResult)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IInputMethodClient iInputMethodClient = IInputMethodClient.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.hideSoftInput(iInputMethodClient, n, (ResultReceiver)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IInputMethodClient iInputMethodClient = IInputMethodClient.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.showSoftInput(iInputMethodClient, n, (ResultReceiver)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getLastInputMethodSubtype();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((InputMethodSubtype)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 4: {
                        boolean bl = false;
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        object = this.getEnabledInputMethodSubtypeList(string2, bl);
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getEnabledInputMethodList(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getInputMethodList(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.addClient(IInputMethodClient.Stub.asInterface(((Parcel)object).readStrongBinder()), IInputContext.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IInputMethodManager {
            public static IInputMethodManager sDefaultImpl;
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
            public void addClient(IInputMethodClient iInputMethodClient, IInputContext iInputContext, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    Object var6_7 = null;
                    IBinder iBinder = iInputMethodClient != null ? iInputMethodClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    iBinder = var6_7;
                    if (iInputContext != null) {
                        iBinder = iInputContext.asBinder();
                    }
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addClient(iInputMethodClient, iInputContext, n);
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

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public InputMethodSubtype getCurrentInputMethodSubtype() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(13, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        InputMethodSubtype inputMethodSubtype = Stub.getDefaultImpl().getCurrentInputMethodSubtype();
                        parcel.recycle();
                        parcel2.recycle();
                        return inputMethodSubtype;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                InputMethodSubtype inputMethodSubtype = parcel.readInt() != 0 ? InputMethodSubtype.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return inputMethodSubtype;
            }

            @Override
            public List<InputMethodInfo> getEnabledInputMethodList(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<InputMethodInfo> list = Stub.getDefaultImpl().getEnabledInputMethodList(n);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<InputMethodInfo> arrayList = parcel2.createTypedArrayList(InputMethodInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<InputMethodSubtype> getEnabledInputMethodSubtypeList(String arrayList, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString((String)((Object)arrayList));
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getEnabledInputMethodSubtypeList((String)((Object)arrayList), bl);
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(InputMethodSubtype.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<InputMethodInfo> getInputMethodList(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<InputMethodInfo> list = Stub.getDefaultImpl().getInputMethodList(n);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<InputMethodInfo> arrayList = parcel2.createTypedArrayList(InputMethodInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getInputMethodWindowVisibleHeight() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getInputMethodWindowVisibleHeight();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public InputMethodSubtype getLastInputMethodSubtype() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(5, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        InputMethodSubtype inputMethodSubtype = Stub.getDefaultImpl().getLastInputMethodSubtype();
                        parcel.recycle();
                        parcel2.recycle();
                        return inputMethodSubtype;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                InputMethodSubtype inputMethodSubtype = parcel.readInt() != 0 ? InputMethodSubtype.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return inputMethodSubtype;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean hideSoftInput(IInputMethodClient iInputMethodClient, int n, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iInputMethodClient != null ? iInputMethodClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    boolean bl = true;
                    if (resultReceiver != null) {
                        parcel.writeInt(1);
                        resultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().hideSoftInput(iInputMethodClient, n, resultReceiver);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public boolean isInputMethodPickerShownForTest() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(12, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isInputMethodPickerShownForTest();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void reportActivityView(IInputMethodClient iInputMethodClient, int n, float[] arrf) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iInputMethodClient != null ? iInputMethodClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeFloatArray(arrf);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportActivityView(iInputMethodClient, n, arrf);
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

            @Override
            public void setAdditionalInputMethodSubtypes(String string2, InputMethodSubtype[] arrinputMethodSubtype) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeTypedArray((Parcelable[])arrinputMethodSubtype, 0);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAdditionalInputMethodSubtypes(string2, arrinputMethodSubtype);
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void showInputMethodAndSubtypeEnablerFromClient(IInputMethodClient iInputMethodClient, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iInputMethodClient != null ? iInputMethodClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showInputMethodAndSubtypeEnablerFromClient(iInputMethodClient, string2);
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void showInputMethodPickerFromClient(IInputMethodClient iInputMethodClient, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iInputMethodClient != null ? iInputMethodClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showInputMethodPickerFromClient(iInputMethodClient, n);
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void showInputMethodPickerFromSystem(IInputMethodClient iInputMethodClient, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iInputMethodClient != null ? iInputMethodClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showInputMethodPickerFromSystem(iInputMethodClient, n, n2);
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean showSoftInput(IInputMethodClient iInputMethodClient, int n, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iInputMethodClient != null ? iInputMethodClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    boolean bl = true;
                    if (resultReceiver != null) {
                        parcel.writeInt(1);
                        resultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().showSoftInput(iInputMethodClient, n, resultReceiver);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
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
            public InputBindResult startInputOrWindowGainedFocus(int n, IInputMethodClient object, IBinder iBinder, int n2, int n3, int n4, EditorInfo editorInfo, IInputContext iInputContext, int n5, int n6) throws RemoteException {
                void var2_5;
                Parcel parcel2;
                Parcel parcel;
                block9 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                        Object var13_16 = null;
                        IBinder iBinder2 = object != null ? object.asBinder() : null;
                        parcel2.writeStrongBinder(iBinder2);
                        parcel2.writeStrongBinder(iBinder);
                        parcel2.writeInt(n2);
                        parcel2.writeInt(n3);
                        parcel2.writeInt(n4);
                        if (editorInfo != null) {
                            parcel2.writeInt(1);
                            editorInfo.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        iBinder2 = var13_16;
                        if (iInputContext != null) {
                            iBinder2 = iInputContext.asBinder();
                        }
                        parcel2.writeStrongBinder(iBinder2);
                        parcel2.writeInt(n5);
                        parcel2.writeInt(n6);
                        if (!this.mRemote.transact(8, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            object = Stub.getDefaultImpl().startInputOrWindowGainedFocus(n, (IInputMethodClient)object, iBinder, n2, n3, n4, editorInfo, iInputContext, n5, n6);
                            parcel.recycle();
                            parcel2.recycle();
                            return object;
                        }
                        parcel.readException();
                        object = parcel.readInt() != 0 ? InputBindResult.CREATOR.createFromParcel(parcel) : null;
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {}
                    break block9;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var2_5;
            }
        }

    }

}

