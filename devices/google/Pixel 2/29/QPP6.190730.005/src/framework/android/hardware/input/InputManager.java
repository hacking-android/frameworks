/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.input;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.content.Context;
import android.hardware.input.IInputDevicesChangedListener;
import android.hardware.input.IInputManager;
import android.hardware.input.ITabletModeChangedListener;
import android.hardware.input.InputDeviceIdentifier;
import android.hardware.input.KeyboardLayout;
import android.hardware.input.TouchCalibration;
import android.media.AudioAttributes;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseArray;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.InputMonitor;
import android.view.PointerIcon;
import com.android.internal.os.SomeArgs;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public final class InputManager {
    public static final String ACTION_QUERY_KEYBOARD_LAYOUTS = "android.hardware.input.action.QUERY_KEYBOARD_LAYOUTS";
    private static final boolean DEBUG = false;
    public static final int DEFAULT_POINTER_SPEED = 0;
    public static final int INJECT_INPUT_EVENT_MODE_ASYNC = 0;
    @UnsupportedAppUsage
    public static final int INJECT_INPUT_EVENT_MODE_WAIT_FOR_FINISH = 2;
    public static final int INJECT_INPUT_EVENT_MODE_WAIT_FOR_RESULT = 1;
    public static final int MAX_POINTER_SPEED = 7;
    public static final String META_DATA_KEYBOARD_LAYOUTS = "android.hardware.input.metadata.KEYBOARD_LAYOUTS";
    public static final int MIN_POINTER_SPEED = -7;
    private static final int MSG_DEVICE_ADDED = 1;
    private static final int MSG_DEVICE_CHANGED = 3;
    private static final int MSG_DEVICE_REMOVED = 2;
    public static final int SWITCH_STATE_OFF = 0;
    public static final int SWITCH_STATE_ON = 1;
    public static final int SWITCH_STATE_UNKNOWN = -1;
    private static final String TAG = "InputManager";
    private static InputManager sInstance;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final IInputManager mIm;
    private final ArrayList<InputDeviceListenerDelegate> mInputDeviceListeners = new ArrayList();
    private SparseArray<InputDevice> mInputDevices;
    private InputDevicesChangedListener mInputDevicesChangedListener;
    private final Object mInputDevicesLock = new Object();
    private List<OnTabletModeChangedListenerDelegate> mOnTabletModeChangedListeners;
    private TabletModeChangedListener mTabletModeChangedListener;
    private final Object mTabletModeLock = new Object();

    private InputManager(IInputManager iInputManager) {
        this.mIm = iInputManager;
    }

    private static boolean containsDeviceId(int[] arrn, int n) {
        for (int i = 0; i < arrn.length; i += 2) {
            if (arrn[i] != n) continue;
            return true;
        }
        return false;
    }

    private int findInputDeviceListenerLocked(InputDeviceListener inputDeviceListener) {
        int n = this.mInputDeviceListeners.size();
        for (int i = 0; i < n; ++i) {
            if (this.mInputDeviceListeners.get((int)i).mListener != inputDeviceListener) continue;
            return i;
        }
        return -1;
    }

    private int findOnTabletModeChangedListenerLocked(OnTabletModeChangedListener onTabletModeChangedListener) {
        int n = this.mOnTabletModeChangedListeners.size();
        for (int i = 0; i < n; ++i) {
            if (this.mOnTabletModeChangedListeners.get((int)i).mListener != onTabletModeChangedListener) continue;
            return i;
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static InputManager getInstance() {
        synchronized (InputManager.class) {
            Object object = sInstance;
            if (object != null) return sInstance;
            try {
                sInstance = object = new InputManager(IInputManager.Stub.asInterface(ServiceManager.getServiceOrThrow("input")));
                return sInstance;
            }
            catch (ServiceManager.ServiceNotFoundException serviceNotFoundException) {
                object = new IllegalStateException(serviceNotFoundException);
                throw object;
            }
        }
    }

    private void initializeTabletModeListenerLocked() {
        TabletModeChangedListener tabletModeChangedListener = new TabletModeChangedListener();
        try {
            this.mIm.registerTabletModeChangedListener(tabletModeChangedListener);
            this.mTabletModeChangedListener = tabletModeChangedListener;
            this.mOnTabletModeChangedListeners = new ArrayList<OnTabletModeChangedListenerDelegate>();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onInputDevicesChanged(int[] arrn) {
        Object object = this.mInputDevicesLock;
        synchronized (object) {
            int n;
            int n2 = this.mInputDevices.size();
            while (--n2 > 0) {
                n = this.mInputDevices.keyAt(n2);
                if (InputManager.containsDeviceId(arrn, n)) continue;
                this.mInputDevices.removeAt(n2);
                this.sendMessageToInputDeviceListenersLocked(2, n);
            }
            n2 = 0;
            while (n2 < arrn.length) {
                n = arrn[n2];
                int n3 = this.mInputDevices.indexOfKey(n);
                if (n3 >= 0) {
                    InputDevice inputDevice = this.mInputDevices.valueAt(n3);
                    if (inputDevice != null) {
                        int n4 = arrn[n2 + 1];
                        if (inputDevice.getGeneration() != n4) {
                            this.mInputDevices.setValueAt(n3, null);
                            this.sendMessageToInputDeviceListenersLocked(3, n);
                        }
                    }
                } else {
                    this.mInputDevices.put(n, null);
                    this.sendMessageToInputDeviceListenersLocked(1, n);
                }
                n2 += 2;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onTabletModeChanged(long l, boolean bl) {
        Object object = this.mTabletModeLock;
        synchronized (object) {
            int n = this.mOnTabletModeChangedListeners.size();
            int n2 = 0;
            while (n2 < n) {
                this.mOnTabletModeChangedListeners.get(n2).sendTabletModeChanged(l, bl);
                ++n2;
            }
            return;
        }
    }

    private void populateInputDevicesLocked() {
        int[] arrn;
        if (this.mInputDevicesChangedListener == null) {
            arrn = new InputDevicesChangedListener();
            try {
                this.mIm.registerInputDevicesChangedListener((IInputDevicesChangedListener)arrn);
                this.mInputDevicesChangedListener = arrn;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        if (this.mInputDevices == null) {
            try {
                arrn = this.mIm.getInputDeviceIds();
                this.mInputDevices = new SparseArray();
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            for (int i = 0; i < arrn.length; ++i) {
                this.mInputDevices.put(arrn[i], null);
            }
        }
    }

    private void sendMessageToInputDeviceListenersLocked(int n, int n2) {
        int n3 = this.mInputDeviceListeners.size();
        for (int i = 0; i < n3; ++i) {
            InputDeviceListenerDelegate inputDeviceListenerDelegate = this.mInputDeviceListeners.get(i);
            inputDeviceListenerDelegate.sendMessage(inputDeviceListenerDelegate.obtainMessage(n, n2, 0));
        }
    }

    public void addKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String string2) {
        if (inputDeviceIdentifier != null) {
            if (string2 != null) {
                try {
                    this.mIm.addKeyboardLayoutForInputDevice(inputDeviceIdentifier, string2);
                    return;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            throw new IllegalArgumentException("keyboardLayoutDescriptor must not be null");
        }
        throw new IllegalArgumentException("inputDeviceDescriptor must not be null");
    }

    public boolean[] deviceHasKeys(int n, int[] arrn) {
        boolean[] arrbl = new boolean[arrn.length];
        try {
            this.mIm.hasKeys(n, -256, arrn, arrbl);
            return arrbl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean[] deviceHasKeys(int[] arrn) {
        return this.deviceHasKeys(-1, arrn);
    }

    public void disableInputDevice(int n) {
        try {
            this.mIm.disableInputDevice(n);
            return;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not disable input device with id = ");
            stringBuilder.append(n);
            Log.w(TAG, stringBuilder.toString());
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void enableInputDevice(int n) {
        try {
            this.mIm.enableInputDevice(n);
            return;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not enable input device with id = ");
            stringBuilder.append(n);
            Log.w(TAG, stringBuilder.toString());
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public String getCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier object) {
        try {
            object = this.mIm.getCurrentKeyboardLayoutForInputDevice((InputDeviceIdentifier)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public String[] getEnabledKeyboardLayoutsForInputDevice(InputDeviceIdentifier arrstring) {
        if (arrstring != null) {
            try {
                arrstring = this.mIm.getEnabledKeyboardLayoutsForInputDevice((InputDeviceIdentifier)arrstring);
                return arrstring;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("inputDeviceDescriptor must not be null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public InputDevice getInputDevice(int n) {
        Object object = this.mInputDevicesLock;
        synchronized (object) {
            InputDevice inputDevice;
            this.populateInputDevicesLocked();
            int n2 = this.mInputDevices.indexOfKey(n);
            if (n2 < 0) {
                return null;
            }
            InputDevice inputDevice2 = inputDevice = this.mInputDevices.valueAt(n2);
            if (inputDevice != null) return inputDevice2;
            try {
                inputDevice2 = inputDevice = this.mIm.getInputDevice(n);
                if (inputDevice == null) return inputDevice2;
                this.mInputDevices.setValueAt(n2, inputDevice);
                return inputDevice;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public InputDevice getInputDeviceByDescriptor(String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("descriptor must not be null.");
        }
        Object object = this.mInputDevicesLock;
        synchronized (object) {
            this.populateInputDevicesLocked();
            int n = this.mInputDevices.size();
            int n2 = 0;
            do {
                block10 : {
                    InputDevice inputDevice;
                    if (n2 >= n) {
                        return null;
                    }
                    InputDevice inputDevice2 = inputDevice = this.mInputDevices.valueAt(n2);
                    if (inputDevice == null) {
                        int n3 = this.mInputDevices.keyAt(n2);
                        try {
                            inputDevice2 = this.mIm.getInputDevice(n3);
                            if (inputDevice2 == null) break block10;
                            this.mInputDevices.setValueAt(n2, inputDevice2);
                        }
                        catch (RemoteException remoteException) {
                            throw remoteException.rethrowFromSystemServer();
                        }
                    }
                    if (string2.equals(inputDevice2.getDescriptor())) {
                        return inputDevice2;
                    }
                }
                ++n2;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int[] getInputDeviceIds() {
        Object object = this.mInputDevicesLock;
        synchronized (object) {
            this.populateInputDevicesLocked();
            int n = this.mInputDevices.size();
            int[] arrn = new int[n];
            int n2 = 0;
            while (n2 < n) {
                arrn[n2] = this.mInputDevices.keyAt(n2);
                ++n2;
            }
            return arrn;
        }
    }

    public Vibrator getInputDeviceVibrator(int n) {
        return new InputDeviceVibrator(n);
    }

    public KeyboardLayout getKeyboardLayout(String object) {
        if (object != null) {
            try {
                object = this.mIm.getKeyboardLayout((String)object);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("keyboardLayoutDescriptor must not be null");
    }

    public KeyboardLayout[] getKeyboardLayouts() {
        try {
            KeyboardLayout[] arrkeyboardLayout = this.mIm.getKeyboardLayouts();
            return arrkeyboardLayout;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public KeyboardLayout[] getKeyboardLayoutsForInputDevice(InputDeviceIdentifier arrkeyboardLayout) {
        try {
            arrkeyboardLayout = this.mIm.getKeyboardLayoutsForInputDevice((InputDeviceIdentifier)arrkeyboardLayout);
            return arrkeyboardLayout;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getPointerSpeed(Context context) {
        int n = 0;
        try {
            int n2;
            n = n2 = Settings.System.getInt(context.getContentResolver(), "pointer_speed");
        }
        catch (Settings.SettingNotFoundException settingNotFoundException) {
            // empty catch block
        }
        return n;
    }

    public TouchCalibration getTouchCalibration(String object, int n) {
        try {
            object = this.mIm.getTouchCalibrationForInputDevice((String)object, n);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public boolean injectInputEvent(InputEvent inputEvent, int n) {
        if (inputEvent != null) {
            if (n != 0 && n != 2 && n != 1) {
                throw new IllegalArgumentException("mode is invalid");
            }
            try {
                boolean bl = this.mIm.injectInputEvent(inputEvent, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("event must not be null");
    }

    public int isInTabletMode() {
        try {
            int n = this.mIm.isInTabletMode();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isInputDeviceEnabled(int n) {
        try {
            boolean bl = this.mIm.isInputDeviceEnabled(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not check enabled status of input device with id = ");
            stringBuilder.append(n);
            Log.w(TAG, stringBuilder.toString());
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public InputMonitor monitorGestureInput(String object, int n) {
        try {
            object = this.mIm.monitorGestureInput((String)object, n);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerInputDeviceListener(InputDeviceListener inputDeviceListener, Handler handler) {
        if (inputDeviceListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        Object object = this.mInputDevicesLock;
        synchronized (object) {
            this.populateInputDevicesLocked();
            if (this.findInputDeviceListenerLocked(inputDeviceListener) < 0) {
                ArrayList<InputDeviceListenerDelegate> arrayList = this.mInputDeviceListeners;
                InputDeviceListenerDelegate inputDeviceListenerDelegate = new InputDeviceListenerDelegate(inputDeviceListener, handler);
                arrayList.add(inputDeviceListenerDelegate);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerOnTabletModeChangedListener(OnTabletModeChangedListener onTabletModeChangedListener, Handler handler) {
        if (onTabletModeChangedListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        Object object = this.mTabletModeLock;
        synchronized (object) {
            if (this.mOnTabletModeChangedListeners == null) {
                this.initializeTabletModeListenerLocked();
            }
            if (this.findOnTabletModeChangedListenerLocked(onTabletModeChangedListener) < 0) {
                OnTabletModeChangedListenerDelegate onTabletModeChangedListenerDelegate = new OnTabletModeChangedListenerDelegate(onTabletModeChangedListener, handler);
                this.mOnTabletModeChangedListeners.add(onTabletModeChangedListenerDelegate);
            }
            return;
        }
    }

    public void removeKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String string2) {
        if (inputDeviceIdentifier != null) {
            if (string2 != null) {
                try {
                    this.mIm.removeKeyboardLayoutForInputDevice(inputDeviceIdentifier, string2);
                    return;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            throw new IllegalArgumentException("keyboardLayoutDescriptor must not be null");
        }
        throw new IllegalArgumentException("inputDeviceDescriptor must not be null");
    }

    public void requestPointerCapture(IBinder iBinder, boolean bl) {
        try {
            this.mIm.requestPointerCapture(iBinder, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String string2) {
        if (inputDeviceIdentifier != null) {
            if (string2 != null) {
                try {
                    this.mIm.setCurrentKeyboardLayoutForInputDevice(inputDeviceIdentifier, string2);
                    return;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            throw new IllegalArgumentException("keyboardLayoutDescriptor must not be null");
        }
        throw new IllegalArgumentException("identifier must not be null");
    }

    public void setCustomPointerIcon(PointerIcon pointerIcon) {
        try {
            this.mIm.setCustomPointerIcon(pointerIcon);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void setPointerIconType(int n) {
        try {
            this.mIm.setPointerIconType(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setPointerSpeed(Context context, int n) {
        if (n >= -7 && n <= 7) {
            Settings.System.putInt(context.getContentResolver(), "pointer_speed", n);
            return;
        }
        throw new IllegalArgumentException("speed out of range");
    }

    public void setTouchCalibration(String string2, int n, TouchCalibration touchCalibration) {
        try {
            this.mIm.setTouchCalibrationForInputDevice(string2, n, touchCalibration);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void tryPointerSpeed(int n) {
        if (n >= -7 && n <= 7) {
            try {
                this.mIm.tryPointerSpeed(n);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("speed out of range");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterInputDeviceListener(InputDeviceListener inputDeviceListener) {
        if (inputDeviceListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        Object object = this.mInputDevicesLock;
        synchronized (object) {
            int n = this.findInputDeviceListenerLocked(inputDeviceListener);
            if (n >= 0) {
                this.mInputDeviceListeners.get(n).removeCallbacksAndMessages(null);
                this.mInputDeviceListeners.remove(n);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterOnTabletModeChangedListener(OnTabletModeChangedListener onTabletModeChangedListener) {
        if (onTabletModeChangedListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        Object object = this.mTabletModeLock;
        synchronized (object) {
            int n = this.findOnTabletModeChangedListenerLocked(onTabletModeChangedListener);
            if (n >= 0) {
                this.mOnTabletModeChangedListeners.remove(n).removeCallbacksAndMessages(null);
            }
            return;
        }
    }

    public static interface InputDeviceListener {
        public void onInputDeviceAdded(int var1);

        public void onInputDeviceChanged(int var1);

        public void onInputDeviceRemoved(int var1);
    }

    private static final class InputDeviceListenerDelegate
    extends Handler {
        public final InputDeviceListener mListener;

        public InputDeviceListenerDelegate(InputDeviceListener inputDeviceListener, Handler object) {
            object = object != null ? ((Handler)object).getLooper() : Looper.myLooper();
            super((Looper)object);
            this.mListener = inputDeviceListener;
        }

        @Override
        public void handleMessage(Message message) {
            int n = message.what;
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        this.mListener.onInputDeviceChanged(message.arg1);
                    }
                } else {
                    this.mListener.onInputDeviceRemoved(message.arg1);
                }
            } else {
                this.mListener.onInputDeviceAdded(message.arg1);
            }
        }
    }

    private final class InputDeviceVibrator
    extends Vibrator {
        private final int mDeviceId;
        private final Binder mToken;

        public InputDeviceVibrator(int n) {
            this.mDeviceId = n;
            this.mToken = new Binder();
        }

        @Override
        public void cancel() {
            try {
                InputManager.this.mIm.cancelVibrate(this.mDeviceId, this.mToken);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        @Override
        public boolean hasAmplitudeControl() {
            return false;
        }

        @Override
        public boolean hasVibrator() {
            return true;
        }

        @Override
        public void vibrate(int n, String arrl, VibrationEffect vibrationEffect, String string2, AudioAttributes audioAttributes) {
            block6 : {
                block5 : {
                    block4 : {
                        if (!(vibrationEffect instanceof VibrationEffect.OneShot)) break block4;
                        vibrationEffect = (VibrationEffect.OneShot)vibrationEffect;
                        arrl = new long[]{0L, ((VibrationEffect.OneShot)vibrationEffect).getDuration()};
                        n = -1;
                        break block5;
                    }
                    if (!(vibrationEffect instanceof VibrationEffect.Waveform)) break block6;
                    vibrationEffect = (VibrationEffect.Waveform)vibrationEffect;
                    arrl = ((VibrationEffect.Waveform)vibrationEffect).getTimings();
                    n = ((VibrationEffect.Waveform)vibrationEffect).getRepeatIndex();
                }
                try {
                    InputManager.this.mIm.vibrate(this.mDeviceId, arrl, n, this.mToken);
                    return;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            Log.w(InputManager.TAG, "Pre-baked effects aren't supported on input devices");
        }
    }

    private final class InputDevicesChangedListener
    extends IInputDevicesChangedListener.Stub {
        private InputDevicesChangedListener() {
        }

        @Override
        public void onInputDevicesChanged(int[] arrn) throws RemoteException {
            InputManager.this.onInputDevicesChanged(arrn);
        }
    }

    public static interface OnTabletModeChangedListener {
        public void onTabletModeChanged(long var1, boolean var3);
    }

    private static final class OnTabletModeChangedListenerDelegate
    extends Handler {
        private static final int MSG_TABLET_MODE_CHANGED = 0;
        public final OnTabletModeChangedListener mListener;

        public OnTabletModeChangedListenerDelegate(OnTabletModeChangedListener onTabletModeChangedListener, Handler object) {
            object = object != null ? ((Handler)object).getLooper() : Looper.myLooper();
            super((Looper)object);
            this.mListener = onTabletModeChangedListener;
        }

        @Override
        public void handleMessage(Message object) {
            if (((Message)object).what == 0) {
                object = (SomeArgs)((Message)object).obj;
                long l = ((SomeArgs)object).argi1;
                long l2 = ((SomeArgs)object).argi2;
                boolean bl = (Boolean)((SomeArgs)object).arg1;
                this.mListener.onTabletModeChanged(l & 0xFFFFFFFFL | l2 << 32, bl);
            }
        }

        public void sendTabletModeChanged(long l, boolean bl) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.argi1 = (int)(-1L & l);
            someArgs.argi2 = (int)(l >> 32);
            someArgs.arg1 = bl;
            this.obtainMessage(0, someArgs).sendToTarget();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SwitchState {
    }

    private final class TabletModeChangedListener
    extends ITabletModeChangedListener.Stub {
        private TabletModeChangedListener() {
        }

        @Override
        public void onTabletModeChanged(long l, boolean bl) {
            InputManager.this.onTabletModeChanged(l, bl);
        }
    }

}

