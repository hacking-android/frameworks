/*
 * Decompiled with CFR 0.145.
 */
package android.mtp;

import android.media.MediaFile;
import android.mtp.MtpStorage;
import android.mtp._$$Lambda$MtpStorageManager$HocvlaKIXOtuA3p8uOP9PA7UJtw;
import android.os.FileObserver;
import android.os.storage.StorageVolume;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MtpStorageManager {
    private static final int IN_IGNORED = 32768;
    private static final int IN_ISDIR = 1073741824;
    private static final int IN_ONLYDIR = 16777216;
    private static final int IN_Q_OVERFLOW = 16384;
    private static final String TAG = MtpStorageManager.class.getSimpleName();
    public static boolean sDebug = false;
    private volatile boolean mCheckConsistency;
    private Thread mConsistencyThread;
    private MtpNotifier mMtpNotifier;
    private int mNextObjectId;
    private int mNextStorageId;
    private HashMap<Integer, MtpObject> mObjects;
    private HashMap<Integer, MtpObject> mRoots;
    private Set<String> mSubdirectories;

    private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    public MtpStorageManager(MtpNotifier mtpNotifier, Set<String> set) {
        this.mMtpNotifier = mtpNotifier;
        this.mSubdirectories = set;
        this.mObjects = new HashMap();
        this.mRoots = new HashMap();
        this.mNextObjectId = 1;
        this.mNextStorageId = 1;
        this.mCheckConsistency = false;
        this.mConsistencyThread = new Thread(new _$$Lambda$MtpStorageManager$HocvlaKIXOtuA3p8uOP9PA7UJtw(this));
        if (this.mCheckConsistency) {
            this.mConsistencyThread.start();
        }
    }

    private MtpObject addObjectToCache(MtpObject mtpObject, String string2, boolean bl) {
        synchronized (this) {
            MtpObject mtpObject2;
            block8 : {
                boolean bl2;
                block7 : {
                    block6 : {
                        if (mtpObject.isRoot() || (mtpObject2 = this.getObject(mtpObject.getId())) == mtpObject) break block6;
                        return null;
                    }
                    mtpObject2 = mtpObject.getChild(string2);
                    if (mtpObject2 == null) break block7;
                    return null;
                }
                if (this.mSubdirectories == null || !mtpObject.isRoot() || (bl2 = this.mSubdirectories.contains(string2))) break block8;
                return null;
            }
            mtpObject2 = new MtpObject(string2, this.getNextObjectId(), mtpObject.mStorage, mtpObject, bl);
            this.mObjects.put(mtpObject2.getId(), mtpObject2);
            mtpObject.addChild(mtpObject2);
            return mtpObject2;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean generalBeginCopyObject(MtpObject iterator, boolean bl) {
        synchronized (this) {
            void var2_2;
            iterator.setState(MtpObjectState.FROZEN);
            iterator.setOperation(MtpOperation.COPY);
            if (var2_2 != false) {
                iterator.setId(this.getNextObjectId());
                this.mObjects.put(((MtpObject)((Object)iterator)).getId(), (MtpObject)((Object)iterator));
            }
            if (((MtpObject)((Object)iterator)).isDir()) {
                iterator = iterator.getChildren().iterator();
                while (iterator.hasNext()) {
                    boolean bl2 = this.generalBeginCopyObject((MtpObject)iterator.next(), (boolean)var2_2);
                    if (bl2) continue;
                    return false;
                }
            }
            return true;
        }
    }

    private boolean generalBeginRemoveObject(MtpObject object, MtpOperation mtpOperation) {
        synchronized (this) {
            ((MtpObject)object).setState(MtpObjectState.FROZEN);
            ((MtpObject)object).setOperation(mtpOperation);
            if (((MtpObject)object).isDir()) {
                object = ((MtpObject)object).getChildren().iterator();
                while (object.hasNext()) {
                    this.generalBeginRemoveObject((MtpObject)object.next(), mtpOperation);
                }
            }
            return true;
        }
    }

    private boolean generalBeginRenameObject(MtpObject mtpObject, MtpObject mtpObject2) {
        synchronized (this) {
            mtpObject.setState(MtpObjectState.FROZEN);
            mtpObject2.setState(MtpObjectState.FROZEN);
            mtpObject.setOperation(MtpOperation.RENAME);
            mtpObject2.setOperation(MtpOperation.RENAME);
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean generalEndAddObject(MtpObject mtpObject, boolean bl, boolean bl2) {
        synchronized (this) {
            boolean bl3;
            boolean bl4;
            int n = 1.$SwitchMap$android$mtp$MtpStorageManager$MtpObjectState[mtpObject.getState().ordinal()];
            if (n != 1) {
                if (n != 2) {
                    if (n != 5) {
                        return false;
                    }
                    mtpObject.setState(MtpObjectState.NORMAL);
                    if (!bl3) {
                        MtpObject mtpObject2 = mtpObject.getParent();
                        bl3 = this.removeObjectFromCache(mtpObject, bl4, false);
                        if (!bl3) {
                            return false;
                        }
                        this.handleAddedObject(mtpObject2, mtpObject.getName(), mtpObject.isDir());
                    }
                } else {
                    if (!(bl4 = this.removeObjectFromCache(mtpObject, bl4, false))) {
                        return false;
                    }
                    if (bl3) {
                        this.mMtpNotifier.sendObjectRemoved(mtpObject.getId());
                    }
                }
            } else if (bl3) {
                mtpObject.setState(MtpObjectState.FROZEN_ONESHOT_ADD);
            } else {
                bl3 = this.removeObjectFromCache(mtpObject, bl4, false);
                if (!bl3) {
                    return false;
                }
            }
            return true;
        }
    }

    private boolean generalEndCopyObject(MtpObject mtpObject, boolean bl, boolean bl2) {
        synchronized (this) {
            block12 : {
                boolean bl3;
                boolean bl4;
                block11 : {
                    if (bl && bl2) {
                        this.mObjects.put(mtpObject.getId(), mtpObject);
                    }
                    bl4 = true;
                    boolean bl5 = true;
                    boolean bl6 = mtpObject.isDir();
                    bl3 = false;
                    if (!bl6) break block11;
                    Object object = new ArrayList(mtpObject.getChildren());
                    Iterator iterator = ((ArrayList)object).iterator();
                    do {
                        bl4 = bl5;
                        if (!iterator.hasNext()) break;
                        object = (MtpObject)iterator.next();
                        bl4 = bl5;
                        if (((MtpObject)object).getOperation() == MtpOperation.COPY) {
                            bl5 = this.generalEndCopyObject((MtpObject)object, bl, bl2) && bl5;
                            bl4 = bl5;
                        }
                        bl5 = bl4;
                        continue;
                        break;
                    } while (true);
                }
                bl2 = bl || !bl2;
                bl2 = this.generalEndAddObject(mtpObject, bl, bl2);
                bl = bl3;
                if (!bl2) break block12;
                bl = bl3;
                if (!bl4) break block12;
                bl = true;
            }
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean generalEndRemoveObject(MtpObject mtpObject, boolean bl, boolean bl2) {
        synchronized (this) {
            boolean bl3;
            int n = 1.$SwitchMap$android$mtp$MtpStorageManager$MtpObjectState[mtpObject.getState().ordinal()];
            if (n != 1) {
                boolean bl4;
                if (n != 2) {
                    if (n != 5) {
                        return false;
                    }
                    mtpObject.setState(MtpObjectState.NORMAL);
                    if (bl3) {
                        MtpObject mtpObject2 = mtpObject.getParent();
                        bl3 = this.removeObjectFromCache(mtpObject, bl4, false);
                        if (!bl3) {
                            return false;
                        }
                        this.handleAddedObject(mtpObject2, mtpObject.getName(), mtpObject.isDir());
                    }
                } else {
                    if (!(bl4 = this.removeObjectFromCache(mtpObject, bl4, false))) {
                        return false;
                    }
                    if (!bl3) {
                        this.mMtpNotifier.sendObjectRemoved(mtpObject.getId());
                    }
                }
            } else if (bl3) {
                mtpObject.setState(MtpObjectState.FROZEN_ONESHOT_DEL);
            } else {
                mtpObject.setState(MtpObjectState.NORMAL);
            }
            return true;
        }
    }

    private boolean generalEndRenameObject(MtpObject mtpObject, MtpObject mtpObject2, boolean bl) {
        synchronized (this) {
            boolean bl2 = true;
            boolean bl3 = !bl;
            bl3 = this.generalEndRemoveObject(mtpObject, bl, bl3);
            bl = this.generalEndAddObject(mtpObject2, bl, bl);
            bl = bl && bl3 ? bl2 : false;
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Collection<MtpObject> getChildren(MtpObject object) {
        synchronized (this) {
            block14 : {
                if (object == null || !((MtpObject)object).isDir()) break block14;
                if (((MtpObject)object).isVisited()) return ((MtpObject)object).getChildren();
                Iterable<Path> iterable = ((MtpObject)object).getPath();
                if (((MtpObject)object).getObserver() != null) {
                    Log.e(TAG, "Observer is not null!");
                }
                Object object2 = new MtpObjectObserver((MtpObject)object);
                ((MtpObject)object).setObserver((FileObserver)object2);
                ((MtpObject)object).getObserver().startWatching();
                iterable = Files.newDirectoryStream((Path)iterable);
                try {
                    for (Path path : iterable) {
                        this.addObjectToCache((MtpObject)object, path.getFileName().toString(), path.toFile().isDirectory());
                    }
                }
                catch (Throwable throwable) {
                    try {
                        throw throwable;
                    }
                    catch (Throwable throwable2) {
                        if (iterable == null) throw throwable2;
                        try {
                            MtpStorageManager.$closeResource(throwable, iterable);
                            throw throwable2;
                        }
                        catch (IOException | DirectoryIteratorException exception) {
                            Log.e(TAG, exception.toString());
                            ((MtpObject)object).getObserver().stopWatching();
                            ((MtpObject)object).setObserver(null);
                            return null;
                        }
                    }
                }
                MtpStorageManager.$closeResource(null, iterable);
                ((MtpObject)object).setVisited(true);
                return ((MtpObject)object).getChildren();
            }
            String string2 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Can't find children of ");
            object = object == null ? "null" : Integer.valueOf(((MtpObject)object).getId());
            stringBuilder.append(object);
            Log.w(string2, stringBuilder.toString());
            return null;
        }
    }

    private int getNextObjectId() {
        int n = this.mNextObjectId;
        this.mNextObjectId = (int)((long)this.mNextObjectId + 1L);
        return n;
    }

    private int getNextStorageId() {
        int n = this.mNextStorageId;
        this.mNextStorageId = n + 1;
        return n;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean getObjects(List<MtpObject> list, MtpObject object, int n, boolean bl) {
        synchronized (this) {
            void var2_2;
            void var3_6;
            boolean bl2;
            Collection<MtpObject> collection = this.getChildren((MtpObject)var2_2);
            if (collection == null) {
                return false;
            }
            for (MtpObject mtpObject : collection) {
                if (var3_6 != false && mtpObject.getFormat() != var3_6) continue;
                list.add(mtpObject);
            }
            boolean bl3 = true;
            boolean bl4 = true;
            if (bl2) {
                Iterator<MtpObject> iterator = collection.iterator();
                bl2 = bl4;
                do {
                    bl3 = bl2;
                    if (!iterator.hasNext()) break;
                    MtpObject mtpObject = iterator.next();
                    bl3 = bl2;
                    if (mtpObject.isDir()) {
                        bl3 = this.getObjects(list, mtpObject, (int)var3_6, true);
                        bl3 = bl2 & bl3;
                    }
                    bl2 = bl3;
                } while (true);
            }
            return bl3;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void handleAddedObject(MtpObject var1_1, String var2_3, boolean var3_5) {
        block31 : {
            block30 : {
                block27 : {
                    block29 : {
                        block28 : {
                            // MONITORENTER : this
                            var4_5 = MtpOperation.NONE;
                            var5_7 = MtpObject.access$100((MtpObject)var1_1, (String)var2_2);
                            if (var5_7 == null) break block27;
                            var4_5 = MtpObject.access$1400(var5_7);
                            var1_1 = MtpObject.access$1500(var5_7);
                            if (var5_7.isDir() != var3_4 && var4_5 != MtpObjectState.FROZEN_REMOVED) {
                                var6_13 = MtpStorageManager.TAG;
                                var7_14 = new StringBuilder();
                                var7_14.append("Inconsistent directory info! ");
                                var7_14.append(var5_7.getPath());
                                Log.d((String)var6_13, var7_14.toString());
                            }
                            MtpObject.access$1600(var5_7, var3_4);
                            var8_15 = 1.$SwitchMap$android$mtp$MtpStorageManager$MtpObjectState[var4_5.ordinal()];
                            if (var8_15 == 1 || var8_15 == 2) break block28;
                            if (var8_15 == 3) ** GOTO lbl36
                            if (var8_15 != 4 && var8_15 != 5) {
                                var7_14 = MtpStorageManager.TAG;
                                var6_13 = new StringBuilder();
                                var6_13.append("Unexpected state in add ");
                                var6_13.append((String)var2_2);
                                var6_13.append(" ");
                                var6_13.append(var4_5);
                                Log.w((String)var7_14, var6_13.toString());
                            } else {
                                // MONITOREXIT : this
                                return;
lbl36: // 1 sources:
                                MtpObject.access$1700(var5_7, MtpObjectState.NORMAL);
                            }
                            break block29;
                        }
                        MtpObject.access$1700(var5_7, MtpObjectState.FROZEN_ADDED);
                    }
                    if (MtpStorageManager.sDebug) {
                        var2_2 = MtpStorageManager.TAG;
                        var6_13 = new StringBuilder();
                        var6_13.append(var4_5);
                        var6_13.append(" transitioned to ");
                        var6_13.append((Object)MtpObject.access$1400(var5_7));
                        var6_13.append(" in op ");
                        var6_13.append(var1_1);
                        Log.i(var2_2, var6_13.toString());
                    }
                    var2_2 = var1_1;
                    var1_1 = var5_7;
                    break block30;
                }
                if ((var1_1 = this.addObjectToCache((MtpObject)var1_1, (String)var2_2, var3_4)) == null) break block31;
                this.mMtpNotifier.sendObjectAdded(var1_1.getId());
                var2_2 = var4_5;
            }
            if (var3_4) {
                var5_8 = MtpOperation.RENAME;
                if (var2_2 == var5_8) {
                    // MONITOREXIT : this
                    return;
                }
                if (var2_2 == MtpOperation.COPY && !(var3_4 = MtpObject.access$800((MtpObject)var1_1))) {
                    // MONITOREXIT : this
                    return;
                }
                if (MtpObject.access$600((MtpObject)var1_1) != null) {
                    Log.e(MtpStorageManager.TAG, "Observer is not null!");
                    // MONITOREXIT : this
                    return;
                }
                var2_2 = new DirectoryStream<Path>((MtpObject)var1_1);
                MtpObject.access$700((MtpObject)var1_1, (FileObserver)var2_2);
                MtpObject.access$600((MtpObject)var1_1).startWatching();
                MtpObject.access$900((MtpObject)var1_1, true);
                var2_2 = Files.newDirectoryStream(var1_1.getPath());
                for (Path var5_10 : var2_2) {
                    if (MtpStorageManager.sDebug) {
                        var6_13 = MtpStorageManager.TAG;
                        var7_14 = new StringBuilder();
                        var7_14.append("Manually handling event for ");
                        var7_14.append(var5_10.getFileName().toString());
                        Log.i((String)var6_13, var7_14.toString());
                    }
                    this.handleAddedObject((MtpObject)var1_1, var5_10.getFileName().toString(), var5_10.toFile().isDirectory());
                }
                MtpStorageManager.$closeResource(null, var2_2);
                return;
                catch (Throwable var4_6) {
                    try {
                        throw var4_6;
                    }
                    catch (Throwable var5_11) {
                        if (var2_2 == null) throw var5_11;
                        try {
                            MtpStorageManager.$closeResource(var4_6, var2_2);
                            throw var5_11;
                        }
                        catch (IOException | DirectoryIteratorException var2_3) {
                            Log.e(MtpStorageManager.TAG, var2_3.toString());
                            MtpObject.access$600((MtpObject)var1_1).stopWatching();
                            MtpObject.access$700((MtpObject)var1_1, null);
                        }
                    }
                }
            }
            // MONITOREXIT : this
            return;
        }
        if (MtpStorageManager.sDebug) {
            var1_1 = MtpStorageManager.TAG;
            var5_12 = new StringBuilder();
            var5_12.append("object ");
            var5_12.append((String)var2_2);
            var5_12.append(" already exists");
            Log.w((String)var1_1, var5_12.toString());
        }
        // MONITOREXIT : this
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void handleChangedObject(MtpObject object, String charSequence) {
        synchronized (this) {
            StringBuilder stringBuilder;
            Object object2 = MtpOperation.NONE;
            object = ((MtpObject)object).getChild((String)((Object)stringBuilder));
            if (object != null) {
                if (!((MtpObject)object).isDir() && ((MtpObject)object).getSize() > 0L) {
                    ((MtpObject)object).getState();
                    ((MtpObject)object).getOperation();
                    this.mMtpNotifier.sendObjectInfoChanged(((MtpObject)object).getId());
                    if (sDebug) {
                        object2 = TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("sendObjectInfoChanged: id=");
                        stringBuilder.append(((MtpObject)object).getId());
                        stringBuilder.append(",size=");
                        stringBuilder.append(((MtpObject)object).getSize());
                        Log.d((String)object2, stringBuilder.toString());
                    }
                }
            } else if (sDebug) {
                object = TAG;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("object ");
                ((StringBuilder)object2).append((String)((Object)stringBuilder));
                ((StringBuilder)object2).append(" null");
                Log.w((String)object, ((StringBuilder)object2).toString());
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void handleRemovedObject(MtpObject mtpObject) {
        synchronized (this) {
            CharSequence charSequence;
            CharSequence charSequence2;
            MtpObjectState mtpObjectState = mtpObject.getState();
            MtpOperation mtpOperation = mtpObject.getOperation();
            int n = 1.$SwitchMap$android$mtp$MtpStorageManager$MtpObjectState[mtpObjectState.ordinal()];
            boolean bl = true;
            if (n != 1) {
                if (n != 4) {
                    if (n != 5) {
                        if (n != 6) {
                            charSequence2 = TAG;
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append("Got unexpected object remove for ");
                            ((StringBuilder)charSequence).append(mtpObject.getName());
                            Log.e((String)charSequence2, ((StringBuilder)charSequence).toString());
                        } else {
                            if (mtpOperation == MtpOperation.RENAME) {
                                bl = false;
                            }
                            this.removeObjectFromCache(mtpObject, bl, false);
                        }
                    } else {
                        mtpObject.setState(MtpObjectState.FROZEN_REMOVED);
                    }
                } else if (this.removeObjectFromCache(mtpObject, true, true)) {
                    this.mMtpNotifier.sendObjectRemoved(mtpObject.getId());
                }
            } else {
                mtpObject.setState(MtpObjectState.FROZEN_REMOVED);
            }
            if (sDebug) {
                charSequence = TAG;
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((Object)mtpObjectState);
                ((StringBuilder)charSequence2).append(" transitioned to ");
                ((StringBuilder)charSequence2).append((Object)mtpObject.getState());
                ((StringBuilder)charSequence2).append(" in op ");
                ((StringBuilder)charSequence2).append((Object)mtpOperation);
                Log.i((String)charSequence, ((StringBuilder)charSequence2).toString());
            }
            return;
        }
    }

    private boolean isSpecialSubDir(MtpObject mtpObject) {
        synchronized (this) {
            boolean bl;
            bl = mtpObject.getParent().isRoot() && this.mSubdirectories != null && !(bl = this.mSubdirectories.contains(mtpObject.getName()));
            return bl;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean removeObjectFromCache(MtpObject iterator, boolean bl, boolean bl2) {
        synchronized (this) {
            StringBuilder stringBuilder;
            void var2_2;
            ArrayList arrayList;
            boolean bl3;
            boolean bl4 = ((MtpObject)((Object)iterator)).isRoot() || ((MtpObject)((Object)iterator)).getParent().mChildren.remove(((MtpObject)((Object)iterator)).getName(), iterator);
            if (!bl4 && sDebug) {
                arrayList = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to remove from parent ");
                stringBuilder.append(((MtpObject)((Object)iterator)).getPath());
                Log.w((String)((Object)arrayList), stringBuilder.toString());
            }
            if (((MtpObject)((Object)iterator)).isRoot()) {
                bl3 = this.mRoots.remove(((MtpObject)((Object)iterator)).getId(), iterator) && bl4;
            } else {
                bl3 = bl4;
                if (var2_2 != false) {
                    bl3 = this.mObjects.remove(((MtpObject)((Object)iterator)).getId(), iterator) && bl4;
                }
            }
            if (!bl3 && sDebug) {
                arrayList = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to remove from global cache ");
                stringBuilder.append(((MtpObject)((Object)iterator)).getPath());
                Log.w(arrayList, stringBuilder.toString());
            }
            if (iterator.getObserver() != null) {
                iterator.getObserver().stopWatching();
                iterator.setObserver(null);
            }
            bl4 = bl3;
            if (((MtpObject)((Object)iterator)).isDir()) {
                boolean bl5;
                bl4 = bl3;
                if (bl5) {
                    arrayList = new ArrayList(iterator.getChildren());
                    iterator = arrayList.iterator();
                    do {
                        bl4 = bl3;
                        if (!iterator.hasNext()) break;
                        bl5 = this.removeObjectFromCache((MtpObject)iterator.next(), (boolean)var2_2, true);
                        bl5 = bl5 && bl3;
                        bl3 = bl5;
                    } while (true);
                }
            }
            return bl4;
        }
    }

    public MtpStorage addMtpStorage(StorageVolume object) {
        synchronized (this) {
            int n = ((this.getNextStorageId() & 65535) << 16) + 1;
            MtpStorage mtpStorage = new MtpStorage((StorageVolume)object, n);
            object = new MtpObject(mtpStorage.getPath(), n, mtpStorage, null, true);
            this.mRoots.put(n, (MtpObject)object);
            return mtpStorage;
        }
    }

    public int beginCopyObject(MtpObject mtpObject, MtpObject mtpObject2) {
        synchronized (this) {
            block11 : {
                boolean bl;
                block10 : {
                    Object object;
                    block9 : {
                        block8 : {
                            if (sDebug) {
                                String string2 = TAG;
                                object = new StringBuilder();
                                ((StringBuilder)object).append("beginCopyObject ");
                                ((StringBuilder)object).append(mtpObject.getName());
                                ((StringBuilder)object).append(" to ");
                                ((StringBuilder)object).append(mtpObject2.getPath());
                                Log.v(string2, ((StringBuilder)object).toString());
                            }
                            object = mtpObject.getName();
                            bl = mtpObject2.isDir();
                            if (bl) break block8;
                            return -1;
                        }
                        if (!mtpObject2.isRoot() || this.mSubdirectories == null || (bl = this.mSubdirectories.contains(object))) break block9;
                        return -1;
                    }
                    this.getChildren(mtpObject2);
                    object = mtpObject2.getChild((String)object);
                    if (object == null) break block10;
                    return -1;
                }
                mtpObject = mtpObject.copy(mtpObject.isDir());
                mtpObject2.addChild(mtpObject);
                mtpObject.setParent(mtpObject2);
                bl = this.generalBeginCopyObject(mtpObject, true);
                if (bl) break block11;
                return -1;
            }
            int n = mtpObject.getId();
            return n;
        }
    }

    public boolean beginMoveObject(MtpObject mtpObject, MtpObject mtpObject2) {
        synchronized (this) {
            Object object;
            boolean bl;
            block12 : {
                block13 : {
                    boolean bl2;
                    block11 : {
                        block10 : {
                            block9 : {
                                if (sDebug) {
                                    String string2 = TAG;
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("beginMoveObject ");
                                    ((StringBuilder)object).append(mtpObject2.getPath());
                                    Log.v(string2, ((StringBuilder)object).toString());
                                }
                                if (!(bl = mtpObject.isRoot())) break block9;
                                return false;
                            }
                            bl = this.isSpecialSubDir(mtpObject);
                            if (!bl) break block10;
                            return false;
                        }
                        this.getChildren(mtpObject2);
                        object = mtpObject2.getChild(mtpObject.getName());
                        if (object == null) break block11;
                        return false;
                    }
                    if (mtpObject.getStorageId() == mtpObject2.getStorageId()) break block12;
                    bl = true;
                    object = mtpObject.copy(true);
                    ((MtpObject)object).setParent(mtpObject2);
                    mtpObject2.addChild((MtpObject)object);
                    if (this.generalBeginRemoveObject(mtpObject, MtpOperation.RENAME) && (bl2 = this.generalBeginCopyObject((MtpObject)object, false))) break block13;
                    bl = false;
                }
                return bl;
            }
            object = mtpObject.copy(false);
            mtpObject.setParent(mtpObject2);
            ((MtpObject)object).getParent().addChild((MtpObject)object);
            mtpObject.getParent().addChild(mtpObject);
            bl = this.generalBeginRenameObject((MtpObject)object, mtpObject);
            return bl;
        }
    }

    public boolean beginRemoveObject(MtpObject mtpObject) {
        synchronized (this) {
            boolean bl;
            if (sDebug) {
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("beginRemoveObject ");
                stringBuilder.append(mtpObject.getName());
                Log.v(string2, stringBuilder.toString());
            }
            bl = !mtpObject.isRoot() && !this.isSpecialSubDir(mtpObject) && (bl = this.generalBeginRemoveObject(mtpObject, MtpOperation.DELETE));
            return bl;
        }
    }

    public boolean beginRenameObject(MtpObject mtpObject, String string2) {
        synchronized (this) {
            Object object;
            boolean bl;
            block9 : {
                block8 : {
                    block7 : {
                        if (sDebug) {
                            String string3 = TAG;
                            object = new StringBuilder();
                            ((StringBuilder)object).append("beginRenameObject ");
                            ((StringBuilder)object).append(mtpObject.getName());
                            ((StringBuilder)object).append(" ");
                            ((StringBuilder)object).append(string2);
                            Log.v(string3, ((StringBuilder)object).toString());
                        }
                        if (!(bl = mtpObject.isRoot())) break block7;
                        return false;
                    }
                    bl = this.isSpecialSubDir(mtpObject);
                    if (!bl) break block8;
                    return false;
                }
                object = mtpObject.getParent().getChild(string2);
                if (object == null) break block9;
                return false;
            }
            object = mtpObject.copy(false);
            mtpObject.setName(string2);
            mtpObject.getParent().addChild(mtpObject);
            ((MtpObject)object).getParent().addChild((MtpObject)object);
            bl = this.generalBeginRenameObject((MtpObject)object, mtpObject);
            return bl;
        }
    }

    public int beginSendObject(MtpObject mtpObject, String string2, int n) {
        synchronized (this) {
            block10 : {
                boolean bl;
                block9 : {
                    block8 : {
                        if (sDebug) {
                            String string3 = TAG;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("beginSendObject ");
                            stringBuilder.append(string2);
                            Log.v(string3, stringBuilder.toString());
                        }
                        if (bl = mtpObject.isDir()) break block8;
                        return -1;
                    }
                    if (!mtpObject.isRoot() || this.mSubdirectories == null || (bl = this.mSubdirectories.contains(string2))) break block9;
                    return -1;
                }
                this.getChildren(mtpObject);
                bl = n == 12289;
                mtpObject = this.addObjectToCache(mtpObject, string2, bl);
                if (mtpObject != null) break block10;
                return -1;
            }
            mtpObject.setState(MtpObjectState.FROZEN);
            mtpObject.setOperation(MtpOperation.ADD);
            n = mtpObject.getId();
            return n;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public boolean checkConsistency() {
        // MONITORENTER : this
        Object object = new ArrayList();
        object.addAll(this.mRoots.values());
        object.addAll(this.mObjects.values());
        boolean bl = true;
        Iterator iterator = object.iterator();
        do {
            boolean bl2;
            block33 : {
                HashSet<String> hashSet;
                Object object222;
                StringBuilder stringBuilder;
                if (!iterator.hasNext()) {
                    // MONITOREXIT : this
                    return bl;
                }
                MtpObject mtpObject = (MtpObject)iterator.next();
                if (!mtpObject.exists()) {
                    object = TAG;
                    hashSet = new HashSet<String>();
                    ((StringBuilder)((Object)hashSet)).append("Object doesn't exist ");
                    ((StringBuilder)((Object)hashSet)).append(mtpObject.getPath());
                    ((StringBuilder)((Object)hashSet)).append(" ");
                    ((StringBuilder)((Object)hashSet)).append(mtpObject.getId());
                    Log.w((String)object, ((StringBuilder)((Object)hashSet)).toString());
                    bl = false;
                }
                if (mtpObject.getState() != MtpObjectState.NORMAL) {
                    hashSet = TAG;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Object ");
                    ((StringBuilder)object).append(mtpObject.getPath());
                    ((StringBuilder)object).append(" in state ");
                    ((StringBuilder)object).append((Object)mtpObject.getState());
                    Log.w((String)((Object)hashSet), ((StringBuilder)object).toString());
                    bl = false;
                }
                if (mtpObject.getOperation() != MtpOperation.NONE) {
                    hashSet = TAG;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Object ");
                    ((StringBuilder)object).append(mtpObject.getPath());
                    ((StringBuilder)object).append(" in operation ");
                    ((StringBuilder)object).append((Object)mtpObject.getOperation());
                    Log.w((String)((Object)hashSet), ((StringBuilder)object).toString());
                    bl = false;
                }
                bl2 = bl;
                if (!mtpObject.isRoot()) {
                    bl2 = bl;
                    if (this.mObjects.get(mtpObject.getId()) != mtpObject) {
                        object = TAG;
                        hashSet = new HashSet<String>();
                        ((StringBuilder)((Object)hashSet)).append("Object ");
                        ((StringBuilder)((Object)hashSet)).append(mtpObject.getPath());
                        ((StringBuilder)((Object)hashSet)).append(" is not in map correctly");
                        Log.w(object, ((StringBuilder)((Object)hashSet)).toString());
                        bl2 = false;
                    }
                }
                bl = bl2;
                if (mtpObject.getParent() != null) {
                    bl = bl2;
                    if (mtpObject.getParent().isRoot()) {
                        bl = bl2;
                        if (mtpObject.getParent() != this.mRoots.get(mtpObject.getParent().getId())) {
                            hashSet = TAG;
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Root parent is not in root mapping ");
                            ((StringBuilder)object).append(mtpObject.getPath());
                            Log.w(hashSet, ((StringBuilder)object).toString());
                            bl = false;
                        }
                    }
                    bl2 = bl;
                    if (!mtpObject.getParent().isRoot()) {
                        bl2 = bl;
                        if (mtpObject.getParent() != this.mObjects.get(mtpObject.getParent().getId())) {
                            object = TAG;
                            hashSet = new HashSet<String>();
                            ((StringBuilder)((Object)hashSet)).append("Parent is not in object mapping ");
                            ((StringBuilder)((Object)hashSet)).append(mtpObject.getPath());
                            Log.w((String)object, ((StringBuilder)((Object)hashSet)).toString());
                            bl2 = false;
                        }
                    }
                    bl = bl2;
                    if (mtpObject.getParent().getChild(mtpObject.getName()) != mtpObject) {
                        hashSet = TAG;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Child does not exist in parent ");
                        ((StringBuilder)object).append(mtpObject.getPath());
                        Log.w(hashSet, ((StringBuilder)object).toString());
                        bl = false;
                    }
                }
                bl2 = bl;
                if (!mtpObject.isDir()) break block33;
                boolean bl3 = mtpObject.isVisited();
                if (bl3 == (bl2 = mtpObject.getObserver() == null)) {
                    object222 = TAG;
                    hashSet = new HashSet<String>();
                    ((StringBuilder)((Object)hashSet)).append(mtpObject.getPath());
                    ((StringBuilder)((Object)hashSet)).append(" is ");
                    object = mtpObject.isVisited() ? "" : "not ";
                    ((StringBuilder)((Object)hashSet)).append((String)object);
                    ((StringBuilder)((Object)hashSet)).append(" visited but observer is ");
                    ((StringBuilder)((Object)hashSet)).append(mtpObject.getObserver());
                    Log.w((String)object222, ((StringBuilder)((Object)hashSet)).toString());
                    bl = false;
                }
                bl2 = bl;
                if (!mtpObject.isVisited()) {
                    bl2 = bl;
                    if (mtpObject.getChildren().size() > 0) {
                        hashSet = TAG;
                        object = new StringBuilder();
                        ((StringBuilder)object).append(mtpObject.getPath());
                        ((StringBuilder)object).append(" is not visited but has children");
                        Log.w(hashSet, ((StringBuilder)object).toString());
                        bl2 = false;
                    }
                }
                object = Files.newDirectoryStream(mtpObject.getPath());
                hashSet = new HashSet<String>();
                Object object3 = object.iterator();
                while (object3.hasNext()) {
                    block34 : {
                        block35 : {
                            object222 = (Path)object3.next();
                            bl = bl2;
                            if (!mtpObject.isVisited()) break block34;
                            bl = bl2;
                            if (mtpObject.getChild(object222.getFileName().toString()) != null) break block34;
                            if (this.mSubdirectories == null || !mtpObject.isRoot()) break block35;
                            bl = bl2;
                            if (!this.mSubdirectories.contains(object222.getFileName().toString())) break block34;
                        }
                        String string2 = TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("File exists in fs but not in children ");
                        stringBuilder.append(object222);
                        Log.w(string2, stringBuilder.toString());
                        bl = false;
                    }
                    hashSet.add(object222.toString());
                    bl2 = bl;
                }
                for (Object object222 : mtpObject.getChildren()) {
                    if (!hashSet.contains(((MtpObject)object222).getPath().toString())) {
                        object3 = TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("File in children doesn't exist in fs ");
                        stringBuilder.append(((MtpObject)object222).getPath());
                        Log.w((String)object3, stringBuilder.toString());
                        bl2 = false;
                    }
                    if (object222 == this.mObjects.get(((MtpObject)object222).getId())) continue;
                    object3 = TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Child is not in object map ");
                    stringBuilder.append(((MtpObject)object222).getPath());
                    Log.w((String)object3, stringBuilder.toString());
                    bl2 = false;
                }
                MtpStorageManager.$closeResource(null, (AutoCloseable)object);
                catch (Throwable throwable) {
                    try {
                        throw throwable;
                    }
                    catch (Throwable throwable2) {
                        if (object == null) throw throwable2;
                        try {
                            MtpStorageManager.$closeResource(throwable, (AutoCloseable)object);
                            throw throwable2;
                        }
                        catch (IOException | DirectoryIteratorException exception) {
                            Log.w(TAG, exception.toString());
                            bl2 = false;
                        }
                    }
                }
            }
            bl = bl2;
        } while (true);
    }

    public void close() {
        synchronized (this) {
            for (MtpObject mtpObject : this.mObjects.values()) {
                if (mtpObject.getObserver() == null) continue;
                mtpObject.getObserver().stopWatching();
                mtpObject.setObserver(null);
            }
            for (MtpObject mtpObject : this.mRoots.values()) {
                if (mtpObject.getObserver() == null) continue;
                mtpObject.getObserver().stopWatching();
                mtpObject.setObserver(null);
            }
            if (this.mCheckConsistency) {
                this.mCheckConsistency = false;
                this.mConsistencyThread.interrupt();
                try {
                    this.mConsistencyThread.join();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dump() {
        synchronized (this) {
            Iterator<Integer> iterator = this.mObjects.keySet().iterator();
            while (iterator.hasNext()) {
                int n = iterator.next();
                MtpObject mtpObject = this.mObjects.get(n);
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(n);
                stringBuilder.append(" | ");
                Object object = mtpObject.getParent() == null ? Integer.valueOf(mtpObject.getParent().getId()) : "null";
                stringBuilder.append(object);
                stringBuilder.append(" | ");
                stringBuilder.append(mtpObject.getName());
                stringBuilder.append(" | ");
                object = mtpObject.isDir() ? "dir" : "obj";
                stringBuilder.append((String)object);
                stringBuilder.append(" | ");
                object = mtpObject.isVisited() ? "v" : "nv";
                stringBuilder.append((String)object);
                stringBuilder.append(" | ");
                stringBuilder.append((Object)mtpObject.getState());
                Log.i(string2, stringBuilder.toString());
            }
            return;
        }
    }

    public boolean endCopyObject(MtpObject mtpObject, boolean bl) {
        synchronized (this) {
            if (sDebug) {
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("endCopyObject ");
                stringBuilder.append(mtpObject.getName());
                stringBuilder.append(" ");
                stringBuilder.append(bl);
                Log.v(string2, stringBuilder.toString());
            }
            bl = this.generalEndCopyObject(mtpObject, bl, false);
            return bl;
        }
    }

    public boolean endMoveObject(MtpObject mtpObject, MtpObject mtpObject2, String object, boolean bl) {
        synchronized (this) {
            block9 : {
                Object object2;
                Object object3;
                block10 : {
                    block11 : {
                        if (sDebug) {
                            object3 = TAG;
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("endMoveObject ");
                            ((StringBuilder)object2).append(bl);
                            Log.v((String)object3, ((StringBuilder)object2).toString());
                        }
                        object3 = mtpObject.getChild((String)object);
                        mtpObject2 = mtpObject2.getChild((String)object);
                        boolean bl2 = false;
                        if (object3 == null || mtpObject2 == null) break block9;
                        if (mtpObject.getStorageId() == mtpObject2.getStorageId()) break block10;
                        boolean bl3 = this.endRemoveObject((MtpObject)object3, bl);
                        boolean bl4 = this.generalEndCopyObject(mtpObject2, bl, true);
                        bl = bl2;
                        if (!bl4) break block11;
                        bl = bl2;
                        if (!bl3) break block11;
                        bl = true;
                    }
                    return bl;
                }
                object2 = object3;
                object = mtpObject2;
                if (!bl) {
                    object = ((MtpObject)object3).getState();
                    ((MtpObject)object3).setParent(mtpObject2.getParent());
                    ((MtpObject)object3).setState(mtpObject2.getState());
                    mtpObject2.setParent(mtpObject);
                    mtpObject2.setState((MtpObjectState)((Object)object));
                    object = object3;
                    ((MtpObject)object).getParent().addChild((MtpObject)object);
                    mtpObject.addChild(mtpObject2);
                    object2 = mtpObject2;
                }
                bl = this.generalEndRenameObject((MtpObject)object2, (MtpObject)object, bl);
                return bl;
            }
            return false;
        }
    }

    public boolean endRemoveObject(MtpObject mtpObject, boolean bl) {
        synchronized (this) {
            block12 : {
                boolean bl2;
                boolean bl3;
                boolean bl4;
                block11 : {
                    Object object;
                    Object object2;
                    if (sDebug) {
                        object2 = TAG;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("endRemoveObject ");
                        ((StringBuilder)object).append(bl);
                        Log.v((String)object2, ((StringBuilder)object).toString());
                    }
                    bl4 = true;
                    boolean bl5 = true;
                    bl3 = mtpObject.isDir();
                    bl2 = false;
                    if (!bl3) break block11;
                    object2 = new ArrayList(mtpObject.getChildren());
                    object2 = ((ArrayList)object2).iterator();
                    do {
                        bl4 = bl5;
                        if (!object2.hasNext()) break;
                        object = (MtpObject)object2.next();
                        bl4 = bl5;
                        if (((MtpObject)object).getOperation() == MtpOperation.DELETE) {
                            bl5 = this.endRemoveObject((MtpObject)object, bl) && bl5;
                            bl4 = bl5;
                        }
                        bl5 = bl4;
                        continue;
                        break;
                    } while (true);
                }
                bl3 = this.generalEndRemoveObject(mtpObject, bl, true);
                bl = bl2;
                if (!bl3) break block12;
                bl = bl2;
                if (!bl4) break block12;
                bl = true;
            }
            return bl;
        }
    }

    public boolean endRenameObject(MtpObject mtpObject, String string2, boolean bl) {
        synchronized (this) {
            Object object;
            Object object2;
            block6 : {
                Object object3;
                if (sDebug) {
                    object3 = TAG;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("endRenameObject ");
                    ((StringBuilder)object2).append(bl);
                    Log.v((String)object3, ((StringBuilder)object2).toString());
                }
                MtpObject mtpObject2 = mtpObject.getParent();
                object = object3 = mtpObject2.getChild(string2);
                object2 = mtpObject;
                if (bl) break block6;
                object2 = ((MtpObject)object3).getState();
                ((MtpObject)object3).setName(mtpObject.getName());
                ((MtpObject)object3).setState(mtpObject.getState());
                mtpObject.setName(string2);
                mtpObject.setState((MtpObjectState)((Object)object2));
                mtpObject2.addChild((MtpObject)object3);
                mtpObject2.addChild(mtpObject);
                object2 = object3;
                object = mtpObject;
            }
            bl = this.generalEndRenameObject((MtpObject)object, (MtpObject)object2, bl);
            return bl;
        }
    }

    public boolean endSendObject(MtpObject mtpObject, boolean bl) {
        synchronized (this) {
            if (sDebug) {
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("endSendObject ");
                stringBuilder.append(bl);
                Log.v(string2, stringBuilder.toString());
            }
            bl = this.generalEndAddObject(mtpObject, bl, true);
            return bl;
        }
    }

    public void flushEvents() {
        try {
            Thread.sleep(500L);
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public MtpObject getByPath(String object) {
        synchronized (this) {
            Object var2_2 = null;
            Iterator<MtpObject> iterator = this.mRoots.values().iterator();
            String[] arrstring = object;
            object = var2_2;
            while (iterator.hasNext()) {
                void var2_6;
                MtpObject mtpObject = iterator.next();
                String[] arrstring2 = arrstring;
                if (arrstring.startsWith(mtpObject.getName())) {
                    object = mtpObject;
                    String string2 = arrstring.substring(mtpObject.getName().length());
                }
                arrstring = var2_6;
            }
            arrstring = arrstring.split("/");
            int n = arrstring.length;
            int n2 = 0;
            do {
                if (n2 >= n) {
                    return object;
                }
                String string3 = arrstring[n2];
                if (object == null || !((MtpObject)object).isDir()) break;
                if (!"".equals(string3)) {
                    if (!((MtpObject)object).isVisited()) {
                        this.getChildren((MtpObject)object);
                    }
                    object = ((MtpObject)object).getChild(string3);
                }
                ++n2;
            } while (true);
            return null;
        }
    }

    public MtpObject getObject(int n) {
        synchronized (this) {
            if (n != 0 && n != -1) {
                block6 : {
                    if (this.mObjects.containsKey(n)) break block6;
                    String string2 = TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Id ");
                    stringBuilder.append(n);
                    stringBuilder.append(" doesn't exist");
                    Log.w(string2, stringBuilder.toString());
                    return null;
                }
                MtpObject mtpObject = this.mObjects.get(n);
                return mtpObject;
            }
            Log.w(TAG, "Can't get root storages with getObject()");
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<MtpObject> getObjects(int n, int n2, int n3) {
        synchronized (this) {
            boolean bl = n == 0;
            ArrayList<MtpObject> arrayList = new ArrayList<MtpObject>();
            int n4 = 1;
            int n5 = n;
            if (n == -1) {
                n5 = 0;
            }
            Iterator<MtpObject> iterator = null;
            Object object = null;
            if (n3 == -1 && n5 == 0) {
                iterator = this.mRoots.values().iterator();
                n = n4;
                while (iterator.hasNext()) {
                    int n6 = this.getObjects(arrayList, iterator.next(), n2, bl);
                    n &= n6;
                }
                if (n == 0) return object;
                return arrayList;
            }
            object = n5 == 0 ? this.getStorageRoot(n3) : this.getObject(n5);
            if (object == null) {
                return null;
            }
            bl = this.getObjects(arrayList, (MtpObject)object, n2, bl);
            object = iterator;
            if (!bl) return object;
            return arrayList;
        }
    }

    public MtpObject getStorageRoot(int n) {
        if (!this.mRoots.containsKey(n)) {
            String string2 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("StorageId ");
            stringBuilder.append(n);
            stringBuilder.append(" doesn't exist");
            Log.w(string2, stringBuilder.toString());
            return null;
        }
        return this.mRoots.get(n);
    }

    public /* synthetic */ void lambda$new$0$MtpStorageManager() {
        while (this.mCheckConsistency) {
            block3 : {
                try {
                    Thread.sleep(15000L);
                    if (!this.checkConsistency()) break block3;
                }
                catch (InterruptedException interruptedException) {
                    return;
                }
                Log.v(TAG, "Cache is consistent");
                continue;
            }
            Log.w(TAG, "Cache is not consistent");
        }
    }

    public void removeMtpStorage(MtpStorage mtpStorage) {
        synchronized (this) {
            this.removeObjectFromCache(this.getStorageRoot(mtpStorage.getStorageId()), true, true);
            return;
        }
    }

    public void setSubdirectories(Set<String> set) {
        synchronized (this) {
            this.mSubdirectories = set;
            return;
        }
    }

    public static abstract class MtpNotifier {
        public abstract void sendObjectAdded(int var1);

        public abstract void sendObjectInfoChanged(int var1);

        public abstract void sendObjectRemoved(int var1);
    }

    public static class MtpObject {
        private HashMap<String, MtpObject> mChildren;
        private int mId;
        private boolean mIsDir;
        private String mName;
        private FileObserver mObserver;
        private MtpOperation mOp;
        private MtpObject mParent;
        private MtpObjectState mState;
        private MtpStorage mStorage;
        private boolean mVisited;

        MtpObject(String object, int n, MtpStorage mtpStorage, MtpObject mtpObject, boolean bl) {
            this.mId = n;
            this.mName = object;
            this.mStorage = Preconditions.checkNotNull(mtpStorage);
            this.mParent = mtpObject;
            object = null;
            this.mObserver = null;
            this.mVisited = false;
            this.mState = MtpObjectState.NORMAL;
            this.mIsDir = bl;
            this.mOp = MtpOperation.NONE;
            if (this.mIsDir) {
                object = new HashMap();
            }
            this.mChildren = object;
        }

        static /* synthetic */ void access$1600(MtpObject mtpObject, boolean bl) {
            mtpObject.setDir(bl);
        }

        private void addChild(MtpObject mtpObject) {
            this.mChildren.put(mtpObject.getName(), mtpObject);
        }

        private MtpObject copy(boolean bl) {
            MtpObject mtpObject = new MtpObject(this.mName, this.mId, this.mStorage, this.mParent, this.mIsDir);
            mtpObject.mIsDir = this.mIsDir;
            mtpObject.mVisited = this.mVisited;
            mtpObject.mState = this.mState;
            HashMap hashMap = this.mIsDir ? new HashMap() : null;
            mtpObject.mChildren = hashMap;
            if (bl && this.mIsDir) {
                Iterator<MtpObject> iterator = this.mChildren.values().iterator();
                while (iterator.hasNext()) {
                    hashMap = iterator.next().copy(true);
                    MtpObject.super.setParent(mtpObject);
                    mtpObject.addChild((MtpObject)((Object)hashMap));
                }
            }
            return mtpObject;
        }

        private boolean exists() {
            return this.getPath().toFile().exists();
        }

        private MtpObject getChild(String string2) {
            return this.mChildren.get(string2);
        }

        private Collection<MtpObject> getChildren() {
            return this.mChildren.values();
        }

        private FileObserver getObserver() {
            return this.mObserver;
        }

        private MtpOperation getOperation() {
            return this.mOp;
        }

        private MtpObjectState getState() {
            return this.mState;
        }

        private boolean isVisited() {
            return this.mVisited;
        }

        private void setDir(boolean bl) {
            if (bl != this.mIsDir) {
                this.mIsDir = bl;
                HashMap hashMap = this.mIsDir ? new HashMap() : null;
                this.mChildren = hashMap;
            }
        }

        private void setId(int n) {
            this.mId = n;
        }

        private void setName(String string2) {
            this.mName = string2;
        }

        private void setObserver(FileObserver fileObserver) {
            this.mObserver = fileObserver;
        }

        private void setOperation(MtpOperation mtpOperation) {
            this.mOp = mtpOperation;
        }

        private void setParent(MtpObject mtpObject) {
            this.mParent = mtpObject;
        }

        private void setState(MtpObjectState mtpObjectState) {
            this.mState = mtpObjectState;
            if (this.mState == MtpObjectState.NORMAL) {
                this.mOp = MtpOperation.NONE;
            }
        }

        private void setVisited(boolean bl) {
            this.mVisited = bl;
        }

        public int getFormat() {
            int n = this.mIsDir ? 12289 : MediaFile.getFormatCode(this.mName, null);
            return n;
        }

        public int getId() {
            return this.mId;
        }

        public long getModifiedTime() {
            return this.getPath().toFile().lastModified() / 1000L;
        }

        public String getName() {
            return this.mName;
        }

        public MtpObject getParent() {
            return this.mParent;
        }

        public Path getPath() {
            Path path = this.isRoot() ? Paths.get(this.mName, new String[0]) : this.mParent.getPath().resolve(this.mName);
            return path;
        }

        public MtpObject getRoot() {
            MtpObject mtpObject = this.isRoot() ? this : this.mParent.getRoot();
            return mtpObject;
        }

        public long getSize() {
            long l = this.mIsDir ? 0L : this.getPath().toFile().length();
            return l;
        }

        public int getStorageId() {
            return this.getRoot().getId();
        }

        public String getVolumeName() {
            return this.mStorage.getVolumeName();
        }

        public boolean isDir() {
            return this.mIsDir;
        }

        public boolean isRoot() {
            boolean bl = this.mParent == null;
            return bl;
        }
    }

    private class MtpObjectObserver
    extends FileObserver {
        MtpObject mObject;

        MtpObjectObserver(MtpObject mtpObject) {
            super(mtpObject.getPath().toString(), 16778184);
            this.mObject = mtpObject;
        }

        @Override
        public void finalize() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onEvent(int n, String charSequence) {
            MtpStorageManager mtpStorageManager = MtpStorageManager.this;
            synchronized (mtpStorageManager) {
                if ((n & 16384) != 0) {
                    Log.e(TAG, "Received Inotify overflow event!");
                }
                Object object = this.mObject.getChild((String)charSequence);
                if ((n & 128) == 0 && (n & 256) == 0) {
                    if ((n & 64) == 0 && (n & 512) == 0) {
                        if ((32768 & n) != 0) {
                            if (sDebug) {
                                String string2 = TAG;
                                charSequence = new StringBuilder();
                                ((StringBuilder)charSequence).append("inotify for ");
                                ((StringBuilder)charSequence).append(this.mObject.getPath());
                                ((StringBuilder)charSequence).append(" deleted");
                                Log.i(string2, ((StringBuilder)charSequence).toString());
                            }
                            if (this.mObject.mObserver != null) {
                                this.mObject.mObserver.stopWatching();
                            }
                            this.mObject.mObserver = null;
                        } else if ((n & 8) != 0) {
                            if (sDebug) {
                                object = TAG;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("inotify for ");
                                stringBuilder.append(this.mObject.getPath());
                                stringBuilder.append(" CLOSE_WRITE: ");
                                stringBuilder.append((String)charSequence);
                                Log.i((String)object, stringBuilder.toString());
                            }
                            MtpStorageManager.this.handleChangedObject(this.mObject, (String)charSequence);
                        } else {
                            object = TAG;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Got unrecognized event ");
                            stringBuilder.append((String)charSequence);
                            stringBuilder.append(" ");
                            stringBuilder.append(n);
                            Log.w((String)object, stringBuilder.toString());
                        }
                    } else {
                        if (object == null) {
                            String string3 = TAG;
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Object was null in event ");
                            ((StringBuilder)object).append((String)charSequence);
                            Log.w(string3, ((StringBuilder)object).toString());
                            return;
                        }
                        if (sDebug) {
                            String string4 = TAG;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Got inotify removed event for ");
                            stringBuilder.append((String)charSequence);
                            stringBuilder.append(" ");
                            stringBuilder.append(n);
                            Log.i(string4, stringBuilder.toString());
                        }
                        MtpStorageManager.this.handleRemovedObject((MtpObject)object);
                    }
                } else {
                    Object object2;
                    if (sDebug) {
                        object2 = TAG;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Got inotify added event for ");
                        ((StringBuilder)object).append((String)charSequence);
                        ((StringBuilder)object).append(" ");
                        ((StringBuilder)object).append(n);
                        Log.i((String)object2, ((StringBuilder)object).toString());
                    }
                    object = MtpStorageManager.this;
                    object2 = this.mObject;
                    boolean bl = (1073741824 & n) != 0;
                    ((MtpStorageManager)object).handleAddedObject((MtpObject)object2, (String)charSequence, bl);
                }
                return;
            }
        }
    }

    private static enum MtpObjectState {
        NORMAL,
        FROZEN,
        FROZEN_ADDED,
        FROZEN_REMOVED,
        FROZEN_ONESHOT_ADD,
        FROZEN_ONESHOT_DEL;
        
    }

    private static enum MtpOperation {
        NONE,
        ADD,
        RENAME,
        COPY,
        DELETE;
        
    }

}

