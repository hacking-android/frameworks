/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.hardware.input.InputDeviceIdentifier;
import android.hardware.input.InputManager;
import android.os.NullVibrator;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Vibrator;
import android.view.KeyCharacterMap;
import android.view.MotionEvent;
import android.view.PointerIcon;
import java.util.ArrayList;
import java.util.List;

public final class InputDevice
implements Parcelable {
    public static final Parcelable.Creator<InputDevice> CREATOR = new Parcelable.Creator<InputDevice>(){

        @Override
        public InputDevice createFromParcel(Parcel parcel) {
            return new InputDevice(parcel);
        }

        public InputDevice[] newArray(int n) {
            return new InputDevice[n];
        }
    };
    public static final int KEYBOARD_TYPE_ALPHABETIC = 2;
    public static final int KEYBOARD_TYPE_NONE = 0;
    public static final int KEYBOARD_TYPE_NON_ALPHABETIC = 1;
    private static final int MAX_RANGES = 1000;
    @Deprecated
    public static final int MOTION_RANGE_ORIENTATION = 8;
    @Deprecated
    public static final int MOTION_RANGE_PRESSURE = 2;
    @Deprecated
    public static final int MOTION_RANGE_SIZE = 3;
    @Deprecated
    public static final int MOTION_RANGE_TOOL_MAJOR = 6;
    @Deprecated
    public static final int MOTION_RANGE_TOOL_MINOR = 7;
    @Deprecated
    public static final int MOTION_RANGE_TOUCH_MAJOR = 4;
    @Deprecated
    public static final int MOTION_RANGE_TOUCH_MINOR = 5;
    @Deprecated
    public static final int MOTION_RANGE_X = 0;
    @Deprecated
    public static final int MOTION_RANGE_Y = 1;
    public static final int SOURCE_ANY = -256;
    public static final int SOURCE_BLUETOOTH_STYLUS = 49154;
    public static final int SOURCE_CLASS_BUTTON = 1;
    public static final int SOURCE_CLASS_JOYSTICK = 16;
    public static final int SOURCE_CLASS_MASK = 255;
    public static final int SOURCE_CLASS_NONE = 0;
    public static final int SOURCE_CLASS_POINTER = 2;
    public static final int SOURCE_CLASS_POSITION = 8;
    public static final int SOURCE_CLASS_TRACKBALL = 4;
    public static final int SOURCE_DPAD = 513;
    public static final int SOURCE_GAMEPAD = 1025;
    public static final int SOURCE_HDMI = 33554433;
    public static final int SOURCE_JOYSTICK = 16777232;
    public static final int SOURCE_KEYBOARD = 257;
    public static final int SOURCE_MOUSE = 8194;
    public static final int SOURCE_MOUSE_RELATIVE = 131076;
    public static final int SOURCE_ROTARY_ENCODER = 4194304;
    public static final int SOURCE_STYLUS = 16386;
    public static final int SOURCE_TOUCHPAD = 1048584;
    public static final int SOURCE_TOUCHSCREEN = 4098;
    public static final int SOURCE_TOUCH_NAVIGATION = 2097152;
    public static final int SOURCE_TRACKBALL = 65540;
    public static final int SOURCE_UNKNOWN = 0;
    private final int mControllerNumber;
    private final String mDescriptor;
    private final int mGeneration;
    private final boolean mHasButtonUnderPad;
    private final boolean mHasMicrophone;
    private final boolean mHasVibrator;
    private final int mId;
    private final InputDeviceIdentifier mIdentifier;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private final boolean mIsExternal;
    private final KeyCharacterMap mKeyCharacterMap;
    private final int mKeyboardType;
    private final ArrayList<MotionRange> mMotionRanges = new ArrayList();
    private final String mName;
    private final int mProductId;
    private final int mSources;
    private final int mVendorId;
    private Vibrator mVibrator;

    @UnsupportedAppUsage
    private InputDevice(int n, int n2, int n3, String string2, int n4, int n5, String string3, boolean bl, int n6, int n7, KeyCharacterMap keyCharacterMap, boolean bl2, boolean bl3, boolean bl4) {
        this.mId = n;
        this.mGeneration = n2;
        this.mControllerNumber = n3;
        this.mName = string2;
        this.mVendorId = n4;
        this.mProductId = n5;
        this.mDescriptor = string3;
        this.mIsExternal = bl;
        this.mSources = n6;
        this.mKeyboardType = n7;
        this.mKeyCharacterMap = keyCharacterMap;
        this.mHasVibrator = bl2;
        this.mHasMicrophone = bl3;
        this.mHasButtonUnderPad = bl4;
        this.mIdentifier = new InputDeviceIdentifier(string3, n4, n5);
    }

    private InputDevice(Parcel parcel) {
        int n;
        this.mId = parcel.readInt();
        this.mGeneration = parcel.readInt();
        this.mControllerNumber = parcel.readInt();
        this.mName = parcel.readString();
        this.mVendorId = parcel.readInt();
        this.mProductId = parcel.readInt();
        this.mDescriptor = parcel.readString();
        int n2 = parcel.readInt();
        boolean bl = false;
        boolean bl2 = n2 != 0;
        this.mIsExternal = bl2;
        this.mSources = parcel.readInt();
        this.mKeyboardType = parcel.readInt();
        this.mKeyCharacterMap = KeyCharacterMap.CREATOR.createFromParcel(parcel);
        bl2 = parcel.readInt() != 0;
        this.mHasVibrator = bl2;
        bl2 = parcel.readInt() != 0;
        this.mHasMicrophone = bl2;
        bl2 = bl;
        if (parcel.readInt() != 0) {
            bl2 = true;
        }
        this.mHasButtonUnderPad = bl2;
        this.mIdentifier = new InputDeviceIdentifier(this.mDescriptor, this.mVendorId, this.mProductId);
        n2 = n = parcel.readInt();
        if (n > 1000) {
            n2 = 1000;
        }
        for (n = 0; n < n2; ++n) {
            this.addMotionRange(parcel.readInt(), parcel.readInt(), parcel.readFloat(), parcel.readFloat(), parcel.readFloat(), parcel.readFloat(), parcel.readFloat());
        }
    }

    @UnsupportedAppUsage
    private void addMotionRange(int n, int n2, float f, float f2, float f3, float f4, float f5) {
        this.mMotionRanges.add(new MotionRange(n, n2, f, f2, f3, f4, f5));
    }

    private void appendSourceDescriptionIfApplicable(StringBuilder stringBuilder, int n, String string2) {
        if ((this.mSources & n) == n) {
            stringBuilder.append(" ");
            stringBuilder.append(string2);
        }
    }

    public static InputDevice getDevice(int n) {
        return InputManager.getInstance().getInputDevice(n);
    }

    public static int[] getDeviceIds() {
        return InputManager.getInstance().getInputDeviceIds();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void disable() {
        InputManager.getInstance().disableInputDevice(this.mId);
    }

    public void enable() {
        InputManager.getInstance().enableInputDevice(this.mId);
    }

    public int getControllerNumber() {
        return this.mControllerNumber;
    }

    public String getDescriptor() {
        return this.mDescriptor;
    }

    public int getGeneration() {
        return this.mGeneration;
    }

    public int getId() {
        return this.mId;
    }

    public InputDeviceIdentifier getIdentifier() {
        return this.mIdentifier;
    }

    public KeyCharacterMap getKeyCharacterMap() {
        return this.mKeyCharacterMap;
    }

    public int getKeyboardType() {
        return this.mKeyboardType;
    }

    public MotionRange getMotionRange(int n) {
        int n2 = this.mMotionRanges.size();
        for (int i = 0; i < n2; ++i) {
            MotionRange motionRange = this.mMotionRanges.get(i);
            if (motionRange.mAxis != n) continue;
            return motionRange;
        }
        return null;
    }

    public MotionRange getMotionRange(int n, int n2) {
        int n3 = this.mMotionRanges.size();
        for (int i = 0; i < n3; ++i) {
            MotionRange motionRange = this.mMotionRanges.get(i);
            if (motionRange.mAxis != n || motionRange.mSource != n2) continue;
            return motionRange;
        }
        return null;
    }

    public List<MotionRange> getMotionRanges() {
        return this.mMotionRanges;
    }

    public String getName() {
        return this.mName;
    }

    public int getProductId() {
        return this.mProductId;
    }

    public int getSources() {
        return this.mSources;
    }

    public int getVendorId() {
        return this.mVendorId;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Vibrator getVibrator() {
        ArrayList<MotionRange> arrayList = this.mMotionRanges;
        synchronized (arrayList) {
            if (this.mVibrator != null) return this.mVibrator;
            this.mVibrator = this.mHasVibrator ? InputManager.getInstance().getInputDeviceVibrator(this.mId) : NullVibrator.getInstance();
            return this.mVibrator;
        }
    }

    public boolean hasButtonUnderPad() {
        return this.mHasButtonUnderPad;
    }

    public boolean[] hasKeys(int ... arrn) {
        return InputManager.getInstance().deviceHasKeys(this.mId, arrn);
    }

    public boolean hasMicrophone() {
        return this.mHasMicrophone;
    }

    public boolean isEnabled() {
        return InputManager.getInstance().isInputDeviceEnabled(this.mId);
    }

    public boolean isExternal() {
        return this.mIsExternal;
    }

    public boolean isFullKeyboard() {
        boolean bl = (this.mSources & 257) == 257 && this.mKeyboardType == 2;
        return bl;
    }

    public boolean isVirtual() {
        boolean bl = this.mId < 0;
        return bl;
    }

    public void setCustomPointerIcon(PointerIcon pointerIcon) {
        InputManager.getInstance().setCustomPointerIcon(pointerIcon);
    }

    public void setPointerType(int n) {
        InputManager.getInstance().setPointerIconType(n);
    }

    public boolean supportsSource(int n) {
        boolean bl = (this.mSources & n) == n;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Input Device ");
        stringBuilder.append(this.mId);
        stringBuilder.append(": ");
        stringBuilder.append(this.mName);
        stringBuilder.append("\n");
        stringBuilder.append("  Descriptor: ");
        stringBuilder.append(this.mDescriptor);
        stringBuilder.append("\n");
        stringBuilder.append("  Generation: ");
        stringBuilder.append(this.mGeneration);
        stringBuilder.append("\n");
        stringBuilder.append("  Location: ");
        Object object = this.mIsExternal ? "external" : "built-in";
        stringBuilder.append((String)object);
        stringBuilder.append("\n");
        stringBuilder.append("  Keyboard Type: ");
        int n = this.mKeyboardType;
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    stringBuilder.append("alphabetic");
                }
            } else {
                stringBuilder.append("non-alphabetic");
            }
        } else {
            stringBuilder.append("none");
        }
        stringBuilder.append("\n");
        stringBuilder.append("  Has Vibrator: ");
        stringBuilder.append(this.mHasVibrator);
        stringBuilder.append("\n");
        stringBuilder.append("  Has mic: ");
        stringBuilder.append(this.mHasMicrophone);
        stringBuilder.append("\n");
        stringBuilder.append("  Sources: 0x");
        stringBuilder.append(Integer.toHexString(this.mSources));
        stringBuilder.append(" (");
        this.appendSourceDescriptionIfApplicable(stringBuilder, 257, "keyboard");
        this.appendSourceDescriptionIfApplicable(stringBuilder, 513, "dpad");
        this.appendSourceDescriptionIfApplicable(stringBuilder, 4098, "touchscreen");
        this.appendSourceDescriptionIfApplicable(stringBuilder, 8194, "mouse");
        this.appendSourceDescriptionIfApplicable(stringBuilder, 16386, "stylus");
        this.appendSourceDescriptionIfApplicable(stringBuilder, 65540, "trackball");
        this.appendSourceDescriptionIfApplicable(stringBuilder, 131076, "mouse_relative");
        this.appendSourceDescriptionIfApplicable(stringBuilder, 1048584, "touchpad");
        this.appendSourceDescriptionIfApplicable(stringBuilder, 16777232, "joystick");
        this.appendSourceDescriptionIfApplicable(stringBuilder, 1025, "gamepad");
        stringBuilder.append(" )\n");
        int n2 = this.mMotionRanges.size();
        for (n = 0; n < n2; ++n) {
            object = this.mMotionRanges.get(n);
            stringBuilder.append("    ");
            stringBuilder.append(MotionEvent.axisToString(((MotionRange)object).mAxis));
            stringBuilder.append(": source=0x");
            stringBuilder.append(Integer.toHexString(((MotionRange)object).mSource));
            stringBuilder.append(" min=");
            stringBuilder.append(((MotionRange)object).mMin);
            stringBuilder.append(" max=");
            stringBuilder.append(((MotionRange)object).mMax);
            stringBuilder.append(" flat=");
            stringBuilder.append(((MotionRange)object).mFlat);
            stringBuilder.append(" fuzz=");
            stringBuilder.append(((MotionRange)object).mFuzz);
            stringBuilder.append(" resolution=");
            stringBuilder.append(((MotionRange)object).mResolution);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mGeneration);
        parcel.writeInt(this.mControllerNumber);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mVendorId);
        parcel.writeInt(this.mProductId);
        parcel.writeString(this.mDescriptor);
        parcel.writeInt((int)this.mIsExternal);
        parcel.writeInt(this.mSources);
        parcel.writeInt(this.mKeyboardType);
        this.mKeyCharacterMap.writeToParcel(parcel, n);
        parcel.writeInt((int)this.mHasVibrator);
        parcel.writeInt((int)this.mHasMicrophone);
        parcel.writeInt((int)this.mHasButtonUnderPad);
        int n2 = this.mMotionRanges.size();
        parcel.writeInt(n2);
        for (n = 0; n < n2; ++n) {
            MotionRange motionRange = this.mMotionRanges.get(n);
            parcel.writeInt(motionRange.mAxis);
            parcel.writeInt(motionRange.mSource);
            parcel.writeFloat(motionRange.mMin);
            parcel.writeFloat(motionRange.mMax);
            parcel.writeFloat(motionRange.mFlat);
            parcel.writeFloat(motionRange.mFuzz);
            parcel.writeFloat(motionRange.mResolution);
        }
    }

    public static final class MotionRange {
        private int mAxis;
        private float mFlat;
        private float mFuzz;
        private float mMax;
        private float mMin;
        private float mResolution;
        private int mSource;

        private MotionRange(int n, int n2, float f, float f2, float f3, float f4, float f5) {
            this.mAxis = n;
            this.mSource = n2;
            this.mMin = f;
            this.mMax = f2;
            this.mFlat = f3;
            this.mFuzz = f4;
            this.mResolution = f5;
        }

        public int getAxis() {
            return this.mAxis;
        }

        public float getFlat() {
            return this.mFlat;
        }

        public float getFuzz() {
            return this.mFuzz;
        }

        public float getMax() {
            return this.mMax;
        }

        public float getMin() {
            return this.mMin;
        }

        public float getRange() {
            return this.mMax - this.mMin;
        }

        public float getResolution() {
            return this.mResolution;
        }

        public int getSource() {
            return this.mSource;
        }

        public boolean isFromSource(int n) {
            boolean bl = (this.getSource() & n) == n;
            return bl;
        }
    }

}

