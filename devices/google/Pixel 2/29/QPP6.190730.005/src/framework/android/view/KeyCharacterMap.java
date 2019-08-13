/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.hardware.input.InputManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AndroidRuntimeException;
import android.util.SparseIntArray;
import android.view.InputDevice;
import android.view.KeyEvent;
import java.text.Normalizer;

public class KeyCharacterMap
implements Parcelable {
    private static final int ACCENT_ACUTE = 180;
    private static final int ACCENT_BREVE = 728;
    private static final int ACCENT_CARON = 711;
    private static final int ACCENT_CEDILLA = 184;
    private static final int ACCENT_CIRCUMFLEX = 710;
    private static final int ACCENT_CIRCUMFLEX_LEGACY = 94;
    private static final int ACCENT_COMMA_ABOVE = 8125;
    private static final int ACCENT_COMMA_ABOVE_RIGHT = 700;
    private static final int ACCENT_DOT_ABOVE = 729;
    private static final int ACCENT_DOT_BELOW = 46;
    private static final int ACCENT_DOUBLE_ACUTE = 733;
    private static final int ACCENT_GRAVE = 715;
    private static final int ACCENT_GRAVE_LEGACY = 96;
    private static final int ACCENT_HOOK_ABOVE = 704;
    private static final int ACCENT_HORN = 39;
    private static final int ACCENT_MACRON = 175;
    private static final int ACCENT_MACRON_BELOW = 717;
    private static final int ACCENT_OGONEK = 731;
    private static final int ACCENT_REVERSED_COMMA_ABOVE = 701;
    private static final int ACCENT_RING_ABOVE = 730;
    private static final int ACCENT_STROKE = 45;
    private static final int ACCENT_TILDE = 732;
    private static final int ACCENT_TILDE_LEGACY = 126;
    private static final int ACCENT_TURNED_COMMA_ABOVE = 699;
    private static final int ACCENT_UMLAUT = 168;
    private static final int ACCENT_VERTICAL_LINE_ABOVE = 712;
    private static final int ACCENT_VERTICAL_LINE_BELOW = 716;
    public static final int ALPHA = 3;
    @Deprecated
    public static final int BUILT_IN_KEYBOARD = 0;
    private static final int CHAR_SPACE = 32;
    public static final int COMBINING_ACCENT = Integer.MIN_VALUE;
    public static final int COMBINING_ACCENT_MASK = Integer.MAX_VALUE;
    public static final Parcelable.Creator<KeyCharacterMap> CREATOR;
    public static final int FULL = 4;
    public static final char HEX_INPUT = '\uef00';
    public static final int MODIFIER_BEHAVIOR_CHORDED = 0;
    public static final int MODIFIER_BEHAVIOR_CHORDED_OR_TOGGLED = 1;
    public static final int NUMERIC = 1;
    public static final char PICKER_DIALOG_INPUT = '\uef01';
    public static final int PREDICTIVE = 2;
    public static final int SPECIAL_FUNCTION = 5;
    public static final int VIRTUAL_KEYBOARD = -1;
    private static final SparseIntArray sAccentToCombining;
    private static final SparseIntArray sCombiningToAccent;
    private static final StringBuilder sDeadKeyBuilder;
    private static final SparseIntArray sDeadKeyCache;
    private long mPtr;

    static {
        sCombiningToAccent = new SparseIntArray();
        sAccentToCombining = new SparseIntArray();
        KeyCharacterMap.addCombining(768, 715);
        KeyCharacterMap.addCombining(769, 180);
        KeyCharacterMap.addCombining(770, 710);
        KeyCharacterMap.addCombining(771, 732);
        KeyCharacterMap.addCombining(772, 175);
        KeyCharacterMap.addCombining(774, 728);
        KeyCharacterMap.addCombining(775, 729);
        KeyCharacterMap.addCombining(776, 168);
        KeyCharacterMap.addCombining(777, 704);
        KeyCharacterMap.addCombining(778, 730);
        KeyCharacterMap.addCombining(779, 733);
        KeyCharacterMap.addCombining(780, 711);
        KeyCharacterMap.addCombining(781, 712);
        KeyCharacterMap.addCombining(786, 699);
        KeyCharacterMap.addCombining(787, 8125);
        KeyCharacterMap.addCombining(788, 701);
        KeyCharacterMap.addCombining(789, 700);
        KeyCharacterMap.addCombining(795, 39);
        KeyCharacterMap.addCombining(803, 46);
        KeyCharacterMap.addCombining(807, 184);
        KeyCharacterMap.addCombining(808, 731);
        KeyCharacterMap.addCombining(809, 716);
        KeyCharacterMap.addCombining(817, 717);
        KeyCharacterMap.addCombining(821, 45);
        sCombiningToAccent.append(832, 715);
        sCombiningToAccent.append(833, 180);
        sCombiningToAccent.append(835, 8125);
        sAccentToCombining.append(96, 768);
        sAccentToCombining.append(94, 770);
        sAccentToCombining.append(126, 771);
        sDeadKeyCache = new SparseIntArray();
        sDeadKeyBuilder = new StringBuilder();
        KeyCharacterMap.addDeadKey(45, 68, 272);
        KeyCharacterMap.addDeadKey(45, 71, 484);
        KeyCharacterMap.addDeadKey(45, 72, 294);
        KeyCharacterMap.addDeadKey(45, 73, 407);
        KeyCharacterMap.addDeadKey(45, 76, 321);
        KeyCharacterMap.addDeadKey(45, 79, 216);
        KeyCharacterMap.addDeadKey(45, 84, 358);
        KeyCharacterMap.addDeadKey(45, 100, 273);
        KeyCharacterMap.addDeadKey(45, 103, 485);
        KeyCharacterMap.addDeadKey(45, 104, 295);
        KeyCharacterMap.addDeadKey(45, 105, 616);
        KeyCharacterMap.addDeadKey(45, 108, 322);
        KeyCharacterMap.addDeadKey(45, 111, 248);
        KeyCharacterMap.addDeadKey(45, 116, 359);
        CREATOR = new Parcelable.Creator<KeyCharacterMap>(){

            @Override
            public KeyCharacterMap createFromParcel(Parcel parcel) {
                return new KeyCharacterMap(parcel);
            }

            public KeyCharacterMap[] newArray(int n) {
                return new KeyCharacterMap[n];
            }
        };
    }

    @UnsupportedAppUsage
    private KeyCharacterMap(long l) {
        this.mPtr = l;
    }

    private KeyCharacterMap(Parcel parcel) {
        if (parcel != null) {
            this.mPtr = KeyCharacterMap.nativeReadFromParcel(parcel);
            if (this.mPtr != 0L) {
                return;
            }
            throw new RuntimeException("Could not read KeyCharacterMap from parcel.");
        }
        throw new IllegalArgumentException("parcel must not be null");
    }

    private static void addCombining(int n, int n2) {
        sCombiningToAccent.append(n, n2);
        sAccentToCombining.append(n2, n);
    }

    private static void addDeadKey(int n, int n2, int n3) {
        if ((n = sAccentToCombining.get(n)) != 0) {
            sDeadKeyCache.put(n << 16 | n2, n3);
            return;
        }
        throw new IllegalStateException("Invalid dead key declaration.");
    }

    public static boolean deviceHasKey(int n) {
        return InputManager.getInstance().deviceHasKeys(new int[]{n})[0];
    }

    public static boolean[] deviceHasKeys(int[] arrn) {
        return InputManager.getInstance().deviceHasKeys(arrn);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int getDeadChar(int n, int n2) {
        if (n2 == n) return n;
        if (32 == n2) {
            return n;
        }
        int n3 = sAccentToCombining.get(n);
        int n4 = 0;
        if (n3 == 0) {
            return 0;
        }
        int n5 = n3 << 16 | n2;
        SparseIntArray sparseIntArray = sDeadKeyCache;
        synchronized (sparseIntArray) {
            int n6;
            n = n6 = sDeadKeyCache.get(n5, -1);
            if (n6 != -1) return n;
            sDeadKeyBuilder.setLength(0);
            sDeadKeyBuilder.append((char)n2);
            sDeadKeyBuilder.append((char)n3);
            String string2 = Normalizer.normalize(sDeadKeyBuilder, Normalizer.Form.NFC);
            n = string2.codePointCount(0, string2.length()) == 1 ? string2.codePointAt(0) : n4;
            sDeadKeyCache.put(n5, n);
            return n;
        }
    }

    public static KeyCharacterMap load(int n) {
        InputManager inputManager = InputManager.getInstance();
        InputDevice inputDevice = inputManager.getInputDevice(n);
        Object object = inputDevice;
        if (inputDevice == null && (object = inputManager.getInputDevice(-1)) == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Could not load key character map for device ");
            ((StringBuilder)object).append(n);
            throw new UnavailableException(((StringBuilder)object).toString());
        }
        return ((InputDevice)object).getKeyCharacterMap();
    }

    private static native void nativeDispose(long var0);

    private static native char nativeGetCharacter(long var0, int var2, int var3);

    private static native char nativeGetDisplayLabel(long var0, int var2);

    private static native KeyEvent[] nativeGetEvents(long var0, char[] var2);

    private static native boolean nativeGetFallbackAction(long var0, int var2, int var3, FallbackAction var4);

    private static native int nativeGetKeyboardType(long var0);

    private static native char nativeGetMatch(long var0, int var2, char[] var3, int var4);

    private static native char nativeGetNumber(long var0, int var2);

    private static native long nativeReadFromParcel(Parcel var0);

    private static native void nativeWriteToParcel(long var0, Parcel var2);

    @Override
    public int describeContents() {
        return 0;
    }

    protected void finalize() throws Throwable {
        long l = this.mPtr;
        if (l != 0L) {
            KeyCharacterMap.nativeDispose(l);
            this.mPtr = 0L;
        }
    }

    public int get(int n, int n2) {
        n2 = KeyEvent.normalizeMetaState(n2);
        n2 = KeyCharacterMap.nativeGetCharacter(this.mPtr, n, n2);
        n = sCombiningToAccent.get(n2);
        if (n != 0) {
            return Integer.MIN_VALUE | n;
        }
        return n2;
    }

    public char getDisplayLabel(int n) {
        return KeyCharacterMap.nativeGetDisplayLabel(this.mPtr, n);
    }

    public KeyEvent[] getEvents(char[] arrc) {
        if (arrc != null) {
            return KeyCharacterMap.nativeGetEvents(this.mPtr, arrc);
        }
        throw new IllegalArgumentException("chars must not be null.");
    }

    public FallbackAction getFallbackAction(int n, int n2) {
        FallbackAction fallbackAction = FallbackAction.obtain();
        if (KeyCharacterMap.nativeGetFallbackAction(this.mPtr, n, n2 = KeyEvent.normalizeMetaState(n2), fallbackAction)) {
            fallbackAction.metaState = KeyEvent.normalizeMetaState(fallbackAction.metaState);
            return fallbackAction;
        }
        fallbackAction.recycle();
        return null;
    }

    @Deprecated
    public boolean getKeyData(int n, KeyData keyData) {
        if (keyData.meta.length >= 4) {
            char c = KeyCharacterMap.nativeGetDisplayLabel(this.mPtr, n);
            if (c == '\u0000') {
                return false;
            }
            keyData.displayLabel = c;
            keyData.number = KeyCharacterMap.nativeGetNumber(this.mPtr, n);
            keyData.meta[0] = KeyCharacterMap.nativeGetCharacter(this.mPtr, n, 0);
            keyData.meta[1] = KeyCharacterMap.nativeGetCharacter(this.mPtr, n, 1);
            keyData.meta[2] = KeyCharacterMap.nativeGetCharacter(this.mPtr, n, 2);
            keyData.meta[3] = KeyCharacterMap.nativeGetCharacter(this.mPtr, n, 3);
            return true;
        }
        throw new IndexOutOfBoundsException("results.meta.length must be >= 4");
    }

    public int getKeyboardType() {
        return KeyCharacterMap.nativeGetKeyboardType(this.mPtr);
    }

    public char getMatch(int n, char[] arrc) {
        return this.getMatch(n, arrc, 0);
    }

    public char getMatch(int n, char[] arrc, int n2) {
        if (arrc != null) {
            n2 = KeyEvent.normalizeMetaState(n2);
            return KeyCharacterMap.nativeGetMatch(this.mPtr, n, arrc, n2);
        }
        throw new IllegalArgumentException("chars must not be null.");
    }

    public int getModifierBehavior() {
        int n = this.getKeyboardType();
        return n != 4 && n != 5;
    }

    public char getNumber(int n) {
        return KeyCharacterMap.nativeGetNumber(this.mPtr, n);
    }

    public boolean isPrintingKey(int n) {
        switch (Character.getType(KeyCharacterMap.nativeGetDisplayLabel(this.mPtr, n))) {
            default: {
                return true;
            }
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: 
        }
        return false;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (parcel != null) {
            KeyCharacterMap.nativeWriteToParcel(this.mPtr, parcel);
            return;
        }
        throw new IllegalArgumentException("parcel must not be null");
    }

    public static final class FallbackAction {
        private static final int MAX_RECYCLED = 10;
        private static FallbackAction sRecycleBin;
        private static final Object sRecycleLock;
        private static int sRecycledCount;
        @UnsupportedAppUsage
        public int keyCode;
        @UnsupportedAppUsage
        public int metaState;
        private FallbackAction next;

        static {
            sRecycleLock = new Object();
        }

        private FallbackAction() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static FallbackAction obtain() {
            Object object = sRecycleLock;
            synchronized (object) {
                if (sRecycleBin == null) {
                    return new FallbackAction();
                }
                FallbackAction fallbackAction = sRecycleBin;
                sRecycleBin = fallbackAction.next;
                --sRecycledCount;
                fallbackAction.next = null;
                return fallbackAction;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void recycle() {
            Object object = sRecycleLock;
            synchronized (object) {
                if (sRecycledCount < 10) {
                    this.next = sRecycleBin;
                    sRecycleBin = this;
                    ++sRecycledCount;
                } else {
                    this.next = null;
                }
                return;
            }
        }
    }

    @Deprecated
    public static class KeyData {
        public static final int META_LENGTH = 4;
        public char displayLabel;
        public char[] meta = new char[4];
        public char number;
    }

    public static class UnavailableException
    extends AndroidRuntimeException {
        public UnavailableException(String string2) {
            super(string2);
        }
    }

}

