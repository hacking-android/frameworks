/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import android.annotation.SystemApi;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.camera2.utils.HashCodeHelpers;
import android.hardware.camera2.utils.SurfaceUtils;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class OutputConfiguration
implements Parcelable {
    public static final Parcelable.Creator<OutputConfiguration> CREATOR = new Parcelable.Creator<OutputConfiguration>(){

        @Override
        public OutputConfiguration createFromParcel(Parcel object) {
            try {
                object = new OutputConfiguration((Parcel)object);
                return object;
            }
            catch (Exception exception) {
                Log.e(OutputConfiguration.TAG, "Exception creating OutputConfiguration from parcel", exception);
                return null;
            }
        }

        public OutputConfiguration[] newArray(int n) {
            return new OutputConfiguration[n];
        }
    };
    private static final int MAX_SURFACES_COUNT = 4;
    @SystemApi
    public static final int ROTATION_0 = 0;
    @SystemApi
    public static final int ROTATION_180 = 2;
    @SystemApi
    public static final int ROTATION_270 = 3;
    @SystemApi
    public static final int ROTATION_90 = 1;
    public static final int SURFACE_GROUP_ID_NONE = -1;
    private static final String TAG = "OutputConfiguration";
    private final int SURFACE_TYPE_SURFACE_TEXTURE;
    private final int SURFACE_TYPE_SURFACE_VIEW;
    private final int SURFACE_TYPE_UNKNOWN;
    private final int mConfiguredDataspace;
    private final int mConfiguredFormat;
    private final int mConfiguredGenerationId;
    private final Size mConfiguredSize;
    private final boolean mIsDeferredConfig;
    private boolean mIsShared;
    private String mPhysicalCameraId;
    private final int mRotation;
    private final int mSurfaceGroupId;
    private final int mSurfaceType;
    private ArrayList<Surface> mSurfaces;

    public OutputConfiguration(int n, Surface surface) {
        this(n, surface, 0);
    }

    @SystemApi
    public OutputConfiguration(int n, Surface surface, int n2) {
        this.SURFACE_TYPE_UNKNOWN = -1;
        this.SURFACE_TYPE_SURFACE_VIEW = 0;
        this.SURFACE_TYPE_SURFACE_TEXTURE = 1;
        Preconditions.checkNotNull(surface, "Surface must not be null");
        Preconditions.checkArgumentInRange(n2, 0, 3, "Rotation constant");
        this.mSurfaceGroupId = n;
        this.mSurfaceType = -1;
        this.mSurfaces = new ArrayList();
        this.mSurfaces.add(surface);
        this.mRotation = n2;
        this.mConfiguredSize = SurfaceUtils.getSurfaceSize(surface);
        this.mConfiguredFormat = SurfaceUtils.getSurfaceFormat(surface);
        this.mConfiguredDataspace = SurfaceUtils.getSurfaceDataspace(surface);
        this.mConfiguredGenerationId = surface.getGenerationId();
        this.mIsDeferredConfig = false;
        this.mIsShared = false;
        this.mPhysicalCameraId = null;
    }

    public OutputConfiguration(OutputConfiguration outputConfiguration) {
        this.SURFACE_TYPE_UNKNOWN = -1;
        this.SURFACE_TYPE_SURFACE_VIEW = 0;
        this.SURFACE_TYPE_SURFACE_TEXTURE = 1;
        if (outputConfiguration != null) {
            this.mSurfaces = outputConfiguration.mSurfaces;
            this.mRotation = outputConfiguration.mRotation;
            this.mSurfaceGroupId = outputConfiguration.mSurfaceGroupId;
            this.mSurfaceType = outputConfiguration.mSurfaceType;
            this.mConfiguredDataspace = outputConfiguration.mConfiguredDataspace;
            this.mConfiguredFormat = outputConfiguration.mConfiguredFormat;
            this.mConfiguredSize = outputConfiguration.mConfiguredSize;
            this.mConfiguredGenerationId = outputConfiguration.mConfiguredGenerationId;
            this.mIsDeferredConfig = outputConfiguration.mIsDeferredConfig;
            this.mIsShared = outputConfiguration.mIsShared;
            this.mPhysicalCameraId = outputConfiguration.mPhysicalCameraId;
            return;
        }
        throw new IllegalArgumentException("OutputConfiguration shouldn't be null");
    }

    private OutputConfiguration(Parcel object) {
        this.SURFACE_TYPE_UNKNOWN = -1;
        this.SURFACE_TYPE_SURFACE_VIEW = 0;
        boolean bl = true;
        this.SURFACE_TYPE_SURFACE_TEXTURE = 1;
        int n = ((Parcel)object).readInt();
        int n2 = ((Parcel)object).readInt();
        int n3 = ((Parcel)object).readInt();
        int n4 = ((Parcel)object).readInt();
        int n5 = ((Parcel)object).readInt();
        boolean bl2 = ((Parcel)object).readInt() == 1;
        if (((Parcel)object).readInt() != 1) {
            bl = false;
        }
        ArrayList arrayList = new ArrayList();
        ((Parcel)object).readTypedList(arrayList, Surface.CREATOR);
        object = ((Parcel)object).readString();
        Preconditions.checkArgumentInRange(n, 0, 3, "Rotation constant");
        this.mSurfaceGroupId = n2;
        this.mRotation = n;
        this.mSurfaces = arrayList;
        this.mConfiguredSize = new Size(n4, n5);
        this.mIsDeferredConfig = bl2;
        this.mIsShared = bl;
        this.mSurfaces = arrayList;
        if (this.mSurfaces.size() > 0) {
            this.mSurfaceType = -1;
            this.mConfiguredFormat = SurfaceUtils.getSurfaceFormat(this.mSurfaces.get(0));
            this.mConfiguredDataspace = SurfaceUtils.getSurfaceDataspace(this.mSurfaces.get(0));
            this.mConfiguredGenerationId = this.mSurfaces.get(0).getGenerationId();
        } else {
            this.mSurfaceType = n3;
            this.mConfiguredFormat = StreamConfigurationMap.imageFormatToInternal(34);
            this.mConfiguredDataspace = StreamConfigurationMap.imageFormatToDataspace(34);
            this.mConfiguredGenerationId = 0;
        }
        this.mPhysicalCameraId = object;
    }

    public <T> OutputConfiguration(Size size, Class<T> class_) {
        block6 : {
            block5 : {
                block4 : {
                    this.SURFACE_TYPE_UNKNOWN = -1;
                    this.SURFACE_TYPE_SURFACE_VIEW = 0;
                    this.SURFACE_TYPE_SURFACE_TEXTURE = 1;
                    Preconditions.checkNotNull(class_, "surfaceSize must not be null");
                    Preconditions.checkNotNull(class_, "klass must not be null");
                    if (class_ != SurfaceHolder.class) break block4;
                    this.mSurfaceType = 0;
                    break block5;
                }
                if (class_ != SurfaceTexture.class) break block6;
                this.mSurfaceType = 1;
            }
            if (size.getWidth() != 0 && size.getHeight() != 0) {
                this.mSurfaceGroupId = -1;
                this.mSurfaces = new ArrayList();
                this.mRotation = 0;
                this.mConfiguredSize = size;
                this.mConfiguredFormat = StreamConfigurationMap.imageFormatToInternal(34);
                this.mConfiguredDataspace = StreamConfigurationMap.imageFormatToDataspace(34);
                this.mConfiguredGenerationId = 0;
                this.mIsDeferredConfig = true;
                this.mIsShared = false;
                this.mPhysicalCameraId = null;
                return;
            }
            throw new IllegalArgumentException("Surface size needs to be non-zero");
        }
        this.mSurfaceType = -1;
        throw new IllegalArgumentException("Unknow surface source class type");
    }

    public OutputConfiguration(Surface surface) {
        this(-1, surface, 0);
    }

    @SystemApi
    public OutputConfiguration(Surface surface, int n) {
        this(-1, surface, n);
    }

    public void addSurface(Surface surface) {
        Preconditions.checkNotNull(surface, "Surface must not be null");
        if (!this.mSurfaces.contains(surface)) {
            if (this.mSurfaces.size() == 1 && !this.mIsShared) {
                throw new IllegalStateException("Cannot have 2 surfaces for a non-sharing configuration");
            }
            if (this.mSurfaces.size() + 1 <= 4) {
                Size size = SurfaceUtils.getSurfaceSize(surface);
                if (!size.equals(this.mConfiguredSize)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Added surface size ");
                    stringBuilder.append(size);
                    stringBuilder.append(" is different than pre-configured size ");
                    stringBuilder.append(this.mConfiguredSize);
                    stringBuilder.append(", the pre-configured size will be used.");
                    Log.w(TAG, stringBuilder.toString());
                }
                if (this.mConfiguredFormat == SurfaceUtils.getSurfaceFormat(surface)) {
                    if (this.mConfiguredFormat != 34 && this.mConfiguredDataspace != SurfaceUtils.getSurfaceDataspace(surface)) {
                        throw new IllegalArgumentException("The dataspace of added surface doesn't match");
                    }
                    this.mSurfaces.add(surface);
                    return;
                }
                throw new IllegalArgumentException("The format of added surface format doesn't match");
            }
            throw new IllegalArgumentException("Exceeds maximum number of surfaces");
        }
        throw new IllegalStateException("Surface is already added!");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void enableSurfaceSharing() {
        this.mIsShared = true;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof OutputConfiguration) {
            int n;
            int n2;
            object = (OutputConfiguration)object;
            if (this.mRotation == ((OutputConfiguration)object).mRotation && this.mConfiguredSize.equals(((OutputConfiguration)object).mConfiguredSize) && (n = this.mConfiguredFormat) == (n2 = ((OutputConfiguration)object).mConfiguredFormat) && this.mSurfaceGroupId == ((OutputConfiguration)object).mSurfaceGroupId && this.mSurfaceType == ((OutputConfiguration)object).mSurfaceType && this.mIsDeferredConfig == ((OutputConfiguration)object).mIsDeferredConfig && this.mIsShared == ((OutputConfiguration)object).mIsShared && n == n2 && this.mConfiguredDataspace == ((OutputConfiguration)object).mConfiguredDataspace && this.mConfiguredGenerationId == ((OutputConfiguration)object).mConfiguredGenerationId && Objects.equals(this.mPhysicalCameraId, ((OutputConfiguration)object).mPhysicalCameraId)) {
                n = Math.min(this.mSurfaces.size(), ((OutputConfiguration)object).mSurfaces.size());
                for (n2 = 0; n2 < n; ++n2) {
                    if (this.mSurfaces.get(n2) == ((OutputConfiguration)object).mSurfaces.get(n2)) continue;
                    return false;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public int getMaxSharedSurfaceCount() {
        return 4;
    }

    @SystemApi
    public int getRotation() {
        return this.mRotation;
    }

    public Surface getSurface() {
        if (this.mSurfaces.size() == 0) {
            return null;
        }
        return this.mSurfaces.get(0);
    }

    public int getSurfaceGroupId() {
        return this.mSurfaceGroupId;
    }

    public List<Surface> getSurfaces() {
        return Collections.unmodifiableList(this.mSurfaces);
    }

    public int hashCode() {
        boolean bl = this.mIsDeferredConfig;
        int n = 0;
        int n2 = 0;
        if (bl) {
            int n3 = this.mRotation;
            int n4 = this.mConfiguredSize.hashCode();
            int n5 = this.mConfiguredFormat;
            int n6 = this.mConfiguredDataspace;
            int n7 = this.mSurfaceGroupId;
            int n8 = this.mSurfaceType;
            boolean bl2 = this.mIsShared;
            String string2 = this.mPhysicalCameraId;
            n = string2 == null ? n2 : string2.hashCode();
            return HashCodeHelpers.hashCode(new int[]{n3, n4, n5, n6, n7, n8, bl2 ? 1 : 0, n});
        }
        int n9 = this.mRotation;
        int n10 = this.mSurfaces.hashCode();
        int n11 = this.mConfiguredGenerationId;
        int n12 = this.mConfiguredSize.hashCode();
        int n13 = this.mConfiguredFormat;
        int n14 = this.mConfiguredDataspace;
        n2 = this.mSurfaceGroupId;
        boolean bl3 = this.mIsShared;
        String string3 = this.mPhysicalCameraId;
        if (string3 != null) {
            n = string3.hashCode();
        }
        return HashCodeHelpers.hashCode(new int[]{n9, n10, n11, n12, n13, n14, n2, bl3 ? 1 : 0, n});
    }

    public boolean isDeferredConfiguration() {
        return this.mIsDeferredConfig;
    }

    public boolean isForPhysicalCamera() {
        boolean bl = this.mPhysicalCameraId != null;
        return bl;
    }

    public void removeSurface(Surface surface) {
        if (this.getSurface() != surface) {
            if (this.mSurfaces.remove(surface)) {
                return;
            }
            throw new IllegalArgumentException("Surface is not part of this output configuration");
        }
        throw new IllegalArgumentException("Cannot remove surface associated with this output configuration");
    }

    public void setPhysicalCameraId(String string2) {
        this.mPhysicalCameraId = string2;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (parcel != null) {
            parcel.writeInt(this.mRotation);
            parcel.writeInt(this.mSurfaceGroupId);
            parcel.writeInt(this.mSurfaceType);
            parcel.writeInt(this.mConfiguredSize.getWidth());
            parcel.writeInt(this.mConfiguredSize.getHeight());
            parcel.writeInt((int)this.mIsDeferredConfig);
            parcel.writeInt((int)this.mIsShared);
            parcel.writeTypedList(this.mSurfaces);
            parcel.writeString(this.mPhysicalCameraId);
            return;
        }
        throw new IllegalArgumentException("dest must not be null");
    }

}

