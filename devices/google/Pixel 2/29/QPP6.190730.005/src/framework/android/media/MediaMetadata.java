/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Bitmap;
import android.media.MediaDescription;
import android.media.Rating;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.Set;

public final class MediaMetadata
implements Parcelable {
    public static final Parcelable.Creator<MediaMetadata> CREATOR;
    private static final SparseArray<String> EDITOR_KEY_MAPPING;
    private static final ArrayMap<String, Integer> METADATA_KEYS_TYPE;
    public static final String METADATA_KEY_ALBUM = "android.media.metadata.ALBUM";
    public static final String METADATA_KEY_ALBUM_ART = "android.media.metadata.ALBUM_ART";
    public static final String METADATA_KEY_ALBUM_ARTIST = "android.media.metadata.ALBUM_ARTIST";
    public static final String METADATA_KEY_ALBUM_ART_URI = "android.media.metadata.ALBUM_ART_URI";
    public static final String METADATA_KEY_ART = "android.media.metadata.ART";
    public static final String METADATA_KEY_ARTIST = "android.media.metadata.ARTIST";
    public static final String METADATA_KEY_ART_URI = "android.media.metadata.ART_URI";
    public static final String METADATA_KEY_AUTHOR = "android.media.metadata.AUTHOR";
    public static final String METADATA_KEY_BT_FOLDER_TYPE = "android.media.metadata.BT_FOLDER_TYPE";
    public static final String METADATA_KEY_COMPILATION = "android.media.metadata.COMPILATION";
    public static final String METADATA_KEY_COMPOSER = "android.media.metadata.COMPOSER";
    public static final String METADATA_KEY_DATE = "android.media.metadata.DATE";
    public static final String METADATA_KEY_DISC_NUMBER = "android.media.metadata.DISC_NUMBER";
    public static final String METADATA_KEY_DISPLAY_DESCRIPTION = "android.media.metadata.DISPLAY_DESCRIPTION";
    public static final String METADATA_KEY_DISPLAY_ICON = "android.media.metadata.DISPLAY_ICON";
    public static final String METADATA_KEY_DISPLAY_ICON_URI = "android.media.metadata.DISPLAY_ICON_URI";
    public static final String METADATA_KEY_DISPLAY_SUBTITLE = "android.media.metadata.DISPLAY_SUBTITLE";
    public static final String METADATA_KEY_DISPLAY_TITLE = "android.media.metadata.DISPLAY_TITLE";
    public static final String METADATA_KEY_DURATION = "android.media.metadata.DURATION";
    public static final String METADATA_KEY_GENRE = "android.media.metadata.GENRE";
    public static final String METADATA_KEY_MEDIA_ID = "android.media.metadata.MEDIA_ID";
    public static final String METADATA_KEY_MEDIA_URI = "android.media.metadata.MEDIA_URI";
    public static final String METADATA_KEY_NUM_TRACKS = "android.media.metadata.NUM_TRACKS";
    public static final String METADATA_KEY_RATING = "android.media.metadata.RATING";
    public static final String METADATA_KEY_TITLE = "android.media.metadata.TITLE";
    public static final String METADATA_KEY_TRACK_NUMBER = "android.media.metadata.TRACK_NUMBER";
    public static final String METADATA_KEY_USER_RATING = "android.media.metadata.USER_RATING";
    public static final String METADATA_KEY_WRITER = "android.media.metadata.WRITER";
    public static final String METADATA_KEY_YEAR = "android.media.metadata.YEAR";
    private static final int METADATA_TYPE_BITMAP = 2;
    private static final int METADATA_TYPE_INVALID = -1;
    private static final int METADATA_TYPE_LONG = 0;
    private static final int METADATA_TYPE_RATING = 3;
    private static final int METADATA_TYPE_TEXT = 1;
    private static final String[] PREFERRED_BITMAP_ORDER;
    private static final String[] PREFERRED_DESCRIPTION_ORDER;
    private static final String[] PREFERRED_URI_ORDER;
    private static final String TAG = "MediaMetadata";
    private final Bundle mBundle;
    private MediaDescription mDescription;

    static {
        PREFERRED_DESCRIPTION_ORDER = new String[]{METADATA_KEY_TITLE, METADATA_KEY_ARTIST, METADATA_KEY_ALBUM, METADATA_KEY_ALBUM_ARTIST, METADATA_KEY_WRITER, METADATA_KEY_AUTHOR, METADATA_KEY_COMPOSER};
        PREFERRED_BITMAP_ORDER = new String[]{METADATA_KEY_DISPLAY_ICON, METADATA_KEY_ART, METADATA_KEY_ALBUM_ART};
        PREFERRED_URI_ORDER = new String[]{METADATA_KEY_DISPLAY_ICON_URI, METADATA_KEY_ART_URI, METADATA_KEY_ALBUM_ART_URI};
        METADATA_KEYS_TYPE = new ArrayMap();
        Object object = METADATA_KEYS_TYPE;
        Integer n = 1;
        ((ArrayMap)object).put((String)METADATA_KEY_TITLE, (Integer)n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ARTIST, n);
        Object object2 = METADATA_KEYS_TYPE;
        object = 0;
        ((ArrayMap)object2).put((String)METADATA_KEY_DURATION, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_AUTHOR, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_WRITER, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_COMPOSER, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_COMPILATION, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DATE, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_YEAR, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_GENRE, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_TRACK_NUMBER, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_NUM_TRACKS, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISC_NUMBER, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ARTIST, n);
        Object object3 = METADATA_KEYS_TYPE;
        object2 = 2;
        ((ArrayMap)object3).put((String)METADATA_KEY_ART, (Integer)object2);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ART_URI, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ART, (Integer)object2);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ART_URI, n);
        ArrayMap<String, Integer> arrayMap = METADATA_KEYS_TYPE;
        object3 = 3;
        arrayMap.put(METADATA_KEY_USER_RATING, (Integer)object3);
        METADATA_KEYS_TYPE.put(METADATA_KEY_RATING, (Integer)object3);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_TITLE, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_SUBTITLE, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_DESCRIPTION, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_ICON, (Integer)object2);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_ICON_URI, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_BT_FOLDER_TYPE, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_MEDIA_ID, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_MEDIA_URI, n);
        EDITOR_KEY_MAPPING = new SparseArray();
        EDITOR_KEY_MAPPING.put(100, METADATA_KEY_ART);
        EDITOR_KEY_MAPPING.put(101, METADATA_KEY_RATING);
        EDITOR_KEY_MAPPING.put(268435457, METADATA_KEY_USER_RATING);
        EDITOR_KEY_MAPPING.put(1, METADATA_KEY_ALBUM);
        EDITOR_KEY_MAPPING.put(13, METADATA_KEY_ALBUM_ARTIST);
        EDITOR_KEY_MAPPING.put(2, METADATA_KEY_ARTIST);
        EDITOR_KEY_MAPPING.put(3, METADATA_KEY_AUTHOR);
        EDITOR_KEY_MAPPING.put(0, METADATA_KEY_TRACK_NUMBER);
        EDITOR_KEY_MAPPING.put(4, METADATA_KEY_COMPOSER);
        EDITOR_KEY_MAPPING.put(15, METADATA_KEY_COMPILATION);
        EDITOR_KEY_MAPPING.put(5, METADATA_KEY_DATE);
        EDITOR_KEY_MAPPING.put(14, METADATA_KEY_DISC_NUMBER);
        EDITOR_KEY_MAPPING.put(9, METADATA_KEY_DURATION);
        EDITOR_KEY_MAPPING.put(6, METADATA_KEY_GENRE);
        EDITOR_KEY_MAPPING.put(10, METADATA_KEY_NUM_TRACKS);
        EDITOR_KEY_MAPPING.put(7, METADATA_KEY_TITLE);
        EDITOR_KEY_MAPPING.put(11, METADATA_KEY_WRITER);
        EDITOR_KEY_MAPPING.put(8, METADATA_KEY_YEAR);
        CREATOR = new Parcelable.Creator<MediaMetadata>(){

            @Override
            public MediaMetadata createFromParcel(Parcel parcel) {
                return new MediaMetadata(parcel);
            }

            public MediaMetadata[] newArray(int n) {
                return new MediaMetadata[n];
            }
        };
    }

    private MediaMetadata(Bundle bundle) {
        this.mBundle = new Bundle(bundle);
    }

    private MediaMetadata(Parcel parcel) {
        this.mBundle = parcel.readBundle();
    }

    @UnsupportedAppUsage
    public static String getKeyFromMetadataEditorKey(int n) {
        return EDITOR_KEY_MAPPING.get(n, null);
    }

    public boolean containsKey(String string2) {
        return this.mBundle.containsKey(string2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof MediaMetadata)) {
            return false;
        }
        MediaMetadata mediaMetadata = (MediaMetadata)object;
        for (int i = 0; i < METADATA_KEYS_TYPE.size(); ++i) {
            object = METADATA_KEYS_TYPE.keyAt(i);
            int n = METADATA_KEYS_TYPE.valueAt(i);
            if (!(n != 0 ? n == 1 && !Objects.equals(this.getString((String)object), mediaMetadata.getString((String)object)) : this.getLong((String)object) != mediaMetadata.getLong((String)object))) continue;
            return false;
        }
        return true;
    }

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

    public MediaDescription getDescription() {
        Object object;
        int n;
        Object object2 = this.mDescription;
        if (object2 != null) {
            return object2;
        }
        String string2 = this.getString(METADATA_KEY_MEDIA_ID);
        CharSequence[] arrcharSequence = new CharSequence[3];
        Object object3 = null;
        Object object4 = null;
        object2 = this.getText(METADATA_KEY_DISPLAY_TITLE);
        if (!TextUtils.isEmpty((CharSequence)object2)) {
            arrcharSequence[0] = object2;
            arrcharSequence[1] = this.getText(METADATA_KEY_DISPLAY_SUBTITLE);
            arrcharSequence[2] = this.getText(METADATA_KEY_DISPLAY_DESCRIPTION);
        } else {
            int n2 = 0;
            for (n = 0; n2 < arrcharSequence.length && n < ((String[])(object2 = PREFERRED_DESCRIPTION_ORDER)).length; ++n) {
                object2 = this.getText(object2[n]);
                int n3 = n2;
                if (!TextUtils.isEmpty((CharSequence)object2)) {
                    arrcharSequence[n2] = object2;
                    n3 = n2 + 1;
                }
                n2 = n3;
            }
        }
        n = 0;
        do {
            object = PREFERRED_BITMAP_ORDER;
            object2 = object3;
            if (n >= ((String[])object).length || (object2 = this.getBitmap(object[n])) != null) break;
            ++n;
        } while (true);
        n = 0;
        do {
            object = PREFERRED_URI_ORDER;
            object3 = object4;
            if (n >= ((String[])object).length) break;
            object3 = this.getString(object[n]);
            if (!TextUtils.isEmpty((CharSequence)object3)) {
                object3 = Uri.parse((String)object3);
                break;
            }
            ++n;
        } while (true);
        object4 = null;
        object = this.getString(METADATA_KEY_MEDIA_URI);
        if (!TextUtils.isEmpty((CharSequence)object)) {
            object4 = Uri.parse((String)object);
        }
        object = new MediaDescription.Builder();
        ((MediaDescription.Builder)object).setMediaId(string2);
        ((MediaDescription.Builder)object).setTitle(arrcharSequence[0]);
        ((MediaDescription.Builder)object).setSubtitle(arrcharSequence[1]);
        ((MediaDescription.Builder)object).setDescription(arrcharSequence[2]);
        ((MediaDescription.Builder)object).setIconBitmap((Bitmap)object2);
        ((MediaDescription.Builder)object).setIconUri((Uri)object3);
        ((MediaDescription.Builder)object).setMediaUri((Uri)object4);
        if (this.mBundle.containsKey(METADATA_KEY_BT_FOLDER_TYPE)) {
            object2 = new Bundle();
            ((BaseBundle)object2).putLong("android.media.extra.BT_FOLDER_TYPE", this.getLong(METADATA_KEY_BT_FOLDER_TYPE));
            ((MediaDescription.Builder)object).setExtras((Bundle)object2);
        }
        this.mDescription = ((MediaDescription.Builder)object).build();
        return this.mDescription;
    }

    public long getLong(String string2) {
        return this.mBundle.getLong(string2, 0L);
    }

    public Rating getRating(String object) {
        Object var2_3 = null;
        try {
            object = (Rating)this.mBundle.getParcelable((String)object);
        }
        catch (Exception exception) {
            Log.w(TAG, "Failed to retrieve a key as Rating.", exception);
            object = var2_3;
        }
        return object;
    }

    public String getString(String charSequence) {
        if ((charSequence = this.getText((String)charSequence)) != null) {
            return charSequence.toString();
        }
        return null;
    }

    public CharSequence getText(String string2) {
        return this.mBundle.getCharSequence(string2);
    }

    public int hashCode() {
        int n = 17;
        for (int i = 0; i < METADATA_KEYS_TYPE.size(); ++i) {
            String string2 = METADATA_KEYS_TYPE.keyAt(i);
            int n2 = METADATA_KEYS_TYPE.valueAt(i);
            if (n2 != 0) {
                if (n2 != 1) continue;
                n = n * 31 + Objects.hash(this.getString(string2));
                continue;
            }
            n = n * 31 + Long.hashCode(this.getLong(string2));
        }
        return n;
    }

    public Set<String> keySet() {
        return this.mBundle.keySet();
    }

    public int size() {
        return this.mBundle.size();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBundle(this.mBundle);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface BitmapKey {
    }

    public static final class Builder {
        private final Bundle mBundle;

        public Builder() {
            this.mBundle = new Bundle();
        }

        public Builder(MediaMetadata mediaMetadata) {
            this.mBundle = new Bundle(mediaMetadata.mBundle);
        }

        public Builder(MediaMetadata object, int n) {
            this((MediaMetadata)object);
            for (String string2 : this.mBundle.keySet()) {
                Object object2 = this.mBundle.get(string2);
                if (object2 == null || !(object2 instanceof Bitmap) || ((Bitmap)(object2 = (Bitmap)object2)).getHeight() <= n && ((Bitmap)object2).getWidth() <= n) continue;
                this.putBitmap(string2, this.scaleBitmap((Bitmap)object2, n));
            }
        }

        private Bitmap scaleBitmap(Bitmap bitmap, int n) {
            float f = n;
            f = Math.min(f / (float)bitmap.getWidth(), f / (float)bitmap.getHeight());
            n = (int)((float)bitmap.getHeight() * f);
            return Bitmap.createScaledBitmap(bitmap, (int)((float)bitmap.getWidth() * f), n, true);
        }

        public MediaMetadata build() {
            return new MediaMetadata(this.mBundle);
        }

        public Builder putBitmap(String string2, Bitmap object) {
            if (METADATA_KEYS_TYPE.containsKey(string2) && (Integer)METADATA_KEYS_TYPE.get(string2) != 2) {
                object = new StringBuilder();
                ((StringBuilder)object).append("The ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" key cannot be used to put a Bitmap");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            this.mBundle.putParcelable(string2, (Parcelable)object);
            return this;
        }

        public Builder putLong(String string2, long l) {
            if (METADATA_KEYS_TYPE.containsKey(string2) && (Integer)METADATA_KEYS_TYPE.get(string2) != 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("The ");
                stringBuilder.append(string2);
                stringBuilder.append(" key cannot be used to put a long");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.mBundle.putLong(string2, l);
            return this;
        }

        public Builder putRating(String string2, Rating object) {
            if (METADATA_KEYS_TYPE.containsKey(string2) && (Integer)METADATA_KEYS_TYPE.get(string2) != 3) {
                object = new StringBuilder();
                ((StringBuilder)object).append("The ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" key cannot be used to put a Rating");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            this.mBundle.putParcelable(string2, (Parcelable)object);
            return this;
        }

        public Builder putString(String string2, String charSequence) {
            if (METADATA_KEYS_TYPE.containsKey(string2) && (Integer)METADATA_KEYS_TYPE.get(string2) != 1) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("The ");
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append(" key cannot be used to put a String");
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            this.mBundle.putCharSequence(string2, charSequence);
            return this;
        }

        public Builder putText(String string2, CharSequence charSequence) {
            if (METADATA_KEYS_TYPE.containsKey(string2) && (Integer)METADATA_KEYS_TYPE.get(string2) != 1) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("The ");
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append(" key cannot be used to put a CharSequence");
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            this.mBundle.putCharSequence(string2, charSequence);
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface LongKey {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RatingKey {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TextKey {
    }

}

