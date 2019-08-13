/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.IRestrictionsManager;
import android.content.Intent;
import android.content.RestrictionEntry;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import com.android.internal.R;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

public class RestrictionsManager {
    public static final String ACTION_PERMISSION_RESPONSE_RECEIVED = "android.content.action.PERMISSION_RESPONSE_RECEIVED";
    public static final String ACTION_REQUEST_LOCAL_APPROVAL = "android.content.action.REQUEST_LOCAL_APPROVAL";
    public static final String ACTION_REQUEST_PERMISSION = "android.content.action.REQUEST_PERMISSION";
    public static final String EXTRA_PACKAGE_NAME = "android.content.extra.PACKAGE_NAME";
    public static final String EXTRA_REQUEST_BUNDLE = "android.content.extra.REQUEST_BUNDLE";
    public static final String EXTRA_REQUEST_ID = "android.content.extra.REQUEST_ID";
    public static final String EXTRA_REQUEST_TYPE = "android.content.extra.REQUEST_TYPE";
    public static final String EXTRA_RESPONSE_BUNDLE = "android.content.extra.RESPONSE_BUNDLE";
    public static final String META_DATA_APP_RESTRICTIONS = "android.content.APP_RESTRICTIONS";
    public static final String REQUEST_KEY_APPROVE_LABEL = "android.request.approve_label";
    public static final String REQUEST_KEY_DATA = "android.request.data";
    public static final String REQUEST_KEY_DENY_LABEL = "android.request.deny_label";
    public static final String REQUEST_KEY_ICON = "android.request.icon";
    public static final String REQUEST_KEY_ID = "android.request.id";
    public static final String REQUEST_KEY_MESSAGE = "android.request.mesg";
    public static final String REQUEST_KEY_NEW_REQUEST = "android.request.new_request";
    public static final String REQUEST_KEY_TITLE = "android.request.title";
    public static final String REQUEST_TYPE_APPROVAL = "android.request.type.approval";
    public static final String RESPONSE_KEY_ERROR_CODE = "android.response.errorcode";
    public static final String RESPONSE_KEY_MESSAGE = "android.response.msg";
    public static final String RESPONSE_KEY_RESPONSE_TIMESTAMP = "android.response.timestamp";
    public static final String RESPONSE_KEY_RESULT = "android.response.result";
    public static final int RESULT_APPROVED = 1;
    public static final int RESULT_DENIED = 2;
    public static final int RESULT_ERROR = 5;
    public static final int RESULT_ERROR_BAD_REQUEST = 1;
    public static final int RESULT_ERROR_INTERNAL = 3;
    public static final int RESULT_ERROR_NETWORK = 2;
    public static final int RESULT_NO_RESPONSE = 3;
    public static final int RESULT_UNKNOWN_REQUEST = 4;
    private static final String TAG = "RestrictionsManager";
    private static final String TAG_RESTRICTION = "restriction";
    private final Context mContext;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final IRestrictionsManager mService;

    public RestrictionsManager(Context context, IRestrictionsManager iRestrictionsManager) {
        this.mContext = context;
        this.mService = iRestrictionsManager;
    }

