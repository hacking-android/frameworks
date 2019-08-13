/*
 * Decompiled with CFR 0.145.
 */
package android.media.tv;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

@SystemApi
public class TvStreamConfig
implements Parcelable {
    public static final Parcelable.Creator<TvStreamConfig> CREATOR;
    public static final int STREAM_TYPE_BUFFER_PRODUCER = 2;
    public static final int STREAM_TYPE_INDEPENDENT_VIDEO_SOURCE = 1;
    static final String TAG;
    private int mGeneration;
    private int mMaxHeight;
    private int mMaxWidth;
    private int mStreamId;
    private int mType;

    static {
        TAG = TvStreamConfig.class.getSimpleName();
        CREATOR = new Parcelable.Creator<TvStreamConfig>(){

            @Override
            public TvStreamConfig createFromParcel(Parcel object) {
                try {
                    Builder builder = new Builder();
                    object = builder.streamId(((Parcel)object).readInt()).type(((Parcel)object).readInt()).maxWidth(((Parcel)object).readInt()).maxHeight(((Parcel)object).readInt()).generation(((Parcel)object).readInt()).build();
                    return object;
                }
                catch (Exception exception) {
                    Log.e(TAG, "Exception creating TvStreamConfig from parcel", exception);
                    return null;
                }
            }

            public TvStreamConfig[] newArray(int n) {
                return new TvStreamConfig[n];
            }
        };
    }

    private TvStreamConfig() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (!(object instanceof TvStreamConfig)) {
            return false;
        }
        object = (TvStreamConfig)object;
        boolean bl2 = bl;
        if (((TvStreamConfig)object).mGeneration == this.mGeneration) {
            bl2 = bl;
            if (((TvStreamConfig)object).mStreamId == this.mStreamId) {
                bl2 = bl;
                if (((TvStreamConfig)object).mType == this.mType) {
                    bl2 = bl;
                    if (((TvStreamConfig)object).mMaxWidth == this.mMaxWidth) {
                        bl2 = bl;
                        if (((TvStreamConfig)object).mMaxHeight == this.mMaxHeight) {
                            bl2 = true;
                        }
                    }
                }
            }
        }
        return bl2;
    }

    public int getGeneration() {
        return this.mGeneration;
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public int getStreamId() {
        return this.mStreamId;
    }

    public int getType() {
        return this.mType;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TvStreamConfig {mStreamId=");
        stringBuilder.append(this.mStreamId);
        stringBuilder.append(";mType=");
        stringBuilder.append(this.mType);
        stringBuilder.append(";mGeneration=");
        stringBuilder.append(this.mGeneration);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mStreamId);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mMaxWidth);
        parcel.writeInt(this.mMaxHeight);
        parcel.writeInt(this.mGeneration);
    }

    public static final class Builder {
        private Integer mGeneration;
        private Integer mMaxHeight;
        private Integer mMaxWidth;
        private Integer mStreamId;
        private Integer mType;

        public TvStreamConfig build() {
            if (this.mStreamId != null && this.mType != null && this.mMaxWidth != null && this.mMaxHeight != null && this.mGeneration != null) {
                TvStreamConfig tvStreamConfig = new TvStreamConfig();
                tvStreamConfig.mStreamId = this.mStreamId;
                tvStreamConfig.mType = this.mType;
                tvStreamConfig.mMaxWidth = this.mMaxWidth;
                tvStreamConfig.mMaxHeight = this.mMaxHeight;
                tvStreamConfig.mGeneration = this.mGeneration;
                return tvStreamConfig;
            }
            throw new UnsupportedOperationException();
        }

        public Builder generation(int n) {
            this.mGeneration = n;
            return this;
        }

        public Builder maxHeight(int n) {
            this.mMaxHeight = n;
            return this;
        }

        public Builder maxWidth(int n) {
            this.mMaxWidth = n;
            return this;
        }

        public Builder streamId(int n) {
            this.mStreamId = n;
            return this;
        }

        public Builder type(int n) {
            this.mType = n;
            return this;
        }
    }

}

