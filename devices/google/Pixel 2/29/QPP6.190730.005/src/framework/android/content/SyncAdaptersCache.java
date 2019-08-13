/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Context;
import android.content.SyncAdapterType;
import android.content.pm.RegisteredServicesCache;
import android.content.pm.XmlSerializerAndParser;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.SparseArray;
import com.android.internal.R;
import com.android.internal.annotations.GuardedBy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class SyncAdaptersCache
extends RegisteredServicesCache<SyncAdapterType> {
    private static final String ATTRIBUTES_NAME = "sync-adapter";
    private static final String SERVICE_INTERFACE = "android.content.SyncAdapter";
    private static final String SERVICE_META_DATA = "android.content.SyncAdapter";
    private static final String TAG = "Account";
    private static final MySerializer sSerializer = new MySerializer();
    @GuardedBy(value={"mServicesLock"})
    private SparseArray<ArrayMap<String, String[]>> mAuthorityToSyncAdapters = new SparseArray();

    @UnsupportedAppUsage
    public SyncAdaptersCache(Context context) {
        super(context, "android.content.SyncAdapter", "android.content.SyncAdapter", ATTRIBUTES_NAME, sSerializer);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String[] getSyncAdapterPackagesForAuthority(String arrstring, int n) {
        Object object = this.mServicesLock;
        synchronized (object) {
            ArrayList<String> arrayList = this.mAuthorityToSyncAdapters.get(n);
            ArrayMap<String[], String[]> arrayMap = arrayList;
            if (arrayList == null) {
                arrayMap = new ArrayMap<String[], String[]>();
                this.mAuthorityToSyncAdapters.put(n, arrayMap);
            }
            if (arrayMap.containsKey(arrstring)) {
                return (String[])arrayMap.get(arrstring);
            }
            String[] arrstring2 = this.getAllServices(n);
            arrayList = new ArrayList<String>();
            arrstring2 = arrstring2.iterator();
            do {
                if (!arrstring2.hasNext()) {
                    arrstring2 = new String[arrayList.size()];
                    arrayList.toArray(arrstring2);
                    arrayMap.put(arrstring, arrstring2);
                    return arrstring2;
                }
                RegisteredServicesCache.ServiceInfo serviceInfo = (RegisteredServicesCache.ServiceInfo)arrstring2.next();
                if (!arrstring.equals(((SyncAdapterType)serviceInfo.type).authority) || serviceInfo.componentName == null) continue;
                arrayList.add(serviceInfo.componentName.getPackageName());
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void onServicesChangedLocked(int n) {
        Object object = this.mServicesLock;
        synchronized (object) {
            ArrayMap<String, String[]> arrayMap = this.mAuthorityToSyncAdapters.get(n);
            if (arrayMap != null) {
                arrayMap.clear();
            }
        }
        super.onServicesChangedLocked(n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void onUserRemoved(int n) {
        Object object = this.mServicesLock;
        synchronized (object) {
            this.mAuthorityToSyncAdapters.remove(n);
        }
        super.onUserRemoved(n);
    }

    @Override
    public SyncAdapterType parseServiceAttributes(Resources object, String object2, AttributeSet object3) {
        object = ((Resources)object).obtainAttributes((AttributeSet)object3, R.styleable.SyncAdapter);
        try {
            object3 = ((TypedArray)object).getString(2);
            String string2 = ((TypedArray)object).getString(1);
            if (!TextUtils.isEmpty((CharSequence)object3)) {
                if (TextUtils.isEmpty(string2)) {
                } else {
                    boolean bl = ((TypedArray)object).getBoolean(3, true);
                    boolean bl2 = ((TypedArray)object).getBoolean(4, true);
                    boolean bl3 = ((TypedArray)object).getBoolean(6, false);
                    boolean bl4 = ((TypedArray)object).getBoolean(5, false);
                    object2 = new SyncAdapterType((String)object3, string2, bl, bl2, bl3, bl4, ((TypedArray)object).getString(0), (String)object2);
                    return object2;
                }
            }
            return null;
        }
        finally {
            ((TypedArray)object).recycle();
        }
    }

    static class MySerializer
    implements XmlSerializerAndParser<SyncAdapterType> {
        MySerializer() {
        }

        @Override
        public SyncAdapterType createFromXml(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
            return SyncAdapterType.newKey(xmlPullParser.getAttributeValue(null, "authority"), xmlPullParser.getAttributeValue(null, "accountType"));
        }

        @Override
        public void writeAsXml(SyncAdapterType syncAdapterType, XmlSerializer xmlSerializer) throws IOException {
            xmlSerializer.attribute(null, "authority", syncAdapterType.authority);
            xmlSerializer.attribute(null, "accountType", syncAdapterType.accountType);
        }
    }

}

