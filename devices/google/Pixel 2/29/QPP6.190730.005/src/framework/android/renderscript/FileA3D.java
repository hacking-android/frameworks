/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.renderscript;

import android.annotation.UnsupportedAppUsage;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.renderscript.BaseObj;
import android.renderscript.Mesh;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import dalvik.system.CloseGuard;
import java.io.File;
import java.io.InputStream;

public class FileA3D
extends BaseObj {
    IndexEntry[] mFileEntries;
    InputStream mInputStream;

    FileA3D(long l, RenderScript renderScript, InputStream inputStream) {
        super(l, renderScript);
        this.mInputStream = inputStream;
        this.guard.open("destroy");
    }

    public static FileA3D createFromAsset(RenderScript object, AssetManager assetManager, String string2) {
        ((RenderScript)object).validate();
        long l = ((RenderScript)object).nFileA3DCreateFromAsset(assetManager, string2);
        if (l != 0L) {
            object = new FileA3D(l, (RenderScript)object, null);
            FileA3D.super.initEntries();
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unable to create a3d file from asset ");
        ((StringBuilder)object).append(string2);
        throw new RSRuntimeException(((StringBuilder)object).toString());
    }

    public static FileA3D createFromFile(RenderScript renderScript, File file) {
        return FileA3D.createFromFile(renderScript, file.getAbsolutePath());
    }

    public static FileA3D createFromFile(RenderScript object, String string2) {
        long l = ((RenderScript)object).nFileA3DCreateFromFile(string2);
        if (l != 0L) {
            object = new FileA3D(l, (RenderScript)object, null);
            FileA3D.super.initEntries();
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unable to create a3d file from ");
        ((StringBuilder)object).append(string2);
        throw new RSRuntimeException(((StringBuilder)object).toString());
    }

    @UnsupportedAppUsage
    public static FileA3D createFromResource(RenderScript object, Resources object2, int n) {
        ((RenderScript)object).validate();
        try {
            object2 = ((Resources)object2).openRawResource(n);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to open resource ");
            stringBuilder.append(n);
            throw new RSRuntimeException(stringBuilder.toString());
        }
        if (object2 instanceof AssetManager.AssetInputStream) {
            long l = ((RenderScript)object).nFileA3DCreateFromAssetStream(((AssetManager.AssetInputStream)object2).getNativeAsset());
            if (l != 0L) {
                object = new FileA3D(l, (RenderScript)object, (InputStream)object2);
                FileA3D.super.initEntries();
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to create a3d file from resource ");
            ((StringBuilder)object).append(n);
            throw new RSRuntimeException(((StringBuilder)object).toString());
        }
        throw new RSRuntimeException("Unsupported asset stream");
    }

    private void initEntries() {
        int n = this.mRS.nFileA3DGetNumIndexEntries(this.getID(this.mRS));
        if (n <= 0) {
            return;
        }
        this.mFileEntries = new IndexEntry[n];
        int[] arrn = new int[n];
        String[] arrstring = new String[n];
        this.mRS.nFileA3DGetIndexEntries(this.getID(this.mRS), n, arrn, arrstring);
        for (int i = 0; i < n; ++i) {
            this.mFileEntries[i] = new IndexEntry(this.mRS, i, this.getID(this.mRS), arrstring[i], EntryType.toEntryType(arrn[i]));
        }
    }

    @UnsupportedAppUsage
    public IndexEntry getIndexEntry(int n) {
        IndexEntry[] arrindexEntry;
        if (this.getIndexEntryCount() != 0 && n >= 0 && n < (arrindexEntry = this.mFileEntries).length) {
            return arrindexEntry[n];
        }
        return null;
    }

    public int getIndexEntryCount() {
        IndexEntry[] arrindexEntry = this.mFileEntries;
        if (arrindexEntry == null) {
            return 0;
        }
        return arrindexEntry.length;
    }

    public static enum EntryType {
        UNKNOWN(0),
        MESH(1);
        
        int mID;

        private EntryType(int n2) {
            this.mID = n2;
        }

        static EntryType toEntryType(int n) {
            return EntryType.values()[n];
        }
    }

    public static class IndexEntry {
        EntryType mEntryType;
        long mID;
        int mIndex;
        BaseObj mLoadedObj;
        String mName;
        RenderScript mRS;

        IndexEntry(RenderScript renderScript, int n, long l, String string2, EntryType entryType) {
            this.mRS = renderScript;
            this.mIndex = n;
            this.mID = l;
            this.mName = string2;
            this.mEntryType = entryType;
            this.mLoadedObj = null;
        }

        static BaseObj internalCreate(RenderScript object, IndexEntry indexEntry) {
            synchronized (IndexEntry.class) {
                long l;
                Object object2;
                block11 : {
                    block10 : {
                        block9 : {
                            if (indexEntry.mLoadedObj == null) break block9;
                            object = indexEntry.mLoadedObj;
                            return object;
                        }
                        object2 = indexEntry.mEntryType;
                        EntryType entryType = EntryType.UNKNOWN;
                        if (object2 != entryType) break block10;
                        return null;
                    }
                    l = ((RenderScript)object).nFileA3DGetEntryByIndex(indexEntry.mID, indexEntry.mIndex);
                    if (l != 0L) break block11;
                    return null;
                }
                if (1.$SwitchMap$android$renderscript$FileA3D$EntryType[indexEntry.mEntryType.ordinal()] == 1) {
                    object2 = new Mesh(l, (RenderScript)object);
                    indexEntry.mLoadedObj = object2;
                    indexEntry.mLoadedObj.updateFromNative();
                    object = indexEntry.mLoadedObj;
                    return object;
                }
                object = new RSRuntimeException("Unrecognized object type in file.");
                throw object;
            }
        }

        @UnsupportedAppUsage
        public EntryType getEntryType() {
            return this.mEntryType;
        }

        public Mesh getMesh() {
            return (Mesh)this.getObject();
        }

        public String getName() {
            return this.mName;
        }

        @UnsupportedAppUsage
        public BaseObj getObject() {
            this.mRS.validate();
            return IndexEntry.internalCreate(this.mRS, this);
        }
    }

}

