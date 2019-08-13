/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.hardware;

import android.annotation.UnsupportedAppUsage;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.hardware.HardwareBuffer;
import android.hardware.Sensor;
import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorDirectChannel;
import android.hardware.SensorEvent;
import android.hardware.SensorEventCallback;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.Handler;
import android.os.Looper;
import android.os.MemoryFile;
import android.os.MessageQueue;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import com.android.internal.annotations.GuardedBy;
import dalvik.system.CloseGuard;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SystemSensorManager
extends SensorManager {
    private static final boolean DEBUG_DYNAMIC_SENSOR = true;
    private static final int MAX_LISTENER_COUNT = 128;
    private static final int MIN_DIRECT_CHANNEL_BUFFER_SIZE = 104;
    @GuardedBy(value={"sLock"})
    private static InjectEventQueue sInjectEventQueue;
    private static final Object sLock;
    @GuardedBy(value={"sLock"})
    private static boolean sNativeClassInited;
    private final Context mContext;
    private BroadcastReceiver mDynamicSensorBroadcastReceiver;
    private HashMap<SensorManager.DynamicSensorCallback, Handler> mDynamicSensorCallbacks = new HashMap();
    private boolean mDynamicSensorListDirty = true;
    private List<Sensor> mFullDynamicSensorsList = new ArrayList<Sensor>();
    private final ArrayList<Sensor> mFullSensorsList = new ArrayList();
    private final HashMap<Integer, Sensor> mHandleToSensor = new HashMap();
    private final Looper mMainLooper;
    private final long mNativeInstance;
    private final HashMap<SensorEventListener, SensorEventQueue> mSensorListeners = new HashMap();
    private final int mTargetSdkLevel;
    private final HashMap<TriggerEventListener, TriggerEventQueue> mTriggerListeners = new HashMap();

    static {
        sLock = new Object();
        sNativeClassInited = false;
        sInjectEventQueue = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SystemSensorManager(Context object, Looper looper) {
        Object object2 = sLock;
        synchronized (object2) {
            if (!sNativeClassInited) {
                sNativeClassInited = true;
                SystemSensorManager.nativeClassInit();
            }
        }
        this.mMainLooper = looper;
        this.mTargetSdkLevel = object.getApplicationInfo().targetSdkVersion;
        this.mContext = object;
        this.mNativeInstance = SystemSensorManager.nativeCreate(((Context)object).getOpPackageName());
        int n = 0;
        while (SystemSensorManager.nativeGetSensorAtIndex(this.mNativeInstance, (Sensor)(object = new Sensor()), n)) {
            this.mFullSensorsList.add((Sensor)object);
            this.mHandleToSensor.put(((Sensor)object).getHandle(), (Sensor)object);
            ++n;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void cleanupSensorConnection(Sensor sensor) {
        this.mHandleToSensor.remove(sensor.getHandle());
        if (sensor.getReportingMode() == 2) {
            HashMap<TriggerEventListener, TriggerEventQueue> hashMap = this.mTriggerListeners;
            synchronized (hashMap) {
                Object object = new HashMap(this.mTriggerListeners);
                Iterator<TriggerEventListener> iterator = ((HashMap)object).keySet().iterator();
                while (iterator.hasNext()) {
                    object = iterator.next();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("removed trigger listener");
                    stringBuilder.append(object.toString());
                    stringBuilder.append(" due to sensor disconnection");
                    Log.i("SensorManager", stringBuilder.toString());
                    this.cancelTriggerSensorImpl((TriggerEventListener)object, sensor, true);
                }
                return;
            }
        }
        HashMap<SensorEventListener, SensorEventQueue> hashMap = this.mSensorListeners;
        synchronized (hashMap) {
            Serializable serializable = new HashMap(this.mSensorListeners);
            Iterator<SensorEventListener> iterator = ((HashMap)serializable).keySet().iterator();
            while (iterator.hasNext()) {
                SensorEventListener sensorEventListener = iterator.next();
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("removed event listener");
                ((StringBuilder)serializable).append(sensorEventListener.toString());
                ((StringBuilder)serializable).append(" due to sensor disconnection");
                Log.i("SensorManager", ((StringBuilder)serializable).toString());
                this.unregisterListenerImpl(sensorEventListener, sensor);
            }
            return;
        }
    }

    private static boolean diffSortedSensorList(List<Sensor> list, List<Sensor> list2, List<Sensor> list3, List<Sensor> list4, List<Sensor> list5) {
        boolean bl = false;
        int n = 0;
        int n2 = 0;
        do {
            if (n2 < list.size() && (n >= list2.size() || list2.get(n).getHandle() > list.get(n2).getHandle())) {
                bl = true;
                if (list5 != null) {
                    list5.add(list.get(n2));
                }
                ++n2;
                continue;
            }
            if (n < list2.size() && (n2 >= list.size() || list2.get(n).getHandle() < list.get(n2).getHandle())) {
                bl = true;
                if (list4 != null) {
                    list4.add(list2.get(n));
                }
                if (list3 != null) {
                    list3.add(list2.get(n));
                }
                ++n;
                continue;
            }
            if (n >= list2.size() || n2 >= list.size() || list2.get(n).getHandle() != list.get(n2).getHandle()) break;
            if (list3 != null) {
                list3.add(list.get(n2));
            }
            ++n;
            ++n2;
        } while (true);
        return bl;
    }

    private static native void nativeClassInit();

    private static native int nativeConfigDirectChannel(long var0, int var2, int var3, int var4);

    private static native long nativeCreate(String var0);

    private static native int nativeCreateDirectChannel(long var0, long var2, int var4, int var5, HardwareBuffer var6);

    private static native void nativeDestroyDirectChannel(long var0, int var2);

    private static native void nativeGetDynamicSensors(long var0, List<Sensor> var2);

    private static native boolean nativeGetSensorAtIndex(long var0, Sensor var2, int var3);

    private static native boolean nativeIsDataInjectionEnabled(long var0);

    private static native int nativeSetOperationParameter(long var0, int var2, int var3, float[] var4, int[] var5);

    private void setupDynamicSensorBroadcastReceiver() {
        if (this.mDynamicSensorBroadcastReceiver == null) {
            this.mDynamicSensorBroadcastReceiver = new BroadcastReceiver(){

                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction() == "android.intent.action.DYNAMIC_SENSOR_CHANGED") {
                        Log.i("SensorManager", "DYNS received DYNAMIC_SENSOR_CHANED broadcast");
                        SystemSensorManager.this.mDynamicSensorListDirty = true;
                        SystemSensorManager.this.updateDynamicSensorList();
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter("dynamic_sensor_change");
            intentFilter.addAction("android.intent.action.DYNAMIC_SENSOR_CHANGED");
            this.mContext.registerReceiver(this.mDynamicSensorBroadcastReceiver, intentFilter);
        }
    }

    private void teardownDynamicSensorBroadcastReceiver() {
        this.mDynamicSensorCallbacks.clear();
        this.mContext.unregisterReceiver(this.mDynamicSensorBroadcastReceiver);
        this.mDynamicSensorBroadcastReceiver = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateDynamicSensorList() {
        List<Sensor> list = this.mFullDynamicSensorsList;
        synchronized (list) {
            if (this.mDynamicSensorListDirty) {
                Object object = new ArrayList();
                SystemSensorManager.nativeGetDynamicSensors(this.mNativeInstance, object);
                Object object22 = new ArrayList();
                final ArrayList<Sensor> arrayList = new ArrayList<Sensor>();
                final ArrayList<Sensor> arrayList2 = new ArrayList<Sensor>();
                if (SystemSensorManager.diffSortedSensorList(this.mFullDynamicSensorsList, object, object22, arrayList, arrayList2)) {
                    Log.i("SensorManager", "DYNS dynamic sensor list cached should be updated");
                    this.mFullDynamicSensorsList = object22;
                    for (Object object22 : arrayList) {
                        this.mHandleToSensor.put(((Sensor)object22).getHandle(), (Sensor)object22);
                    }
                    object = new Handler(this.mContext.getMainLooper());
                    for (Object object22 : this.mDynamicSensorCallbacks.entrySet()) {
                        final SensorManager.DynamicSensorCallback dynamicSensorCallback = (SensorManager.DynamicSensorCallback)object22.getKey();
                        object22 = object22.getValue() == null ? object : (Handler)object22.getValue();
                        Runnable runnable = new Runnable(){

                            @Override
                            public void run() {
                                for (Sensor sensor : arrayList) {
                                    dynamicSensorCallback.onDynamicSensorConnected(sensor);
                                }
                                for (Sensor sensor : arrayList2) {
                                    dynamicSensorCallback.onDynamicSensorDisconnected(sensor);
                                }
                            }
                        };
                        ((Handler)object22).post(runnable);
                    }
                    object22 = arrayList2.iterator();
                    while (object22.hasNext()) {
                        this.cleanupSensorConnection((Sensor)object22.next());
                    }
                }
                this.mDynamicSensorListDirty = false;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected boolean cancelTriggerSensorImpl(TriggerEventListener triggerEventListener, Sensor sensor, boolean bl) {
        if (sensor != null && sensor.getReportingMode() != 2) {
            return false;
        }
        HashMap<TriggerEventListener, TriggerEventQueue> hashMap = this.mTriggerListeners;
        synchronized (hashMap) {
            TriggerEventQueue triggerEventQueue = this.mTriggerListeners.get(triggerEventListener);
            if (triggerEventQueue == null) {
                return false;
            }
            bl = sensor == null ? triggerEventQueue.removeAllSensors() : triggerEventQueue.removeSensor(sensor, bl);
            if (bl && !triggerEventQueue.hasSensors()) {
                this.mTriggerListeners.remove(triggerEventListener);
                triggerEventQueue.dispose();
            }
            return bl;
        }
    }

    @Override
    protected int configureDirectChannelImpl(SensorDirectChannel sensorDirectChannel, Sensor sensor, int n) {
        if (sensorDirectChannel.isOpen()) {
            if (n >= 0 && n <= 3) {
                if (sensor == null && n != 0) {
                    throw new IllegalArgumentException("when sensor is null, rate can only be DIRECT_RATE_STOP");
                }
                int n2 = sensor == null ? -1 : sensor.getHandle();
                n2 = SystemSensorManager.nativeConfigDirectChannel(this.mNativeInstance, sensorDirectChannel.getNativeHandle(), n2, n);
                int n3 = 0;
                int n4 = 0;
                if (n == 0) {
                    n = n4;
                    if (n2 == 0) {
                        n = 1;
                    }
                    return n;
                }
                n = n3;
                if (n2 > 0) {
                    n = n2;
                }
                return n;
            }
            throw new IllegalArgumentException("rate parameter invalid");
        }
        throw new IllegalStateException("channel is closed");
    }

    @Override
    protected SensorDirectChannel createDirectChannelImpl(MemoryFile object, HardwareBuffer hardwareBuffer) {
        block8 : {
            block9 : {
                block10 : {
                    block11 : {
                        block12 : {
                            int n;
                            block13 : {
                                long l;
                                int n2;
                                block7 : {
                                    block5 : {
                                        block4 : {
                                            block6 : {
                                                if (object == null) break block5;
                                                try {
                                                    n2 = ((MemoryFile)object).getFileDescriptor().getInt$();
                                                    if (((MemoryFile)object).length() < 104) break block4;
                                                }
                                                catch (IOException iOException) {
                                                    throw new IllegalArgumentException("MemoryFile object is not valid");
                                                }
                                                l = ((MemoryFile)object).length();
                                                n = SystemSensorManager.nativeCreateDirectChannel(this.mNativeInstance, l, 1, n2, null);
                                                if (n <= 0) break block6;
                                                n2 = 1;
                                                break block7;
                                            }
                                            object = new StringBuilder();
                                            ((StringBuilder)object).append("create MemoryFile direct channel failed ");
                                            ((StringBuilder)object).append(n);
                                            throw new UncheckedIOException(new IOException(((StringBuilder)object).toString()));
                                        }
                                        throw new IllegalArgumentException("Size of MemoryFile has to be greater than 104");
                                    }
                                    if (hardwareBuffer == null) break block8;
                                    if (hardwareBuffer.getFormat() != 33) break block9;
                                    if (hardwareBuffer.getHeight() != 1) break block10;
                                    if (hardwareBuffer.getWidth() < 104) break block11;
                                    if ((hardwareBuffer.getUsage() & 0x800000L) == 0L) break block12;
                                    l = hardwareBuffer.getWidth();
                                    n = SystemSensorManager.nativeCreateDirectChannel(this.mNativeInstance, l, 2, -1, hardwareBuffer);
                                    if (n <= 0) break block13;
                                    n2 = 2;
                                }
                                return new SensorDirectChannel(this, n, n2, l);
                            }
                            object = new StringBuilder();
                            ((StringBuilder)object).append("create HardwareBuffer direct channel failed ");
                            ((StringBuilder)object).append(n);
                            throw new UncheckedIOException(new IOException(((StringBuilder)object).toString()));
                        }
                        throw new IllegalArgumentException("HardwareBuffer must set usage flag USAGE_SENSOR_DIRECT_DATA");
                    }
                    throw new IllegalArgumentException("Width if HaradwareBuffer must be greater than 104");
                }
                throw new IllegalArgumentException("Height of HardwareBuffer must be 1");
            }
            throw new IllegalArgumentException("Format of HardwareBuffer must be BLOB");
        }
        throw new NullPointerException("shared memory object cannot be null");
    }

    @Override
    protected void destroyDirectChannelImpl(SensorDirectChannel sensorDirectChannel) {
        if (sensorDirectChannel != null) {
            SystemSensorManager.nativeDestroyDirectChannel(this.mNativeInstance, sensorDirectChannel.getNativeHandle());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected boolean flushImpl(SensorEventListener object) {
        if (object == null) {
            throw new IllegalArgumentException("listener cannot be null");
        }
        HashMap<SensorEventListener, SensorEventQueue> hashMap = this.mSensorListeners;
        synchronized (hashMap) {
            object = this.mSensorListeners.get(object);
            boolean bl = false;
            if (object == null) {
                return false;
            }
            if (((BaseEventQueue)object).flush() != 0) return bl;
            return true;
        }
    }

    @Override
    protected List<Sensor> getFullDynamicSensorList() {
        this.setupDynamicSensorBroadcastReceiver();
        this.updateDynamicSensorList();
        return this.mFullDynamicSensorsList;
    }

    @Override
    protected List<Sensor> getFullSensorList() {
        return this.mFullSensorsList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected boolean initDataInjectionImpl(boolean bl) {
        Object object = sLock;
        synchronized (object) {
            boolean bl2 = true;
            if (bl) {
                if (!SystemSensorManager.nativeIsDataInjectionEnabled(this.mNativeInstance)) {
                    Log.e("SensorManager", "Data Injection mode not enabled");
                    return false;
                }
                Object object2 = sInjectEventQueue;
                if (object2 == null) {
                    try {
                        sInjectEventQueue = object2 = new InjectEventQueue(this.mMainLooper, this, this.mContext.getPackageName());
                    }
                    catch (RuntimeException runtimeException) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Cannot create InjectEventQueue: ");
                        ((StringBuilder)object2).append(runtimeException);
                        Log.e("SensorManager", ((StringBuilder)object2).toString());
                    }
                }
                if (sInjectEventQueue == null) return false;
                return bl2;
            }
            if (sInjectEventQueue == null) return true;
            sInjectEventQueue.dispose();
            sInjectEventQueue = null;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected boolean injectSensorDataImpl(Sensor sensor, float[] arrf, int n, long l) {
        Object object = sLock;
        synchronized (object) {
            InjectEventQueue injectEventQueue = sInjectEventQueue;
            boolean bl = false;
            if (injectEventQueue == null) {
                Log.e("SensorManager", "Data injection mode not activated before calling injectSensorData");
                return false;
            }
            n = sInjectEventQueue.injectSensorData(sensor.getHandle(), arrf, n, l);
            if (n != 0) {
                sInjectEventQueue.dispose();
                sInjectEventQueue = null;
            }
            if (n != 0) return bl;
            return true;
        }
    }

    @Override
    protected void registerDynamicSensorCallbackImpl(SensorManager.DynamicSensorCallback dynamicSensorCallback, Handler handler) {
        Log.i("SensorManager", "DYNS Register dynamic sensor callback");
        if (dynamicSensorCallback != null) {
            if (this.mDynamicSensorCallbacks.containsKey(dynamicSensorCallback)) {
                return;
            }
            this.setupDynamicSensorBroadcastReceiver();
            this.mDynamicSensorCallbacks.put(dynamicSensorCallback, handler);
            return;
        }
        throw new IllegalArgumentException("callback cannot be null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected boolean registerListenerImpl(SensorEventListener sensorEventListener, Sensor sensor, int n, Handler object, int n2, int n3) {
        if (sensorEventListener != null && sensor != null) {
            if (sensor.getReportingMode() == 2) {
                Log.e("SensorManager", "Trigger Sensors should use the requestTriggerSensor.");
                return false;
            }
            if (n2 >= 0 && n >= 0) {
                if (this.mSensorListeners.size() >= 128) {
                    throw new IllegalStateException("register failed, the sensor listeners size has exceeded the maximum limit 128");
                }
                HashMap<SensorEventListener, SensorEventQueue> hashMap = this.mSensorListeners;
                synchronized (hashMap) {
                    Object object2 = this.mSensorListeners.get(sensorEventListener);
                    if (object2 != null) {
                        return ((BaseEventQueue)object2).addSensor(sensor, n, n2);
                    }
                    SensorEventQueue sensorEventQueue = new SensorEventQueue(sensorEventListener, (Looper)(object = object != null ? ((Handler)object).getLooper() : this.mMainLooper), this, (String)(object2 = sensorEventListener.getClass().getEnclosingClass() != null ? sensorEventListener.getClass().getEnclosingClass().getName() : sensorEventListener.getClass().getName()));
                    if (!sensorEventQueue.addSensor(sensor, n, n2)) {
                        sensorEventQueue.dispose();
                        return false;
                    }
                    this.mSensorListeners.put(sensorEventListener, sensorEventQueue);
                    return true;
                }
            }
            Log.e("SensorManager", "maxBatchReportLatencyUs and delayUs should be non-negative");
            return false;
        }
        Log.e("SensorManager", "sensor or listener is null");
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected boolean requestTriggerSensorImpl(TriggerEventListener triggerEventListener, Sensor sensor) {
        if (sensor == null) {
            throw new IllegalArgumentException("sensor cannot be null");
        }
        if (triggerEventListener == null) {
            throw new IllegalArgumentException("listener cannot be null");
        }
        if (sensor.getReportingMode() != 2) {
            return false;
        }
        if (this.mTriggerListeners.size() >= 128) {
            throw new IllegalStateException("request failed, the trigger listeners size has exceeded the maximum limit 128");
        }
        HashMap<TriggerEventListener, TriggerEventQueue> hashMap = this.mTriggerListeners;
        synchronized (hashMap) {
            Object object = this.mTriggerListeners.get(triggerEventListener);
            if (object != null) {
                return ((BaseEventQueue)object).addSensor(sensor, 0, 0);
            }
            object = triggerEventListener.getClass().getEnclosingClass() != null ? triggerEventListener.getClass().getEnclosingClass().getName() : triggerEventListener.getClass().getName();
            TriggerEventQueue triggerEventQueue = new TriggerEventQueue(triggerEventListener, this.mMainLooper, this, (String)object);
            if (!triggerEventQueue.addSensor(sensor, 0, 0)) {
                triggerEventQueue.dispose();
                return false;
            }
            this.mTriggerListeners.put(triggerEventListener, triggerEventQueue);
            return true;
        }
    }

    @Override
    protected boolean setOperationParameterImpl(SensorAdditionalInfo sensorAdditionalInfo) {
        int n = -1;
        if (sensorAdditionalInfo.sensor != null) {
            n = sensorAdditionalInfo.sensor.getHandle();
        }
        boolean bl = SystemSensorManager.nativeSetOperationParameter(this.mNativeInstance, n, sensorAdditionalInfo.type, sensorAdditionalInfo.floatValues, sensorAdditionalInfo.intValues) == 0;
        return bl;
    }

    @Override
    protected void unregisterDynamicSensorCallbackImpl(SensorManager.DynamicSensorCallback dynamicSensorCallback) {
        Log.i("SensorManager", "Removing dynamic sensor listerner");
        this.mDynamicSensorCallbacks.remove(dynamicSensorCallback);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void unregisterListenerImpl(SensorEventListener sensorEventListener, Sensor sensor) {
        if (sensor != null && sensor.getReportingMode() == 2) {
            return;
        }
        HashMap<SensorEventListener, SensorEventQueue> hashMap = this.mSensorListeners;
        synchronized (hashMap) {
            boolean bl;
            SensorEventQueue sensorEventQueue = this.mSensorListeners.get(sensorEventListener);
            if (sensorEventQueue != null && (bl = sensor == null ? sensorEventQueue.removeAllSensors() : sensorEventQueue.removeSensor(sensor, true)) && !sensorEventQueue.hasSensors()) {
                this.mSensorListeners.remove(sensorEventListener);
                sensorEventQueue.dispose();
            }
            return;
        }
    }

    private static abstract class BaseEventQueue {
        protected static final int OPERATING_MODE_DATA_INJECTION = 1;
        protected static final int OPERATING_MODE_NORMAL = 0;
        private final SparseBooleanArray mActiveSensors = new SparseBooleanArray();
        private final CloseGuard mCloseGuard = CloseGuard.get();
        protected final SystemSensorManager mManager;
        private long mNativeSensorEventQueue;
        protected final SparseIntArray mSensorAccuracies = new SparseIntArray();

        BaseEventQueue(Looper looper, SystemSensorManager systemSensorManager, int n, String string2) {
            String string3 = string2;
            if (string2 == null) {
                string3 = "";
            }
            this.mNativeSensorEventQueue = BaseEventQueue.nativeInitBaseEventQueue(systemSensorManager.mNativeInstance, new WeakReference<BaseEventQueue>(this), looper.getQueue(), string3, n, systemSensorManager.mContext.getOpPackageName());
            this.mCloseGuard.open("dispose");
            this.mManager = systemSensorManager;
        }

        private int disableSensor(Sensor sensor) {
            long l = this.mNativeSensorEventQueue;
            if (l != 0L) {
                if (sensor != null) {
                    return BaseEventQueue.nativeDisableSensor(l, sensor.getHandle());
                }
                throw new NullPointerException();
            }
            throw new NullPointerException();
        }

        private void dispose(boolean bl) {
            long l;
            CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                if (bl) {
                    closeGuard.warnIfOpen();
                }
                this.mCloseGuard.close();
            }
            if ((l = this.mNativeSensorEventQueue) != 0L) {
                BaseEventQueue.nativeDestroySensorEventQueue(l);
                this.mNativeSensorEventQueue = 0L;
            }
        }

        private int enableSensor(Sensor sensor, int n, int n2) {
            long l = this.mNativeSensorEventQueue;
            if (l != 0L) {
                if (sensor != null) {
                    return BaseEventQueue.nativeEnableSensor(l, sensor.getHandle(), n, n2);
                }
                throw new NullPointerException();
            }
            throw new NullPointerException();
        }

        private static native void nativeDestroySensorEventQueue(long var0);

        private static native int nativeDisableSensor(long var0, int var2);

        private static native int nativeEnableSensor(long var0, int var2, int var3, int var4);

        private static native int nativeFlushSensor(long var0);

        private static native long nativeInitBaseEventQueue(long var0, WeakReference<BaseEventQueue> var2, MessageQueue var3, String var4, int var5, String var6);

        private static native int nativeInjectSensorData(long var0, int var2, float[] var3, int var4, long var5);

        public boolean addSensor(Sensor sensor, int n, int n2) {
            int n3 = sensor.getHandle();
            if (this.mActiveSensors.get(n3)) {
                return false;
            }
            this.mActiveSensors.put(n3, true);
            this.addSensorEvent(sensor);
            if (this.enableSensor(sensor, n, n2) != 0 && (n2 == 0 || n2 > 0 && this.enableSensor(sensor, n, 0) != 0)) {
                this.removeSensor(sensor, false);
                return false;
            }
            return true;
        }

        protected abstract void addSensorEvent(Sensor var1);

        @UnsupportedAppUsage
        protected void dispatchAdditionalInfoEvent(int n, int n2, int n3, float[] arrf, int[] arrn) {
        }

        @UnsupportedAppUsage
        protected abstract void dispatchFlushCompleteEvent(int var1);

        @UnsupportedAppUsage
        protected abstract void dispatchSensorEvent(int var1, float[] var2, int var3, long var4);

        public void dispose() {
            this.dispose(false);
        }

        protected void finalize() throws Throwable {
            try {
                this.dispose(true);
                return;
            }
            finally {
                super.finalize();
            }
        }

        public int flush() {
            long l = this.mNativeSensorEventQueue;
            if (l != 0L) {
                return BaseEventQueue.nativeFlushSensor(l);
            }
            throw new NullPointerException();
        }

        public boolean hasSensors() {
            SparseBooleanArray sparseBooleanArray = this.mActiveSensors;
            boolean bl = true;
            if (sparseBooleanArray.indexOfValue(true) < 0) {
                bl = false;
            }
            return bl;
        }

        protected int injectSensorDataBase(int n, float[] arrf, int n2, long l) {
            return BaseEventQueue.nativeInjectSensorData(this.mNativeSensorEventQueue, n, arrf, n2, l);
        }

        public boolean removeAllSensors() {
            for (int i = 0; i < this.mActiveSensors.size(); ++i) {
                if (!this.mActiveSensors.valueAt(i)) continue;
                int n = this.mActiveSensors.keyAt(i);
                Sensor sensor = (Sensor)this.mManager.mHandleToSensor.get(n);
                if (sensor == null) continue;
                this.disableSensor(sensor);
                this.mActiveSensors.put(n, false);
                this.removeSensorEvent(sensor);
            }
            return true;
        }

        public boolean removeSensor(Sensor sensor, boolean bl) {
            int n = sensor.getHandle();
            if (this.mActiveSensors.get(n)) {
                if (bl) {
                    this.disableSensor(sensor);
                }
                this.mActiveSensors.put(sensor.getHandle(), false);
                this.removeSensorEvent(sensor);
                return true;
            }
            return false;
        }

        protected abstract void removeSensorEvent(Sensor var1);
    }

    final class InjectEventQueue
    extends BaseEventQueue {
        public InjectEventQueue(Looper looper, SystemSensorManager systemSensorManager2, String string2) {
            super(looper, systemSensorManager2, 1, string2);
        }

        @Override
        protected void addSensorEvent(Sensor sensor) {
        }

        @Override
        protected void dispatchFlushCompleteEvent(int n) {
        }

        @Override
        protected void dispatchSensorEvent(int n, float[] arrf, int n2, long l) {
        }

        int injectSensorData(int n, float[] arrf, int n2, long l) {
            return this.injectSensorDataBase(n, arrf, n2, l);
        }

        @Override
        protected void removeSensorEvent(Sensor sensor) {
        }
    }

    static final class SensorEventQueue
    extends BaseEventQueue {
        private final SensorEventListener mListener;
        private final SparseArray<SensorEvent> mSensorsEvents = new SparseArray();

        public SensorEventQueue(SensorEventListener sensorEventListener, Looper looper, SystemSensorManager systemSensorManager, String string2) {
            super(looper, systemSensorManager, 0, string2);
            this.mListener = sensorEventListener;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void addSensorEvent(Sensor sensor) {
            SensorEvent sensorEvent = new SensorEvent(Sensor.getMaxLengthValuesArray(sensor, this.mManager.mTargetSdkLevel));
            SparseArray<SensorEvent> sparseArray = this.mSensorsEvents;
            synchronized (sparseArray) {
                this.mSensorsEvents.put(sensor.getHandle(), sensorEvent);
                return;
            }
        }

        @Override
        protected void dispatchAdditionalInfoEvent(int n, int n2, int n3, float[] object, int[] arrn) {
            if (this.mListener instanceof SensorEventCallback) {
                Sensor sensor = (Sensor)this.mManager.mHandleToSensor.get(n);
                if (sensor == null) {
                    return;
                }
                object = new SensorAdditionalInfo(sensor, n2, n3, arrn, (float[])object);
                ((SensorEventCallback)this.mListener).onSensorAdditionalInfo((SensorAdditionalInfo)object);
            }
        }

        @Override
        protected void dispatchFlushCompleteEvent(int n) {
            if (this.mListener instanceof SensorEventListener2) {
                Sensor sensor = (Sensor)this.mManager.mHandleToSensor.get(n);
                if (sensor == null) {
                    return;
                }
                ((SensorEventListener2)this.mListener).onFlushCompleted(sensor);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        protected void dispatchSensorEvent(int n, float[] arrf, int n2, long l) {
            Sensor sensor = (Sensor)this.mManager.mHandleToSensor.get(n);
            if (sensor == null) {
                return;
            }
            SparseArray<SensorEvent> sparseArray = this.mSensorsEvents;
            // MONITORENTER : sparseArray
            SensorEvent sensorEvent = this.mSensorsEvents.get(n);
            // MONITOREXIT : sparseArray
            if (sensorEvent == null) {
                return;
            }
            System.arraycopy(arrf, 0, sensorEvent.values, 0, sensorEvent.values.length);
            sensorEvent.timestamp = l;
            sensorEvent.accuracy = n2;
            sensorEvent.sensor = sensor;
            n2 = this.mSensorAccuracies.get(n);
            if (sensorEvent.accuracy >= 0 && n2 != sensorEvent.accuracy) {
                this.mSensorAccuracies.put(n, sensorEvent.accuracy);
                this.mListener.onAccuracyChanged(sensorEvent.sensor, sensorEvent.accuracy);
            }
            this.mListener.onSensorChanged(sensorEvent);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void removeSensorEvent(Sensor sensor) {
            SparseArray<SensorEvent> sparseArray = this.mSensorsEvents;
            synchronized (sparseArray) {
                this.mSensorsEvents.delete(sensor.getHandle());
                return;
            }
        }
    }

    static final class TriggerEventQueue
    extends BaseEventQueue {
        private final TriggerEventListener mListener;
        private final SparseArray<TriggerEvent> mTriggerEvents = new SparseArray();

        public TriggerEventQueue(TriggerEventListener triggerEventListener, Looper looper, SystemSensorManager systemSensorManager, String string2) {
            super(looper, systemSensorManager, 0, string2);
            this.mListener = triggerEventListener;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void addSensorEvent(Sensor sensor) {
            TriggerEvent triggerEvent = new TriggerEvent(Sensor.getMaxLengthValuesArray(sensor, this.mManager.mTargetSdkLevel));
            SparseArray<TriggerEvent> sparseArray = this.mTriggerEvents;
            synchronized (sparseArray) {
                this.mTriggerEvents.put(sensor.getHandle(), triggerEvent);
                return;
            }
        }

        @Override
        protected void dispatchFlushCompleteEvent(int n) {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        protected void dispatchSensorEvent(int n, float[] object, int n2, long l) {
            Sensor sensor = (Sensor)this.mManager.mHandleToSensor.get(n);
            if (sensor == null) {
                return;
            }
            SparseArray<TriggerEvent> sparseArray = this.mTriggerEvents;
            // MONITORENTER : sparseArray
            TriggerEvent triggerEvent = this.mTriggerEvents.get(n);
            // MONITOREXIT : sparseArray
            if (triggerEvent == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Error: Trigger Event is null for Sensor: ");
                ((StringBuilder)object).append(sensor);
                Log.e("SensorManager", ((StringBuilder)object).toString());
                return;
            }
            System.arraycopy(object, 0, triggerEvent.values, 0, triggerEvent.values.length);
            triggerEvent.timestamp = l;
            triggerEvent.sensor = sensor;
            this.mManager.cancelTriggerSensorImpl(this.mListener, sensor, false);
            this.mListener.onTrigger(triggerEvent);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void removeSensorEvent(Sensor sensor) {
            SparseArray<TriggerEvent> sparseArray = this.mTriggerEvents;
            synchronized (sparseArray) {
                this.mTriggerEvents.delete(sensor.getHandle());
                return;
            }
        }
    }

}

