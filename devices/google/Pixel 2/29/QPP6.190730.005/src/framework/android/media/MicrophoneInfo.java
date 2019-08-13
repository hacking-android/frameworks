/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.AudioDeviceInfo;
import android.util.Pair;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public final class MicrophoneInfo {
    public static final int CHANNEL_MAPPING_DIRECT = 1;
    public static final int CHANNEL_MAPPING_PROCESSED = 2;
    public static final int DIRECTIONALITY_BI_DIRECTIONAL = 2;
    public static final int DIRECTIONALITY_CARDIOID = 3;
    public static final int DIRECTIONALITY_HYPER_CARDIOID = 4;
    public static final int DIRECTIONALITY_OMNI = 1;
    public static final int DIRECTIONALITY_SUPER_CARDIOID = 5;
    public static final int DIRECTIONALITY_UNKNOWN = 0;
    public static final int GROUP_UNKNOWN = -1;
    public static final int INDEX_IN_THE_GROUP_UNKNOWN = -1;
    public static final int LOCATION_MAINBODY = 1;
    public static final int LOCATION_MAINBODY_MOVABLE = 2;
    public static final int LOCATION_PERIPHERAL = 3;
    public static final int LOCATION_UNKNOWN = 0;
    public static final Coordinate3F ORIENTATION_UNKNOWN;
    public static final Coordinate3F POSITION_UNKNOWN;
    public static final float SENSITIVITY_UNKNOWN = -3.4028235E38f;
    public static final float SPL_UNKNOWN = -3.4028235E38f;
    private String mAddress;
    private List<Pair<Integer, Integer>> mChannelMapping;
    private String mDeviceId;
    private int mDirectionality;
    private List<Pair<Float, Float>> mFrequencyResponse;
    private int mGroup;
    private int mIndexInTheGroup;
    private int mLocation;
    private float mMaxSpl;
    private float mMinSpl;
    private Coordinate3F mOrientation;
    private int mPortId;
    private Coordinate3F mPosition;
    private float mSensitivity;
    private int mType;

    static {
        POSITION_UNKNOWN = new Coordinate3F(-3.4028235E38f, -3.4028235E38f, -3.4028235E38f);
        ORIENTATION_UNKNOWN = new Coordinate3F(0.0f, 0.0f, 0.0f);
    }

    @UnsupportedAppUsage
    MicrophoneInfo(String string2, int n, String string3, int n2, int n3, int n4, Coordinate3F coordinate3F, Coordinate3F coordinate3F2, List<Pair<Float, Float>> list, List<Pair<Integer, Integer>> list2, float f, float f2, float f3, int n5) {
        this.mDeviceId = string2;
        this.mType = n;
        this.mAddress = string3;
        this.mLocation = n2;
        this.mGroup = n3;
        this.mIndexInTheGroup = n4;
        this.mPosition = coordinate3F;
        this.mOrientation = coordinate3F2;
        this.mFrequencyResponse = list;
        this.mChannelMapping = list2;
        this.mSensitivity = f;
        this.mMaxSpl = f2;
        this.mMinSpl = f3;
        this.mDirectionality = n5;
    }

    public String getAddress() {
        return this.mAddress;
    }

    public List<Pair<Integer, Integer>> getChannelMapping() {
        return this.mChannelMapping;
    }

    public String getDescription() {
        return this.mDeviceId;
    }

    public int getDirectionality() {
        return this.mDirectionality;
    }

    public List<Pair<Float, Float>> getFrequencyResponse() {
        return this.mFrequencyResponse;
    }

    public int getGroup() {
        return this.mGroup;
    }

    public int getId() {
        return this.mPortId;
    }

    public int getIndexInTheGroup() {
        return this.mIndexInTheGroup;
    }

    public int getInternalDeviceType() {
        return this.mType;
    }

    public int getLocation() {
        return this.mLocation;
    }

    public float getMaxSpl() {
        return this.mMaxSpl;
    }

    public float getMinSpl() {
        return this.mMinSpl;
    }

    public Coordinate3F getOrientation() {
        return this.mOrientation;
    }

    public Coordinate3F getPosition() {
        return this.mPosition;
    }

    public float getSensitivity() {
        return this.mSensitivity;
    }

    public int getType() {
        return AudioDeviceInfo.convertInternalDeviceToDeviceType(this.mType);
    }

    public void setChannelMapping(List<Pair<Integer, Integer>> list) {
        this.mChannelMapping = list;
    }

    public void setId(int n) {
        this.mPortId = n;
    }

    public static final class Coordinate3F {
        public final float x;
        public final float y;
        public final float z;

        Coordinate3F(float f, float f2, float f3) {
            this.x = f;
            this.y = f2;
            this.z = f3;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof Coordinate3F)) {
                return false;
            }
            object = (Coordinate3F)object;
            if (this.x != ((Coordinate3F)object).x || this.y != ((Coordinate3F)object).y || this.z != ((Coordinate3F)object).z) {
                bl = false;
            }
            return bl;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface MicrophoneDirectionality {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface MicrophoneLocation {
    }

}

