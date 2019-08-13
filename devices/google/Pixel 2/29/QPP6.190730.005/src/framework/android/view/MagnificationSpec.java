/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pools;

public class MagnificationSpec
implements Parcelable {
    public static final Parcelable.Creator<MagnificationSpec> CREATOR;
    private static final int MAX_POOL_SIZE = 20;
    private static final Pools.SynchronizedPool<MagnificationSpec> sPool;
    public float offsetX;
    public float offsetY;
    public float scale = 1.0f;

    static {
        sPool = new Pools.SynchronizedPool(20);
        CREATOR = new Parcelable.Creator<MagnificationSpec>(){

            @Override
            public MagnificationSpec createFromParcel(Parcel parcel) {
                MagnificationSpec magnificationSpec = MagnificationSpec.obtain();
                magnificationSpec.initFromParcel(parcel);
                return magnificationSpec;
            }

            public MagnificationSpec[] newArray(int n) {
                return new MagnificationSpec[n];
            }
        };
    }

    private MagnificationSpec() {
    }

    private void initFromParcel(Parcel parcel) {
        this.scale = parcel.readFloat();
        this.offsetX = parcel.readFloat();
        this.offsetY = parcel.readFloat();
    }

    public static MagnificationSpec obtain() {
        MagnificationSpec magnificationSpec = sPool.acquire();
        if (magnificationSpec == null) {
            magnificationSpec = new MagnificationSpec();
        }
        return magnificationSpec;
    }

    public static MagnificationSpec obtain(MagnificationSpec magnificationSpec) {
        MagnificationSpec magnificationSpec2 = MagnificationSpec.obtain();
        magnificationSpec2.scale = magnificationSpec.scale;
        magnificationSpec2.offsetX = magnificationSpec.offsetX;
        magnificationSpec2.offsetY = magnificationSpec.offsetY;
        return magnificationSpec2;
    }

    public void clear() {
        this.scale = 1.0f;
        this.offsetX = 0.0f;
        this.offsetY = 0.0f;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (MagnificationSpec)object;
            if (this.scale != ((MagnificationSpec)object).scale || this.offsetX != ((MagnificationSpec)object).offsetX || this.offsetY != ((MagnificationSpec)object).offsetY) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int hashCode() {
        float f = this.scale;
        int n = 0;
        int n2 = f != 0.0f ? Float.floatToIntBits(f) : 0;
        f = this.offsetX;
        int n3 = f != 0.0f ? Float.floatToIntBits(f) : 0;
        f = this.offsetY;
        if (f != 0.0f) {
            n = Float.floatToIntBits(f);
        }
        return (n2 * 31 + n3) * 31 + n;
    }

    public void initialize(float f, float f2, float f3) {
        if (!(f < 1.0f)) {
            this.scale = f;
            this.offsetX = f2;
            this.offsetY = f3;
            return;
        }
        throw new IllegalArgumentException("Scale must be greater than or equal to one!");
    }

    public boolean isNop() {
        boolean bl = this.scale == 1.0f && this.offsetX == 0.0f && this.offsetY == 0.0f;
        return bl;
    }

    public void recycle() {
        this.clear();
        sPool.release(this);
    }

    public void setTo(MagnificationSpec magnificationSpec) {
        this.scale = magnificationSpec.scale;
        this.offsetX = magnificationSpec.offsetX;
        this.offsetY = magnificationSpec.offsetY;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<scale:");
        stringBuilder.append(Float.toString(this.scale));
        stringBuilder.append(",offsetX:");
        stringBuilder.append(Float.toString(this.offsetX));
        stringBuilder.append(",offsetY:");
        stringBuilder.append(Float.toString(this.offsetY));
        stringBuilder.append(">");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeFloat(this.scale);
        parcel.writeFloat(this.offsetX);
        parcel.writeFloat(this.offsetY);
        this.recycle();
    }

}

