/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseIntArray;

@Deprecated
public abstract class MediaMetadataEditor {
    public static final int BITMAP_KEY_ARTWORK = 100;
    public static final int KEY_EDITABLE_MASK = 536870911;
    protected static final SparseIntArray METADATA_KEYS_TYPE = new SparseIntArray(17);
    protected static final int METADATA_TYPE_BITMAP = 2;
    protected static final int METADATA_TYPE_INVALID = -1;
    protected static final int METADATA_TYPE_LONG = 0;
    protected static final int METADATA_TYPE_RATING = 3;
    protected static final int METADATA_TYPE_STRING = 1;
    public static final int RATING_KEY_BY_OTHERS = 101;
    public static final int RATING_KEY_BY_USER = 268435457;
    private static final String TAG = "MediaMetadataEditor";
    protected boolean mApplied = false;
    protected boolean mArtworkChanged = false;
    protected long mEditableKeys;
    protected Bitmap mEditorArtwork;
    protected Bundle mEditorMetadata;
    protected MediaMetadata.Builder mMetadataBuilder;
    protected boolean mMetadataChanged = false;

    static {
        METADATA_KEYS_TYPE.put(0, 0);
        METADATA_KEYS_TYPE.put(14, 0);
        METADATA_KEYS_TYPE.put(9, 0);
        METADATA_KEYS_TYPE.put(8, 0);
        METADATA_KEYS_TYPE.put(1, 1);
        METADATA_KEYS_TYPE.put(13, 1);
        METADATA_KEYS_TYPE.put(7, 1);
        METADATA_KEYS_TYPE.put(2, 1);
        METADATA_KEYS_TYPE.put(3, 1);
        METADATA_KEYS_TYPE.put(15, 1);
        METADATA_KEYS_TYPE.put(4, 1);
        METADATA_KEYS_TYPE.put(5, 1);
        METADATA_KEYS_TYPE.put(6, 1);
        METADATA_KEYS_TYPE.put(11, 1);
        METADATA_KEYS_TYPE.put(100, 2);
        METADATA_KEYS_TYPE.put(101, 3);
        METADATA_KEYS_TYPE.put(268435457, 3);
    }

    protected MediaMetadataEditor() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addEditableKey(int n) {
        synchronized (this) {
            if (this.mApplied) {
                Log.e(TAG, "Can't change editable keys of a previously applied MetadataEditor");
                return;
            }
            if (n == 268435457) {
                this.mEditableKeys |= (long)(536870911 & n);
                this.mMetadataChanged = true;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Metadata key ");
                stringBuilder.append(n);
                stringBuilder.append(" cannot be edited");
                Log.e(TAG, stringBuilder.toString());
            }
            return;
        }
    }

    public abstract void apply();

