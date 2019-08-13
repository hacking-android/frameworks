/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.util.Log;
import android.util.MathUtils;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.TimeZone;

@Deprecated
public class Metadata {
    public static final int ALBUM = 8;
    public static final int ALBUM_ART = 18;
    public static final int ANY = 0;
    public static final int ARTIST = 9;
    public static final int AUDIO_BIT_RATE = 21;
    public static final int AUDIO_CODEC = 26;
    public static final int AUDIO_SAMPLE_RATE = 23;
    public static final int AUTHOR = 10;
    public static final int BIT_RATE = 20;
    public static final int BOOLEAN_VAL = 3;
    public static final int BYTE_ARRAY_VAL = 7;
    public static final int CD_TRACK_MAX = 16;
    public static final int CD_TRACK_NUM = 15;
    public static final int COMMENT = 6;
    public static final int COMPOSER = 11;
    public static final int COPYRIGHT = 7;
    public static final int DATE = 13;
    public static final int DATE_VAL = 6;
    public static final int DOUBLE_VAL = 5;
    public static final int DRM_CRIPPLED = 31;
    public static final int DURATION = 14;
    private static final int FIRST_CUSTOM = 8192;
    public static final int GENRE = 12;
    public static final int INTEGER_VAL = 2;
    private static final int LAST_SYSTEM = 31;
    private static final int LAST_TYPE = 7;
    public static final int LONG_VAL = 4;
    public static final Set<Integer> MATCH_ALL;
    public static final Set<Integer> MATCH_NONE;
    public static final int MIME_TYPE = 25;
    public static final int NUM_TRACKS = 30;
    @UnsupportedAppUsage
    public static final int PAUSE_AVAILABLE = 1;
    public static final int RATING = 17;
    @UnsupportedAppUsage
    public static final int SEEK_AVAILABLE = 4;
    @UnsupportedAppUsage
    public static final int SEEK_BACKWARD_AVAILABLE = 2;
    @UnsupportedAppUsage
    public static final int SEEK_FORWARD_AVAILABLE = 3;
    public static final int STRING_VAL = 1;
    private static final String TAG = "media.Metadata";
    public static final int TITLE = 5;
    public static final int VIDEO_BIT_RATE = 22;
    public static final int VIDEO_CODEC = 27;
    public static final int VIDEO_FRAME = 19;
    public static final int VIDEO_FRAME_RATE = 24;
    public static final int VIDEO_HEIGHT = 28;
    public static final int VIDEO_WIDTH = 29;
    private static final int kInt32Size = 4;
    private static final int kMetaHeaderSize = 8;
    private static final int kMetaMarker = 1296389185;
    private static final int kRecordHeaderSize = 12;
    private final HashMap<Integer, Integer> mKeyToPosMap = new HashMap();
    private Parcel mParcel;

    static {
        MATCH_NONE = Collections.EMPTY_SET;
        MATCH_ALL = Collections.singleton(0);
    }

