/*
 * Decompiled with CFR 0.145.
 */
package android.os.storage;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.os.storage.DiskInfo;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.DebugUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.Writer;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

public class VolumeInfo
implements Parcelable {
    public static final String ACTION_VOLUME_STATE_CHANGED = "android.os.storage.action.VOLUME_STATE_CHANGED";
    @UnsupportedAppUsage
    public static final Parcelable.Creator<VolumeInfo> CREATOR;
    private static final String DOCUMENT_AUTHORITY = "com.android.externalstorage.documents";
    private static final String DOCUMENT_ROOT_PRIMARY_EMULATED = "primary";
    public static final String EXTRA_VOLUME_ID = "android.os.storage.extra.VOLUME_ID";
    public static final String EXTRA_VOLUME_STATE = "android.os.storage.extra.VOLUME_STATE";
    public static final String ID_EMULATED_INTERNAL = "emulated";
    public static final String ID_PRIVATE_INTERNAL = "private";
    public static final int MOUNT_FLAG_PRIMARY = 1;
    public static final int MOUNT_FLAG_VISIBLE = 2;
    public static final int STATE_BAD_REMOVAL = 8;
    public static final int STATE_CHECKING = 1;
    public static final int STATE_EJECTING = 5;
    public static final int STATE_FORMATTING = 4;
    public static final int STATE_MOUNTED = 2;
    public static final int STATE_MOUNTED_READ_ONLY = 3;
    public static final int STATE_REMOVED = 7;
    public static final int STATE_UNMOUNTABLE = 6;
    public static final int STATE_UNMOUNTED = 0;
    public static final int TYPE_ASEC = 3;
    @UnsupportedAppUsage
    public static final int TYPE_EMULATED = 2;
    public static final int TYPE_OBB = 4;
    public static final int TYPE_PRIVATE = 1;
    @UnsupportedAppUsage
    public static final int TYPE_PUBLIC = 0;
    public static final int TYPE_STUB = 5;
    private static final Comparator<VolumeInfo> sDescriptionComparator;
    private static ArrayMap<String, String> sEnvironmentToBroadcast;
    private static SparseIntArray sStateToDescrip;
    private static SparseArray<String> sStateToEnvironment;
    @UnsupportedAppUsage
    public final DiskInfo disk;
    @UnsupportedAppUsage
    public String fsLabel;
    public String fsType;
    @UnsupportedAppUsage
    public String fsUuid;
    public final String id;
    @UnsupportedAppUsage
    public String internalPath;
    public int mountFlags = 0;
    public int mountUserId = -10000;
    public final String partGuid;
    @UnsupportedAppUsage
    public String path;
    @UnsupportedAppUsage
    public int state = 0;
    @UnsupportedAppUsage
    public final int type;

    static {
        sStateToEnvironment = new SparseArray();
        sEnvironmentToBroadcast = new ArrayMap();
        sStateToDescrip = new SparseIntArray();
        sDescriptionComparator = new Comparator<VolumeInfo>(){

            @Override
            public int compare(VolumeInfo volumeInfo, VolumeInfo volumeInfo2) {
                if (VolumeInfo.ID_PRIVATE_INTERNAL.equals(volumeInfo.getId())) {
                    return -1;
                }
                if (volumeInfo.getDescription() == null) {
                    return 1;
                }
                if (volumeInfo2.getDescription() == null) {
                    return -1;
                }
                return volumeInfo.getDescription().compareTo(volumeInfo2.getDescription());
            }
        };
        sStateToEnvironment.put(0, "unmounted");
        sStateToEnvironment.put(1, "checking");
        sStateToEnvironment.put(2, "mounted");
        sStateToEnvironment.put(3, "mounted_ro");
        sStateToEnvironment.put(4, "unmounted");
        sStateToEnvironment.put(5, "ejecting");
        sStateToEnvironment.put(6, "unmountable");
        sStateToEnvironment.put(7, "removed");
        sStateToEnvironment.put(8, "bad_removal");
        sEnvironmentToBroadcast.put("unmounted", "android.intent.action.MEDIA_UNMOUNTED");
        sEnvironmentToBroadcast.put("checking", "android.intent.action.MEDIA_CHECKING");
        sEnvironmentToBroadcast.put("mounted", "android.intent.action.MEDIA_MOUNTED");
        sEnvironmentToBroadcast.put("mounted_ro", "android.intent.action.MEDIA_MOUNTED");
        sEnvironmentToBroadcast.put("ejecting", "android.intent.action.MEDIA_EJECT");
        sEnvironmentToBroadcast.put("unmountable", "android.intent.action.MEDIA_UNMOUNTABLE");
        sEnvironmentToBroadcast.put("removed", "android.intent.action.MEDIA_REMOVED");
        sEnvironmentToBroadcast.put("bad_removal", "android.intent.action.MEDIA_BAD_REMOVAL");
        sStateToDescrip.put(0, 17039944);
        sStateToDescrip.put(1, 17039936);
        sStateToDescrip.put(2, 17039940);
        sStateToDescrip.put(3, 17039941);
        sStateToDescrip.put(4, 17039938);
        sStateToDescrip.put(5, 17039937);
        sStateToDescrip.put(6, 17039943);
        sStateToDescrip.put(7, 17039942);
        sStateToDescrip.put(8, 17039935);
        CREATOR = new Parcelable.Creator<VolumeInfo>(){

            @Override
            public VolumeInfo createFromParcel(Parcel parcel) {
                return new VolumeInfo(parcel);
            }

            public VolumeInfo[] newArray(int n) {
                return new VolumeInfo[n];
            }
        };
    }

    @UnsupportedAppUsage
    public VolumeInfo(Parcel parcel) {
        this.id = parcel.readString();
        this.type = parcel.readInt();
        this.disk = parcel.readInt() != 0 ? DiskInfo.CREATOR.createFromParcel(parcel) : null;
        this.partGuid = parcel.readString();
        this.mountFlags = parcel.readInt();
        this.mountUserId = parcel.readInt();
        this.state = parcel.readInt();
        this.fsType = parcel.readString();
        this.fsUuid = parcel.readString();
        this.fsLabel = parcel.readString();
        this.path = parcel.readString();
        this.internalPath = parcel.readString();
    }

    public VolumeInfo(String string2, int n, DiskInfo diskInfo, String string3) {
        this.id = Preconditions.checkNotNull(string2);
        this.type = n;
        this.disk = diskInfo;
        this.partGuid = string3;
    }

    @UnsupportedAppUsage
    public static int buildStableMtpStorageId(String string2) {
        int n;
        if (TextUtils.isEmpty(string2)) {
            return 0;
        }
        int n2 = 0;
        for (n = 0; n < string2.length(); ++n) {
            n2 = n2 * 31 + string2.charAt(n);
        }
        n2 = n = (n2 << 16 ^ n2) & -65536;
        if (n == 0) {
            n2 = 131072;
        }
        n = n2;
        if (n2 == 65536) {
            n = 131072;
        }
        n2 = n;
        if (n == -65536) {
            n2 = -131072;
        }
        return n2 | 1;
    }

    public static String getBroadcastForEnvironment(String string2) {
        return sEnvironmentToBroadcast.get(string2);
    }

    public static String getBroadcastForState(int n) {
        return VolumeInfo.getBroadcastForEnvironment(VolumeInfo.getEnvironmentForState(n));
    }

    public static Comparator<VolumeInfo> getDescriptionComparator() {
        return sDescriptionComparator;
    }

    @UnsupportedAppUsage
    public static String getEnvironmentForState(int n) {
        String string2 = sStateToEnvironment.get(n);
        if (string2 != null) {
            return string2;
        }
        return "unknown";
    }

    @UnsupportedAppUsage
    public Intent buildBrowseIntent() {
        return this.buildBrowseIntentForUser(UserHandle.myUserId());
    }

    public Intent buildBrowseIntentForUser(int n) {
        block4 : {
            Uri uri;
            block3 : {
                block2 : {
                    int n2 = this.type;
                    if (n2 != 0 && n2 != 5 || this.mountUserId != n) break block2;
                    uri = DocumentsContract.buildRootUri(DOCUMENT_AUTHORITY, this.fsUuid);
                    break block3;
                }
                if (this.type != 2 || !this.isPrimary()) break block4;
                uri = DocumentsContract.buildRootUri(DOCUMENT_AUTHORITY, DOCUMENT_ROOT_PRIMARY_EMULATED);
            }
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setDataAndType(uri, "vnd.android.document/root");
            intent.putExtra("android.provider.extra.SHOW_ADVANCED", this.isPrimary());
            return intent;
        }
        return null;
    }

    @UnsupportedAppUsage
    public StorageVolume buildStorageVolume(Context object, int n, boolean bl) {
        long l;
        File file;
        boolean bl2;
        StorageManager storageManager = ((Context)object).getSystemService(StorageManager.class);
        String string2 = bl ? "unmounted" : VolumeInfo.getEnvironmentForState(this.state);
        Object object2 = this.getPathForUser(n);
        File file2 = object2;
        if (object2 == null) {
            file2 = new File("/dev/null");
        }
        if ((file = this.getInternalPathForUser(n)) == null) {
            file = new File("/dev/null");
        }
        String string3 = null;
        object2 = this.fsUuid;
        int n2 = this.type;
        if (n2 == 2) {
            VolumeInfo volumeInfo = storageManager.findPrivateForEmulated(this);
            if (volumeInfo != null) {
                string3 = storageManager.getBestVolumeDescription(volumeInfo);
                object2 = volumeInfo.fsUuid;
            }
            bl = !ID_EMULATED_INTERNAL.equals(this.id);
            l = 0L;
            boolean bl3 = true;
            bl2 = bl;
            bl = bl3;
        } else {
            if (n2 != 0 && n2 != 5) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected volume type ");
                ((StringBuilder)object).append(this.type);
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            string3 = storageManager.getBestVolumeDescription(this);
            if ("vfat".equals(this.fsType)) {
                l = 0xFFFFFFFFL;
                bl = false;
                bl2 = true;
            } else {
                l = 0L;
                bl = false;
                bl2 = true;
            }
        }
        object = string3 == null ? ((Context)object).getString(17039374) : string3;
        return new StorageVolume(this.id, file2, file, (String)object, this.isPrimary(), bl2, bl, false, l, new UserHandle(n), (String)object2, string2);
    }

    public VolumeInfo clone() {
        Parcel parcel = Parcel.obtain();
        try {
            this.writeToParcel(parcel, 0);
            parcel.setDataPosition(0);
            VolumeInfo volumeInfo = CREATOR.createFromParcel(parcel);
            return volumeInfo;
        }
        finally {
            parcel.recycle();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("VolumeInfo{");
        stringBuilder.append(this.id);
        stringBuilder.append("}:");
        indentingPrintWriter.println(stringBuilder.toString());
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair("type", DebugUtils.valueToString(this.getClass(), "TYPE_", this.type));
        indentingPrintWriter.printPair("diskId", this.getDiskId());
        indentingPrintWriter.printPair("partGuid", this.partGuid);
        indentingPrintWriter.printPair("mountFlags", DebugUtils.flagsToString(this.getClass(), "MOUNT_FLAG_", this.mountFlags));
        indentingPrintWriter.printPair("mountUserId", this.mountUserId);
        indentingPrintWriter.printPair("state", DebugUtils.valueToString(this.getClass(), "STATE_", this.state));
        indentingPrintWriter.println();
        indentingPrintWriter.printPair("fsType", this.fsType);
        indentingPrintWriter.printPair("fsUuid", this.fsUuid);
        indentingPrintWriter.printPair("fsLabel", this.fsLabel);
        indentingPrintWriter.println();
        indentingPrintWriter.printPair("path", this.path);
        indentingPrintWriter.printPair("internalPath", this.internalPath);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
    }

    public boolean equals(Object object) {
        if (object instanceof VolumeInfo) {
            return Objects.equals(this.id, ((VolumeInfo)object).id);
        }
        return false;
    }

    @UnsupportedAppUsage
    public String getDescription() {
        if (!ID_PRIVATE_INTERNAL.equals(this.id) && !ID_EMULATED_INTERNAL.equals(this.id)) {
            if (!TextUtils.isEmpty(this.fsLabel)) {
                return this.fsLabel;
            }
            return null;
        }
        return Resources.getSystem().getString(17041103);
    }

    @UnsupportedAppUsage
    public DiskInfo getDisk() {
        return this.disk;
    }

    @UnsupportedAppUsage
    public String getDiskId() {
        Object object = this.disk;
        object = object != null ? ((DiskInfo)object).id : null;
        return object;
    }

    @UnsupportedAppUsage
    public String getFsUuid() {
        return this.fsUuid;
    }

    @UnsupportedAppUsage
    public String getId() {
        return this.id;
    }

    @UnsupportedAppUsage
    public File getInternalPath() {
        Object object = this.internalPath;
        object = object != null ? new File((String)object) : null;
        return object;
    }

    @UnsupportedAppUsage
    public File getInternalPathForUser(int n) {
        if (this.path == null) {
            return null;
        }
        int n2 = this.type;
        if (n2 != 0 && n2 != 5) {
            return this.getPathForUser(n);
        }
        return new File(this.path.replace("/storage/", "/mnt/media_rw/"));
    }

    @UnsupportedAppUsage
    public int getMountUserId() {
        return this.mountUserId;
    }

    public String getNormalizedFsUuid() {
        String string2 = this.fsUuid;
        string2 = string2 != null ? string2.toLowerCase(Locale.US) : null;
        return string2;
    }

    @UnsupportedAppUsage
    public File getPath() {
        Object object = this.path;
        object = object != null ? new File((String)object) : null;
        return object;
    }

    @UnsupportedAppUsage
    public File getPathForUser(int n) {
        String string2 = this.path;
        if (string2 == null) {
            return null;
        }
        int n2 = this.type;
        if (n2 != 0 && n2 != 5) {
            if (n2 == 2) {
                return new File(string2, Integer.toString(n));
            }
            return null;
        }
        return new File(this.path);
    }

    @UnsupportedAppUsage
    public int getState() {
        return this.state;
    }

    public int getStateDescription() {
        return sStateToDescrip.get(this.state, 0);
    }

    @UnsupportedAppUsage
    public int getType() {
        return this.type;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    @UnsupportedAppUsage
    public boolean isMountedReadable() {
        int n = this.state;
        boolean bl = n == 2 || n == 3;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isMountedWritable() {
        boolean bl = this.state == 2;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isPrimary() {
        int n = this.mountFlags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isPrimaryPhysical() {
        boolean bl = this.isPrimary() && this.getType() == 0;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isVisible() {
        boolean bl = (this.mountFlags & 2) != 0;
        return bl;
    }

    public boolean isVisibleForRead(int n) {
        return this.isVisibleForUser(n);
    }

    public boolean isVisibleForUser(int n) {
        int n2 = this.type;
        if ((n2 == 0 || n2 == 5) && this.mountUserId == n) {
            return this.isVisible();
        }
        if (this.type == 2) {
            return this.isVisible();
        }
        return false;
    }

    @UnsupportedAppUsage
    public boolean isVisibleForWrite(int n) {
        return this.isVisibleForUser(n);
    }

    public String toString() {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        this.dump(new IndentingPrintWriter(charArrayWriter, "    ", 80));
        return charArrayWriter.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.id);
        parcel.writeInt(this.type);
        if (this.disk != null) {
            parcel.writeInt(1);
            this.disk.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.partGuid);
        parcel.writeInt(this.mountFlags);
        parcel.writeInt(this.mountUserId);
        parcel.writeInt(this.state);
        parcel.writeString(this.fsType);
        parcel.writeString(this.fsUuid);
        parcel.writeString(this.fsLabel);
        parcel.writeString(this.path);
        parcel.writeString(this.internalPath);
    }

}

