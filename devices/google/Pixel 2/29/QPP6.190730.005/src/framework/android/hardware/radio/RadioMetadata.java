/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.annotation.SystemApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import java.util.Set;

@SystemApi
public final class RadioMetadata
implements Parcelable {
    public static final Parcelable.Creator<RadioMetadata> CREATOR;
    private static final ArrayMap<String, Integer> METADATA_KEYS_TYPE;
    public static final String METADATA_KEY_ALBUM = "android.hardware.radio.metadata.ALBUM";
    public static final String METADATA_KEY_ART = "android.hardware.radio.metadata.ART";
    public static final String METADATA_KEY_ARTIST = "android.hardware.radio.metadata.ARTIST";
    public static final String METADATA_KEY_CLOCK = "android.hardware.radio.metadata.CLOCK";
    public static final String METADATA_KEY_DAB_COMPONENT_NAME = "android.hardware.radio.metadata.DAB_COMPONENT_NAME";
    public static final String METADATA_KEY_DAB_COMPONENT_NAME_SHORT = "android.hardware.radio.metadata.DAB_COMPONENT_NAME_SHORT";
    public static final String METADATA_KEY_DAB_ENSEMBLE_NAME = "android.hardware.radio.metadata.DAB_ENSEMBLE_NAME";
    public static final String METADATA_KEY_DAB_ENSEMBLE_NAME_SHORT = "android.hardware.radio.metadata.DAB_ENSEMBLE_NAME_SHORT";
    public static final String METADATA_KEY_DAB_SERVICE_NAME = "android.hardware.radio.metadata.DAB_SERVICE_NAME";
    public static final String METADATA_KEY_DAB_SERVICE_NAME_SHORT = "android.hardware.radio.metadata.DAB_SERVICE_NAME_SHORT";
    public static final String METADATA_KEY_GENRE = "android.hardware.radio.metadata.GENRE";
    public static final String METADATA_KEY_ICON = "android.hardware.radio.metadata.ICON";
    public static final String METADATA_KEY_PROGRAM_NAME = "android.hardware.radio.metadata.PROGRAM_NAME";
    public static final String METADATA_KEY_RBDS_PTY = "android.hardware.radio.metadata.RBDS_PTY";
    public static final String METADATA_KEY_RDS_PI = "android.hardware.radio.metadata.RDS_PI";
    public static final String METADATA_KEY_RDS_PS = "android.hardware.radio.metadata.RDS_PS";
    public static final String METADATA_KEY_RDS_PTY = "android.hardware.radio.metadata.RDS_PTY";
    public static final String METADATA_KEY_RDS_RT = "android.hardware.radio.metadata.RDS_RT";
    public static final String METADATA_KEY_TITLE = "android.hardware.radio.metadata.TITLE";
    private static final int METADATA_TYPE_BITMAP = 2;
    private static final int METADATA_TYPE_CLOCK = 3;
    private static final int METADATA_TYPE_INT = 0;
    private static final int METADATA_TYPE_INVALID = -1;
    private static final int METADATA_TYPE_TEXT = 1;
    private static final int NATIVE_KEY_ALBUM = 7;
    private static final int NATIVE_KEY_ART = 10;
    private static final int NATIVE_KEY_ARTIST = 6;
    private static final int NATIVE_KEY_CLOCK = 11;
    private static final int NATIVE_KEY_GENRE = 8;
    private static final int NATIVE_KEY_ICON = 9;
    private static final int NATIVE_KEY_INVALID = -1;
    private static final SparseArray<String> NATIVE_KEY_MAPPING;
    private static final int NATIVE_KEY_RBDS_PTY = 3;
    private static final int NATIVE_KEY_RDS_PI = 0;
    private static final int NATIVE_KEY_RDS_PS = 1;
    private static final int NATIVE_KEY_RDS_PTY = 2;
    private static final int NATIVE_KEY_RDS_RT = 4;
    private static final int NATIVE_KEY_TITLE = 5;
    private static final String TAG = "BroadcastRadio.metadata";
    private final Bundle mBundle;

    static {
        METADATA_KEYS_TYPE = new ArrayMap();
        Object object = METADATA_KEYS_TYPE;
        Object object2 = 0;
        ((ArrayMap)object).put((String)METADATA_KEY_RDS_PI, (Integer)object2);
        Object object3 = METADATA_KEYS_TYPE;
        object = 1;
        ((ArrayMap)object3).put((String)METADATA_KEY_RDS_PS, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_RDS_PTY, (Integer)object2);
        METADATA_KEYS_TYPE.put(METADATA_KEY_RBDS_PTY, (Integer)object2);
        METADATA_KEYS_TYPE.put(METADATA_KEY_RDS_RT, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_TITLE, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ARTIST, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_GENRE, (Integer)object);
        object2 = METADATA_KEYS_TYPE;
        object3 = 2;
        ((ArrayMap)object2).put(METADATA_KEY_ICON, object3);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ART, (Integer)object3);
        METADATA_KEYS_TYPE.put(METADATA_KEY_CLOCK, 3);
        METADATA_KEYS_TYPE.put(METADATA_KEY_PROGRAM_NAME, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DAB_ENSEMBLE_NAME, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DAB_ENSEMBLE_NAME_SHORT, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DAB_SERVICE_NAME, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DAB_SERVICE_NAME_SHORT, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DAB_COMPONENT_NAME, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DAB_COMPONENT_NAME_SHORT, (Integer)object);
        NATIVE_KEY_MAPPING = new SparseArray();
        NATIVE_KEY_MAPPING.put(0, METADATA_KEY_RDS_PI);
        NATIVE_KEY_MAPPING.put(1, METADATA_KEY_RDS_PS);
        NATIVE_KEY_MAPPING.put(2, METADATA_KEY_RDS_PTY);
        NATIVE_KEY_MAPPING.put(3, METADATA_KEY_RBDS_PTY);
        NATIVE_KEY_MAPPING.put(4, METADATA_KEY_RDS_RT);
        NATIVE_KEY_MAPPING.put(5, METADATA_KEY_TITLE);
        NATIVE_KEY_MAPPING.put(6, METADATA_KEY_ARTIST);
        NATIVE_KEY_MAPPING.put(7, METADATA_KEY_ALBUM);
        NATIVE_KEY_MAPPING.put(8, METADATA_KEY_GENRE);
        NATIVE_KEY_MAPPING.put(9, METADATA_KEY_ICON);
        NATIVE_KEY_MAPPING.put(10, METADATA_KEY_ART);
        NATIVE_KEY_MAPPING.put(11, METADATA_KEY_CLOCK);
        CREATOR = new Parcelable.Creator<RadioMetadata>(){

            @Override
            public RadioMetadata createFromParcel(Parcel parcel) {
                return new RadioMetadata(parcel);
            }

            public RadioMetadata[] newArray(int n) {
                return new RadioMetadata[n];
            }
        };
    }

    RadioMetadata() {
        this.mBundle = new Bundle();
    }

    private RadioMetadata(Bundle bundle) {
        this.mBundle = new Bundle(bundle);
    }

    private RadioMetadata(Parcel parcel) {
        this.mBundle = parcel.readBundle();
    }

    public static String getKeyFromNativeKey(int n) {
        return NATIVE_KEY_MAPPING.get(n, null);
    }

    private static void putInt(Bundle object, String string2, int n) {
        int n2 = METADATA_KEYS_TYPE.getOrDefault(string2, -1);
        if (n2 != 0 && n2 != 2) {
            object = new StringBuilder();
            ((StringBuilder)object).append("The ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" key cannot be used to put an int");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        ((BaseBundle)object).putInt(string2, n);
    }

    public boolean containsKey(String string2) {
        return this.mBundle.containsKey(string2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public Bitmap getBitmap(String object) {
        Object var2_3 = null;
        try {
            object = (Bitmap)this.mBundle.getParcelable((String)object);
        }
        catch (Exception exception) {
            Log.w(TAG, "Failed to retrieve a key as Bitmap.", exception);
            object = var2_3;
        }
        return object;
    }

    public int getBitmapId(String string2) {
        if (!METADATA_KEY_ICON.equals(string2) && !METADATA_KEY_ART.equals(string2)) {
            return 0;
        }
        return this.getInt(string2);
    }

    public Clock getClock(String object) {
        Object var2_3 = null;
        try {
            object = (Clock)this.mBundle.getParcelable((String)object);
        }
        catch (Exception exception) {
            Log.w(TAG, "Failed to retrieve a key as Clock.", exception);
            object = var2_3;
        }
        return object;
    }

    public int getInt(String string2) {
        return this.mBundle.getInt(string2, 0);
    }

    public String getString(String string2) {
        return this.mBundle.getString(string2);
    }

    public Set<String> keySet() {
        return this.mBundle.keySet();
    }

    int putBitmapFromNative(int n, byte[] object) {
        String string2 = RadioMetadata.getKeyFromNativeKey(n);
        if (METADATA_KEYS_TYPE.containsKey(string2) && METADATA_KEYS_TYPE.get(string2) == 2) {
            block4 : {
                object = BitmapFactory.decodeByteArray(object, 0, ((byte[])object).length);
                if (object == null) break block4;
                try {
                    this.mBundle.putParcelable(string2, (Parcelable)object);
                    return 0;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            return -1;
        }
        return -1;
    }

    int putClockFromNative(int n, long l, int n2) {
        String string2 = RadioMetadata.getKeyFromNativeKey(n);
        if (METADATA_KEYS_TYPE.containsKey(string2) && METADATA_KEYS_TYPE.get(string2) == 3) {
            this.mBundle.putParcelable(string2, new Clock(l, n2));
            return 0;
        }
        return -1;
    }

    int putIntFromNative(int n, int n2) {
        String string2 = RadioMetadata.getKeyFromNativeKey(n);
        try {
            RadioMetadata.putInt(this.mBundle, string2, n2);
            return 0;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return -1;
        }
    }

    int putStringFromNative(int n, String string2) {
        String string3 = RadioMetadata.getKeyFromNativeKey(n);
        if (METADATA_KEYS_TYPE.containsKey(string3) && METADATA_KEYS_TYPE.get(string3) == 1) {
            this.mBundle.putString(string3, string2);
            return 0;
        }
        return -1;
    }

    public int size() {
        return this.mBundle.size();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("RadioMetadata[");
        boolean bl = true;
        for (String string2 : this.mBundle.keySet()) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            String string3 = string2;
            if (string2.startsWith("android.hardware.radio.metadata")) {
                string3 = string2.substring("android.hardware.radio.metadata".length());
            }
            stringBuilder.append(string3);
            stringBuilder.append('=');
            stringBuilder.append(this.mBundle.get(string2));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBundle(this.mBundle);
    }

    public static final class Builder {
        private final Bundle mBundle;

        public Builder() {
            this.mBundle = new Bundle();
        }

        public Builder(RadioMetadata radioMetadata) {
            this.mBundle = new Bundle(radioMetadata.mBundle);
        }

        /*
         * WARNING - void declaration
         */
        public Builder(RadioMetadata object2, int n) {
            this((RadioMetadata)object2);
            for (String string2 : this.mBundle.keySet()) {
                void var2_4;
                Object object = this.mBundle.get(string2);
                if (object == null || !(object instanceof Bitmap) || ((Bitmap)(object = (Bitmap)object)).getHeight() <= var2_4 && ((Bitmap)object).getWidth() <= var2_4) continue;
                this.putBitmap(string2, this.scaleBitmap((Bitmap)object, (int)var2_4));
            }
        }

        private Bitmap scaleBitmap(Bitmap bitmap, int n) {
            float f = n;
            f = Math.min(f / (float)bitmap.getWidth(), f / (float)bitmap.getHeight());
            n = (int)((float)bitmap.getHeight() * f);
            return Bitmap.createScaledBitmap(bitmap, (int)((float)bitmap.getWidth() * f), n, true);
        }

        public RadioMetadata build() {
            return new RadioMetadata(this.mBundle);
        }

        public Builder putBitmap(String string2, Bitmap object) {
            if (METADATA_KEYS_TYPE.containsKey(string2) && (Integer)METADATA_KEYS_TYPE.get(string2) == 2) {
                this.mBundle.putParcelable(string2, (Parcelable)object);
                return this;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("The ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" key cannot be used to put a Bitmap");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public Builder putClock(String string2, long l, int n) {
            if (METADATA_KEYS_TYPE.containsKey(string2) && (Integer)METADATA_KEYS_TYPE.get(string2) == 3) {
                this.mBundle.putParcelable(string2, new Clock(l, n));
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("The ");
            stringBuilder.append(string2);
            stringBuilder.append(" key cannot be used to put a RadioMetadata.Clock.");
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public Builder putInt(String string2, int n) {
            RadioMetadata.putInt(this.mBundle, string2, n);
            return this;
        }

        public Builder putString(String string2, String charSequence) {
            if (METADATA_KEYS_TYPE.containsKey(string2) && (Integer)METADATA_KEYS_TYPE.get(string2) == 1) {
                this.mBundle.putString(string2, (String)charSequence);
                return this;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("The ");
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(" key cannot be used to put a String");
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
    }

    @SystemApi
    public static final class Clock
    implements Parcelable {
        public static final Parcelable.Creator<Clock> CREATOR = new Parcelable.Creator<Clock>(){

            @Override
            public Clock createFromParcel(Parcel parcel) {
                return new Clock(parcel);
            }

            public Clock[] newArray(int n) {
                return new Clock[n];
            }
        };
        private final int mTimezoneOffsetMinutes;
        private final long mUtcEpochSeconds;

        public Clock(long l, int n) {
            this.mUtcEpochSeconds = l;
            this.mTimezoneOffsetMinutes = n;
        }

        private Clock(Parcel parcel) {
            this.mUtcEpochSeconds = parcel.readLong();
            this.mTimezoneOffsetMinutes = parcel.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public int getTimezoneOffsetMinutes() {
            return this.mTimezoneOffsetMinutes;
        }

        public long getUtcEpochSeconds() {
            return this.mUtcEpochSeconds;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeLong(this.mUtcEpochSeconds);
            parcel.writeInt(this.mTimezoneOffsetMinutes);
        }

    }

}