    private static Bundle addRestrictionToBundle(Bundle object, RestrictionEntry restrictionEntry) {
        switch (restrictionEntry.getType()) {
            default: {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unsupported restrictionEntry type: ");
                ((StringBuilder)object).append(restrictionEntry.getType());
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            case 8: {
                RestrictionEntry[] arrrestrictionEntry = restrictionEntry.getRestrictions();
                Parcelable[] arrparcelable = new Bundle[arrrestrictionEntry.length];
                for (int i = 0; i < arrrestrictionEntry.length; ++i) {
                    RestrictionEntry[] arrrestrictionEntry2 = arrrestrictionEntry[i].getRestrictions();
                    if (arrrestrictionEntry2 == null) {
                        Log.w(TAG, "addRestrictionToBundle: Non-bundle entry found in bundle array");
                        arrparcelable[i] = new Bundle();
                        continue;
                    }
                    arrparcelable[i] = RestrictionsManager.convertRestrictionsToBundle(Arrays.asList(arrrestrictionEntry2));
                }
                ((Bundle)object).putParcelableArray(restrictionEntry.getKey(), arrparcelable);
                break;
            }
            case 7: {
                Bundle bundle = RestrictionsManager.convertRestrictionsToBundle(Arrays.asList(restrictionEntry.getRestrictions()));
                ((Bundle)object).putBundle(restrictionEntry.getKey(), bundle);
                break;
            }
            case 5: {
                ((BaseBundle)object).putInt(restrictionEntry.getKey(), restrictionEntry.getIntValue());
                break;
            }
            case 2: 
            case 3: 
            case 4: {
                ((BaseBundle)object).putStringArray(restrictionEntry.getKey(), restrictionEntry.getAllSelectedStrings());
                break;
            }
            case 1: {
                ((BaseBundle)object).putBoolean(restrictionEntry.getKey(), restrictionEntry.getSelectedState());
                break;
            }
            case 0: 
            case 6: {
                ((BaseBundle)object).putString(restrictionEntry.getKey(), restrictionEntry.getSelectedString());
            }
        }
        return object;
    }

    public static Bundle convertRestrictionsToBundle(List<RestrictionEntry> object) {
        Bundle bundle = new Bundle();
        object = object.iterator();
        while (object.hasNext()) {
            RestrictionsManager.addRestrictionToBundle(bundle, (RestrictionEntry)object.next());
        }
        return bundle;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private List<RestrictionEntry> loadManifestRestrictions(String string2, XmlResourceParser object) {
        Context context = this.mContext.createPackageContext(string2, 0);
        ArrayList<RestrictionEntry> arrayList = new ArrayList<RestrictionEntry>();
        try {
            int n = object.next();
            while (n != 1) {
                RestrictionEntry restrictionEntry;
                if (n == 2 && (restrictionEntry = this.loadRestrictionElement(context, (XmlResourceParser)object)) != null) {
                    arrayList.add(restrictionEntry);
                }
                n = object.next();
            }
            return arrayList;
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Reading restriction metadata for ");
            ((StringBuilder)object).append(string2);
            Log.w(TAG, ((StringBuilder)object).toString(), iOException);
            return null;
        }
        catch (XmlPullParserException xmlPullParserException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Reading restriction metadata for ");
            stringBuilder.append(string2);
            Log.w(TAG, stringBuilder.toString(), xmlPullParserException);
            return null;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return null;
        }
    }

    private RestrictionEntry loadRestriction(Context object, TypedArray object2, XmlResourceParser xmlResourceParser) throws IOException, XmlPullParserException {
        Object object3 = object;
        String string2 = ((TypedArray)object2).getString(3);
        int n = ((TypedArray)object2).getInt(6, -1);
        String string3 = ((TypedArray)object2).getString(2);
        String string4 = ((TypedArray)object2).getString(0);
        int n2 = ((TypedArray)object2).getResourceId(1, 0);
        int n3 = ((TypedArray)object2).getResourceId(5, 0);
        if (n == -1) {
            Log.w(TAG, "restrictionType cannot be omitted");
            return null;
        }
        if (string2 == null) {
            Log.w(TAG, "key cannot be omitted");
            return null;
        }
        RestrictionEntry restrictionEntry = new RestrictionEntry(n, string2);
        restrictionEntry.setTitle(string3);
        restrictionEntry.setDescription(string4);
        if (n2 != 0) {
            restrictionEntry.setChoiceEntries((Context)object3, n2);
        }
        if (n3 != 0) {
            restrictionEntry.setChoiceValues((Context)object3, n3);
        }
        switch (n) {
            default: {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unknown restriction type ");
                ((StringBuilder)object).append(n);
                Log.w(TAG, ((StringBuilder)object).toString());
                break;
            }
            case 7: 
            case 8: {
                n3 = xmlResourceParser.getDepth();
                object2 = new ArrayList();
                while (XmlUtils.nextElementWithin(xmlResourceParser, n3)) {
                    object3 = this.loadRestrictionElement((Context)object, xmlResourceParser);
                    if (object3 == null) {
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("Child entry cannot be loaded for bundle restriction ");
                        ((StringBuilder)object3).append(string2);
                        Log.w(TAG, ((StringBuilder)object3).toString());
                        continue;
                    }
                    object2.add(object3);
                    if (n != 8 || ((RestrictionEntry)object3).getType() == 7) continue;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("bundle_array ");
                    ((StringBuilder)object3).append(string2);
                    ((StringBuilder)object3).append(" can only contain entries of type bundle");
                    Log.w(TAG, ((StringBuilder)object3).toString());
                }
                restrictionEntry.setRestrictions(object2.toArray(new RestrictionEntry[object2.size()]));
                break;
            }
            case 5: {
                restrictionEntry.setIntValue(((TypedArray)object2).getInt(4, 0));
                break;
            }
            case 4: {
                n = ((TypedArray)object2).getResourceId(4, 0);
                if (n == 0) break;
                restrictionEntry.setAllSelectedStrings(((Context)object).getResources().getStringArray(n));
                break;
            }
            case 1: {
                restrictionEntry.setSelectedState(((TypedArray)object2).getBoolean(4, false));
                break;
            }
            case 0: 
            case 2: 
            case 6: {
                restrictionEntry.setSelectedString(((TypedArray)object2).getString(4));
            }
        }
        return restrictionEntry;
    }

    private RestrictionEntry loadRestrictionElement(Context context, XmlResourceParser xmlResourceParser) throws IOException, XmlPullParserException {
        AttributeSet attributeSet;
        if (xmlResourceParser.getName().equals(TAG_RESTRICTION) && (attributeSet = Xml.asAttributeSet(xmlResourceParser)) != null) {
            return this.loadRestriction(context, context.obtainStyledAttributes(attributeSet, R.styleable.RestrictionEntry), xmlResourceParser);
        }
        return null;
    }

    public Intent createLocalApprovalIntent() {
        try {
            if (this.mService != null) {
                Intent intent = this.mService.createLocalApprovalIntent();
                return intent;
            }
            return null;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Bundle getApplicationRestrictions() {
        try {
            if (this.mService != null) {
                Bundle bundle = this.mService.getApplicationRestrictions(this.mContext.getPackageName());
                return bundle;
            }
            return null;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<RestrictionEntry> getManifestRestrictions(String string2) {
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = this.mContext.getPackageManager().getApplicationInfo(string2, 128);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No such package ");
            stringBuilder.append(string2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (applicationInfo != null && applicationInfo.metaData.containsKey(META_DATA_APP_RESTRICTIONS)) {
            return this.loadManifestRestrictions(string2, applicationInfo.loadXmlMetaData(this.mContext.getPackageManager(), META_DATA_APP_RESTRICTIONS));
        }
        return null;
    }

    public boolean hasRestrictionsProvider() {
        try {
            if (this.mService != null) {
                boolean bl = this.mService.hasRestrictionsProvider();
                return bl;
            }
            return false;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void notifyPermissionResponse(String string2, PersistableBundle persistableBundle) {
        if (string2 != null) {
            if (persistableBundle != null) {
                if (persistableBundle.containsKey(REQUEST_KEY_ID)) {
                    if (persistableBundle.containsKey(RESPONSE_KEY_RESULT)) {
                        try {
                            if (this.mService != null) {
                                this.mService.notifyPermissionResponse(string2, persistableBundle);
                            }
                            return;
                        }
                        catch (RemoteException remoteException) {
                            throw remoteException.rethrowFromSystemServer();
                        }
                    }
                    throw new IllegalArgumentException("RESPONSE_KEY_RESULT must be specified");
                }
                throw new IllegalArgumentException("REQUEST_KEY_ID must be specified");
            }
            throw new NullPointerException("request cannot be null");
        }
        throw new NullPointerException("packageName cannot be null");
    }

    public void requestPermission(String string2, String string3, PersistableBundle persistableBundle) {
        if (string2 != null) {
            if (string3 != null) {
                if (persistableBundle != null) {
                    try {
                        if (this.mService != null) {
                            this.mService.requestPermission(this.mContext.getPackageName(), string2, string3, persistableBundle);
                        }
                        return;
                    }
                    catch (RemoteException remoteException) {
                        throw remoteException.rethrowFromSystemServer();
                    }
                }
                throw new NullPointerException("request cannot be null");
            }
            throw new NullPointerException("requestId cannot be null");
        }
        throw new NullPointerException("requestType cannot be null");
    }
}