    public void clear() {
        synchronized (this) {
            MediaMetadata.Builder builder;
            if (this.mApplied) {
                Log.e("MediaMetadataEditor", "Can't clear a previously applied MediaMetadataEditor");
                return;
            }
            this.mEditorMetadata.clear();
            this.mEditorArtwork = null;
            this.mMetadataBuilder = builder = new MediaMetadata.Builder();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Bitmap getBitmap(int n, Bitmap object) throws IllegalArgumentException {
        synchronized (this) {
            Throwable throwable2;
            if (n == 100) {
                try {
                    if (this.mEditorArtwork == null) return object;
                    return this.mEditorArtwork;
                }
                catch (Throwable throwable2) {}
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid type 'Bitmap' for key ");
                stringBuilder.append(n);
                object = new IllegalArgumentException(stringBuilder.toString());
                throw object;
            }
            throw throwable2;
        }
    }

    public int[] getEditableKeys() {
        synchronized (this) {
            block3 : {
                if (this.mEditableKeys != 0x10000001L) break block3;
                return new int[]{268435457};
            }
            return null;
        }
    }

    public long getLong(int n, long l) throws IllegalArgumentException {
        synchronized (this) {
            block4 : {
                if (METADATA_KEYS_TYPE.get(n, -1) != 0) break block4;
                l = this.mEditorMetadata.getLong(String.valueOf(n), l);
                return l;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid type 'long' for key ");
            stringBuilder.append(n);
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
            throw illegalArgumentException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Object getObject(int n, Object object) throws IllegalArgumentException {
        synchronized (this) {
            int n2 = METADATA_KEYS_TYPE.get(n, -1);
            if (n2 != 0) {
                if (n2 != 1) {
                    if (n2 != 2) {
                        if (n2 == 3) {
                            if (!this.mEditorMetadata.containsKey(String.valueOf(n))) return object;
                            return this.mEditorMetadata.getParcelable(String.valueOf(n));
                        }
                    } else if (n == 100) {
                        if (this.mEditorArtwork == null) return object;
                        return this.mEditorArtwork;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid key ");
                    ((StringBuilder)object).append(n);
                    IllegalArgumentException illegalArgumentException = new IllegalArgumentException(((StringBuilder)object).toString());
                    throw illegalArgumentException;
                }
                if (!this.mEditorMetadata.containsKey(String.valueOf(n))) return object;
                return this.mEditorMetadata.getString(String.valueOf(n));
            }
            if (!this.mEditorMetadata.containsKey(String.valueOf(n))) return object;
            long l = this.mEditorMetadata.getLong(String.valueOf(n));
            return l;
        }
    }

    public String getString(int n, String charSequence) throws IllegalArgumentException {
        synchronized (this) {
            block4 : {
                if (METADATA_KEYS_TYPE.get(n, -1) != 1) break block4;
                charSequence = this.mEditorMetadata.getString(String.valueOf(n), (String)charSequence);
                return charSequence;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Invalid type 'String' for key ");
            ((StringBuilder)charSequence).append(n);
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(((StringBuilder)charSequence).toString());
            throw illegalArgumentException;
        }
    }

    public MediaMetadataEditor putBitmap(int n, Bitmap object) throws IllegalArgumentException {
        synchronized (this) {
            block6 : {
                if (this.mApplied) {
                    Log.e("MediaMetadataEditor", "Can't edit a previously applied MediaMetadataEditor");
                    return this;
                }
                if (n != 100) break block6;
                this.mEditorArtwork = object;
                this.mArtworkChanged = true;
                return this;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid type 'Bitmap' for key ");
            ((StringBuilder)object).append(n);
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(((StringBuilder)object).toString());
            throw illegalArgumentException;
        }
    }

    public MediaMetadataEditor putLong(int n, long l) throws IllegalArgumentException {
        synchronized (this) {
            block6 : {
                block5 : {
                    if (!this.mApplied) break block5;
                    Log.e("MediaMetadataEditor", "Can't edit a previously applied MediaMetadataEditor");
                    return this;
                }
                if (METADATA_KEYS_TYPE.get(n, -1) != 0) break block6;
                this.mEditorMetadata.putLong(String.valueOf(n), l);
                this.mMetadataChanged = true;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid type 'long' for key ");
            stringBuilder.append(n);
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
            throw illegalArgumentException;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public MediaMetadataEditor putObject(int n, Object object) throws IllegalArgumentException {
        synchronized (this) {
            block15 : {
                block11 : {
                    block12 : {
                        block13 : {
                            block14 : {
                                block10 : {
                                    if (!this.mApplied) break block10;
                                    Log.e("MediaMetadataEditor", "Can't edit a previously applied MediaMetadataEditor");
                                    return this;
                                }
                                int n2 = METADATA_KEYS_TYPE.get(n, -1);
                                if (n2 == 0) break block11;
                                if (n2 == 1) break block12;
                                if (n2 == 2) break block13;
                                if (n2 != 3) break block14;
                                this.mEditorMetadata.putParcelable(String.valueOf(n), (Parcelable)object);
                                this.mMetadataChanged = true;
                                return this;
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Invalid key ");
                            stringBuilder.append(n);
                            object = new IllegalArgumentException(stringBuilder.toString());
                            throw object;
                        }
                        if (object == null) return this.putBitmap(n, (Bitmap)object);
                        if (object instanceof Bitmap) return this.putBitmap(n, (Bitmap)object);
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Not a Bitmap for key ");
                        ((StringBuilder)object).append(n);
                        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(((StringBuilder)object).toString());
                        throw illegalArgumentException;
                    }
                    if (object == null) return this.putString(n, (String)object);
                    if (object instanceof String) return this.putString(n, (String)object);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Not a String for key ");
                    stringBuilder.append(n);
                    object = new IllegalArgumentException(stringBuilder.toString());
                    throw object;
                }
                if (!(object instanceof Long)) break block15;
                return this.putLong(n, (Long)object);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Not a non-null Long for key ");
            stringBuilder.append(n);
            object = new IllegalArgumentException(stringBuilder.toString());
            throw object;
        }
    }

    public MediaMetadataEditor putString(int n, String object) throws IllegalArgumentException {
        synchronized (this) {
            block6 : {
                block5 : {
                    if (!this.mApplied) break block5;
                    Log.e("MediaMetadataEditor", "Can't edit a previously applied MediaMetadataEditor");
                    return this;
                }
                if (METADATA_KEYS_TYPE.get(n, -1) != 1) break block6;
                this.mEditorMetadata.putString(String.valueOf(n), (String)object);
                this.mMetadataChanged = true;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid type 'String' for key ");
            stringBuilder.append(n);
            object = new IllegalArgumentException(stringBuilder.toString());
            throw object;
        }
    }

    public void removeEditableKeys() {
        synchronized (this) {
            if (this.mApplied) {
                Log.e("MediaMetadataEditor", "Can't remove all editable keys of a previously applied MetadataEditor");
                return;
            }
            if (this.mEditableKeys != 0L) {
                this.mEditableKeys = 0L;
                this.mMetadataChanged = true;
            }
            return;
        }
    }
}

