/*
 * Decompiled with CFR 0.145.
 */
package android.app.slice;

import android.app.slice.ISliceManager;
import android.app.slice.Slice;
import android.app.slice.SliceSpec;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.ArraySet;
import com.android.internal.util.Preconditions;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class SliceManager {
    public static final String ACTION_REQUEST_SLICE_PERMISSION = "com.android.intent.action.REQUEST_SLICE_PERMISSION";
    public static final String CATEGORY_SLICE = "android.app.slice.category.SLICE";
    public static final String SLICE_METADATA_KEY = "android.metadata.SLICE_URI";
    private static final String TAG = "SliceManager";
    private final Context mContext;
    private final ISliceManager mService;
    private final IBinder mToken = new Binder();

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

    public SliceManager(Context context, Handler handler) throws ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mService = ISliceManager.Stub.asInterface(ServiceManager.getServiceOrThrow("slice"));
    }

    private String getAuthority(Intent object) {
        if (!((Intent)(object = new Intent((Intent)object))).hasCategory(CATEGORY_SLICE)) {
            ((Intent)object).addCategory(CATEGORY_SLICE);
        }
        object = (object = this.mContext.getPackageManager().queryIntentContentProviders((Intent)object, 0)) != null && !object.isEmpty() ? ((ResolveInfo)object.get((int)0)).providerInfo.authority : null;
        return object;
    }

    private Uri resolveStatic(Intent parcelable, ContentResolver contentResolver) {
        Preconditions.checkNotNull(parcelable, "intent");
        boolean bl = parcelable.getComponent() != null || parcelable.getPackage() != null || parcelable.getData() != null;
        Preconditions.checkArgument(bl, "Slice intent must be explicit %s", parcelable);
        Uri uri = parcelable.getData();
        if (uri != null && "vnd.android.slice".equals(contentResolver.getType(uri))) {
            return uri;
        }
        parcelable = this.mContext.getPackageManager().resolveActivity((Intent)parcelable, 128);
        if (parcelable != null && ((ResolveInfo)parcelable).activityInfo != null && parcelable.activityInfo.metaData != null && parcelable.activityInfo.metaData.containsKey(SLICE_METADATA_KEY)) {
            return Uri.parse(parcelable.activityInfo.metaData.getString(SLICE_METADATA_KEY));
        }
        return null;
    }

    @Deprecated
    public Slice bindSlice(Intent intent, List<SliceSpec> list) {
        return this.bindSlice(intent, new ArraySet<SliceSpec>(list));
    }

    /*
     * Exception decompiling
     */
    public Slice bindSlice(Intent var1_1, Set<SliceSpec> var2_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
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

    @Deprecated
    public Slice bindSlice(Uri uri, List<SliceSpec> list) {
        return this.bindSlice(uri, new ArraySet<SliceSpec>(list));
    }

    /*
     * Exception decompiling
     */
    public Slice bindSlice(Uri var1_1, Set<SliceSpec> var2_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
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

    public int checkSlicePermission(Uri uri, int n, int n2) {
        try {
            n = this.mService.checkSlicePermission(uri, this.mContext.getPackageName(), null, n, n2, null);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void enforceSlicePermission(Uri object, String charSequence, int n, int n2, String[] arrstring) {
        try {
            if (UserHandle.isSameApp(n2, Process.myUid())) {
                return;
            }
            if (charSequence == null) {
                object = new SecurityException("No pkg specified");
                throw object;
            }
            if (this.mService.checkSlicePermission((Uri)object, this.mContext.getPackageName(), (String)charSequence, n, n2, arrstring) != -1) {
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("User ");
            ((StringBuilder)charSequence).append(n2);
            ((StringBuilder)charSequence).append(" does not have slice permission for ");
            ((StringBuilder)charSequence).append(object);
            ((StringBuilder)charSequence).append(".");
            SecurityException securityException = new SecurityException(((StringBuilder)charSequence).toString());
            throw securityException;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<Uri> getPinnedSlices() {
        try {
            List<Uri> list = Arrays.asList(this.mService.getPinnedSlices(this.mContext.getPackageName()));
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Set<SliceSpec> getPinnedSpecs(Uri object) {
        try {
            object = new ArraySet<SliceSpec>(Arrays.asList(this.mService.getPinnedSpecs((Uri)object, this.mContext.getPackageName())));
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Exception decompiling
     */
    public Collection<Uri> getSliceDescendants(Uri var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
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

    public void grantPermissionFromUser(Uri uri, String string2, boolean bl) {
        try {
            this.mService.grantPermissionFromUser(uri, string2, this.mContext.getPackageName(), bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void grantSlicePermission(String string2, Uri uri) {
        try {
            this.mService.grantSlicePermission(this.mContext.getPackageName(), string2, uri);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean hasSliceAccess() {
        try {
            boolean bl = this.mService.hasSliceAccess(this.mContext.getPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Exception decompiling
     */
    public Uri mapIntentToUri(Intent var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
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

    @Deprecated
    public void pinSlice(Uri uri, List<SliceSpec> list) {
        this.pinSlice(uri, new ArraySet<SliceSpec>(list));
    }

    public void pinSlice(Uri uri, Set<SliceSpec> set) {
        try {
            this.mService.pinSlice(this.mContext.getPackageName(), uri, set.toArray(new SliceSpec[set.size()]), this.mToken);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void revokeSlicePermission(String string2, Uri uri) {
        try {
            this.mService.revokeSlicePermission(this.mContext.getPackageName(), string2, uri);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void unpinSlice(Uri uri) {
        try {
            this.mService.unpinSlice(this.mContext.getPackageName(), uri, this.mToken);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }
}

