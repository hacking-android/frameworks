/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.PlayerBase;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Objects;

public final class VolumeShaper
implements AutoCloseable {
    private int mId;
    private final WeakReference<PlayerBase> mWeakPlayerBase;

    VolumeShaper(Configuration configuration, PlayerBase playerBase) {
        this.mWeakPlayerBase = new WeakReference<PlayerBase>(playerBase);
        this.mId = this.applyPlayer(configuration, new Operation.Builder().defer().build());
    }

    private int applyPlayer(Configuration object, Operation operation) {
        WeakReference<PlayerBase> weakReference = this.mWeakPlayerBase;
        if (weakReference != null) {
            if ((weakReference = (PlayerBase)weakReference.get()) != null) {
                int n = ((PlayerBase)((Object)weakReference)).playerApplyVolumeShaper((Configuration)object, operation);
                if (n < 0) {
                    if (n == -38) {
                        throw new IllegalStateException("player or VolumeShaper deallocated");
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("invalid configuration or operation: ");
                    ((StringBuilder)object).append(n);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                return n;
            }
            throw new IllegalStateException("player deallocated");
        }
        throw new IllegalStateException("uninitialized shaper");
    }

    private State getStatePlayer(int n) {
        WeakReference<PlayerBase> weakReference = this.mWeakPlayerBase;
        if (weakReference != null) {
            if ((weakReference = (PlayerBase)weakReference.get()) != null) {
                if ((weakReference = ((PlayerBase)((Object)weakReference)).playerGetVolumeShaperState(n)) != null) {
                    return weakReference;
                }
                throw new IllegalStateException("shaper cannot be found");
            }
            throw new IllegalStateException("player deallocated");
        }
        throw new IllegalStateException("uninitialized shaper");
    }

    public void apply(Operation operation) {
        this.applyPlayer(new Configuration(this.mId), operation);
    }

    @Override
    public void close() {
        Object object;
        try {
            object = new Configuration(this.mId);
            Operation.Builder builder = new Operation.Builder();
            this.applyPlayer((Configuration)object, builder.terminate().build());
        }
        catch (IllegalStateException illegalStateException) {
            // empty catch block
        }
        object = this.mWeakPlayerBase;
        if (object != null) {
            ((Reference)object).clear();
        }
    }

    protected void finalize() {
        this.close();
    }

    int getId() {
        return this.mId;
    }

    public float getVolume() {
        return this.getStatePlayer(this.mId).getVolume();
    }

    public void replace(Configuration configuration, Operation operation, boolean bl) {
        this.mId = this.applyPlayer(configuration, new Operation.Builder(operation).replace(this.mId, bl).build());
    }

    public static final class Configuration
    implements Parcelable {
        public static final Parcelable.Creator<Configuration> CREATOR;
        public static final Configuration CUBIC_RAMP;
        public static final int INTERPOLATOR_TYPE_CUBIC = 2;
        public static final int INTERPOLATOR_TYPE_CUBIC_MONOTONIC = 3;
        public static final int INTERPOLATOR_TYPE_LINEAR = 1;
        public static final int INTERPOLATOR_TYPE_STEP = 0;
        public static final Configuration LINEAR_RAMP;
        private static final int MAXIMUM_CURVE_POINTS = 16;
        public static final int OPTION_FLAG_CLOCK_TIME = 2;
        private static final int OPTION_FLAG_PUBLIC_ALL = 3;
        public static final int OPTION_FLAG_VOLUME_IN_DBFS = 1;
        public static final Configuration SCURVE_RAMP;
        public static final Configuration SINE_RAMP;
        static final int TYPE_ID = 0;
        static final int TYPE_SCALE = 1;
        @UnsupportedAppUsage
        private final double mDurationMs;
        @UnsupportedAppUsage
        private final int mId;
        @UnsupportedAppUsage
        private final int mInterpolatorType;
        @UnsupportedAppUsage
        private final int mOptionFlags;
        @UnsupportedAppUsage
        private final float[] mTimes;
        @UnsupportedAppUsage
        private final int mType;
        @UnsupportedAppUsage
        private final float[] mVolumes;

        static {
            LINEAR_RAMP = new Builder().setInterpolatorType(1).setCurve(new float[]{0.0f, 1.0f}, new float[]{0.0f, 1.0f}).setDuration(1000L).build();
            CUBIC_RAMP = new Builder().setInterpolatorType(2).setCurve(new float[]{0.0f, 1.0f}, new float[]{0.0f, 1.0f}).setDuration(1000L).build();
            float[] arrf = new float[16];
            float[] arrf2 = new float[16];
            float[] arrf3 = new float[16];
            for (int i = 0; i < 16; ++i) {
                float f;
                arrf[i] = (float)i / 15.0f;
                arrf2[i] = f = (float)Math.sin((double)arrf[i] * 3.141592653589793 / 2.0);
                arrf3[i] = f * f;
            }
            SINE_RAMP = new Builder().setInterpolatorType(2).setCurve(arrf, arrf2).setDuration(1000L).build();
            SCURVE_RAMP = new Builder().setInterpolatorType(2).setCurve(arrf, arrf3).setDuration(1000L).build();
            CREATOR = new Parcelable.Creator<Configuration>(){

                @Override
                public Configuration createFromParcel(Parcel parcel) {
                    int n = parcel.readInt();
                    int n2 = parcel.readInt();
                    if (n == 0) {
                        return new Configuration(n2);
                    }
                    int n3 = parcel.readInt();
                    double d = parcel.readDouble();
                    int n4 = parcel.readInt();
                    parcel.readFloat();
                    parcel.readFloat();
                    int n5 = parcel.readInt();
                    float[] arrf = new float[n5];
                    float[] arrf2 = new float[n5];
                    for (int i = 0; i < n5; ++i) {
                        arrf[i] = parcel.readFloat();
                        arrf2[i] = parcel.readFloat();
                    }
                    return new Configuration(n, n2, n3, d, n4, arrf, arrf2);
                }

                public Configuration[] newArray(int n) {
                    return new Configuration[n];
                }
            };
        }

        public Configuration(int n) {
            if (n >= 0) {
                this.mType = 0;
                this.mId = n;
                this.mInterpolatorType = 0;
                this.mOptionFlags = 0;
                this.mDurationMs = 0.0;
                this.mTimes = null;
                this.mVolumes = null;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("negative id ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        @UnsupportedAppUsage
        private Configuration(int n, int n2, int n3, double d, int n4, float[] arrf, float[] arrf2) {
            this.mType = n;
            this.mId = n2;
            this.mOptionFlags = n3;
            this.mDurationMs = d;
            this.mInterpolatorType = n4;
            this.mTimes = arrf;
            this.mVolumes = arrf2;
        }

        private static String checkCurveForErrors(float[] object, float[] arrf, boolean bl) {
            int n;
            if (object == null) {
                return "times array must be non-null";
            }
            if (arrf == null) {
                return "volumes array must be non-null";
            }
            if (((float[])object).length != arrf.length) {
                return "array length must match";
            }
            if (((float[])object).length < 2) {
                return "array length must be at least 2";
            }
            if (((float[])object).length > 16) {
                return "array length must be no larger than 16";
            }
            if (object[0] != 0.0f) {
                return "times must start at 0.f";
            }
            if (object[((float[])object).length - 1] != 1.0f) {
                return "times must end at 1.f";
            }
            for (n = 1; n < ((float[])object).length; ++n) {
                if (object[n] > object[n - 1]) continue;
                object = new StringBuilder();
                ((StringBuilder)object).append("times not monotonic increasing, check index ");
                ((StringBuilder)object).append(n);
                return ((StringBuilder)object).toString();
            }
            if (bl) {
                for (n = 0; n < arrf.length; ++n) {
                    if (arrf[n] <= 0.0f) continue;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("volumes for log scale cannot be positive, check index ");
                    ((StringBuilder)object).append(n);
                    return ((StringBuilder)object).toString();
                }
            } else {
                for (n = 0; n < arrf.length; ++n) {
                    if (arrf[n] >= 0.0f && arrf[n] <= 1.0f) {
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("volumes for linear scale must be between 0.f and 1.f, check index ");
                    ((StringBuilder)object).append(n);
                    return ((StringBuilder)object).toString();
                }
            }
            return null;
        }

        private static void checkCurveForErrorsAndThrowException(float[] object, float[] arrf, boolean bl, boolean bl2) {
            if ((object = Configuration.checkCurveForErrors(object, arrf, bl)) != null) {
                if (bl2) {
                    throw new IllegalStateException((String)object);
                }
                throw new IllegalArgumentException((String)object);
            }
        }

        private static void checkValidVolumeAndThrowException(float f, boolean bl) {
            block7 : {
                block6 : {
                    block5 : {
                        if (!bl) break block5;
                        if (!(f <= 0.0f)) {
                            throw new IllegalArgumentException("dbfs volume must be 0.f or less");
                        }
                        break block6;
                    }
                    if (!(f >= 0.0f) || !(f <= 1.0f)) break block7;
                }
                return;
            }
            throw new IllegalArgumentException("volume must be >= 0.f and <= 1.f");
        }

        private static void clampVolume(float[] arrf, boolean bl) {
            if (bl) {
                for (int i = 0; i < arrf.length; ++i) {
                    if (arrf[i] <= 0.0f) continue;
                    arrf[i] = 0.0f;
                }
            } else {
                for (int i = 0; i < arrf.length; ++i) {
                    if (!(arrf[i] >= 0.0f)) {
                        arrf[i] = 0.0f;
                        continue;
                    }
                    if (arrf[i] <= 1.0f) continue;
                    arrf[i] = 1.0f;
                }
            }
        }

        public static int getMaximumCurvePoints() {
            return 16;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (!(object instanceof Configuration)) {
                return false;
            }
            boolean bl = true;
            if (object == this) {
                return true;
            }
            object = (Configuration)object;
            int n = this.mType;
            if (n != ((Configuration)object).mType || this.mId != ((Configuration)object).mId || n != 0 && (this.mOptionFlags != ((Configuration)object).mOptionFlags || this.mDurationMs != ((Configuration)object).mDurationMs || this.mInterpolatorType != ((Configuration)object).mInterpolatorType || !Arrays.equals(this.mTimes, ((Configuration)object).mTimes) || !Arrays.equals(this.mVolumes, ((Configuration)object).mVolumes))) {
                bl = false;
            }
            return bl;
        }

        int getAllOptionFlags() {
            return this.mOptionFlags;
        }

        public long getDuration() {
            return (long)this.mDurationMs;
        }

        public int getId() {
            return this.mId;
        }

        public int getInterpolatorType() {
            return this.mInterpolatorType;
        }

        public int getOptionFlags() {
            return this.mOptionFlags & 3;
        }

        public float[] getTimes() {
            return this.mTimes;
        }

        public int getType() {
            return this.mType;
        }

        public float[] getVolumes() {
            return this.mVolumes;
        }

        public int hashCode() {
            int n = this.mType;
            n = n == 0 ? Objects.hash(n, this.mId) : Objects.hash(n, this.mId, this.mOptionFlags, this.mDurationMs, this.mInterpolatorType, Arrays.hashCode(this.mTimes), Arrays.hashCode(this.mVolumes));
            return n;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("VolumeShaper.Configuration{mType = ");
            stringBuilder.append(this.mType);
            stringBuilder.append(", mId = ");
            stringBuilder.append(this.mId);
            int n = this.mType;
            CharSequence charSequence = "}";
            if (n != 0) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(", mOptionFlags = 0x");
                ((StringBuilder)charSequence).append(Integer.toHexString(this.mOptionFlags).toUpperCase());
                ((StringBuilder)charSequence).append(", mDurationMs = ");
                ((StringBuilder)charSequence).append(this.mDurationMs);
                ((StringBuilder)charSequence).append(", mInterpolatorType = ");
                ((StringBuilder)charSequence).append(this.mInterpolatorType);
                ((StringBuilder)charSequence).append(", mTimes[] = ");
                ((StringBuilder)charSequence).append(Arrays.toString(this.mTimes));
                ((StringBuilder)charSequence).append(", mVolumes[] = ");
                ((StringBuilder)charSequence).append(Arrays.toString(this.mVolumes));
                ((StringBuilder)charSequence).append("}");
                charSequence = ((StringBuilder)charSequence).toString();
            }
            stringBuilder.append((String)charSequence);
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mType);
            parcel.writeInt(this.mId);
            if (this.mType != 0) {
                float[] arrf;
                parcel.writeInt(this.mOptionFlags);
                parcel.writeDouble(this.mDurationMs);
                parcel.writeInt(this.mInterpolatorType);
                parcel.writeFloat(0.0f);
                parcel.writeFloat(0.0f);
                parcel.writeInt(this.mTimes.length);
                for (n = 0; n < (arrf = this.mTimes).length; ++n) {
                    parcel.writeFloat(arrf[n]);
                    parcel.writeFloat(this.mVolumes[n]);
                }
            }
        }

        public static final class Builder {
            private double mDurationMs = 1000.0;
            private int mId = -1;
            private int mInterpolatorType = 2;
            private int mOptionFlags = 2;
            private float[] mTimes = null;
            private int mType = 1;
            private float[] mVolumes = null;

            public Builder() {
            }

            public Builder(Configuration configuration) {
                this.mType = configuration.getType();
                this.mId = configuration.getId();
                this.mOptionFlags = configuration.getAllOptionFlags();
                this.mInterpolatorType = configuration.getInterpolatorType();
                this.mDurationMs = configuration.getDuration();
                this.mTimes = (float[])configuration.getTimes().clone();
                this.mVolumes = (float[])configuration.getVolumes().clone();
            }

            public Configuration build() {
                boolean bl = (this.mOptionFlags & 1) != 0;
                Configuration.checkCurveForErrorsAndThrowException(this.mTimes, this.mVolumes, bl, true);
                return new Configuration(this.mType, this.mId, this.mOptionFlags, this.mDurationMs, this.mInterpolatorType, this.mTimes, this.mVolumes);
            }

            public Builder invertVolumes() {
                int n;
                boolean bl = (this.mOptionFlags & 1) != 0;
                Configuration.checkCurveForErrorsAndThrowException(this.mTimes, this.mVolumes, bl, true);
                float[] arrf = this.mVolumes;
                float f = arrf[0];
                float f2 = arrf[0];
                for (n = 1; n < (arrf = this.mVolumes).length; ++n) {
                    float f3;
                    float f4;
                    if (arrf[n] < f) {
                        f4 = arrf[n];
                        f3 = f2;
                    } else {
                        f3 = f2;
                        f4 = f;
                        if (arrf[n] > f2) {
                            f3 = arrf[n];
                            f4 = f;
                        }
                    }
                    f2 = f3;
                    f = f4;
                }
                for (n = 0; n < (arrf = this.mVolumes).length; ++n) {
                    arrf[n] = f2 + f - arrf[n];
                }
                return this;
            }

            public Builder reflectTimes() {
                int n;
                float[] arrf;
                boolean bl = (this.mOptionFlags & 1) != 0;
                Configuration.checkCurveForErrorsAndThrowException(this.mTimes, this.mVolumes, bl, true);
                for (n = 0; n < (arrf = this.mTimes).length / 2; ++n) {
                    float f = arrf[n];
                    arrf[n] = 1.0f - arrf[arrf.length - 1 - n];
                    arrf[arrf.length - 1 - n] = 1.0f - f;
                    arrf = this.mVolumes;
                    f = arrf[n];
                    arrf[n] = arrf[arrf.length - 1 - n];
                    arrf[arrf.length - 1 - n] = f;
                }
                if ((1 & arrf.length) != 0) {
                    arrf[n] = 1.0f - arrf[n];
                }
                return this;
            }

            public Builder scaleToEndVolume(float f) {
                boolean bl = (this.mOptionFlags & 1) != 0;
                Configuration.checkCurveForErrorsAndThrowException(this.mTimes, this.mVolumes, bl, true);
                Configuration.checkValidVolumeAndThrowException(f, bl);
                float[] arrf = this.mVolumes;
                float f2 = arrf[0];
                float f3 = arrf[arrf.length - 1];
                if (f3 == f2) {
                    for (int i = 0; i < (arrf = this.mVolumes).length; ++i) {
                        arrf[i] = arrf[i] + this.mTimes[i] * (f - f2);
                    }
                } else {
                    f = (f - f2) / (f3 - f2);
                    for (int i = 0; i < (arrf = this.mVolumes).length; ++i) {
                        arrf[i] = (arrf[i] - f2) * f + f2;
                    }
                }
                Configuration.clampVolume(this.mVolumes, bl);
                return this;
            }

            public Builder scaleToStartVolume(float f) {
                boolean bl = (this.mOptionFlags & 1) != 0;
                Configuration.checkCurveForErrorsAndThrowException(this.mTimes, this.mVolumes, bl, true);
                Configuration.checkValidVolumeAndThrowException(f, bl);
                float[] arrf = this.mVolumes;
                float f2 = arrf[0];
                float f3 = arrf[arrf.length - 1];
                if (f3 == f2) {
                    for (int i = 0; i < (arrf = this.mVolumes).length; ++i) {
                        arrf[i] = arrf[i] + (1.0f - this.mTimes[i]) * (f - f2);
                    }
                } else {
                    f = (f - f3) / (f2 - f3);
                    for (int i = 0; i < (arrf = this.mVolumes).length; ++i) {
                        arrf[i] = (arrf[i] - f3) * f + f3;
                    }
                }
                Configuration.clampVolume(this.mVolumes, bl);
                return this;
            }

            public Builder setCurve(float[] arrf, float[] arrf2) {
                int n = this.mOptionFlags;
                boolean bl = true;
                if ((n & 1) == 0) {
                    bl = false;
                }
                Configuration.checkCurveForErrorsAndThrowException(arrf, arrf2, bl, false);
                this.mTimes = (float[])arrf.clone();
                this.mVolumes = (float[])arrf2.clone();
                return this;
            }

            public Builder setDuration(long l) {
                if (l > 0L) {
                    this.mDurationMs = l;
                    return this;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("duration: ");
                stringBuilder.append(l);
                stringBuilder.append(" not positive");
                throw new IllegalArgumentException(stringBuilder.toString());
            }

            public Builder setId(int n) {
                if (n >= -1) {
                    this.mId = n;
                    return this;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("invalid id: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }

            public Builder setInterpolatorType(int n) {
                if (n != 0 && n != 1 && n != 2 && n != 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("invalid interpolatorType: ");
                    stringBuilder.append(n);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                this.mInterpolatorType = n;
                return this;
            }

            public Builder setOptionFlags(int n) {
                if ((n & -4) == 0) {
                    this.mOptionFlags = this.mOptionFlags & -4 | n;
                    return this;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("invalid bits in flag: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface InterpolatorType {
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface OptionFlag {
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface Type {
        }

    }

    public static final class Operation
    implements Parcelable {
        public static final Parcelable.Creator<Operation> CREATOR;
        private static final int FLAG_CREATE_IF_NEEDED = 16;
        private static final int FLAG_DEFER = 8;
        private static final int FLAG_JOIN = 4;
        private static final int FLAG_NONE = 0;
        private static final int FLAG_PUBLIC_ALL = 3;
        private static final int FLAG_REVERSE = 1;
        private static final int FLAG_TERMINATE = 2;
        public static final Operation PLAY;
        public static final Operation REVERSE;
        @UnsupportedAppUsage
        private final int mFlags;
        @UnsupportedAppUsage
        private final int mReplaceId;
        @UnsupportedAppUsage
        private final float mXOffset;

        static {
            PLAY = new Builder().build();
            REVERSE = new Builder().reverse().build();
            CREATOR = new Parcelable.Creator<Operation>(){

                @Override
                public Operation createFromParcel(Parcel parcel) {
                    return new Operation(parcel.readInt(), parcel.readInt(), parcel.readFloat());
                }

                public Operation[] newArray(int n) {
                    return new Operation[n];
                }
            };
        }

        @UnsupportedAppUsage
        private Operation(int n, int n2, float f) {
            this.mFlags = n;
            this.mReplaceId = n2;
            this.mXOffset = f;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (!(object instanceof Operation)) {
                return false;
            }
            boolean bl = true;
            if (object == this) {
                return true;
            }
            object = (Operation)object;
            if (this.mFlags != ((Operation)object).mFlags || this.mReplaceId != ((Operation)object).mReplaceId || Float.compare(this.mXOffset, ((Operation)object).mXOffset) != 0) {
                bl = false;
            }
            return bl;
        }

        public int hashCode() {
            return Objects.hash(this.mFlags, this.mReplaceId, Float.valueOf(this.mXOffset));
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("VolumeShaper.Operation{mFlags = 0x");
            stringBuilder.append(Integer.toHexString(this.mFlags).toUpperCase());
            stringBuilder.append(", mReplaceId = ");
            stringBuilder.append(this.mReplaceId);
            stringBuilder.append(", mXOffset = ");
            stringBuilder.append(this.mXOffset);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mFlags);
            parcel.writeInt(this.mReplaceId);
            parcel.writeFloat(this.mXOffset);
        }

        public static final class Builder {
            int mFlags;
            int mReplaceId;
            float mXOffset;

            public Builder() {
                this.mFlags = 0;
                this.mReplaceId = -1;
                this.mXOffset = Float.NaN;
            }

            public Builder(Operation operation) {
                this.mReplaceId = operation.mReplaceId;
                this.mFlags = operation.mFlags;
                this.mXOffset = operation.mXOffset;
            }

            private Builder setFlags(int n) {
                if ((n & -4) == 0) {
                    this.mFlags = this.mFlags & -4 | n;
                    return this;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("flag has unknown bits set: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }

            public Operation build() {
                return new Operation(this.mFlags, this.mReplaceId, this.mXOffset);
            }

            public Builder createIfNeeded() {
                this.mFlags |= 16;
                return this;
            }

            public Builder defer() {
                this.mFlags |= 8;
                return this;
            }

            public Builder replace(int n, boolean bl) {
                this.mReplaceId = n;
                this.mFlags = bl ? (this.mFlags |= 4) : (this.mFlags &= -5);
                return this;
            }

            public Builder reverse() {
                this.mFlags ^= 1;
                return this;
            }

            public Builder setXOffset(float f) {
                if (!(f < 0.0f)) {
                    if (!(f > 1.0f)) {
                        this.mXOffset = f;
                        return this;
                    }
                    throw new IllegalArgumentException("xOffset > 1.f not allowed");
                }
                throw new IllegalArgumentException("Negative xOffset not allowed");
            }

            public Builder terminate() {
                this.mFlags |= 2;
                return this;
            }
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface Flag {
        }

    }

    public static final class State
    implements Parcelable {
        public static final Parcelable.Creator<State> CREATOR = new Parcelable.Creator<State>(){

            @Override
            public State createFromParcel(Parcel parcel) {
                return new State(parcel.readFloat(), parcel.readFloat());
            }

            public State[] newArray(int n) {
                return new State[n];
            }
        };
        @UnsupportedAppUsage
        private float mVolume;
        @UnsupportedAppUsage
        private float mXOffset;

        @UnsupportedAppUsage
        State(float f, float f2) {
            this.mVolume = f;
            this.mXOffset = f2;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (!(object instanceof State)) {
                return false;
            }
            boolean bl = true;
            if (object == this) {
                return true;
            }
            object = (State)object;
            if (this.mVolume != ((State)object).mVolume || this.mXOffset != ((State)object).mXOffset) {
                bl = false;
            }
            return bl;
        }

        public float getVolume() {
            return this.mVolume;
        }

        public float getXOffset() {
            return this.mXOffset;
        }

        public int hashCode() {
            return Objects.hash(Float.valueOf(this.mVolume), Float.valueOf(this.mXOffset));
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("VolumeShaper.State{mVolume = ");
            stringBuilder.append(this.mVolume);
            stringBuilder.append(", mXOffset = ");
            stringBuilder.append(this.mXOffset);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeFloat(this.mVolume);
            parcel.writeFloat(this.mXOffset);
        }

    }

}

