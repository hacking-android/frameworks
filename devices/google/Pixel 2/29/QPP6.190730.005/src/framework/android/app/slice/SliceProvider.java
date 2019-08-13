/*
 * Decompiled with CFR 0.145.
 */
package android.app.slice;

import android.app.PendingIntent;
import android.app.slice.Slice;
import android.app.slice.SliceManager;
import android.app.slice.SliceSpec;
import android.app.slice._$$Lambda$SliceProvider$bIgM5f4PsMvz_YYWEeFTjvTqevw;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Parcelable;
import android.os.Process;
import android.os.StrictMode;
import android.util.ArraySet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class SliceProvider
extends ContentProvider {
    private static final boolean DEBUG = false;
    public static final String EXTRA_BIND_URI = "slice_uri";
    public static final String EXTRA_INTENT = "slice_intent";
    public static final String EXTRA_PKG = "pkg";
    public static final String EXTRA_PROVIDER_PKG = "provider_pkg";
    public static final String EXTRA_RESULT = "result";
    public static final String EXTRA_SLICE = "slice";
    public static final String EXTRA_SLICE_DESCENDANTS = "slice_descendants";
    public static final String EXTRA_SUPPORTED_SPECS = "supported_specs";
    public static final String METHOD_GET_DESCENDANTS = "get_descendants";
    public static final String METHOD_GET_PERMISSIONS = "get_permissions";
    public static final String METHOD_MAP_INTENT = "map_slice";
    public static final String METHOD_MAP_ONLY_INTENT = "map_only";
    public static final String METHOD_PIN = "pin";
    public static final String METHOD_SLICE = "bind_slice";
    public static final String METHOD_UNPIN = "unpin";
    private static final long SLICE_BIND_ANR = 2000L;
    public static final String SLICE_TYPE = "vnd.android.slice";
    private static final String TAG = "SliceProvider";
    private final Runnable mAnr = new _$$Lambda$SliceProvider$bIgM5f4PsMvz_YYWEeFTjvTqevw(this);
    private final String[] mAutoGrantPermissions;
    private String mCallback;
    private SliceManager mSliceManager;

    public SliceProvider() {
        this.mAutoGrantPermissions = new String[0];
    }

    public SliceProvider(String ... arrstring) {
        this.mAutoGrantPermissions = arrstring;
    }

    public static PendingIntent createPermissionIntent(Context context, Uri uri, String string2) {
        Intent intent = new Intent("com.android.intent.action.REQUEST_SLICE_PERMISSION");
        intent.setComponent(new ComponentName("com.android.systemui", "com.android.systemui.SlicePermissionActivity"));
        intent.putExtra(EXTRA_BIND_URI, uri);
        intent.putExtra(EXTRA_PKG, string2);
        intent.putExtra(EXTRA_PROVIDER_PKG, context.getPackageName());
        intent.setData(uri.buildUpon().appendQueryParameter("package", string2).build());
        return PendingIntent.getActivity(context, 0, intent, 0);
    }

    public static CharSequence getPermissionString(Context object, String string2) {
        PackageManager packageManager = ((Context)object).getPackageManager();
        try {
            object = ((Context)object).getString(17041039, packageManager.getApplicationInfo(string2, 0).loadLabel(packageManager), ((Context)object).getApplicationInfo().loadLabel(packageManager));
            return object;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            throw new RuntimeException("Unknown calling app", nameNotFoundException);
        }
    }

    private Slice handleBindSlice(Uri parcelable, List<SliceSpec> list, String string2, int n, int n2) {
        if (string2 == null) {
            string2 = this.getContext().getPackageManager().getNameForUid(n);
        }
        try {
            this.mSliceManager.enforceSlicePermission((Uri)parcelable, string2, n2, n, this.mAutoGrantPermissions);
        }
        catch (SecurityException securityException) {
            return this.createPermissionSlice(this.getContext(), (Uri)parcelable, string2);
        }
        this.mCallback = "onBindSlice";
        Handler.getMain().postDelayed(this.mAnr, 2000L);
        try {
            parcelable = this.onBindSliceStrict((Uri)parcelable, list);
            return parcelable;
        }
        finally {
            Handler.getMain().removeCallbacks(this.mAnr);
        }
    }

    private Collection<Uri> handleGetDescendants(Uri uri) {
        this.mCallback = "onGetSliceDescendants";
        return this.onGetSliceDescendants(uri);
    }

    private void handlePinSlice(Uri uri) {
        this.mCallback = "onSlicePinned";
        Handler.getMain().postDelayed(this.mAnr, 2000L);
        try {
            this.onSlicePinned(uri);
            return;
        }
        finally {
            Handler.getMain().removeCallbacks(this.mAnr);
        }
    }

    private void handleUnpinSlice(Uri uri) {
        this.mCallback = "onSliceUnpinned";
        Handler.getMain().postDelayed(this.mAnr, 2000L);
        try {
            this.onSliceUnpinned(uri);
            return;
        }
        finally {
            Handler.getMain().removeCallbacks(this.mAnr);
        }
    }

    private Slice onBindSliceStrict(Uri parcelable, List<SliceSpec> list) {
        StrictMode.ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        try {
            ArraySet<SliceSpec> arraySet = new ArraySet<SliceSpec>();
            StrictMode.setThreadPolicy(((StrictMode.ThreadPolicy.Builder)((Object)arraySet)).detectAll().penaltyDeath().build());
            arraySet = new ArraySet<SliceSpec>(list);
            parcelable = this.onBindSlice((Uri)parcelable, (Set<SliceSpec>)arraySet);
            return parcelable;
        }
        finally {
            StrictMode.setThreadPolicy(threadPolicy);
        }
    }

    @Override
    public void attachInfo(Context context, ProviderInfo providerInfo) {
        super.attachInfo(context, providerInfo);
        this.mSliceManager = context.getSystemService(SliceManager.class);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Bundle call(String object, String object2, Bundle cloneable) {
        if (((String)object).equals(METHOD_SLICE)) {
            object = this.handleBindSlice(SliceProvider.getUriWithoutUserId((Uri)((Bundle)cloneable).getParcelable(EXTRA_BIND_URI)), ((Bundle)cloneable).getParcelableArrayList(EXTRA_SUPPORTED_SPECS), this.getCallingPackage(), Binder.getCallingUid(), Binder.getCallingPid());
            object2 = new Bundle();
            ((Bundle)object2).putParcelable(EXTRA_SLICE, (Parcelable)object);
            return object2;
        }
        if (((String)object).equals(METHOD_MAP_INTENT)) {
            object = (Intent)((Bundle)cloneable).getParcelable(EXTRA_INTENT);
            if (object == null) {
                return null;
            }
            object = this.onMapIntentToUri((Intent)object);
            cloneable = ((Bundle)cloneable).getParcelableArrayList(EXTRA_SUPPORTED_SPECS);
            object2 = new Bundle();
            if (object != null) {
                ((Bundle)object2).putParcelable(EXTRA_SLICE, this.handleBindSlice((Uri)object, (List<SliceSpec>)((Object)cloneable), this.getCallingPackage(), Binder.getCallingUid(), Binder.getCallingPid()));
                return object2;
            } else {
                ((Bundle)object2).putParcelable(EXTRA_SLICE, null);
            }
            return object2;
        }
        if (((String)object).equals(METHOD_MAP_ONLY_INTENT)) {
            object = (Intent)((Bundle)cloneable).getParcelable(EXTRA_INTENT);
            if (object == null) {
                return null;
            }
            object = this.onMapIntentToUri((Intent)object);
            object2 = new Bundle();
            ((Bundle)object2).putParcelable(EXTRA_SLICE, (Parcelable)object);
            return object2;
        }
        if (((String)object).equals(METHOD_PIN)) {
            Uri uri = SliceProvider.getUriWithoutUserId((Uri)((Bundle)cloneable).getParcelable(EXTRA_BIND_URI));
            if (Binder.getCallingUid() != 1000) throw new SecurityException("Only the system can pin/unpin slices");
            this.handlePinSlice(uri);
            return super.call((String)object, (String)object2, (Bundle)cloneable);
        } else if (((String)object).equals(METHOD_UNPIN)) {
            Uri uri = SliceProvider.getUriWithoutUserId((Uri)((Bundle)cloneable).getParcelable(EXTRA_BIND_URI));
            if (Binder.getCallingUid() != 1000) throw new SecurityException("Only the system can pin/unpin slices");
            this.handleUnpinSlice(uri);
            return super.call((String)object, (String)object2, (Bundle)cloneable);
        } else {
            if (((String)object).equals(METHOD_GET_DESCENDANTS)) {
                object2 = SliceProvider.getUriWithoutUserId((Uri)((Bundle)cloneable).getParcelable(EXTRA_BIND_URI));
                object = new Bundle();
                ((Bundle)object).putParcelableArrayList(EXTRA_SLICE_DESCENDANTS, new ArrayList<Uri>(this.handleGetDescendants((Uri)object2)));
                return object;
            }
            if (!((String)object).equals(METHOD_GET_PERMISSIONS)) return super.call((String)object, (String)object2, (Bundle)cloneable);
            if (Binder.getCallingUid() != 1000) throw new SecurityException("Only the system can get permissions");
            object = new Bundle();
            ((BaseBundle)object).putStringArray(EXTRA_RESULT, this.mAutoGrantPermissions);
            return object;
        }
    }

    public Slice createPermissionSlice(Context context, Uri uri, String string2) {
        this.mCallback = "onCreatePermissionRequest";
        Handler.getMain().postDelayed(this.mAnr, 2000L);
        Object object = this.onCreatePermissionRequest(uri);
        Slice.Builder builder = new Slice.Builder(uri);
        object = new Slice.Builder(builder).addIcon(Icon.createWithResource(context, 17302765), null, Collections.<String>emptyList()).addHints(Arrays.asList("title", "shortcut")).addAction((PendingIntent)object, new Slice.Builder(builder).build(), null);
        TypedValue typedValue = new TypedValue();
        new ContextThemeWrapper(context, 16974123).getTheme().resolveAttribute(16843829, typedValue, true);
        int n = typedValue.data;
        builder.addSubSlice(new Slice.Builder(uri.buildUpon().appendPath("permission").build()).addIcon(Icon.createWithResource(context, 17302286), null, Collections.<String>emptyList()).addText(SliceProvider.getPermissionString(context, string2), null, Collections.<String>emptyList()).addInt(n, "color", Collections.<String>emptyList()).addSubSlice(((Slice.Builder)object).build(), null).build(), null);
        return builder.addHints(Arrays.asList("permission_request")).build();
        finally {
            Handler.getMain().removeCallbacks(this.mAnr);
        }
    }

    @Override
    public final int delete(Uri uri, String string2, String[] arrstring) {
        return 0;
    }

    @Override
    public final String getType(Uri uri) {
        return SLICE_TYPE;
    }

    @Override
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public /* synthetic */ void lambda$new$0$SliceProvider() {
        Process.sendSignal(Process.myPid(), 3);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Timed out while handling slice callback ");
        stringBuilder.append(this.mCallback);
        Log.wtf(TAG, stringBuilder.toString());
    }

    @Deprecated
    public Slice onBindSlice(Uri uri, List<SliceSpec> list) {
        return null;
    }

    public Slice onBindSlice(Uri uri, Set<SliceSpec> set) {
        return this.onBindSlice(uri, new ArrayList<SliceSpec>(set));
    }

    public PendingIntent onCreatePermissionRequest(Uri uri) {
        return SliceProvider.createPermissionIntent(this.getContext(), uri, this.getCallingPackage());
    }

    public Collection<Uri> onGetSliceDescendants(Uri uri) {
        return Collections.emptyList();
    }

    public Uri onMapIntentToUri(Intent intent) {
        throw new UnsupportedOperationException("This provider has not implemented intent to uri mapping");
    }

    public void onSlicePinned(Uri uri) {
    }

    public void onSliceUnpinned(Uri uri) {
    }

    @Override
    public final Cursor query(Uri uri, String[] arrstring, Bundle bundle, CancellationSignal cancellationSignal) {
        return null;
    }

    @Override
    public final Cursor query(Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3) {
        return null;
    }

    @Override
    public final Cursor query(Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3, CancellationSignal cancellationSignal) {
        return null;
    }

    @Override
    public final int update(Uri uri, ContentValues contentValues, String string2, String[] arrstring) {
        return 0;
    }
}

