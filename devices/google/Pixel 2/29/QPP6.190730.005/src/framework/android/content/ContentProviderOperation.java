/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentProvider;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ContentProviderOperation
implements Parcelable {
    public static final Parcelable.Creator<ContentProviderOperation> CREATOR = new Parcelable.Creator<ContentProviderOperation>(){

        @Override
        public ContentProviderOperation createFromParcel(Parcel parcel) {
            return new ContentProviderOperation(parcel);
        }

        public ContentProviderOperation[] newArray(int n) {
            return new ContentProviderOperation[n];
        }
    };
    private static final String TAG = "ContentProviderOperation";
    public static final int TYPE_ASSERT = 4;
    @UnsupportedAppUsage
    public static final int TYPE_DELETE = 3;
    @UnsupportedAppUsage
    public static final int TYPE_INSERT = 1;
    @UnsupportedAppUsage
    public static final int TYPE_UPDATE = 2;
    private final Integer mExpectedCount;
    private final boolean mFailureAllowed;
    @UnsupportedAppUsage
    private final String mSelection;
    private final String[] mSelectionArgs;
    private final Map<Integer, Integer> mSelectionArgsBackReferences;
    @UnsupportedAppUsage
    private final int mType;
    @UnsupportedAppUsage
    private final Uri mUri;
    private final ContentValues mValues;
    private final ContentValues mValuesBackReferences;
    private final boolean mYieldAllowed;

    private ContentProviderOperation(Builder builder) {
        this.mType = builder.mType;
        this.mUri = builder.mUri;
        this.mValues = builder.mValues;
        this.mSelection = builder.mSelection;
        this.mSelectionArgs = builder.mSelectionArgs;
        this.mExpectedCount = builder.mExpectedCount;
        this.mSelectionArgsBackReferences = builder.mSelectionArgsBackReferences;
        this.mValuesBackReferences = builder.mValuesBackReferences;
        this.mYieldAllowed = builder.mYieldAllowed;
        this.mFailureAllowed = builder.mFailureAllowed;
    }

    public ContentProviderOperation(ContentProviderOperation contentProviderOperation, Uri uri) {
        this.mType = contentProviderOperation.mType;
        this.mUri = uri;
        this.mValues = contentProviderOperation.mValues;
        this.mSelection = contentProviderOperation.mSelection;
        this.mSelectionArgs = contentProviderOperation.mSelectionArgs;
        this.mExpectedCount = contentProviderOperation.mExpectedCount;
        this.mSelectionArgsBackReferences = contentProviderOperation.mSelectionArgsBackReferences;
        this.mValuesBackReferences = contentProviderOperation.mValuesBackReferences;
        this.mYieldAllowed = contentProviderOperation.mYieldAllowed;
        this.mFailureAllowed = contentProviderOperation.mFailureAllowed;
    }

    private ContentProviderOperation(Parcel parcel) {
        this.mType = parcel.readInt();
        this.mUri = Uri.CREATOR.createFromParcel(parcel);
        int n = parcel.readInt();
        Object var3_3 = null;
        Object object = n != 0 ? ContentValues.CREATOR.createFromParcel(parcel) : null;
        this.mValues = object;
        object = parcel.readInt() != 0 ? parcel.readString() : null;
        this.mSelection = object;
        object = parcel.readInt() != 0 ? parcel.readStringArray() : null;
        this.mSelectionArgs = object;
        object = parcel.readInt() != 0 ? Integer.valueOf(parcel.readInt()) : null;
        this.mExpectedCount = object;
        object = parcel.readInt() != 0 ? ContentValues.CREATOR.createFromParcel(parcel) : null;
        this.mValuesBackReferences = object;
        object = parcel.readInt() != 0 ? new HashMap() : var3_3;
        this.mSelectionArgsBackReferences = object;
        if (this.mSelectionArgsBackReferences != null) {
            int n2 = parcel.readInt();
            for (n = 0; n < n2; ++n) {
                this.mSelectionArgsBackReferences.put(parcel.readInt(), parcel.readInt());
            }
        }
        n = parcel.readInt();
        boolean bl = false;
        boolean bl2 = n != 0;
        this.mYieldAllowed = bl2;
        bl2 = bl;
        if (parcel.readInt() != 0) {
            bl2 = true;
        }
        this.mFailureAllowed = bl2;
    }

    private ContentProviderResult applyInternal(ContentProvider object, ContentProviderResult[] object2, int n) throws OperationApplicationException {
        block18 : {
            block16 : {
                int n2;
                block14 : {
                    Object object3;
                    Object object4;
                    Object object5;
                    block17 : {
                        block15 : {
                            object3 = this.resolveValueBackReferences((ContentProviderResult[])object2, n);
                            object5 = this.resolveSelectionArgsBackReferences((ContentProviderResult[])object2, n);
                            n = this.mType;
                            if (n == 1) {
                                if ((object = ((ContentProvider)object).insert(this.mUri, (ContentValues)object3)) != null) {
                                    return new ContentProviderResult((Uri)object);
                                }
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Insert into ");
                                ((StringBuilder)object).append(this.mUri);
                                ((StringBuilder)object).append(" returned no result");
                                throw new OperationApplicationException(((StringBuilder)object).toString());
                            }
                            if (n != 3) break block15;
                            n = ((ContentProvider)object).delete(this.mUri, this.mSelection, (String[])object5);
                            break block16;
                        }
                        if (n != 2) break block17;
                        n = ((ContentProvider)object).update(this.mUri, (ContentValues)object3, this.mSelection, (String[])object5);
                        break block16;
                    }
                    if (n != 4) break block18;
                    if (object3 != null) {
                        object2 = new ArrayList();
                        object4 = ((ContentValues)object3).valueSet().iterator();
                        while (object4.hasNext()) {
                            ((ArrayList)object2).add(object4.next().getKey());
                        }
                        object2 = ((ArrayList)object2).toArray(new String[((ArrayList)object2).size()]);
                    } else {
                        object2 = null;
                    }
                    object = ((ContentProvider)object).query(this.mUri, (String[])object2, this.mSelection, (String[])object5, null);
                    n2 = object.getCount();
                    if (object2 == null) break block14;
                    block6 : do {
                        if (!object.moveToNext()) break block14;
                        n = 0;
                        do {
                            if (n >= ((Object)object2).length) continue block6;
                            object4 = object.getString(n);
                            if (!TextUtils.equals((CharSequence)object4, (CharSequence)(object5 = ((ContentValues)object3).getAsString((String)object2[n])))) break block6;
                            ++n;
                        } while (true);
                        break;
                    } while (true);
                    try {
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("Found value ");
                        ((StringBuilder)object3).append((String)object4);
                        ((StringBuilder)object3).append(" when expected ");
                        ((StringBuilder)object3).append((String)object5);
                        ((StringBuilder)object3).append(" for column ");
                        ((StringBuilder)object3).append((String)object2[n]);
                        OperationApplicationException operationApplicationException = new OperationApplicationException(((StringBuilder)object3).toString());
                        throw operationApplicationException;
                    }
                    catch (Throwable throwable) {
                        object.close();
                        throw throwable;
                    }
                }
                object.close();
                n = n2;
            }
            object = this.mExpectedCount;
            if (object != null && (Integer)object != n) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Expected ");
                ((StringBuilder)object).append(this.mExpectedCount);
                ((StringBuilder)object).append(" rows but actual ");
                ((StringBuilder)object).append(n);
                throw new OperationApplicationException(((StringBuilder)object).toString());
            }
            return new ContentProviderResult(n);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("bad type, ");
        ((StringBuilder)object).append(this.mType);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    private long backRefToValue(ContentProviderResult[] object, int n, Integer n2) {
        if (n2 < n) {
            object = object[n2];
            long l = ((ContentProviderResult)object).uri != null ? ContentUris.parseId(((ContentProviderResult)object).uri) : (long)((ContentProviderResult)object).count.intValue();
            return l;
        }
        Log.e(TAG, this.toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("asked for back ref ");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(" but there are only ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" back refs");
        throw new ArrayIndexOutOfBoundsException(((StringBuilder)object).toString());
    }

    public static Builder newAssertQuery(Uri uri) {
        return new Builder(4, uri);
    }

    public static Builder newDelete(Uri uri) {
        return new Builder(3, uri);
    }

    public static Builder newInsert(Uri uri) {
        return new Builder(1, uri);
    }

    public static Builder newUpdate(Uri uri) {
        return new Builder(2, uri);
    }

    public ContentProviderResult apply(ContentProvider object, ContentProviderResult[] arrcontentProviderResult, int n) throws OperationApplicationException {
        if (this.mFailureAllowed) {
            try {
                object = this.applyInternal((ContentProvider)object, arrcontentProviderResult, n);
                return object;
            }
            catch (Exception exception) {
                return new ContentProviderResult(exception.getMessage());
            }
        }
        return this.applyInternal((ContentProvider)object, arrcontentProviderResult, n);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public int getType() {
        return this.mType;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public boolean isAssertQuery() {
        boolean bl = this.mType == 4;
        return bl;
    }

    public boolean isDelete() {
        boolean bl = this.mType == 3;
        return bl;
    }

    public boolean isFailureAllowed() {
        return this.mFailureAllowed;
    }

    public boolean isInsert() {
        int n = this.mType;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public boolean isReadOperation() {
        boolean bl = this.mType == 4;
        return bl;
    }

    public boolean isUpdate() {
        boolean bl = this.mType == 2;
        return bl;
    }

    public boolean isWriteOperation() {
        boolean bl;
        int n = this.mType;
        boolean bl2 = bl = true;
        if (n != 3) {
            bl2 = bl;
            if (n != 1) {
                bl2 = n == 2 ? bl : false;
            }
        }
        return bl2;
    }

    public boolean isYieldAllowed() {
        return this.mYieldAllowed;
    }

    public String[] resolveSelectionArgsBackReferences(ContentProviderResult[] arrcontentProviderResult, int n) {
        if (this.mSelectionArgsBackReferences == null) {
            return this.mSelectionArgs;
        }
        String[] object2 = this.mSelectionArgs;
        String[] arrstring = new String[object2.length];
        System.arraycopy(object2, 0, arrstring, 0, object2.length);
        for (Map.Entry<Integer, Integer> entry : this.mSelectionArgsBackReferences.entrySet()) {
            Integer n2 = entry.getKey();
            int n3 = entry.getValue();
            arrstring[n2.intValue()] = String.valueOf(this.backRefToValue(arrcontentProviderResult, n, n3));
        }
        return arrstring;
    }

    public ContentValues resolveValueBackReferences(ContentProviderResult[] object, int n) {
        if (this.mValuesBackReferences == null) {
            return this.mValues;
        }
        ContentValues contentValues = this.mValues;
        contentValues = contentValues == null ? new ContentValues() : new ContentValues(contentValues);
        Iterator<Map.Entry<String, Object>> iterator = this.mValuesBackReferences.valueSet().iterator();
        while (iterator.hasNext()) {
            String string2 = iterator.next().getKey();
            Integer n2 = this.mValuesBackReferences.getAsInteger(string2);
            if (n2 != null) {
                contentValues.put(string2, this.backRefToValue((ContentProviderResult[])object, n, n2));
                continue;
            }
            Log.e(TAG, this.toString());
            object = new StringBuilder();
            ((StringBuilder)object).append("values backref ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" is not an integer");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        return contentValues;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mType: ");
        stringBuilder.append(this.mType);
        stringBuilder.append(", mUri: ");
        stringBuilder.append(this.mUri);
        stringBuilder.append(", mSelection: ");
        stringBuilder.append(this.mSelection);
        stringBuilder.append(", mExpectedCount: ");
        stringBuilder.append(this.mExpectedCount);
        stringBuilder.append(", mYieldAllowed: ");
        stringBuilder.append(this.mYieldAllowed);
        stringBuilder.append(", mValues: ");
        stringBuilder.append(this.mValues);
        stringBuilder.append(", mValuesBackReferences: ");
        stringBuilder.append(this.mValuesBackReferences);
        stringBuilder.append(", mSelectionArgsBackReferences: ");
        stringBuilder.append(this.mSelectionArgsBackReferences);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mType);
        Uri.writeToParcel(parcel, this.mUri);
        if (this.mValues != null) {
            parcel.writeInt(1);
            this.mValues.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.mSelection != null) {
            parcel.writeInt(1);
            parcel.writeString(this.mSelection);
        } else {
            parcel.writeInt(0);
        }
        if (this.mSelectionArgs != null) {
            parcel.writeInt(1);
            parcel.writeStringArray(this.mSelectionArgs);
        } else {
            parcel.writeInt(0);
        }
        if (this.mExpectedCount != null) {
            parcel.writeInt(1);
            parcel.writeInt(this.mExpectedCount);
        } else {
            parcel.writeInt(0);
        }
        if (this.mValuesBackReferences != null) {
            parcel.writeInt(1);
            this.mValuesBackReferences.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.mSelectionArgsBackReferences != null) {
            parcel.writeInt(1);
            parcel.writeInt(this.mSelectionArgsBackReferences.size());
            for (Map.Entry<Integer, Integer> entry : this.mSelectionArgsBackReferences.entrySet()) {
                parcel.writeInt(entry.getKey());
                parcel.writeInt(entry.getValue());
            }
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt((int)this.mYieldAllowed);
        parcel.writeInt((int)this.mFailureAllowed);
    }

    public static class Builder {
        private Integer mExpectedCount;
        private boolean mFailureAllowed;
        private String mSelection;
        private String[] mSelectionArgs;
        private Map<Integer, Integer> mSelectionArgsBackReferences;
        private final int mType;
        private final Uri mUri;
        private ContentValues mValues;
        private ContentValues mValuesBackReferences;
        private boolean mYieldAllowed;

        private Builder(int n, Uri uri) {
            if (uri != null) {
                this.mType = n;
                this.mUri = uri;
                return;
            }
            throw new IllegalArgumentException("uri must not be null");
        }

        public ContentProviderOperation build() {
            ContentValues contentValues;
            if (this.mType == 2 && ((contentValues = this.mValues) == null || contentValues.isEmpty()) && ((contentValues = this.mValuesBackReferences) == null || contentValues.isEmpty())) {
                throw new IllegalArgumentException("Empty values");
            }
            if (this.mType == 4 && ((contentValues = this.mValues) == null || contentValues.isEmpty()) && ((contentValues = this.mValuesBackReferences) == null || contentValues.isEmpty()) && this.mExpectedCount == null) {
                throw new IllegalArgumentException("Empty values");
            }
            return new ContentProviderOperation(this);
        }

        public Builder withExpectedCount(int n) {
            int n2 = this.mType;
            if (n2 != 2 && n2 != 3 && n2 != 4) {
                throw new IllegalArgumentException("only updates, deletes, and asserts can have expected counts");
            }
            this.mExpectedCount = n;
            return this;
        }

        public Builder withFailureAllowed(boolean bl) {
            this.mFailureAllowed = bl;
            return this;
        }

        public Builder withSelection(String string2, String[] arrstring) {
            int n = this.mType;
            if (n != 2 && n != 3 && n != 4) {
                throw new IllegalArgumentException("only updates, deletes, and asserts can have selections");
            }
            this.mSelection = string2;
            if (arrstring == null) {
                this.mSelectionArgs = null;
            } else {
                this.mSelectionArgs = new String[arrstring.length];
                System.arraycopy(arrstring, 0, this.mSelectionArgs, 0, arrstring.length);
            }
            return this;
        }

        public Builder withSelectionBackReference(int n, int n2) {
            int n3 = this.mType;
            if (n3 != 2 && n3 != 3 && n3 != 4) {
                throw new IllegalArgumentException("only updates, deletes, and asserts can have selection back-references");
            }
            if (this.mSelectionArgsBackReferences == null) {
                this.mSelectionArgsBackReferences = new HashMap<Integer, Integer>();
            }
            this.mSelectionArgsBackReferences.put(n, n2);
            return this;
        }

        public Builder withValue(String charSequence, Object object) {
            block15 : {
                block6 : {
                    block14 : {
                        block13 : {
                            block12 : {
                                block11 : {
                                    block10 : {
                                        block9 : {
                                            block8 : {
                                                block7 : {
                                                    block5 : {
                                                        int n = this.mType;
                                                        if (n != 1 && n != 2 && n != 4) {
                                                            throw new IllegalArgumentException("only inserts and updates can have values");
                                                        }
                                                        if (this.mValues == null) {
                                                            this.mValues = new ContentValues();
                                                        }
                                                        if (object != null) break block5;
                                                        this.mValues.putNull((String)charSequence);
                                                        break block6;
                                                    }
                                                    if (!(object instanceof String)) break block7;
                                                    this.mValues.put((String)charSequence, (String)object);
                                                    break block6;
                                                }
                                                if (!(object instanceof Byte)) break block8;
                                                this.mValues.put((String)charSequence, (Byte)object);
                                                break block6;
                                            }
                                            if (!(object instanceof Short)) break block9;
                                            this.mValues.put((String)charSequence, (Short)object);
                                            break block6;
                                        }
                                        if (!(object instanceof Integer)) break block10;
                                        this.mValues.put((String)charSequence, (Integer)object);
                                        break block6;
                                    }
                                    if (!(object instanceof Long)) break block11;
                                    this.mValues.put((String)charSequence, (Long)object);
                                    break block6;
                                }
                                if (!(object instanceof Float)) break block12;
                                this.mValues.put((String)charSequence, (Float)object);
                                break block6;
                            }
                            if (!(object instanceof Double)) break block13;
                            this.mValues.put((String)charSequence, (Double)object);
                            break block6;
                        }
                        if (!(object instanceof Boolean)) break block14;
                        this.mValues.put((String)charSequence, (Boolean)object);
                        break block6;
                    }
                    if (!(object instanceof byte[])) break block15;
                    this.mValues.put((String)charSequence, (byte[])object);
                }
                return this;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("bad value type: ");
            ((StringBuilder)charSequence).append(object.getClass().getName());
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }

        public Builder withValueBackReference(String string2, int n) {
            int n2 = this.mType;
            if (n2 != 1 && n2 != 2 && n2 != 4) {
                throw new IllegalArgumentException("only inserts, updates, and asserts can have value back-references");
            }
            if (this.mValuesBackReferences == null) {
                this.mValuesBackReferences = new ContentValues();
            }
            this.mValuesBackReferences.put(string2, n);
            return this;
        }

        public Builder withValueBackReferences(ContentValues contentValues) {
            int n = this.mType;
            if (n != 1 && n != 2 && n != 4) {
                throw new IllegalArgumentException("only inserts, updates, and asserts can have value back-references");
            }
            this.mValuesBackReferences = contentValues;
            return this;
        }

        public Builder withValues(ContentValues contentValues) {
            int n = this.mType;
            if (n != 1 && n != 2 && n != 4) {
                throw new IllegalArgumentException("only inserts, updates, and asserts can have values");
            }
            if (this.mValues == null) {
                this.mValues = new ContentValues();
            }
            this.mValues.putAll(contentValues);
            return this;
        }

        public Builder withYieldAllowed(boolean bl) {
            this.mYieldAllowed = bl;
            return this;
        }
    }

}

