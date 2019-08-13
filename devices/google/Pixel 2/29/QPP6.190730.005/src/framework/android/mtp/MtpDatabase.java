/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.mtp.-$
 *  android.mtp.-$$Lambda
 *  android.mtp.-$$Lambda$UV1wDVoVlbcxpr8zevj_aMFtUGw
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  dalvik.system.CloseGuard
 */
package android.mtp;

import android.content.BroadcastReceiver;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteClosable;
import android.database.sqlite.SQLiteDatabase;
import android.mtp.-$;
import android.mtp.MtpPropertyGroup;
import android.mtp.MtpPropertyList;
import android.mtp.MtpServer;
import android.mtp.MtpStorage;
import android.mtp.MtpStorageManager;
import android.mtp._$$Lambda$UV1wDVoVlbcxpr8zevj_aMFtUGw;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.storage.StorageVolume;
import android.provider.MediaStore;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.WindowManager;
import com.android.internal.annotations.VisibleForNative;
import com.google.android.collect.Sets;
import dalvik.system.CloseGuard;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MtpDatabase
implements AutoCloseable {
    private static final int[] AUDIO_PROPERTIES;
    private static final int[] DEVICE_PROPERTIES;
    private static final int[] FILE_PROPERTIES;
    private static final String[] ID_PROJECTION;
    private static final int[] IMAGE_PROPERTIES;
    private static final String NO_MEDIA = ".nomedia";
    private static final String[] PATH_PROJECTION;
    private static final String PATH_WHERE = "_data=?";
    private static final int[] PLAYBACK_FORMATS;
    private static final String TAG;
    private static final int[] VIDEO_PROPERTIES;
    private int mBatteryLevel;
    private BroadcastReceiver mBatteryReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.BATTERY_CHANGED")) {
                MtpDatabase.this.mBatteryScale = intent.getIntExtra("scale", 0);
                int n = intent.getIntExtra("level", 0);
                if (n != MtpDatabase.this.mBatteryLevel) {
                    MtpDatabase.this.mBatteryLevel = n;
                    if (MtpDatabase.this.mServer != null) {
                        MtpDatabase.this.mServer.sendDevicePropertyChanged(20481);
                    }
                }
            }
        }
    };
    private int mBatteryScale;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final AtomicBoolean mClosed = new AtomicBoolean();
    private final Context mContext;
    private SharedPreferences mDeviceProperties;
    private int mDeviceType;
    private MtpStorageManager mManager;
    private final ContentProviderClient mMediaProvider;
    @VisibleForNative
    private long mNativeContext;
    private final SparseArray<MtpPropertyGroup> mPropertyGroupsByFormat = new SparseArray();
    private final SparseArray<MtpPropertyGroup> mPropertyGroupsByProperty = new SparseArray();
    private MtpServer mServer;
    private final HashMap<String, MtpStorage> mStorageMap = new HashMap();

    static {
        TAG = MtpDatabase.class.getSimpleName();
        ID_PROJECTION = new String[]{"_id"};
        PATH_PROJECTION = new String[]{"_data"};
        System.loadLibrary("media_jni");
        PLAYBACK_FORMATS = new int[]{12288, 12289, 12292, 12293, 12296, 12297, 12299, 14337, 14338, 14340, 14343, 14344, 14347, 14349, 47361, 47362, 47363, 47490, 47491, 47492, 47621, 47632, 47633, 47636, 47746, 47366, 14353, 14354};
        FILE_PROPERTIES = new int[]{56321, 56322, 56323, 56324, 56327, 56329, 56385, 56331, 56388, 56544, 56398};
        AUDIO_PROPERTIES = new int[]{56390, 56474, 56475, 56459, 56473, 56457, 56470, 56985, 56978, 56986, 56980, 56979};
        VIDEO_PROPERTIES = new int[]{56390, 56474, 56457, 56392};
        IMAGE_PROPERTIES = new int[]{56392};
        DEVICE_PROPERTIES = new int[]{54273, 54274, 20483, 20481, 54279};
    }

    public MtpDatabase(Context context, String[] object) {
        this.native_setup();
        this.mContext = Objects.requireNonNull(context);
        this.mMediaProvider = context.getContentResolver().acquireContentProviderClient("media");
        MtpStorageManager.MtpNotifier mtpNotifier = new MtpStorageManager.MtpNotifier(){

            @Override
            public void sendObjectAdded(int n) {
                if (MtpDatabase.this.mServer != null) {
                    MtpDatabase.this.mServer.sendObjectAdded(n);
                }
            }

            @Override
            public void sendObjectInfoChanged(int n) {
                if (MtpDatabase.this.mServer != null) {
                    MtpDatabase.this.mServer.sendObjectInfoChanged(n);
                }
            }

            @Override
            public void sendObjectRemoved(int n) {
                if (MtpDatabase.this.mServer != null) {
                    MtpDatabase.this.mServer.sendObjectRemoved(n);
                }
            }
        };
        object = object == null ? null : Sets.newHashSet(object);
        this.mManager = new MtpStorageManager(mtpNotifier, (Set<String>)object);
        this.initDeviceProperties(context);
        this.mDeviceType = SystemProperties.getInt("sys.usb.mtp.device_type", 0);
        this.mCloseGuard.open("close");
    }

    @VisibleForNative
    private int beginCopyObject(int n, int n2, int n3) {
        MtpStorageManager.MtpObject mtpObject = this.mManager.getObject(n);
        MtpStorageManager.MtpObject mtpObject2 = n2 == 0 ? this.mManager.getStorageRoot(n3) : this.mManager.getObject(n2);
        if (mtpObject != null && mtpObject2 != null) {
            return this.mManager.beginCopyObject(mtpObject, mtpObject2);
        }
        return 8201;
    }

    @VisibleForNative
    private int beginDeleteObject(int n) {
        MtpStorageManager.MtpObject mtpObject = this.mManager.getObject(n);
        if (mtpObject == null) {
            return 8201;
        }
        if (!this.mManager.beginRemoveObject(mtpObject)) {
            return 8194;
        }
        return 8193;
    }

    @VisibleForNative
    private int beginMoveObject(int n, int n2, int n3) {
        MtpStorageManager.MtpObject mtpObject = this.mManager.getObject(n);
        MtpStorageManager.MtpObject mtpObject2 = n2 == 0 ? this.mManager.getStorageRoot(n3) : this.mManager.getObject(n2);
        if (mtpObject != null && mtpObject2 != null) {
            n = this.mManager.beginMoveObject(mtpObject, mtpObject2) ? 8193 : 8194;
            return n;
        }
        return 8201;
    }

    @VisibleForNative
    private int beginSendObject(String object, int n, int n2, int n3) {
        Object object2 = this.mManager;
        object2 = n2 == 0 ? ((MtpStorageManager)object2).getStorageRoot(n3) : ((MtpStorageManager)object2).getObject(n2);
        if (object2 == null) {
            return -1;
        }
        object = Paths.get((String)object, new String[0]);
        return this.mManager.beginSendObject((MtpStorageManager.MtpObject)object2, object.getFileName().toString(), n);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void deleteFromMedia(MtpStorageManager.MtpObject var1_1, Path var2_4, boolean var3_5) {
        var1_1 = MediaStore.Files.getMtpObjectsUri(var1_1.getVolumeName());
        if (!var3_5) ** GOTO lbl19
        try {
            var4_6 = this.mMediaProvider;
            var5_8 = new StringBuilder();
            var5_8.append(var2_4);
            var5_8.append("/%");
            var5_9 = var5_8.toString();
            var6_10 = Integer.toString(var2_4.toString().length() + 1);
            var7_11 = new StringBuilder();
            var7_11.append(var2_4.toString());
            var7_11.append("/");
            var4_6.delete((Uri)var1_1, "_data LIKE ?1 AND lower(substr(_data,1,?2))=lower(?3)", new String[]{var5_9, var6_10, var7_11.toString()});
lbl19: // 2 sources:
            var4_6 = var2_4.toString();
            if (this.mMediaProvider.delete((Uri)var1_1, "_data=?", new String[]{var4_6}) > 0) {
                if (var3_5 != false) return;
                if (var2_4.toString().toLowerCase(Locale.US).endsWith(".nomedia") == false) return;
                MediaStore.scanFile(this.mContext, var2_4.getParent().toFile());
                return;
            }
            var4_6 = MtpDatabase.TAG;
            var1_1 = new StringBuilder();
            var1_1.append("Mediaprovider didn't delete ");
            var1_1.append(var2_4);
            Log.i((String)var4_6, var1_1.toString());
            return;
        }
        catch (Exception var1_2) {
            var1_3 = MtpDatabase.TAG;
            var4_7 = new StringBuilder();
            var4_7.append("Failed to delete ");
            var4_7.append(var2_4);
            var4_7.append(" from MediaProvider");
            Log.d(var1_3, var4_7.toString());
        }
    }

    @VisibleForNative
    private void endCopyObject(int n, boolean bl) {
        MtpStorageManager.MtpObject mtpObject = this.mManager.getObject(n);
        if (mtpObject != null && this.mManager.endCopyObject(mtpObject, bl)) {
            if (!bl) {
                return;
            }
            MediaStore.scanFile(this.mContext, mtpObject.getPath().toFile());
            return;
        }
        Log.e(TAG, "Failed to end copy object");
    }

    @VisibleForNative
    private void endDeleteObject(int n, boolean bl) {
        MtpStorageManager.MtpObject mtpObject = this.mManager.getObject(n);
        if (mtpObject == null) {
            return;
        }
        if (!this.mManager.endRemoveObject(mtpObject, bl)) {
            Log.e(TAG, "Failed to end remove object");
        }
        if (bl) {
            this.deleteFromMedia(mtpObject, mtpObject.getPath(), mtpObject.isDir());
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @VisibleForNative
    private void endMoveObject(int var1_1, int var2_2, int var3_3, int var4_4, int var5_5, boolean var6_6) {
        block13 : {
            block12 : {
                block11 : {
                    var7_7 = var1_1 == 0 ? this.mManager.getStorageRoot(var3_3) : this.mManager.getObject(var1_1);
                    var8_12 = var2_2 == 0 ? this.mManager.getStorageRoot(var4_4) : this.mManager.getObject(var2_2);
                    var9_13 = this.mManager.getObject(var5_5).getName();
                    if (var8_12 == null || var7_7 == null || !this.mManager.endMoveObject((MtpStorageManager.MtpObject)var7_7, (MtpStorageManager.MtpObject)var8_12, (String)var9_13, var6_6)) break block13;
                    var10_14 = this.mManager.getObject(var5_5);
                    if (var6_6 == false) return;
                    if (var10_14 == null) {
                        return;
                    }
                    var11_15 = new ContentValues();
                    var12_16 = var8_12.getPath().resolve((String)var9_13);
                    var9_13 = var7_7.getPath().resolve((String)var9_13);
                    var11_15.put("_data", var12_16.toString());
                    if (var10_14.getParent().isRoot()) {
                        var11_15.put("parent", 0);
                    } else {
                        var1_1 = this.findInMedia((MtpStorageManager.MtpObject)var8_12, var12_16.getParent());
                        if (var1_1 == -1) {
                            this.deleteFromMedia(var10_14, (Path)var9_13, var10_14.isDir());
                            return;
                        }
                        var11_15.put("parent", var1_1);
                    }
                    var8_12 = var9_13.toString();
                    var1_1 = -1;
                    var6_6 = var7_7.isRoot();
                    if (var6_6) break block11;
                    try {
                        var1_1 = this.findInMedia((MtpStorageManager.MtpObject)var7_7, var9_13.getParent());
                    }
                    catch (RemoteException var7_8) {
                        break block12;
                    }
                }
                var6_6 = var7_7.isRoot();
                if (var6_6 || var1_1 != -1) ** GOTO lbl39
                try {
                    MediaStore.scanFile(this.mContext, var12_16.toFile());
                    return;
lbl39: // 1 sources:
                    var7_7 = MediaStore.Files.getMtpObjectsUri(var10_14.getVolumeName());
                    this.mMediaProvider.update((Uri)var7_7, var11_15, "_data=?", new String[]{var8_12});
                    return;
                }
                catch (RemoteException var7_9) {}
                break block12;
                catch (RemoteException var7_10) {
                    // empty catch block
                }
            }
            Log.e(MtpDatabase.TAG, "RemoteException in mMediaProvider.update", (Throwable)var7_11);
            return;
        }
        Log.e(MtpDatabase.TAG, "Failed to end move object");
    }

    @VisibleForNative
    private void endSendObject(int n, boolean bl) {
        MtpStorageManager.MtpObject mtpObject = this.mManager.getObject(n);
        if (mtpObject != null && this.mManager.endSendObject(mtpObject, bl)) {
            if (bl) {
                MediaStore.scanFile(this.mContext, mtpObject.getPath().toFile());
            }
            return;
        }
        Log.e(TAG, "Failed to successfully end send object");
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private int findInMedia(MtpStorageManager.MtpObject var1_1, Path var2_3) {
        var3_4 = MediaStore.Files.getMtpObjectsUri(var1_1.getVolumeName());
        var4_5 = -1;
        var1_1 = null;
        var5_6 = null;
        var3_4 = this.mMediaProvider.query((Uri)var3_4, MtpDatabase.ID_PROJECTION, "_data=?", new String[]{var2_3.toString()}, null, null);
        var6_8 = var4_5;
        if (var3_4 != null) {
            var6_8 = var4_5;
            var5_6 = var3_4;
            var1_1 = var3_4;
            if (var3_4.moveToNext()) {
                var5_6 = var3_4;
                var1_1 = var3_4;
                var6_8 = var3_4.getInt(0);
            }
        }
        var7_9 = var6_8;
        if (var3_4 == null) return var7_9;
        var1_1 = var3_4;
        var1_1.close();
        var7_9 = var6_8;
        return var7_9;
        {
            catch (RemoteException var5_7) {}
            var5_6 = var1_1;
            {
                var8_10 = MtpDatabase.TAG;
                var5_6 = var1_1;
                var5_6 = var1_1;
                var3_4 = new StringBuilder();
                var5_6 = var1_1;
                var3_4.append("Error finding ");
                var5_6 = var1_1;
                var3_4.append(var2_3);
                var5_6 = var1_1;
                var3_4.append(" in MediaProvider");
                var5_6 = var1_1;
                Log.e(var8_10, var3_4.toString());
                var7_9 = var4_5;
                if (var1_1 == null) return var7_9;
                var6_8 = var4_5;
                return var6_8;
            }
        }
        ** finally { 
lbl45: // 1 sources:
        if (var5_6 == null) throw var1_2;
        var5_6.close();
        throw var1_2;
    }

    @VisibleForNative
    private int getDeviceProperty(int n, long[] object, char[] arrc) {
        switch (n) {
            default: {
                return 8202;
            }
            case 54279: {
                object[0] = this.mDeviceType;
                return 8193;
            }
            case 54273: 
            case 54274: {
                int n2;
                object = this.mDeviceProperties.getString(Integer.toString(n), "");
                n = n2 = ((String)object).length();
                if (n2 > 255) {
                    n = 255;
                }
                ((String)object).getChars(0, n, arrc, 0);
                arrc[n] = (char)(false ? 1 : 0);
                return 8193;
            }
            case 20483: {
                object = ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay();
                n = ((Display)object).getMaximumSizeDimension();
                int n3 = ((Display)object).getMaximumSizeDimension();
                object = new StringBuilder();
                ((StringBuilder)object).append(Integer.toString(n));
                ((StringBuilder)object).append("x");
                ((StringBuilder)object).append(Integer.toString(n3));
                object = ((StringBuilder)object).toString();
                ((String)object).getChars(0, ((String)object).length(), arrc, 0);
                arrc[object.length()] = (char)(false ? 1 : 0);
                return 8193;
            }
            case 20481: 
        }
        object[0] = this.mBatteryLevel;
        object[1] = (long)this.mBatteryScale;
        return 8193;
    }

    @VisibleForNative
    private int getNumObjects(int n, int n2, int n3) {
        List<MtpStorageManager.MtpObject> list = this.mManager.getObjects(n3, n2, n);
        if (list == null) {
            return -1;
        }
        return list.size();
    }

    @VisibleForNative
    private int getObjectFilePath(int n, char[] arrc, long[] arrl) {
        MtpStorageManager.MtpObject mtpObject = this.mManager.getObject(n);
        if (mtpObject == null) {
            return 8201;
        }
        String string2 = mtpObject.getPath().toString();
        n = Integer.min(string2.length(), 4096);
        string2.getChars(0, n, arrc, 0);
        arrc[n] = (char)(false ? 1 : 0);
        arrl[0] = mtpObject.getSize();
        arrl[1] = mtpObject.getFormat();
        return 8193;
    }

    private int getObjectFormat(int n) {
        MtpStorageManager.MtpObject mtpObject = this.mManager.getObject(n);
        if (mtpObject == null) {
            return -1;
        }
        return mtpObject.getFormat();
    }

    @VisibleForNative
    private boolean getObjectInfo(int n, int[] arrn, char[] arrc, long[] arrl) {
        MtpStorageManager.MtpObject mtpObject = this.mManager.getObject(n);
        if (mtpObject == null) {
            return false;
        }
        arrn[0] = mtpObject.getStorageId();
        arrn[1] = mtpObject.getFormat();
        n = mtpObject.getParent().isRoot() ? 0 : mtpObject.getParent().getId();
        arrn[2] = n;
        n = Integer.min(mtpObject.getName().length(), 255);
        mtpObject.getName().getChars(0, n, arrc, 0);
        arrc[n] = (char)(false ? 1 : 0);
        arrl[0] = mtpObject.getModifiedTime();
        arrl[1] = mtpObject.getModifiedTime();
        return true;
    }

    @VisibleForNative
    private int[] getObjectList(int n, int n2, int n3) {
        List<MtpStorageManager.MtpObject> list = this.mManager.getObjects(n3, n2, n);
        if (list == null) {
            return null;
        }
        int[] arrn = new int[list.size()];
        for (n = 0; n < list.size(); ++n) {
            arrn[n] = list.get(n).getId();
        }
        return arrn;
    }

    /*
     * Exception decompiling
     */
    public static Uri getObjectPropertiesUri(int var0, String var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    @VisibleForNative
    private MtpPropertyList getObjectPropertyList(int n, int n2, int n3, int n4, int n5) {
        Object object;
        Object object2;
        MtpStorageManager.MtpObject mtpObject;
        int n6;
        Object object3;
        Object object4;
        block36 : {
            block35 : {
                block33 : {
                    block34 : {
                        block32 : {
                            block30 : {
                                block31 : {
                                    int n7 = n;
                                    n6 = n2;
                                    if (n3 == 0) {
                                        if (n4 == 0) {
                                            return new MtpPropertyList(8198);
                                        }
                                        return new MtpPropertyList(43015);
                                    }
                                    n = n7;
                                    n2 = n5;
                                    if (n5 != -1) break block30;
                                    if (n7 == 0) break block31;
                                    n = n7;
                                    n2 = n5;
                                    if (n7 != -1) break block30;
                                }
                                n = -1;
                                n2 = 0;
                            }
                            if (n2 != 0 && n2 != 1) {
                                return new MtpPropertyList(43016);
                            }
                            object4 = null;
                            object2 = null;
                            if (n != -1) break block32;
                            object = object4 = this.mManager.getObjects(0, n6, -1);
                            object3 = object2;
                            if (object4 == null) {
                                return new MtpPropertyList(8201);
                            }
                            break block33;
                        }
                        object = object4;
                        object3 = object2;
                        if (n == 0) break block33;
                        mtpObject = this.mManager.getObject(n);
                        if (mtpObject == null) {
                            return new MtpPropertyList(8201);
                        }
                        if (mtpObject.getFormat() == n6) break block34;
                        object = object4;
                        object3 = object2;
                        if (n6 != 0) break block33;
                    }
                    object3 = mtpObject;
                    object = object4;
                }
                if (n == 0) break block35;
                n4 = n;
                if (n2 != 1) break block36;
            }
            n2 = n;
            if (n == 0) {
                n2 = -1;
            }
            object2 = this.mManager.getObjects(n2, n6, -1);
            n4 = n2;
            object = object2;
            if (object2 == null) {
                return new MtpPropertyList(8201);
            }
        }
        object2 = object;
        if (object == null) {
            object2 = new ArrayList<MtpStorageManager.MtpObject>();
        }
        if (object3 != null) {
            object2.add(object3);
        }
        object4 = new MtpPropertyList(8193);
        object2 = object2.iterator();
        n = n6;
        while (object2.hasNext()) {
            mtpObject = (MtpStorageManager.MtpObject)object2.next();
            if (n3 == -1) {
                n2 = n;
                if (n == 0) {
                    n2 = n;
                    if (n4 != 0) {
                        n2 = n;
                        if (n4 != -1) {
                            n2 = mtpObject.getFormat();
                        }
                    }
                }
                object3 = this.mPropertyGroupsByFormat.get(n2);
                n5 = n2;
                object = object3;
                if (object3 == null) {
                    object = new MtpPropertyGroup(this.getSupportedObjectProperties(n2));
                    this.mPropertyGroupsByFormat.put(n2, (MtpPropertyGroup)object);
                    n5 = n2;
                }
            } else {
                object3 = this.mPropertyGroupsByProperty.get(n3);
                n5 = n;
                object = object3;
                if (object3 == null) {
                    object = new MtpPropertyGroup(new int[]{n3});
                    this.mPropertyGroupsByProperty.put(n3, (MtpPropertyGroup)object);
                    n5 = n;
                }
            }
            if ((n = ((MtpPropertyGroup)object).getPropertyList(this.mMediaProvider, mtpObject.getVolumeName(), mtpObject, (MtpPropertyList)object4)) != 8193) {
                return new MtpPropertyList(n);
            }
            n = n5;
        }
        return object4;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @VisibleForNative
    private int[] getObjectReferences(int n) {
        Throwable throwable2222;
        Object object = this.mManager.getObject(n);
        if (object == null) {
            return null;
        }
        n = this.findInMedia((MtpStorageManager.MtpObject)object, ((MtpStorageManager.MtpObject)object).getPath());
        if (n == -1) {
            return null;
        }
        Object object2 = MediaStore.Files.getMtpReferencesUri(((MtpStorageManager.MtpObject)object).getVolumeName(), n);
        Object object3 = null;
        object = null;
        object2 = this.mMediaProvider.query((Uri)object2, PATH_PROJECTION, null, null, null, null);
        if (object2 == null) {
            if (object2 == null) return null;
            object2.close();
            return null;
        }
        object = object2;
        object3 = object2;
        object = object2;
        object3 = object2;
        int[] arrn = new ArrayList();
        do {
            object = object2;
            object3 = object2;
            if (!object2.moveToNext()) {
                object = object2;
                object3 = object2;
                arrn = arrn.stream().mapToInt(_$$Lambda$UV1wDVoVlbcxpr8zevj_aMFtUGw.INSTANCE).toArray();
                object2.close();
                return arrn;
            }
            object = object2;
            object3 = object2;
            Object object4 = object2.getString(0);
            object = object2;
            object3 = object2;
            if ((object4 = this.mManager.getByPath((String)object4)) == null) continue;
            object = object2;
            object3 = object2;
            arrn.add(((MtpStorageManager.MtpObject)object4).getId());
        } while (true);
        {
            catch (Throwable throwable2222) {
            }
            catch (RemoteException remoteException) {}
            object = object3;
            {
                Log.e(TAG, "RemoteException in getObjectList", remoteException);
                if (object3 == null) return null;
                object3.close();
                return null;
            }
        }
        if (object == null) throw throwable2222;
        object.close();
        throw throwable2222;
    }

    @VisibleForNative
    private int[] getSupportedCaptureFormats() {
        return null;
    }

    @VisibleForNative
    private int[] getSupportedDeviceProperties() {
        return DEVICE_PROPERTIES;
    }

    /*
     * Exception decompiling
     */
    @VisibleForNative
    private int[] getSupportedObjectProperties(int var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    @VisibleForNative
    private int[] getSupportedPlaybackFormats() {
        return PLAYBACK_FORMATS;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void initDeviceProperties(Context var1_1) {
        block12 : {
            block11 : {
                this.mDeviceProperties = var1_1.getSharedPreferences("device-properties", 0);
                if (var1_1.getDatabasePath("device-properties").exists() == false) return;
                var2_3 /* !! */  = null;
                var3_4 = null;
                var4_5 = null;
                var5_6 = null;
                var6_7 = null;
                var7_8 = var4_5;
                var8_9 = var5_6;
                var9_10 = var1_1.openOrCreateDatabase("device-properties", 0, null);
                var2_3 /* !! */  = var6_7;
                if (var9_10 != null) {
                    var3_4 = var9_10;
                    var7_8 = var4_5;
                    var2_3 /* !! */  = var9_10;
                    var8_9 = var5_6;
                    var4_5 = var9_10.query("properties", new String[]{"_id", "code", "value"}, null, null, null, null, null);
                    var2_3 /* !! */  = var4_5;
                    if (var4_5 != null) {
                        var3_4 = var9_10;
                        var7_8 = var4_5;
                        var2_3 /* !! */  = var9_10;
                        var8_9 = var4_5;
                        var5_6 = this.mDeviceProperties.edit();
                        do {
                            var3_4 = var9_10;
                            var7_8 = var4_5;
                            var2_3 /* !! */  = var9_10;
                            var8_9 = var4_5;
                            if (!var4_5.moveToNext()) break;
                            var3_4 = var9_10;
                            var7_8 = var4_5;
                            var2_3 /* !! */  = var9_10;
                            var8_9 = var4_5;
                            var5_6.putString(var4_5.getString(1), var4_5.getString(2));
                        } while (true);
                        var3_4 = var9_10;
                        var7_8 = var4_5;
                        var2_3 /* !! */  = var9_10;
                        var8_9 = var4_5;
                        var5_6.commit();
                        var2_3 /* !! */  = var4_5;
                    }
                }
                if (var2_3 /* !! */  != null) {
                    var2_3 /* !! */ .close();
                }
                if (var9_10 == null) break block11;
                var2_3 /* !! */  = var9_10;
                do {
                    var2_3 /* !! */ .close();
                    break block11;
                    break;
                } while (true);
                {
                    catch (Throwable var1_2) {
                        break block12;
                    }
                    catch (Exception var9_11) {}
                    var3_4 = var2_3 /* !! */ ;
                    var7_8 = var8_9;
                    {
                        Log.e(MtpDatabase.TAG, "failed to migrate device properties", var9_11);
                        if (var8_9 != null) {
                            var8_9.close();
                        }
                        if (var2_3 /* !! */  != null) ** continue;
                    }
                }
            }
            var1_1.deleteDatabase("device-properties");
            return;
        }
        if (var7_8 != null) {
            var7_8.close();
        }
        if (var3_4 == null) throw var1_2;
        var3_4.close();
        throw var1_2;
    }

    private final native void native_finalize();

    private final native void native_setup();

    private int renameFile(int n, String object) {
        MtpStorageManager.MtpObject mtpObject = this.mManager.getObject(n);
        if (mtpObject == null) {
            return 8201;
        }
        Path path = mtpObject.getPath();
        if (!this.mManager.beginRenameObject(mtpObject, (String)object)) {
            return 8194;
        }
        object = mtpObject.getPath();
        boolean bl = path.toFile().renameTo(object.toFile());
        try {
            Os.access((String)path.toString(), (int)OsConstants.F_OK);
            Os.access((String)object.toString(), (int)OsConstants.F_OK);
        }
        catch (ErrnoException errnoException) {
            // empty catch block
        }
        if (!this.mManager.endRenameObject(mtpObject, path.getFileName().toString(), bl)) {
            Log.e(TAG, "Failed to end rename object");
        }
        if (!bl) {
            return 8194;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("_data", object.toString());
        String string2 = path.toString();
        try {
            Uri uri = MediaStore.Files.getMtpObjectsUri(mtpObject.getVolumeName());
            this.mMediaProvider.update(uri, contentValues, "_data=?", new String[]{string2});
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "RemoteException in mMediaProvider.update", remoteException);
        }
        if (mtpObject.isDir()) {
            if (path.getFileName().startsWith(".") && !object.startsWith(".")) {
                MediaStore.scanFile(this.mContext, object.toFile());
            }
        } else if (path.getFileName().toString().toLowerCase(Locale.US).equals(".nomedia") && !object.getFileName().toString().toLowerCase(Locale.US).equals(".nomedia")) {
            MediaStore.scanFile(this.mContext, object.getParent().toFile());
        }
        return 8193;
    }

    @VisibleForNative
    private void rescanFile(String string2, int n, int n2) {
        MediaStore.scanFile(this.mContext, new File(string2));
    }

    @VisibleForNative
    private int setDeviceProperty(int n, long l, String string2) {
        switch (n) {
            default: {
                return 8202;
            }
            case 54273: 
            case 54274: 
        }
        SharedPreferences.Editor editor = this.mDeviceProperties.edit();
        editor.putString(Integer.toString(n), string2);
        n = editor.commit() ? 8193 : 8194;
        return n;
    }

    @VisibleForNative
    private int setObjectProperty(int n, int n2, long l, String string2) {
        if (n2 != 56327) {
            return 43018;
        }
        return this.renameFile(n, string2);
    }

    @VisibleForNative
    private int setObjectReferences(int n, int[] arrn) {
        Object object = this.mManager.getObject(n);
        if (object == null) {
            return 8201;
        }
        n = this.findInMedia((MtpStorageManager.MtpObject)object, ((MtpStorageManager.MtpObject)object).getPath());
        if (n == -1) {
            return 8194;
        }
        object = MediaStore.Files.getMtpReferencesUri(((MtpStorageManager.MtpObject)object).getVolumeName(), n);
        ArrayList<Object> arrayList = new ArrayList<Object>();
        for (int n2 : arrn) {
            Object object2 = this.mManager.getObject(n2);
            if (object2 == null || (n2 = this.findInMedia((MtpStorageManager.MtpObject)object2, ((MtpStorageManager.MtpObject)object2).getPath())) == -1) continue;
            object2 = new ContentValues();
            ((ContentValues)object2).put("_id", n2);
            arrayList.add(object2);
        }
        try {
            n = this.mMediaProvider.bulkInsert((Uri)object, arrayList.toArray(new ContentValues[0]));
            if (n > 0) {
                return 8193;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "RemoteException in setObjectReferences", remoteException);
        }
        return 8194;
    }

    public void addStorage(StorageVolume object) {
        MtpStorage mtpStorage = this.mManager.addMtpStorage((StorageVolume)object);
        this.mStorageMap.put(((StorageVolume)object).getPath(), mtpStorage);
        object = this.mServer;
        if (object != null) {
            ((MtpServer)object).addStorage(mtpStorage);
        }
    }

    @Override
    public void close() {
        this.mManager.close();
        this.mCloseGuard.close();
        if (this.mClosed.compareAndSet(false, true)) {
            ContentProviderClient contentProviderClient = this.mMediaProvider;
            if (contentProviderClient != null) {
                contentProviderClient.close();
            }
            this.native_finalize();
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public void removeStorage(StorageVolume storageVolume) {
        MtpStorage mtpStorage = this.mStorageMap.get(storageVolume.getPath());
        if (mtpStorage == null) {
            return;
        }
        MtpServer mtpServer = this.mServer;
        if (mtpServer != null) {
            mtpServer.removeStorage(mtpStorage);
        }
        this.mManager.removeMtpStorage(mtpStorage);
        this.mStorageMap.remove(storageVolume.getPath());
    }

    public void setServer(MtpServer mtpServer) {
        this.mServer = mtpServer;
        try {
            this.mContext.unregisterReceiver(this.mBatteryReceiver);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        if (mtpServer != null) {
            this.mContext.registerReceiver(this.mBatteryReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        }
    }

}

