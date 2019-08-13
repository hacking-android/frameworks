/*
 * Decompiled with CFR 0.145.
 */
package android.os.storage;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.provider.DocumentsContract;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.Writer;
import java.util.Locale;

public final class StorageVolume
implements Parcelable {
    private static final String ACTION_OPEN_EXTERNAL_DIRECTORY = "android.os.storage.action.OPEN_EXTERNAL_DIRECTORY";
    public static final Parcelable.Creator<StorageVolume> CREATOR = new Parcelable.Creator<StorageVolume>(){

        @Override
        public StorageVolume createFromParcel(Parcel parcel) {
            return new StorageVolume(parcel);
        }

        public StorageVolume[] newArray(int n) {
            return new StorageVolume[n];
        }
    };
    public static final String EXTRA_DIRECTORY_NAME = "android.os.storage.extra.DIRECTORY_NAME";
    public static final String EXTRA_STORAGE_VOLUME = "android.os.storage.extra.STORAGE_VOLUME";
    public static final int STORAGE_ID_INVALID = 0;
    public static final int STORAGE_ID_PRIMARY = 65537;
    private final boolean mAllowMassStorage;
    @UnsupportedAppUsage
    private final String mDescription;
    private final boolean mEmulated;
    private final String mFsUuid;
    @UnsupportedAppUsage
    private final String mId;
    private final File mInternalPath;
    private final long mMaxFileSize;
    private final UserHandle mOwner;
    @UnsupportedAppUsage
    private final File mPath;
    @UnsupportedAppUsage
    private final boolean mPrimary;
    @UnsupportedAppUsage
    private final boolean mRemovable;
    private final String mState;

    private StorageVolume(Parcel parcel) {
        this.mId = parcel.readString();
        this.mPath = new File(parcel.readString());
        this.mInternalPath = new File(parcel.readString());
        this.mDescription = parcel.readString();
        int n = parcel.readInt();
        boolean bl = true;
        boolean bl2 = n != 0;
        this.mPrimary = bl2;
        bl2 = parcel.readInt() != 0;
        this.mRemovable = bl2;
        bl2 = parcel.readInt() != 0;
        this.mEmulated = bl2;
        bl2 = parcel.readInt() != 0 ? bl : false;
        this.mAllowMassStorage = bl2;
        this.mMaxFileSize = parcel.readLong();
        this.mOwner = (UserHandle)parcel.readParcelable(null);
        this.mFsUuid = parcel.readString();
        this.mState = parcel.readString();
    }

    public StorageVolume(String string2, File file, File file2, String string3, boolean bl, boolean bl2, boolean bl3, boolean bl4, long l, UserHandle userHandle, String string4, String string5) {
        this.mId = Preconditions.checkNotNull(string2);
        this.mPath = Preconditions.checkNotNull(file);
        this.mInternalPath = Preconditions.checkNotNull(file2);
        this.mDescription = Preconditions.checkNotNull(string3);
        this.mPrimary = bl;
        this.mRemovable = bl2;
        this.mEmulated = bl3;
        this.mAllowMassStorage = bl4;
        this.mMaxFileSize = l;
        this.mOwner = Preconditions.checkNotNull(userHandle);
        this.mFsUuid = string4;
        this.mState = Preconditions.checkNotNull(string5);
    }

    public static String normalizeUuid(String string2) {
        string2 = string2 != null ? string2.toLowerCase(Locale.US) : null;
        return string2;
    }

    @UnsupportedAppUsage
    public boolean allowMassStorage() {
        return this.mAllowMassStorage;
    }

    @Deprecated
    public Intent createAccessIntent(String string2) {
        if (this.isPrimary() && string2 == null || string2 != null && !Environment.isStandardDirectory(string2)) {
            return null;
        }
        Intent intent = new Intent(ACTION_OPEN_EXTERNAL_DIRECTORY);
        intent.putExtra(EXTRA_STORAGE_VOLUME, this);
        intent.putExtra(EXTRA_DIRECTORY_NAME, string2);
        return intent;
    }

    public Intent createOpenDocumentTreeIntent() {
        Object object = this.isEmulated() ? "primary" : this.mFsUuid;
        object = DocumentsContract.buildRootUri("com.android.externalstorage.documents", (String)object);
        return new Intent("android.intent.action.OPEN_DOCUMENT_TREE").putExtra("android.provider.extra.INITIAL_URI", (Parcelable)object).putExtra("android.provider.extra.SHOW_ADVANCED", true);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String dump() {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        this.dump(new IndentingPrintWriter(charArrayWriter, "    ", 80));
        return charArrayWriter.toString();
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("StorageVolume:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair("mId", this.mId);
        indentingPrintWriter.printPair("mPath", this.mPath);
        indentingPrintWriter.printPair("mInternalPath", this.mInternalPath);
        indentingPrintWriter.printPair("mDescription", this.mDescription);
        indentingPrintWriter.printPair("mPrimary", this.mPrimary);
        indentingPrintWriter.printPair("mRemovable", this.mRemovable);
        indentingPrintWriter.printPair("mEmulated", this.mEmulated);
        indentingPrintWriter.printPair("mAllowMassStorage", this.mAllowMassStorage);
        indentingPrintWriter.printPair("mMaxFileSize", this.mMaxFileSize);
        indentingPrintWriter.printPair("mOwner", this.mOwner);
        indentingPrintWriter.printPair("mFsUuid", this.mFsUuid);
        indentingPrintWriter.printPair("mState", this.mState);
        indentingPrintWriter.decreaseIndent();
    }

    public boolean equals(Object object) {
        File file;
        if (object instanceof StorageVolume && (file = this.mPath) != null) {
            return file.equals(((StorageVolume)object).mPath);
        }
        return false;
    }

    public String getDescription(Context context) {
        return this.mDescription;
    }

    @UnsupportedAppUsage
    public int getFatVolumeId() {
        String string2 = this.mFsUuid;
        if (string2 != null && string2.length() == 9) {
            long l;
            try {
                l = Long.parseLong(this.mFsUuid.replace("-", ""), 16);
            }
            catch (NumberFormatException numberFormatException) {
                return -1;
            }
            return (int)l;
        }
        return -1;
    }

    @UnsupportedAppUsage
    public String getId() {
        return this.mId;
    }

    public String getInternalPath() {
        return this.mInternalPath.toString();
    }

    @UnsupportedAppUsage
    public long getMaxFileSize() {
        return this.mMaxFileSize;
    }

    public String getNormalizedUuid() {
        return StorageVolume.normalizeUuid(this.mFsUuid);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public UserHandle getOwner() {
        return this.mOwner;
    }

    public String getPath() {
        return this.mPath.toString();
    }

    @UnsupportedAppUsage
    public File getPathFile() {
        return this.mPath;
    }

    public String getState() {
        return this.mState;
    }

    @UnsupportedAppUsage
    public String getUserLabel() {
        return this.mDescription;
    }

    public String getUuid() {
        return this.mFsUuid;
    }

    public int hashCode() {
        return this.mPath.hashCode();
    }

    public boolean isEmulated() {
        return this.mEmulated;
    }

    public boolean isPrimary() {
        return this.mPrimary;
    }

    public boolean isRemovable() {
        return this.mRemovable;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("StorageVolume: ").append(this.mDescription);
        if (this.mFsUuid != null) {
            stringBuilder.append(" (");
            stringBuilder.append(this.mFsUuid);
            stringBuilder.append(")");
        }
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mId);
        parcel.writeString(this.mPath.toString());
        parcel.writeString(this.mInternalPath.toString());
        parcel.writeString(this.mDescription);
        parcel.writeInt((int)this.mPrimary);
        parcel.writeInt((int)this.mRemovable);
        parcel.writeInt((int)this.mEmulated);
        parcel.writeInt((int)this.mAllowMassStorage);
        parcel.writeLong(this.mMaxFileSize);
        parcel.writeParcelable(this.mOwner, n);
        parcel.writeString(this.mFsUuid);
        parcel.writeString(this.mState);
    }

}

