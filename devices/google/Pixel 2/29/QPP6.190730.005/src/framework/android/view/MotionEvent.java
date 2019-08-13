/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.CriticalNative
 *  dalvik.annotation.optimization.FastNative
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Matrix;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.view.InputEvent;
import android.view.KeyEvent;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class MotionEvent
extends InputEvent
implements Parcelable {
    public static final int ACTION_BUTTON_PRESS = 11;
    public static final int ACTION_BUTTON_RELEASE = 12;
    public static final int ACTION_CANCEL = 3;
    public static final int ACTION_DOWN = 0;
    public static final int ACTION_HOVER_ENTER = 9;
    public static final int ACTION_HOVER_EXIT = 10;
    public static final int ACTION_HOVER_MOVE = 7;
    public static final int ACTION_MASK = 255;
    public static final int ACTION_MOVE = 2;
    public static final int ACTION_OUTSIDE = 4;
    @Deprecated
    public static final int ACTION_POINTER_1_DOWN = 5;
    @Deprecated
    public static final int ACTION_POINTER_1_UP = 6;
    @Deprecated
    public static final int ACTION_POINTER_2_DOWN = 261;
    @Deprecated
    public static final int ACTION_POINTER_2_UP = 262;
    @Deprecated
    public static final int ACTION_POINTER_3_DOWN = 517;
    @Deprecated
    public static final int ACTION_POINTER_3_UP = 518;
    public static final int ACTION_POINTER_DOWN = 5;
    @Deprecated
    public static final int ACTION_POINTER_ID_MASK = 65280;
    @Deprecated
    public static final int ACTION_POINTER_ID_SHIFT = 8;
    public static final int ACTION_POINTER_INDEX_MASK = 65280;
    public static final int ACTION_POINTER_INDEX_SHIFT = 8;
    public static final int ACTION_POINTER_UP = 6;
    public static final int ACTION_SCROLL = 8;
    public static final int ACTION_UP = 1;
    public static final int AXIS_BRAKE = 23;
    public static final int AXIS_DISTANCE = 24;
    public static final int AXIS_GAS = 22;
    public static final int AXIS_GENERIC_1 = 32;
    public static final int AXIS_GENERIC_10 = 41;
    public static final int AXIS_GENERIC_11 = 42;
    public static final int AXIS_GENERIC_12 = 43;
    public static final int AXIS_GENERIC_13 = 44;
    public static final int AXIS_GENERIC_14 = 45;
    public static final int AXIS_GENERIC_15 = 46;
    public static final int AXIS_GENERIC_16 = 47;
    public static final int AXIS_GENERIC_2 = 33;
    public static final int AXIS_GENERIC_3 = 34;
    public static final int AXIS_GENERIC_4 = 35;
    public static final int AXIS_GENERIC_5 = 36;
    public static final int AXIS_GENERIC_6 = 37;
    public static final int AXIS_GENERIC_7 = 38;
    public static final int AXIS_GENERIC_8 = 39;
    public static final int AXIS_GENERIC_9 = 40;
    public static final int AXIS_HAT_X = 15;
    public static final int AXIS_HAT_Y = 16;
    public static final int AXIS_HSCROLL = 10;
    public static final int AXIS_LTRIGGER = 17;
    public static final int AXIS_ORIENTATION = 8;
    public static final int AXIS_PRESSURE = 2;
    public static final int AXIS_RELATIVE_X = 27;
    public static final int AXIS_RELATIVE_Y = 28;
    public static final int AXIS_RTRIGGER = 18;
    public static final int AXIS_RUDDER = 20;
    public static final int AXIS_RX = 12;
    public static final int AXIS_RY = 13;
    public static final int AXIS_RZ = 14;
    public static final int AXIS_SCROLL = 26;
    public static final int AXIS_SIZE = 3;
    private static final SparseArray<String> AXIS_SYMBOLIC_NAMES;
    public static final int AXIS_THROTTLE = 19;
    public static final int AXIS_TILT = 25;
    public static final int AXIS_TOOL_MAJOR = 6;
    public static final int AXIS_TOOL_MINOR = 7;
    public static final int AXIS_TOUCH_MAJOR = 4;
    public static final int AXIS_TOUCH_MINOR = 5;
    public static final int AXIS_VSCROLL = 9;
    public static final int AXIS_WHEEL = 21;
    public static final int AXIS_X = 0;
    public static final int AXIS_Y = 1;
    public static final int AXIS_Z = 11;
    public static final int BUTTON_BACK = 8;
    public static final int BUTTON_FORWARD = 16;
    public static final int BUTTON_PRIMARY = 1;
    public static final int BUTTON_SECONDARY = 2;
    public static final int BUTTON_STYLUS_PRIMARY = 32;
    public static final int BUTTON_STYLUS_SECONDARY = 64;
    private static final String[] BUTTON_SYMBOLIC_NAMES;
    public static final int BUTTON_TERTIARY = 4;
    public static final int CLASSIFICATION_AMBIGUOUS_GESTURE = 1;
    public static final int CLASSIFICATION_DEEP_PRESS = 2;
    public static final int CLASSIFICATION_NONE = 0;
    public static final Parcelable.Creator<MotionEvent> CREATOR;
    private static final boolean DEBUG_CONCISE_TOSTRING = false;
    public static final int EDGE_BOTTOM = 2;
    public static final int EDGE_LEFT = 4;
    public static final int EDGE_RIGHT = 8;
    public static final int EDGE_TOP = 1;
    public static final int FLAG_HOVER_EXIT_PENDING = 4;
    public static final int FLAG_IS_GENERATED_GESTURE = 8;
    public static final int FLAG_TAINTED = Integer.MIN_VALUE;
    public static final int FLAG_TARGET_ACCESSIBILITY_FOCUS = 1073741824;
    public static final int FLAG_WINDOW_IS_OBSCURED = 1;
    public static final int FLAG_WINDOW_IS_PARTIALLY_OBSCURED = 2;
    @UnsupportedAppUsage
    private static final int HISTORY_CURRENT = Integer.MIN_VALUE;
    public static final int INVALID_POINTER_ID = -1;
    private static final String LABEL_PREFIX = "AXIS_";
    private static final int MAX_RECYCLED = 10;
    private static final long NS_PER_MS = 1000000L;
    private static final String TAG = "MotionEvent";
    public static final int TOOL_TYPE_ERASER = 4;
    public static final int TOOL_TYPE_FINGER = 1;
    public static final int TOOL_TYPE_MOUSE = 3;
    public static final int TOOL_TYPE_STYLUS = 2;
    private static final SparseArray<String> TOOL_TYPE_SYMBOLIC_NAMES;
    public static final int TOOL_TYPE_UNKNOWN = 0;
    private static final Object gRecyclerLock;
    private static MotionEvent gRecyclerTop;
    private static int gRecyclerUsed;
    private static final Object gSharedTempLock;
    private static PointerCoords[] gSharedTempPointerCoords;
    private static int[] gSharedTempPointerIndexMap;
    private static PointerProperties[] gSharedTempPointerProperties;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private long mNativePtr;
    private MotionEvent mNext;

    static {
        SparseArray<String> sparseArray = AXIS_SYMBOLIC_NAMES = new SparseArray();
        sparseArray.append(0, "AXIS_X");
        sparseArray.append(1, "AXIS_Y");
        sparseArray.append(2, "AXIS_PRESSURE");
        sparseArray.append(3, "AXIS_SIZE");
        sparseArray.append(4, "AXIS_TOUCH_MAJOR");
        sparseArray.append(5, "AXIS_TOUCH_MINOR");
        sparseArray.append(6, "AXIS_TOOL_MAJOR");
        sparseArray.append(7, "AXIS_TOOL_MINOR");
        sparseArray.append(8, "AXIS_ORIENTATION");
        sparseArray.append(9, "AXIS_VSCROLL");
        sparseArray.append(10, "AXIS_HSCROLL");
        sparseArray.append(11, "AXIS_Z");
        sparseArray.append(12, "AXIS_RX");
        sparseArray.append(13, "AXIS_RY");
        sparseArray.append(14, "AXIS_RZ");
        sparseArray.append(15, "AXIS_HAT_X");
        sparseArray.append(16, "AXIS_HAT_Y");
        sparseArray.append(17, "AXIS_LTRIGGER");
        sparseArray.append(18, "AXIS_RTRIGGER");
        sparseArray.append(19, "AXIS_THROTTLE");
        sparseArray.append(20, "AXIS_RUDDER");
        sparseArray.append(21, "AXIS_WHEEL");
        sparseArray.append(22, "AXIS_GAS");
        sparseArray.append(23, "AXIS_BRAKE");
        sparseArray.append(24, "AXIS_DISTANCE");
        sparseArray.append(25, "AXIS_TILT");
        sparseArray.append(26, "AXIS_SCROLL");
        sparseArray.append(27, "AXIS_REALTIVE_X");
        sparseArray.append(28, "AXIS_REALTIVE_Y");
        sparseArray.append(32, "AXIS_GENERIC_1");
        sparseArray.append(33, "AXIS_GENERIC_2");
        sparseArray.append(34, "AXIS_GENERIC_3");
        sparseArray.append(35, "AXIS_GENERIC_4");
        sparseArray.append(36, "AXIS_GENERIC_5");
        sparseArray.append(37, "AXIS_GENERIC_6");
        sparseArray.append(38, "AXIS_GENERIC_7");
        sparseArray.append(39, "AXIS_GENERIC_8");
        sparseArray.append(40, "AXIS_GENERIC_9");
        sparseArray.append(41, "AXIS_GENERIC_10");
        sparseArray.append(42, "AXIS_GENERIC_11");
        sparseArray.append(43, "AXIS_GENERIC_12");
        sparseArray.append(44, "AXIS_GENERIC_13");
        sparseArray.append(45, "AXIS_GENERIC_14");
        sparseArray.append(46, "AXIS_GENERIC_15");
        sparseArray.append(47, "AXIS_GENERIC_16");
        BUTTON_SYMBOLIC_NAMES = new String[]{"BUTTON_PRIMARY", "BUTTON_SECONDARY", "BUTTON_TERTIARY", "BUTTON_BACK", "BUTTON_FORWARD", "BUTTON_STYLUS_PRIMARY", "BUTTON_STYLUS_SECONDARY", "0x00000080", "0x00000100", "0x00000200", "0x00000400", "0x00000800", "0x00001000", "0x00002000", "0x00004000", "0x00008000", "0x00010000", "0x00020000", "0x00040000", "0x00080000", "0x00100000", "0x00200000", "0x00400000", "0x00800000", "0x01000000", "0x02000000", "0x04000000", "0x08000000", "0x10000000", "0x20000000", "0x40000000", "0x80000000"};
        TOOL_TYPE_SYMBOLIC_NAMES = new SparseArray();
        sparseArray = TOOL_TYPE_SYMBOLIC_NAMES;
        sparseArray.append(0, "TOOL_TYPE_UNKNOWN");
        sparseArray.append(1, "TOOL_TYPE_FINGER");
        sparseArray.append(2, "TOOL_TYPE_STYLUS");
        sparseArray.append(3, "TOOL_TYPE_MOUSE");
        sparseArray.append(4, "TOOL_TYPE_ERASER");
        gRecyclerLock = new Object();
        gSharedTempLock = new Object();
        CREATOR = new Parcelable.Creator<MotionEvent>(){

            @Override
            public MotionEvent createFromParcel(Parcel parcel) {
                parcel.readInt();
                return MotionEvent.createFromParcelBody(parcel);
            }

            public MotionEvent[] newArray(int n) {
                return new MotionEvent[n];
            }
        };
    }

    private MotionEvent() {
    }

    public static String actionToString(int n) {
        int n2;
        int n3;
        switch (n) {
            default: {
                n3 = (65280 & n) >> 8;
                n2 = n & 255;
                break;
            }
            case 12: {
                return "ACTION_BUTTON_RELEASE";
            }
            case 11: {
                return "ACTION_BUTTON_PRESS";
            }
            case 10: {
                return "ACTION_HOVER_EXIT";
            }
            case 9: {
                return "ACTION_HOVER_ENTER";
            }
            case 8: {
                return "ACTION_SCROLL";
            }
            case 7: {
                return "ACTION_HOVER_MOVE";
            }
            case 4: {
                return "ACTION_OUTSIDE";
            }
            case 3: {
                return "ACTION_CANCEL";
            }
            case 2: {
                return "ACTION_MOVE";
            }
            case 1: {
                return "ACTION_UP";
            }
            case 0: {
                return "ACTION_DOWN";
            }
        }
        if (n2 != 5) {
            if (n2 != 6) {
                return Integer.toString(n);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ACTION_POINTER_UP(");
            stringBuilder.append(n3);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ACTION_POINTER_DOWN(");
        stringBuilder.append(n3);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private static <T> void appendUnless(T t, StringBuilder stringBuilder, String string2, T t2) {
        stringBuilder.append(string2);
        stringBuilder.append(t2);
    }

    public static int axisFromString(String string2) {
        int n;
        String string3 = string2;
        if (string2.startsWith(LABEL_PREFIX) && (n = MotionEvent.nativeAxisFromString(string3 = string2.substring(LABEL_PREFIX.length()))) >= 0) {
            return n;
        }
        try {
            n = Integer.parseInt(string3, 10);
            return n;
        }
        catch (NumberFormatException numberFormatException) {
            return -1;
        }
    }

    public static String axisToString(int n) {
        CharSequence charSequence;
        String string2 = MotionEvent.nativeAxisToString(n);
        if (string2 != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(LABEL_PREFIX);
            ((StringBuilder)charSequence).append(string2);
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = Integer.toString(n);
        }
        return charSequence;
    }

    public static String buttonStateToString(int n) {
        if (n == 0) {
            return "0";
        }
        StringBuilder stringBuilder = null;
        int n2 = 0;
        int n3 = n;
        n = n2;
        while (n3 != 0) {
            n2 = (n3 & 1) != 0 ? 1 : 0;
            n3 >>>= 1;
            CharSequence charSequence = stringBuilder;
            if (n2 != 0) {
                charSequence = BUTTON_SYMBOLIC_NAMES[n];
                if (stringBuilder == null) {
                    if (n3 == 0) {
                        return charSequence;
                    }
                    charSequence = new StringBuilder((String)charSequence);
                } else {
                    stringBuilder.append('|');
                    stringBuilder.append((String)charSequence);
                    charSequence = stringBuilder;
                }
            }
            ++n;
            stringBuilder = charSequence;
        }
        return stringBuilder.toString();
    }

    private static final float clamp(float f, float f2, float f3) {
        if (f < f2) {
            return f2;
        }
        if (f > f3) {
            return f3;
        }
        return f;
    }

    public static String classificationToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return "NONE";
                }
                return "DEEP_PRESS";
            }
            return "AMBIGUOUS_GESTURE";
        }
        return "NONE";
    }

    public static MotionEvent createFromParcelBody(Parcel parcel) {
        MotionEvent motionEvent = MotionEvent.obtain();
        motionEvent.mNativePtr = MotionEvent.nativeReadFromParcel(motionEvent.mNativePtr, parcel);
        return motionEvent;
    }

    private static final void ensureSharedTempPointerCapacity(int n) {
        PointerCoords[] arrpointerCoords = gSharedTempPointerCoords;
        if (arrpointerCoords == null || arrpointerCoords.length < n) {
            int n2;
            for (n2 = (arrpointerCoords = MotionEvent.gSharedTempPointerCoords) != null ? arrpointerCoords.length : 8; n2 < n; n2 *= 2) {
            }
            gSharedTempPointerCoords = PointerCoords.createArray(n2);
            gSharedTempPointerProperties = PointerProperties.createArray(n2);
            gSharedTempPointerIndexMap = new int[n2];
        }
    }

    private static native void nativeAddBatch(long var0, long var2, PointerCoords[] var4, int var5);

    private static native int nativeAxisFromString(String var0);

    private static native String nativeAxisToString(int var0);

    @CriticalNative
    private static native long nativeCopy(long var0, long var2, boolean var4);

    private static native void nativeDispose(long var0);

    @CriticalNative
    private static native int nativeFindPointerIndex(long var0, int var2);

    @CriticalNative
    private static native int nativeGetAction(long var0);

    @CriticalNative
    private static native int nativeGetActionButton(long var0);

    @FastNative
    private static native float nativeGetAxisValue(long var0, int var2, int var3, int var4);

    @CriticalNative
    private static native int nativeGetButtonState(long var0);

    @CriticalNative
    private static native int nativeGetClassification(long var0);

    @CriticalNative
    private static native int nativeGetDeviceId(long var0);

    @CriticalNative
    private static native int nativeGetDisplayId(long var0);

    @CriticalNative
    private static native long nativeGetDownTimeNanos(long var0);

    @CriticalNative
    private static native int nativeGetEdgeFlags(long var0);

    @FastNative
    private static native long nativeGetEventTimeNanos(long var0, int var2);

    @CriticalNative
    private static native int nativeGetFlags(long var0);

    @CriticalNative
    private static native int nativeGetHistorySize(long var0);

    @CriticalNative
    private static native int nativeGetMetaState(long var0);

    private static native void nativeGetPointerCoords(long var0, int var2, int var3, PointerCoords var4);

    @CriticalNative
    private static native int nativeGetPointerCount(long var0);

    @FastNative
    private static native int nativeGetPointerId(long var0, int var2);

    private static native void nativeGetPointerProperties(long var0, int var2, PointerProperties var3);

    @UnsupportedAppUsage
    @FastNative
    private static native float nativeGetRawAxisValue(long var0, int var2, int var3, int var4);

    @CriticalNative
    private static native int nativeGetSource(long var0);

    @FastNative
    private static native int nativeGetToolType(long var0, int var2);

    @CriticalNative
    private static native float nativeGetXOffset(long var0);

    @CriticalNative
    private static native float nativeGetXPrecision(long var0);

    @CriticalNative
    private static native float nativeGetYOffset(long var0);

    @CriticalNative
    private static native float nativeGetYPrecision(long var0);

    private static native long nativeInitialize(long var0, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, float var11, float var12, float var13, float var14, long var15, long var17, int var19, PointerProperties[] var20, PointerCoords[] var21);

    @CriticalNative
    private static native boolean nativeIsTouchEvent(long var0);

    @CriticalNative
    private static native void nativeOffsetLocation(long var0, float var2, float var3);

    private static native long nativeReadFromParcel(long var0, Parcel var2);

    @CriticalNative
    private static native void nativeScale(long var0, float var2);

    @CriticalNative
    private static native void nativeSetAction(long var0, int var2);

    @CriticalNative
    private static native void nativeSetActionButton(long var0, int var2);

    @CriticalNative
    private static native void nativeSetButtonState(long var0, int var2);

    @CriticalNative
    private static native void nativeSetDisplayId(long var0, int var2);

    @CriticalNative
    private static native void nativeSetDownTimeNanos(long var0, long var2);

    @CriticalNative
    private static native void nativeSetEdgeFlags(long var0, int var2);

    @CriticalNative
    private static native void nativeSetFlags(long var0, int var2);

    @CriticalNative
    private static native void nativeSetSource(long var0, int var2);

    @CriticalNative
    private static native void nativeTransform(long var0, long var2);

    private static native void nativeWriteToParcel(long var0, Parcel var2);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private static MotionEvent obtain() {
        MotionEvent motionEvent;
        Object object = gRecyclerLock;
        synchronized (object) {
            motionEvent = gRecyclerTop;
            if (motionEvent == null) {
                return new MotionEvent();
            }
            gRecyclerTop = motionEvent.mNext;
            --gRecyclerUsed;
        }
        motionEvent.mNext = null;
        motionEvent.prepareForReuse();
        return motionEvent;
    }

    public static MotionEvent obtain(long l, long l2, int n, float f, float f2, float f3, float f4, int n2, float f5, float f6, int n3, int n4) {
        return MotionEvent.obtain(l, l2, n, f, f2, f3, f4, n2, f5, f6, n3, n4, 0, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static MotionEvent obtain(long l, long l2, int n, float f, float f2, float f3, float f4, int n2, float f5, float f6, int n3, int n4, int n5, int n6) {
        MotionEvent motionEvent = MotionEvent.obtain();
        Object object = gSharedTempLock;
        synchronized (object) {
            MotionEvent.ensureSharedTempPointerCapacity(1);
            PointerProperties[] arrpointerProperties = gSharedTempPointerProperties;
            arrpointerProperties[0].clear();
            arrpointerProperties[0].id = 0;
            PointerCoords[] arrpointerCoords = gSharedTempPointerCoords;
            arrpointerCoords[0].clear();
            arrpointerCoords[0].x = f;
            arrpointerCoords[0].y = f2;
            arrpointerCoords[0].pressure = f3;
            arrpointerCoords[0].size = f4;
            motionEvent.mNativePtr = MotionEvent.nativeInitialize(motionEvent.mNativePtr, n3, n5, n6, n, 0, n4, n2, 0, 0, 0.0f, 0.0f, f5, f6, l * 1000000L, l2 * 1000000L, 1, arrpointerProperties, arrpointerCoords);
            return motionEvent;
        }
    }

    public static MotionEvent obtain(long l, long l2, int n, float f, float f2, int n2) {
        return MotionEvent.obtain(l, l2, n, f, f2, 1.0f, 1.0f, n2, 1.0f, 1.0f, 0, 0);
    }

    @Deprecated
    public static MotionEvent obtain(long l, long l2, int n, int n2, float f, float f2, float f3, float f4, int n3, float f5, float f6, int n4, int n5) {
        return MotionEvent.obtain(l, l2, n, f, f2, f3, f4, n3, f5, f6, n4, n5);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public static MotionEvent obtain(long l, long l2, int n, int n2, int[] object, PointerCoords[] arrpointerCoords, int n3, float f, float f2, int n4, int n5, int n6, int n7) {
        Object object2 = gSharedTempLock;
        synchronized (object2) {
            MotionEvent.ensureSharedTempPointerCapacity(n2);
            PointerProperties[] arrpointerProperties = gSharedTempPointerProperties;
            int n8 = 0;
            while (n8 < n2) {
                arrpointerProperties[n8].clear();
                arrpointerProperties[n8].id = object[n8];
                ++n8;
            }
            return MotionEvent.obtain(l, l2, n, n2, arrpointerProperties, arrpointerCoords, n3, 0, f, f2, n4, n5, n6, n7);
        }
    }

    public static MotionEvent obtain(long l, long l2, int n, int n2, PointerProperties[] arrpointerProperties, PointerCoords[] arrpointerCoords, int n3, int n4, float f, float f2, int n5, int n6, int n7, int n8) {
        return MotionEvent.obtain(l, l2, n, n2, arrpointerProperties, arrpointerCoords, n3, n4, f, f2, n5, n6, n7, 0, n8);
    }

    public static MotionEvent obtain(long l, long l2, int n, int n2, PointerProperties[] arrpointerProperties, PointerCoords[] arrpointerCoords, int n3, int n4, float f, float f2, int n5, int n6, int n7, int n8, int n9) {
        MotionEvent motionEvent = MotionEvent.obtain();
        motionEvent.mNativePtr = MotionEvent.nativeInitialize(motionEvent.mNativePtr, n5, n7, n8, n, n9, n6, n3, n4, 0, 0.0f, 0.0f, f, f2, l * 1000000L, l2 * 1000000L, n2, arrpointerProperties, arrpointerCoords);
        if (motionEvent.mNativePtr == 0L) {
            Log.e("MotionEvent", "Could not initialize MotionEvent");
            motionEvent.recycle();
            return null;
        }
        return motionEvent;
    }

    public static MotionEvent obtain(MotionEvent motionEvent) {
        if (motionEvent != null) {
            MotionEvent motionEvent2 = MotionEvent.obtain();
            motionEvent2.mNativePtr = MotionEvent.nativeCopy(motionEvent2.mNativePtr, motionEvent.mNativePtr, true);
            return motionEvent2;
        }
        throw new IllegalArgumentException("other motion event must not be null");
    }

    public static MotionEvent obtainNoHistory(MotionEvent motionEvent) {
        if (motionEvent != null) {
            MotionEvent motionEvent2 = MotionEvent.obtain();
            motionEvent2.mNativePtr = MotionEvent.nativeCopy(motionEvent2.mNativePtr, motionEvent.mNativePtr, false);
            return motionEvent2;
        }
        throw new IllegalArgumentException("other motion event must not be null");
    }

    public static String toolTypeToString(int n) {
        String string2 = TOOL_TYPE_SYMBOLIC_NAMES.get(n);
        if (string2 == null) {
            string2 = Integer.toString(n);
        }
        return string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void addBatch(long l, float f, float f2, float f3, float f4, int n) {
        Object object = gSharedTempLock;
        synchronized (object) {
            MotionEvent.ensureSharedTempPointerCapacity(1);
            PointerCoords[] arrpointerCoords = gSharedTempPointerCoords;
            arrpointerCoords[0].clear();
            arrpointerCoords[0].x = f;
            arrpointerCoords[0].y = f2;
            arrpointerCoords[0].pressure = f3;
            arrpointerCoords[0].size = f4;
            MotionEvent.nativeAddBatch(this.mNativePtr, 1000000L * l, arrpointerCoords, n);
            return;
        }
    }

    public final void addBatch(long l, PointerCoords[] arrpointerCoords, int n) {
        MotionEvent.nativeAddBatch(this.mNativePtr, 1000000L * l, arrpointerCoords, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public final boolean addBatch(MotionEvent motionEvent) {
        int n = MotionEvent.nativeGetAction(this.mNativePtr);
        if (n != 2 && n != 7) {
            return false;
        }
        if (n != MotionEvent.nativeGetAction(motionEvent.mNativePtr)) {
            return false;
        }
        if (MotionEvent.nativeGetDeviceId(this.mNativePtr) != MotionEvent.nativeGetDeviceId(motionEvent.mNativePtr)) return false;
        if (MotionEvent.nativeGetSource(this.mNativePtr) != MotionEvent.nativeGetSource(motionEvent.mNativePtr)) return false;
        if (MotionEvent.nativeGetDisplayId(this.mNativePtr) != MotionEvent.nativeGetDisplayId(motionEvent.mNativePtr)) return false;
        if (MotionEvent.nativeGetFlags(this.mNativePtr) != MotionEvent.nativeGetFlags(motionEvent.mNativePtr)) return false;
        if (MotionEvent.nativeGetClassification(this.mNativePtr) != MotionEvent.nativeGetClassification(motionEvent.mNativePtr)) {
            return false;
        }
        int n2 = MotionEvent.nativeGetPointerCount(this.mNativePtr);
        if (n2 != MotionEvent.nativeGetPointerCount(motionEvent.mNativePtr)) {
            return false;
        }
        Object object = gSharedTempLock;
        synchronized (object) {
            MotionEvent.ensureSharedTempPointerCapacity(Math.max(n2, 2));
            PointerProperties[] arrpointerProperties = gSharedTempPointerProperties;
            PointerCoords[] arrpointerCoords = gSharedTempPointerCoords;
            for (n = 0; n < n2; ++n) {
                MotionEvent.nativeGetPointerProperties(this.mNativePtr, n, arrpointerProperties[0]);
                MotionEvent.nativeGetPointerProperties(motionEvent.mNativePtr, n, arrpointerProperties[1]);
                if (arrpointerProperties[0].equals(arrpointerProperties[1])) continue;
                return false;
            }
            int n3 = MotionEvent.nativeGetMetaState(motionEvent.mNativePtr);
            int n4 = MotionEvent.nativeGetHistorySize(motionEvent.mNativePtr);
            n = 0;
            while (n <= n4) {
                int n5 = n == n4 ? Integer.MIN_VALUE : n;
                for (int i = 0; i < n2; ++i) {
                    MotionEvent.nativeGetPointerCoords(motionEvent.mNativePtr, i, n5, arrpointerCoords[i]);
                }
                long l = MotionEvent.nativeGetEventTimeNanos(motionEvent.mNativePtr, n5);
                MotionEvent.nativeAddBatch(this.mNativePtr, l, arrpointerCoords, n3);
                ++n;
            }
            return true;
        }
    }

    @Override
    public final void cancel() {
        this.setAction(3);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final MotionEvent clampNoHistory(float f, float f2, float f3, float f4) {
        motionEvent = MotionEvent.obtain();
        var6_9 = MotionEvent.gSharedTempLock;
        // MONITORENTER : var6_9
        var7_10 = MotionEvent.nativeGetPointerCount(this.mNativePtr);
        MotionEvent.ensureSharedTempPointerCapacity(var7_10);
        var8_11 = MotionEvent.gSharedTempPointerProperties;
        var9_12 = MotionEvent.gSharedTempPointerCoords;
        var10_13 = 0;
        do lbl-1000: // 2 sources:
        {
            if (var10_13 >= var7_10) {
                motionEvent.mNativePtr = MotionEvent.nativeInitialize(motionEvent.mNativePtr, MotionEvent.nativeGetDeviceId(this.mNativePtr), MotionEvent.nativeGetSource(this.mNativePtr), MotionEvent.nativeGetDisplayId(this.mNativePtr), MotionEvent.nativeGetAction(this.mNativePtr), MotionEvent.nativeGetFlags(this.mNativePtr), MotionEvent.nativeGetEdgeFlags(this.mNativePtr), MotionEvent.nativeGetMetaState(this.mNativePtr), MotionEvent.nativeGetButtonState(this.mNativePtr), MotionEvent.nativeGetClassification(this.mNativePtr), MotionEvent.nativeGetXOffset(this.mNativePtr), MotionEvent.nativeGetYOffset(this.mNativePtr), MotionEvent.nativeGetXPrecision(this.mNativePtr), MotionEvent.nativeGetYPrecision(this.mNativePtr), MotionEvent.nativeGetDownTimeNanos(this.mNativePtr), MotionEvent.nativeGetEventTimeNanos(this.mNativePtr, Integer.MIN_VALUE), var7_10, var8_11, var9_12);
                // MONITOREXIT : var6_9
                return motionEvent;
            }
            MotionEvent.nativeGetPointerProperties(this.mNativePtr, var10_13, var8_11[var10_13]);
            MotionEvent.nativeGetPointerCoords(this.mNativePtr, var10_13, Integer.MIN_VALUE, var9_12[var10_13]);
            var11_14 = var9_12[var10_13];
            var12_15 = var9_12[var10_13].x;
            var11_14.x = MotionEvent.clamp(var12_15, f, f3);
            var11_14 = var9_12[var10_13];
            var12_15 = var9_12[var10_13].y;
            break;
        } while (true);
        catch (Throwable throwable) {
            throw var5_8;
        }
        {
            var11_14.y = MotionEvent.clamp(var12_15, f2, f4);
            ++var10_13;
            ** while (true)
        }
        catch (Throwable throwable) {
            throw var5_8;
        }
    }

    @UnsupportedAppUsage
    @Override
    public MotionEvent copy() {
        return MotionEvent.obtain(this);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mNativePtr != 0L) {
                MotionEvent.nativeDispose(this.mNativePtr);
                this.mNativePtr = 0L;
            }
            return;
        }
        finally {
            Object.super.finalize();
        }
    }

    public final int findPointerIndex(int n) {
        return MotionEvent.nativeFindPointerIndex(this.mNativePtr, n);
    }

    public final int getAction() {
        return MotionEvent.nativeGetAction(this.mNativePtr);
    }

    public final int getActionButton() {
        return MotionEvent.nativeGetActionButton(this.mNativePtr);
    }

    public final int getActionIndex() {
        return (MotionEvent.nativeGetAction(this.mNativePtr) & 65280) >> 8;
    }

    public final int getActionMasked() {
        return MotionEvent.nativeGetAction(this.mNativePtr) & 255;
    }

    public final float getAxisValue(int n) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, n, 0, Integer.MIN_VALUE);
    }

    public final float getAxisValue(int n, int n2) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, n, n2, Integer.MIN_VALUE);
    }

    public final int getButtonState() {
        return MotionEvent.nativeGetButtonState(this.mNativePtr);
    }

    public int getClassification() {
        return MotionEvent.nativeGetClassification(this.mNativePtr);
    }

    @Override
    public final int getDeviceId() {
        return MotionEvent.nativeGetDeviceId(this.mNativePtr);
    }

    @Override
    public int getDisplayId() {
        return MotionEvent.nativeGetDisplayId(this.mNativePtr);
    }

    public final long getDownTime() {
        return MotionEvent.nativeGetDownTimeNanos(this.mNativePtr) / 1000000L;
    }

    public final int getEdgeFlags() {
        return MotionEvent.nativeGetEdgeFlags(this.mNativePtr);
    }

    @Override
    public final long getEventTime() {
        return MotionEvent.nativeGetEventTimeNanos(this.mNativePtr, Integer.MIN_VALUE) / 1000000L;
    }

    @UnsupportedAppUsage
    @Override
    public final long getEventTimeNano() {
        return MotionEvent.nativeGetEventTimeNanos(this.mNativePtr, Integer.MIN_VALUE);
    }

    public final int getFlags() {
        return MotionEvent.nativeGetFlags(this.mNativePtr);
    }

    public final float getHistoricalAxisValue(int n, int n2) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, n, 0, n2);
    }

    public final float getHistoricalAxisValue(int n, int n2, int n3) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, n, n2, n3);
    }

    public final long getHistoricalEventTime(int n) {
        return MotionEvent.nativeGetEventTimeNanos(this.mNativePtr, n) / 1000000L;
    }

    public final long getHistoricalEventTimeNano(int n) {
        return MotionEvent.nativeGetEventTimeNanos(this.mNativePtr, n);
    }

    public final float getHistoricalOrientation(int n) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 8, 0, n);
    }

    public final float getHistoricalOrientation(int n, int n2) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 8, n, n2);
    }

    public final void getHistoricalPointerCoords(int n, int n2, PointerCoords pointerCoords) {
        MotionEvent.nativeGetPointerCoords(this.mNativePtr, n, n2, pointerCoords);
    }

    public final float getHistoricalPressure(int n) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 2, 0, n);
    }

    public final float getHistoricalPressure(int n, int n2) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 2, n, n2);
    }

    public final float getHistoricalSize(int n) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 3, 0, n);
    }

    public final float getHistoricalSize(int n, int n2) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 3, n, n2);
    }

    public final float getHistoricalToolMajor(int n) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 6, 0, n);
    }

    public final float getHistoricalToolMajor(int n, int n2) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 6, n, n2);
    }

    public final float getHistoricalToolMinor(int n) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 7, 0, n);
    }

    public final float getHistoricalToolMinor(int n, int n2) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 7, n, n2);
    }

    public final float getHistoricalTouchMajor(int n) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 4, 0, n);
    }

    public final float getHistoricalTouchMajor(int n, int n2) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 4, n, n2);
    }

    public final float getHistoricalTouchMinor(int n) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 5, 0, n);
    }

    public final float getHistoricalTouchMinor(int n, int n2) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 5, n, n2);
    }

    public final float getHistoricalX(int n) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 0, 0, n);
    }

    public final float getHistoricalX(int n, int n2) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 0, n, n2);
    }

    public final float getHistoricalY(int n) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 1, 0, n);
    }

    public final float getHistoricalY(int n, int n2) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 1, n, n2);
    }

    public final int getHistorySize() {
        return MotionEvent.nativeGetHistorySize(this.mNativePtr);
    }

    public final int getMetaState() {
        return MotionEvent.nativeGetMetaState(this.mNativePtr);
    }

    public final float getOrientation() {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 8, 0, Integer.MIN_VALUE);
    }

    public final float getOrientation(int n) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 8, n, Integer.MIN_VALUE);
    }

    public final void getPointerCoords(int n, PointerCoords pointerCoords) {
        MotionEvent.nativeGetPointerCoords(this.mNativePtr, n, Integer.MIN_VALUE, pointerCoords);
    }

    public final int getPointerCount() {
        return MotionEvent.nativeGetPointerCount(this.mNativePtr);
    }

    public final int getPointerId(int n) {
        return MotionEvent.nativeGetPointerId(this.mNativePtr, n);
    }

    @UnsupportedAppUsage
    public final int getPointerIdBits() {
        int n = 0;
        int n2 = MotionEvent.nativeGetPointerCount(this.mNativePtr);
        for (int i = 0; i < n2; ++i) {
            n |= 1 << MotionEvent.nativeGetPointerId(this.mNativePtr, i);
        }
        return n;
    }

    public final void getPointerProperties(int n, PointerProperties pointerProperties) {
        MotionEvent.nativeGetPointerProperties(this.mNativePtr, n, pointerProperties);
    }

    public final float getPressure() {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 2, 0, Integer.MIN_VALUE);
    }

    public final float getPressure(int n) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 2, n, Integer.MIN_VALUE);
    }

    public final float getRawX() {
        return MotionEvent.nativeGetRawAxisValue(this.mNativePtr, 0, 0, Integer.MIN_VALUE);
    }

    public float getRawX(int n) {
        return MotionEvent.nativeGetRawAxisValue(this.mNativePtr, 0, n, Integer.MIN_VALUE);
    }

    public final float getRawY() {
        return MotionEvent.nativeGetRawAxisValue(this.mNativePtr, 1, 0, Integer.MIN_VALUE);
    }

    public float getRawY(int n) {
        return MotionEvent.nativeGetRawAxisValue(this.mNativePtr, 1, n, Integer.MIN_VALUE);
    }

    public final float getSize() {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 3, 0, Integer.MIN_VALUE);
    }

    public final float getSize(int n) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 3, n, Integer.MIN_VALUE);
    }

    @Override
    public final int getSource() {
        return MotionEvent.nativeGetSource(this.mNativePtr);
    }

    public final float getToolMajor() {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 6, 0, Integer.MIN_VALUE);
    }

    public final float getToolMajor(int n) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 6, n, Integer.MIN_VALUE);
    }

    public final float getToolMinor() {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 7, 0, Integer.MIN_VALUE);
    }

    public final float getToolMinor(int n) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 7, n, Integer.MIN_VALUE);
    }

    public final int getToolType(int n) {
        return MotionEvent.nativeGetToolType(this.mNativePtr, n);
    }

    public final float getTouchMajor() {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 4, 0, Integer.MIN_VALUE);
    }

    public final float getTouchMajor(int n) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 4, n, Integer.MIN_VALUE);
    }

    public final float getTouchMinor() {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 5, 0, Integer.MIN_VALUE);
    }

    public final float getTouchMinor(int n) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 5, n, Integer.MIN_VALUE);
    }

    public final float getX() {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 0, 0, Integer.MIN_VALUE);
    }

    public final float getX(int n) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 0, n, Integer.MIN_VALUE);
    }

    public final float getXPrecision() {
        return MotionEvent.nativeGetXPrecision(this.mNativePtr);
    }

    public final float getY() {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 1, 0, Integer.MIN_VALUE);
    }

    public final float getY(int n) {
        return MotionEvent.nativeGetAxisValue(this.mNativePtr, 1, n, Integer.MIN_VALUE);
    }

    public final float getYPrecision() {
        return MotionEvent.nativeGetYPrecision(this.mNativePtr);
    }

    public final boolean isButtonPressed(int n) {
        boolean bl = false;
        if (n == 0) {
            return false;
        }
        if ((this.getButtonState() & n) == n) {
            bl = true;
        }
        return bl;
    }

    public final boolean isHoverExitPending() {
        boolean bl = (this.getFlags() & 4) != 0;
        return bl;
    }

    @Override
    public final boolean isTainted() {
        boolean bl = (Integer.MIN_VALUE & this.getFlags()) != 0;
        return bl;
    }

    public final boolean isTargetAccessibilityFocus() {
        boolean bl = (1073741824 & this.getFlags()) != 0;
        return bl;
    }

    public final boolean isTouchEvent() {
        return MotionEvent.nativeIsTouchEvent(this.mNativePtr);
    }

    public final boolean isWithinBoundsNoHistory(float f, float f2, float f3, float f4) {
        int n = MotionEvent.nativeGetPointerCount(this.mNativePtr);
        for (int i = 0; i < n; ++i) {
            float f5 = MotionEvent.nativeGetAxisValue(this.mNativePtr, 0, i, Integer.MIN_VALUE);
            float f6 = MotionEvent.nativeGetAxisValue(this.mNativePtr, 1, i, Integer.MIN_VALUE);
            if (!(f5 < f || f5 > f3 || f6 < f2 || f6 > f4)) {
                continue;
            }
            return false;
        }
        return true;
    }

    public final void offsetLocation(float f, float f2) {
        if (f != 0.0f || f2 != 0.0f) {
            MotionEvent.nativeOffsetLocation(this.mNativePtr, f, f2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void recycle() {
        super.recycle();
        Object object = gRecyclerLock;
        synchronized (object) {
            if (gRecyclerUsed < 10) {
                ++gRecyclerUsed;
                this.mNext = gRecyclerTop;
                gRecyclerTop = this;
            }
            return;
        }
    }

    @UnsupportedAppUsage
    public final void scale(float f) {
        if (f != 1.0f) {
            MotionEvent.nativeScale(this.mNativePtr, f);
        }
    }

    public final void setAction(int n) {
        MotionEvent.nativeSetAction(this.mNativePtr, n);
    }

    public final void setActionButton(int n) {
        MotionEvent.nativeSetActionButton(this.mNativePtr, n);
    }

    public final void setButtonState(int n) {
        MotionEvent.nativeSetButtonState(this.mNativePtr, n);
    }

    @Override
    public void setDisplayId(int n) {
        MotionEvent.nativeSetDisplayId(this.mNativePtr, n);
    }

    @UnsupportedAppUsage
    public final void setDownTime(long l) {
        MotionEvent.nativeSetDownTimeNanos(this.mNativePtr, 1000000L * l);
    }

    public final void setEdgeFlags(int n) {
        MotionEvent.nativeSetEdgeFlags(this.mNativePtr, n);
    }

    public void setHoverExitPending(boolean bl) {
        int n = this.getFlags();
        long l = this.mNativePtr;
        n = bl ? (n |= 4) : (n &= -5);
        MotionEvent.nativeSetFlags(l, n);
    }

    public final void setLocation(float f, float f2) {
        this.offsetLocation(f - this.getX(), f2 - this.getY());
    }

    @Override
    public final void setSource(int n) {
        MotionEvent.nativeSetSource(this.mNativePtr, n);
    }

    @Override
    public final void setTainted(boolean bl) {
        int n = this.getFlags();
        long l = this.mNativePtr;
        n = bl ? Integer.MIN_VALUE | n : Integer.MAX_VALUE & n;
        MotionEvent.nativeSetFlags(l, n);
    }

    public final void setTargetAccessibilityFocus(boolean bl) {
        int n = this.getFlags();
        long l = this.mNativePtr;
        n = bl ? 1073741824 | n : -1073741825 & n;
        MotionEvent.nativeSetFlags(l, n);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public final MotionEvent split(int var1_1) {
        block20 : {
            var2_2 = MotionEvent.obtain();
            var3_3 = MotionEvent.gSharedTempLock;
            // MONITORENTER : var3_3
            var4_9 = var2_2;
            var4_9 = var3_3;
            var5_10 = MotionEvent.nativeGetPointerCount(this.mNativePtr);
            var4_9 = var2_2;
            var4_9 = var3_3;
            MotionEvent.ensureSharedTempPointerCapacity(var5_10);
            var4_9 = var2_2;
            var4_9 = var3_3;
            var6_11 = MotionEvent.gSharedTempPointerProperties;
            var4_9 = var2_2;
            var4_9 = var3_3;
            var7_12 = MotionEvent.gSharedTempPointerCoords;
            var4_9 = var2_2;
            var4_9 = var3_3;
            var8_13 = MotionEvent.gSharedTempPointerIndexMap;
            var4_9 = var2_2;
            var4_9 = var3_3;
            var9_14 = MotionEvent.nativeGetAction(this.mNativePtr);
            var10_15 = var9_14 & 255;
            var11_16 = (65280 & var9_14) >> 8;
            var12_17 = 0;
            var13_18 = -1;
            var14_19 = 0;
            do {
                block19 : {
                    var15_20 = 1;
                    if (var12_17 >= var5_10) break;
                    var4_9 = var2_2;
                    var4_9 = var3_3;
                    MotionEvent.nativeGetPointerProperties(this.mNativePtr, var12_17, var6_11[var14_19]);
                    var16_21 = var14_19;
                    var15_20 = var13_18;
                    var4_9 = var2_2;
                    var4_9 = var3_3;
                    if ((1 << var6_11[var14_19].id & var1_1) == 0) break block19;
                    if (var12_17 == var11_16) {
                        var13_18 = var14_19;
                    }
                    var8_13[var14_19] = var12_17;
                    var16_21 = var14_19 + 1;
                    var15_20 = var13_18;
                }
                ++var12_17;
                var14_19 = var16_21;
                var13_18 = var15_20;
            } while (true);
            if (var14_19 == 0) ** GOTO lbl-1000
            var1_1 = var10_15 != 5 && var10_15 != 6 ? var9_14 : (var13_18 < 0 ? 2 : (var14_19 == 1 ? (var10_15 == 5 ? 0 : var15_20) : var13_18 << 8 | var10_15));
            var4_9 = var2_2;
            var4_9 = var3_3;
            var17_22 = MotionEvent.nativeGetHistorySize(this.mNativePtr);
            var18_23 = 0;
            var13_18 = var9_14;
            var12_17 = var10_15;
            var15_20 = var11_16;
            var16_21 = var14_19;
            var14_19 = var18_23;
            var9_14 = var5_10;
            ** GOTO lbl72
lbl-1000: // 1 sources:
            {
                var4_9 = var3_3;
                var4_9 = var3_3;
                var2_2 = new IllegalArgumentException("idBits did not match any ids in the event");
                var4_9 = var3_3;
                throw var2_2;
                catch (Throwable var3_4) {
                    var2_2 = var4_9;
                    break block20;
                }
lbl72: // 1 sources:
                do {
                    block21 : {
                        if (var14_19 <= var17_22) {
                            var5_10 = var14_19 == var17_22 ? Integer.MIN_VALUE : var14_19;
                        } else {
                            var4_9 = var3_3;
                            ** try [egrp 7[TRYBLOCK] [22 : 650->685)] { 
lbl78: // 1 sources:
                            // MONITOREXIT : var3_3
                            return var2_2;
                        }
                        for (var11_16 = 0; var11_16 < var16_21; ++var11_16) {
                            var4_9 = var2_2;
                            var4_9 = var3_3;
                            {
                                MotionEvent.nativeGetPointerCoords(this.mNativePtr, var8_13[var11_16], var5_10, var7_12[var11_16]);
                            }
                        }
                        var4_9 = var2_2;
                        var4_9 = var3_3;
                        var19_24 = MotionEvent.nativeGetEventTimeNanos(this.mNativePtr, var5_10);
                        if (var14_19 != 0) ** GOTO lbl123
                        var4_9 = var2_2;
                        var4_9 = var3_3;
                        var21_25 = var2_2.mNativePtr;
                        var4_9 = var2_2;
                        var4_9 = var3_3;
                        var5_10 = MotionEvent.nativeGetDeviceId(this.mNativePtr);
                        var4_9 = var2_2;
                        var4_9 = var3_3;
                        var11_16 = MotionEvent.nativeGetSource(this.mNativePtr);
                        var4_9 = var2_2;
                        var4_9 = var3_3;
                        var23_26 = MotionEvent.nativeGetDisplayId(this.mNativePtr);
                        var4_9 = var2_2;
                        var4_9 = var3_3;
                        var18_23 = MotionEvent.nativeGetFlags(this.mNativePtr);
                        var4_9 = var2_2;
                        var4_9 = var3_3;
                        var24_27 = MotionEvent.nativeGetEdgeFlags(this.mNativePtr);
                        var4_9 = var2_2;
                        var4_9 = var3_3;
                        var10_15 = MotionEvent.nativeGetMetaState(this.mNativePtr);
                        var4_9 = var2_2;
                        var4_9 = var3_3;
                        var25_28 = MotionEvent.nativeGetButtonState(this.mNativePtr);
                        var4_9 = var3_3;
                        var21_25 = MotionEvent.nativeInitialize(var21_25, var5_10, var11_16, var23_26, var1_1, var18_23, var24_27, var10_15, var25_28, MotionEvent.nativeGetClassification(this.mNativePtr), MotionEvent.nativeGetXOffset(this.mNativePtr), MotionEvent.nativeGetYOffset(this.mNativePtr), MotionEvent.nativeGetXPrecision(this.mNativePtr), MotionEvent.nativeGetYPrecision(this.mNativePtr), MotionEvent.nativeGetDownTimeNanos(this.mNativePtr), var19_24, var16_21, var6_11, var7_12);
                        {
                            catch (Throwable var3_5) {
                                var2_2 = var4_9;
                                break block20;
                            }
                        }
                        var2_2.mNativePtr = var21_25;
                        break block21;
lbl123: // 1 sources:
                        var4_9 = var3_3;
                        MotionEvent.nativeAddBatch(var2_2.mNativePtr, var19_24, var7_12, 0);
                    }
                    ++var14_19;
                    continue;
                    break;
                } while (true);
                catch (Throwable var3_6) {
                    var2_2 = var4_9;
                }
            }
            break block20;
lbl131: // 2 sources:
            catch (Throwable var3_8) {
                var2_2 = var4_9;
            }
        }
        var4_9 = var2_2;
        // MONITOREXIT : var2_2
        throw var3_7;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MotionEvent { action=");
        stringBuilder.append(MotionEvent.actionToString(this.getAction()));
        MotionEvent.appendUnless("0", stringBuilder, ", actionButton=", MotionEvent.buttonStateToString(this.getActionButton()));
        int n = this.getPointerCount();
        for (int i = 0; i < n; ++i) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append(", id[");
            charSequence.append(i);
            charSequence.append("]=");
            MotionEvent.appendUnless(i, stringBuilder, charSequence.toString(), this.getPointerId(i));
            float f = this.getX(i);
            float f2 = this.getY(i);
            stringBuilder.append(", x[");
            stringBuilder.append(i);
            stringBuilder.append("]=");
            stringBuilder.append(f);
            stringBuilder.append(", y[");
            stringBuilder.append(i);
            stringBuilder.append("]=");
            stringBuilder.append(f2);
            charSequence = TOOL_TYPE_SYMBOLIC_NAMES.get(1);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", toolType[");
            stringBuilder2.append(i);
            stringBuilder2.append("]=");
            MotionEvent.appendUnless(charSequence, stringBuilder, stringBuilder2.toString(), MotionEvent.toolTypeToString(this.getToolType(i)));
        }
        MotionEvent.appendUnless("0", stringBuilder, ", buttonState=", MotionEvent.buttonStateToString(this.getButtonState()));
        MotionEvent.appendUnless(MotionEvent.classificationToString(0), stringBuilder, ", classification=", MotionEvent.classificationToString(this.getClassification()));
        MotionEvent.appendUnless("0", stringBuilder, ", metaState=", KeyEvent.metaStateToString(this.getMetaState()));
        MotionEvent.appendUnless("0", stringBuilder, ", flags=0x", Integer.toHexString(this.getFlags()));
        MotionEvent.appendUnless("0", stringBuilder, ", edgeFlags=0x", Integer.toHexString(this.getEdgeFlags()));
        MotionEvent.appendUnless(1, stringBuilder, ", pointerCount=", n);
        MotionEvent.appendUnless(0, stringBuilder, ", historySize=", this.getHistorySize());
        stringBuilder.append(", eventTime=");
        stringBuilder.append(this.getEventTime());
        stringBuilder.append(", downTime=");
        stringBuilder.append(this.getDownTime());
        stringBuilder.append(", deviceId=");
        stringBuilder.append(this.getDeviceId());
        stringBuilder.append(", source=0x");
        stringBuilder.append(Integer.toHexString(this.getSource()));
        stringBuilder.append(", displayId=");
        stringBuilder.append(this.getDisplayId());
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    public final void transform(Matrix matrix) {
        if (matrix != null) {
            MotionEvent.nativeTransform(this.mNativePtr, matrix.native_instance);
            return;
        }
        throw new IllegalArgumentException("matrix must not be null");
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(1);
        MotionEvent.nativeWriteToParcel(this.mNativePtr, parcel);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Classification {
    }

    public static final class PointerCoords {
        private static final int INITIAL_PACKED_AXIS_VALUES = 8;
        @UnsupportedAppUsage
        private long mPackedAxisBits;
        @UnsupportedAppUsage
        private float[] mPackedAxisValues;
        public float orientation;
        public float pressure;
        public float size;
        public float toolMajor;
        public float toolMinor;
        public float touchMajor;
        public float touchMinor;
        public float x;
        public float y;

        public PointerCoords() {
        }

        public PointerCoords(PointerCoords pointerCoords) {
            this.copyFrom(pointerCoords);
        }

        @UnsupportedAppUsage
        public static PointerCoords[] createArray(int n) {
            PointerCoords[] arrpointerCoords = new PointerCoords[n];
            for (int i = 0; i < n; ++i) {
                arrpointerCoords[i] = new PointerCoords();
            }
            return arrpointerCoords;
        }

        public void clear() {
            this.mPackedAxisBits = 0L;
            this.x = 0.0f;
            this.y = 0.0f;
            this.pressure = 0.0f;
            this.size = 0.0f;
            this.touchMajor = 0.0f;
            this.touchMinor = 0.0f;
            this.toolMajor = 0.0f;
            this.toolMinor = 0.0f;
            this.orientation = 0.0f;
        }

        public void copyFrom(PointerCoords pointerCoords) {
            block2 : {
                int n;
                float[] arrf;
                float[] arrf2;
                block4 : {
                    block3 : {
                        long l;
                        this.mPackedAxisBits = l = pointerCoords.mPackedAxisBits;
                        if (l == 0L) break block2;
                        arrf2 = pointerCoords.mPackedAxisValues;
                        n = Long.bitCount(l);
                        float[] arrf3 = this.mPackedAxisValues;
                        if (arrf3 == null) break block3;
                        arrf = arrf3;
                        if (n <= arrf3.length) break block4;
                    }
                    this.mPackedAxisValues = arrf = new float[arrf2.length];
                }
                System.arraycopy(arrf2, 0, arrf, 0, n);
            }
            this.x = pointerCoords.x;
            this.y = pointerCoords.y;
            this.pressure = pointerCoords.pressure;
            this.size = pointerCoords.size;
            this.touchMajor = pointerCoords.touchMajor;
            this.touchMinor = pointerCoords.touchMinor;
            this.toolMajor = pointerCoords.toolMajor;
            this.toolMinor = pointerCoords.toolMinor;
            this.orientation = pointerCoords.orientation;
        }

        public float getAxisValue(int n) {
            block12 : {
                long l;
                switch (n) {
                    default: {
                        if (n >= 0 && n <= 63) {
                            l = this.mPackedAxisBits;
                            if ((l & Long.MIN_VALUE >>> n) != 0L) break;
                            return 0.0f;
                        }
                        break block12;
                    }
                    case 8: {
                        return this.orientation;
                    }
                    case 7: {
                        return this.toolMinor;
                    }
                    case 6: {
                        return this.toolMajor;
                    }
                    case 5: {
                        return this.touchMinor;
                    }
                    case 4: {
                        return this.touchMajor;
                    }
                    case 3: {
                        return this.size;
                    }
                    case 2: {
                        return this.pressure;
                    }
                    case 1: {
                        return this.y;
                    }
                    case 0: {
                        return this.x;
                    }
                }
                n = Long.bitCount(-1L >>> n & l);
                return this.mPackedAxisValues[n];
            }
            throw new IllegalArgumentException("Axis out of range.");
        }

        public void setAxisValue(int n, float f) {
            block14 : {
                block17 : {
                    block15 : {
                        long l;
                        long l2;
                        block16 : {
                            float[] arrf;
                            float[] arrf2;
                            switch (n) {
                                default: {
                                    if (n < 0 || n > 63) break block14;
                                    l = this.mPackedAxisBits;
                                    l2 = Long.MIN_VALUE >>> n;
                                    n = Long.bitCount(-1L >>> n & l);
                                    arrf = arrf2 = this.mPackedAxisValues;
                                    if ((l & l2) != 0L) break block15;
                                    if (arrf2 != null) break;
                                    this.mPackedAxisValues = arrf = new float[8];
                                    break block16;
                                }
                                case 8: {
                                    this.orientation = f;
                                    break block17;
                                }
                                case 7: {
                                    this.toolMinor = f;
                                    break block17;
                                }
                                case 6: {
                                    this.toolMajor = f;
                                    break block17;
                                }
                                case 5: {
                                    this.touchMinor = f;
                                    break block17;
                                }
                                case 4: {
                                    this.touchMajor = f;
                                    break block17;
                                }
                                case 3: {
                                    this.size = f;
                                    break block17;
                                }
                                case 2: {
                                    this.pressure = f;
                                    break block17;
                                }
                                case 1: {
                                    this.y = f;
                                    break block17;
                                }
                                case 0: {
                                    this.x = f;
                                    break block17;
                                }
                            }
                            int n2 = Long.bitCount(l);
                            if (n2 < arrf2.length) {
                                arrf = arrf2;
                                if (n != n2) {
                                    System.arraycopy(arrf2, n, arrf2, n + 1, n2 - n);
                                    arrf = arrf2;
                                }
                            } else {
                                arrf = new float[n2 * 2];
                                System.arraycopy(arrf2, 0, arrf, 0, n);
                                System.arraycopy(arrf2, n, arrf, n + 1, n2 - n);
                                this.mPackedAxisValues = arrf;
                            }
                        }
                        this.mPackedAxisBits = l | l2;
                    }
                    arrf[n] = f;
                }
                return;
            }
            throw new IllegalArgumentException("Axis out of range.");
        }
    }

    public static final class PointerProperties {
        public int id;
        public int toolType;

        public PointerProperties() {
            this.clear();
        }

        public PointerProperties(PointerProperties pointerProperties) {
            this.copyFrom(pointerProperties);
        }

        @UnsupportedAppUsage
        public static PointerProperties[] createArray(int n) {
            PointerProperties[] arrpointerProperties = new PointerProperties[n];
            for (int i = 0; i < n; ++i) {
                arrpointerProperties[i] = new PointerProperties();
            }
            return arrpointerProperties;
        }

        private boolean equals(PointerProperties pointerProperties) {
            boolean bl = pointerProperties != null && this.id == pointerProperties.id && this.toolType == pointerProperties.toolType;
            return bl;
        }

        public void clear() {
            this.id = -1;
            this.toolType = 0;
        }

        public void copyFrom(PointerProperties pointerProperties) {
            this.id = pointerProperties.id;
            this.toolType = pointerProperties.toolType;
        }

        public boolean equals(Object object) {
            if (object instanceof PointerProperties) {
                return this.equals((PointerProperties)object);
            }
            return false;
        }

        public int hashCode() {
            return this.id | this.toolType << 8;
        }
    }

}

