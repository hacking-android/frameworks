/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.MathUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public abstract class VibrationEffect
implements Parcelable {
    public static final Parcelable.Creator<VibrationEffect> CREATOR;
    public static final int DEFAULT_AMPLITUDE = -1;
    public static final int EFFECT_CLICK = 0;
    public static final int EFFECT_DOUBLE_CLICK = 1;
    public static final int EFFECT_HEAVY_CLICK = 5;
    public static final int EFFECT_POP = 4;
    public static final int EFFECT_STRENGTH_LIGHT = 0;
    public static final int EFFECT_STRENGTH_MEDIUM = 1;
    public static final int EFFECT_STRENGTH_STRONG = 2;
    public static final int EFFECT_TEXTURE_TICK = 21;
    public static final int EFFECT_THUD = 3;
    public static final int EFFECT_TICK = 2;
    public static final int MAX_AMPLITUDE = 255;
    private static final int PARCEL_TOKEN_EFFECT = 3;
    private static final int PARCEL_TOKEN_ONE_SHOT = 1;
    private static final int PARCEL_TOKEN_WAVEFORM = 2;
    public static final int[] RINGTONES;

    static {
        RINGTONES = new int[]{6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        CREATOR = new Parcelable.Creator<VibrationEffect>(){

            @Override
            public VibrationEffect createFromParcel(Parcel parcel) {
                int n = parcel.readInt();
                if (n == 1) {
                    return new OneShot(parcel);
                }
                if (n == 2) {
                    return new Waveform(parcel);
                }
                if (n == 3) {
                    return new Prebaked(parcel);
                }
                throw new IllegalStateException("Unexpected vibration event type token in parcel.");
            }

            public VibrationEffect[] newArray(int n) {
                return new VibrationEffect[n];
            }
        };
    }

    public static VibrationEffect createOneShot(long l, int n) {
        OneShot oneShot = new OneShot(l, n);
        ((VibrationEffect)oneShot).validate();
        return oneShot;
    }

    public static VibrationEffect createPredefined(int n) {
        return VibrationEffect.get(n, true);
    }

    public static VibrationEffect createWaveform(long[] arrl, int n) {
        int[] arrn = new int[arrl.length];
        for (int i = 0; i < arrl.length / 2; ++i) {
            arrn[i * 2 + 1] = -1;
        }
        return VibrationEffect.createWaveform(arrl, arrn, n);
    }

    public static VibrationEffect createWaveform(long[] object, int[] arrn, int n) {
        object = new Waveform((long[])object, arrn, n);
        ((VibrationEffect)object).validate();
        return object;
    }

    public static VibrationEffect get(int n) {
        return VibrationEffect.get(n, true);
    }

    public static VibrationEffect get(int n, boolean bl) {
        Prebaked prebaked = new Prebaked(n, bl);
        ((VibrationEffect)prebaked).validate();
        return prebaked;
    }

    public static VibrationEffect get(Uri arrstring, Context object) {
        Uri uri;
        ContentResolver contentResolver = ((Context)object).getContentResolver();
        Uri uri2 = uri = contentResolver.uncanonicalize((Uri)arrstring);
        if (uri == null) {
            uri2 = arrstring;
        }
        arrstring = ((Context)object).getResources().getStringArray(17236051);
        for (int i = 0; i < arrstring.length && i < RINGTONES.length; ++i) {
            if (arrstring[i] == null || (object = contentResolver.uncanonicalize(Uri.parse(arrstring[i]))) == null || !((Uri)object).equals(uri2)) continue;
            return VibrationEffect.get(RINGTONES[i]);
        }
        return null;
    }

    protected static int scale(int n, float f, int n2) {
        f = MathUtils.pow((float)n / 255.0f, f);
        return (int)((float)n2 * f);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public abstract long getDuration();

    public abstract void validate();

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EffectType {
    }

    public static class OneShot
    extends VibrationEffect
    implements Parcelable {
        public static final Parcelable.Creator<OneShot> CREATOR = new Parcelable.Creator<OneShot>(){

            @Override
            public OneShot createFromParcel(Parcel parcel) {
                parcel.readInt();
                return new OneShot(parcel);
            }

            public OneShot[] newArray(int n) {
                return new OneShot[n];
            }
        };
        private final int mAmplitude;
        private final long mDuration;

        public OneShot(long l, int n) {
            this.mDuration = l;
            this.mAmplitude = n;
        }

        public OneShot(Parcel parcel) {
            this.mDuration = parcel.readLong();
            this.mAmplitude = parcel.readInt();
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof OneShot;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            object = (OneShot)object;
            bl = bl2;
            if (((OneShot)object).mDuration == this.mDuration) {
                bl = bl2;
                if (((OneShot)object).mAmplitude == this.mAmplitude) {
                    bl = true;
                }
            }
            return bl;
        }

        public int getAmplitude() {
            return this.mAmplitude;
        }

        @Override
        public long getDuration() {
            return this.mDuration;
        }

        public int hashCode() {
            return 17 + (int)this.mDuration * 37 + this.mAmplitude * 37;
        }

        public OneShot resolve(int n) {
            if (n <= 255 && n >= 0) {
                if (this.mAmplitude == -1) {
                    return new OneShot(this.mDuration, n);
                }
                return this;
            }
            throw new IllegalArgumentException("Amplitude is negative or greater than MAX_AMPLITUDE");
        }

        public OneShot scale(float f, int n) {
            if (n <= 255 && n >= 0) {
                n = OneShot.scale(this.mAmplitude, f, n);
                return new OneShot(this.mDuration, n);
            }
            throw new IllegalArgumentException("Amplitude is negative or greater than MAX_AMPLITUDE");
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("OneShot{mDuration=");
            stringBuilder.append(this.mDuration);
            stringBuilder.append(", mAmplitude=");
            stringBuilder.append(this.mAmplitude);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void validate() {
            int n = this.mAmplitude;
            if (n >= -1 && n != 0 && n <= 255) {
                if (this.mDuration > 0L) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("duration must be positive (duration=");
                stringBuilder.append(this.mDuration);
                stringBuilder.append(")");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("amplitude must either be DEFAULT_AMPLITUDE, or between 1 and 255 inclusive (amplitude=");
            stringBuilder.append(this.mAmplitude);
            stringBuilder.append(")");
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(1);
            parcel.writeLong(this.mDuration);
            parcel.writeInt(this.mAmplitude);
        }

    }

    public static class Prebaked
    extends VibrationEffect
    implements Parcelable {
        public static final Parcelable.Creator<Prebaked> CREATOR = new Parcelable.Creator<Prebaked>(){

            @Override
            public Prebaked createFromParcel(Parcel parcel) {
                parcel.readInt();
                return new Prebaked(parcel);
            }

            public Prebaked[] newArray(int n) {
                return new Prebaked[n];
            }
        };
        private final int mEffectId;
        private int mEffectStrength;
        private final boolean mFallback;

        public Prebaked(int n, boolean bl) {
            this.mEffectId = n;
            this.mFallback = bl;
            this.mEffectStrength = 1;
        }

        public Prebaked(Parcel parcel) {
            int n = parcel.readInt();
            boolean bl = parcel.readByte() != 0;
            this(n, bl);
            this.mEffectStrength = parcel.readInt();
        }

        private static boolean isValidEffectStrength(int n) {
            return n == 0 || n == 1 || n == 2;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof Prebaked;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            object = (Prebaked)object;
            bl = bl2;
            if (this.mEffectId == ((Prebaked)object).mEffectId) {
                bl = bl2;
                if (this.mFallback == ((Prebaked)object).mFallback) {
                    bl = bl2;
                    if (this.mEffectStrength == ((Prebaked)object).mEffectStrength) {
                        bl = true;
                    }
                }
            }
            return bl;
        }

        @Override
        public long getDuration() {
            return -1L;
        }

        public int getEffectStrength() {
            return this.mEffectStrength;
        }

        public int getId() {
            return this.mEffectId;
        }

        public int hashCode() {
            return 17 + this.mEffectId * 37 + this.mEffectStrength * 37;
        }

        public void setEffectStrength(int n) {
            if (Prebaked.isValidEffectStrength(n)) {
                this.mEffectStrength = n;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid effect strength: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public boolean shouldFallback() {
            return this.mFallback;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Prebaked{mEffectId=");
            stringBuilder.append(this.mEffectId);
            stringBuilder.append(", mEffectStrength=");
            stringBuilder.append(this.mEffectStrength);
            stringBuilder.append(", mFallback=");
            stringBuilder.append(this.mFallback);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void validate() {
            int n = this.mEffectId;
            if (n != 0 && n != 1 && n != 2 && n != 3 && n != 4 && n != 5 && n != 21 && (n < RINGTONES[0] || this.mEffectId > RINGTONES[RINGTONES.length - 1])) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown prebaked effect type (value=");
                stringBuilder.append(this.mEffectId);
                stringBuilder.append(")");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            if (Prebaked.isValidEffectStrength(this.mEffectStrength)) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown prebaked effect strength (value=");
            stringBuilder.append(this.mEffectStrength);
            stringBuilder.append(")");
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(3);
            parcel.writeInt(this.mEffectId);
            parcel.writeByte((byte)(this.mFallback ? 1 : 0));
            parcel.writeInt(this.mEffectStrength);
        }

    }

    public static class Waveform
    extends VibrationEffect
    implements Parcelable {
        public static final Parcelable.Creator<Waveform> CREATOR = new Parcelable.Creator<Waveform>(){

            @Override
            public Waveform createFromParcel(Parcel parcel) {
                parcel.readInt();
                return new Waveform(parcel);
            }

            public Waveform[] newArray(int n) {
                return new Waveform[n];
            }
        };
        private final int[] mAmplitudes;
        private final int mRepeat;
        private final long[] mTimings;

        public Waveform(Parcel parcel) {
            this(parcel.createLongArray(), parcel.createIntArray(), parcel.readInt());
        }

        public Waveform(long[] arrl, int[] arrn, int n) {
            this.mTimings = new long[arrl.length];
            System.arraycopy(arrl, 0, this.mTimings, 0, arrl.length);
            this.mAmplitudes = new int[arrn.length];
            System.arraycopy(arrn, 0, this.mAmplitudes, 0, arrn.length);
            this.mRepeat = n;
        }

        private static boolean hasNonZeroEntry(long[] arrl) {
            int n = arrl.length;
            for (int i = 0; i < n; ++i) {
                if (arrl[i] == 0L) continue;
                return true;
            }
            return false;
        }

        public boolean equals(Object object) {
            boolean bl;
            block1 : {
                boolean bl2 = object instanceof Waveform;
                bl = false;
                if (!bl2) {
                    return false;
                }
                object = (Waveform)object;
                if (!Arrays.equals(this.mTimings, ((Waveform)object).mTimings) || !Arrays.equals(this.mAmplitudes, ((Waveform)object).mAmplitudes) || this.mRepeat != ((Waveform)object).mRepeat) break block1;
                bl = true;
            }
            return bl;
        }

        public int[] getAmplitudes() {
            return this.mAmplitudes;
        }

        @Override
        public long getDuration() {
            if (this.mRepeat >= 0) {
                return Long.MAX_VALUE;
            }
            long l = 0L;
            long[] arrl = this.mTimings;
            int n = arrl.length;
            for (int i = 0; i < n; ++i) {
                l += arrl[i];
            }
            return l;
        }

        public int getRepeatIndex() {
            return this.mRepeat;
        }

        public long[] getTimings() {
            return this.mTimings;
        }

        public int hashCode() {
            return 17 + Arrays.hashCode(this.mTimings) * 37 + Arrays.hashCode(this.mAmplitudes) * 37 + this.mRepeat * 37;
        }

        public Waveform resolve(int n) {
            if (n <= 255 && n >= 0) {
                int[] arrn = this.mAmplitudes;
                arrn = Arrays.copyOf(arrn, arrn.length);
                for (int i = 0; i < arrn.length; ++i) {
                    if (arrn[i] != -1) continue;
                    arrn[i] = n;
                }
                return new Waveform(this.mTimings, arrn, this.mRepeat);
            }
            throw new IllegalArgumentException("Amplitude is negative or greater than MAX_AMPLITUDE");
        }

        public Waveform scale(float f, int n) {
            if (n <= 255 && n >= 0) {
                if (f == 1.0f && n == 255) {
                    return new Waveform(this.mTimings, this.mAmplitudes, this.mRepeat);
                }
                int[] arrn = this.mAmplitudes;
                arrn = Arrays.copyOf(arrn, arrn.length);
                for (int i = 0; i < arrn.length; ++i) {
                    arrn[i] = Waveform.scale(arrn[i], f, n);
                }
                return new Waveform(this.mTimings, arrn, this.mRepeat);
            }
            throw new IllegalArgumentException("Amplitude is negative or greater than MAX_AMPLITUDE");
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Waveform{mTimings=");
            stringBuilder.append(Arrays.toString(this.mTimings));
            stringBuilder.append(", mAmplitudes=");
            stringBuilder.append(Arrays.toString(this.mAmplitudes));
            stringBuilder.append(", mRepeat=");
            stringBuilder.append(this.mRepeat);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void validate() {
            Object object = this.mTimings;
            if (((long[])object).length == this.mAmplitudes.length) {
                if (Waveform.hasNonZeroEntry((long[])object)) {
                    int n;
                    object = this.mTimings;
                    int n2 = ((Object)object).length;
                    Object object2 = 0;
                    for (n = 0; n < n2; ++n) {
                        if (object[n] >= 0L) {
                            continue;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("timings must all be >= 0 (timings=");
                        ((StringBuilder)object).append(Arrays.toString(this.mTimings));
                        ((StringBuilder)object).append(")");
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    object = this.mAmplitudes;
                    n2 = ((Object)object).length;
                    for (n = object2; n < n2; ++n) {
                        object2 = object[n];
                        if (object2 >= -1 && object2 <= 255) {
                            continue;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("amplitudes must all be DEFAULT_AMPLITUDE or between 0 and 255 (amplitudes=");
                        ((StringBuilder)object).append(Arrays.toString(this.mAmplitudes));
                        ((StringBuilder)object).append(")");
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    n = this.mRepeat;
                    if (n >= -1 && n < this.mTimings.length) {
                        return;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("repeat index must be within the bounds of the timings array (timings.length=");
                    ((StringBuilder)object).append(this.mTimings.length);
                    ((StringBuilder)object).append(", index=");
                    ((StringBuilder)object).append(this.mRepeat);
                    ((StringBuilder)object).append(")");
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("at least one timing must be non-zero (timings=");
                ((StringBuilder)object).append(Arrays.toString(this.mTimings));
                ((StringBuilder)object).append(")");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("timing and amplitude arrays must be of equal length (timings.length=");
            ((StringBuilder)object).append(this.mTimings.length);
            ((StringBuilder)object).append(", amplitudes.length=");
            ((StringBuilder)object).append(this.mAmplitudes.length);
            ((StringBuilder)object).append(")");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(2);
            parcel.writeLongArray(this.mTimings);
            parcel.writeIntArray(this.mAmplitudes);
            parcel.writeInt(this.mRepeat);
        }

    }

}

