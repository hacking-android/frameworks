/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.input;

import android.annotation.UnsupportedAppUsage;
import android.hardware.input.IInputDevicesChangedListener;
import android.hardware.input.ITabletModeChangedListener;
import android.hardware.input.InputDeviceIdentifier;
import android.hardware.input.KeyboardLayout;
import android.hardware.input.TouchCalibration;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.InputMonitor;
import android.view.PointerIcon;

public interface IInputManager
extends IInterface {
    public void addKeyboardLayoutForInputDevice(InputDeviceIdentifier var1, String var2) throws RemoteException;

    public void cancelVibrate(int var1, IBinder var2) throws RemoteException;

    public void disableInputDevice(int var1) throws RemoteException;

    public void enableInputDevice(int var1) throws RemoteException;

    public String getCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier var1) throws RemoteException;

    public String[] getEnabledKeyboardLayoutsForInputDevice(InputDeviceIdentifier var1) throws RemoteException;

    public InputDevice getInputDevice(int var1) throws RemoteException;

    public int[] getInputDeviceIds() throws RemoteException;

    public KeyboardLayout getKeyboardLayout(String var1) throws RemoteException;

    public KeyboardLayout[] getKeyboardLayouts() throws RemoteException;

    public KeyboardLayout[] getKeyboardLayoutsForInputDevice(InputDeviceIdentifier var1) throws RemoteException;

    public TouchCalibration getTouchCalibrationForInputDevice(String var1, int var2) throws RemoteException;

    public boolean hasKeys(int var1, int var2, int[] var3, boolean[] var4) throws RemoteException;

    @UnsupportedAppUsage
    public boolean injectInputEvent(InputEvent var1, int var2) throws RemoteException;

    public int isInTabletMode() throws RemoteException;

    public boolean isInputDeviceEnabled(int var1) throws RemoteException;

    public InputMonitor monitorGestureInput(String var1, int var2) throws RemoteException;

    public void registerInputDevicesChangedListener(IInputDevicesChangedListener var1) throws RemoteException;

    public void registerTabletModeChangedListener(ITabletModeChangedListener var1) throws RemoteException;

    public void removeKeyboardLayoutForInputDevice(InputDeviceIdentifier var1, String var2) throws RemoteException;

    public void requestPointerCapture(IBinder var1, boolean var2) throws RemoteException;

    public void setCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier var1, String var2) throws RemoteException;

    public void setCustomPointerIcon(PointerIcon var1) throws RemoteException;

    public void setPointerIconType(int var1) throws RemoteException;

    public void setTouchCalibrationForInputDevice(String var1, int var2, TouchCalibration var3) throws RemoteException;

    public void tryPointerSpeed(int var1) throws RemoteException;

    public void vibrate(int var1, long[] var2, int var3, IBinder var4) throws RemoteException;

    public static class Default
    implements IInputManager {
        @Override
        public void addKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String string2) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancelVibrate(int n, IBinder iBinder) throws RemoteException {
        }

        @Override
        public void disableInputDevice(int n) throws RemoteException {
        }

        @Override
        public void enableInputDevice(int n) throws RemoteException {
        }

        @Override
        public String getCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier) throws RemoteException {
            return null;
        }

        @Override
        public String[] getEnabledKeyboardLayoutsForInputDevice(InputDeviceIdentifier inputDeviceIdentifier) throws RemoteException {
            return null;
        }

        @Override
        public InputDevice getInputDevice(int n) throws RemoteException {
            return null;
        }

        @Override
        public int[] getInputDeviceIds() throws RemoteException {
            return null;
        }

        @Override
        public KeyboardLayout getKeyboardLayout(String string2) throws RemoteException {
            return null;
        }

        @Override
        public KeyboardLayout[] getKeyboardLayouts() throws RemoteException {
            return null;
        }

        @Override
        public KeyboardLayout[] getKeyboardLayoutsForInputDevice(InputDeviceIdentifier inputDeviceIdentifier) throws RemoteException {
            return null;
        }

        @Override
        public TouchCalibration getTouchCalibrationForInputDevice(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public boolean hasKeys(int n, int n2, int[] arrn, boolean[] arrbl) throws RemoteException {
            return false;
        }

        @Override
        public boolean injectInputEvent(InputEvent inputEvent, int n) throws RemoteException {
            return false;
        }

        @Override
        public int isInTabletMode() throws RemoteException {
            return 0;
        }

        @Override
        public boolean isInputDeviceEnabled(int n) throws RemoteException {
            return false;
        }

        @Override
        public InputMonitor monitorGestureInput(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public void registerInputDevicesChangedListener(IInputDevicesChangedListener iInputDevicesChangedListener) throws RemoteException {
        }

        @Override
        public void registerTabletModeChangedListener(ITabletModeChangedListener iTabletModeChangedListener) throws RemoteException {
        }

        @Override
        public void removeKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String string2) throws RemoteException {
        }

        @Override
        public void requestPointerCapture(IBinder iBinder, boolean bl) throws RemoteException {
        }

        @Override
        public void setCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String string2) throws RemoteException {
        }

        @Override
        public void setCustomPointerIcon(PointerIcon pointerIcon) throws RemoteException {
        }

        @Override
        public void setPointerIconType(int n) throws RemoteException {
        }

        @Override
        public void setTouchCalibrationForInputDevice(String string2, int n, TouchCalibration touchCalibration) throws RemoteException {
        }

        @Override
        public void tryPointerSpeed(int n) throws RemoteException {
        }

        @Override
        public void vibrate(int n, long[] arrl, int n2, IBinder iBinder) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IInputManager {
        private static final String DESCRIPTOR = "android.hardware.input.IInputManager";
        static final int TRANSACTION_addKeyboardLayoutForInputDevice = 17;
        static final int TRANSACTION_cancelVibrate = 23;
        static final int TRANSACTION_disableInputDevice = 5;
        static final int TRANSACTION_enableInputDevice = 4;
        static final int TRANSACTION_getCurrentKeyboardLayoutForInputDevice = 14;
        static final int TRANSACTION_getEnabledKeyboardLayoutsForInputDevice = 16;
        static final int TRANSACTION_getInputDevice = 1;
        static final int TRANSACTION_getInputDeviceIds = 2;
        static final int TRANSACTION_getKeyboardLayout = 13;
        static final int TRANSACTION_getKeyboardLayouts = 11;
        static final int TRANSACTION_getKeyboardLayoutsForInputDevice = 12;
        static final int TRANSACTION_getTouchCalibrationForInputDevice = 9;
        static final int TRANSACTION_hasKeys = 6;
        static final int TRANSACTION_injectInputEvent = 8;
        static final int TRANSACTION_isInTabletMode = 20;
        static final int TRANSACTION_isInputDeviceEnabled = 3;
        static final int TRANSACTION_monitorGestureInput = 27;
        static final int TRANSACTION_registerInputDevicesChangedListener = 19;
        static final int TRANSACTION_registerTabletModeChangedListener = 21;
        static final int TRANSACTION_removeKeyboardLayoutForInputDevice = 18;
        static final int TRANSACTION_requestPointerCapture = 26;
        static final int TRANSACTION_setCurrentKeyboardLayoutForInputDevice = 15;
        static final int TRANSACTION_setCustomPointerIcon = 25;
        static final int TRANSACTION_setPointerIconType = 24;
        static final int TRANSACTION_setTouchCalibrationForInputDevice = 10;
        static final int TRANSACTION_tryPointerSpeed = 7;
        static final int TRANSACTION_vibrate = 22;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IInputManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IInputManager) {
                return (IInputManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IInputManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 27: {
                    return "monitorGestureInput";
                }
                case 26: {
                    return "requestPointerCapture";
                }
                case 25: {
                    return "setCustomPointerIcon";
                }
                case 24: {
                    return "setPointerIconType";
                }
                case 23: {
                    return "cancelVibrate";
                }
                case 22: {
                    return "vibrate";
                }
                case 21: {
                    return "registerTabletModeChangedListener";
                }
                case 20: {
                    return "isInTabletMode";
                }
                case 19: {
                    return "registerInputDevicesChangedListener";
                }
                case 18: {
                    return "removeKeyboardLayoutForInputDevice";
                }
                case 17: {
                    return "addKeyboardLayoutForInputDevice";
                }
                case 16: {
                    return "getEnabledKeyboardLayoutsForInputDevice";
                }
                case 15: {
                    return "setCurrentKeyboardLayoutForInputDevice";
                }
                case 14: {
                    return "getCurrentKeyboardLayoutForInputDevice";
                }
                case 13: {
                    return "getKeyboardLayout";
                }
                case 12: {
                    return "getKeyboardLayoutsForInputDevice";
                }
                case 11: {
                    return "getKeyboardLayouts";
                }
                case 10: {
                    return "setTouchCalibrationForInputDevice";
                }
                case 9: {
                    return "getTouchCalibrationForInputDevice";
                }
                case 8: {
                    return "injectInputEvent";
                }
                case 7: {
                    return "tryPointerSpeed";
                }
                case 6: {
                    return "hasKeys";
                }
                case 5: {
                    return "disableInputDevice";
                }
                case 4: {
                    return "enableInputDevice";
                }
                case 3: {
                    return "isInputDeviceEnabled";
                }
                case 2: {
                    return "getInputDeviceIds";
                }
                case 1: 
            }
            return "getInputDevice";
        }

        public static boolean setDefaultImpl(IInputManager iInputManager) {
            if (Proxy.sDefaultImpl == null && iInputManager != null) {
                Proxy.sDefaultImpl = iInputManager;
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
                boolean bl = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.monitorGestureInput(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((InputMonitor)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        this.requestPointerCapture(iBinder, bl);
                        parcel.writeNoException();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PointerIcon.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setCustomPointerIcon((PointerIcon)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setPointerIconType(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancelVibrate(((Parcel)object).readInt(), ((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.vibrate(((Parcel)object).readInt(), ((Parcel)object).createLongArray(), ((Parcel)object).readInt(), ((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerTabletModeChangedListener(ITabletModeChangedListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isInTabletMode();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerInputDevicesChangedListener(IInputDevicesChangedListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        InputDeviceIdentifier inputDeviceIdentifier = ((Parcel)object).readInt() != 0 ? InputDeviceIdentifier.CREATOR.createFromParcel((Parcel)object) : null;
                        this.removeKeyboardLayoutForInputDevice(inputDeviceIdentifier, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        InputDeviceIdentifier inputDeviceIdentifier = ((Parcel)object).readInt() != 0 ? InputDeviceIdentifier.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addKeyboardLayoutForInputDevice(inputDeviceIdentifier, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? InputDeviceIdentifier.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getEnabledKeyboardLayoutsForInputDevice((InputDeviceIdentifier)object);
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        InputDeviceIdentifier inputDeviceIdentifier = ((Parcel)object).readInt() != 0 ? InputDeviceIdentifier.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setCurrentKeyboardLayoutForInputDevice(inputDeviceIdentifier, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? InputDeviceIdentifier.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getCurrentKeyboardLayoutForInputDevice((InputDeviceIdentifier)object);
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getKeyboardLayout(((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((KeyboardLayout)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? InputDeviceIdentifier.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getKeyboardLayoutsForInputDevice((InputDeviceIdentifier)object);
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getKeyboardLayouts();
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? TouchCalibration.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setTouchCalibrationForInputDevice(string2, n, (TouchCalibration)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTouchCalibrationForInputDevice(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((TouchCalibration)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        InputEvent inputEvent = ((Parcel)object).readInt() != 0 ? InputEvent.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.injectInputEvent(inputEvent, ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.tryPointerSpeed(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        int n3 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        int[] arrn = ((Parcel)object).createIntArray();
                        n2 = ((Parcel)object).readInt();
                        object = n2 < 0 ? null : new boolean[n2];
                        n = this.hasKeys(n3, n, arrn, (boolean[])object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        parcel.writeBooleanArray((boolean[])object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disableInputDevice(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.enableInputDevice(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isInputDeviceEnabled(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getInputDeviceIds();
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getInputDevice(((Parcel)object).readInt());
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((InputDevice)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IInputManager {
            public static IInputManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void addKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (inputDeviceIdentifier != null) {
                        parcel.writeInt(1);
                        inputDeviceIdentifier.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addKeyboardLayoutForInputDevice(inputDeviceIdentifier, string2);
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
            public void cancelVibrate(int n, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelVibrate(n, iBinder);
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
            public void disableInputDevice(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableInputDevice(n);
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
            public void enableInputDevice(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableInputDevice(n);
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
            public String getCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((InputDeviceIdentifier)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getCurrentKeyboardLayoutForInputDevice((InputDeviceIdentifier)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readString();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getEnabledKeyboardLayoutsForInputDevice(InputDeviceIdentifier arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (arrstring != null) {
                        parcel.writeInt(1);
                        arrstring.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrstring = Stub.getDefaultImpl().getEnabledKeyboardLayoutsForInputDevice((InputDeviceIdentifier)arrstring);
                        return arrstring;
                    }
                    parcel2.readException();
                    arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public InputDevice getInputDevice(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(1, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        InputDevice inputDevice = Stub.getDefaultImpl().getInputDevice(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return inputDevice;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                InputDevice inputDevice = parcel2.readInt() != 0 ? InputDevice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return inputDevice;
            }

            @Override
            public int[] getInputDeviceIds() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int[] arrn = Stub.getDefaultImpl().getInputDeviceIds();
                        return arrn;
                    }
                    parcel2.readException();
                    int[] arrn = parcel2.createIntArray();
                    return arrn;
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
            public KeyboardLayout getKeyboardLayout(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(13, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getKeyboardLayout((String)object);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? KeyboardLayout.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public KeyboardLayout[] getKeyboardLayouts() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        KeyboardLayout[] arrkeyboardLayout = Stub.getDefaultImpl().getKeyboardLayouts();
                        return arrkeyboardLayout;
                    }
                    parcel2.readException();
                    KeyboardLayout[] arrkeyboardLayout = parcel2.createTypedArray(KeyboardLayout.CREATOR);
                    return arrkeyboardLayout;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public KeyboardLayout[] getKeyboardLayoutsForInputDevice(InputDeviceIdentifier arrkeyboardLayout) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (arrkeyboardLayout != null) {
                        parcel.writeInt(1);
                        arrkeyboardLayout.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrkeyboardLayout = Stub.getDefaultImpl().getKeyboardLayoutsForInputDevice((InputDeviceIdentifier)arrkeyboardLayout);
                        return arrkeyboardLayout;
                    }
                    parcel2.readException();
                    arrkeyboardLayout = parcel2.createTypedArray(KeyboardLayout.CREATOR);
                    return arrkeyboardLayout;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public TouchCalibration getTouchCalibrationForInputDevice(String object, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(9, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getTouchCalibrationForInputDevice((String)object, n);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? TouchCalibration.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public boolean hasKeys(int n, int n2, int[] arrn, boolean[] arrbl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeIntArray(arrn);
                    if (arrbl == null) {
                        parcel.writeInt(-1);
                    } else {
                        parcel.writeInt(arrbl.length);
                    }
                    IBinder iBinder = this.mRemote;
                    boolean bl = false;
                    if (!iBinder.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().hasKeys(n, n2, arrn, arrbl);
                        return bl;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        bl = true;
                    }
                    parcel2.readBooleanArray(arrbl);
                    return bl;
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
            public boolean injectInputEvent(InputEvent inputEvent, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (inputEvent != null) {
                        parcel.writeInt(1);
                        inputEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().injectInputEvent(inputEvent, n);
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
            public int isInTabletMode() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().isInTabletMode();
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

            @Override
            public boolean isInputDeviceEnabled(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(3, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isInputDeviceEnabled(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public InputMonitor monitorGestureInput(String object, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(27, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().monitorGestureInput((String)object, n);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? InputMonitor.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerInputDevicesChangedListener(IInputDevicesChangedListener iInputDevicesChangedListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iInputDevicesChangedListener != null ? iInputDevicesChangedListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerInputDevicesChangedListener(iInputDevicesChangedListener);
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
            public void registerTabletModeChangedListener(ITabletModeChangedListener iTabletModeChangedListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iTabletModeChangedListener != null ? iTabletModeChangedListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerTabletModeChangedListener(iTabletModeChangedListener);
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
            public void removeKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (inputDeviceIdentifier != null) {
                        parcel.writeInt(1);
                        inputDeviceIdentifier.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeKeyboardLayoutForInputDevice(inputDeviceIdentifier, string2);
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
            public void requestPointerCapture(IBinder iBinder, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestPointerCapture(iBinder, bl);
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
            public void setCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (inputDeviceIdentifier != null) {
                        parcel.writeInt(1);
                        inputDeviceIdentifier.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCurrentKeyboardLayoutForInputDevice(inputDeviceIdentifier, string2);
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
            public void setCustomPointerIcon(PointerIcon pointerIcon) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (pointerIcon != null) {
                        parcel.writeInt(1);
                        pointerIcon.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCustomPointerIcon(pointerIcon);
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
            public void setPointerIconType(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPointerIconType(n);
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
            public void setTouchCalibrationForInputDevice(String string2, int n, TouchCalibration touchCalibration) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (touchCalibration != null) {
                        parcel.writeInt(1);
                        touchCalibration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTouchCalibrationForInputDevice(string2, n, touchCalibration);
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
            public void tryPointerSpeed(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().tryPointerSpeed(n);
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
            public void vibrate(int n, long[] arrl, int n2, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeLongArray(arrl);
                    parcel.writeInt(n2);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().vibrate(n, arrl, n2, iBinder);
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