    private boolean checkMetadataId(int n) {
        if (n > 0 && (31 >= n || n >= 8192)) {
            return true;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid metadata ID ");
        stringBuilder.append(n);
        Log.e(TAG, stringBuilder.toString());
        return false;
    }

    private void checkType(int n, int n2) {
        n = this.mKeyToPosMap.get(n);
        this.mParcel.setDataPosition(n);
        n = this.mParcel.readInt();
        if (n == n2) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Wrong type ");
        stringBuilder.append(n2);
        stringBuilder.append(" but got ");
        stringBuilder.append(n);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public static int firstCustomId() {
        return 8192;
    }

    public static int lastSytemId() {
        return 31;
    }

    public static int lastType() {
        return 7;
    }

    private boolean scanAllRecords(Parcel object, int n) {
        int n2;
        int n3;
        block7 : {
            int n4;
            n3 = 0;
            int n5 = 0;
            this.mKeyToPosMap.clear();
            n2 = n;
            do {
                n = n5;
                if (n2 <= 12) break block7;
                n = ((Parcel)object).dataPosition();
                int n6 = ((Parcel)object).readInt();
                if (n6 <= 12) {
                    Log.e(TAG, "Record is too short");
                    n = 1;
                    break block7;
                }
                n4 = ((Parcel)object).readInt();
                if (!this.checkMetadataId(n4)) {
                    n = 1;
                    break block7;
                }
                if (this.mKeyToPosMap.containsKey(n4)) {
                    Log.e(TAG, "Duplicate metadata ID found");
                    n = 1;
                    break block7;
                }
                this.mKeyToPosMap.put(n4, ((Parcel)object).dataPosition());
                n4 = ((Parcel)object).readInt();
                if (n4 <= 0 || n4 > 7) break;
                try {
                    ((Parcel)object).setDataPosition(MathUtils.addOrThrow(n, n6));
                    n2 -= n6;
                    ++n3;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid size: ");
                    ((StringBuilder)object).append(illegalArgumentException.getMessage());
                    Log.e(TAG, ((StringBuilder)object).toString());
                    n = 1;
                    break block7;
                }
            } while (true);
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid metadata type ");
            ((StringBuilder)object).append(n4);
            Log.e(TAG, ((StringBuilder)object).toString());
            n = 1;
        }
        if (n2 == 0 && n == 0) {
            return true;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Ran out of data or error on record ");
        ((StringBuilder)object).append(n3);
        Log.e(TAG, ((StringBuilder)object).toString());
        this.mKeyToPosMap.clear();
        return false;
    }

    @UnsupportedAppUsage
    public boolean getBoolean(int n) {
        this.checkType(n, 3);
        n = this.mParcel.readInt();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    @UnsupportedAppUsage
    public byte[] getByteArray(int n) {
        this.checkType(n, 7);
        return this.mParcel.createByteArray();
    }

    @UnsupportedAppUsage
    public Date getDate(int n) {
        this.checkType(n, 6);
        long l = this.mParcel.readLong();
        Object object = this.mParcel.readString();
        if (((String)object).length() == 0) {
            return new Date(l);
        }
        object = Calendar.getInstance(TimeZone.getTimeZone((String)object));
        ((Calendar)object).setTimeInMillis(l);
        return ((Calendar)object).getTime();
    }

    @UnsupportedAppUsage
    public double getDouble(int n) {
        this.checkType(n, 5);
        return this.mParcel.readDouble();
    }

    @UnsupportedAppUsage
    public int getInt(int n) {
        this.checkType(n, 2);
        return this.mParcel.readInt();
    }

    @UnsupportedAppUsage
    public long getLong(int n) {
        this.checkType(n, 4);
        return this.mParcel.readLong();
    }

    @UnsupportedAppUsage
    public String getString(int n) {
        this.checkType(n, 1);
        return this.mParcel.readString();
    }

    @UnsupportedAppUsage
    public boolean has(int n) {
        if (this.checkMetadataId(n)) {
            return this.mKeyToPosMap.containsKey(n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid key: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public Set<Integer> keySet() {
        return this.mKeyToPosMap.keySet();
    }

    @UnsupportedAppUsage
    public boolean parse(Parcel parcel) {
        if (parcel.dataAvail() < 8) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Not enough data ");
            stringBuilder.append(parcel.dataAvail());
            Log.e(TAG, stringBuilder.toString());
            return false;
        }
        int n = parcel.dataPosition();
        int n2 = parcel.readInt();
        if (parcel.dataAvail() + 4 >= n2 && n2 >= 8) {
            int n3 = parcel.readInt();
            if (n3 != 1296389185) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Marker missing ");
                stringBuilder.append(Integer.toHexString(n3));
                Log.e(TAG, stringBuilder.toString());
                parcel.setDataPosition(n);
                return false;
            }
            if (!this.scanAllRecords(parcel, n2 - 8)) {
                parcel.setDataPosition(n);
                return false;
            }
            this.mParcel = parcel;
            return true;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad size ");
        stringBuilder.append(n2);
        stringBuilder.append(" avail ");
        stringBuilder.append(parcel.dataAvail());
        stringBuilder.append(" position ");
        stringBuilder.append(n);
        Log.e(TAG, stringBuilder.toString());
        parcel.setDataPosition(n);
        return false;
    }
}

